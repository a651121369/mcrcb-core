package com.untech.mcrcb.web.dao;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.untech.mcrcb.web.model.WjwPaydetail;
import com.untech.mcrcb.web.enhance.BaseDao;
import com.unteck.common.dao.jdbc.NamedParameterJdbcPager;
import com.unteck.common.dao.support.Pagination;

/**
 * WJW_PAYDETAIL DAO
 *
 * @author chenyong
 * @since 2015-11-04
 */
@Repository
public class WjwPaydetailDao extends BaseDao<WjwPaydetail, Long> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcPager jdbcPager;

    public List<Map<String, Object>> getOutAccList(Long orgId, Integer inOrOut,
                                                   Integer accFld, boolean accNo) {
        StringBuffer dynamicSql = subfetchAccListSql(orgId, inOrOut, accFld, accNo);
        return jdbcTemplate.queryForList(dynamicSql.toString());
    }

    /**
     * TODO:
     *
     * @param orgId   组织机构id
     * @param inOrOut 收入或支出
     * @param accFld  是否虚拟
     * @param accNo   账号
     * @param accCode 账户体系
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author Mr.lx
     * @Date 2019/4/3 0003
     **/
    public List<Map<String, Object>> getOutAccList(Long orgId, Integer inOrOut,
                                                   Integer accFld, boolean accNo, int accCode) {
        StringBuffer dynamicSql = subfetchAccListSql(orgId, inOrOut, accFld, accNo);
        dynamicSql.append(" AND ACCOUNT_SYS_CODE=" + accCode + "");
        logger.info("dynamicSql==" + dynamicSql.toString());
        return jdbcTemplate.queryForList(dynamicSql.toString());
    }

    /**
     * TODO: ---拼接sql语句
     *
     * @return java.lang.StringBuffer
     * @Author Mr.lx
     * @Date 2019/4/3 0003
     **/
    private StringBuffer subfetchAccListSql(Long orgId, Integer inOrOut,
                                            Integer accFld, boolean accNo) {
        StringBuffer dynamicSql = new StringBuffer();
        String sql = "select ACC_NO as accNo,CUST_NAME as custName,"
                + "UNIT_NAME as unitName from WJW_ACCOUNT ";
        dynamicSql.append(sql);
        dynamicSql.append(" where 1=1");
        dynamicSql.append(" AND IN_OR_OUT=" + inOrOut + "");
        dynamicSql.append(" AND ACC_FLD=" + accFld + "");
        if (accNo) {
            dynamicSql.append(" AND right(ACC_NO,4)='0000'");
        } else {
            dynamicSql.append(" AND UNIT_NO=" + orgId + "");
        }
        return dynamicSql;
    }


    public List<Map<String, Object>> getPayAccInfo(Long orgId, Integer inOrOut,
                                                   Integer accFld) {
        String sql = "select (a.amount-coalesce(b.amt,0)) as \"accAmount\",(a.drug_amt-coalesce(b.dgAmt,0)) as \"drugAmt\",(a.medical_amt-coalesce(b.mdAmt,0)) as \"medcAmt\"  from  wjw_account a left join   "
                + "(select sum(amount) as amt,sum(case when item = 1 then amount else 0 end) as mdAmt, sum(case when item = 2 then amount else 0 end) as dgAmt,unit_no as unit_no  from wjw_paydetail where coalesce(back_flg,0)!=1 and status in(1,2,3,5) group by unit_no) b  on a.unit_no = b.unit_no "
                + "where a.unit_no = '" + orgId + "' and a.acc_fld=" + accFld + " and a.in_or_out =" + inOrOut;
//		String sql = "select ACC_NO as accNo,CUST_NAME as custName, AMOUNT as amount,MEDICAL_AMT as medcAmt,DRUG_AMT as drugAmt,"
//				+ "UNIT_NAME as unitName from WJW_ACCOUNT where  IN_OR_OUT="+inOrOut+" and ACC_FLD="+accFld
//				+" and UNIT_NO='" +orgId+"'";
        logger.info("sql:" + sql);
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getPayAccInfoByAcc(Long orgId, Integer inOrOut,
                                                        Integer accFld) {
        String sql = "SELECT\n" +
                "acc.ID,\n" +
                "acc.ACC_NO,\n" +
                "acc.CUST_NAME,\n" +
                "acc.AMOUNT  AS accAmount,\n" +
                "acc.DRUG_AMT AS drugAmt,\n" +
                "acc.MEDICAL_AMT AS medcAmt\n" +
                "FROM\n" +
                "wjw_account acc\n" +
                "where 1=1\n" +
                "AND acc.unit_no = '" + orgId + "'\n" +
                "AND acc.acc_fld = '" + accFld + "'\n" +
                "AND acc.in_or_out = '" + inOrOut + "'";
//		String sql = "select ACC_NO as accNo,CUST_NAME as custName, AMOUNT as amount,MEDICAL_AMT as medcAmt,DRUG_AMT as drugAmt,"
//				+ "UNIT_NAME as unitName from WJW_ACCOUNT where  IN_OR_OUT="+inOrOut+" and ACC_FLD="+accFld
//				+" and UNIT_NO='" +orgId+"'";
        logger.info("sql:" + sql);
        return jdbcTemplate.queryForList(sql);
    }


    public List<Map<String, Object>> getdspUser(Long orgId) {
        String sql = "select a.USER_ID as userId,b.USER_NAME as userName from WJW_CHECKER a "
                + "left join UNTECK_USER b on a.USER_ID=b.USER_CODE where UNIT_ID='" + orgId + "'";
        return jdbcTemplate.queryForList(sql);
    }

    public void insert(WjwPaydetail wjwPaydetail) {
        String sql = "insert into WJW_PAYDETAIL (AMOUNT, CONN_NO, CURRENCY, ECNO_FL, FOOT_YSDW, FUNC_FL, IN_ACCNO, IN_BANK, IN_NAME, ITEM,"
                + " ITME_YS, OPER_NO, OUT_ACCNAME, OUT_ACCNO, OUT_BANK, PAY_TIME, PAY_WAY, STATUS, TOP_YSDW, UNIT_NAME, UNIT_NO, "
                + "XN_ACCTNAME, XN_ACCTNO, YT, ZB_DETAIL, ZJ_FLD,ZC_ACCTNO,ZC_ACCTNAME) "
                + "values(" + wjwPaydetail.getAmount() + ", '" + wjwPaydetail.getConnNo() + "','" + wjwPaydetail.getCurrency() + "', '"
                + wjwPaydetail.getEcnoFl() + "','" + wjwPaydetail.getFootYsdw() + "','" + wjwPaydetail.getFuncFl() + "','" + wjwPaydetail.getInAccno() + "','"
                + wjwPaydetail.getInBank() + "','" + wjwPaydetail.getInName() + "'," + wjwPaydetail.getItem() + ",'" + wjwPaydetail.getItmeYs() + "','"
                + wjwPaydetail.getOperNo() + "','" + wjwPaydetail.getOutAccname() + "','" + wjwPaydetail.getOutAccno() + "','" + wjwPaydetail.getOutBank() + "','"
                + wjwPaydetail.getPayTime() + "',"
                + wjwPaydetail.getPayWay() + "," + wjwPaydetail.getStatus() + ",'" + wjwPaydetail.getTopYsdw() + "','" + wjwPaydetail.getUnitName() + "','"
                + wjwPaydetail.getUnitNo() + "','" + wjwPaydetail.getXnAcctName() + "','" + wjwPaydetail.getXnAcctno() + "','" + wjwPaydetail.getYt() + "','"
                + wjwPaydetail.getZbDetail() + "','" + wjwPaydetail.getZjFld() + "','" + wjwPaydetail.getZcAcctno() + "','" + wjwPaydetail.getZcAcctname() + "')";
        jdbcTemplate.execute(sql);
    }

    public void addPayDetail(WjwPaydetail entity) {
        String sql = "insert into WJW_PAYDETAIL (AMOUNT, CONN_NO, CURRENCY, ECNO_FL, FOOT_YSDW, FUNC_FL, IN_ACCNO, IN_BANK, IN_NAME, ITEM,"
                + " ITME_YS, OPER_NO, OUT_ACCNAME, OUT_ACCNO, OUT_BANK, PAY_TIME, PAY_WAY, STATUS, TOP_YSDW, UNIT_NAME, UNIT_NO, "
                + "XN_ACCTNAME, XN_ACCTNO, YT, ZB_DETAIL, ZJ_FLD,ZC_ACCTNO,ZC_ACCTNAME,FH_USER,FH_TIME,BACK_FLG,BACK_VOUCHER) "
                + "values(" + entity.getAmount() + ", '" + entity.getConnNo() + "','" + entity.getCurrency() + "', '"
                + entity.getEcnoFl() + "','" + entity.getFootYsdw() + "','" + entity.getFuncFl() + "','" + entity.getInAccno() + "','"
                + entity.getInBank() + "','" + entity.getInName() + "'," + entity.getItem() + ",'" + entity.getItmeYs() + "','"
                + entity.getOperNo() + "','" + entity.getOutAccname() + "','" + entity.getOutAccno() + "','" + entity.getOutBank() + "','"
                + entity.getPayTime() + "',"
                + entity.getPayWay() + "," + entity.getStatus() + ",'" + entity.getTopYsdw() + "','" + entity.getUnitName() + "','"
                + entity.getUnitNo() + "','" + entity.getXnAcctName() + "','" + entity.getXnAcctno() + "','" + entity.getYt() + "','"
                + entity.getZbDetail() + "','" + entity.getZjFld() + "','" + entity.getZcAcctno() + "','" + entity.getZcAcctname() + "','"
                + entity.getFhUser() + "','" + entity.getFhTime() + "'," + entity.getBackFlg() + ",'" + entity.getBackVoucher() + "')";
        logger.info("sql:" + sql);
        jdbcTemplate.execute(sql);
    }

    public List<Map<String, Object>> getPayDetailAll(String connNo) {

        String sql = "select ID from WJW_PAYDETAIL where CONN_NO='" + connNo + "'";
        return jdbcTemplate.queryForList(sql);
    }

    public void updatePayDetail(WjwPaydetail paydetail) {
        String sql = "update WJW_PAYDETAIL set IN_ACCNO='" + paydetail.getInAccno() + "',YT='" + paydetail.getYt() + "',IN_NAME='" + paydetail.getInName() + "',"
                + "IN_BANK='" + paydetail.getInBank() + "' where ID=" + paydetail.getId();
        logger.info("sql:" + sql);
        jdbcTemplate.execute(sql);
    }


    /**
     * 获得凭证最大序列号
     *
     * @return
     */
    public int getMaxCode() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String date = format.format(new Date()) + "02";
        String sql = "select max(substr(OPER_NO,length(OPER_NO)-2)) from WJW_PAYDETAIL where OPER_NO like '" + date + "%'";
        Number number = this.jdbcTemplate.queryForObject(sql, Integer.class);
        return (number != null ? number.intValue() : 0);
    }

    /**
     * 指标摘要
     *
     * @return
     */
    public List<Map<String, Object>> findZbDetails() {
        String sql = "select a.code as \"zbCode\",a.name as \"zbName\" from unteck_dict_entry a "
                + "left join unteck_dict_type b on a.dict_type_id = b.id where b.code = 'CORE.ZBZY'";
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * 经济分类
     *
     * @return
     */
    public List<Map<String, Object>> findEconomic() {
        String sql = "select a.code as \"ecCode\",a.name as \"ecName\" from unteck_dict_entry a "
                + "left join unteck_dict_type b on a.dict_type_id = b.id where b.code = 'CORE.ECONO'";
        return jdbcTemplate.queryForList(sql);
    }


    public String getConno(Long id) {
        String getConnSql = "select CONN_NO from wjw_PAYMAIN where ID = " + id + "";
        return jdbcTemplate.queryForList(getConnSql).get(0).get("CONN_NO").toString();
    }

    public void ampulate(String connNo) {
        String ampulsteSql = "delete from wjw_PAYDETAIL where CONN_NO = '" + connNo + "'";
        jdbcTemplate.execute(ampulsteSql);
    }

    public void ampulateById(Long id) {
        String ampulateByIdSql = "delete from wjw_PAYMAIN where ID = " + id;
        jdbcTemplate.execute(ampulateByIdSql);
    }

    public Map<String, Object> queryPayByVoucher(String voucher) {
        String sql = "select OUT_ACCNO as \"OUT_ACCNO\", OUT_ACCNAME as \"OUT_ACCNAME\", OUT_BANK as \"OUT_BANK\", "
                + "IN_ACCNO as \"IN_ACCNO\", IN_NAME as \"IN_NAME\", IN_BANK as \"IN_BANK\", UNIT_NAME as \"UNIT_NAME\","
                + " AMOUNT as \"AMOUNT\", STATUS as \"STATUS\",BACK_FLG as \"BACK_FLG\",YT as \"YT\",PRINT_TIME as \"PRINT_TIME\"  from wjw_paydetail  "
                + "where OPER_NO ='" + voucher + "'";
        logger.info("sql:" + sql);
        return jdbcTemplate.queryForList(sql).size() == 0 ? null : jdbcTemplate.queryForList(sql).get(0);
    }

    public Pagination<Map<String, Object>> modifyPaydetail(Integer start, Integer limit, String connNo) {
        String sql = "select ID as \"id\",AMOUNT as \"amount\",UNIT_NAME as \"unitName\",IN_ACCNO as \"inAccno\", IN_NAME as \"inName\", IN_BANK as \"inBank\","
                + "PAY_TIME as \"payTime\",ZJ_FLD as \"zjFld\",PAY_WAY as \"payWay\",TOP_YSDW as \"topYsdw\",FOOT_YSDW as \"footYsdw\" ,ITME_YS as \"itemYs\","
                + "FUNC_FL as funcFl,ECNO_FL as \"ecnoFl\",ZB_DETAIL as \"zbDetail\",STATUS as \"status\",YT as \"yt\",ITEM as \"item\",CONN_NO as \"connNo\",NOTE1 as \"note1\""
                + " from WJW_PAYDETAIL where CONN_NO='" + connNo + "'";
        logger.info("sql:" + sql);
        return jdbcPager.queryPage(sql, start, limit);
    }

    public Map<String, Object> findPaydetailById(long id) {
        String sql = "select UNIT_NAME as \"unitName\", AMOUNT as \"amount\",IN_ACCNO as \"inAccno\", IN_NAME as \"inName\", IN_BANK as \"inBank\","
                + "OUT_ACCNO as \"outAccno\", OUT_ACCNAME as \"outAccname\", OUT_BANK as \"outBank\",OPER_NO as \"operNo\","
                + "PAY_TIME as \"payTime\",ZJ_FLD as \"zjFld\",PAY_WAY as \"payWay\",TOP_YSDW as \"topYsdw\",FOOT_YSDW as \"footYsdw\" ,"
                + "FUNC_FL as funcFl,ECNO_FL as \"ecnoFl\",ZB_DETAIL as \"zbDetail\",YT as \"yt\" "
                + " from WJW_PAYDETAIL where ID=" + id;
        logger.info("sql:" + sql);
        return jdbcTemplate.queryForMap(sql);
    }

    public Pagination<Map<String, Object>> getBankPayBack(Integer start,
                                                          Integer limit, String startTime, String endTime) {
        String sql = "select ID as \"id\", UNIT_NAME as \"unitName\", AMOUNT as \"amount\",IN_ACCNO as \"inAccno\", IN_NAME as \"inName\", IN_BANK as \"inBank\","
                + "OUT_ACCNO as \"outAccno\", OUT_ACCNAME as \"outAccname\", OUT_BANK as \"outBank\",OPER_NO as \"operNo\",STATUS as \"status\","
                + "PAY_TIME as \"payTime\",ZJ_FLD as \"zjFld\",PAY_WAY as \"payWay\",TOP_YSDW as \"topYsdw\",FOOT_YSDW as \"footYsdw\",ITEM as \"item\","
                + "FUNC_FL as funcFl,ECNO_FL as \"ecnoFl\",ZB_DETAIL as \"zbDetail\",YT as \"yt\",BACK_FLG as \"backFlg\",NOTE1 as \"note1\",BACK_VOUCHER as \"backVoucher\" "
                + " from WJW_PAYDETAIL where BACK_FLG=3";
        if (StringUtils.isNotBlank(startTime)) {
            sql += " and PAY_TIME>='" + startTime + "'";
        }
        if (StringUtils.isNotBlank(endTime)) {
            sql += " and PAY_TIME<='" + endTime + "'";
        }
//		sql += " ORDER BY PAY_TIME ASC";
        logger.info("sql:" + sql);
        return jdbcPager.queryPage(sql, start, limit);
    }

    public Pagination<Map<String, Object>> getBackPaydetail(Integer start,
                                                            Integer limit, String startTime, String endTime, String unitNo, String status) {
        String sql = "select ID as \"id\",UNIT_NO as \"unitNo\",UNIT_NAME as \"unitName\", AMOUNT as \"amount\",IN_ACCNO as \"inAccno\", IN_NAME as \"inName\", IN_BANK as \"inBank\","
                + "OUT_ACCNO as \"outAccno\", OUT_ACCNAME as \"outAccname\", OUT_BANK as \"outBank\",OPER_NO as \"operNo\",STATUS as \"status\",ITME_YS as \"itmeYs\","
                + "PAY_TIME as \"payTime\",ZJ_FLD as \"zjFld\",PAY_WAY as \"payWay\",TOP_YSDW as \"topYsdw\",FOOT_YSDW as \"footYsdw\",ITEM as \"item\","
                + "FUNC_FL as funcFl,ECNO_FL as \"ecnoFl\",ZB_DETAIL as \"zbDetail\",YT as \"yt\",BACK_FLG as \"backFlg\",NOTE1 as \"note1\",BACK_VOUCHER as \"backVoucher\" "
                + " from WJW_PAYDETAIL where BACK_FLG in(1,2)";
        if (StringUtils.isNotBlank(startTime)) {
            sql += " and PAY_TIME>='" + startTime + "'";
        }
        if (StringUtils.isNotBlank(endTime)) {
            sql += " and PAY_TIME<='" + endTime + "'";
        }
        if (StringUtils.isNotBlank(status)) {
            sql += " and STATUS=" + status;
        }
        if (StringUtils.isNotBlank(unitNo)) {
            sql += " and UNIT_NO='" + unitNo.trim() + "'";
        }
//		sql += " ORDER BY BACK_FLG ASC";
        return jdbcPager.queryPage(sql, start, limit);
    }

    public Map<String, Object> findPaydetailByVoucher(String voucher) {
        String sql = "select * from wjw_paydetail where OPER_NO ='" + voucher + "'";
        logger.info("sql:" + sql);
        return jdbcTemplate.queryForList(sql).size() == 0 ? null : jdbcTemplate.queryForList(sql).get(0);
    }

    public void updatePayBackFlag(int backFlg, String backVoucher) {
        String sql = "update wjw_paydetail set BACK_FLG=" + backFlg + " where OPER_NO='" + backVoucher + "'";
        logger.info("sql:" + sql);
        jdbcTemplate.update(sql);
    }

    public Map<String, Object> findPayByVoucher(String voucher) {

        String sql = "select  IN_NAME, IN_ACCNO,"
                + "AMOUNT,(case when ITEM=2 then AMOUNT else 0.00 end) as DRUG_AMT,"
                + "(case when ITEM=1 then AMOUNT else 0.00 end) as MEDICAL_AMT, "
                + "ZC_ACCTNO, ZC_ACCTNAME,STATUS,"
                + "XN_ACCTNO, XN_ACCTNAME,CONN_NO  "
                + " from WJW_PAYDETAIL  where OPER_NO = '" + voucher + "'";
        logger.info("sql:" + sql);
        return jdbcTemplate.queryForList(sql).size() == 0 ?
                null : jdbcTemplate.queryForList(sql).get(0);
    }


    public void deleteBankPayByBackVoucher(String voucher) {
        String sql = "delete from WJW_PAYDETAIL where BACK_VOUCHER='" + voucher + "'";
        jdbcTemplate.execute(sql);
    }

    public void deleteByVoucher(String voucher, Boolean isPayBack) {
        String sql = "delete from WJW_ACCCHANGE where 1=1 ";
        if (isPayBack) {
            sql += " and note2='" + voucher + "'";
        } else {
            sql += " and note1='" + voucher + "'";
        }
        logger.info("sql:" + sql);
        jdbcTemplate.execute(sql);
    }

    public Map<String, Object> findNewPayByBackVoucher(String voucher) {
        String sql = "select * from WJW_PAYDETAIL where BACK_VOUCHER='" + voucher + "'";
        logger.info("sql:" + sql);
        return jdbcTemplate.queryForList(sql).size() == 0 ?
                null : jdbcTemplate.queryForList(sql).get(0);
    }

    public List<Map<String, Object>> downloadBackPay(String startTime,
                                                     String endTime, String status, String unitNo) {
        String sql = "select ID as \"id\",UNIT_NO as \"unitNo\",UNIT_NAME as \"unitName\", AMOUNT as \"amount\",IN_ACCNO as \"inAccno\", IN_NAME as \"inName\", IN_BANK as \"inBank\","
                + "OUT_ACCNO as \"outAccno\", OUT_ACCNAME as \"outAccname\", OUT_BANK as \"outBank\",OPER_NO as \"operNo\",STATUS as \"status\",ITME_YS as \"itmeYs\","
                + "PAY_TIME as \"payTime\",ZJ_FLD as \"zjFld\",PAY_WAY as \"payWay\",TOP_YSDW as \"topYsdw\",FOOT_YSDW as \"footYsdw\",ITEM as \"item\","
                + "FUNC_FL as funcFl,ECNO_FL as \"ecnoFl\",ZB_DETAIL as \"zbDetail\",YT as \"yt\",BACK_FLG as \"backFlg\",BACK_VOUCHER as \"backVoucher\",NOTE1 as \"note1\" "
                + " from WJW_PAYDETAIL where BACK_FLG in(1,2)";
        if (StringUtils.isNotBlank(startTime)) {
            sql += " and PAY_TIME>='" + startTime + "'";
        }
        if (StringUtils.isNotBlank(endTime)) {
            sql += " and PAY_TIME<='" + endTime + "'";
        }
        if (StringUtils.isNotBlank(status)) {
            sql += " and STATUS=" + status;
        }
        if (StringUtils.isNotBlank(unitNo)) {
            sql += " and UNIT_NO='" + unitNo.trim() + "'";
        }
        logger.info("sql:" + sql);
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> findById(Long id) {
        String sql = "select ID as \"id\",UNIT_NO as \"unitNo\",UNIT_NAME as \"unitName\", AMOUNT as \"amount\",IN_ACCNO as \"inAccno\", IN_NAME as \"inName\", IN_BANK as \"inBank\","
                + "OUT_ACCNO as \"outAccno\", OUT_ACCNAME as \"outAccname\", OUT_BANK as \"outBank\",OPER_NO as \"operNo\",STATUS as \"status\",ITME_YS as \"itmeYs\","
                + "PAY_TIME as \"payTime\",ZJ_FLD as \"zjFld\",PAY_WAY as \"payWay\",TOP_YSDW as \"topYsdw\",FOOT_YSDW as \"footYsdw\",ITEM as \"item\","
                + "FUNC_FL as funcFl,ECNO_FL as \"ecnoFl\",ZB_DETAIL as \"zbDetail\",YT as \"yt\",BACK_FLG as \"backFlg\",BACK_VOUCHER as \"backVoucher\",PRINT_TIME as \"printTime\" "
                + " from WJW_PAYDETAIL where ID = '" + id + "'";
        logger.info("sql:" + sql);
        return jdbcTemplate.queryForList(sql);
    }

    public void updateOperNo(WjwPaydetail paydetail) {
        String sql = "update WJW_PAYDETAIL set OPER_NO='" + paydetail.getOperNo() + "' where ID=" + paydetail.getId();
        logger.info("sql:" + sql);
        jdbcTemplate.execute(sql);
    }

    public void updateByid(WjwPaydetail paydetail) {
        String sql = "update WJW_PAYDETAIL set PRINT_TIME='" + paydetail.getPrintTime() + "' where ID=" + paydetail.getId();
        logger.info("sql:" + sql);
        jdbcTemplate.execute(sql);
    }

}

