package com.untech.mcrcb.web.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.untech.mcrcb.web.dao.WjwCertNumDao;
import com.unteck.common.dao.support.Pagination;

@Service
public class WjwCertNumService {
	@Autowired
	private WjwCertNumDao dao;
	
	public Pagination<Map<String,Object>>  getPayeracct(Integer start,Integer limit,String certNo){
		Integer connNo = null;
		 if(certNo != null){
			 connNo = dao.getNo(certNo); 
		 }
		return dao. getPayeracct(start,limit,connNo);
	} 
}
