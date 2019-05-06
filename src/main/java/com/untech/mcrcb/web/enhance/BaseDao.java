//-------------------------------------------------------------------------
// 
//-------------------------------------------------------------------------

package com.untech.mcrcb.web.enhance;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.unteck.common.dao.hibernate4.HibernateBaseDaoImpl;
import com.unteck.common.dao.jdbc.NamedParameterJdbcPager;
import com.unteck.common.dao.support.Pagination;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.RootEntityResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Description of the class
 * 
 * @author lxt
 * @version 1.0
 * @since 2014-5-8
 */

public class BaseDao<T, ID extends Serializable> extends HibernateBaseDaoImpl<T, ID>
{
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    protected NamedParameterJdbcPager jdbcPager;

    @Autowired
    protected SessionFactory sessionFactory;

    protected Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }

    public T findUnique(final QueryFilter filter)
    {
        Criteria executableCriteria = filter.getDetachedCriteria(entityClass).getExecutableCriteria(getSession());
        BaseDao.this.prepareCriteria(executableCriteria);

        executableCriteria.setResultTransformer(RootEntityResultTransformer.INSTANCE);
        List<T> list = executableCriteria.setFirstResult(0).setMaxResults(1).list();

        if (list.size() != 0)
        {
            return (T) list.get(0);
        }
        return null;
    }

    public Pagination<T> findPage(final QueryFilter filter)
    {
        Criteria executableCriteria = filter.getCountDetachedCriteria(entityClass).getExecutableCriteria(getSession());
        BaseDao.this.prepareCriteria(executableCriteria);

        long totalRecords = ((Long) executableCriteria.setProjection(Projections.rowCount()).uniqueResult())
                .longValue();
        
        if (totalRecords != 0) {
            filter.getAliasSet().clear();
            executableCriteria = filter.getDetachedCriteria(entityClass).getExecutableCriteria(getSession());
            BaseDao.this.prepareCriteria(executableCriteria);

        executableCriteria.setResultTransformer(RootEntityResultTransformer.INSTANCE);
        List<T> items = executableCriteria.setFirstResult(filter.getStart()).setMaxResults(filter.getLimit()).list();

        long totalPages = (long) Math.ceil(totalRecords * 1.0D / filter.getLimit());
            return new Pagination(totalPages, filter.getStart(), filter.getLimit(), totalRecords, items);
        } else {
            return new Pagination(0, filter.getStart(), filter.getLimit(), 0, Collections.EMPTY_LIST);
        }
    }
    
    public List<T> findAll(final QueryFilter filter)
    {
        
        Criteria executableCriteria = filter.getDetachedCriteria(entityClass).getExecutableCriteria(getSession());
        BaseDao.this.prepareCriteria(executableCriteria);
        executableCriteria.setResultTransformer(RootEntityResultTransformer.INSTANCE);
        return executableCriteria.list();
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
