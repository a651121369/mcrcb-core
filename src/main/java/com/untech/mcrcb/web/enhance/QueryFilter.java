package com.untech.mcrcb.web.enhance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Q_paramname_datatype_comparetype
 * 
 * <pre>
 *  <p>datatype:</p>
 *  S  - String
 *  L  - Long
 *  N  - Integer
 *  BD - BigDecimal
 *  DB - Double
 *  BL - Boolean 
 *  FL - Float
 *  SN - Short
 *  D  - 时间
 *  DL - 日期小于
 *  DG - 日期大于
 * 
 * <p>comparetype</p>
 *  LT - 小于
 *  GT - 大于
 *  LE - 小于等于
 *  GE - 大于等于
 *  LK - %条件%
 *  LFK - %条件
 *  RHK - 条件%
 *  NULL - 为null
 *  NOTNULL - 不为null
 *  EMP - 为空
 *  NOTEMP - 不为空
 *  IN - 在[...]之中
 *  NOTIN - 不在[...]之中 
 *  NEQ 不等于
 *  * 等于
 * </pre>
 * 
 * @author lxt
 * @version 1.0
 * @since 2012-12-24
 */
public final class QueryFilter
{
    protected final Logger                logger      = LoggerFactory.getLogger(QueryFilter.class);

    private Set<String>                   aliasSet;

    private List<CriteriaCommand>         commands    = new ArrayList<CriteriaCommand>();

    private Map<String, FieldCommandImpl> logicMap    = new HashMap<String, FieldCommandImpl>();

    Map<String, Object>                   paramObjMap = new HashMap<String, Object>();

    Map<String, Object>                   conditions  = new HashMap<String, Object>();

    private Integer                       start       = 0;

    private Integer                       limit       = 100;
    
    private HttpServletRequest req;
    
    private HttpSession sess;

    public HttpServletRequest getReq()
    {
        return req;
    }

    public HttpSession getSess()
    {
        return sess;
    }

    /**
     * 
     * Description of this constructor
     * 
     * @author Administrator
     * @since 2012-12-25
     */
    public QueryFilter()
    {
    }

    public QueryFilter(HttpServletRequest request)
    {
        this.req = request;
        this.sess = request.getSession();
        Enumeration paramEnu = request.getParameterNames();
        while (paramEnu.hasMoreElements())
        {
            String paramName = (String) paramEnu.nextElement();

            String paramValue = request.getParameter(paramName);

            if (paramName.startsWith("Q_") || paramName.startsWith("ORS_") || paramName.startsWith("OR_")
                    || paramName.startsWith("SQL"))
            {
                addFilter(paramName, paramValue);
            } else {
                paramObjMap.put(paramName, paramValue);
            }
        }

        String s_start = request.getParameter("start");
        String s_limit = request.getParameter("limit");
        if (StringUtils.isNotEmpty(s_start))
        {
            start = new Integer(s_start);
        }

        if (StringUtils.isNotEmpty(s_limit))
        {
            limit = new Integer(s_limit);
        }

        String sort = request.getParameter("sort");
        String dir = request.getParameter("dir");

        if ((StringUtils.isNotEmpty(sort)) && (StringUtils.isNotEmpty(dir)))
        {
            addSorted(sort, dir);
        }
    }

    public <T> T getParamObj(String key)
    {
        return (T) paramObjMap.get(key);
    }

    public void setParamObj(String key, Object val)
    {
        paramObjMap.put(key, val);
    }

    public void addFilter(String paramName, Object paramValue)
    {
        FieldCommandImpl fieldCommand;
        String[] fieldInfo = paramName.split("[_]");

         if (fieldInfo.length < 3 || fieldInfo.length > 5)
        {
            logger.error("", "Query param name [" + paramName + "] is not right format.");
            return;
        }

        if (paramValue instanceof String)
        {
            if (fieldInfo.length == 3)
                paramValue = convertObject(fieldInfo[2], (String) paramValue);
            else 
                paramValue = convertObject(fieldInfo[0], fieldInfo[2], fieldInfo[3], (String) paramValue);
        }
        if (paramValue == null)
        {
            return;
        }

        if (fieldInfo[0].equalsIgnoreCase("SQL"))
        {
            conditions.put(paramName, paramValue);
            return;
        }
        else
        {

            if ((fieldInfo != null) && (fieldInfo.length == 5))
            {
                if (paramValue != null)
                {
                    FieldCommandImpl command = logicMap.get(fieldInfo[4]);
                    if (command == null)
                    {
                        fieldCommand = new FieldCommandImpl(fieldInfo[1], paramValue, fieldInfo[3], this);
                        commands.add(fieldCommand);
                        logicMap.put(fieldInfo[4], fieldCommand);
                    }
                    else
                    {
                        if (fieldInfo[0].contains("OR"))
                        {
                            command.addOrCriteria(fieldInfo[2], fieldInfo[1], paramValue);
                        }
                        else
                        {
                            command.addAndCriteria(fieldInfo[2], fieldInfo[1], paramValue);
                        }
                    }
                }
            }
            else
                if ((fieldInfo != null) && (fieldInfo.length == 4))
                {
                    fieldCommand = new FieldCommandImpl(fieldInfo[1], paramValue, fieldInfo[3], this);
                    this.commands.add(fieldCommand);
                }
                else
                    if ((fieldInfo != null) && (fieldInfo.length == 3))
                    {
                        fieldCommand = new FieldCommandImpl(fieldInfo[1], paramValue, fieldInfo[2], this);
                        this.commands.add(fieldCommand);
                    }

            paramObjMap.put(paramName, paramValue);
        }
    }

    public void addSorted(String orderBy, String ascDesc)
    {
        this.commands.add(new SortCommandImpl(orderBy, ascDesc, this));
    }

    public List<CriteriaCommand> getCommands()
    {
        return this.commands;
    }

    public Set<String> getAliasSet()
    {
        if (aliasSet == null)
        {
            aliasSet = new HashSet<String>();
        }
        return this.aliasSet;
    }

    private Object convertObject(String q, String type, String operation, String paramValue)
    {
        if (StringUtils.isBlank(paramValue))
        {
            return null;
        }

        if (StringUtils.equals("IN", operation) || StringUtils.equals("NOTIN", operation))
        {
            String[] values = paramValue.split("[,]");
            Object[] vals = new Object[values.length];

            for (int i = 0; i < values.length; i++)
            {
                vals[i] = convertObject(type, values[i]);
            }
            
            if (q.equals("SQL")) {
                StringBuffer sb = new StringBuffer();
                for(int i = 0; i < vals.length;i++){
                	Object v = vals[i];
                    if (v instanceof String) {
                        if (i ==0) {
                            sb.append("'");
                        } else
                    	{
                    		sb.append("','");
                    	}
                    	    
                    	sb.append(v);
                    	if (i== vals.length - 1) {
                            sb.append("'");
                        }
                    } else {
                    	if (i != 0)
                    		sb.append(",");
                    	sb.append(v);
                    }
                }
                return sb.toString();
            } else {
                return vals;
            }
        }
        else
            if (q.endsWith("ORS"))
            {
                List<Object> objs = new ArrayList<Object>();
                String[] values = paramValue.split("[,]");

                for (String value : values)
                {
                    objs.add(convertObject(type, value));
                }
                return objs;
            }
            else
            {
                return convertObject(type, paramValue);
            }
    }

    private Object convertObject(Object type, String paramValue)
    {
        Object value = null;
        try
        {
            if ("S".equals(type))
            {
                value = paramValue;
            }
            else
                if ("L".equals(type))
                {
                    value = new Long(paramValue);
                }
                else
                    if ("N".equals(type))
                    {
                        value = new Integer(paramValue);
                    }
                    else
                        if ("BD".equals(type))
                        {
                            value = new BigDecimal(paramValue);
                        }
                        else
                            if ("DB".equals(type))
                            {
                                value = new Double(paramValue);
                            }
                            else
                                if ("BL".equals(type))
                                {
                                    value = new Boolean(paramValue);
                                }
                                else
                                    if ("FT".equals(type))
                                    {
                                        value = new Float(paramValue);
                                    }
                                    else
                                        if ("SN".equals(type))
                                        {
                                            value = new Short(paramValue);
                                        }
                                        else
                                            if ("D".equals(type))
                                            {
                                                value = DateUtils.parseDate(paramValue, new String[] { "yyyy-MM-dd",
                                                        "yyyy-MM-dd HH:mm:ss","yyyyMMdd" });
                                            }
                                            else
                                                if ("DL".equals(type))
                                                {
                                                    Calendar cal = Calendar.getInstance();
                                                    cal.setTime(DateUtils.parseDate(paramValue,
                                                            new String[] { "yyyy-MM-dd","yyyyMMdd" }));
                                                    value = setStartDay(cal).getTime();
                                                }
                                                else
                                                    if ("DG".equals(type))
                                                    {
                                                        Calendar cal = Calendar.getInstance();
                                                        cal.setTime(DateUtils.parseDate(paramValue,
                                                                new String[] { "yyyy-MM-dd","yyyyMMdd" }));
                                                        value = setEndDay(cal).getTime();
                                                    }
                                                    else
                                                    {
                                                        value = paramValue;
                                                    }
        }
        catch (Exception ex)
        {
            logger.error("", ex);
        }
        return value;
    }

    public Integer getStart()
    {
        return start;
    }

    public void setStart(Integer start)
    {
        this.start = start;
    }

    public Integer getLimit()
    {
        return limit;
    }

    public void setLimit(Integer limit)
    {
        this.limit = limit;
    }

    public DetachedCriteria getCountDetachedCriteria(Class clazz)
    {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz);

        for (int i = 0; i < getCommands().size(); ++i)
        {
            CriteriaCommand cc = getCommands().get(i);
            if (cc instanceof FieldCommandImpl)
                criteria = cc.execute(criteria);
        }
        return criteria;
    }

    public DetachedCriteria getDetachedCriteria(Class clazz)
    {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz);

        for (int i = 0; i < getCommands().size(); ++i)
        {
            CriteriaCommand cc = getCommands().get(i);
            criteria = cc.execute(criteria);
        }
        return criteria;
    }

    private Calendar setStartDay(Calendar cal)
    {
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        return cal;
    }

    private Calendar setEndDay(Calendar cal)
    {
        cal.set(11, 23);
        cal.set(12, 59);
        cal.set(13, 59);
        return cal;
    }

    public StringBuffer getConditions()
    {
        StringBuffer condition = new StringBuffer();
        Iterator<Entry<String, Object>> iter = conditions.entrySet().iterator();
        while (iter.hasNext())
        {
            Entry<String, Object> entry = iter.next();
            render(entry,condition);
        }

        return condition;
    }

    private void render(Entry<String, Object> entry,StringBuffer sql)
    {
        String[] fieldInfo = entry.getKey().split("[_]");
        
        if (fieldInfo.length == 3) {
            return;
        }
        if (sql.length() != 0) {
        	sql.append(" and ");
        }
        sql.append(" ").append(fieldInfo[1].replace("$", "_"));
        if ("LT".equals(fieldInfo[3]))
        {
            sql.append(" <:");
        }
        else
            if ("GT".equals(fieldInfo[3]))
            {
                sql.append(" >:");
            }
            else
                if ("LE".equals(fieldInfo[3]))
                {
                    sql.append(" <=:");
                }
                else
                    if ("GE".equals(fieldInfo[3]))
                    {
                        sql.append(" >=:");
                    }
                    else
                        if ("LK".equals(fieldInfo[3]))
                        {
                            sql.append(" like :");
                            if(!((String)entry.getValue()).contains("%"))
                            entry.setValue("%"+entry.getValue() + "%");
                        }
                        else
                            if ("LFK".equals(fieldInfo[3]))
                            {
                                sql.append(" like :");
                                if(!((String)entry.getValue()).contains("%"))
                                entry.setValue(entry.getValue() + "%");
                            }
                            else
                                if ("RHK".equals(fieldInfo[3]))
                                {
                                    sql.append(" like :");
                                    if(!((String)entry.getValue()).contains("%"))
                                        entry.setValue( "%"+entry.getValue());
                                }
                                else
                                    if ("NULL".equals(fieldInfo[3]))
                                    {
                                        sql.append(" is null");
                                    }
                                    else
                                        if ("NOTNULL".equals(fieldInfo[3]))
                                        {
                                            sql.append(" not null");
                                        }
                                        else
                                            if ("EMP".equals(fieldInfo[3]))
                                            {
                                                sql.append(" is empty");
                                            }
                                            else
                                                if ("NOTEMP".equals(fieldInfo[3]))
                                                {
                                                    sql.append(" not empty");
                                                }
                                                else
                                                    if ("IN".equals(fieldInfo[3]))
                                                    {
                                                        sql.append(" in (");
                                                    }
                                                    else
                                                        if ("NOTIN".equals(fieldInfo[3]))
                                                        {
                                                            sql.append(" not in (");
                                                        }
                                                            else
                                                                if ("NEQ".equals(fieldInfo[3]))
                                                                {
                                                                    sql.append(" != :");
                                                                }
                                                                else
                                                                {
                                                                    sql.append(" =:");
                                                                }
        if ("IN".equals(fieldInfo[3]) || "NOTIN".equals(fieldInfo[3])){
            sql.append(entry.getValue());
            sql.append(") ");
        } else {
            sql.append(entry.getKey());
            sql.append(" ");
        }
    }

    public Map<String, Object> getSqlParams()
    {
        return conditions;
    }
}