package com.untech.mcrcb.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.untech.mcrcb.web.enhance.QueryFilter;
import com.untech.mcrcb.web.model.WjwAccchange;
import com.untech.mcrcb.web.service.MxQueryService;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.ResponseData;
import com.unteck.tpc.framework.web.controller.BaseController;

/**
 * WJW_ACCCHANGE Controller
 * @author            chenyong
 * @since             2015-11-04
 */
@Controller
@RequestMapping(value="/MxQuery")
public class MxQueryController extends BaseController
{
    @Autowired
    private MxQueryService service;
    
    @RequestMapping(value = "/index")
    public String index(){
        return "/mcrcb-core/MxQuery";
    }
    
    @RequestMapping(value = "/subindex")
    public String subindex(){
        return "/mcrcb-core/MxSubQuery";
    }
    
    @RequestMapping(value = "/pager")
    @ResponseBody
    public Pagination<WjwAccchange> pager(HttpServletRequest request){
        QueryFilter filter = new QueryFilter(request);
        return service.findPage(filter);
    }
    
    @RequestMapping(value = "/queryMx")
	public ModelAndView queryMx(String accNo,String beginTime,String endTime) {
		List<Map<String, Object>> list = service.fetchPager(beginTime, endTime, accNo);
		ModelAndView pageView = new ModelAndView("/mcrcb-core/printMxDetail");
		pageView.addObject("list",list);
		pageView.addObject("beginTime",beginTime);
		pageView.addObject("endTime",endTime);
		pageView.addObject("acctNo",accNo);
		List<Map<String, Object>>  accList  = service.getAcctName(accNo);
		if(accList != null && accList.size() > 0){
			pageView.addObject("acctName",accList.get(0).get("accName").toString());
		}else{
			pageView.addObject("acctName","");
		}
		
		return pageView;
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
    
    @RequestMapping(value="/update")
    @ResponseBody
    public ResponseData update(WjwAccchange entity)
    {
      WjwAccchange oldEntity = (WjwAccchange)service.getEntity(entity.getId());
      if (oldEntity == null) {
          return new ResponseData(false, "记录不存在");
      }
      oldEntity.setUnitNo(entity.getUnitNo());
      oldEntity.setUnitName(entity.getUnitName());
      oldEntity.setAccNo(entity.getAccNo());
      oldEntity.setAccName(entity.getAccName());
      oldEntity.setDfAccno(entity.getDfAccno());
      oldEntity.setDfAccname(entity.getDfAccname());
      oldEntity.setAmount(entity.getAmount());
      oldEntity.setDrugAmt(entity.getDrugAmt());
      oldEntity.setMedcAmt(entity.getMedcAmt());
      oldEntity.setTranAmt(entity.getTranAmt());
      oldEntity.setTranTime(entity.getTranTime());
      oldEntity.setInOrOut(entity.getInOrOut());
      oldEntity.setOtherAmt(entity.getOtherAmt());
      oldEntity.setNote1(entity.getNote1());
      oldEntity.setNote2(entity.getNote2());
      service.updateEntity(oldEntity);
      return ResponseData.SUCCESS_NO_DATA;
    }
	@RequestMapping(value="/delete")
    @ResponseBody
    public ResponseData delete(Long[] ids)
    {
      for (Long id : ids)
      {
        service.deleteEntity(id);
      }
      return ResponseData.SUCCESS_NO_DATA;
    }
    
    
}

