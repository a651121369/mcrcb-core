package com.untech.mcrcb.web.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.model.WjwAccountDayEnd;
import com.untech.mcrcb.web.model.WjwIncomedetail;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.unteck.common.dao.jdbc.NamedParameterJdbcPager;
import com.unteck.common.dao.support.Pagination;

@Repository
public class AccountDayEndDao extends BaseDao<WjwAccountDayEnd, Long> {
	
	@Autowired
	private NamedParameterJdbcPager jdbcPager;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Pagination<Map<String, Object>> getAccDayEndInfo(Integer start, Integer limit,String PAY_ACC_TRUE
			,String PAY_ACCNAME_TRUE,String GET_ACC_TRUE,String GET_ACCNAME_TRUE){
		String sql =" select a.PAY_ACC_TRUE as \"PAY_ACC_TRUE\",a.PAY_ACCNAME_TRUE as \"PAY_ACCNAME_TRUE\", "
				+ "a.PAY_ACCMONEY_TRUE as \"PAY_ACCMONEY_TRUE\",a.GET_ACC_TRUE as \"GET_ACC_TRUE\",  "
				+ "a.GET_ACCNAME_TRUE as \"GET_ACCNAME_TRUE\", a.GET_ACCMONEY_TRUE as \"GET_ACCMONEY_TRUE\", "
				+ "a.PAY_ACC_FAL as \"PAY_ACC_FAL\",a.PAY_ACCNAME_FAL as \"PAY_ACCNAME_FAL\", "
				+ "a.PAY_ACCMONEY_FAL as \"PAY_ACCMONEY_FAL\",a.GET_ACC_FAL as \"GET_ACC_FAL\",a.GET_ACCNAME_FAL as \"GET_ACCNAME_FAL\","
				+ "a.GET_ACCMONEY_FAL as \"GET_ACCMONEY_FAL\",a.STATUS_DAYEND as \"STATUS_DAYEND\",a.DATE_DAYEND as \"DATE_DAYEND\" "
				+ " from WJW_ACCOUNT_DAYEND a where  1=1 " ;
		if(StringUtils.isNotBlank(PAY_ACC_TRUE)) {
			sql += " and a.PAY_ACC_TRUE = '"+PAY_ACC_TRUE.trim()+"'";
		}
		if(StringUtils.isNotBlank(PAY_ACCNAME_TRUE)) {
			sql += " and a.PAY_ACCNAME_TRUE like '%"+PAY_ACCNAME_TRUE.trim()+"%'";
		}
		if(StringUtils.isNotBlank(GET_ACC_TRUE)) {
			sql += " and a.GET_ACC_TRUE = '"+GET_ACC_TRUE.trim()+"'";
		}
		if(StringUtils.isNotBlank(GET_ACCNAME_TRUE)) {
			sql += " and a.GET_ACCNAME_TRUE like '%"+GET_ACCNAME_TRUE.trim()+"%'";
		}
		logger.info("sql:"+sql);
    	return jdbcPager.queryPage(sql, start, limit);
    }
	
	
	public List<Map<String,Object>> getParAccSql(String account){
		String getParAccSql = "select ACC_NO,CUST_NAME from wjw_ACCOUNT where ACC_PARENT = '"+account+"'";
		logger.info("sql:"+getParAccSql);
		return jdbcTemplate.queryForList(getParAccSql);
	}
	
	public List<Map<String,Object>> getChildAccMoney(String account){
		String getChildAccMoneySql = "select AMOUNT from wjw_ACCOUNT where ACC_PARENT = '"+account+"'";
		logger.info("sql:"+getChildAccMoneySql);
		return jdbcTemplate.queryForList(getChildAccMoneySql);
	}
	
	public List<Map<String,Object>> getAccMoney(String account){
		String getAccMoneySql = "select AMOUNT from wjw_ACCOUNT where ACC_NO = '"+account+"'";
		logger.info("sql:"+getAccMoneySql);
		return jdbcTemplate.queryForList(getAccMoneySql);
	}
	
	public void addAccDayEnd(String PAY_ACC_TRUE,String PAY_ACCNAME_TRUE,BigDecimal PAY_ACCMONEY_TRUE,
			String GET_ACC_TRUE,String GET_ACCNAME_TRUE,BigDecimal GET_ACCMONEY_TRUE,
			String xPayAcc,String xPayAccName,BigDecimal xPayAmount,
			String xGetAcc,String xGetAccName,BigDecimal xGetAmont,
			String flag,String inDay){
		String insertSql = "insert into WJW_ACCOUNT_DAYEND (PAY_ACC_TRUE,PAY_ACCNAME_TRUE,PAY_ACCMONEY_TRUE,"
				+ "GET_ACC_TRUE,GET_ACCNAME_TRUE,GET_ACCMONEY_TRUE,PAY_ACC_FAL,PAY_ACCNAME_FAL,PAY_ACCMONEY_FAL,"
				+ "GET_ACC_FAL,GET_ACCNAME_FAL,GET_ACCMONEY_FAL,STATUS_DAYEND,DATE_DAYEND) "
				+ "values('"+PAY_ACC_TRUE+"','"+PAY_ACCNAME_TRUE+"',"+PAY_ACCMONEY_TRUE+","
						+ "'"+GET_ACC_TRUE+"','"+GET_ACCNAME_TRUE+"',"+GET_ACCMONEY_TRUE+","
						+ "'"+xPayAcc+"','"+xPayAccName+"',"+xPayAmount+","
						+ "'"+xGetAcc+"','"+xGetAccName+"',"+xGetAmont+","
						+ "'"+flag+"','"+inDay+"')";
		logger.info("sql:"+insertSql);
		jdbcTemplate.execute(insertSql);
		
	}
	
	public List<Map<String,Object>> getAccountInfo() {
		String sql = "select max((case when in_or_out=1 and ACC_FLD=1 then ACC_NO else '' end)) as \"GET_ACC_TRUE\","
				+ "max((case when in_or_out=1 and ACC_FLD=1 then CUST_NAME else '' end)) as \"GET_ACCNAME_TRUE\","
				+ "max((case when in_or_out=2 and ACC_FLD=1 then ACC_NO else '' end)) as \"PAY_ACC_TRUE\","
				+ "max((case when in_or_out=2 and ACC_FLD=1 then CUST_NAME else '' end)) as \"PAY_ACCNAME_TRUE\" "
				+ "from WJW_ACCOUNT";
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql);
	}


	public Map<String, Object> dayEnd(String inAccno, String outAccno) {
		String sql = "select max((case when IN_OR_OUT=1 and ACC_FLD=2 and ACC_PARENT='"+inAccno+"' then ACC_NO else '' end)) as \"IN_ACCNO\","
				+ "max((case when IN_OR_OUT=1 and ACC_FLD=2 and ACC_PARENT='"+inAccno+"' then CUST_NAME else '' end)) as \"IN_ACCNAME\","
				+ "max((case when IN_OR_OUT=1 and ACC_FLD=2 and ACC_PARENT='"+inAccno+"' then (AMOUNT+INT_COME) else 0 end)) as \"IN_AMOUNT\","
				+ "max((case when IN_OR_OUT=2 and ACC_FLD=2 and ACC_PARENT='"+outAccno+"' then ACC_NO else '' end)) as \"OUT_ACCNO\","
				+ "max((case when IN_OR_OUT=2 and ACC_FLD=2 and ACC_PARENT='"+outAccno+"' then CUST_NAME else '' end)) as \"OUT_ACCNAME\","
				+ "max((case when IN_OR_OUT=2 and ACC_FLD=2 and ACC_PARENT='"+outAccno+"' then (AMOUNT+INT_COME) else 0 end)) as \"OUT_AMOUNT\" "
				+ "from wjw_account";
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForMap(sql);
	}


	public Map<String, Object> queryChildAmountAll(String inXnAccno,
			String outXnAccno) {
		String sql = "select sum((case when IN_OR_OUT=1 and ACC_FLD=2 and ACC_PARENT='"+inXnAccno+"' then AMOUNT else 0 end)) as \"CHILD_IN_AMT\","
		+ "sum((case when IN_OR_OUT=2 and ACC_FLD=2 and ACC_PARENT='"+outXnAccno+"' then AMOUNT else 0 end)) as \"CHILD_OUT_AMT\"  "
		+ "from wjw_account";
//		String sql = "select sum((case when IN_OR_OUT=1 and ACC_FLD=2 and right(ACC_NO,4)!='9999' and ACC_PARENT='"+inXnAccno+"' then AMOUNT else 0 end)) as \"CHILD_IN_AMT\","
//				+ "sum((case when IN_OR_OUT=2 and ACC_FLD=2 and right(ACC_NO,4)!='9999' and ACC_PARENT='"+outXnAccno+"' then AMOUNT else 0 end)) as \"CHILD_OUT_AMT\"  "
//				+ "from wjw_account";
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForMap(sql);
	}


	public Map<String, Object> queryParentAmount(String inXnAccno,
			String outXnAccno) {
		String sql = "select Max((case when IN_OR_OUT=1 and ACC_FLD=2 and ACC_NO='"+inXnAccno+"' then AMOUNT else 0 end)) as \"PARENT_IN_AMT\","
				+ "Max((case when IN_OR_OUT=2 and ACC_FLD=2 and ACC_NO='"+outXnAccno+"' then AMOUNT else 0 end)) as \"PARENT_OUT_AMT\" "
				+ "from wjw_account";
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForMap(sql);
	}


	
	public Long checkDayEnd(String date) {
		String sql = "select count(*) from WJW_ACCOUNT_DAYEND where DATE_DAYEND='"+date+"'";
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForLong(sql);
		
	}


	public void updateDayEnd(String outAccno, String outAccname,
			BigDecimal outZsAmt, String inAccno, String inAccname,
			BigDecimal inZsAmt, String outXnAccno, String outXnAccname,
			BigDecimal outXnAmt, String inXnAccno, String inXnAccname,
			BigDecimal inXnAmt, String string, String date) {
		String sql = "update WJW_ACCOUNT_DAYEND set PAY_ACCMONEY_TRUE="+outZsAmt+",GET_ACCMONEY_TRUE="+inZsAmt
				+",PAY_ACCMONEY_FAL="+outXnAmt+",GET_ACCMONEY_FAL="+inXnAmt+" where DATE_DAYEND='"+date+"'";
		logger.info("sql:"+sql);
		jdbcTemplate.update(sql);
	}

}
