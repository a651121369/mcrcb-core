package com.untech.mcrcb.web.dao;



import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.untech.mcrcb.web.model.WjwPayeracct;
import com.untech.mcrcb.web.enhance.BaseDao;
import com.unteck.common.dao.jdbc.NamedParameterJdbcPager;
import com.unteck.common.dao.support.Pagination;

/**
 * WJW_PAYERACCT DAO
 * @author            liuyong
 * @since             2015-11-16
 */
@Repository
public class WjwPayeracctDao
  extends BaseDao<WjwPayeracct, Long>
{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcPager jdbcPager;
	
	public Pagination<Map<String,Object>> getPayeracct(Integer start, Integer limit,String unitNo,
  		  String acctNo,String acctName,String acctBank) {
	  	  String sql = "select id,acct_no as \"acctNo\",acct_name as \"acctName\","
	  	  		+"acct_bank as \"acctBank\" ,note1,note2 from WJW_PAYERACCT where UNIT_NO='"+unitNo+"' ";
	  	  if(StringUtils.isNotBlank(acctNo)){
	  		  sql += " and acct_no like '%"+acctNo.trim()+"%'";
	  	  }
	  	  if(StringUtils.isNotBlank(acctName)){
	  		  sql += " and acct_name like '%"+acctName.trim()+"%'";
	  	  }
	  	  if(StringUtils.isNotBlank(acctBank)){
	  		  sql += " and acct_bank like '%"+acctBank.trim()+"%'";
	  	  }
		logger.info("sql:"+sql);
	  	  Pagination<Map<String,Object>> list = jdbcPager.queryPage(sql, start, limit);
		return list;
	}
}

