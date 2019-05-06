package com.untech.mcrcb.web.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.model.WjwAccount;
import com.unteck.common.dao.jdbc.NamedParameterJdbcPager;

/**
 * WJW_PAYMAIN DAO
 * @author            chenyong
 * @since             2015-11-04
 */
@Repository
public class AccountOpenDao
  extends BaseDao<WjwAccount, Long>
{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcPager jdbcPager;
	
	
	
		
}

