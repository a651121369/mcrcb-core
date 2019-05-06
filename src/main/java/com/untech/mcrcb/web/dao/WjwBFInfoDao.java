package com.untech.mcrcb.web.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.unteck.common.dao.jdbc.NamedParameterJdbcPager;
import com.unteck.common.dao.support.Pagination;

@Repository
public class WjwBFInfoDao {
	
	@Autowired
	private NamedParameterJdbcPager jdbcPager;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Pagination<Map<String, Object>> getWjwBFInfo(Integer start, Integer limit,   
			 String BEGINTIME,String ENDTIME,String UNIT_NO,int parentId,Long orgId) {
		String sql = 
				" select a.UNIT_NAME as \"UNIT_NAME\",a.BF_TIME as \"BF_TIME\", "
				+ "a.AMOUNT as \"AMOUNT\",a.BF_ITEM as \"BF_ITEM\",  "
				+ "a.IN_BFQ_AMT as \"IN_BFQ_AMT\", a.IN_BFH_AMT as \"IN_BFH_AMT\", "
				+ "a.OUT_BFQ_AMT as \"OUT_BFQ_AMT\",a.OUT_BFH_AMT as \"OUT_BFH_AMT\" "
				+ " from wjw_BFDETAIL a where 1=1 " ;
			if(StringUtils.isNotBlank(BEGINTIME)) {
				sql += " and a.BF_TIME >= '"+ BEGINTIME+"'";
			}
			if(StringUtils.isNotBlank(ENDTIME)) {
				sql += " and a.BF_TIME <= '"+ ENDTIME+"'";
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
		sql += "  ORDER BY a.BF_TIME DESC";
		System.out.println("sql"+sql);
		System.out.println("start="+start+"limit"+limit);
		return jdbcPager.queryPage(sql, start, limit);
		//return pageBeanUtil.ConvertToNeedData(start, limit, sql);
	}
	
	
	public List<Map<String,Object>> getWjwBFInfoDownMx(String beginDate,String endDate,
			String UNIT_NO,int parentId,Long orgId){
		String sql = 
				" select a.UNIT_NAME as \"UNIT_NAME\",a.BF_TIME as \"BF_TIME\", "
				+ "a.AMOUNT as \"AMOUNT\",a.BF_ITEM as \"BF_ITEM\",  "
				+ "a.IN_BFQ_AMT as \"IN_BFQ_AMT\", a.IN_BFH_AMT as \"IN_BFH_AMT\", "
				+ "a.OUT_BFQ_AMT as \"OUT_BFQ_AMT\",a.OUT_BFH_AMT as \"OUT_BFH_AMT\" "
				+ " from wjw_BFDETAIL a where 1=1 " ;
			if(StringUtils.isNotBlank(beginDate)) {
				sql += " and a.BF_TIME >= '"+ beginDate+"'";
			}
			if(StringUtils.isNotBlank(endDate)) {
				sql += " and a.BF_TIME <= '"+ endDate+"'";
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
