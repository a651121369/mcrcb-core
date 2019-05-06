package com.untech.mcrcb.web.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.model.WjwAccchange;
import com.unteck.common.dao.jdbc.NamedParameterJdbcPager;
import com.unteck.common.dao.support.Pagination;

@Repository
public class WjwAccountHXDao extends BaseDao<WjwAccchange, Long>{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcPager jdbcPager;

	public Pagination<Map<String, Object>> getCertNoInfo(Integer start, Integer limit,String startTime,
			String endTime,String status,String connNo,String amount) {
		
		String sql = 
				" select * from (select a.INC_TIME as \"DATETIME\",a.CERT_NO as \"connNo\",a.UNIT_NAME as \"UNIT_NAME\",a.UNIT_NO as \"UNIT_NO\", "
				+ "a.OUT_ACCNAME as \"OUT_ACCNAME\",a.OUT_ACCNO as \"OUT_ACCNO\",  "
				+ "a.IN_NAME as \"IN_NAME\", a.IN_ACCNO as \"IN_ACCNO\", "
				+ "a.AMOUNT as \"AMOUNT\",a.DRUG_AMT as \"DRUG_AMT\", "
				+ "a.MEDICAL_AMT as \"MEDICAL_AMT\",1 as \"STATE\",a.STATUS  as \"STATUS\",a.FH_TIME  as \"FH_TIME\" "
				+ " from WJW_INCOMEDETAIL a where 1=1 " ;
		if(StringUtils.isNotBlank(connNo)) {
			sql += " and a.CERT_NO like  '%"+connNo.trim()+"%'";
		}
		if(StringUtils.isNotBlank(amount)){
			sql += " and a.AMOUNT="+amount.trim()+"";
		}
		if(StringUtils.isNotBlank(startTime)){
			sql += " and a.INC_TIME>='"+startTime+"'";
		}
		if(StringUtils.isNotBlank(endTime)){
			sql += " and a.INC_TIME<='"+endTime+"'";
		}
		if(StringUtils.isNotBlank(status)){
			sql += " and a.STATUS="+status;
		}else{
			sql += " and a.STATUS in(5,6) ";
		}
		sql += 
				"union all select b.PAY_TIME as \"DATETIME\",b.OPER_NO as \"connNo\",b.UNIT_NAME as \"UNIT_NAME\",b.UNIT_NO as \"UNIT_NO\", "
				+ "b.OUT_ACCNAME as \"OUT_ACCNAME\",b.OUT_ACCNO as \"OUT_ACCNO\",  "
				+ "b.IN_NAME as \"IN_NAME\", b.IN_ACCNO as \"IN_ACCNO\", "
				+ "b.AMOUNT as \"AMOUNT\",(case when b.ITEM=2 then b.AMOUNT else 0.00 end) as \"DRUG_AMT\", "
				+ "(case when b.ITEM=1 then b.AMOUNT else 0.00 end) as \"MEDICAL_AMT\",2 as \"STATE\",b.STATUS  as \"STATUS\",b.FH_TIME  as \"FH_TIME\" "
				+ " from wjw_PAYDETAIL b where 1=1 " ;
		if(StringUtils.isNotBlank(connNo)) {
			sql += " and b.OPER_NO like '%"+connNo.trim()+"%'";
		}
		if(StringUtils.isNotBlank(amount)){
			sql += " and b.AMOUNT="+amount.trim()+"";
		}
		if(StringUtils.isNotBlank(startTime)){
			sql += " and b.PAY_TIME>='"+startTime+"'";
		}
		if(StringUtils.isNotBlank(endTime)){
			sql += " and b.PAY_TIME<='"+endTime+"'";
		}
		if(StringUtils.isNotBlank(status)){
			sql += " and b.STATUS="+status;
		}else{
			sql += " and b.STATUS in(5,6) ";
		}
		sql += 
				"union all select c.OPER_TIME as \"DATETIME\",c.CERT_NO as \"connNo\",c.UNIT_NAME as \"UNIT_NAME\",c.UNIT_NO as \"UNIT_NO\", "
						+ "c.OUT_ACCNAME as \"OUT_ACCNAME\",c.OUT_ACCNO as \"OUT_ACCNO\",  "
						+ "c.IN_NAME as \"IN_NAME\", c.IN_ACCNO as \"IN_ACCNO\", "
						+ "c.BF_AMT as \"AMOUNT\",c.BF_DRUG_AMT as \"DRUG_AMT\", "
						+ "c.BF_MEDC_AMT as \"MEDICAL_AMT\",3 as \"STATE\",c.STATUS  as \"STATUS\",c.FH_TIME  as \"FH_TIME\" "
						+ " from wjw_BFHZB c where 1=1 " ;
		if(StringUtils.isNotBlank(connNo)) {
			sql += " and c.CERT_NO like '%"+connNo.trim()+"%'";
		}
		if(StringUtils.isNotBlank(amount)){
			sql += " and c.BF_AMT="+amount.trim()+"";
		}
		if(StringUtils.isNotBlank(startTime)){
			sql += " and c.OPER_TIME>='"+startTime+"'";
		}
		if(StringUtils.isNotBlank(endTime)){
			sql += " and c.OPER_TIME<='"+endTime+"'";
		}
		if(StringUtils.isNotBlank(status)){
			sql += " and c.STATUS="+status;
		}else{
			sql += " and c.STATUS in(5,6)";
		}
		sql += " ) t";
		logger.info("sql:"+sql);
		return jdbcPager.queryPage(sql, start, limit);
		
	}
	
	
	public void update(String dspUserno,String dspUserName,String connNo,String time){
		String updateByCertNoSql = "update wjw_INCOMEDETAIL set FH_USER = '"+dspUserno+"',STATUS = 6 ,FH_TIME = '"+time+"'";
		updateByCertNoSql += "where CERT_NO = '"+connNo+"'";
		logger.info("sql:"+updateByCertNoSql);
		jdbcTemplate.update(updateByCertNoSql);
	}
	
	public void updateIncomeDetailStatus(String voucher,String time,String userCode){
		String updateByCertNoSql = "update wjw_INCOMEDETAIL set FH_USER = '"+userCode+"',STATUS = 6 ,FH_TIME = '"+time+"'";
		updateByCertNoSql += "where CERT_NO = '"+voucher+"'";
		logger.info("sql:"+updateByCertNoSql);
		jdbcTemplate.update(updateByCertNoSql);
	}
	
	public List<Map<String,Object>> getWjwIncomedetail(String connNo){
		String selectByCertNoSql = 
				" select a.UNIT_NAME as \"UNIT_NAME\",a.UNIT_NO as \"UNIT_NO\", "
				+ "a.OUT_ACCNAME as \"OUT_ACCNAME\",a.OUT_ACCNO as \"OUT_ACCNO\",  "
				+ "a.IN_NAME as \"IN_NAME\", a.IN_ACCNO as \"IN_ACCNO\", "
				+ "a.AMOUNT as \"AMOUNT\",a.DRUG_AMT as \"DRUG_AMT\", "
				+ "a.MEDICAL_AMT as \"MEDICAL_AMT\",a.OTHER_AMT as \"OTHER_AMT\",a.ZC_ACCTNO as \"ZC_ACCTNO\",a.ZC_ACCTNAME as \"ZC_ACCTNAME\","
				+ "a.XN_ACCTNO as \"XN_ACCTNO\",a.XN_ACCTNAME as \"XN_ACCTNAME\",a.STATUS as \"STATUS\",a.FH_TIME as \"FH_TIME\" "
				+ " from wjw_INCOMEDETAIL a where  CERT_NO = '"+connNo+"'" ;
		logger.info("sql:"+selectByCertNoSql);
		return jdbcTemplate.queryForList(selectByCertNoSql);
	}
	
	public String selectStatus(String connNo){
		String selectByCertNoSql = "select STATUS from wjw_INCOMEDETAIL where CERT_NO = '"+connNo+"'";
		logger.info("sql:"+selectByCertNoSql);
		return jdbcTemplate.queryForList(selectByCertNoSql).get(0).get("STATUS").toString();
	}
	
	public List<Map<String,Object>> getMoneySql(String ZcAcctno){
		String getMoneySql = "select AMOUNT,DRUG_AMT,MEDICAL_AMT,UNIT_NO,UNIT_NAME from wjw_ACCOUNT where ACC_NO = '"+ZcAcctno+"'";
		logger.info("sql:"+getMoneySql);
		return jdbcTemplate.queryForList(getMoneySql);
	}
	
	public void updateZmoney(BigDecimal Amount,BigDecimal DrugAmt,BigDecimal MedicalAmt,String Acctno){
		String upMoneySql = "update wjw_ACCOUNT set AMOUNT = "+Amount+",DRUG_AMT = "+DrugAmt+",MEDICAL_AMT = "+MedicalAmt+" where ACC_NO = '"+Acctno+"'";
		logger.info(upMoneySql);
		jdbcTemplate.update(upMoneySql);
	}
	
	public List<Map<String,Object>> selectZFStatus(String connNo){
		String selectByCertNoSql = "select STATUS,CONN_NO from wjw_PAYDETAIL where OPER_NO = '"+connNo+"'";
		logger.info("sql:"+selectByCertNoSql);
		return jdbcTemplate.queryForList(selectByCertNoSql);
	}
	
	public void updateZFStatus(String dspUserno,String dspUserName,String connNo,String time){
		String updateByCertNoSql = "update wjw_PAYDETAIL set FH_USER = '"+dspUserno+"',STATUS = 6 ,FH_TIME = '"+time+"' ";
		updateByCertNoSql += "where OPER_NO = '"+connNo+"'";
		jdbcTemplate.update(updateByCertNoSql);
	}
	
	public void updateZFStatus(String connNo,String time,String userCode){
		String updateByCertNoSql = "update wjw_PAYDETAIL set FH_USER = '"+userCode+"',STATUS = 6 ,FH_TIME = '"+time+"' ";
		updateByCertNoSql += "where OPER_NO = '"+connNo+"'";
		logger.info("updateByCertNoSql:"+updateByCertNoSql);
		jdbcTemplate.update(updateByCertNoSql);
	}
	
	public void updateZFZBStatus(String CONN_NO,String time){
		String updateByCONN_NOSql = "update wjw_PAYMAIN set STATUS = 6 where CONN_NO = '"+CONN_NO+"'";
		logger.info("sql:"+updateByCONN_NOSql);
		jdbcTemplate.update(updateByCONN_NOSql);
	}
	
	public List<Map<String,Object>> getWjwPaydetail(String connNo){
		String selectByCertNoSql = 
						  "select b.UNIT_NAME as \"UNIT_NAME\",b.UNIT_NO as \"UNIT_NO\", "
						+ "b.OUT_ACCNAME as \"OUT_ACCNAME\",b.OUT_ACCNO as \"OUT_ACCNO\",  "
						+ "b.IN_NAME as \"IN_NAME\", b.IN_ACCNO as \"IN_ACCNO\", "
						+ "b.AMOUNT as \"AMOUNT\",(case when b.ITEM=2 then b.AMOUNT else 0.00 end) as \"DRUG_AMT\", "
						+ "(case when b.ITEM=1 then b.AMOUNT else 0.00 end) as \"MEDICAL_AMT\",1 as \"STATE\", "
						+ "b.ZC_ACCTNO as \"ZC_ACCTNO\", b.ZC_ACCTNAME as \"ZC_ACCTNAME\",b.STATUS as \"STATUS\", "
						+ "b.XN_ACCTNO as \"XN_ACCTNO\", b.XN_ACCTNAME as \"XN_ACCTNAME\",b.CONN_NO as \"CONN_NO\"  "
						+ " from wjw_PAYDETAIL b where b.OPER_NO = '"+connNo.trim()+"'" ;
		logger.info("sql"+selectByCertNoSql);
		return jdbcTemplate.queryForList(selectByCertNoSql);
	}
	
	
	public List<Map<String,Object>> selectBFStatus(String connNo){
		String selectByCertNoSql = "select STATUS,CONN_NO from wjw_BFHZB where CERT_NO = '"+connNo+"'";
		logger.info("sql:"+selectByCertNoSql);
		return jdbcTemplate.queryForList(selectByCertNoSql);
	}
	
	public void updateBFStatus(String dspUserno,String dspUserName,String connNo,String time){
		String updateByCertNoSql = "update wjw_BFHZB set FH_USER = '"+dspUserno+"',STATUS = 6 ,FH_TIME = '"+time+"' ";
		updateByCertNoSql += "where CERT_NO = '"+connNo+"'";
		logger.info("sql:"+updateByCertNoSql);
		jdbcTemplate.update(updateByCertNoSql);
	}
	
	public void updateBFStatus(String voucher,String time,String userCode){
		String updateByCertNoSql = "update wjw_BFHZB set FH_USER = '"+userCode+"',STATUS = 6 ,FH_TIME = '"+time+"' ";
		updateByCertNoSql += "where CERT_NO = '"+voucher+"'";
		logger.info("sql:"+updateByCertNoSql);
		jdbcTemplate.update(updateByCertNoSql);
	}
	
	public List<Map<String,Object>> getWjwBFHZ(String connNo){
		String selectByCertNoSql = 
				          "select c.UNIT_NAME as \"UNIT_NAME\",c.UNIT_NO as \"UNIT_NO\", "
						+ "c.OUT_ACCNAME as \"OUT_ACCNAME\",c.OUT_ACCNO as \"OUT_ACCNO\",  "
						+ "c.IN_NAME as \"IN_NAME\", c.IN_ACCNO as \"IN_ACCNO\", "
						+ "c.BF_AMT as \"AMOUNT\",c.BF_DRUG_AMT as \"DRUG_AMT\", "
						+ "c.BF_MEDC_AMT as \"MEDICAL_AMT\", c.STATUS as \"STATUS\","
						+ "c.ZC_ACCTNO as \"ZC_ACCTNO\", c.ZC_ACCTNAME as \"ZC_ACCTNAME\", "
						+ "c.XN_ACCTNO as \"XN_ACCTNO\", c.XN_ACCTNAME as \"XN_ACCTNAME\",c.CONN_NO as \"CONN_NO\" "
						+ " from wjw_BFHZB c where CERT_NO = '"+connNo+"' " ;
		logger.info("sql:"+selectByCertNoSql);
		return jdbcTemplate.queryForList(selectByCertNoSql);
	}
	
	public List<Map<String,Object>> getChildAccInfo(String CONN_NO){
		String selectByCertNoSql = "select UNIT_NO as \"UNIT_NO\",UNIT_NAME as \"UNIT_NAME\","
				+ "AMOUNT as \"AMOUNT\",BF_TIME as \"BF_TIME\","
				+ "DRUG_BF_AMT as \"DRUG_BF_AMT\",MEDC_BF_AMT as \"MEDC_BF_AMT\","
				+ "XN_ACCTNO as \"XN_ACCTNO\",XN_ACCTNAME as \"XN_ACCTNAME\","
				+ "ZC_ACCTNO as \"ZC_ACCTNO\",ZC_ACCTNAME as \"ZC_ACCTNAME\""
				+ " from wjw_BFDETAIL where CONN_NO = '"+CONN_NO+"'";
		logger.info("sql:"+selectByCertNoSql);
		return jdbcTemplate.queryForList(selectByCertNoSql);
	}
	
	public List<Map<String,Object>> findTallyAll(){
		String sql = "select  a.CERT_NO as VOUCHER,1 as TYPE,a.FH_TIME as TALLY_DATE,a.AMOUNT as AMOUNT, a.STATUS as STATUS "
				+" from wjw_incomedetail a where a.STATUS=6 ";
		sql += "union all select b.OPER_NO as VOUCHER,2 as TYPE,b.FH_TIME as TALLY_DATE,b.AMOUNT as AMOUNT,b.STATUS as STATUS "  
				+" from wjw_paydetail b where b.STATUS=6";
		sql += " union all select c.OPER_NO as VOUCHER,3 as TYPE,c.FH_TIME as TALLY_DATE,c.BF_AMT as AMOUNT,c.STATUS as STATUS "  
				+"from wjw_bfhzb c where c.STATUS=6";
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql);
	}
	
	public void insertAccchangeEntity(WjwAccchange w){
		String sql = "insert into wjw_accchange (unit_no,unit_name,acc_no,acc_name,df_accno,"
				+ "df_accname,amount,drug_amt,medc_amt,tran_amt,tran_time,in_or_out,other_amt,note1) "
				+ "values('"+w.getUnitNo()+"','"+w.getUnitName()+"','"+w.getAccNo()+"','"+w.getAccName()
				+"','"+w.getDfAccno()+"','"+w.getDfAccname()+"',"+w.getAmount()+","+w.getDrugAmt()
				+","+w.getMedcAmt()+","+w.getTranAmt()+",'"+w.getTranTime()+"',"+w.getInOrOut()
				+","+w.getOtherAmt()+",'"+w.getNote1()+"')";
		System.out.println("sql:"+sql);
		jdbcTemplate.execute(sql);
	}


	public Pagination<Map<String, Object>> incomeDetail(Integer start,
			Integer limit, String startTime, String endTime, String status,
			String connNo,String amount) {
		
		String sql = " select a.INC_TIME as \"DATETIME\",a.CERT_NO as \"connNo\",a.UNIT_NAME as \"UNIT_NAME\",a.UNIT_NO as \"UNIT_NO\", "
				+ "a.OUT_ACCNAME as \"OUT_ACCNAME\",a.OUT_ACCNO as \"OUT_ACCNO\",  "
				+ "a.IN_NAME as \"IN_NAME\", a.IN_ACCNO as \"IN_ACCNO\", "
				+ "a.AMOUNT as \"AMOUNT\",a.DRUG_AMT as \"DRUG_AMT\", "
				+ "a.MEDICAL_AMT as \"MEDICAL_AMT\",1 as \"STATE\",a.STATUS  as \"STATUS\",a.FH_TIME  as \"FH_TIME\" "
				+ " from wjw_INCOMEDETAIL a where 1=1 " ;
		if(StringUtils.isNotBlank(startTime)){
			sql += " and a.INC_TIME>='"+startTime+"'";
		}
		if(StringUtils.isNotBlank(endTime)){
			sql += " and a.INC_TIME<='"+endTime+"'";
		}
		if(StringUtils.isNotBlank(status)){
			sql += " and a.STATUS="+status;
		}else{
			sql += " and a.STATUS in(5,6)";
		}
		if(StringUtils.isNotBlank(connNo)){
			sql += " and a.CERT_NO like '%"+connNo.trim()+"%'";
		}
		if(StringUtils.isNotBlank(amount)){
			sql += " and a.AMOUNT="+amount.trim()+"";
		}
		return jdbcPager.queryPage(sql, start, limit);
	}


	public Pagination<Map<String, Object>> payDetail(Integer start,
			Integer limit, String startTime, String endTime, String status,
			String connNo,String amount) {
		String sql = "select b.PAY_TIME as \"DATETIME\",b.OPER_NO as \"connNo\",b.UNIT_NAME as \"UNIT_NAME\",b.UNIT_NO as \"UNIT_NO\", "
				+ "b.OUT_ACCNAME as \"OUT_ACCNAME\",b.OUT_ACCNO as \"OUT_ACCNO\",  "
				+ "b.IN_NAME as \"IN_NAME\", b.IN_ACCNO as \"IN_ACCNO\", "
				+ "b.AMOUNT as \"AMOUNT\",(case when b.ITEM=2 then b.AMOUNT else 0.00 end) as \"DRUG_AMT\", "
				+ "(case when b.ITEM=1 then b.AMOUNT else 0.00 end) as \"MEDICAL_AMT\",2 as \"STATE\",b.STATUS  as \"STATUS\",b.FH_TIME  as \"FH_TIME\" "
				+ " from wjw_PAYDETAIL b where 1=1 " ;
		if(StringUtils.isNotBlank(startTime)){
			sql += " and b.PAY_TIME>='"+startTime+"'";
		}
		if(StringUtils.isNotBlank(endTime)){
			sql += " and b.PAY_TIME<='"+endTime+"'";
		}
		if(StringUtils.isNotBlank(status)){
			sql += " and b.STATUS="+status;
		}else{
			sql += " and b.STATUS in(5,6)";
		}
		if(StringUtils.isNotBlank(connNo)){
			sql += " and b.OPER_NO like '%"+connNo.trim()+"%'";
		}
		if(StringUtils.isNotBlank(amount)){
			sql += " and b.AMOUNT="+amount.trim()+"";
		}
		return jdbcPager.queryPage(sql, start, limit);
	}


	public Pagination<Map<String, Object>> appropriate(Integer start,
			Integer limit, String startTime, String endTime, String status,
			String connNo,String amount) {
		String sql = "select c.OPER_TIME as \"DATETIME\",c.CERT_NO as \"connNo\",c.UNIT_NAME as \"UNIT_NAME\",c.UNIT_NO as \"UNIT_NO\", "
				+ "c.OUT_ACCNAME as \"OUT_ACCNAME\",c.OUT_ACCNO as \"OUT_ACCNO\",  "
				+ "c.IN_NAME as \"IN_NAME\", c.IN_ACCNO as \"IN_ACCNO\", "
				+ "c.BF_AMT as \"AMOUNT\",c.BF_DRUG_AMT as \"DRUG_AMT\", "
				+ "c.BF_MEDC_AMT as \"MEDICAL_AMT\",3 as \"STATE\",c.STATUS  as \"STATUS\",c.FH_TIME  as \"FH_TIME\" "
				+ " from wjw_BFHZB c where 1=1 " ;
		if(StringUtils.isNotBlank(startTime)){
			sql += " and c.OPER_TIME>='"+startTime+"'";
		}
		if(StringUtils.isNotBlank(endTime)){
			sql += " and c.OPER_TIME<='"+endTime+"'";
		}
		if(StringUtils.isNotBlank(status)){
			sql += " and c.STATUS="+status;
		}else{
			sql += " and c.STATUS in(5,6)";
		}
		if(StringUtils.isNotBlank(connNo)){
			sql += " and c.CERT_NO like '%"+connNo.trim()+"%'";
		}
		if(StringUtils.isNotBlank(amount)){
			sql += " and c.AMOUNT="+amount.trim()+"";
		}
		return jdbcPager.queryPage(sql, start, limit);
	}
	
}
