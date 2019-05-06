package com.untech.mcrcb.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.untech.mcrcb.web.service.WjwBfdetailService;
import com.untech.mcrcb.web.util.ListHasMapForm;
import com.untech.mcrcb.web.model.WjwBfdetail;
import com.untech.mcrcb.web.enhance.QueryFilter;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.ResponseData;
import com.unteck.tpc.framework.web.controller.BaseController;

/**
 * WJW_BFDETAIL Controller
 * @author            chenyong
 * @since             2015-11-04
 */
@Controller
@RequestMapping(value="/WjwBfdetail")
public class WjwBfdetailController extends BaseController
{
    @Autowired
    private WjwBfdetailService service;
    
    @RequestMapping(value = "/index")
    public String index(){
        return "/mcrcb-core/WjwBfdetail";
    }
    
    @RequestMapping(value = "/pager")
    @ResponseBody
    public Pagination<WjwBfdetail> pager(HttpServletRequest request){
        QueryFilter filter = new QueryFilter(request);
        return service.findPage(filter);
    }
    
    @RequestMapping(value = "/getBfdetailList")
    @ResponseBody
    public Pagination<Map<String,Object>> getBfdetailList(Integer start,Integer limit,
    		Long id,String unitNo,String unitName,String connNo,String startTime,String endTime){
        return service.getBfdetailList(start,limit,id,unitNo,unitName,connNo,startTime,endTime);
    }
    
    @RequestMapping(value="/save")
    @ResponseBody
    public ResponseData save(WjwBfdetail entity)
    {
		if (service.getEntity(entity.getId()) != null) {
           return new ResponseData(false, "记录已存在");
       }	
      service.insertEntity(entity);
      return ResponseData.SUCCESS_NO_DATA;
    }
    
    @RequestMapping(value="/update")
    @ResponseBody
    public ResponseData update(WjwBfdetail entity)
    {
      WjwBfdetail oldEntity = (WjwBfdetail)service.getEntity(entity.getId());
      if (oldEntity == null) {
          return new ResponseData(false, "记录不存在");
      }
      oldEntity.setUnitNo(entity.getUnitNo());
      oldEntity.setAmount(entity.getAmount());
      oldEntity.setUnitName(entity.getUnitName());
      oldEntity.setBfTime(entity.getBfTime());
      oldEntity.setBfItem(entity.getBfItem());
      oldEntity.setInBfqAmt(entity.getInBfqAmt());
      oldEntity.setInBfhAmt(entity.getInBfhAmt());
      oldEntity.setOutBfqAmt(entity.getOutBfqAmt());
      oldEntity.setOutBfhAmt(entity.getOutBfhAmt());
      oldEntity.setConnNo(entity.getConnNo());
      oldEntity.setDrugBfAmt(entity.getDrugBfAmt());
      oldEntity.setMedcBfAmt(entity.getMedcBfAmt());
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

