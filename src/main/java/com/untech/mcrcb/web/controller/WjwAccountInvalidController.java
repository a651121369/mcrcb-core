package com.untech.mcrcb.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.untech.mcrcb.web.service.WjwAccountInvalidService;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.ResponseData;

/**
 * WjwAccountInvalid Controller
 * @author            dingkai
 * @since             2016-5-25
 */
@Controller
@RequestMapping(value="/mcrcb-core")
public class WjwAccountInvalidController {
	@Autowired
	private WjwAccountInvalidService service;
	
	/**
	 * 打开凭证作废页面
	 * @return
	 */
	@RequestMapping(value = "/wjwAccountInvalid")
    public String index(){
        return "/mcrcb-core/wjwAccountInvalid";
    }
	
	@RequestMapping(value = "/getAllCertNoInfo")
	@ResponseBody
	public Pagination<Map<String, Object>> getCertNoInfo(Integer start, Integer limit,String connNo,String amount,
			String startTime,String endTime){
    	return service.getAllCertNoInfo(start, limit,startTime,endTime,connNo,amount);
    }
	
	
	 /**
     * 作废操作
     * @param ids
     * @return
     */
	@RequestMapping(value="/invalidCertNoInfo")
    @ResponseBody
    public ResponseData invalidCertNoInfo(Long[] ids){
      service.invalidCertNoInfo(ids);
      return ResponseData.SUCCESS_NO_DATA;
    }
}
