package com.untech.mcrcb.web.dao;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.model.WjwBfdetail;
import com.unteck.common.dao.support.Pagination;

/**
 * WJW_BFDETAIL DAO
 * @author            chenyong
 * @since             2015-11-04
 */
@Repository
public class WjwBfdetailDao extends BaseDao<WjwBfdetail, Long>{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void saveWjwBfdetail(WjwBfdetail entity){
		save(entity);
	}

	public void deleteByConnNo(String connNo) {
		String sql = "delete from WJW_BFDETAIL where CONN_NO = '"+connNo+"'";
		jdbcTemplate.update(sql);
	}

	
	public Pagination<Map<String, Object>> getBfdetailList(Integer start, Integer limit, Long id, String unitNo,
			String unitName, String connNo, String startTime, String endTime,Integer parentId,Long orgId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String sql = "select ID as \"id\",UNIT_NO as \"unitNo\",AMOUNT as \"amount\","+
				 "UNIT_NAME as \"unitName\",BF_TIME as\"bfTime\",BF_ITEM as \"bfItem\","+
				 "IN_BFQ_AMT as \"inBfqAmt\",IN_BFH_AMT as\"inBfhAmt\",OUT_BFQ_AMT as \"outBfqAmt\","+
				 "OUT_BFH_AMT as \"outBfhAmt\",CONN_NO as\"connNo\",DRUG_BF_AMT as \"drugBfAmt\","+
				 "MEDC_BF_AMT as \"medcBfAmt\",NOTE1 as\"note1\",NOTE2 as \"note2\",ZC_ACCTNAME as \"zcAcctname\","+
				"XN_ACCTNO as \"xnAcctno\",XN_ACCTNAME as\"xnAcctName\",ZC_ACCTNO as \"zcAcctno\""+
				" from WJW_BFDETAIL a where 1=1 ";
		if(id != null && id > 0){
			sql += " and ID="+id;
		}
		if(StringUtils.hasText(unitNo)) {
			sql += " and UNIT_NO = '"+unitNo+"'";
		}
		if(StringUtils.hasText(unitName)) {
			sql += " and UNIT_NAME like :unitName";
			paramMap.put("unitName", "%"+ unitName + "%");
		}
		if(StringUtils.hasText(connNo)) {
			sql += " and CONN_NO like :connNo";
			paramMap.put("connNo", "%"+ connNo + "%");
		}
		if(StringUtils.hasText(startTime)){
			sql += "  and BF_TIME >='"+startTime+"'";
		}
		if(StringUtils.hasText(endTime)){
			sql += "  and left(BF_TIME,8) <='"+endTime+"'";
		}
		if(parentId >0 ){
			sql += "  and UNIT_NO ='"+orgId+"'";
		}
		sql += "  ORDER BY BF_TIME DESC";
		return jdbcPager.queryPage(sql, start, limit,paramMap);
	}

	public List<Map<String,Object>> getBfhzbConnNo(String connNo) {
		String sql = "select UNIT_NO as \"unitNo\",UNIT_NAME as \"unitName\","
				+ "AMOUNT as \"amount\",DRUG_BF_AMT as \"drugAmt\","
				+ "MEDC_BF_AMT as \"medcAmt\",BF_TIME as \"bfTime\" "
				+ " from WJW_BFDETAIL where CONN_NO = '"+connNo+"'";
		return jdbcTemplate.queryForList(sql);
		
	}
	
	public Pagination<Map<String,Object>> getBfhzbConnNo(String connNo,Integer start,Integer limit) {
//		String sql = "SELECT\n" +
//				"	UNIT_NO AS \"unitNo\",\n" +
//				"	UNIT_NAME AS \"unitName\",\n" +
//				"	AMOUNT AS \"amount\",\n" +
//				"	DRUG_BF_AMT AS \"drugAmt\",\n" +
//				"	MEDC_BF_AMT AS \"medcAmt\",\n" +
//				"	BF_TIME AS \"bfTime\"\n" +
//				" FROM\n" +
//				"	WJW_BFDETAIL\n" +
//				" WHERE\n" +
//				"	CONN_NO = '"+connNo+"'" +
//				" UNION ALL\n" +
//				"	SELECT\n" +
//				"		UNIT_NO AS \"unitNo\",\n" +
//				"		UNIT_NAME AS \"unitName\",\n" +
//				"		BF_AMT AS \"amount\",\n" +
//				"		BF_DRUG_AMT AS \"drugAmt\",\n" +
//				"		BF_MEDC_AMT AS \"medcAmt\",\n" +
//				"		OPER_TIME AS \"bfTime\"\n" +
//				"	FROM\n" +
//				"		WJW_BFHZB\n" +
//				"	WHERE\n" +
//				"		CONN_NO = '"+connNo+"'";
		String sql = "SELECT\n" +
				"	UNIT_NO AS \"unitNo\",\n" +
				"	UNIT_NAME AS \"unitName\",\n" +
				"	AMOUNT AS \"amount\",\n" +
				"	DRUG_BF_AMT AS \"drugAmt\",\n" +
				"	MEDC_BF_AMT AS \"medcAmt\",\n" +
				"	BF_TIME AS \"bfTime\"\n" +
				" FROM\n" +
				"	WJW_BFDETAIL\n" +
				" WHERE\n" +
				"	CONN_NO = '"+connNo+"'";
		logger.info("sql:"+sql);
//		return jdbcPager.queryPage(sql, start, limit,paramMap);Pagination<Map<String, Object>>
		return jdbcPager.queryPage(sql, start, limit);
		
	}
	
	public List<Map<String,Object>> downLoadAppropriate(String connNo) {
		String sql = "select UNIT_NO as \"unitNo\",UNIT_NAME as \"unitName\","
				+ "AMOUNT as \"amount\",DRUG_BF_AMT as \"drugAmt\","
				+ "MEDC_BF_AMT as \"medcAmt\",BF_TIME as \"bfTime\" "
				+ " from WJW_BFDETAIL where CONN_NO = '"+connNo+"'"
				+"union all  select UNIT_NO as \"unitNo\",UNIT_NAME as \"unitName\","
				+ "BF_AMT as \"amount\",BF_DRUG_AMT as \"drugAmt\","
				+ "BF_MEDC_AMT as \"medcAmt\",OPER_TIME as \"bfTime\" "
				+ " from WJW_BFHZB where CONN_NO = '"+connNo+"'";
		

		logger.info("sql:"+sql);

		return jdbcTemplate.queryForList(sql);
		
	}

}

