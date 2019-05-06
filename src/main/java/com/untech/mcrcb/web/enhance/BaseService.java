//-------------------------------------------------------------------------
// 
//-------------------------------------------------------------------------

package com.untech.mcrcb.web.enhance;

import java.io.Serializable;
import java.util.List;

import com.unteck.common.dao.service.BaseServiceImpl;
import com.unteck.common.dao.support.Pagination;

import org.springframework.transaction.annotation.Transactional;

/**
 * Description of the class
 *
 * @author lxt
 * @version 1.0
 * @since 2014-5-8
 */

public abstract class BaseService<T, ID extends Serializable> extends BaseServiceImpl<T, ID> {
    @Transactional(readOnly = true)
    public Pagination<T> findPage(QueryFilter filter) {
        return getHibernateBaseDao().findPage(filter);
    }

    @Transactional(readOnly = true)
    public List<T> findAll(QueryFilter filter) {
        return getHibernateBaseDao().findAll(filter);
    }

    @Transactional(readOnly = true)
    public T findUniqune(QueryFilter filter) {
        return getHibernateBaseDao().findUnique(filter);
    }

    public abstract BaseDao<T, ID> getHibernateBaseDao();
}

