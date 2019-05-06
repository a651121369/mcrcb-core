package com.untech.mcrcb.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.untech.mcrcb.web.dao.WjwCheckerDao;
import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.enhance.BaseService;
import com.untech.mcrcb.web.model.WjwChecker;
import com.unteck.common.dao.support.Pagination;

/**
 * WJW_CHECKER Service
 *
 * @author            chenyong
 * @since             2015-11-04
 */

@Service
public class WjwCheckerService
      extends BaseService<WjwChecker, Long>
    {
      @Autowired
      private WjwCheckerDao dao;
      
      public BaseDao<WjwChecker, Long> getHibernateBaseDao()
      {
        return this.dao;
      }

        public List<Map<String, Object>> getRoleUser(String roleId){
            return dao.getRoleUser(roleId);
        }
        public List<Map<String, Object>> getRoleUser(String roleId,String roleIdByYyzz){
            return dao.getRoleUser(roleId,roleIdByYyzz);
        }
      
      public Pagination<Map<String, Object>> pager(Integer start, Integer limit,String user_id,String unit_id) {
    	  return dao.pager(start,limit,user_id, unit_id);
    			  
      }
      
      public List<Map<String, Object>> check(String unitId) {
    	  return dao.check(unitId);
      }
}

