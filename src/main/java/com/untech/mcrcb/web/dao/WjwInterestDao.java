package com.untech.mcrcb.web.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.model.WjwInterest;
import com.unteck.common.dao.jdbc.NamedParameterJdbcPager;
import com.unteck.common.dao.support.Pagination;
@Repository
public class WjwInterestDao extends BaseDao<WjwInterest, Long> {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcPager jdbcPager;
	public Pagination<Map<String, Object>> findInterestOut(Integer start,
			Integer limit, String startTime, String endTime) {
		String sql = "select ID as \"id\",UNIT_NO as \"unitNo\",UNIT_NAME as \"unitName\",IN_ACCNO as \"inAccno\","
				+ "IN_ACCNAME as \"inAccname\",IN_BANK as \"inBank\",OUT_ACCNO as \"outAccno\",OUT_ACCNAME as \"outAccname\","
				+ "OUT_BANK as \"outBank\",AMOUNT as \"amount\",VOUCHER as \"voucher\",PAY_WAY as \"payWay\",STATUS as \"status\",PAY_TIME as \"payTime\","
				+ "IN_OR_OUT as \"inOrOut\",OPERATOR as \"operator\",FH_USER as \"fhUser\",FH_TIME as \"fhTime\" from WJW_INTEREST where 1=1 ";
		if(StringUtils.isNotBlank(startTime)){
			sql += " and PAY_TIME>='"+startTime+"'";
		}
		if(StringUtils.isNotBlank(endTime)){
			sql += " and PAY_TIME<='"+endTime+"'";
		}
		logger.info("sql:"+sql);
		return jdbcPager.queryPage(sql, start, limit);
	}
	public Map<String, Object> getInterest(Long id) {
		String sql = "select ID as \"id\",UNIT_NO as \"unitNo\",UNIT_NAME as \"unitName\",IN_ACCNO as \"inAccno\","
				+ "IN_ACCNAME as \"inAccname\",IN_BANK as \"inBank\",OUT_ACCNO as \"outAccno\",OUT_ACCNAME as \"outAccname\","
				+ "OUT_BANK as \"outBank\",AMOUNT as \"amount\",VOUCHER as \"voucher\",PAY_WAY as \"payWay\",STATUS as \"status\",PAY_TIME as \"payTime\","
				+ "IN_OR_OUT as \"inOrOut\",OPERATOR as \"operator\",FH_USER as \"fhUser\",FH_TIME as \"fhTime\" from WJW_INTEREST where ID="+id;
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql).size()==0?null:jdbcTemplate.queryForList(sql).get(0);
	}

	/**
	 * 获得凭证最大序列号
	 * @return
	 */
	public int getMaxCode() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String date = format.format(new Date())+"04";
		String sql = "select max(substr(VOUCHER,length(VOUCHER)-2)) from WJW_INTEREST where VOUCHER like '"+date+"%'";
	//	int num = this.jdbcTemplate.queryForInt(sql);
		Number number = this.jdbcTemplate.queryForObject(sql,Integer.class);
		return (number != null ? number.intValue() : 0);
	}
	public String getWSYJianpin(Long OId){
		String sql = "select DESCN from UNTECK_ORGANIZATION WHERE ID="+OId;
		return this.jdbcTemplate.queryForObject(sql,String.class);
	}
	public Map<String, Object> findBankAccount(String inOrOut) {
		String sql = "select ACC_NO,CUST_NAME from WJW_ACCOUNT where ACC_FLD=1 and IN_OR_OUT="+inOrOut;
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql).size()==0?null:jdbcTemplate.queryForList(sql).get(0);
	}
	public void deleteInterest(Long id) {
		String sql = "update WJW_INTEREST set STATUS=4 where ID="+id;
		logger.info("sql:"+sql);
		jdbcTemplate.update(sql);
	}
	public Map<String, Object> queryInterestOutByVoucher(String voucher) {
		String sql = "select * from WJW_INTEREST where VOUCHER='"+voucher+"'";
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql).size()==0?null:jdbcTemplate.queryForList(sql).get(0);
	}
}
