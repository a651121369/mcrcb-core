package com.untech.mcrcb.web.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.untech.mcrcb.web.enhance.QueryFilter;
import com.untech.mcrcb.web.model.WjwAccount;
import com.untech.mcrcb.web.service.AccountOpenService;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.ResponseData;
import com.unteck.tpc.framework.web.controller.BaseController;

/**
 * WJW_ACCOUNT Controller
 * @author            chenyong
 * @since             2015-11-04
 */
@Controller
@RequestMapping(value="/AccountOpen")
public class AccountOpenController extends BaseController
{
    @Autowired
    private AccountOpenService service;
    
    @RequestMapping(value = "/index")
    public String index(){
        return "/mcrcb-core/acctOpen";
    }
    
    /**
	 * @param node
	 * @param pid 父级ID
	 */
	@RequestMapping("/queryLlzl")
	@ResponseBody
	public List<Map<String, Object>> queryLlzl(String node, Long pid) throws Exception {
		//return service.queryLlzl(pid);
		return null;
	}
    
    @RequestMapping(value = "/pager")
    @ResponseBody
    public Pagination<WjwAccount> pager(HttpServletRequest request){
        QueryFilter filter = new QueryFilter(request);
        return service.findPage(filter);
    }
    
    @RequestMapping(value="/save")
    @ResponseBody
    public ResponseData save(WjwAccount entity)
    {
		if (service.getEntity(entity.getId()) != null) {
           return new ResponseData(false, "记录已存在");
       }	
      service.insertEntity(entity);
      return ResponseData.SUCCESS_NO_DATA;
    }
    
    @RequestMapping(value="/update")
    @ResponseBody
    public ResponseData update(WjwAccount entity)
    {
      WjwAccount oldEntity = (WjwAccount)service.getEntity(entity.getId());
      if (oldEntity == null) {
          return new ResponseData(false, "记录不存在");
      }
      oldEntity.setAccNo(entity.getAccNo());
      oldEntity.setCustName(entity.getCustName());
      oldEntity.setAccType(entity.getAccType());
      oldEntity.setUnitNo(entity.getUnitNo());
      oldEntity.setUnitName(entity.getUnitName());
      oldEntity.setAmount(entity.getAmount());
      oldEntity.setAccNum(entity.getAccNum());
      oldEntity.setAccStatus(entity.getAccStatus());
      oldEntity.setAccParent(entity.getAccParent());
      oldEntity.setCreateTime(entity.getCreateTime());
      oldEntity.setCreateUser(entity.getCreateUser());
      oldEntity.setUpdateTime(entity.getUpdateTime());
      oldEntity.setUpdateUser(entity.getUpdateUser());
      oldEntity.setLevel(entity.getLevel());
      oldEntity.setRate(entity.getRate());
      oldEntity.setBankUnit(entity.getBankUnit());
      oldEntity.setBankName(entity.getBankName());
      oldEntity.setBankAmount(entity.getBankAmount());
      oldEntity.setUnkCome(entity.getUnkCome());
      oldEntity.setIntCome(entity.getIntCome());
      oldEntity.setCurrency(entity.getCurrency());
      oldEntity.setAccFld(entity.getAccFld());
      oldEntity.setAccPro(entity.getAccPro());
      oldEntity.setIntFlag(entity.getIntFlag());
      oldEntity.setIntType(entity.getIntType());
      oldEntity.setInOrOut(entity.getInOrOut());
      oldEntity.setDrugAmt(entity.getDrugAmt());
      oldEntity.setMedicalAmt(entity.getMedicalAmt());
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

