package com.untech.mcrcb.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.untech.mcrcb.web.dao.AccountOpenDao;
import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.enhance.BaseService;
import com.untech.mcrcb.web.model.WjwAccount;

/**
 * WJW_PAYMAIN Service
 *
 * @author            chenyong
 * @since             2015-11-04
 */

@Service
public class AccountOpenService
      extends BaseService<WjwAccount, Long>
    {
      @Autowired
      private AccountOpenDao dao;
      
      public BaseDao<WjwAccount, Long> getHibernateBaseDao()
      {
        return this.dao;
      }
      
      
}

