package com.untech.mcrcb.web.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.untech.mcrcb.web.model.DownloadBackup;
import com.unteck.common.dao.jdbc.NamedParameterJdbcPager;
import com.unteck.common.dao.support.Pagination;

@Repository
public class DownloadBackupDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcPager jdbcPager;
	
	public Pagination<Map<String,Object>> getAll(Integer start, Integer limit) {
	  	  String sql = "select id,name,time,address,file_name as fileName from WJW_BEIFEN";
	  	  Pagination<Map<String,Object>> list = jdbcPager.queryPage(sql, start, limit);
	  	  return list;
	}
	
	public void insert(DownloadBackup rce){
		String sql = "insert into WJW_BEIFEN (name,time,address,file_name) "
				+ "values('"+rce.getName()+"', '"+rce.getTime()+"','"+rce.getAddress()+"','"+rce.getFileName()+"')";
		jdbcTemplate.execute(sql);
	}
	
	public List<Map<String, Object>> getSelect(Long id){
		String sql = "select name,time,address,file_name as fileName from WJW_BEIFEN where id='"+id+"'";
		return jdbcTemplate.queryForList(sql);
	}
}
