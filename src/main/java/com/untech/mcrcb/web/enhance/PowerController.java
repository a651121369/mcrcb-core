package com.untech.mcrcb.web.enhance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.untech.mcrcb.web.util.Utils;
import com.unteck.common.dao.jdbc.NamedParameterJdbcPager;
import com.unteck.common.dao.support.CustomSQL;
import com.unteck.common.dao.support.CustomSQL.SQLBean;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.spring.JXLSExcelView;
import com.unteck.tpc.framework.core.util.SecurityContextUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * 
 *
 * @author            lxt
 * @version           1.0
 * @since             2014-7-11
 */
@Controller
public class PowerController
{
  @Autowired
  private InternalResourceViewResolver viewResolver;
  
  protected final Logger                logger      = LoggerFactory.getLogger(PowerController.class);
  @Autowired
  protected NamedParameterJdbcPager jdbcPager;

  @Autowired
  protected SessionFactory sessionFactory;

  protected Session getSession()
  {
      return sessionFactory.getCurrentSession();
  }
  
  @RequestMapping(value = "/jsp/**")
  public String jsp(HttpServletRequest request, HttpServletResponse response) throws Exception
  {	
	 //SysPortletFilter只拦截/home/index,这里redirect防止拦截不到;
	 if(request.getRequestURI().equals("/jsp/home/index")){
		  return "redirect:/home/index";
	  }
      String page = request.getRequestURI().toString().substring(4);
      View a = viewResolver.resolveViewName(page, Locale.getDefault());
      if (a != null)
          return page;
      response.sendError(HttpServletResponse.SC_NOT_FOUND);
      return null;
  }
  
  @RequestMapping(value = "/xls/{selectId}")
  public ModelAndView xls(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response, @PathVariable("selectId") String selectId)
  {
	  response.setCharacterEncoding("UTF-8");
      SQLBean sqlBean = CustomSQL.getInstance().getSQLBean(selectId);
      int tjrq = (Integer) request.getSession().getServletContext().getAttribute("tjrq");
      QueryFilter filter = new QueryFilter(request);
      Map<String,Object> root = new HashMap<String, Object>();
      root.put("filter", filter);
      root.put("req", request);
      root.put("loginUser", SecurityContextUtil.getCurrentUser());
      root.put("tjrq", tjrq);
      root.put("tjrq_first", (tjrq/100)*100 + 1);
      root.put("currentDate", Utils.getCurrentDay());
      root.put("firstDayOfCurrentMonth", Utils.getFirstDayOfCurrentMonth());
      
      Enumeration<String> enumeration = request.getParameterNames();
      while (enumeration.hasMoreElements())
      {
          String paramName = (String) enumeration.nextElement();
          String paramValue = request.getParameter(paramName);
          
          paramValue = paramValue.trim();
          if (StringUtils.isNotBlank(paramValue)){
        	  try {
        		  paramValue = URLDecoder.decode(paramValue,"UTF-8");
	    		} catch (UnsupportedEncodingException e1) {
	    			e1.printStackTrace();
	    		}
              root.put(paramName, paramValue);
              modelMap.put(paramName, paramValue);    
          }
      }
      
      enumeration = request.getAttributeNames();
      while(enumeration.hasMoreElements()) {
          String name = enumeration.nextElement();
          root.put(name, request.getAttribute(name));
      }
      
      enumeration = request.getSession().getAttributeNames();
      Map<String,Object> sessionAttrs = new HashMap<String, Object>();
      while(enumeration.hasMoreElements()) {
          String name = enumeration.nextElement();
          sessionAttrs.put(name, request.getSession().getAttribute(name));
      }
      root.put("sess", sessionAttrs);
      
      root.put("count", false);
      String rowSQL = CustomSQL.getInstance().get(selectId, root);
      SQLQuery rowQuery = getSession().createSQLQuery(rowSQL.toString());
      rowQuery.setProperties(filter.getSqlParams());
      rowQuery.setResultTransformer(LowercaseAliasToEntityMapResultTransformer.INSTANCE);
      @SuppressWarnings("unchecked")
	List<Map<String, Object>> items = rowQuery.list();
      
      Map<String, Object> e = new HashMap<String, Object>();
      for(Map<String, Object> item:items) {
          Iterator<Entry<String, Object>> iter = item.entrySet().iterator();
          while(iter.hasNext()) {
              Entry<String, Object> entry = iter.next();
              if (entry.getValue() instanceof Clob)
              {
                  try
                  {
                      e.put(entry.getKey(), clobToString((Clob)entry.getValue()));
                  }
                  catch (Exception e1){
                      logger.error("pager2", e1);
                  }
              }
              else {
                  e.put(entry.getKey(), entry.getValue());
              }
          }
          
          item.clear();
          item.putAll(e);
          e.clear();
      }
      modelMap.addAttribute("list",items);
      String xls = sqlBean.getXls();
      if (StringUtils.isBlank(xls)) {
          throw new RuntimeException(selectId+" 没有配置报表模板(示例.xls|sample.xls)");
      }
      String[] strs = xls.split("\\|");
      String filename = null;
      
      if(StringUtils.isNotBlank(request.getParameter("fileName"))){
    	  filename = request.getParameter("fileName");
          try {
        	  filename = URLDecoder.decode(filename,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
          
      }
      if (strs.length == 2) {
          modelMap.put("ExcelExportFileName", filename!=null?filename:strs[0]);    
          modelMap.put("ExcelTemplateFileName", strs[1]); 
          modelMap.put("filename",filename!=null?filename.endsWith("xls")?filename.split(".x")[0]:filename:strs[0]);
      } else {
          modelMap.put("ExcelExportFileName", filename!=null?filename:strs[0]);    
          modelMap.put("ExcelTemplateFileName", strs[0]);   
          modelMap.put("filename",filename!=null?filename.endsWith("xls")?filename.split(".x")[0]:filename:strs[0]);
      }
      return new ModelAndView(new JXLSExcelView(),modelMap);
  }
  
  @RequestMapping(value = "/select/{selectId}")
  public String select(HttpServletRequest request,HttpServletResponse response, @PathVariable("selectId") String selectId)
  {
      SQLBean sqlBean = CustomSQL.getInstance().getSQLBean(selectId);
      int tjrq = (Integer) request.getSession().getServletContext().getAttribute("tjrq");
      QueryFilter filter = new QueryFilter(request);
      Map<String,Object> root = new HashMap<String, Object>();
      root.put("filter", filter);
      root.put("req", request);
      root.put("loginUser", SecurityContextUtil.getCurrentUser());
      root.put("tjrq", tjrq);
      root.put("tjrq_first", (tjrq/100)*100 + 1);
      root.put("currentDate", Utils.getCurrentDay());
      root.put("firstDayOfCurrentMonth", Utils.getFirstDayOfCurrentMonth());
      
      @SuppressWarnings("unchecked")
	Enumeration<String> enumeration = request.getParameterNames();
      while (enumeration.hasMoreElements())
      {
          String paramName = (String) enumeration.nextElement();
          String paramValue = request.getParameter(paramName);
          if (StringUtils.isNotBlank(paramValue)){
        	  paramValue = paramValue.trim();
        	  root.put(paramName, paramValue);
          }
      }
      
      enumeration = request.getAttributeNames();
      while(enumeration.hasMoreElements()) {
          String name = enumeration.nextElement();
          root.put(name, request.getAttribute(name));
      }
      
      enumeration = request.getSession().getAttributeNames();
      Map<String,Object> sessionAttrs = new HashMap<String, Object>();
      while(enumeration.hasMoreElements()) {
    	  String name = enumeration.nextElement();
    	  sessionAttrs.put(name, request.getSession().getAttribute(name));
      }
      root.put("sess", sessionAttrs);
      
      Object result = null;
      try{
          logger.warn(selectId);
          if (StringUtils.equals(sqlBean.getStyle(),"druidPager")) {
              result = druid(filter, root,selectId);
          } else if (StringUtils.equals(sqlBean.getStyle(),"countPager")) {
              result = countPager(filter, root,selectId);
          } else if (StringUtils.equals(sqlBean.getStyle(),"list")) {
              result = list(filter, root,selectId);
          } else {
              result = druid(filter,root, selectId);
          }
      }catch(RuntimeException e) {
          String sql = CustomSQL.getInstance().get(selectId,root);
          logger.error("select engine error " + selectId);
          logger.error(sql);
          throw e;
      }
      
      if (StringUtils.isNotBlank(sqlBean.getView())) {
          request.setAttribute("result", result);
          return sqlBean.getView();
      } else {
          response.setContentType("application/json; charset=utf-8");
          ObjectMapper objectMapper = new ObjectMapper();
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          objectMapper.setDateFormat(dateFormat);
          try
          {
              objectMapper.writeValue(response.getOutputStream(), result);
          }
          catch (Exception e)
          {
        	  System.out.println("错误："+e);
              logger.error("select",e);
          }
      }
      return null;
  }
  
  protected void lowercaseKey(List<Map<String, Object>> items)
  {
      Map<String, Object> e = new HashMap<String, Object>();
      for(Map<String, Object> item:items) {
          Iterator<Entry<String, Object>> iter = item.entrySet().iterator();
          while(iter.hasNext()) {
               Entry<String, Object> entry = iter.next();
               e.put(extractFieldName(entry.getKey()), entry.getValue());
          }
          
          item.clear();
          item.putAll(e);
          e.clear();
      }
  }

  private Pagination<Map<String, Object>> druid(QueryFilter filter,Map<String,Object> root,String selectId){
      String sql = CustomSQL.getInstance().get(selectId,root);
      try{
          Pagination<Map<String, Object>> page =jdbcPager.queryPageDb2(sql, filter.getStart(), filter.getLimit(), filter.getSqlParams());
          lowercaseKey(page.getResult());
          return page;
      } catch(Exception e) {
          logger.error("pager1",sql);
          logger.error("pager1",e);
      }
      return  new Pagination(0, filter.getStart(), filter.getLimit(), 0, Collections.EMPTY_LIST);
  }

  private Pagination<Map<String, Object>> countPager(QueryFilter filter,Map<String,Object> root,String selectId){
      
      root.put("count", true);
      String countSQL = CustomSQL.getInstance().get(selectId, root);
      
      root.put("count", false);
      String rowSQL = CustomSQL.getInstance().get(selectId, root);
      
      SQLQuery countQuery = getSession().createSQLQuery(countSQL.toString());
      SQLQuery rowQuery = getSession().createSQLQuery(rowSQL.toString());
      countQuery.setProperties(filter.getSqlParams());
      rowQuery.setProperties(filter.getSqlParams());
      long totalRecords = ((Number) countQuery.uniqueResult())
              .longValue();
      
      if (totalRecords != 0) {
          rowQuery.setFirstResult(filter.getStart()).setMaxResults(filter.getLimit());
          
          rowQuery.setResultTransformer(LowercaseAliasToEntityMapResultTransformer.INSTANCE);
          List<Map<String, Object>> items = rowQuery.list();
          long totalPages = (long) Math.ceil(totalRecords * 1.0D / filter.getLimit());
          
          Map<String, Object> e = new HashMap<String, Object>();
          for(Map<String, Object> item:items) {
              Iterator<Entry<String, Object>> iter = item.entrySet().iterator();
              while(iter.hasNext()) {
                  Entry<String, Object> entry = iter.next();
                  if (entry.getValue() instanceof Clob)
                  {
                      try
                      {
                          e.put(entry.getKey(), clobToString((Clob)entry.getValue()));
                      }
                      catch (Exception e1){
                          logger.error("pager2", e1);
                      }
                  }
                  else {
                      e.put(entry.getKey(), entry.getValue());
                  }
              }
              
              item.clear();
              item.putAll(e);
              e.clear();
          }
          
          return new Pagination(totalPages, filter.getStart(), filter.getLimit(), totalRecords, items);
      } else {
          return new Pagination(0, filter.getStart(), filter.getLimit(), 0, Collections.EMPTY_LIST);
      }
  }

  private List<Map<String, Object>> list(QueryFilter filter, Map<String,Object> root, String selectId)
  {
      
      
      String rowSQL = CustomSQL.getInstance().get(selectId, root);
      
      SQLQuery rowQuery = getSession().createSQLQuery(rowSQL.toString());
      rowQuery.setProperties(filter.getSqlParams());
          rowQuery.setResultTransformer(LowercaseAliasToEntityMapResultTransformer.INSTANCE);
          List<Map<String, Object>> items = rowQuery.list();
          Map<String, Object> e = new HashMap<String, Object>();
          for(Map<String, Object> item:items) {
              Iterator<Entry<String, Object>> iter = item.entrySet().iterator();
              while(iter.hasNext()) {
                   Entry<String, Object> entry = iter.next();
                   if (entry.getValue() instanceof Clob)
                   {
                       try
                       {
                           e.put(entry.getKey(), clobToString((Clob)entry.getValue()));
                       }
                       catch (Exception e1){
                           logger.error("selectMapPager2", e1);
                       }
                   }
                   else {
                       e.put(entry.getKey(), entry.getValue());
                   }
              }
              
              item.clear();
              item.putAll(e);
              e.clear();
          }
          
          return items;
  }
  
  private String clobToString(Clob clob) throws SQLException, IOException {
      String reString = "";
         Reader is = clob.getCharacterStream();// 得到流
         BufferedReader br = new BufferedReader(is);
         String s = br.readLine();
         StringBuffer sb = new StringBuffer();
         while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
             sb.append(s);
             s = br.readLine();
         }
         reString = sb.toString();
         return reString; 
     }
  
  private String extractFieldName(String columnName)
  {
      StringBuffer temp = new StringBuffer();
      String[] str = columnName.split("_");

      boolean first = true;
      for (int i = 0; i < str.length; i++)
      {
          if (str[i].equals(""))
          {
              continue;
          }
          if (first)
          {
              if (str[i].length() == 1) {
                  temp.append(str[i].toUpperCase());
              } else {
                  temp.append(str[i].toLowerCase());
              }
              first = false;
          }
          else
          {
              temp.append(str[i].substring(0, 1).toUpperCase()).append(
                      str[i].substring(1).toLowerCase());
          }
      }

      return temp.toString();
  }
}
