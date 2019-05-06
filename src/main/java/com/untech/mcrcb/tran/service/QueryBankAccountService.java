package com.untech.mcrcb.tran.service;


import java.util.Map;

import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.untech.mcrcb.web.dao.WjwAccountDao;
import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.enhance.BaseService;

@Service
public class QueryBankAccountService extends BaseService<T,Long>{
	@Autowired
	private WjwAccountDao dao;
	@Override
	public BaseDao<T, Long> getHibernateBaseDao() {
		
		return this.getHibernateBaseDao();
	}

	public Map<String, Object> queryBankAccount() {
		
		return dao.queryBankAccount();
	}

}
