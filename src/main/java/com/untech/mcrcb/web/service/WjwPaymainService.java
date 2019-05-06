package com.untech.mcrcb.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.untech.mcrcb.web.dao.WjwPaymainDao;
import com.untech.mcrcb.web.model.WjwPaymain;
import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.enhance.BaseService;
import com.unteck.common.dao.support.Pagination;

/**
 * WJW_PAYMAIN Service
 *
 * @author            chenyong
 * @since             2015-11-04
 */

@Service
public class WjwPaymainService
      extends BaseService<WjwPaymain, Long>
    {
      @Autowired
      private WjwPaymainDao dao;
      
      public BaseDao<WjwPaymain, Long> getHibernateBaseDao()
      {
        return this.dao;
      }
      
      public Pagination<Map<String,Object>> getWjwPayMainList(Integer start, Integer limit){
    	  
    	  return dao.getWjwPayMainList(start,limit);
      }

	public List<Map<String, Object>> getUsers(String status,String unitId) {

		return dao.getUsers(status,unitId);
	}
	
	@Transactional
    public void insertPayMain(WjwPaymain paymain){
  	  dao.insertPayMain(paymain);
    }
	
	public List<Map<String, Object>> getUserName(String  userCode ){
		return dao.getUserName(userCode);
	}
}

