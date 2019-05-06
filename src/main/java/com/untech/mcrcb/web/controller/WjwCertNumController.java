package com.untech.mcrcb.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.untech.mcrcb.web.service.WjwCertNumService;
import com.unteck.common.dao.support.Pagination;

@Controller
@RequestMapping(value="/wjwCertNum")
public class WjwCertNumController {

	@Autowired
	private WjwCertNumService service;
	
	//打开凭证号查询页面
	@RequestMapping(value="/open")
    public ModelAndView open(){
    	return new ModelAndView("/mcrcb-core/certNum");
    }
	
	//根据条件查询
    @RequestMapping(value = "/pager")
    @ResponseBody
    public Pagination<Map<String,Object>> pager(Integer start,Integer limit, String certNo){
    	return service. getPayeracct(start,limit,certNo);
    }
	
}
