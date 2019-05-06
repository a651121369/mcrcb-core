package com.untech.mcrcb.web.dao;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.untech.mcrcb.web.model.WjwPaymain;
import com.untech.mcrcb.web.enhance.BaseDao;
import com.unteck.common.dao.jdbc.NamedParameterJdbcPager;
import com.unteck.common.dao.support.Pagination;

/**
 * WJW_PAYMAIN DAO
 * @author            chenyong
 * @since             2015-11-04
 */
@Repository
public class WjwPaymainDao
  extends BaseDao<WjwPaymain, Long>
{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcPager jdbcPager;
	public Pagination<Map<String,Object>> getWjwPayMainList(Integer start, Integer limit) {
	  
  	  String sql = "select id as \"id\",unit_name as \"unitName\", unit_no as \"unitNo\",pay_type as \"payType\",sq_time as \"sqTime\",status as \"status\",dsp_userno as \"dspUserno\","
  	  		+ "cs_userno as \"csUserno\", fs_userno as \"fsUserno\",cs_time as \"csTime\",fs_time as \"fsTime\",conn_no as \"connNo\",dsp_username as \"dspUsername\","
  	  		+ "cs_username as \"csUsername\",fs_username as \"fsUsername\",sq_userno as \"sqUserno\",sq_username as \"sqUsername\",remark as \"remark\",note1 as \"note1\","
  	  		+ "note2 as \"note2\"  from WJW_PAYMAIN  where status=1 or status=2";
  	  	Pagination<Map<String,Object>> list = jdbcPager.queryPage(sql, start, limit);
		return list;
	}
	public List<Map<String, Object>> getUsers(String status,String unitId) {
		System.out.println("status:"+status+"    "+"unitId:"+unitId);
		String sql = "";
		if("1".equals(status)){
			
			sql = "select c.user_code as \"userId\", c.user_name as \"userName\" from unteck_role a left join unteck_user_role b on a.id = b.role_id left join unteck_user c "
					+ " on b.user_id = c.id where a.code='ROLE_CHECK' and c.del_flag=0 ";
			
		}else if("2".equals(status)){
			
			sql = "select c.user_code as \"userId\", c.user_name as \"userName\" from unteck_role a left join unteck_user_role b on a.id = b.role_id left join unteck_user c "
					+ " on b.user_id = c.id where a.code='ROLE_RECHECK' and c.del_flag=0 ";
			
		}
		return jdbcTemplate.queryForList(sql);
	}
	
	public void insertPayMain(WjwPaymain paymain){
		String sql = "insert into WJW_PAYMAIN (CONN_NO, DSP_USERNAME, DSP_USERNO, SQ_TIME,"
				+ " PAY_TYPE, REMARK, SQ_USERNAME, SQ_USERNO, STATUS, UNIT_NAME, UNIT_NO) "
				+ "values('"+paymain.getConnNo()+"','"+paymain.getDspUsername()+"','"+paymain.getDspUserno()+"','"
				+paymain.getSqTime()+ "','"+paymain.getPayType()+ "','"+paymain.getRemark()+"','"+paymain.getSqUsername()+"','"
				+paymain.getSqUserno()+ "',"+paymain.getStatus()+",'"+paymain.getUnitName()+"','"+paymain.getUnitNo()+"')";
		jdbcTemplate.execute(sql);
	}
	
	public List<Map<String, Object>> getUserName(String  userCode ) {
		String sql = "SELECT user_code as \"userCode\",user_name  as \"userName\" " +
				" FROM UNTECK_USER where  user_code='"+userCode+"' ";
		logger.info("sql:"+sql);
		return this.jdbcTemplate.queryForList(sql);
	}
}

