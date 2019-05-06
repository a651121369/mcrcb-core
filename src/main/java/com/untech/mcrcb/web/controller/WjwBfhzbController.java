package com.untech.mcrcb.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.untech.mcrcb.web.enhance.QueryFilter;
import com.untech.mcrcb.web.model.WjwBfhzb;
import com.untech.mcrcb.web.service.WjwBfhzbService;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.ResponseData;
import com.unteck.tpc.framework.web.controller.BaseController;

/**
 * WJW_BFHZB Controller
 * @author            chenyong
 * @since             2015-11-04
 */
@Controller
@RequestMapping(value="/WjwBfhzb")
public class WjwBfhzbController extends BaseController
{
    @Autowired
    private WjwBfhzbService service;
    
    @RequestMapping(value = "/index")
    public String index(){
        return "/mcrcb-core/WjwBfhzb";
    }
    @RequestMapping(value="/toAppropriatePrint")
    public ModelAndView toAppropriatePrint(HttpServletRequest request){
    	request.setAttribute("id", request.getParameter("id"));
    	return new ModelAndView("/mcrcb-core/appropriateLodopPage");
    }
    @RequestMapping(value="/appropriatePrint")
    @ResponseBody
    public List<Map<String,Object>> appropriatePrint(Long id){
    	return service.appropriatePrint(id);
    }
    @RequestMapping(value = "/index2")
    public String index2(Long id,ModelMap modelMap){
    	service.printList(id,modelMap);
        return "/mcrcb-core/WjwBfhList";
    }
    @RequestMapping(value="/toAppropriateDetail")
    public ModelAndView toAppropriateDetail(HttpServletRequest request){
    	request.setAttribute("id", request.getParameter("id"));
    	return new ModelAndView("/mcrcb-core/appropriateDetail");
    }
    @RequestMapping(value="/appropriateDetail")
    @ResponseBody
    public Pagination<Map<String,Object>> appropriateDetail(Long id,Integer start,Integer limit){
    	System.out.println("start:"+start);
    	System.out.println("limit:"+limit);
    	return service.findAppropriateDetail(id,start,limit);
    }
    
    @RequestMapping(value="dowloadAppropriateDetail")
    public void dowloadAppropriateDetail(HttpServletResponse response,Long id) throws IOException{
    	String fileName = "蒙城卫健委拨付明细_"+new SimpleDateFormat("yyyyMMdd").format(new Date());
    	response.setHeader("Content-disposition", "attchment;filename="+new String(fileName.getBytes("gbk"),"ISO_8859_1")+".xls");
    	response.setContentType("application/vnd.ms-excel");
    	InputStream in = service.dowloadAppropriateDetail(id);
    	OutputStream out = response.getOutputStream();
    	byte[] buf = new byte[2048];
    	try {
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
    @RequestMapping(value = "/getBfHzbList")
    @ResponseBody
    public Pagination<Map<String,Object>> getBfHzbList(Integer start,Integer limit,
    		String operNo,String startTime,String endTime){
        return service.getBfHzbList(start,limit,operNo,startTime,endTime);
    }
    
    @RequestMapping(value = "/pager")
    @ResponseBody
    public Pagination<WjwBfhzb> pager(HttpServletRequest request){
        QueryFilter filter = new QueryFilter(request);
        return service.findPage(filter);
    }


    @RequestMapping(value = "/getAllIncomeAccount")
    @ResponseBody
    public Pagination<Map<String,Object>> getAllIncomeAccount(){
        return service.getAllIncomeAccount();
    }

    @ResponseBody
    @RequestMapping(value = "/getWjwIncomeAccountList")
    public List<Map<String,Object>> getWjwIncomeAccountList(){
        return service.getWjwIncomeAccountList();
    }

    @ResponseBody
    @RequestMapping(value = "/getJlyIncomeAccountList")
    public List<Map<String,Object>> getJlyIncomeAccountList(){
        return service.getJlyIncomeAccountList();
    }

    @RequestMapping(value="/save")
    @ResponseBody
    public ResponseData save(WjwBfhzb entity)
    {
//		if (service.getEntity(entity.getId()) != null) {
//           return new ResponseData(false, "记录已存在");
//       }	
//      service.insertEntity(entity);
      return ResponseData.SUCCESS_NO_DATA;
    }
    
    /**
     * 开始拨付
     * @param request
     * @return
     */
    @RequestMapping(value="/appropriate")
    @ResponseBody
    public ResponseData appropriate(HttpServletRequest request){
    	return service.appropriate(request);
    	//return ResponseData.SUCCESS_NO_DATA;
    }
    
    @RequestMapping(value="/update")
    @ResponseBody
    public ResponseData update(WjwBfhzb entity)
    {
      WjwBfhzb oldEntity = (WjwBfhzb)service.getEntity(entity.getId());
      if (oldEntity == null) {
          return new ResponseData(false, "记录不存在");
      }
      oldEntity.setUnitNo(entity.getUnitNo());
      oldEntity.setUnitName(entity.getUnitName());
      oldEntity.setBfAmt(entity.getBfAmt());
      oldEntity.setBfDrugAmt(entity.getBfDrugAmt());
      oldEntity.setBfMedcAmt(entity.getBfMedcAmt());
      oldEntity.setBfTim(entity.getBfTim());
      oldEntity.setOperNo(entity.getOperNo());
      oldEntity.setOperTime(entity.getOperTime());
      oldEntity.setDetail(entity.getDetail());
      oldEntity.setConnNo(entity.getConnNo());
      oldEntity.setRemark(entity.getRemark());
      oldEntity.setNote1(entity.getNote1());
      oldEntity.setNote2(entity.getNote2());
      service.updateEntity(oldEntity);
      return ResponseData.SUCCESS_NO_DATA;
    }
	@RequestMapping(value="/delete")
    @ResponseBody
    public ResponseData delete(Long[] ids)
    {
//      for (Long id : ids)
//      {
//        service.deleteEntity(id);
//      }
		service.deleteBF(ids);
      return ResponseData.SUCCESS_NO_DATA;
    }
    
	@RequestMapping(value="/zf")
    @ResponseBody
    public ResponseData zf(Long[] ids)
    {
		service.zfBF(ids);
      return ResponseData.SUCCESS_NO_DATA;
    }
    
}

