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
public class WjwJKInfoDao {
	
	@Autowired
	private NamedParameterJdbcPager jdbcPager;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Pagination<Map<String, Object>> getWjwJKInfo(Integer start, Integer limit,
														String BEGINTIME,String ENDTIME,String UNIT_NO,int parentId,Long orgId) {
		String sql =
				" select a.UNIT_NAME as \"UNIT_NAME\",a.INC_TIME as \"INC_TIME\", "
						+ "a.AMOUNT as \"AMOUNT\",a.DRUG_AMT as \"DRUG_AMT\",  "
						+ "(a.MEDICAL_AMT+a.OTHER_AMT) as \"MEDICAL_AMT\", a.OUT_ACCNAME as \"OUT_ACCNAME\", "
						+"a.STATUS as \"STATUS\", "
						+ "a.NOTE1 as \"NOTE1\" "
						+ " from wjw_INCOMEDETAIL a where 1=1 " ;
		if(StringUtils.isNotBlank(BEGINTIME)) {
			sql += " and a.INC_TIME >= '"+ BEGINTIME+"'";
		}
		if(StringUtils.isNotBlank(ENDTIME)) {
			sql += " and a.INC_TIME <= '"+ ENDTIME+"'";
		}
		if(StringUtils.isNotBlank(UNIT_NO)) {
			sql += " and a.UNIT_NO = '"+UNIT_NO.trim()+"'";
		}
		if(parentId >0 ){
			sql += "  and UNIT_NO ='"+orgId+"'";
		}

		sql += " and a.STATUS!=4";
		sql += " ORDER BY INC_TIME DESC";
		System.out.println("sql"+sql);
		System.out.println("start="+start+"limit"+limit);
		return jdbcPager.queryPage(sql, start, limit);
		//return pageBeanUtil.ConvertToNeedData(start, limit, sql);
	}


	public Pagination<Map<String, Object>> getWjwJKInfoByAccCode(Integer start, Integer limit,
														String beginTime,String endTime,String unitNo,int parentId,Long orgId,int accCode) {
		StringBuffer dynamicSql = this.subTradePagerSql(beginTime,endTime,unitNo,parentId,orgId);
		dynamicSql.append(" and a.STATUS!=4");
		if (Constants.ACC_CODE.WSY==accCode){
			dynamicSql.append("  AND a.UNIT_NO LIKE '3400%'");
		}else if (Constants.ACC_CODE.JLY==accCode){
			dynamicSql.append("  AND a.UNIT_NO LIKE '3500%'");
		}
		dynamicSql.append("  ORDER BY a.INC_TIME DESC");
		System.out.println("sql"+dynamicSql.toString());
		System.out.println("start="+start+"limit"+limit);
		return jdbcPager.queryPage(dynamicSql.toString(), start, limit);
	}


	private StringBuffer subTradePagerSql(String beginTime,String endTime,String unitNo,int parentId,Long orgId) {
		StringBuffer dynamicSql = new StringBuffer();
		String sql = "SELECT\n" +
						"	a.UNIT_NAME AS \"UNIT_NAME\",\n" +
						"	a.INC_TIME AS \"INC_TIME\",\n" +
						"	a.AMOUNT AS \"AMOUNT\",\n" +
						"	a.DRUG_AMT AS \"DRUG_AMT\",\n" +
						"	(a.MEDICAL_AMT + a.OTHER_AMT) AS \"MEDICAL_AMT\",\n" +
						"	a.OUT_ACCNAME AS \"OUT_ACCNAME\",\n" +
						"	a. STATUS AS \"STATUS\",\n" +
						"	a.NOTE1 AS \"NOTE1\"\n" +
						"FROM\n" +
						"	wjw_INCOMEDETAIL a\n" +
						"WHERE\n" +
						"	1 = 1\n";
		dynamicSql.append(sql);
		if(StringUtils.isNotBlank(beginTime)) {
			dynamicSql.append(" and a.INC_TIME >= '"+ beginTime+"'");
		}
		if(StringUtils.isNotBlank(endTime)) {
			dynamicSql.append(" and a.INC_TIME <= '"+ endTime+"'");
		}
		if(StringUtils.isNotBlank(unitNo)) {
			dynamicSql.append(" and a.UNIT_NO = '"+unitNo.trim()+"'");
		}
		if(parentId >0 ){
			dynamicSql.append("  and UNIT_NO ='"+orgId+"'");
		}
		return dynamicSql;
	}



	public List<Map<String,Object>> getWjwJKInfoDownMx(String beginDate,String endDate,
			String UNIT_NO,int parentId,Long orgId){
//		String sql = 
//				" select a.UNIT_NAME as \"UNIT_NAME\",a.INC_TIME as \"INC_TIME\", "
//				+ "a.AMOUNT as \"AMOUNT\",a.ITEM1_AMT as \"ITEM1_AMT\",  "
//				+ "a.ITEM2_AMT as \"ITEM2_AMT\", a.OUT_ACCNAME as \"OUT_ACCNAME\", "
//				+ "a.NOTE1 as \"NOTE1\" "
//				+ " from wjw_INCOMEDETAIL a where 1=1 " ;
		String sql = 
				" select a.UNIT_NAME as \"UNIT_NAME\",a.INC_TIME as \"INC_TIME\", "
				+ "a.AMOUNT as \"AMOUNT\",a.DRUG_AMT as \"ITEM1_AMT\",  "
				+ "(a.MEDICAL_AMT+a.OTHER_AMT) as \"ITEM2_AMT\", a.OUT_ACCNAME as \"OUT_ACCNAME\", "
				+"a.STATUS as \"STATUS\", "
				+ "a.NOTE1 as \"NOTE1\" "
				+ " from wjw_INCOMEDETAIL a where 1=1 " ;
			if(StringUtils.isNotBlank(beginDate)) {
				sql += " and a.INC_TIME >= '"+ beginDate+"'";
			}
			if(StringUtils.isNotBlank(endDate)) {
				sql += " and a.INC_TIME <= '"+ endDate+"'";
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
