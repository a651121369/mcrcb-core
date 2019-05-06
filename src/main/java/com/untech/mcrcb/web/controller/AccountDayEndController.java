package com.untech.mcrcb.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.untech.mcrcb.web.service.AccountDayEndService;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.ResponseData;

@Controller
@RequestMapping(value="/mcrcb-core")
public class AccountDayEndController {
	
	@Autowired
	private AccountDayEndService service;
	
	@RequestMapping(value = "/wjwAccountDayEnd")
	public void openPage() {
	}
	
	@RequestMapping(value = "/accountInfo")
    @ResponseBody
    public Map<String,Object> getAccountInfo(){
        return service.getAccountInfo();
    }
	
	@RequestMapping(value = "/getAccDayEndInfo")
	@ResponseBody
	public Pagination<Map<String, Object>> getAccDayEndInfo(Integer start, Integer limit,String PAY_ACC_TRUE
			,String PAY_ACCNAME_TRUE,String GET_ACC_TRUE,String GET_ACCNAME_TRUE){
    	return service.getAccDayEndInfo(start, limit, PAY_ACC_TRUE,PAY_ACCNAME_TRUE,GET_ACC_TRUE,GET_ACCNAME_TRUE);
    }
	
	@RequestMapping(value = "/addAccDayEndInfo")
	@ResponseBody
	public ResponseData addAccDayEndInfo(String PAY_ACC_TRUE,String PAY_ACCNAME_TRUE,String PAY_ACCMONEY_TRUE,
			String GET_ACC_TRUE,String GET_ACCNAME_TRUE,String GET_ACCMONEY_TRUE){
		System.out.println("进入日结");
		System.out.println("PAY_ACC_TRUE = " + PAY_ACC_TRUE);
		System.out.println("PAY_ACCNAME_TRUE = " + PAY_ACCNAME_TRUE);
		System.out.println("PAY_ACCMONEY_TRUE = " + PAY_ACCMONEY_TRUE);
		System.out.println("GET_ACC_TRUE = " + GET_ACC_TRUE);
		System.out.println("GET_ACCNAME_TRUE = " + GET_ACCNAME_TRUE);
		System.out.println("GET_ACCMONEY_TRUE = " + GET_ACCMONEY_TRUE);
		String flag = service.addAccDayEndInfo(PAY_ACC_TRUE, PAY_ACCNAME_TRUE, PAY_ACCMONEY_TRUE, GET_ACC_TRUE, GET_ACCNAME_TRUE, GET_ACCMONEY_TRUE);
		System.out.println("flag = " + flag);
		if("0".equals(flag)){
			return new ResponseData(false,"未查询到待日结的账户,且确认账户信息！");
		}
		else if("1".equals(flag)){
			return new ResponseData(true,"账户日结成功！");
		}
		else if("2".equals(flag)){
			return new ResponseData(false,"账户日结失败,支出主账户余额与虚拟支出主账户余额不符！");
		}
		else if("3".equals(flag)){
			return new ResponseData(false,"账户日结失败,虚拟支出主账户余额与虚拟支出子账户账户余额不符！");
		}
		else if("4".equals(flag)){
			return new ResponseData(false,"账户日结失败,收入主账户余额与虚拟收入主账户余额不符！");
		}
		else if("5".equals(flag)){
			return new ResponseData(false,"账户日结失败,虚拟收入主账户余额与虚拟收入子账户账户余额不符！");
		}else{
			return new ResponseData(false,"日结失败！");
		}
		
	}

}
