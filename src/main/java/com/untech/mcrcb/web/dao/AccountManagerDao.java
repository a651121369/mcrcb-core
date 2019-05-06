package com.untech.mcrcb.web.dao;


import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.untech.mcrcb.web.model.WjwAccount;

import com.untech.mcrcb.web.enhance.BaseDao;
import com.unteck.common.dao.support.Pagination;

/**
 * mcrcb
 * WJW_ACCOUNT DAO
 *
 * @author chenyong
 * @since 2015-11-04
 * <p>
 * TODO:代码基本不是我写的，我写不出这么lowB的代码，我接手维护
 */
@Repository
public class AccountManagerDao extends BaseDao<WjwAccount, Long> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Pagination<Map<String, Object>> fetchPager(String acctNo, String custName,
                                                      Integer start, Integer limit) {
        String sql = "SELECT ID as \"id\", ACC_NO as \"accNo\" , CUST_NAME as \"custName\", ACCOUNT_SYS_CODE as \"accCode\", ACC_TYPE as \"accType\", UNIT_NO as \"unitNo\", UNIT_NAME as \"unitName\"," +
                "AMOUNT as \"amount\", ACC_NUM as \"accNum\", ACC_STATUS as \"accStatus\", ACC_PARENT as \"accParent\", " +
                "CREATE_TIME as \"createTime\", CREATE_USER as \"createUser\", UPDATE_TIME as \"updateTime\", UPDATE_USER as \"updateUser\"," +
                " LEVEL as \"level\", RATE as \"rate\", BANK_UNIT as \"bankUnit\", BANK_NAME as \"bankName\", BANK_AMOUNT as \"bankAmount\"," +
                "UNK_COME as \"unkCome\", INT_COME as \"intCome\", CURRENCY as \"currency\", ACC_FLD as \"accFld\", ACC_PRO as \"accPro\", " +
                "INT_FLAG as \"intFlag\", INT_TYPE as \"intType\", IN_OR_OUT as \"inOrOut\", DRUG_AMT as \"drugAmt\", MEDICAL_AMT as \"medicalAmt\"," +
                " OTHER_AMT as \"otherAmt\" , NOTE1 as \"note1\", NOTE2 as \"note1\" FROM WJW_ACCOUNT where (ACC_FLD=1 or ACC_NO like '%0000' or ACC_NO like '%9999' ) ";

        if (!"".equals(acctNo) && !"null".equals(acctNo) && acctNo != null) {
            sql += "  and ACC_NO like '%" + acctNo + "%'";
        }
        if (!"".equals(custName) && !"null".equals(custName) && custName != null) {
            sql += "  and CUST_NAME like '%" + custName + "%'";
        }
        logger.info("sql:"+sql);
        return jdbcPager.queryPage(sql, start, limit);
    }

    /**
     * TODO: 查询子账号
     *
     * @return com.unteck.common.dao.support.Pagination<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author Mr.lx
     * @Date 2019/3/28 0028
     **/
    public Pagination<Map<String, Object>> subfetchPager(String acctNo, String custName,
                                                         Integer start, Integer limit) {
        StringBuffer dynamicSql = this.subfetchPagerSql(acctNo, custName);
        logger.info("dynamicSql:"+dynamicSql.toString());
        return jdbcPager.queryPage(dynamicSql.toString(), start, limit);
    }

    /**
     * TODO: 查询子账号
     * @return com.unteck.common.dao.support.Pagination<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author Mr.lx
     * @Date 2019/3/28 0028
     **/
    public Pagination<Map<String, Object>> subfetchPagerByaccCode(String acctNo, String custName, String accCode,
                                                                  Integer start, Integer limit) {
        StringBuffer dynamicSql = this.subfetchPagerSql(acctNo, custName);
        if (StringUtils.isNotBlank(accCode)) {
            dynamicSql.append("  and ACCOUNT_SYS_CODE = '" + accCode + "'");
        }
        logger.info("dynamicSql:"+dynamicSql.toString());
        return jdbcPager.queryPage(dynamicSql.toString(), start, limit);
    }

    /**
     * TODO: 重构了查询子账户方法，在其主方法体上进行更改
     * @param acctNo   账号
     * @param custName 账号名称
     * @return java.lang.StringBuffer
     * @Author Mr.lx
     * @Date 2019/3/28 002 8
     **/
    private StringBuffer subfetchPagerSql(String acctNo, String custName) {
        StringBuffer dynamicSql = new StringBuffer();
        String sql = "SELECT ID as \"id\", ACC_NO as \"accNo\" , CUST_NAME as \"custName\",ACCOUNT_SYS_CODE as \"accCode\",  ACC_TYPE as \"accType\", UNIT_NO as \"unitNo\", UNIT_NAME as \"unitName\"," +
                "AMOUNT as \"amount\", ACC_NUM as \"accNum\", ACC_STATUS as \"accStatus\", ACC_PARENT as \"accParent\", " +
                "CREATE_TIME as \"createTime\", CREATE_USER as \"createUser\", UPDATE_TIME as \"updateTime\", UPDATE_USER as \"updateUser\"," +
                " LEVEL as \"level\", RATE as \"rate\", BANK_UNIT as \"bankUnit\", BANK_NAME as \"bankName\", BANK_AMOUNT as \"bankAmount\"," +
                "UNK_COME as \"unkCome\", INT_COME as \"intCome\", CURRENCY as \"currency\", ACC_FLD as \"accFld\", ACC_PRO as \"accPro\", " +
                "INT_FLAG as \"intFlag\", INT_TYPE as \"intType\", IN_OR_OUT as \"inOrOut\", DRUG_AMT as \"drugAmt\", MEDICAL_AMT as \"medicalAmt\"," +
                " OTHER_AMT as \"otherAmt\" , NOTE1 as \"note1\", NOTE2 as \"note1\" FROM WJW_ACCOUNT where ACC_FLD=2 and  ACC_NO not like '%9999' and  ACC_NO not like '%0000'";
        dynamicSql.append(sql);
        if (!"".equals(acctNo) && !"null".equals(acctNo) && acctNo != null) {
            dynamicSql.append("  and ACC_NO like '%" + acctNo + "%'");
        }
        if (!"".equals(custName) && !"null".equals(custName) && custName != null) {
            dynamicSql.append("  and CUST_NAME like '%" + custName + "%'");
        }
        return dynamicSql;
    }


    public List<Map<String, Object>> gettopOrg(String id) {
        String sql = "SELECT ID as \"id\",NAME  as \"name\",DESCN as \"descn\" FROM  UNTECK_ORGANIZATION WHERE PARENTID = 0 ";
        logger.info("sql:"+sql);
        return this.jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getSubOrg() {
        String sql = "SELECT ID  as \"orgNo\" , name as \"orgName\",DESCN as \"descn\" FROM  UNTECK_ORGANIZATION WHERE PARENTID <> 0 ";
        logger.info("sql:"+sql);
        return this.jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> check(String id) {
        String sql = "SELECT * FROM  WJW_ACCOUNT WHERE ACC_FLD=2 and IN_OR_OUT=" + id + " and ACC_NO like '%0000'";
        logger.info("sql:"+sql);
        return this.jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getMainAcct(String type) {
        String sql = "SELECT ACC_NO as \"accNo\" , CUST_NAME as \"custName\" FROM  WJW_ACCOUNT WHERE ACC_FLD=2 and IN_OR_OUT=" + type + " and ACC_NO like '%0000'";
        logger.info("sql:"+sql);
        return this.jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getMainAccoutByCode(int code, int type) {
//		String sql = "SELECT ACC_NO as "accNo" , CUST_NAME as "custName" FROM  WJW_ACCOUNT WHERE ACC_FLD=2 and ACCOUNT_SYS_CODE="+code+" and IN_OR_OUT="+type+" and ACC_NO like '%0000'";
        String sql = "SELECT\n" +
                "	ACC_NO AS accNo,\n" +
                "	CUST_NAME AS custName\n" +
                "FROM \n" +
                "	WJW_ACCOUNT \n" +
                "WHERE \n" +
                "	ACC_FLD = 2 \n" +
                "AND ACCOUNT_SYS_CODE =" + code + " \n" +
                "AND IN_OR_OUT =" + type + " \n" +
                "AND ACC_NO LIKE '%0000'";
        logger.info("sql:"+sql);
        return this.jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getOrgInfo(String type) {
        String sql = "SELECT id as \"orgNo\" , name as \"orgName\" FROM  UNTECK_ORGANIZATION where 1=1 ";
        if (!"".equals(type)) {
            sql = sql + " and id = " + type;
        }
        sql = sql + " order by id asc";
        logger.info("sql:"+sql);
        return this.jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getMainAcctNo() {
        String sql = "SELECT ACC_NO as \"acctId\",CUST_NAME as \"acctName\" FROM" +
                "  wjw_ACCOUNT WHERE ACC_FLD=2 and  ACC_NO like '%0000'";
        logger.info("sql:"+sql);
        return this.jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getSubAcctNo(String acctNo) {
        String sql = "SELECT ACC_NO as \"acctId\",CUST_NAME as \"acctName\" FROM" +
                "  wjw_ACCOUNT WHERE ACC_FLD=2 and  ACC_PARENT ='" + acctNo + "'";
        logger.info("sql:"+sql);
        return this.jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getAcctNoByOrg(String orgNo) {
        String sql = "SELECT ACC_NO as \"acctId\",CUST_NAME as \"acctName\" FROM" +
                "  wjw_ACCOUNT WHERE ACC_FLD=2 and  UNIT_NO ='" + orgNo + "'";
        logger.info("sql:"+sql);
        return this.jdbcTemplate.queryForList(sql);
    }

}

