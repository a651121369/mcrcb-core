package com.untech.mcrcb.tran.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.untech.mcrcb.web.dao.WjwAccountDao;
import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.enhance.BaseService;
@Service
public class QueryAccountService extends BaseService<T, Long>{
	
	
	@Autowired
	private WjwAccountDao dao;
	@Override
	public BaseDao<T, Long> getHibernateBaseDao() {
		
		return this.getHibernateBaseDao();
	}
	
	public List<Map<String,Object>> QueryAccount(String tallyType){
		return dao.QueryAccount(tallyType);
	}

	public Map<String, Object> QueryAccount(String tallyType, String accountNo) {
		
		return dao.QueryAccount(tallyType,accountNo);
	}
}
