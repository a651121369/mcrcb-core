package com.untech.mcrcb.web.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.untech.mcrcb.web.model.WjwCertNum;
import com.unteck.common.dao.jdbc.NamedParameterJdbcPager;
import com.unteck.common.dao.support.Pagination;

@Repository
public class WjwCertNumDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcPager jdbcPager;
	
	public Pagination<Map<String,Object>> getPayeracct(Integer start, Integer limit,Integer connNo) {
		  	  String sql = "select id,cert_no as \"certNo\",time,user_no as \"userNo\",user_name as \"userName\",unit_no as \"unitNo\" ,unit_name as \"unitName\",note "+
		  			  		"from WJW_CERT_NUM where conn_no='"+connNo+"' ";
		  	  Pagination<Map<String,Object>> list = jdbcPager.queryPage(sql, start, limit);
			return list;
		}
	
	public void insert(WjwCertNum entity){
		//根据交易凭证号进行判断，是否该凭证号已保存，
//		int maxNo= this.getNo(entity.getCertNo());
//		if (0==maxNo){
//			System.out.println("sql====maxNo已存在"+maxNo);
//			return ;
//		}
		String sql = "insert into WJW_CERT_NUM (cert_no, time,user_no,user_name, unit_no, unit_name, conn_no, note ) "
				+ "values("+entity.getCertNo()+", '"+entity.getTime()+"','"+entity.getUserNo()+"','"+entity.getUserName()+"', '"
				+entity.getUnitNo()+ "','"+entity.getUnitName()+"','"+entity.getConnNo()+"','"+entity.getNote()+"')";
		System.out.println("sql"+sql);
		jdbcTemplate.execute(sql);
	}
	
	public int getMaxNo() {
		String sql = "select IFNULL(MAX(conn_no),0)  from WJW_CERT_NUM";
		System.out.println("sql"+sql);
		Number number = this.jdbcTemplate.queryForObject(sql,Integer.class);
		return (number != null ? number.intValue() : 0);
	}
	
	public int getNo(String certNo) {
		String sql = "select IFNULL(conn_no,0)  from WJW_CERT_NUM WHERE cert_no='"+certNo+"'";
		System.out.println("sql"+sql);
		Number number = this.jdbcTemplate.queryForObject(sql,Integer.class);
		return (number != null ? number.intValue() : 0);
	}
	
	public void deleteVoucher(String voucher) {
		String sql = "delete from WJW_CERT_NUM where cert_no='"+voucher+"'";
		System.out.println("sql"+sql);
		jdbcTemplate.execute(sql);
	}
	
}
