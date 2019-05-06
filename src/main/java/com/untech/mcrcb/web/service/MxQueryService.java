package com.untech.mcrcb.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.untech.mcrcb.web.dao.MxQueryDao;
import com.untech.mcrcb.web.dao.WjwAccchangeDao;
import com.untech.mcrcb.web.model.WjwAccchange;

import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.enhance.BaseService;

/**
 * WJW_ACCCHANGE Service
 *
 * @author            chenyong
 * @since             2015-11-04
 */

@Service
public class MxQueryService
      extends BaseService<WjwAccchange, Long>
    {
      @Autowired
      private MxQueryDao dao;
      
      public BaseDao<WjwAccchange, Long> getHibernateBaseDao()
      {
        return this.dao;
      }
      
      public List<Map<String, Object>> fetchPager(String beginTime,String endTime,String accNo) {
    	  return dao.fetchPager(beginTime, endTime, accNo);
      }
      public List<Map<String, Object>>  getAcctName(String acctNo){
    		return dao.getAcctName(acctNo);
    	}
}

