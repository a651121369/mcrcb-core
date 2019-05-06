package com.untech.mcrcb.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.untech.mcrcb.web.common.Constants;
import com.untech.mcrcb.web.service.PublicService;
import com.untech.mcrcb.web.service.WjwAccountService;
import com.unteck.tpc.framework.core.util.SecurityContextUtil;
import com.unteck.tpc.framework.web.model.admin.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.untech.mcrcb.web.service.WjwCheckerService;
import com.untech.mcrcb.web.util.ListHasMapForm;
import com.untech.mcrcb.web.model.WjwChecker;
import com.untech.mcrcb.web.enhance.QueryFilter;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.ResponseData;
import com.unteck.tpc.framework.web.controller.BaseController;

/**
 * WJW_CHECKER Controller
 * @author            chenyong
 * @since             2015-11-04
 */
@Controller
@RequestMapping(value="/WjwChecker")
public class WjwCheckerController extends BaseController
{
    @Autowired
    private WjwCheckerService service;
    @Autowired
    private WjwAccountService wjwAccountService;

    @Autowired
    private PublicService publicService;



    @RequestMapping(value = "/index")
    public String index(){
        return "/mcrcb-core/WjwChecker";
    }
    
    @RequestMapping(value = "/pagerbak")
    @ResponseBody
    public Pagination<WjwChecker> pagerbak(HttpServletRequest request){
        QueryFilter filter = new QueryFilter(request);
        return service.findPage(filter);
    }
    
    @RequestMapping(value = "/pager")
    @ResponseBody
    public  Pagination<Map<String, Object>> pager(Integer start, Integer limit,String userId,String unitId){
        return service.pager(start,limit,userId,unitId);
    }
    
    @RequestMapping(value="/save")
    @ResponseBody
    public ResponseData save(WjwChecker entity)
    {
		/*if (service.getEntity(entity.getId()) != null) {
           return new ResponseData(false, "记录已存在");
       }	*/
      List<Map<String,Object>> list = service.check(entity.getUnitId());
      if(list != null && list.size() > 0){
    	  return new ResponseData(false, "该机构已经设置初审人员");
      }
      entity.setId(0l);
      service.insertEntity(entity);
      return ResponseData.SUCCESS_NO_DATA;
    }
    
    @RequestMapping(value="/update")
    @ResponseBody
    public ResponseData update(WjwChecker entity)
    {
      WjwChecker oldEntity = (WjwChecker)service.getEntity(entity.getId());
      if (oldEntity == null) {
          return new ResponseData(false, "记录不存在");
      }
      oldEntity.setUserId(entity.getUserId());
      oldEntity.setUnitId(entity.getUnitId());
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
    
	//查询角色对应的人
	@RequestMapping(value="/getCsUser")
    @ResponseBody
	public List<Map<String, Object>> getCsUser(){
		return service.getRoleUser(Constants.USER_ROLE.ROLE_WSY_CHECK,Constants.USER_ROLE.ROLE_JLY_CHECK);
	}
	
	//查询角色对应的人
	@RequestMapping(value="/getFsUser")
    @ResponseBody
	public List<Map<String, Object>> getFsUser(){

        User user = SecurityContextUtil.getCurrentUser();
        Map<String,Object> map = wjwAccountService.getOrganization(user.getOrgId());
        Integer parentId = (Integer) map.get("PARENTID");
        if(parentId==0){
            //判断用户是否拥有卫计委权限的用户
            int userCode=publicService.judgeUserForm(user);
            //是卫计委用户，则查看全部交易记录
            if (Constants.ACC_CODE.WJW == userCode){
                return service.getRoleUser(Constants.USER_ROLE.ROLE_WSY_RECHECK,Constants.USER_ROLE.ROLE_JLY_RECHECK);
                //非卫计委用户
            }else{
                int accCode=publicService.judgeAccountType(user);
                if (Constants.ACC_CODE.WSY ==accCode){
                    return service.getRoleUser(Constants.USER_ROLE.ROLE_WSY_RECHECK);
                }else if(Constants.ACC_CODE.JLY == accCode){
                    return service.getRoleUser(Constants.USER_ROLE.ROLE_JLY_RECHECK);
                }
            }
        }
//		return service.getRoleUser("ROLE_RECHECK");
        return service.getRoleUser(Constants.USER_ROLE.ROLE_WSY_RECHECK,Constants.USER_ROLE.ROLE_JLY_RECHECK);
	}
    
}

