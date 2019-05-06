package com.untech.mcrcb.web.dao;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.untech.mcrcb.web.common.Constants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.untech.mcrcb.web.model.WjwAccount;
import com.untech.mcrcb.web.enhance.BaseDao;
import com.unteck.common.dao.jdbc.NamedParameterJdbcPager;
import com.unteck.common.dao.support.Pagination;

/**
 * mcrcb
 * WJW_ACCOUNT DAO
 *
 * @author chenyong
 * @since 2015-11-04
 */
@Repository
public class WjwAccountDao extends BaseDao<WjwAccount, Long> {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcPager jdbcPager;

    public Pagination<Map<String, Object>> findDqfAccounts(Integer start, Integer limit) {
        String sql = "select ID as \"id\",ACC_NO as \"accNo\",CUST_NAME as \"custName\",ACC_TYPE as \"accType\",UNIT_NO as \"unitNo\"," +
                "UNIT_NAME as \"unitName\",coalesce(CAST(AMOUNT as decimal(21,2)),0) as \"amount\",coalesce(CAST(ACC_NUM as decimal(21,2)),0) as \"accNum\",ACC_STATUS as \"accStatus\",ACC_PARENT as \"accParent\"," +
                "CREATE_TIME as \"createTime\",CREATE_USER as \"createUser\",UPDATE_TIME as \"updateTime\",UPDATE_USER as \"updateUser\"," +
                "LEVEL as \"level\",RATE as \"rate\",BANK_UNIT as \"bankUnit\",BANK_NAME as \"bankName\",BANK_AMOUNT as \"bankAmount\"," +
                "coalesce(CAST(UNK_COME as decimal(21,2)),0) as \"unkCome\",coalesce(CAST(INT_COME as decimal(21,2)),0) as \"intCome\",currency as \"currency\",ACC_FLD  as \"accFld\",ACC_PRO  as \"accPro\"," +
                "INT_FLAG  as \"intFlag\",INT_TYPE as \"intType\",IN_OR_OUT as \"inOrOut\",coalesce(CAST(DRUG_AMT as decimal(21,2)),0) as \"drugAmt\",coalesce(CAST(MEDICAL_AMT as decimal(21,2)),0) as \"medicalAmt\","
                + "coalesce(CAST(OTHER_AMT as decimal(21,2)),0) as \"otherAmt\" from WJW_ACCOUNT where 1=1 " +
                "and right(acc_no,4)='9999'";
        logger.info("sql:" + sql);

        return jdbcPager.queryPage(sql, start, limit);
    }

    /**
     * 查找待清分子账户
     *
     * @param accNo
     * @param parent
     * @return
     */
    public List<Map<String, Object>> findDqfChildren(String accNo, String parent) {
        String sql = "select ID as \"id\",ACC_NO as \"accNo\",CUST_NAME as \"custName\",ACC_TYPE as \"accType\",UNIT_NO as \"unitNo\"," +
                "UNIT_NAME as \"unitName\",coalesce(CAST(AMOUNT as decimal(21,2)),0) as \"amount\",coalesce(CAST(ACC_NUM as decimal(21,2)),0) as \"accNum\",ACC_STATUS as \"accStatus\",ACC_PARENT as \"accParent\"," +
                "CREATE_TIME as \"createTime\",CREATE_USER as \"createUser\",UPDATE_TIME as \"updateTime\",UPDATE_USER as \"updateUser\"," +
                "LEVEL as \"level\",RATE as \"rate\",BANK_UNIT as \"bankUnit\",BANK_NAME as \"bankName\",BANK_AMOUNT as \"bankAmount\"," +
                "coalesce(CAST(UNK_COME as decimal(21,2)),0) as \"unkCome\",coalesce(CAST(INT_COME as decimal(21,2)),0) as \"intCome\",currency as \"currency\",ACC_FLD  as \"accFld\",ACC_PRO  as \"accPro\"," +
                "INT_FLAG  as \"intFlag\",INT_TYPE as \"intType\",IN_OR_OUT as \"inOrOut\",coalesce(CAST(DRUG_AMT as decimal(21,2)),0) as \"drugAmt\",coalesce(CAST(MEDICAL_AMT as decimal(21,2)),0) as \"medicalAmt\","
                + "coalesce(CAST(OTHER_AMT as decimal(21,2)),0) as \"otherAmt\" from WJW_ACCOUNT where 1=1 " +
                " and ACC_PARENT ='" + parent + "'" +
                " and acc_no !='" + accNo + "'";
        logger.info("sql:" + sql);
        return jdbcTemplate.queryForList(sql);
    }

    public Map<String, Object> findByAccNo(String accNo) {
        System.out.println(accNo);
        String sql = "select * from WJW_ACCOUNT where acc_no ='" + accNo + "'";
        logger.info("sql:" + sql);
        return jdbcTemplate.queryForMap(sql);
    }

    public void updateAccount(BigDecimal amount, BigDecimal drugAmt, BigDecimal medicalAmt, String accNo) {
        String sql = "update WJW_ACCOUNT set AMOUNT = " + amount + ",DRUG_AMT = " + drugAmt + ",MEDICAL_AMT = " + medicalAmt + " where ACC_NO = '" + accNo + "'";
        logger.info("sql:" + sql);
        jdbcTemplate.update(sql);
    }

    //	public WjwAccount findByAccNo(String accNo) {
//		System.out.println(accNo);
//		String sql = "select * from WJW_ACCOUNT where acc_no ='"+accNo+"'";
//		Map<String,Object> map = jdbcTemplate.queryForMap(sql);
//		WjwAccount account = new WjwAccount();
//		account.setId(Long.parseLong(( map.get("ID")+"")));
//		account.setAccNo((String) map.get("ACC_NO"));
//		account.setCustName((String) map.get("CUST_NAME"));
//		account.setAccType((Integer) map.get("ACC_TYPE"));
//		account.setUnitNo((String) map.get("UNIT_NO"));
//		account.setUnitName((String) map.get("UNIT_NAME"));
//		account.setAmount((BigDecimal) map.get("AMOUNT"));
//		account.setAccNum((BigDecimal) map.get("ACC_NUM"));
//		account.setAccStatus((Integer) map.get("ACC_STATUS"));
//		account.setAccParent((String) map.get("ACC_PARENT"));
//		account.setCreateTime((String) map.get("CREATE_TIME"));
//		account.setCreateUser((String) map.get("CREATE_USER"));
//		account.setUpdateTime((String) map.get("UPDATE_TIME"));
//		account.setUpdateUser((String) map.get("UPDATE_USER"));
//		account.setLevel((Integer) map.get("LEVEL"));
//		account.setRate((BigDecimal) map.get("RATE"));
//		account.setBankUnit((String) map.get("BANK_UNIT"));
//		account.setBankName((String) map.get("BANK_NAME"));
//		account.setBankAmount((BigDecimal) map.get("BANK_AMOUNT"));
//		account.setUnkCome((BigDecimal) map.get("UNK_COME"));
//		account.setIntCome((BigDecimal) map.get("INT_COME"));
//		account.setCurrency((String) map.get("CURRENCY"));
//		account.setAccFld((Integer) map.get("ACC_FLD"));
//		account.setAccPro((Integer) map.get("ACC_PRO"));
//		account.setIntFlag((Integer) map.get("INT_FLAG"));
//		account.setIntType((Integer) map.get("INT_TYPE"));
//		account.setInOrOut((Integer) map.get("IN_OR_OUT"));
//		account.setDrugAmt((BigDecimal) map.get("DRUG_AMT"));
//		account.setMedicalAmt((BigDecimal) map.get("MEDICAL_AMT"));
//		account.setOtherAmt((BigDecimal) map.get("OTHER_AMT"));
//		account.setNote1((String) map.get("NOTE1"));
//		account.setNote2((String) map.get("NOTE2"));
//		return account;
//	}
    public Pagination<Map<String, Object>> findMainAccounts(Integer start,
                                                            Integer limit) {
        String sql = "select ID as \"id\",ACC_NO as \"accNo\",CUST_NAME as \"custName\",ACC_TYPE as \"accType\",UNIT_NO as \"unitNo\"," +
                "UNIT_NAME as \"unitName\",coalesce(CAST(AMOUNT as decimal(21,2)),0) as \"amount\",coalesce(CAST(ACC_NUM as decimal(21,2)),0) as \"accNum\",ACC_STATUS as \"accStatus\",ACC_PARENT as \"accParent\"," +
                "CREATE_TIME as \"createTime\",CREATE_USER as \"createUser\",UPDATE_TIME as \"updateTime\",UPDATE_USER as \"updateUser\"," +
                "LEVEL as \"level\",RATE as \"rate\",BANK_UNIT as \"bankUnit\",BANK_NAME as \"bankName\",BANK_AMOUNT as \"bankAmount\"," +
                "coalesce(CAST(UNK_COME as decimal(21,2)),0) as \"unkCome\",coalesce(CAST(INT_COME as decimal(21,2)),0) as \"intCome\",currency as \"currency\",ACC_FLD  as \"accFld\",ACC_PRO  as \"accPro\"," +
                "INT_FLAG  as \"intFlag\",INT_TYPE as \"intType\",IN_OR_OUT as \"inOrOut\",coalesce(CAST(DRUG_AMT as decimal(21,2)),0) as \"drugAmt\",coalesce(CAST(MEDICAL_AMT as decimal(21,2)),0) as \"medicalAmt\","
                + "coalesce(CAST(OTHER_AMT as decimal(21,2)),0) as \"otherAmt\" from WJW_ACCOUNT where 1=1 " +
                "and  right(acc_no,4) = '0000' ";
        logger.info("sql:" + sql);
        return jdbcPager.queryPage(sql, start, limit);

    }

    public List<Map<String, Object>> findMain() {
        String sql = "select ACC_NO as \"accNo\",CUST_NAME as \"custName\"  from WJW_ACCOUNT where 1=1 " +
                " and right(acc_no,4) ='0000' ";
        logger.info("sql:" + sql);
        return jdbcTemplate.queryForList(sql);

    }

    public List<Map<String, Object>> findMainForInterest() {
        String sql = "select ACC_NO as \"accNo\",CUST_NAME as \"custName\",(INT_COME-coalesce(b.amt,0)) as \"intCome\"  from WJW_ACCOUNT a left join (select OUT_ACCNO, SUM(AMOUNT) as amt from WJW_INTEREST WHERE STATUS=5 GROUP BY OUT_ACCNO) b on a.ACC_NO = b.OUT_ACCNO where 1=1 " +
                " and right(a.acc_no,4) ='0000'";
        logger.info("sql:" + sql);
        return jdbcTemplate.queryForList(sql);

    }

    public void updateZmoney(BigDecimal Amount, BigDecimal DrugAmt, BigDecimal MedicalAmt, BigDecimal otherAmt, String Acctno) {
        String upMoneySql = "update wjw_ACCOUNT set AMOUNT = " + Amount + ",DRUG_AMT = " + DrugAmt + ",MEDICAL_AMT = " + MedicalAmt + ",OTHER_AMT = " + otherAmt + " where ACC_NO = '" + Acctno + "'";
        System.out.println(upMoneySql);
        jdbcTemplate.update(upMoneySql);
    }

    public List<Map<String, Object>> findDqfAccs() {
        //默认待清分只能添加医养中心的
        String sql = "select ACC_NO as \"accNo\",CUST_NAME as \"custName\" from WJW_ACCOUNT where 1=1 " +
                " and right(acc_no,4) ='9999' and ACCOUNT_SYS_CODE ='2' ";
        logger.info("sql:" + sql);
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> QueryAccount(String tallyType) {
//		String sql = "select ACC_NO as \"ACC_NO\",CUST_NAME as \"CUST_NAME\","+       
//				"UNIT_NAME as \"UNIT_NAME\",AMOUNT  as \"AMOUNT\","+        
//				"DRUG_AMT  as \"DRUG_AMT\",MEDICAL_AMT as \"MEDICAL_AMT\",OTHER_AMT as \"OTHER_AMT\" "+
//				"from WJW_ACCOUNT where ACC_PARENT ='"+accountNo+"'"+
//				" and IN_OR_OUT ='"+tallyType+"'"+" and right(ACC_NO,4) !='9999' ";
        String sql = "select ACC_NO as \"ACC_NO\",CUST_NAME as \"CUST_NAME\"," +
                "UNIT_NAME as \"UNIT_NAME\",AMOUNT  as \"AMOUNT\"," +
                "DRUG_AMT  as \"DRUG_AMT\",MEDICAL_AMT as \"MEDICAL_AMT\",OTHER_AMT as \"OTHER_AMT\" " +
                "from WJW_ACCOUNT where ACC_FLD=2 "
                + " and IN_OR_OUT =" + tallyType;
        logger.info("sql:" + sql);
        return jdbcTemplate.queryForList(sql);
    }

    public Map<String, Object> QueryAccount(String tallyType, String accountNo) {
        String sql = "select ACC_NO as \"ACC_NO\",CUST_NAME as \"CUST_NAME\"," +
                "UNIT_NAME as \"UNIT_NAME\",AMOUNT  as \"AMOUNT\"," +
                "DRUG_AMT  as \"DRUG_AMT\",MEDICAL_AMT as \"MEDICAL_AMT\",OTHER_AMT as \"OTHER_AMT\" " +
                "from WJW_ACCOUNT where ACC_NO='" + accountNo + "' "
                + " and IN_OR_OUT =" + tallyType;
        logger.info("sql:" + sql);
        return jdbcTemplate.queryForList(sql).size() == 0 ? null : jdbcTemplate.queryForList(sql).get(0);

    }

    public Map<String, Object> queryBankAccount() {
//		String sql = "select Max(case when ACC_FLD=1 and IN_OR_OUT=1 then ACC_NO else '' end) as \"IN_ACCNO\","
//		+ "Max(case when ACC_FLD=1 and IN_OR_OUT=1 then CUST_NAME else '' end) as \"IN_ACCNAME\","
//		+ "Max(case when ACC_FLD=1 and IN_OR_OUT=1 then AMOUNT else 0 end) as \"IN_AMT\","
//		+ "Max(case when ACC_FLD=1 and IN_OR_OUT=2 then ACC_NO else '' end) as \"OUT_ACCNO\","
//		+ "Max(case when ACC_FLD=1 and IN_OR_OUT=2 then CUST_NAME else '' end) as \"OUT_ACCNAME\","
//		+ "Max(case when ACC_FLD=1 and IN_OR_OUT=2 then AMOUNT else 0 end) as \"OUT_AMT\" "
//		+"from WJW_ACCOUNT";
        String sql = "select Max(case when ACC_FLD=1 and IN_OR_OUT=1 then ACC_NO else '' end) as \"IN_ACCNO\","
                + "Max(case when ACC_FLD=1 and IN_OR_OUT=1 then CUST_NAME else '' end) as \"IN_ACCNAME\","
                + "Max(case when ACC_FLD=1 and IN_OR_OUT=2 then ACC_NO else '' end) as \"OUT_ACCNO\","
                + "Max(case when ACC_FLD=1 and IN_OR_OUT=2 then CUST_NAME else '' end) as \"OUT_ACCNAME\" "
                + "from WJW_ACCOUNT WHERE 1=1 AND ACCOUNT_SYS_CODE=1";
        logger.info("sql:" + sql);
        return jdbcTemplate.queryForMap(sql);
    }

    public List<Map<String, Object>> findAllAccounts(String unitNo) {
        String sql = "select ACC_NO as \"accNo\",CUST_NAME as \"accName\" from WJW_ACCOUNT where  ACC_FLD=2";
        sql = unitNo == "" ? sql : sql + " and UNIT_NO='" + unitNo + "'";
        logger.info("sql:" + sql);
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> findAllAccountsByAccCode(String unitNo,int accCode) {
        String sql = "select ACC_NO as \"accNo\",CUST_NAME as \"accName\" from WJW_ACCOUNT where  ACC_FLD=2";
        sql = unitNo == "" ? sql : sql + " and UNIT_NO='" + unitNo + "'";
        sql +=" and  ACCOUNT_SYS_CODE="+accCode+"";
        logger.info("sql:" + sql);
        return jdbcTemplate.queryForList(sql);
    }

    public Map<String, Object> getOrganization(String orgId) {
        String sql = "select ID as code,NAME as name,PARENTID from UNTECK_ORGANIZATION";
        sql = (orgId == "" ? sql : (sql + " where ID=" + orgId));
        return jdbcTemplate.queryForList(sql).get(0);
    }

    public Map<String, Object> findDqfAccount(String inOrOut) {
        String sql = "select * from wjw_account where IN_OR_OUT=" + inOrOut + " and right(ACC_NO,4)='9999'";
        logger.info("sql:" + sql);
        return jdbcTemplate.queryForList(sql).size() == 0 ? null : jdbcTemplate.queryForList(sql).get(0);
    }

    public Map<String, Object> findMainAccount(String inOrOut) {
        String sql = "select * from wjw_account where IN_OR_OUT=" + inOrOut + " and right(ACC_NO,4)='0000'";
        logger.info("sql:" + sql);
        return jdbcTemplate.queryForList(sql).size() == 0 ? null : jdbcTemplate.queryForList(sql).get(0);
    }

    /** TODO: ---根据虚拟账号查询类型
     * @Author Mr.lx
     * @Date 2019/4/10 0010
    * @param accNo 虚拟账号
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> findAccountByAccNo(String accNo) {
        String sql = "select * from wjw_account where ACC_NO=" + accNo;
        logger.info("sql:" + sql);
        return jdbcTemplate.queryForList(sql).size() == 0 ? null : jdbcTemplate.queryForList(sql).get(0);
    }

    public void updateLx(String accNo, BigDecimal intCome) {
        String sql = "update wjw_account set INT_COME=" + intCome + " where ACC_NO='" + accNo + "'";
        logger.info("sql:" + sql);
        jdbcTemplate.execute(sql);
    }
}

