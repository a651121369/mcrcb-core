package com.untech.mcrcb.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.untech.mcrcb.web.service.WjwPaymainService;
import com.untech.mcrcb.web.util.ListHasMapForm;
import com.untech.mcrcb.web.model.WjwPaymain;
import com.untech.mcrcb.web.enhance.QueryFilter;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.ResponseData;
import com.unteck.tpc.framework.web.controller.BaseController;
import com.unteck.tpc.framework.web.dao.admin.UserDao;
import com.unteck.tpc.framework.web.model.admin.User;
import com.unteck.tpc.framework.web.service.admin.UserService;

/**
 * WJW_PAYMAIN Controller
 * @author            chenyong
 * @since             2015-11-04
 */
@Controller
@RequestMapping(value="/WjwPaymain")
public class WjwPaymainController extends BaseController
{
    @Autowired
    private WjwPaymainService service;
    @RequestMapping(value = "/index")
    public String index(){
        return "/mcrcb-core/WjwPaymain";
    }
    
    
    
    @RequestMapping(value="/list")
    @ResponseBody
    public Pagination<Map<String,Object>> getWjwPayMainList(Integer start,Integer limit){
    	return service.getWjwPayMainList(start, limit);
    }
    @RequestMapping(value="/getUsers")
    @ResponseBody
    public List<Map<String,Object>> getUsers(String status,String unitId){
    	return service.getUsers(status,unitId);
    }
    
 
    
    @RequestMapping(value="/update")
    @ResponseBody
    public ResponseData update(WjwPaymain entity)
    {
     	
      WjwPaymain oldEntity = (WjwPaymain)service.getEntity(entity.getId());
      if (oldEntity == null) {
          return new ResponseData(false, "记录不存在");
      }
      Map<String,Object> map = service.getUserName(entity.getDspUserno()).get(0);
//      oldEntity.setUnitName(entity.getUnitName());
//      oldEntity.setUnitNo(entity.getUnitNo());
//      oldEntity.setPayType(entity.getPayType());
//      oldEntity.setSqTime(entity.getSqTime());
//      oldEntity.setStatus(entity.getStatus());
      oldEntity.setDspUserno(entity.getDspUserno());
//      oldEntity.setCsUserno(entity.getCsUserno());
//      oldEntity.setFsUserno(entity.getFsUserno());
//      oldEntity.setCsTime(entity.getCsTime());
//      oldEntity.setFsTime(entity.getFsTime());
//      oldEntity.setConnNo(entity.getConnNo());
      oldEntity.setDspUsername((String) map.get("userName"));
//      oldEntity.setCsUsername(entity.getCsUsername());
//      oldEntity.setFsUsername(entity.getFsUsername());
//      oldEntity.setSqUserno(entity.getSqUserno());
//      oldEntity.setSqUsername(entity.getSqUsername());
//      oldEntity.setRemark(entity.getRemark());
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

