package com.unteck.common.dao.support;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.InstanceAlreadyExistsException;
import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.unteck.common.dao.jmx.SQLManager;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class CustomSQL
  implements ApplicationContextAware
{
  private static Logger logger = LoggerFactory.getLogger(CustomSQL.class);
  private Map<String, SQLBean> _sqlPool = new ConcurrentHashMap();
  private static final String STRING_SPACE = " ";
  private final SAXReader saxReader = new SAXReader();
  private Configuration configuration = null;
  private StringTemplateLoader stringTemplateLoader = null;
  private Map<String, Long> configMap = new HashMap();
  private boolean reloadSQLFiles = false;
  private static CustomSQL instance = null;
  
  public void setApplicationContext(ApplicationContext context)
    throws BeansException
  {
    instance = (CustomSQL)context.getBean(CustomSQL.class);
    instance.init();
  }
  
  public static CustomSQL getInstance()
  {
    if (instance == null) {
      synchronized (CustomSQL.class)
      {
        if (instance == null)
        {
          instance = new CustomSQL();
          instance.init();
        }
      }
    }
    return instance;
  }
  
  public void init()
  {
    this.reloadSQLFiles = Boolean.valueOf(System.getProperty("reloadSQLFiles")).booleanValue();
    try
    {
      this.configuration = new Configuration();
      this.stringTemplateLoader = new StringTemplateLoader();
      this.configuration.setDefaultEncoding("UTF-8");
      this.configuration.setNumberFormat("#");
      
      Resource[] configs = loadConfigs();
      for (Resource _config : configs)
      {
        logger.info("Loading " + _config.getURL().getPath());
        this.configMap.put(_config.getURL().getPath(), Long.valueOf(_config.lastModified()));
        read(_config.getURL().getPath(),_config.getInputStream());
      }
      this.configuration.setTemplateLoader(this.stringTemplateLoader);
    }
    catch (Exception e)
    {
      logger.error("", e);
    }
    SQLManager manager = new SQLManager();
    register("com.unteck.common.dao:type=SQLStat", manager);
  }
  
  private ObjectName register(String name, Object mbean)
  {
    try
    {
      ObjectName objectName = new ObjectName(name);
      
      MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
      try
      {
        mbeanServer.registerMBean(mbean, objectName);
      }
      catch (InstanceAlreadyExistsException ex)
      {
        mbeanServer.unregisterMBean(objectName);
        mbeanServer.registerMBean(mbean, objectName);
      }
      return objectName;
    }
    catch (JMException e)
    {
      throw new IllegalArgumentException(name, e);
    }
  }
  
  protected Resource[] loadConfigs()
    throws IOException
  {
    PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
    return patternResolver.getResources("classpath*:custom-sql/*.xml");
  }
  
  public String get(String id)
  {
    if (this.reloadSQLFiles) {
      try
      {
        reloadConfig();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    SQLBean bean = (SQLBean)this._sqlPool.get(id);
    if (bean == null) {
      throw new IllegalStateException("sql id 不存在：" + id);
    }
    if ("simple".equals(bean.getTempateType())) {
      return ((SQLBean)this._sqlPool.get(id)).getContent();
    }
    throw new RuntimeException("SQL 模板类型不正确，只可以是simple类型");
  }
  
  public SQLBean getSQLBean(String id)
  {
      if (this.reloadSQLFiles) {
          try
          {
              reloadConfig();
          }
          catch (IOException e)
          {
              e.printStackTrace();
          }
      }
      return (SQLBean)this._sqlPool.get(id);
  }
  public String get(String id, Map<String, Object> models)
  {
    if (this.reloadSQLFiles) {
      try
      {
        reloadConfig();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    try
    {
      Template template = this.configuration.getTemplate(id);
      
      StringWriter writer = new StringWriter();
      template.process(models, writer);
      return writer.toString();
    }
    catch (TemplateException e)
    {
      throw new RuntimeException("Parse sql failed", e);
    }
    catch (FileNotFoundException e)
    {
      throw new IllegalStateException("sql id 不存在：" + id);
    }
    catch (IOException e)
    {
      throw new RuntimeException("Parse sql failed", e);
    }
  }
  
  private void reloadConfig()
    throws IOException
  {
    Resource[] newConfigs = loadConfigs();
    for (Resource newConfig : newConfigs)
    {
      boolean flag = true;
      for (Map.Entry<String, Long> entry : this.configMap.entrySet()) {
        if (newConfig.getURL().getPath().equals(entry.getKey()))
        {
          flag = false;
          if ("file".equals(newConfig.getURL().getProtocol()) && newConfig.getFile().lastModified() != ((Long)entry.getValue()).longValue())
          {
              try{
            this.configMap.put((String)entry.getKey(), Long.valueOf(newConfig.getFile().lastModified()));
            read(newConfig.getURL().getPath(),newConfig.getInputStream());
            logger.info("Reloading " + (String)entry.getKey());
          }catch(Exception e) {
              logger.error("read",e);
          }
            break;
          }
        }
      }
      if (flag && "file".equals(newConfig.getURL().getProtocol()))
      {
          try{
        this.configMap.put(newConfig.getURL().getPath(), Long.valueOf(newConfig.getFile().lastModified()));
        read(newConfig.getURL().getPath(),newConfig.getInputStream());
        logger.info("Reloading " + newConfig.getURL().getPath());
          }catch(Exception e) {
              logger.error("read",e);
          }
      }
    }
  }
  
  protected void read(String path, InputStream is)
  {
    if (is == null) {
      return;
    }
    Document document = null;
    try
    {
      document = this.saxReader.read(is);
    }
    catch (DocumentException e)
    {
        logger.error("error loading file " + path,e);
        return;
    }
    Element rootElement = document.getRootElement();
    for (Object sqlObj : rootElement.elements("sql"))
    {
      Element sqlElement = (Element)sqlObj;
      
      String id = sqlElement.attributeValue("id");
      String sqlType = sqlElement.attributeValue("sqlType");
      String tempateType = sqlElement.attributeValue("tempateType");
      String style = sqlElement.attributeValue("style");
      String view = sqlElement.attributeValue("view");
      String xls = sqlElement.attributeValue("xls");
      if (("simple".equals(tempateType)) || ("freeMarker".equals(tempateType)))
      {
        String content = transform(sqlElement.getText());
        
        SQLBean bean = new SQLBean();
        bean.setTempateType(tempateType);
        bean.setSqlType(sqlType);
        bean.setContent(content);
        bean.setStyle(style);
        bean.setView(view);
        bean.setXls(xls);
        if ("freeMarker".equals(tempateType)) {
          this.stringTemplateLoader.putTemplate(id, content);
        }
        this._sqlPool.put(id, bean);
      }
      else
      {
        logger.warn("{} 对应 tempateType 值 {} 不正确，可选值为：simple和freeMarker", id, sqlType);
      }
    }
  }
  
  protected String transform(String sql)
  {
    StringBuilder sb = new StringBuilder();
    try
    {
      BufferedReader bufferedReader = 
        new BufferedReader(new StringReader(sql));
      
      String line = null;
      while ((line = bufferedReader.readLine()) != null)
      {
        sb.append(line.trim());
        sb.append(" ");
      }
      bufferedReader.close();
    }
    catch (IOException ioe)
    {
      return sql;
    }
    return sb.toString();
  }
  
  public Map<String, SQLBean> getAllSQL()
  {
    return this._sqlPool;
  }
  
  public StringTemplateLoader getStringTemplateLoader()
  {
    return this.stringTemplateLoader;
  }
  
  public Configuration getConfiguration()
  {
    return this.configuration;
  }
  
  public static class SQLBean
  {
    private String tempateType = "simple";
    private String sqlType = "SQL";
    private String content = "";
    private String style;
    private String view;
    private String xls;

    public String getXls()
    {
        return xls;
    }

    public void setXls(String xls)
    {
        this.xls = xls;
    }

    public String getStyle()
    {
        return style;
    }

    public void setStyle(String style)
    {
        this.style = style;
    }

    public String getView()
    {
        return view;
    }

    public void setView(String view)
    {
        this.view = view;
    }

    public String getTempateType()
    {
      return this.tempateType;
    }
    
    public void setTempateType(String tempateType)
    {
      this.tempateType = tempateType;
    }
    
    public String getSqlType()
    {
      return this.sqlType;
    }
    
    public void setSqlType(String sqlType)
    {
      this.sqlType = sqlType;
    }
    
    public String getContent()
    {
      return this.content;
    }
    
    public void setContent(String content)
    {
      this.content = content;
    }
  }
}
