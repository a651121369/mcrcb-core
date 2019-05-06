package com.untech.mcrcb.web.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.untech.mcrcb.web.dao.WjwPayeracctDao;
import com.untech.mcrcb.web.model.WjwPayeracct;
import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.enhance.BaseService;
import com.unteck.common.dao.support.Pagination;

/**
 * WJW_PAYERACCT Service
 *
 * @author            liuyong
 * @since             2015-11-16
 */

@Service
public class WjwPayeracctService
      extends BaseService<WjwPayeracct, Long>
    {
      @Autowired
      private WjwPayeracctDao dao;
      @Autowired
      private JdbcTemplate jdbcTemplate;
      public BaseDao<WjwPayeracct, Long> getHibernateBaseDao()
      {
        return this.dao;
      }
      
      public Pagination<Map<String,Object>> getPayeracct(Integer start, Integer limit,String unitNo,
    		  String acctNo,String acctName,String acctBank){
    	  return dao.getPayeracct(start,limit,unitNo,acctNo,acctName,acctBank);
      }
	public void  insert(WjwPayeracct entity) {
		String sql = "insert into wjw_payeracct (acct_no,acct_name,acct_bank,NOTE1,NOTE2,UNIT_NO) values("
				+ "'"+entity.getAcctNo()+"','"+entity.getAcctName()+"','"+entity.getAcctBank()+"','"+entity.getNote1()
				+"','"+entity.getNote2()+"','"+entity.getUnitNo()+"')";
		jdbcTemplate.execute(sql);
	}
}

