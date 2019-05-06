package com.untech.mcrcb.web.dao;



import java.util.List;
import java.util.Map;

import com.untech.mcrcb.web.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.untech.mcrcb.web.model.WjwAccchange;
import com.untech.mcrcb.web.enhance.BaseDao;
import com.unteck.common.dao.jdbc.NamedParameterJdbcPager;
import com.unteck.common.dao.support.Pagination;
import org.springframework.util.StringUtils;

/**
 * WJW_ACCCHANGE DAO
 * @author            chenyong
 * @since             2015-11-04
 */
@Repository
public class WjwAccchangeDao
  extends BaseDao<WjwAccchange, Long>
{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcPager jdbcPager;
	
	
	public Pagination<Map<String,Object>> findDqfDetails(Integer start,Integer limit,String startTime,String endTime){
		StringBuffer dynamicSql = this.subfetchPagerSql(startTime,endTime);
		dynamicSql.append("  ORDER BY TRAN_TIME DESC");
		logger.info("sql:"+dynamicSql.toString());
		return jdbcPager.queryPage(dynamicSql.toString(), start, limit);
	}

	public Pagination<Map<String,Object>> findDqfDetailsByAccCode(Integer start,Integer limit,int accCode,String startTime,String endTime){
		StringBuffer dynamicSql = this.subfetchPagerSql(startTime,endTime);
		if (Constants.ACC_CODE.WSY==accCode){
			dynamicSql.append("  AND a.UNIT_NO LIKE '3400%'");
		}else if (Constants.ACC_CODE.JLY==accCode){
			dynamicSql.append("  AND a.UNIT_NO LIKE '3500%'");
		}
		dynamicSql.append("  ORDER BY TRAN_TIME DESC");
		logger.info("sql:"+dynamicSql.toString());
		return jdbcPager.queryPage(dynamicSql.toString(), start, limit);
	}

	private StringBuffer subfetchPagerSql(String startTime,String endTime) {
		StringBuffer dynamicSql = new StringBuffer();
		String sql = "select a.ID as \"id\",a.unit_no as \"unitNo\",a.unit_name as \"unitName\",a.acc_no as \"accNo\",a.acc_name as \"accName\","
				+ "a.df_accno as \"dfAccno\",a.df_accname as \"dfAccname\",a.amount as \"amount\",a.drug_amt as \"drugAmt\",a.medc_amt as \"medcAmt\","
				+ "a.tran_amt as \"tranAmt\",a.tran_time as \"tranTime\",a.in_or_out as \"inOrOut\",a.other_amt as \"otherAmt\",a.note1 as \"note1\","
				+ "a.note2 as \"note2\",a.flag as \"flag\",a.trade_count as \"tradeCount\",a.descstr as \"descstr\",a.unk_type as \"unkType\"  from wjw_accchange a  where 1=1 and a.flag in(1,2)  and a.acc_no like '%9999' and a.df_accno like '%0000'";
		dynamicSql.append(sql);
		if(StringUtils.hasText(startTime)){
			dynamicSql.append("  and a.tran_time >='"+startTime+"'");
		}
		if(StringUtils.hasText(endTime)){
			dynamicSql.append("  and a.tran_time <='"+endTime+"'");
		}
		return dynamicSql;
	}

	
	public Pagination<Map<String,Object>> findQfMx(String uuid,Integer start,Integer limit,String startTime,String endTime){
		String sql = "select ID as \"id\",unit_no as \"unitNo\",unit_name as \"unitName\",acc_no as \"accNo\",acc_name as \"accName\","
				+ "df_accno as \"dfAccno\",df_accname as \"dfAccname\",amount as \"amount\",drug_amt as \"drugAmt\",medc_amt as \"medcAmt\","
				+ "tran_amt as \"tranAmt\",tran_time as \"tranTime\",in_or_out as \"inOrOut\",other_amt as \"otherAmt\",note1 as \"note1\","
				+ "note2 as \"note2\",flag as \"flag\",unk_type as \"unkType\" from wjw_accchange a  where 1=1 "
				+ "and a.note2='"+uuid+"'" ;

		if(StringUtils.hasText(startTime)){
			sql += "  and a.tran_time >='"+startTime+"'";
		}
		if(StringUtils.hasText(endTime)){
			sql += "  and a.tran_time <='"+endTime+"'";
		}
		sql += "and flag is null";
		logger.info("sql:"+sql);
		return jdbcPager.queryPage(sql, start, limit);
	}

	
	public void insertEntity(WjwAccchange w){
		String sql = "insert into wjw_accchange (unit_no,unit_name,acc_no,acc_name,df_accno,"
				+ "df_accname,amount,drug_amt,medc_amt,tran_amt,tran_time,in_or_out,other_amt,note2,flag) "
				+ "values('"+w.getUnitNo()+"','"+w.getUnitName()+"','"+w.getAccNo()+"','"+w.getAccName()
				+"','"+w.getDfAccno()+"','"+w.getDfAccname()+"',"+w.getAmount()+","+w.getDrugAmt()
				+","+w.getMedcAmt()+","+w.getTranAmt()+",'"+w.getTranTime()+"',"+w.getInOrOut()
				+","+w.getOtherAmt()+",'"+w.getNote2()+"',"+w.getFlag()+")";
		logger.info("sql:"+sql);
		jdbcTemplate.execute(sql);
	}


	public Pagination<Map<String, Object>> findLxDetails(Integer start,
			Integer limit, String startTime, String endTime) {
		String sql = "select ID as \"id\",unit_no as \"unitNo\",unit_name as \"unitName\",acc_no as \"accNo\",acc_name as \"accName\","
				+ "df_accno as \"dfAccno\",df_accname as \"dfAccname\",amount as \"amount\",drug_amt as \"drugAmt\",medc_amt as \"medcAmt\","
				+ "tran_amt as \"tranAmt\",tran_time as \"tranTime\",in_or_out as \"inOrOut\",other_amt as \"otherAmt\",note1 as \"note1\","
				+ "note2 as \"note2\",flag as \"flag\"  from wjw_accchange a  where 1=1 and a.flag =3 ";
			
		if(StringUtils.hasText(startTime)){
			sql += "  and a.tran_time >='"+startTime+"'";
		}
		if(StringUtils.hasText(endTime)){
			sql += "  and a.tran_time <='"+endTime+"'";
		}
		logger.info("sql:"+sql);	
		return jdbcPager.queryPage(sql, start, limit);
	}


	public Pagination<Map<String, Object>> queryTradeDetail(Integer start,Integer limit,
			String startTime, String endTime, String accNo,String unitNo) {
		StringBuffer dynamicSql = this.subTradePagerSql(startTime,endTime,accNo);
		if(!StringUtils.isEmpty(unitNo)){
			dynamicSql.append(" and a.unit_no ='"+unitNo+"'");
		}
		dynamicSql.append("  ORDER BY TRAN_TIME DESC");
		logger.info("sql:"+dynamicSql.toString());
		return jdbcPager.queryPage(dynamicSql.toString(), start, limit);
	}


	public Pagination<Map<String, Object>> queryTradeDetailChanage(Integer start,Integer limit,
															String startTime, String endTime, String accNo,String unitNo,int accCode) {
		StringBuffer dynamicSql = this.subTradePagerSql(startTime,endTime,accNo);
		if (Constants.ACC_CODE.WSY==accCode){
			dynamicSql.append("  AND a.UNIT_NO LIKE '3400%'");
		}else if (Constants.ACC_CODE.JLY==accCode){
			dynamicSql.append("  AND a.UNIT_NO LIKE '3500%'");
		}
//		if(!StringUtils.isEmpty(unitNo)){
//			dynamicSql.append(" and a.unit_no ='"+unitNo+"'");
//		}
		dynamicSql.append("  ORDER BY TRAN_TIME DESC");
		logger.info("sql:"+dynamicSql.toString());
		return jdbcPager.queryPage(dynamicSql.toString(), start, limit);
	}

	private StringBuffer subTradePagerSql(String startTime, String endTime, String accNo) {
		StringBuffer dynamicSql = new StringBuffer();
		String sql = "select ID as \"id\",unit_no as \"unitNo\",unit_name as \"unitName\",acc_no as \"accNo\",acc_name as \"accName\","
				+ "df_accno as \"dfAccno\",df_accname as \"dfAccname\",amount as \"amount\",drug_amt as \"drugAmt\",medc_amt as \"medcAmt\","
				+ "tran_amt as \"tranAmt\",tran_time as \"tranTime\",in_or_out as \"inOrOut\",other_amt as \"otherAmt\",note1 as \"note1\","
				+ "note2 as \"note2\",flag as \"flag\"  from wjw_accchange a  where 1=1 ";
		dynamicSql.append(sql);
		if(!StringUtils.isEmpty(accNo)){
			dynamicSql.append(" and a.acc_no='"+accNo+"'");
		}
		if(StringUtils.hasText(startTime)){
			dynamicSql.append("  and a.tran_time >='"+startTime+"'");
		}
		if(StringUtils.hasText(endTime)){
			dynamicSql.append("  and a.tran_time <='"+endTime+"'");
		}
		return dynamicSql;
	}


	public List<Map<String, Object>> downloadTradeDetail(String startTime,
			String endTime, String accNo) {
		String sql = "select ID as \"id\",unit_no as \"unitNo\",unit_name as \"unitName\",acc_no as \"accNo\",acc_name as \"accName\","
				+ "df_accno as \"dfAccno\",df_accname as \"dfAccname\",amount as \"amount\",drug_amt as \"drugAmt\",medc_amt as \"medcAmt\","
				+ "tran_amt as \"tranAmt\",tran_time as \"tranTime\",in_or_out as \"inOrOut\",other_amt as \"otherAmt\",note1 as \"note1\","
				+ "note2 as \"note2\",flag as \"flag\"  from wjw_accchange a  where 1=1 ";
		if(!StringUtils.isEmpty(accNo)){
			sql += " and a.acc_no='"+accNo+"'";
		}
		if(StringUtils.hasText(startTime)){
			sql += "  and a.tran_time >='"+startTime+"'";
		}
		if(StringUtils.hasText(endTime)){
			sql += "  and a.tran_time <='"+endTime+"'";
		}
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql);
	}

	public Map<String, Object> getOrganization(String orgId) {
		String sql = "select ID as code,NAME as name,PARENTID from UNTECK_ORGANIZATION";
		sql = (orgId==""?sql:(sql+" where ID="+orgId));
		return jdbcTemplate.queryForList(sql).get(0);
	}


	public void deleteByConn(String conn,String flag) {
		String sql = "delete from wjw_accchange where NOTE2='"+conn+"'";
		sql += flag==null?" and FLAG is null":" and FLAG is not null";
		logger.info("sql:"+sql);
		jdbcTemplate.execute(sql);
	}

	public List<Map<String, Object>> findByConn(String conn,String accNo) {
		String sql = "select * from wjw_accchange where ACC_NO='"+accNo+"' and NOTE2='"+conn+"' and flag is null";
		logger.info("sql:"+sql);
		return jdbcTemplate.queryForList(sql);
	}


	public void updateFlag(String conn, int flag) {
		String sql = "update wjw_accchange set FLAG="+flag+" where NOTE2='"+conn+"' and flag=2";
		logger.info("sql:"+sql);
		jdbcTemplate.execute(sql);
	}


	public Map<String, Object> queryInterestByVoucher(String voucher) {
		String sql = "select DF_ACCNO as \"OUT_ACCNO\", DF_ACCNAME as \"OUT_ACCNAME\", OUT_BANK as \"OUT_BANK\", "
				+ "ACC_NO as \"IN_ACCNO\", ACC_NAME as \"IN_NAME\", IN_BANK as \"IN_BANK\", UNIT_NAME as \"UNIT_NAME\","
				+ " TRAN_AMT as \"AMOUNT\", STATUS as \"STATUS\" ,TRAN_TIME as \"TIME\"  from wjw_accchange  "
				+ "where NOTE1 ='"+voucher+"'"+"AND FLAG=3";
		return jdbcTemplate.queryForList(sql).size()==0?null:jdbcTemplate.queryForList(sql).get(0);
	}


	public Map<String, Object> queryUnknownIncomeByVoucher(String voucher) {
		String sql = "select DF_ACCNO as \"OUT_ACCNO\", DF_ACCNAME as \"OUT_ACCNAME\", OUT_BANK as \"OUT_BANK\", "
				+ "ACC_NO as \"IN_ACCNO\", ACC_NAME as \"IN_NAME\", IN_BANK as \"IN_BANK\", UNIT_NAME as \"UNIT_NAME\","
				+ " TRAN_AMT as \"AMOUNT\", STATUS as \"STATUS\" ,TRAN_TIME as \"TIME\"  from wjw_accchange  "
				+ "where NOTE1 ='"+voucher+"'"+"AND FLAG=1 AND RIGHT(ACC_NO,4)='9999'";
		return jdbcTemplate.queryForList(sql).size()==0?null:jdbcTemplate.queryForList(sql).get(0);
	}
}

