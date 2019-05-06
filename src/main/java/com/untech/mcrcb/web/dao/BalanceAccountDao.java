package com.untech.mcrcb.web.dao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.model.WjwAccchange;
import com.untech.mcrcb.web.model.WjwPaydetail;
import com.untech.mcrcb.web.util.Utils;
import com.unteck.tpc.framework.core.util.SecurityContextUtil;

@Repository
public class BalanceAccountDao extends BaseDao<WjwAccchange, Long>{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Map<String,Object> findIncomeByVoucher(String voucher){
		String sql = "select AMOUNT,DRUG_AMT,MEDICAL_AMT,OTHER_AMT,"
				+"XN_ACCTNO,XN_ACCTNAME,ZC_ACCTNO,ZC_ACCTNAME, "
				+ "CERT_NO as VOUCHER,STATUS   from wjw_incomedetail where CERT_NO='"+voucher+"'";
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql).size()==0?
				null:jdbcTemplate.queryForList(sql).get(0);
	}

	public Map<String, Object> findAccountByAccountNo(String accNo) {
		
		String sql = "select AMOUNT,DRUG_AMT,MEDICAL_AMT,UNIT_NO,UNIT_NAME from wjw_ACCOUNT where ACC_NO = '"+accNo+"'";
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql).size()==0?
				null:jdbcTemplate.queryForList(sql).get(0);
	}

	public void updateAccountAmount(BigDecimal amount, BigDecimal drugAmt,
			BigDecimal medicalAmt,String accNo) {
		String sql = "update WJW_ACCOUNT set AMOUNT = "+amount+",DRUG_AMT = "+drugAmt+",MEDICAL_AMT = "+medicalAmt+" where ACC_NO = '"+accNo+"'";
		logger.info("sql:"+sql);
		jdbcTemplate.update(sql);
		
	}
	
	public void updateIncomeDetailStatus(String voucher,int status,String date,String user){
		String sql = "update WJW_INCOMEDETAIL set FH_USER = '"+user+"',STATUS ="+status+" ,FH_TIME = '"+date+"'"
				+" where CERT_NO = '"+voucher+"'";
		logger.info("sql:"+sql);
		jdbcTemplate.update(sql);
	}

	public void deleteByVoucher(String voucher,Boolean isPayBack){
		String sql = "delete from WJW_ACCCHANGE where 1=1 ";
		if(isPayBack){
			sql += " and note2='"+voucher+"'";
		}else{
			sql += " and note1='"+voucher+"'";
		}
		logger.info("sql:"+sql);
		jdbcTemplate.execute(sql);
	}

	public Map<String, Object> findPayByVoucher(String voucher) {

		String sql = "select  IN_NAME, IN_ACCNO,IN_BANK,UNIT_NO,"
				+ "AMOUNT,(case when ITEM=2 then AMOUNT else 0.00 end) as DRUG_AMT,"
				+ "(case when ITEM=1 then AMOUNT else 0.00 end) as MEDICAL_AMT, "
				+ "ZC_ACCTNO, ZC_ACCTNAME,STATUS,"
				+ "XN_ACCTNO, XN_ACCTNAME,CONN_NO,BACK_FLG,BACK_VOUCHER "
				+ " from WJW_PAYDETAIL  where OPER_NO = '"+voucher+"'" ;
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql).size()==0?
				null:jdbcTemplate.queryForList(sql).get(0);
	}
	
	public Map<String, Object> findPaydetailByVoucher(String voucher) {
		String sql = "select * from WJW_PAYDETAIL  where OPER_NO = '"+voucher+"'" ;
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql).size()==0?
				null:jdbcTemplate.queryForList(sql).get(0);
	}

	public void updatePayDetailStatus(String voucher, int stauts, String date,
			String user,boolean isPayback) {
		String sql = "update wjw_PAYDETAIL set FH_USER = '"+user+"',STATUS ="+stauts+" ,FH_TIME = '"+date+"'";
					 
		if(isPayback){
			if(stauts==5){
				sql += ",BACK_FLG=1 ";//退汇未处理
			}else if(stauts==6){
				sql += ",BACK_FLG=2 ";//退汇记账完成
			}		
		}
		sql += " where OPER_NO = '"+voucher+"'";
		logger.info("sql:"+sql);
		jdbcTemplate.update(sql);
		
	}
	
	public void updatePayMainStatus(String conn,String date){
		String sql = "update wjw_PAYMAIN set STATUS = 6 where CONN_NO = '"+conn+"'";
		logger.info("sql:"+sql);
		jdbcTemplate.update(sql);
	}

	public Map<String, Object> findAppropriateByVoucher(String voucher) {

		String sql = "select BF_AMT as AMOUNT,BF_DRUG_AMT as DRUG_AMT,"
				+ "BF_MEDC_AMT as MEDICAL_AMT, STATUS,"
				+ "ZC_ACCTNO,ZC_ACCTNAME,"
				+ "XN_ACCTNO,XN_ACCTNAME,CONN_NO "
				+ " from WJW_BFHZB where CERT_NO = '"+voucher+"'" ;
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql).size()==0?
				null:jdbcTemplate.queryForList(sql).get(0);
	}

	public void updateAppropriateStatus(String voucher, int status, String date,
			String user) {
		String sql = "update WJW_BFHZB set FH_USER = '"+user+"',STATUS ="+status+",FH_TIME = '"+date+"' "+
					" where CERT_NO = '"+voucher+"'";
		logger.info("sql:"+sql);
		jdbcTemplate.update(sql);
		
	}

	public List<Map<String, Object>> findAppropriateDetailByConn(String conn) {
		
		String sql = "select AMOUNT,DRUG_BF_AMT as DRUG_AMT,MEDC_BF_AMT as MEDICAL_AMT,"
					+ "XN_ACCTNO,XN_ACCTNAME,ZC_ACCTNO,ZC_ACCTNAME "
					+ " from WJW_BFDETAIL where CONN_NO = '"+conn+"'";
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql);

	}

	public Map<String, Object> findMainAccount(String inOrOut) {
		String sql = "select * from wjw_account where IN_OR_OUT="+inOrOut+" and right(ACC_NO,4)='0000'";
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql).size()==0?null:jdbcTemplate.queryForList(sql).get(0);
	}
	public Map<String, Object> findMainAccountChange(String inOrOut,int accCode) {
		String sql = "select * from wjw_account where IN_OR_OUT="+inOrOut+" AND ACCOUNT_SYS_CODE ="+accCode+" and right(ACC_NO,4)='0000'";
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql).size()==0?null:jdbcTemplate.queryForList(sql).get(0);
	}
	public Map<String, Object> findDqfAccount(String inOrOut) {
		String sql = "select * from wjw_account where IN_OR_OUT="+inOrOut+" and right(ACC_NO,4)='9999'";
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql).size()==0?null:jdbcTemplate.queryForList(sql).get(0);
	}
	public Map<String, Object> findDqfAccountChange(String inOrOut,int accCode) {
		String sql = "select * from wjw_account where IN_OR_OUT="+inOrOut+" AND ACCOUNT_SYS_CODE ="+accCode+" and right(ACC_NO,4)='9999'";
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql).size()==0?null:jdbcTemplate.queryForList(sql).get(0);
	}
	public Map<String, Object> findTradeDetailByVoucher(String voucher) {
		String sql = "select * from wjw_accchange where NOTE1='"+voucher+"'";
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql).size()==0?null:jdbcTemplate.queryForList(sql).get(0);
	}

	public void updateAccountAmount(String accNo,BigDecimal amount) {
		String sql = "update WJW_ACCOUNT set AMOUNT = "+amount+" where ACC_NO = '"+accNo+"'";
		logger.info("sql:"+sql);
		jdbcTemplate.update(sql);
		
	}

	public void addPayDetail(WjwPaydetail entity){
		String sql = "insert into WJW_PAYDETAIL (AMOUNT, CONN_NO, CURRENCY, ECNO_FL, FOOT_YSDW, FUNC_FL, IN_ACCNO, IN_BANK, IN_NAME, ITEM,"
				+ " ITME_YS, OPER_NO, OUT_ACCNAME, OUT_ACCNO, OUT_BANK, PAY_TIME, PAY_WAY, STATUS, TOP_YSDW, UNIT_NAME, UNIT_NO, "
				+ "XN_ACCTNAME, XN_ACCTNO, YT, ZB_DETAIL, ZJ_FLD,ZC_ACCTNO,ZC_ACCTNAME,FH_USER,FH_TIME,BACK_FLG,BACK_VOUCHER,NOTE1) "
				+ "values("+entity.getAmount()+", '"+entity.getConnNo()+"','"+entity.getCurrency()+"', '"
				+entity.getEcnoFl()+ "','"+entity.getFootYsdw()+"','"+entity.getFuncFl()+"','"+entity.getInAccno()+"','"
				+entity.getInBank()+ "','"+entity.getInName()+"',"+entity.getItem()+",'"+entity.getItmeYs()+"','"
				+entity.getOperNo()+ "','"+entity.getOutAccname()+"','"+entity.getOutAccno()+"','"+entity.getOutBank()+"','"
				+entity.getPayTime()+"',"
				+entity.getPayWay()+ ","+entity.getStatus()+",'"+entity.getTopYsdw()+"','"+entity.getUnitName()+"','"
				+entity.getUnitNo()+ "','"+entity.getXnAcctName()+"','"+entity.getXnAcctno()+"','"+entity.getYt()+"','"
				+entity.getZbDetail()+ "','"+entity.getZjFld()+"','"+entity.getZcAcctno()+ "','"+entity.getZcAcctname()+"','"
				+entity.getFhUser()+"','"+entity.getFhTime()+"',"+entity.getBackFlg()+",'"+entity.getBackVoucher()+"','"+entity.getNote1()+"')";
		logger.info("sql:"+sql);
		jdbcTemplate.execute(sql);
	}
	/**
	 * 根据机构号获取卫生院简称
	 * @param OId
	 * @return
	 */
	public String getWSYJianpin(Long OId){
		String sql = "select DESCN from UNTECK_ORGANIZATION WHERE ID="+OId;
		return this.jdbcTemplate.queryForObject(sql,String.class);
	}
	/**
	 * 获得凭证最大序列号
	 * @return
	 */
	public int getMaxCode() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String date = format.format(new Date())+"02";
		String sql = "select max(substr(OPER_NO,length(OPER_NO)-2)) from WJW_PAYDETAIL where OPER_NO like '"+date+"%'";
	//	int num = this.jdbcTemplate.queryForInt(sql);
		Number number = this.jdbcTemplate.queryForObject(sql,Integer.class);
		return (number != null ? number.intValue() : 0);
	}

	public void updatePayBackFlag(int backFlg, String backVoucher,String desc) {
		String sql = "update wjw_paydetail set BACK_FLG="+backFlg+", note1='"+desc+"'  where OPER_NO='"+backVoucher+"'";
		logger.info("sql:"+sql);
		jdbcTemplate.update(sql);
	}

	public Map<String, Object> findInterestOutByVoucher(String voucher) {
		String sql = "select * from WJW_INTEREST where VOUCHER='"+voucher+"'";
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql).size()==0?null:jdbcTemplate.queryForList(sql).get(0);
	}

	public void updateMainAccountInterest(String accNo, BigDecimal intCome) {
		String sql = "update WJW_ACCOUNT set INT_COME="+intCome+" where ACC_NO='"+accNo+"'";
		logger.info("sql:"+sql);
		jdbcTemplate.update(sql);
	}

	public void updateInterestOutStatus(Integer status,String voucher) {
		String sql = "update WJW_INTEREST set STATUS="+status+" where VOUCHER='"+voucher+"'";
		logger.info("sql:"+sql);
		jdbcTemplate.update(sql);
	}

	public Map<String, Object> findDQFTradeByVoucher(String voucher) {
		String sql  = "select * from WJW_ACCCHANGE where RIGHT(ACC_NO,4)='9999' and NOTE1='"+voucher+"'";
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql).size()==0?null:jdbcTemplate.queryForList(sql).get(0);
	}

	public Map<String, Object> findPayBackByVoucher(String voucher,Integer backFlg) {
		String sql = "select * from WJW_PAYDETAIL  where OPER_NO = '"+voucher+"'"+" and BACK_FLG="+backFlg;
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql).size()==0?
				null:jdbcTemplate.queryForList(sql).get(0);
	}

	public Map<String, Object> findNewPayByBackVoucher(String voucher) {
		String sql = "select * from WJW_PAYDETAIL where BACK_VOUCHER='"+voucher+"'";
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql).size()==0?
				null:jdbcTemplate.queryForList(sql).get(0);
	}

	public void deleteNewPayByVoucher(String newVoucher) {
		String sql = "delete from WJW_PAYDETAIL where OPER_NO='"+newVoucher+"'";
		logger.info("sql:"+sql);
		jdbcTemplate.update(sql);
	}

	public Map<String, Object> findPayeeInfoByAccno(String unitNo,String inAccno) {
		String sql = "select * from WJW_PAYERACCT where ACCT_NO='"+inAccno+"' and UNIT_NO='"+unitNo+"'";
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql).size()==0?
				null:jdbcTemplate.queryForList(sql).get(0);
	}

	public void savePayeeInfo(String unitNo, String inName, String inAccno,
			String inBank) {
		String sql = "insert into WJW_PAYERACCT (ACCT_NO,ACCT_NAME,ACCT_BANK,UNIT_NO) values("
				+ "'"+inAccno+"','"+inName+"','"+inBank+"','"+unitNo+"')";
		logger.info("sql:"+sql);
		jdbcTemplate.execute(sql);
	}

	public void updatePayeeInfo(String unitNo, String inName, String inAccno,
			String inBank) {
		String sql = "update WJW_PAYERACCT set ACCT_NAME='"+inName+"',"+"ACCT_BANK='"+inBank+"' where ACCT_NO='"+inAccno+"' and UNIT_NO='"+unitNo+"'";
		logger.info("sql:"+sql);
		jdbcTemplate.execute(sql);
	}



}
