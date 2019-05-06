//-------------------------------------------------------------------------
package com.untech.mcrcb.web.controller;

import java.util.List;
import java.util.Map;

import com.untech.mcrcb.web.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.untech.mcrcb.web.service.PublicService;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.SecurityContextUtil;
import com.unteck.tpc.framework.web.controller.BaseController;
import com.unteck.tpc.framework.web.model.admin.User;

/**
 *
 * @author            fanhua
 * @version           1.0
 * @since             2015年11月5日
 */
@RequestMapping(value="/public/")
@Controller
public class PublicController extends BaseController {

	@Autowired
	private PublicService publicService;

	@RequestMapping(value="/getOrganization")
	@ResponseBody
	public List<Map<String, Object>> getOrganization(){
		User user = SecurityContextUtil.getCurrentUser();
		Map<String, Object> map = publicService.getOrganization(user.getOrgId().toString()).get(0);
		Integer parentId = (Integer)map.get("PARENTID");
		if(parentId==0){
			//判断用户是否拥有卫计委权限的用户
			int userCode=publicService.judgeUserForm(user);
			//是卫计委用户，则查看全部交易记录
			if (Constants.ACC_CODE.WJW == userCode){
				return publicService.getOrganization("");
				//非卫计委用户
			}else{
				int accCode=publicService.judgeAccountType(user);
				return publicService.getOrganization("",accCode);
			}
		}else{
			return publicService.getOrganization(user.getOrgId().toString());
		}
	}

	@RequestMapping(value="/getAccCode")
	@ResponseBody
	public List<Map<String, Object>> getAccCode(){
		return publicService.getAccCode();
	}


	@RequestMapping(value="/getOrganizationbf")
	@ResponseBody
	public List<Map<String, Object>> getOrganizationbf(){
		User user = SecurityContextUtil.getCurrentUser();
		Map<String, Object> map = publicService.getOrganization(user.getOrgId().toString()).get(0);
		Integer parentId = (Integer)map.get("PARENTID");
		if(parentId==0){
			return publicService.getOrganization("",Constants.ACC_CODE.WSY);
		}else{
			return publicService.getOrganization(user.getOrgId().toString());
		}
	}

	@RequestMapping(value="/getInAcc")
	@ResponseBody
	 public Pagination<Map<String,Object>> getInAcc(Integer start,Integer limit,String acctName){
		String unitNo = SecurityContextUtil.getCurrentUser().getOrgId()+"";
		return publicService.findInAccInfoByInName(start, limit,unitNo,acctName);
	}
}

