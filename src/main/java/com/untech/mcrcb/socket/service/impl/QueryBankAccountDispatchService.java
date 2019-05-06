package com.untech.mcrcb.socket.service.impl;

import java.util.Map;

import com.untech.mcrcb.socket.service.AbstractDispatchService;
import com.untech.mcrcb.tran.service.QueryBankAccountService;
import com.unteck.tpc.framework.core.spring.SpringApplicationContextHolder;

public class QueryBankAccountDispatchService extends AbstractDispatchService{
	
	private String wjwCode;
	private QueryBankAccountService service = SpringApplicationContextHolder.getBean(QueryBankAccountService.class);
	public QueryBankAccountDispatchService(String type, String[] request)
			throws Exception {
		super(type, request);
		this.wjwCode = request[0];
	}

	@Override
	public void handler() throws Exception {
		try {
			this.response = new String[7];
			Map<String,Object> map = service.queryBankAccount();
			if(null==map){
				responseCode = "9999";
				responseMessage = "查询真实账户失败";
			}else{
				this.response[3] = map.get("IN_ACCNO").toString();
				this.response[4] = map.get("IN_ACCNAME").toString();
				this.response[5] = map.get("OUT_ACCNO").toString();
				this.response[6] = map.get("OUT_ACCNAME").toString();
				responseCode = "0000";
				responseMessage = "交易成功";				
			}
			this.response[0] = wjwCode;
			this.response[1] = responseCode;
			this.response[2] = responseMessage;
		} catch (Exception e) {
			e.printStackTrace();
			responseCode = "9999";
			responseMessage = "交易失败，系统异常";
			
			this.response[0] = wjwCode;
			this.response[1] = responseCode;
			this.response[2] = responseMessage;
		}

	}


}
