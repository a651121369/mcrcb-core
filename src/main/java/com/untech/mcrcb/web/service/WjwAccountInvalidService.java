package com.untech.mcrcb.web.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.untech.mcrcb.tran.service.AccountingBaseService;
import com.untech.mcrcb.web.dao.WjwAccountInvalidDao;
import com.unteck.common.dao.support.Pagination;

@Service
public class WjwAccountInvalidService extends AccountingBaseService{
	@Autowired
	private WjwAccountInvalidDao wjwAccountInvalidDao;
	
	/**
	 * 获取所有支付凭证信息
	 * @param start
	 * @param limit
	 * @param startTime
	 * @param endTime
	 * @param connNo
	 * @param amount
	 * @return
	 */
	public Pagination<Map<String, Object>> getAllCertNoInfo(Integer start, Integer limit,String startTime,String endTime,
			String connNo,String amount){
			//支付
			return wjwAccountInvalidDao.payDetail(start,limit,startTime,endTime,connNo,amount);
			//return wjwAccountInvalidDao.getCertNoInfo(start, limit, startTime,endTime,status, connNo,amount);		   	
    }

	/**
	 * 作废
	 * @param ids
	 */
	public void invalidCertNoInfo(Long[] ids) {
		for (Long id : ids){
			wjwAccountInvalidDao.invalidCertNoInfo(id);
	      }
	}
}
