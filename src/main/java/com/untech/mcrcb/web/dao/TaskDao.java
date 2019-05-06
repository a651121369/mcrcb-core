package com.untech.mcrcb.web.dao;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.model.WjwPaymain;
import com.untech.mcrcb.web.service.WjwPaydetailService;
import com.unteck.common.dao.jdbc.NamedParameterJdbcPager;
import com.unteck.common.dao.support.Pagination;

/**
 * WJW_PAYMAIN DAO
 * @author            chenyong
 * @since             2015-11-04
 */
@Repository
public class TaskDao
  extends BaseDao<WjwPaymain, Long>
{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcPager jdbcPager;
	
	@Autowired
	private WjwPaydetailService wjwPaydetailService;
	
	public Pagination<Map<String, Object>> fetchPager(String  connNo,String unitNo,String startTime,String endTime,
			Integer start, Integer limit,String dspUserno ) {
		String sql = "SELECT ID  as\"id\", UNIT_NAME as \"unitName\", UNIT_NO as \"unitNo\", PAY_TYPE  as \"payType\", SQ_TIME  as \"sqTime\", STATUS  as \"status\", " +
				"DSP_USERNO  as \"dspUserno\" , CS_USERNO  as \"csUserno\" , FS_USERNO  as \"fsUserno\" , " +
				"CS_TIME  as \"csTime\" , FS_TIME  as \"fsTime\", CONN_NO  as \"connNo\" , DSP_USERNAME  as \"dspUsername\", CS_USERNAME  as \"csUsername\", " +
				"FS_USERNAME  as \"fsUsername\", SQ_USERNO  as \"sqUserno\", SQ_USERNAME  as \"sqUsername\", REMARK  as \"remark\", NOTE1  as \"note1\", " +
				"NOTE2  as \"note2\" FROM WJW_PAYMAIN  where DSP_USERNO='"+dspUserno+"'  and STATUS  in (1,2)";
		if(!"".equals(startTime ) && !"null".equals(startTime ) && startTime != null){
			sql += "  and SQ_TIME >='"+startTime+"'";
		}
		if(!"".equals(endTime ) && !"null".equals(startTime ) && startTime != null){
			sql += "  and SQ_TIME <='"+endTime+"'";
		}
		if(!"".equals(connNo ) && !"null".equals(connNo ) && connNo != null){
			sql += "  and CONN_NO ='"+connNo+"'";
		}
		if(!"".equals(unitNo ) && !"null".equals(unitNo ) && unitNo != null){
			sql += "  and UNIT_NO in ('"+unitNo+"')";
		}
		logger.info("sql:"+sql);
		return jdbcPager.queryPage(sql, start, limit);
	}
	
	public Pagination<Map<String, Object>> fetchPagerfinish(String  connNo,String unitNo,String startTime,String endTime,
			Integer start, Integer limit,String dspUserno ) {
		String sql = "SELECT ID  as\"id\", UNIT_NAME as \"unitName\", UNIT_NO as \"unitNo\", PAY_TYPE  as \"payType\", SQ_TIME  as \"sqTime\", STATUS  as \"status\", " +
				"DSP_USERNO  as \"dspUserno\" , CS_USERNO  as \"csUserno\" , FS_USERNO  as \"fsUserno\" , " +
				"CS_TIME  as \"csTime\" , FS_TIME  as \"fsTime\", CONN_NO  as \"connNo\" , DSP_USERNAME  as \"dspUsername\", CS_USERNAME  as \"csUsername\", " +
				"FS_USERNAME  as \"fsUsername\", SQ_USERNO  as \"sqUserno\", SQ_USERNAME  as \"sqUsername\", REMARK  as \"remark\", NOTE1  as \"note1\", " +
				"NOTE2  as \"note2\" FROM WJW_PAYMAIN  where 1=1 and (CS_USERNO='"+dspUserno+"' or FS_USERNO ='"+dspUserno+"') ";
		if(!"".equals(startTime ) && !"null".equals(startTime ) && startTime != null){
			sql += "  and SQ_TIME >='"+startTime+"'";
		}
		if(!"".equals(endTime ) && !"null".equals(startTime ) && startTime != null){
			sql += "  and SQ_TIME <='"+endTime+"'";
		}
		if(!"".equals(connNo ) && !"null".equals(connNo ) && connNo != null){
			sql += "  and CONN_NO ='"+connNo+"'";
		}
		if(!"".equals(unitNo ) && !"null".equals(unitNo ) && unitNo != null){
			sql += "  and UNIT_NO in ('"+unitNo+"')";
		}
		sql += "  ORDER BY SQ_TIME DESC";
		logger.info("sql:"+sql);
		return jdbcPager.queryPage(sql, start, limit);
	}
	
	public Pagination<Map<String, Object>> fetchPagerself(String  connNo,String startTime,String endTime,
			Integer start, Integer limit,String dspUserno ) {
		String sql = "SELECT ID  as\"id\", UNIT_NAME as \"unitName\", UNIT_NO as \"unitNo\", PAY_TYPE  as \"payType\", SQ_TIME  as \"sqTime\", STATUS  as \"status\", " +
				"DSP_USERNO  as \"dspUserno\" , CS_USERNO  as \"csUserno\" , FS_USERNO  as \"fsUserno\" , " +
				"CS_TIME  as \"csTime\" , FS_TIME  as \"fsTime\", CONN_NO  as \"connNo\" , DSP_USERNAME  as \"dspUsername\", CS_USERNAME  as \"csUsername\", " +
				"FS_USERNAME  as \"fsUsername\", SQ_USERNO  as \"sqUserno\", SQ_USERNAME  as \"sqUsername\", REMARK  as \"remark\", NOTE1  as \"note1\", " +
				"NOTE2  as \"note2\" FROM WJW_PAYMAIN  where 1=1 and SQ_USERNO='"+dspUserno+"' ";
		if(!"".equals(startTime ) && !"null".equals(startTime ) && startTime != null){
			sql += "  and SQ_TIME >='"+startTime+"'";
		}
		if(!"".equals(endTime ) && !"null".equals(startTime ) && startTime != null){
			sql += "  and SQ_TIME <='"+endTime+"'";
		}
		if(!"".equals(connNo ) && !"null".equals(connNo ) && connNo != null){
			sql += "  and CONN_NO ='"+connNo+"'";
		}
		sql += "  ORDER BY SQ_TIME DESC";
		logger.info("sql:"+sql);
		return jdbcPager.queryPage(sql, start, limit);
	}
	
	public List<Map<String, Object>> getPayMainByConnno(String  connNo ) {
		String sql = "SELECT  ID  as\"id\", UNIT_NAME as \"unitName\", UNIT_NO as \"unitNo\", PAY_TYPE  as \"payType\", SQ_TIME  as \"sqTime\", STATUS  as \"status\", " +
				"DSP_USERNO  as \"dspUserno\" , CS_USERNO  as \"csUserno\" , FS_USERNO  as \"fsUserno\" , " +
				"CS_TIME  as \"csTime\" , FS_TIME  as \"fsTime\", CONN_NO  as \"connNo\" , DSP_USERNAME  as \"dspUsername\", CS_USERNAME  as \"csUsername\", " +
				"FS_USERNAME  as \"fsUsername\", SQ_USERNO  as \"sqUserno\", SQ_USERNAME  as \"sqUsername\", REMARK  as \"remark\", NOTE1  as \"note1\", " +
				"NOTE2  as \"note2\" FROM WJW_PAYMAIN  where CONN_NO='"+connNo+"'";
		logger.info("sql:"+sql);
		return this.jdbcTemplate.queryForList(sql);
	}
	
	public List<Map<String, Object>> getPayMainById(String  id ) {
		String sql = "SELECT ID  as\"id\", UNIT_NAME as \"unitName\", UNIT_NO as \"unitNo\", PAY_TYPE  as \"payType\", SQ_TIME  as \"sqTime\", STATUS  as \"status\", " +
				"DSP_USERNO  as \"dspUserno\" , CS_USERNO  as \"csUserno\" , FS_USERNO  as \"fsUserno\" , " +
				"CS_TIME  as \"csTime\" , FS_TIME  as \"fsTime\", CONN_NO  as \"connNo\" , DSP_USERNAME  as \"dspUsername\", CS_USERNAME  as \"csUsername\", " +
				"FS_USERNAME  as \"fsUsername\", SQ_USERNO  as \"sqUserno\", SQ_USERNAME  as \"sqUsername\", REMARK  as \"remark\", NOTE1  as \"note1\", " +
				"NOTE2  as \"note2\" FROM WJW_PAYMAIN  where id="+id;
		logger.info("sql:"+sql);
		return this.jdbcTemplate.queryForList(sql);
	}
	
	
	public List<Map<String, Object>> getPayMxByConnno(String  connNo ) {
		String sql = "SELECT @rownum:=@rownum+1 as \"rowid\", ID as \"id\", UNIT_NO as \"unitNo\", AMOUNT as \"amount\", UNIT_NAME as \"unitName\", OUT_ACCNO as \"outAccno\", OUT_ACCNAME as \"outAccname\", " +
				"OUT_BANK as \"outBank\", IN_ACCNO as \"inAccno\", IN_NAME as \"inName\"," +
				"IN_BANK as \"inBank\", PAY_TIME as \"payTime\", ZJ_FLD as \"zjFld\", " +
				"PAY_WAY as \"payWay\", TOP_YSDW as \"topYsdw\", FOOT_YSDW as \"footYsdw\", ITME_YS as \"itmeYs\", " +
				"FUNC_FL as \"funcFl\", ECNO_FL as \"ecnoFl\", ZB_DETAIL as \"zbDetail\", " +
				"CURRENCY as \"currency\", STATUS as \"status\", OPER_NO as \"operNo\", YT as \"yt\", " +
				"ITEM as \"item\", CONN_NO as \"connNo\", NOTE1 as \"note1\", NOTE2 as \"note2\"" +
				" FROM WJW_PAYDETAIL, (select @rownum:=0) a where  CONN_NO='"+connNo+"'";
		logger.info("sql:"+sql);
		return this.jdbcTemplate.queryForList(sql);
	}

	public List<Map<String, Object>> getUserName(String  userCode ) {
		String sql = "SELECT id as userId,user_code as \"userCode\",user_name  as \"userName\" " +
				" FROM UNTECK_USER where  user_code='"+userCode+"' ";
		logger.info("sql:"+sql);
		return this.jdbcTemplate.queryForList(sql);
	}
	public Map<String, Object> getUserByCode(String  userCode) {
		String sql = "SELECT id as userId,user_code as \"userCode\",user_name  as \"userName\" " +
				" FROM UNTECK_USER where  user_code='"+userCode+"' ";
		logger.info("sql:"+sql);
		return this.jdbcTemplate.queryForMap(sql);
	}

	public List<Map<String, Object>> getUserRole(Integer  userId) {
		String sql = "SELECT\n" +
						"	urole.USER_ID as userId,\n" +
						"	role.`CODE` as roleCode\n" +
						"FROM\n" +
						"	unteck_user_role urole\n" +
						"LEFT JOIN unteck_role role\n" +
						"ON urole.ROLE_ID=role.ID\n" +
						"where 1=1\n" +
						"AND urole.USER_ID ='"+userId+"'";
		logger.info("sql:"+sql);
		return this.jdbcTemplate.queryForList(sql);
	}


	public List<Map<String, Object>> queryMainAcctNo( ) {
		String sql = "select  acc_no as  \"accNo\" ,cust_name \"custName\",bank_unit \"bankUnit\",bank_name  \"bankName\" from wjw_account where in_or_out=2 and ACC_FLD=1";
		logger.info("sql:"+sql);
		return this.jdbcTemplate.queryForList(sql);
	}
	public List<Map<String, Object>> queryMainAcctNoByaccCode(int accCode) {
		String sql = "select  acc_no as  \"accNo\" ,cust_name \"custName\",bank_unit \"bankUnit\",bank_name  \"bankName\" from wjw_account where in_or_out=2 and ACC_FLD=1 and ACCOUNT_SYS_CODE = '"+accCode+"'";
		logger.info("sql:"+sql);
		return this.jdbcTemplate.queryForList(sql);
	}
	
	public  void updatePayMxAcct(String  connNo,String accNo,String custName,String bankName ) {
		String sql = " update wjw_PAYDETAIL  set  OUT_ACCNO='"+accNo+"',OUT_ACCNAME='"+custName+"',OUT_BANK='"+ bankName+"' where CONN_NO='"+connNo+"'";
		logger.info("sql:"+sql);
		this.jdbcTemplate.update(sql);
				
	}
	
	public  void updatePayMx(String  connNo,int status) {
		String sql = " update wjw_PAYDETAIL  set  STATUS="+status+" where CONN_NO='"+connNo+"'";
		logger.info("sql:"+sql);
		this.jdbcTemplate.update(sql);
				
	}

	public  void upadateStaAndOper(Long id,int status,String operNo) {
		String sql =  " update wjw_PAYDETAIL  set  STATUS="+status+", OPER_NO="+operNo+" where id='"+id+"'";
		logger.info("sql:"+sql);
		this.jdbcTemplate.update(sql);
	}
	public  void upadateStaAndOper(Long id,int status) {
		String sql =  " update wjw_PAYDETAIL  set  STATUS="+status+" where id='"+id+"'";
		logger.info("sql:"+sql);
		this.jdbcTemplate.update(sql);
	}
	
	public  void upadateSta(Long id,int status,String operNo) {
		String sql =  " update wjw_PAYDETAIL  set  STATUS="+status+", OPER_NO="+operNo+" where id='"+id+"'";
		logger.info("sql:"+sql);
		this.jdbcTemplate.update(sql);
	}
	
	public  void updatePayAndNo(String  connNo,int status) {
		if(status!=5){
			String sql = " update wjw_PAYDETAIL  set  STATUS="+status+" where CONN_NO='"+connNo+"'";	
			logger.info("sql:"+sql);
			this.jdbcTemplate.update(sql);
		}
	}
	
	public List<Map<String, Object>> getInAccList(String IN_ACCNO ) {
		String getInAccListSql = "select ACCT_NO from WJW_PAYERACCT where  ACCT_NO = '"+IN_ACCNO+"'";
		logger.info("sql:"+getInAccListSql);
		return this.jdbcTemplate.queryForList(getInAccListSql);
	}
	
	public void saveInAcc(String IN_ACCNO,String IN_NAME,String IN_BANK,String unitNo){
		String saveInAccSql = "insert into WJW_PAYERACCT (ACCT_NO,ACCT_NAME,ACCT_BANK,UNIT_NO) values("
				+ "'"+IN_ACCNO+"','"+IN_NAME+"','"+IN_BANK+"','"+unitNo+"')";
		logger.info("sql:"+saveInAccSql);
		jdbcTemplate.execute(saveInAccSql);
	}
		
}

