package com.untech.mcrcb.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.untech.mcrcb.web.service.WjwAccountHXService;
import com.untech.mcrcb.web.util.Utils;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.ResponseData;
import com.unteck.tpc.framework.core.util.SecurityContextUtil;

@Controller
@RequestMapping(value="/mcrcb-core")
public class WjwAccountHXController {
	
	@Autowired
	private WjwAccountHXService service;
	
	@RequestMapping(value = "/wjwAccountHx")
	public void openPage() {
	}
	
	@RequestMapping(value = "/getCertNoInfo")
	@ResponseBody
	public Pagination<Map<String, Object>> getCertNoInfo(Integer start, Integer limit,String connNo,String amount,
			String startTime,String endTime,String tranType,String status){
    	return service.getCertNoInfo(start, limit,startTime,endTime,tranType,status, connNo,amount);
    }
	
	@RequestMapping(value = "/changeAccInfoByCertNo")
	@ResponseBody
	public ResponseData changeAccInfoByCertNo(Integer start, Integer limit,String connNo,
			String STATE,String TIME,String AMOUNT,String appTime){

		if("1".equals(STATE)){
			Long number = Utils.dateSubstract(TIME, appTime);
			if(number>5){
				return new ResponseData(false, "缴款核销失败，该笔缴款已经过期");
			}			
		}
		String user =  SecurityContextUtil.getCurrentUser().getUserCode();  //当前用户编号
//    	String dspUserName =  SecurityContextUtil.getCurrentUser().getUserName();//当前用户姓名
//    	int flag = service.saveInfo(connNo, STATE,dspUserno,dspUserName,TIME);
    	int flag = service.tally(connNo, STATE, AMOUNT, user, TIME);
    	
//    	if(flag == 0){
//    		return ResponseData.SUCCESS_NO_DATA;
//    	}else if(flag == 1){
//    		return new ResponseData(false, "凭证单处于非完成状态,不允许记账!");
//    	}
    	
    	//0--失败  1--成功  2--非记账状态   3--已记账   4--记账金额不正确  5--数据库访问异常  6--系统异常
    	if(flag==0){
    		return new ResponseData(false, "记账失败");
    	}else if(flag==1){
    		return new ResponseData(false, "记账成功");
    	}else if(flag==2){
    		return new ResponseData(false, "非记账状态不可以记账");
    	}else if(flag==3){
    		return new ResponseData(false, "该笔纪录已经记账");
    	}else if(flag==4){
    		return new ResponseData(false, "记账金额不正确,记账失败");
    	}else if(flag==5){
    		return new ResponseData(false, "数据库访问异常");
    	}else if(flag==6){
    		return new ResponseData(false, "系统异常");
    	}
    	return new ResponseData(false, "核销失败!");
    }
	
}
