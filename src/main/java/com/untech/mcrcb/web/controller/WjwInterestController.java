package com.untech.mcrcb.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.untech.mcrcb.web.service.WjwInterestService;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.ResponseData;
import com.unteck.tpc.framework.web.controller.BaseController;

@Controller
@RequestMapping(value="/WjwInterest")
public class WjwInterestController extends BaseController {
	@Autowired
	private WjwInterestService service;
	@RequestMapping(value="/index")
	public String index(){
		return "mcrcb-core/interestOut";
	}

	@RequestMapping(value="/findInterestOut")
	@ResponseBody
	public Pagination<Map<String,Object>> findInterestOut(Integer start,Integer limit,String startTime,String endTime){
		return service.findInterestOut(start,limit,startTime,endTime);
	}
	@RequestMapping(value="/interestPay")
	@ResponseBody
	public ResponseData interestPay(String accNo,String accName,String inAccno,String inAccname,String inBank,
			String payAmt,Integer payWay,String date){
		boolean flag = service.interestPay(accNo,accName,inAccno,inAccname,inBank,payAmt,payWay,date);
		if(flag){
			return new ResponseData(true,"利息支出成功！");
		}
		return new ResponseData(false,"利息支出失败！");
	}
	@RequestMapping(value="/toPrint")
	public ModelAndView toPrint(HttpServletRequest request){
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		return new ModelAndView("mcrcb-core/interestLodopPage");
	}
	@RequestMapping(value="/getInterest")
	@ResponseBody
	public Map<String,Object> getInterest(Long id){
		return service.getInterest(id);
	}
	@RequestMapping(value="/del")
	@ResponseBody
	public ResponseData delete(Long[] ids){
		service.deleteInterest(ids);
		return ResponseData.SUCCESS_NO_DATA;
	}
}
