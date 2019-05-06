//-------------------------------------------------------------------------
package com.untech.mcrcb.web.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.untech.mcrcb.web.common.Constants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.model.WjwPaydetail;
import com.unteck.common.dao.support.Pagination;

/**
 *
 * @author            fanhua
 * @version           1.0
 * @since             2015年11月5日
 */
@Repository
public class PublicDao extends BaseDao<WjwPaydetail,Long> {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Map<String, Object>> getAccCode(){
		String sql = "select * from wjw_account_sys";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		logger.info("-------------------------------" + sql );
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		for(Map<String, Object> map : list){
			Map<String, Object> map1 =  new HashMap<String, Object>();
			map1.put("CODE", map.get("ID"));
			map1.put("NAME", map.get("NAME"));
			map1.put("PARENTID", map.get("PARENTID"));
			result.add(map1);
		}
		return result;
	}

	public List<Map<String, Object>> getOrganization(String orgId){
		String sql = "select * from UNTECK_ORGANIZATION";
		sql = orgId==""?sql:sql+"";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(orgId==""?sql:sql+" where ID="+orgId);
		logger.info("-------------------------------" + sql );
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		for(Map<String, Object> map : list){
			Map<String, Object> map1 =  new HashMap<String, Object>();
			map1.put("CODE", map.get("ID"));
			map1.put("NAME", map.get("NAME"));
			map1.put("PARENTID", map.get("PARENTID"));
			result.add(map1);
		}
		 return result;
	}

	public List<Map<String, Object>> getOrganizationByAccCode(String orgId,int accCode){
		String sql = "select * from UNTECK_ORGANIZATION where 1=1";
		sql = orgId==""?sql:sql+"";
		if (Constants.ACC_CODE.WSY==accCode){
			sql +=" and id like '3400%'";
		}else if (Constants.ACC_CODE.JLY==accCode){
			sql +=" and id like '3500%'";
		}
		List<Map<String, Object>> list = jdbcTemplate.queryForList(orgId==""?sql:sql+" where ID="+orgId);
		logger.info("-------------------------------" + sql );
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		for(Map<String, Object> map : list){
			Map<String, Object> map1 =  new HashMap<String, Object>();
			map1.put("CODE", map.get("ID"));
			map1.put("NAME", map.get("NAME"));
			map1.put("PARENTID", map.get("PARENTID"));
			result.add(map1);
		}
		return result;
	}
	public List<Map<String, Object>> getInAcc(){
		String getInAccSql = "select ACCT_NO as \"inAccno\",ACCT_NAME as \"inName\","
				+ "ACCT_BANK as \"inBank\" from WJW_PAYERACCT";
		logger.info("sql:"+getInAccSql);
		return jdbcTemplate.queryForList(getInAccSql);
	}
	
	public Pagination<Map<String,Object>> findInAccInfoByInName(Integer start, Integer limit,String unitNo,String inName){
		String sql = "select ACCT_NO as \"inAccno\",ACCT_NAME as \"inName\","
				+ "ACCT_BANK as \"inBank\" from WJW_PAYERACCT where UNIT_NO='"+unitNo+"'";
		if(StringUtils.isNotBlank(inName)){
			sql += " and ACCT_NAME like '%"+inName.trim()+"%'";
		}
		logger.info("sql:"+sql);
		Pagination<Map<String,Object>> list = jdbcPager.queryPage(sql, start, limit);
		return list;
	}
	 
}

