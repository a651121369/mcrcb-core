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
public class WjwAccSumInfoDao {
	
	@Autowired
	private NamedParameterJdbcPager jdbcPager;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Pagination<Map<String, Object>> getWjwAccSumInfo(Integer start, Integer limit,   
			String unitNo,String accCode) {
				
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
		sql+=",sum(amount) as \"AMOUNT\" ,"
				+ "sum(case when in_or_out=1 then amount else 0 end) as \"SAMOUNT\" ,"
				+ "sum(case when in_or_out=2 then amount else 0 end) as \"ZAMOUNT\" "
				+ "from WJW_ACCOUNT where 1=1 and ACC_NO like '%9999' and ACC_FLD =2  ";
		if (StringUtils.isNotBlank(accCode)){
			sql += " AND ACCOUNT_SYS_CODE='"+accCode+"'";
		}
		sql += " union";
		sql += " select unit_no as \"UNIT_NO\",unit_name as \"UNIT_NAME\",sum(amount) as \"AMOUNT\" ,";
		sql += "sum(case when in_or_out=1 then amount else 0 end) as \"SAMOUNT\" ,";
		sql += "sum(case when in_or_out=2 then amount else 0 end) as \"ZAMOUNT\" ";
		sql += "from WJW_ACCOUNT where 1=1 and ACC_NO not like '%9999' and ACC_FLD =2 ";
		if (StringUtils.isNotBlank(accCode)){
			sql += " AND ACCOUNT_SYS_CODE='"+accCode+"'";
		}
		sql += " group by unit_no,unit_name";
		sql += ")a";	
		if(StringUtils.isNotBlank(unitNo)) {
			sql += " where UNIT_NO = '"+unitNo.trim()+"' ";
		}		
		System.out.println("sql"+sql);
		System.out.println("start="+start+"limit"+limit);
		return jdbcPager.queryPage(sql, start, limit);

	}	
	
	public List<Map<String,Object>> getWjwAccSumInfoDownMx(String UNIT_NO){
		String sql = 
				"select unit_no as \"UNIT_NO\",unit_name as \"UNIT_NAME\",sum(amount) as \"AMOUNT\" ,"
				+ "sum(case when in_or_out=1 then amount else 0 end) as \"SAMOUNT\" ,"
				+ "sum(case when in_or_out=2 then amount else 0 end) as \"ZAMOUNT\" "
				+ "from WJW_ACCOUNT where 1=1 and ACC_NO not like '%9999' and ACC_FLD =2 ";
		sql += " group by unit_no,unit_name";
		sql += " union";
		sql += " select '3400001' as \"UNIT_NO\",'待清分账户' as \"UNIT_NAME\",sum(amount) as \"AMOUNT\" ,";
		sql += "sum(case when in_or_out=1 then amount else 0 end) as \"SAMOUNT\" ,";
		sql += "sum(case when in_or_out=2 then amount else 0 end) as \"ZAMOUNT\" ";
		sql += "from WJW_ACCOUNT where 1=1 and ACC_NO like '%9999' and ACC_FLD =2";
//		if(StringUtils.isNotBlank(UNIT_NO)) {
//			sql += " and UNIT_NO = '"+UNIT_NO.trim()+"' ";
//		}
//		if(StringUtils.isNotBlank(UNIT_NAME)) {
//			sql += " and UNIT_NAME like '%"+UNIT_NAME.trim()+"%' ";
//		}
//		sql += " group by unit_no,unit_name";
		System.out.println("sql"+sql);
		return this.jdbcTemplate.queryForList(sql);
		//return pageBeanUtil.ConvertToNeedData(sql);
	}

}
