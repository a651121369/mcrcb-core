package com.untech.mcrcb.web.dao;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.model.WjwIncomedetail;
import com.unteck.common.dao.support.Pagination;

/**
 * WJW_INCOMEDETAIL DAO
 * @author            chenyong
 * @since             2015-11-04
 */
@Repository
public class WjwIncomedetailDao extends BaseDao<WjwIncomedetail, Long>{
	@Autowired
	private JdbcTemplate jdbcTemplate;


	public Pagination<Map<String, Object>> getWjwIncomedetailList(Integer start,Integer limit,
    		String unitNo,String certNo,String startTime,String endTime,Integer parentId,Long orgId){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String sql = "select a.ID as \"id\",a.UNIT_NO as \"unitNo\",a.AMOUNT as \"amount\","+
				 "a.UNIT_NAME as \"unitName\",a.OUT_ACCNO as \"outAccno\",a.OUT_ACCNAME as \"outAccname\","+
				 "a.OUT_BANK as \"outBank\",a.IN_ACCNO as \"inAccno\",a.IN_NAME as \"inName\","+
				 "a.IN_BANK as \"inBank\",a.ITEM1 as \"item1\",a.ITEM2 as \"item2\","+
				 "a.ITEM3 as \"item3\",a.ITEM1_DW as \"item1Dw\",a.ITEM2_DW as \"item2Dw\","+
				"a.ITEM3_DW as \"item3Dw\",a.ITEM1_NUM as \"item1Num\",a.ITEM2_NUM as \"item2Num\","+
				"a.ITEM3_NUM as \"item3Num\",a.ITEM1_ST as \"item1St\",a.ITEM2_ST as \"item2St\","+
				"a.ITEM3_ST as \"item3St\",a.ITEM1_AMT as \"item1Amt\",a.ITEM2_AMT as \"item2Amt\","+
				"a.ITEM3_AMT as \"item3Amt\",a.ITEM1_CODE as \"item1Code\",a.ITEM2_CODE as \"item2Code\","+
				"a.ITEM3_CODE as \"item3Code\",a.CURRENCY as \"currency\",a.STATUS as \"status\","+
				"a.CERT_NO as \"certNo\",a.YT as \"yt\",a.INC_TIME as \"incTime\","+
				"a.OPER_NO as \"operNo\",a.OPER_NAME as \"operName\",a.FH_USER as \"fhUser\","+
				"a.FH_TIME as \"fhTime\",a.NOTE1 as \"note1\",a.NOTE2 as \"note2\""+
				" from WJW_INCOMEDETAIL a where a.status > 0";
		if(StringUtils.hasText(unitNo)) {
			sql += " and a.UNIT_NO like :unitNo";
			
			paramMap.put("unitNo", "%"+ unitNo + "%");
		}
		if(StringUtils.hasText(certNo)) {
			sql += " and a.CERT_NO like :certNo";
			paramMap.put("certNo", "%"+ certNo + "%");
		}
		if(StringUtils.hasText(startTime)){
			sql += "  and INC_TIME >='"+startTime+"'";
		}
		if(StringUtils.hasText(endTime)){
			sql += "  and left(INC_TIME,8) <='"+endTime+"'";
		}
		if(parentId >0 ){
			sql += "  and UNIT_NO ='"+orgId+"'";
		}
		sql += "  ORDER BY INC_TIME DESC";
		logger.info("sql:"+sql);
		return jdbcPager.queryPage(sql, start, limit,paramMap);
	}

	/**
	 * 获得凭证最大序列号
	 * @return
	 */
	public int getMaxCode() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String date = format.format(new Date())+"01";
		String sql = "select max(substr(CERT_NO,length(CERT_NO)-2)) from WJW_INCOMEDETAIL where CERT_NO like '"+date+"%'";
	//	int num = this.jdbcTemplate.queryForInt(sql);
		Number number = this.jdbcTemplate.queryForObject(sql,Integer.class);
		return (number != null ? number.intValue() : 0);
	}
	
	/**
	 * 根据机构号获取卫生院简称
	 * @param OId
	 * @return
	 */
	public String getWSYJianpin(Long OId){
		String sql = "select DESCN from UNTECK_ORGANIZATION WHERE ID="+OId;
		logger.info("sql:"+sql);
		return this.jdbcTemplate.queryForObject(sql,String.class);
	}

	public Map<String, Object> getWjwIncomedetailById(Integer id) {
		String sql = "select a.ID as \"id\",a.UNIT_NO as \"unitNo\",a.AMOUNT as \"amount\","+
				 "a.UNIT_NAME as \"unitName\",a.OUT_ACCNO as \"outAccno\",a.OUT_ACCNAME as \"outAccname\","+
				 "a.OUT_BANK as \"outBank\",a.IN_ACCNO as \"inAccno\",a.IN_NAME as \"inName\","+
				 "a.IN_BANK as \"inBank\",a.ITEM1 as \"item1\",a.ITEM2 as \"item2\","+
				 "a.ITEM3 as \"item3\",a.ITEM1_DW as \"item1Dw\",a.ITEM2_DW as \"item2Dw\","+
				"a.ITEM3_DW as \"item3Dw\",a.ITEM1_NUM as \"item1Num\",a.ITEM2_NUM as \"item2Num\","+
				"a.ITEM3_NUM as \"item3Num\",a.ITEM1_ST as \"item1St\",a.ITEM2_ST as \"item2St\","+
				"a.ITEM3_ST as \"item3St\",a.ITEM1_AMT as \"item1Amt\",a.ITEM2_AMT as \"item2Amt\","+
				"a.ITEM3_AMT as \"item3Amt\",a.ITEM1_CODE as \"item1Code\",a.ITEM2_CODE as \"item2Code\","+
				"a.ITEM3_CODE as \"item3Code\",a.CURRENCY as \"currency\",a.STATUS as \"status\","+
				"a.CERT_NO as \"certNo\",a.YT as \"yt\",a.INC_TIME as \"incTime\","+
				"a.OPER_NO as \"operNo\",a.OPER_NAME as \"operName\",a.FH_USER as \"fhUser\","+
				"a.FH_TIME as \"fhTime\",a.NOTE1 as \"note1\",a.NOTE2 as \"note2\""+
				" from WJW_INCOMEDETAIL a where a.ID="+id;
		logger.info("sql:"+sql);
		return this.jdbcTemplate.queryForList(sql).get(0);
	}

	public Integer getOrgParentId(Long orgId) {
		String sql = "select PARENTID from UNTECK_ORGANIZATION where ID="+orgId;
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForObject(sql,Integer.class);
	}

	public Map<String, Object> getWjwInAccount() {
		String sql = "select ACC_NO as 'accNo',CUST_NAME as 'custName',BANK_NAME as 'bankName' from WJW_ACCOUNT where in_or_out=1 and ACC_FLD=1";
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForMap(sql);
	}
	public Map<String, Object> getWjwYlyInAccount(int accCode) {
		String sql = "SELECT\n" +
				"	ACC_NO AS 'accNo',\n" +
				"	CUST_NAME AS 'custName',\n" +
				"	BANK_NAME AS 'bankName'\n" +
				"FROM\n" +
				"	WJW_ACCOUNT\n" +
				"WHERE\n" +
				" 1=1\n" +
				"AND in_or_out = 1\n" +
				"AND ACC_FLD = 1\n" +
				"AND ACCOUNT_SYS_CODE ='"+accCode+"'";
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForMap(sql);
	}

	public void deleteIncome(Long id) {
		String sql = "update WJW_INCOMEDETAIL set STATUS = 4 where ID = "+id;
		logger.info("sql:"+sql);
		jdbcTemplate.update(sql);
	}
	
	public void insertAll(WjwIncomedetail income){
		String sql = "insert into WJW_INCOMEDETAIL (OPER_NO,OPER_NAME,UNIT_NO,UNIT_NAME,"
				+ "INC_TIME,CERT_NO,MEDICAL_AMT,DRUG_AMT,"
				+ "OTHER_AMT,OUT_ACCNO,OUT_ACCNAME,OUT_BANK,"
				+ "ITEM1,ITEM1_AMT,ITEM1_CODE,ITEM1_DW,ITEM1_NUM,ITEM1_ST,"
				+ "ITEM2,ITEM2_AMT,ITEM2_CODE,ITEM2_DW,ITEM2_NUM,ITEM2_ST,"
				+ "ITEM3,ITEM3_AMT,ITEM3_CODE,ITEM3_DW,ITEM3_NUM,ITEM3_ST,"
				+ "AMOUNT,XN_ACCTNO,"
				+ "XN_ACCTNAME,ZC_ACCTNO,ZC_ACCTNAME,STATUS,"
				+ "IN_ACCNO,IN_NAME,IN_BANK) values("
				+ "'"+income.getOperNo()+"','"+income.getOperName()+"','"+income.getUnitNo()+"','"+income.getUnitName()+"',"
				+ "'"+income.getIncTime()+"','"+income.getCertNo()+"',"+income.getMedicalAmt()+","+income.getDrugAmt()+","
				+ ""+income.getOtherAmt()+",'"+income.getOutAccno()+"','"+income.getOutAccname()+"','"+income.getOutBank()+"',"
				+ "'"+income.getItem1()+"',"+income.getItem1Amt()+",'"+income.getItem1Code()+"','"+income.getItem1Dw()+"',"
				+income.getItem1Num()+",'"+income.getItem1St()+"',"
				
				+ "'"+income.getItem2()+"',"+income.getItem2Amt()+",'"+income.getItem2Code()+"','"+income.getItem2Dw()+"',"
				+income.getItem2Num()+",'"+income.getItem2St()+"',"
				
				+ "'"+income.getItem3()+"',"+income.getItem3Amt()+",'"+income.getItem3Code()+"','"+income.getItem3Dw()+"',"
				+income.getItem3Num()+",'"+income.getItem3St()+"',"
				
				
				+ ""+income.getAmount()+",'"+income.getXnAcctno()+"',"
				+ "'"+income.getXnAcctName()+"','"+income.getZcAcctno()+"','"+income.getZcAcctname()+"',"+income.getStatus()+","
				+ "'"+income.getInAccno()+"','"+income.getInName()+"','"+income.getInBank()+"')";
		logger.info("sql:"+sql);
		jdbcTemplate.execute(sql);
	}

	public Map<String, Object> queryIncomeByVoucher(
			String voucher) {
		String sql = "select OUT_ACCNO as \"OUT_ACCNO\", OUT_ACCNAME as \"OUT_ACCNAME\", OUT_BANK as \"OUT_BANK\", "
				+ "IN_ACCNO as \"IN_ACCNO\", IN_NAME as \"IN_NAME\", IN_BANK as \"IN_BANK\", UNIT_NAME as \"UNIT_NAME\","
				+ " AMOUNT as \"AMOUNT\", STATUS as \"STATUS\" ,INC_TIME as \"TIME\"  from wjw_incomedetail  "
				+ "where CERT_NO ='"+voucher+"'";
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql).size()==0?null:jdbcTemplate.queryForList(sql).get(0);
	}
}

