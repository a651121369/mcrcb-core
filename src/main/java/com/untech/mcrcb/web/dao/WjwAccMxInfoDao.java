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
public class WjwAccMxInfoDao {
	
	@Autowired
	private NamedParameterJdbcPager jdbcPager;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Pagination<Map<String, Object>> getWjwAccMxInfo(Integer start, Integer limit,   
			String UNIT_NO,String accCode) {

		String sql = 
					"select * from (";
		if (StringUtils.isBlank(accCode)){
			sql += " select '3400001' as \"UNIT_NO\",'待清分总账户（包含卫生院，医养中心）' as \"UNIT_NAME\"";
		}else{
			if (Constants.ACC_CODE.WSY_STR.equals(accCode)){
				sql += " select '3400001' as \"UNIT_NO\",'卫生院待清分账户' as \"UNIT_NAME\"";
			}else if(Constants.ACC_CODE.JLY_STR.equals(accCode)){
				sql += " select '3500001' as \"UNIT_NO\",'医养中心待清分账户' as \"UNIT_NAME\"";
			}
		}
		sql+=",sum(amount) as \"AMOUNT\",sum(case when in_or_out=1 then DRUG_AMT else 0 end) as \"DRUG_AMT_IN\" ,"
					+ "sum(case when in_or_out=1 then MEDICAL_AMT else 0 end) as \"MEDC_AMT_IN\", "
					+ "sum(case when in_or_out=2 then DRUG_AMT else 0 end) as \"DRUG_AMT_OUT\", "
					+ "sum(case when in_or_out=2 then MEDICAL_AMT else 0 end) as \"MEDC_AMT_OUT\" "
					+ "from WJW_ACCOUNT where 1=1 and ACC_NO like '%9999' ";
		if (StringUtils.isNotBlank(accCode)){
			sql += " AND ACCOUNT_SYS_CODE='"+accCode+"'";
		}
			sql += " union";
			sql += " select unit_no as \"UNIT_NO\",unit_name as \"UNIT_NAME\",sum(amount) as \"AMOUNT\" ,";
			sql += "sum(case when in_or_out=1 then DRUG_AMT else 0 end) as \"DRUG_AMT_IN\" ,";
			sql += "sum(case when in_or_out=1 then MEDICAL_AMT else 0 end) as \"MEDC_AMT_IN\", ";
			sql += "sum(case when in_or_out=2 then DRUG_AMT else 0 end) as \"DRUG_AMT_OUT\", ";
			sql += "sum(case when in_or_out=2 then MEDICAL_AMT else 0 end) as \"MEDC_AMT_OUT\" ";
			sql += "from WJW_ACCOUNT where 1=1 and ACC_NO not like '%9999' ";
		if (StringUtils.isNotBlank(accCode)){
			sql += " AND ACCOUNT_SYS_CODE='"+accCode+"'";
		}
			sql += " group by unit_no,unit_name";
			sql += ")a";
		
		if(StringUtils.isNotBlank(UNIT_NO)) {
			sql += " where UNIT_NO = '"+UNIT_NO.trim()+"' ";
		}

		System.out.println("sql"+sql);
		return jdbcPager.queryPage(sql, start, limit);
	}
	
	
	public List<Map<String,Object>> getWjwAccMxInfoDownMx(String UNIT_NO){
		String sql = 
				"select unit_no as \"UNIT_NO\",unit_name as \"UNIT_NAME\",sum(amount) as \"AMOUNT\" ,"
				+ "sum(case when in_or_out=1 then DRUG_AMT else 0 end) as \"DRUG_AMT_IN\" ,"
				+ "sum(case when in_or_out=1 then MEDICAL_AMT else 0 end) as \"MEDC_AMT_IN\", "
				+ "sum(case when in_or_out=2 then DRUG_AMT else 0 end) as \"DRUG_AMT_OUT\", "
				+ "sum(case when in_or_out=2 then MEDICAL_AMT else 0 end) as \"MEDC_AMT_OUT\" "
				+ "from WJW_ACCOUNT where 1=1 and ACC_NO not like '%9999' ";
		sql += " group by unit_no,unit_name";
		sql += " union";
		sql += " select '3400001' as \"UNIT_NO\",'待清分账户' as \"UNIT_NAME\",sum(amount) as \"AMOUNT\" ,";
		sql += "sum(case when in_or_out=1 then DRUG_AMT else 0 end) as \"DRUG_AMT_IN\" ,";
		sql += "sum(case when in_or_out=1 then MEDICAL_AMT else 0 end) as \"MEDC_AMT_IN\", ";
		sql += "sum(case when in_or_out=2 then DRUG_AMT else 0 end) as \"DRUG_AMT_OUT\", ";
		sql += "sum(case when in_or_out=2 then MEDICAL_AMT else 0 end) as \"MEDC_AMT_OUT\" ";
		sql += "from WJW_ACCOUNT where 1=1 and ACC_NO like '%9999' ";
//		if(StringUtils.isNotBlank(UNIT_NO)) {
//			sql += " and UNIT_NO = '"+UNIT_NO.trim()+"' ";
//		}
//		if(StringUtils.isNotBlank(UNIT_NAME)) {
//			sql += " and UNIT_NAME like '%"+UNIT_NAME.trim()+"%' ";
//		}
//		sql += " group by unit_no,unit_name";
		System.out.println("sql"+sql);
		return this.jdbcTemplate.queryForList(sql);
	}
}
