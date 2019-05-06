package com.untech.mcrcb.web.dao;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.model.WjwAccchange;

/**
 * WJW_ACCCHANGE DAO
 * @author            chenyong
 * @since             2015-11-04
 */
@Repository
public class MxQueryDao
  extends BaseDao<WjwAccchange, Long>
{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Map<String, Object>> fetchPager(String beginTime,String endTime,String accNo) {
		String sql = "SELECT ID  as \"id\",UNIT_NO as \"unitNo\",  UNIT_NAME as \"unitName\", ACC_NO as \"accNo\", " +
				"ACC_NAME as \"accName\", DF_ACCNO as \"dfAccno\", DF_ACCNAME as \"dfAccname\", AMOUNT as \"amount\"," +
				" DRUG_AMT as \"drugAmt\", MEDC_AMT as \"medcAmt\", TRAN_AMT as \"tranAmt\", TRAN_TIME as \"tranTime\"," +
				" IN_OR_OUT as \"inOrOut\", OTHER_AMT as \"otherAmt\", NOTE1 as \"note1\", NOTE2 as \"note2\"" +
				" FROM WJW_ACCCHANGE where ACC_NO='"+accNo+"'" ;
		if(!"".equals(beginTime)){
			sql = sql +" and TRAN_TIME >='"+beginTime+"'";
		}
		if(!"".equals(endTime)){
			sql = sql +" and TRAN_TIME <='"+endTime+"'";
		}
		//sql = sql +" order by TRAN_TIME asc,id asc ";
		sql = sql +" order by id asc ";
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql);
	}
	
	public List<Map<String, Object>>  getAcctName(String acctNo){
		String sql = "SELECT ACC_NO as \"accNo\",CUST_NAME as \"accName\" FROM" +
				"  wjw_ACCOUNT WHERE ACC_NO='"+acctNo+"'";
		logger.info("sql:"+sql);
		return this.jdbcTemplate.queryForList(sql);
	}

}

