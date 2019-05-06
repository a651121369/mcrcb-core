package com.untech.mcrcb.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.untech.mcrcb.web.service.WjwPayeracctService;
import com.untech.mcrcb.web.util.ListHasMapForm;
import com.untech.mcrcb.web.model.WjwPayeracct;
import com.untech.mcrcb.web.enhance.QueryFilter;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.ResponseData;
import com.unteck.tpc.framework.core.util.SecurityContextUtil;
import com.unteck.tpc.framework.web.controller.BaseController;

/**
 * WJW_PAYERACCT Controller
 * @author            liuyong
 * @since             2015-11-16
 */
@Controller
@RequestMapping(value="/WjwPayeracct")
public class WjwPayeracctController extends BaseController
{
	@Autowired
    private WjwPayeracctService service;
    
    @RequestMapping(value = "/index")
    public String index(){
        return "/mcrcb-core/WjwPayeracct";
    }
    
    /**
     * 打开收款账户信息页面
     * @author dingkai
     * @since  2016-5-24
     */
    @RequestMapping(value = "/payeracct")
    public String openPage(){
        return "/mcrcb-core/WjwPayeracct";
    }
    
    @RequestMapping(value = "/pager")
    @ResponseBody
    public Pagination<Map<String,Object>> pager(Integer start,Integer limit, String acctNox,String acctNamex,String acctBankx){
    	String unitNo = SecurityContextUtil.getCurrentUser().getOrgId()+"";
//        QueryFilter filter = new QueryFilter(request);
//        return service.findPage(filter);
    	return service.getPayeracct(start, limit,unitNo,acctNox,acctNamex,acctBankx);
    }
    
    @RequestMapping(value="/save")
    @ResponseBody
    public ResponseData save(WjwPayeracct entity)
    {
//		if (service.getEntity(entity.getId()) != null) {
//           return new ResponseData(false, "记录已存在");
//       }
      entity.setUnitNo(SecurityContextUtil.getCurrentUser().getOrgId()+"");
      service.insert(entity);
      
      return ResponseData.SUCCESS_NO_DATA;
    }
    
    @RequestMapping(value="/update")
    @ResponseBody
    public ResponseData update(WjwPayeracct entity)
    {
      WjwPayeracct oldEntity = (WjwPayeracct)service.getEntity(entity.getId());
      if (oldEntity == null) {
          return new ResponseData(false, "记录不存在");
      }
      oldEntity.setAcctNo(entity.getAcctNo());
      oldEntity.setAcctName(entity.getAcctName());
      oldEntity.setAcctBank(entity.getAcctBank());
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

