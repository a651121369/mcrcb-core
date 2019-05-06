package com.untech.mcrcb.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.untech.mcrcb.web.common.Constants;
import com.untech.mcrcb.web.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.untech.mcrcb.web.service.WjwAccchangeService;
import com.untech.mcrcb.web.util.ListHasMapForm;
import com.untech.mcrcb.web.model.WjwAccchange;
import com.untech.mcrcb.web.enhance.QueryFilter;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.ResponseData;
import com.unteck.tpc.framework.core.util.SecurityContextUtil;
import com.unteck.tpc.framework.web.controller.BaseController;
import com.unteck.tpc.framework.web.model.admin.User;

/**
 * WJW_ACCCHANGE Controller
 * @author            chenyong
 * @since             2015-11-04
 */
@Controller
@RequestMapping(value="/WjwAccchange")
public class WjwAccchangeController extends BaseController
{
    @Autowired
    private WjwAccchangeService service;

    @Autowired
    private PublicService publicService;

    
    @RequestMapping(value = "/index")
    public String index(){
        return "/mcrcb-core/WjwAccchange";
    }
    
    @RequestMapping(value="/tradeDetail")
    public String tradeDetail(){
    	return "/mcrcb-core/tradeDetail";
    }
    
    @RequestMapping(value = "/queryTradeDetail")
    @ResponseBody
    public Pagination<Map<String,Object>> queryTradeDetail(Integer start,Integer limit,String startTime,String endTime,String accNo){
    	User user = SecurityContextUtil.getCurrentUser();
    	Map<String,Object> map = service.getOrganization(user.getOrgId().toString());
    	Integer parentId = (Integer) map.get("PARENTID");
    	if(parentId==0){
    	    //判断用户是否拥有卫计委权限的用户
            int userCode=publicService.judgeUserForm(user);
            //是卫计委用户，则查看全部交易记录
            if (Constants.ACC_CODE.WJW == userCode){
                return service.queryTradeDetail(start,limit,startTime,endTime,accNo,"");
                //非卫计委用户
            }else{
                int accCode=publicService.judgeAccountType(user);
                return service.queryTradeDetailChanage(start,limit,startTime,endTime,accNo,"",accCode);
            }
    	}
    	return service.queryTradeDetail(start,limit,startTime,endTime,accNo,user.getOrgId().toString());
    }
    
    @RequestMapping(value = "/downloadTradeDetail")
    public void downloadTradeDetail(HttpServletResponse response,String startTime,String endTime,String accNo) throws IOException{
    	String filename = "蒙城卫健委交易明细_"+new SimpleDateFormat("yyyyMMdd").format(new Date());
    	response.setHeader("Content-disposition", "attachment;filename="+new String(filename.getBytes("gbk"),"ISO_8859_1")+".xls");
    	response.setContentType("application/vnd.ms-excel");
    	byte[] buf = new byte[2048];
    	OutputStream out = null;
    	InputStream in = null;
    	try {
			out = response.getOutputStream();
			in = service.downloadTradeDetail(startTime,endTime,accNo);
			while((in.read(buf))!=-1){
				out.write(buf);
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null!=out){
				out.close();
			}
			if(null!=in){
				in.close();
			}
		}
    }
    
    @RequestMapping(value = "/findDqfDetails")
    @ResponseBody
    public Pagination<Map<String,Object>> findDqfDetails(Integer start,Integer limit,String startTime,String endTime){
        User user = SecurityContextUtil.getCurrentUser();
        int accCode= publicService.judgeAccountType(user);
        Map<String,Object> map = service.getOrganization(user.getOrgId().toString());
        Integer parentId = (Integer) map.get("PARENTID");
        if(parentId==0){
            //判断用户是否拥有卫计委权限的用户
            int userCode=publicService.judgeUserForm(user);
            //是卫计委用户，则查看全部交易记录
            if (Constants.ACC_CODE.WJW == userCode){
                return service.findDqfDetails(start,limit,startTime,endTime);
                //非卫计委用户
            }else{
                 accCode=publicService.judgeAccountType(user);
                return service.findDqfDetailsByAccCode(start,limit,accCode,startTime,endTime);
            }
        }

//        return service.findDqfDetails(start,limit,startTime,endTime);
        return service.findDqfDetailsByAccCode(start,limit,accCode,startTime,endTime);
    }
    
    @RequestMapping(value = "/findQfMx")
    @ResponseBody
    public Pagination<Map<String,Object>> findQfMx(HttpServletRequest request,Integer start,Integer limit,String startTime,String endTime){
    	String note2 = request.getParameter("note2");
    	return service.findQfMx(note2,start,limit,startTime,endTime);
    }
    @RequestMapping(value = "/qfMx")
    public ModelAndView openPayDetailMx(HttpServletRequest request){
    	request.setAttribute("accNo",request.getParameter("accNo"));
    	request.setAttribute("note2",request.getParameter("note2"));
    	return new ModelAndView("/mcrcb-core/WjwAccchangeQfMx");
    }
    
    @RequestMapping(value = "/findLxDetails")
    @ResponseBody
    public Pagination<Map<String,Object>> findLxDetails(Integer start,Integer limit,String startTime,String endTime){
    	return service.findLxDetails(start,limit,startTime,endTime);
    }
    
    /**
     * 待清分撤销
     */
    @RequestMapping(value="/dqfRollback")
    @ResponseBody
    public ResponseData dqfRollback(Long id,Long status){
    	WjwAccchange entity = service.getEntity(id);
    	if(!service.dqfRollback(entity,status)){
    		return new ResponseData(false,"待清分账务撤销失败");
    	}
    	return new ResponseData(true,"待清分账务撤销成功");
    }
    
    @RequestMapping(value="/save")
    @ResponseBody
    public ResponseData save(WjwAccchange entity)
    {
		if (service.getEntity(entity.getId()) != null) {
           return new ResponseData(false, "记录已存在");
       }	
      service.insertEntity(entity);
      return ResponseData.SUCCESS_NO_DATA;
    }
    
    /**
     * 利息入账撤销
     */
    @RequestMapping(value="/lxRollback")
    @ResponseBody
    public ResponseData lxRollback(Long id,Long status){
    	WjwAccchange entity = service.getEntity(id);
    	if(!service.lxRollback(entity,status)){
    		return new ResponseData(false,"利息入账撤销失败");
    	}
    	return new ResponseData(true,"利息入账撤销成功");
    }


    
    
}

