package com.untech.mcrcb.socket.server.support;

import com.untech.mcrcb.socket.service.AbstractDispatchService;
import com.untech.mcrcb.socket.service.impl.DayEndDispatchService;
import com.untech.mcrcb.socket.service.impl.QueryAccountDispatchService;
import com.untech.mcrcb.socket.service.impl.QueryBankAccountDispatchService;
import com.untech.mcrcb.socket.service.impl.TallyDispatchService;
import com.untech.mcrcb.socket.service.impl.TallyReversedDispatchService;
import com.untech.mcrcb.socket.service.impl.VoucherDispatchService;

public class ServiceFactoryBean {

	
	public static AbstractDispatchService getServiceBean(String type,String[] request) throws Exception{
		
		if("0001".equals(type)){
			//记账
			return new TallyDispatchService(type,request);
		}else if("0002".equals(type)){
			//凭证查询
			return new VoucherDispatchService(type,request);
		}else if("0003".equals(type)){
			//账户余额查询
			return new QueryAccountDispatchService(type, request);
		}else if("0004".equals(type)){
			//日结
			return new DayEndDispatchService(type, request);
		}else if("0005".equals(type)){
			//查询真实账户
			return new QueryBankAccountDispatchService(type, request);
		}else if("0007".equals(type)){
			//记账冲正
			return new TallyReversedDispatchService(type,request);
		}
//		else if("0008".equals(type)){
//			//待清分入账冲销
//			return new DQFTallyReversedDispatchService(type,request);
//		}
		return null;
	}
	
}
