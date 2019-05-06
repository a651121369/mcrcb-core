package com.untech.mcrcb.web.dao;

import java.util.List;
import java.util.Map;

import com.untech.mcrcb.web.common.Constants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.unteck.common.dao.jdbc.NamedParameterJdbcPager;
import com.unteck.common.dao.support.Pagination;

@Repository
public class WjwZFInfoDao {
	
	@Autowired
	private NamedParameterJdbcPager jdbcPager;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Pagination<Map<String, Object>> getWjwZFInfo(Integer start, Integer limit,
			 String BEGINTIME,String ENDTIME,String UNIT_NO,int parentId,Long orgId) {
		String sql =
				" select a.UNIT_NAME as \"UNIT_NAME\",a.PAY_TIME as \"PAY_TIME\", "
				+ "a.IN_NAME as \"IN_NAME\",a.IN_ACCNO as \"IN_ACCNO\",a.OPER_NO as \"OPER_NO\","
				+ "a.AMOUNT as \"AMOUNT\", a.ITEM as \"ITEM\", a.STATUS as \"STATUS\","
				+ "a.YT as \"YT\",a.NOTE1 as \"NOTE1\" "
				+ " from wjw_PAYDETAIL a where 1=1 " ;
			if(StringUtils.isNotBlank(BEGINTIME)) {
				sql += " and a.PAY_TIME >= '"+ BEGINTIME+"'";
			}
			if(StringUtils.isNotBlank(ENDTIME)) {
				sql += " and a.PAY_TIME <= '"+ ENDTIME+"'";
			}
			if(StringUtils.isNotBlank(UNIT_NO)) {
				sql += " and a.UNIT_NO = '"+UNIT_NO.trim()+"'";
			}
//			if(StringUtils.isNotBlank(UNIT_NAME)) {
//				sql += " and a.UNIT_NAME like '%"+UNIT_NAME.trim()+"%'";
//			}
			if(parentId >0 ){
				sql += "  and UNIT_NO ='"+orgId+"'";
			}
		sql += "  ORDER BY a.PAY_TIME DESC";
		System.out.println("sql"+sql);
		System.out.println("start="+start+"limit"+limit);
		return jdbcPager.queryPage(sql, start, limit);
	}

	public Pagination<Map<String, Object>> getWjwZFInfoByAccCode(Integer start, Integer limit,
														String BEGINTIME,String ENDTIME,String UNIT_NO,int parentId,Long orgId,int accCode) {
		String sql =
				" select a.UNIT_NAME as \"UNIT_NAME\",a.PAY_TIME as \"PAY_TIME\", "
						+ "a.IN_NAME as \"IN_NAME\",a.IN_ACCNO as \"IN_ACCNO\",a.OPER_NO as \"OPER_NO\","
						+ "a.AMOUNT as \"AMOUNT\", a.ITEM as \"ITEM\", a.STATUS as \"STATUS\","
						+ "a.YT as \"YT\",a.NOTE1 as \"NOTE1\" "
						+ " from wjw_PAYDETAIL a where 1=1 " ;
		if(StringUtils.isNotBlank(BEGINTIME)) {
			sql += " and a.PAY_TIME >= '"+ BEGINTIME+"'";
		}
		if(StringUtils.isNotBlank(ENDTIME)) {
			sql += " and a.PAY_TIME <= '"+ ENDTIME+"'";
		}
		if(StringUtils.isNotBlank(UNIT_NO)) {
			sql += " and a.UNIT_NO = '"+UNIT_NO.trim()+"'";
		}
		if(parentId >0 ){
			sql += "  and UNIT_NO ='"+orgId+"'";
		}

		if (Constants.ACC_CODE.WSY==accCode){
			sql += "  AND a.UNIT_NO LIKE '3400%'";
		}else if (Constants.ACC_CODE.JLY==accCode){
			sql += "  AND a.UNIT_NO LIKE '3500%'";
		}
		sql += "  ORDER BY a.PAY_TIME DESC";
		System.out.println("sql"+sql);
		System.out.println("start="+start+"limit"+limit);
		return jdbcPager.queryPage(sql, start, limit);
	}




	public List<Map<String,Object>> getWjwZFInfoDownMx(String beginDate,String endDate,
			String UNIT_NO,int parentId,Long orgId){
		String sql = 
				" select a.UNIT_NAME as \"UNIT_NAME\",a.PAY_TIME as \"PAY_TIME\", "
				+ "a.IN_NAME as \"IN_NAME\",a.IN_ACCNO as \"IN_ACCNO\",  "
				+ "a.AMOUNT as \"AMOUNT\", a.ITEM as \"ITEM\", "
				+ "a.YT as \"YT\",a.NOTE1 as \"NOTE1\" "
				+ " from wjw_PAYDETAIL a where 1=1 " ;
			if(StringUtils.isNotBlank(beginDate)) {
				sql += " and a.PAY_TIME >= '"+ beginDate+"'";
			}
			if(StringUtils.isNotBlank(endDate)) {
				sql += " and a.PAY_TIME <= '"+ endDate+"'";
			}
			if(StringUtils.isNotBlank(UNIT_NO)) {
				sql += " and a.UNIT_NO = '"+UNIT_NO.trim()+"'";
			}
//			if(StringUtils.isNotBlank(UNIT_NAME)) {
//				sql += " and a.UNIT_NAME like '%"+UNIT_NAME.trim()+"%'";
//			}
			if(parentId >0 ){
				sql += "  and UNIT_NO ='"+orgId+"'";
			}
		System.out.println("sql"+sql);
		return this.jdbcTemplate.queryForList(sql);
		//return pageBeanUtil.ConvertToNeedData(sql);
	}

}
