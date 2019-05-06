package com.untech.mcrcb.web.dao;



import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.untech.mcrcb.web.model.WjwAccount;
import com.untech.mcrcb.web.model.WjwBfhzb;
import com.unteck.common.dao.support.Pagination;
import com.untech.mcrcb.web.enhance.BaseDao;

/**
 * WJW_BFHZB DAO
 * @author            chenyong
 * @since             2015-11-04
 */
@Repository
public class WjwBfhzbDao extends BaseDao<WjwBfhzb, Long>{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Pagination<Map<String, Object>> getAllIncomeAccount(){
		String sql = "select ACC_NO as \"accNo\",CUST_NAME as \"custName\", "
				+ "UNIT_NO as \"unitNo\",UNIT_NAME as \"unitName\", "
				+ "DRUG_AMT as \"bfDrugAmt\",MEDICAL_AMT as \"bfMedcAmt\" "
				+ "from WJW_ACCOUNT "
				//测试  暂时注释限制条件
//				+ "where RIGHT(ACC_NO,4) != '9999' and "
//				+ "RIGHT(ACC_NO,4) != '0000' and in_or_out=1"
				;
		logger.info("sql:"+sql);
		return jdbcPager.queryPage(sql,0,50);
	}

	public List<Map<String, Object>> getAllIncomeAccountList() {
		String sql = "select ACC_NO as \"accNo\",CUST_NAME as \"custName\", "
				+ "UNIT_NO as \"unitNo\",UNIT_NAME as \"unitName\", "
				+ "DRUG_AMT as \"bfDrugAmt\",MEDICAL_AMT as \"bfMedcAmt\" "
				+ "from WJW_ACCOUNT "
				+ "where RIGHT(ACC_NO,4) != '9999' and RIGHT(ACC_NO,4) != '0000' "
				+ "and ACC_FLD=2 and in_or_out=1";
		System.out.println(sql);
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql);
	}

	public List<Map<String, Object>> getWjwIncomeAccountList() {
		StringBuffer dynamicSql = this.subfetchPagerSql();
		dynamicSql.append(" AND ACCOUNT_SYS_CODE =1");
		logger.info("sql:"+dynamicSql.toString());
		return jdbcTemplate.queryForList(dynamicSql.toString());
	}

	public List<Map<String, Object>> getJlyIncomeAccountList() {
		StringBuffer dynamicSql = this.subfetchPagerSql();
		dynamicSql.append(" AND ACCOUNT_SYS_CODE =2");
		logger.info("sql:"+dynamicSql.toString());
		return jdbcTemplate.queryForList(dynamicSql.toString());
	}
	/**
	 * TODO: 重构了查询子账户方法，在其主方法体上进行更改
	 * @return java.lang.StringBuffer
	 * @Author Mr.lx
	 * @Date 2019/3/28 002 8
	 **/
	private StringBuffer subfetchPagerSql() {
		StringBuffer dynamicSql = new StringBuffer();
		String sql = "select ACC_NO as \"accNo\",CUST_NAME as \"custName\",ACCOUNT_SYS_CODE as \"accCode\", "
				+ "UNIT_NO as \"unitNo\",UNIT_NAME as \"unitName\", "
				+ "DRUG_AMT as \"bfDrugAmt\",MEDICAL_AMT as \"bfMedcAmt\" "
				+ "from WJW_ACCOUNT "
				+ "where RIGHT(ACC_NO,4) != '9999' and RIGHT(ACC_NO,4) != '0000' "
				+ "and ACC_FLD=2 and in_or_out=1";
		dynamicSql.append(sql);
		return dynamicSql;
	}


	/**
	 * 根据id获得拨付关联号
	 * @param id
	 * @return
	 */
	public String getBfhzbConnNo(Long id){
		String sql = "select CONN_NO from WJW_BFHZB where ID="+id;
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForObject(sql,String.class);
	}
	
	public Map<String,Object> getPrintList(Long id){
		String sql = "select UNIT_NO as \"unitNo\",UNIT_NAME as \"unitName\","
				+ "BF_AMT as \"amount\",BF_DRUG_AMT as \"drugAmt\","
				+ "BF_MEDC_AMT as \"medcAmt\",BF_TIM as \"bfTime\" "
				+ "from WJW_BFHZB where ID = "+id;
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql).get(0);
	}
	
	/**
	 * 根据机构号获得收入账户
	 * @param unitNo
	 * @return
	 */
	public WjwAccount findIncomeAccountByUnitNo(String unitNo) {
		String sql = "select * from WJW_ACCOUNT where in_or_out=1 and UNIT_NO ='"+unitNo+"'";
		logger.info("sql:"+sql);
		return changeToAccount(jdbcTemplate.queryForMap(sql));
	}
	/** TODO: --- 缴款的时候---如果是医养中心的，直接缴款到支出户，不存在收入户，
	 * @Author Mr.lx
	 * @Date 2019/4/4 0004
	* @param unitNo  机构号
	 * @return com.untech.mcrcb.web.model.WjwAccount
	 **/
	public WjwAccount findOutcomeAccountByUnitNo(String unitNo) {
		String sql = "select * from WJW_ACCOUNT where in_or_out=2 and UNIT_NO ='"+unitNo+"'";
		logger.info("sql:"+sql);
		return changeToAccount(jdbcTemplate.queryForMap(sql));
	}

	/**
	 * 根据机构号获得支出账户
	 * @param unitNo
	 * @return
	 */
	public WjwAccount findOutAccountByUnitNo(String unitNo) {
		String sql = "select * from WJW_ACCOUNT where in_or_out=2 and UNIT_NO ='"+unitNo+"'";
//		return jdbcTemplate.queryForObject(sql,WjwAccount.class);
		logger.info("sql:"+sql);
		return changeToAccount(jdbcTemplate.queryForMap(sql));
	}
	
	/**
	 * 获得卫计委收入真实账户
	 * @return
	 */
	public WjwAccount findWjwMainInAccount(){
		String sql = "select * from WJW_ACCOUNT where in_or_out=1 and ACC_FLD=1";
		logger.info("sql:"+sql);
		return changeToAccount(jdbcTemplate.queryForMap(sql));
	}
	/** TODO:
	 * @Author Mr.lx
	 * @Date 2019/4/2 0002
	* @param accCode   用户类型
	 * @return com.untech.mcrcb.web.model.WjwAccount
	 **/
	public WjwAccount findWjwMainInAccount(String accCode){
		String sql = "SELECT\n" +
						"	*\n" +
						"FROM\n" +
						"	WJW_ACCOUNT\n" +
						"WHERE\n" +
						"	in_or_out = 1\n" +
						"AND ACC_FLD = 1\n" +
						"AND ACCOUNT_SYS_CODE="+accCode+"";
		logger.info("sql:"+sql);
		return changeToAccount(jdbcTemplate.queryForMap(sql));
	}

	/**
	 * 获得卫计委支出真实账户
	 * @return
	 */
	public WjwAccount findWjwMainOutAccount(){
		String sql = "select * from WJW_ACCOUNT where in_or_out=2 and ACC_FLD=1";
		logger.info("sql:"+sql);
		return changeToAccount(jdbcTemplate.queryForMap(sql));
	}
	public WjwAccount findWjwMainOutAccount(String accCode){
		String sql = "SELECT\n" +
							"	*\n" +
							"FROM\n" +
							"	WJW_ACCOUNT\n" +
							"WHERE\n" +
							"	in_or_out = 2\n" +
							"AND ACC_FLD = 1\n" +
							"AND ACCOUNT_SYS_CODE="+accCode+"";
		logger.info("sql:"+sql);
		return changeToAccount(jdbcTemplate.queryForMap(sql));
	}
	
	/**
	 * 获得卫计委收入虚拟账户
	 * @return
	 */
	public WjwAccount findWjwXNInAccount(){
		String sql = "select * from WJW_ACCOUNT where in_or_out=1 and ACC_FLD=2 and RIGHT(ACC_NO,4) = '0000'";
		logger.info("sql:"+sql);
		return changeToAccount(jdbcTemplate.queryForMap(sql));
	}
	public WjwAccount findWjwXNInAccount(String accCode){
		String sql = "select * from WJW_ACCOUNT where in_or_out=1 and ACC_FLD=2 and RIGHT(ACC_NO,4) = '0000' AND ACCOUNT_SYS_CODE="+accCode+"";
		logger.info("sql:"+sql);
		return changeToAccount(jdbcTemplate.queryForMap(sql));
	}

	public WjwAccount findWjwInAccount(int accCode){
		String sql = "SELECT\n" +
						"	*\n" +
						"FROM\n" +
						"	WJW_ACCOUNT\n" +
						"WHERE\n" +
						"	in_or_out = 1\n" +
						"AND ACC_FLD = 2\n" +
						"AND ACCOUNT_SYS_CODE ='"+accCode+"'\n" +
						"AND RIGHT (ACC_NO, 4) = '0000'";
		logger.info("sql:"+sql);
		return changeToAccount(jdbcTemplate.queryForMap(sql));
	}
	/** TODO:  医养中心的缴款，直接缴到支出户，省去拨付的流程
	 * @Author Mr.lx
	 * @Date 2019/4/4 0004
	* @param accCode
	 * @return com.untech.mcrcb.web.model.WjwAccount
	 **/
	public WjwAccount findWjwOutAccount(int accCode){
		String sql = "SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	WJW_ACCOUNT\n" +
				"WHERE\n" +
				"	in_or_out = 2\n" +
				"AND ACC_FLD = 2\n" +
				"AND ACCOUNT_SYS_CODE ='"+accCode+"'\n" +
				"AND RIGHT (ACC_NO, 4) = '0000'";
		logger.info("sql:"+sql);
		return changeToAccount(jdbcTemplate.queryForMap(sql));
	}
	
	/**
	 * 获得卫计委支出虚拟账户
	 * @return
	 */
	public WjwAccount findWjwXNOutAccount(String accCode){
		String sql = "select * from WJW_ACCOUNT where in_or_out=2 and ACC_FLD=2 and RIGHT(ACC_NO,4) = '0000'  AND ACCOUNT_SYS_CODE="+accCode+"";
		logger.info("sql:"+sql);
		return changeToAccount(jdbcTemplate.queryForMap(sql));
	}
	
	/**
	 * 将map转换成账户
	 * @param map
	 * @return
	 */
	public WjwAccount changeToAccount(Map<String,Object> map){
		WjwAccount account = new WjwAccount();
		account.setId(Long.parseLong(( map.get("ID")+"")));
		account.setAccNo((String) map.get("ACC_NO"));
		account.setCustName((String) map.get("CUST_NAME"));
		account.setAccType((Integer) map.get("ACC_TYPE"));
		account.setAccCode((Integer) map.get("ACCOUNT_SYS_CODE"));
		account.setUnitNo((String) map.get("UNIT_NO"));
		account.setUnitName((String) map.get("UNIT_NAME"));
		account.setAmount((BigDecimal) map.get("AMOUNT"));
		account.setAccNum((BigDecimal) map.get("ACC_NUM"));
		account.setAccStatus((Integer) map.get("STATUS"));
		account.setAccParent((String) map.get("ACC_PARENT"));
		account.setCreateTime((String) map.get("CREATE_TIME"));
		account.setCreateUser((String) map.get("CREATE_USER"));
		account.setUpdateTime((String) map.get("UPDATE_TIME"));
		account.setUpdateUser((String) map.get("UPDATE_USER"));
		account.setLevel((Integer) map.get("LEVEL"));
		account.setRate((BigDecimal) map.get("RATE"));
		account.setBankUnit((String) map.get("BANK_UNIT"));
		account.setBankName((String) map.get("BANK_NAME"));
		account.setBankAmount((BigDecimal) map.get("BANK_AMOUNT"));
		account.setUnkCome((BigDecimal) map.get("UNK_COME"));
		account.setIntCome((BigDecimal) map.get("INT_COME"));
		account.setCurrency((String) map.get("CURRENCY"));
		account.setAccFld((Integer) map.get("ACC_FLD"));
		account.setAccPro((Integer) map.get("ACC_PRO"));
		account.setIntFlag((Integer) map.get("INT_FLAG"));
		account.setIntType((Integer) map.get("INT_TYPE"));
		account.setInOrOut((Integer) map.get("IN_OR_OUT"));
		account.setDrugAmt((BigDecimal) map.get("DRUG_AMT"));
		account.setMedicalAmt((BigDecimal) map.get("MEDICAL_AMT"));
		account.setOtherAmt((BigDecimal) map.get("OTHER_AMT"));
		account.setNote1((String) map.get("NOTE1"));
		account.setNote2((String) map.get("NOTE2"));
		return account;
	}

	public Pagination<Map<String, Object>> getBfHzbList(Integer start, Integer limit, String operNo, String startTime,
			String endTime) {
		String sql = "select UNIT_NO as \"unitNo\",UNIT_NAME as \"unitName\",BF_AMT as \"bfAmt\","
				+ "BF_DRUG_AMT as \"bfDrugAmt\",BF_MEDC_AMT as \"bfMedcAmt\",BF_TIM as \"bfTim\", "
				+ "ID as \"id\",OPER_NO as \"operNo\",OPER_TIME as \"operTime\",DETAIL as \"detail\","
				+ "CONN_NO as \"connNo\",REMARK as \"remark\",NOTE1 as \"note1\",NOTE2 as \"note2\", "
				+ "OUT_ACCNO as \"outAccno\",OUT_ACCNAME as \"outAccname\",OUT_BANK as \"outBank\","
				+ "IN_ACCNO as \"inAccno\", IN_NAME as \"inName\",IN_BANK as \"inBank\","
				+ "CERT_NO as \"certNo\", XN_ACCTNO as \"xnAcctno\",XN_ACCTNAME as \"xnAcctName\","
				+ "ZC_ACCTNO as \"zcAcctno\", ZC_ACCTNAME as \"zcAcctname\",STATUS as \"status\" "
				+ "from WJW_BFHZB where 1=1 ";
		if(StringUtils.hasText(operNo)){
			sql += "  and OPER_NO ='"+operNo+"'";
		}
		if(StringUtils.hasText(startTime)){
			sql += "  and OPER_TIME >='"+startTime+"'";
		}
		if(StringUtils.hasText(endTime)){
			sql += "  and left(OPER_TIME,8) <='"+endTime+"'";
		}
		sql += "  ORDER BY BF_TIM DESC";
		logger.info("sql:"+sql);
		return jdbcPager.queryPage(sql, start, limit);
	}
	
	/**
	 * 获得凭证最大序列号
	 * @return
	 */
	public int getMaxCode() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String date = format.format(new Date())+"03";
		String sql = "select max(substr(CERT_NO,length(CERT_NO)-2)) from WJW_BFHZB where CERT_NO like '"+date+"%'";
	//	int num = this.jdbcTemplate.queryForInt(sql);
		logger.info("sql:"+sql);
		Number number = this.jdbcTemplate.queryForObject(sql,Integer.class);
		return (number != null ? number.intValue() : 0);
	}

	public void deleteIncome(Long id) {
		String sql = "update WJW_BFHZB set STATUS = 4 where ID = "+id;
		logger.info("sql:"+sql);
		jdbcTemplate.update(sql);
	}

	public String getCertNo(Long id) {
		String sql ="select CERT_NO as \"certNo\" from WJW_BFHZB where ID="+id;
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForObject(sql, String.class);
	}

	public Map<String, Object> queryAppropriateByVoucher(String voucher) {
		String sql = "select OUT_ACCNO as \"OUT_ACCNO\", OUT_ACCNAME as \"OUT_ACCNAME\", OUT_BANK as \"OUT_BANK\", "
				+ "IN_ACCNO as \"IN_ACCNO\", IN_NAME as \"IN_NAME\", IN_BANK as \"IN_BANK\", UNIT_NAME as \"UNIT_NAME\","
				+ " BF_AMT as \"AMOUNT\", STATUS as \"STATUS\" from wjw_bfhzb "
				+ "where CERT_NO ='"+voucher+"'";
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql).size()==0?null:jdbcTemplate.queryForList(sql).get(0);
	}

	public List<Map<String, Object>> appropriatePrint(Long id) {
		String sql = "select UNIT_NO as \"unitNo\",UNIT_NAME as \"unitName\",BF_AMT as \"amount\","
				+ "BF_DRUG_AMT as \"bfDrugAmt\",BF_MEDC_AMT as \"bfMedcAmt\",BF_TIM as \"bfTim\", "
				+ "ID as \"id\",OPER_NO as \"operNo\",OPER_TIME as \"operTime\",DETAIL as \"detail\","
				+ "CONN_NO as \"connNo\",REMARK as \"remark\",NOTE1 as \"note1\",NOTE2 as \"note2\", "
				+ "OUT_ACCNO as \"outAccno\",OUT_ACCNAME as \"outAccname\",OUT_BANK as \"outBank\","
				+ "IN_ACCNO as \"inAccno\", IN_NAME as \"inName\",IN_BANK as \"inBank\","
				+ "CERT_NO as \"certNo\", XN_ACCTNO as \"xnAcctno\",XN_ACCTNAME as \"xnAcctName\","
				+ "ZC_ACCTNO as \"zcAcctno\", ZC_ACCTNAME as \"zcAcctname\",STATUS as \"status\" "
				+ "from WJW_BFHZB where ID="+id;
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql);
	}


}

