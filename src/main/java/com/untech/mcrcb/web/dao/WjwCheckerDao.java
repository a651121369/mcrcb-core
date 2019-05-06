package com.untech.mcrcb.web.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.untech.mcrcb.web.model.WjwChecker;

import com.untech.mcrcb.web.enhance.BaseDao;
import com.unteck.common.dao.jdbc.NamedParameterJdbcPager;
import com.unteck.common.dao.support.Pagination;

/**
 * WJW_CHECKER DAO
 * 
 * @author chenyong
 * @since 2015-11-04
 */
@Repository
public class WjwCheckerDao extends BaseDao<WjwChecker, Long> {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcPager jdbcPager;

	public List<Map<String, Object>> getRoleUser(String role_id) {
		String sql = "select user_code as \"userCode\",user_name as \"userName\" from unteck_user where id in (" +
				"select user_id  from UNTECK_USER_ROLE where role_id in (" +
				"select id from unteck_role where code='"+role_id+"'))";
		logger.info("sql:"+sql);
		return this.jdbcTemplate.queryForList(sql);
	}

	public List<Map<String, Object>> getRoleUser(String roleId,String roleIdByYyzz) {
		String sql = "select user_code as \"userCode\",user_name as \"userName\" from unteck_user where id in (" +
				"select user_id  from UNTECK_USER_ROLE where role_id in (" +
				"select id from unteck_role where code='"+roleId+"' or CODE = '"+roleIdByYyzz+"'))";
		logger.info("sql:"+sql);
		return this.jdbcTemplate.queryForList(sql);
	}
	
	public Pagination<Map<String, Object>> pager(Integer start, Integer limit,String user_id,String unit_id) {
		String sql = "select  a.id as \"id\",a.user_id as \"userId\",a.unit_id as \"unitId\"," +
				"a.note1 as \"note1\",a.note2 as \"note2\",b.name as \"unitName\"," +
				"c.user_name as \"userName\" from wjw_checker a left join UNTECK_ORGANIZATION b on a.unit_id = b.id " +
				"left join UNTECK_USER c on a.user_id=c.user_code where 1=1 ";
		if(!"".equals(user_id) && !"null".equals(user_id) && user_id != null) {
			sql = sql + " and a.user_id='"+user_id+"'";
		}
		if(!"".equals(unit_id)  && !"null".equals(unit_id) && unit_id != null){
			sql = sql + " and a.unit_id='"+unit_id+"'";
		}
		logger.info("sql:"+sql);
		return jdbcPager.queryPage(sql, start, limit);
	}
	
	public List<Map<String, Object>> check(String unitId) {
		String sql = "select * from WJW_CHECKER where unit_id='"+unitId+"'";
		logger.info("sql:"+sql);
		return this.jdbcTemplate.queryForList(sql);
	}
	
}
