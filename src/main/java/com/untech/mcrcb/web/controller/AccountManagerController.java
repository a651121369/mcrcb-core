package com.untech.mcrcb.web.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.validator.internal.util.privilegedactions.SetAccessibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.untech.mcrcb.web.enhance.QueryFilter;
import com.untech.mcrcb.web.model.WjwAccount;
import com.untech.mcrcb.web.service.AccountManagerService;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.ResponseData;
import com.unteck.tpc.framework.core.util.SecurityContextUtil;
import com.unteck.tpc.framework.web.controller.BaseController;

/**
 * WJW_ACCOUNT Controller
 * 
 * @author chenyong
 * @since 2015-11-04
 * 
 * create unique index WJW_PAYDETAIL_index  on  WJW_PAYDETAIL(OPER_NO);
  
  create unique index WJW_INCOMEDETAIL_index  on  WJW_INCOMEDETAIL(CERT_NO);
  
  create unique index WJW_BFHZB_index  on  WJW_BFHZB(CERT_NO);
 */
@Controller
@RequestMapping(value = "/AccountManager")
public class AccountManagerController extends BaseController {
	@Autowired
	private AccountManagerService service;

	@RequestMapping(value = "/index")
	public String index() {
		return "mcrcb-core/SubAccountOpen";
	}

	@RequestMapping(value = "/indexopen")
	public String indexopen() {
		return "mcrcb-core/AccountOpen";
	}

	@RequestMapping(value = "/pager")
	@ResponseBody
	public Pagination<WjwAccount> pager(HttpServletRequest request) {
		QueryFilter filter = new QueryFilter(request);
		return service.findPage(filter);
	}

	@RequestMapping(value = "/pageropen")
	@ResponseBody
	public Pagination<Map<String, Object>> pageropen(String accNo,
			String custName, Integer start, Integer limit) {
		return this.service.fetchPager(accNo, custName, start, limit);
	}

	@RequestMapping(value = "/subpageropen")
	@ResponseBody
	public Pagination<Map<String, Object>> subpageropen(String accNo,
			String custName,String accCode, Integer start, Integer limit) {
		return this.service.subfetchPagerByaccCode(accNo, custName,accCode, start, limit);
	}

	@RequestMapping(value = "/save")
	@ResponseBody
	public ResponseData save(WjwAccount entity) {
		/*
		 * if (service.getEntity(entity.getId()) != null) { return new
		 * ResponseData(false, "记录已存在"); }
		 */
		entity.setId(0l);
		service.insertEntity(entity);
		return ResponseData.SUCCESS_NO_DATA;
	}

	@RequestMapping(value = "/saveopen")
	@ResponseBody
	public ResponseData saveopen(WjwAccount entity) {
		/*
		 * if (service.getEntity(entity.getId()) != null) { return new
		 * ResponseData(false, "记录已存在"); }
		 */
		entity.setId(0l);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String time = format.format(new Date());
		entity.setCreateUser(SecurityContextUtil.getCurrentUser().getUserCode());
		entity.setCreateTime(time);
		entity.setOtherAmt(new BigDecimal(0.00));
		entity.setUnkCome(new BigDecimal(0.00));
		entity.setIntCome(new BigDecimal(0.00));
		entity.setAccParent("0");
		entity.setUpdateTime("");
		entity.setUpdateUser("");
		entity.setLevel(0);
		//维护机构号
		List<Map<String,Object>> list = service.gettopOrg("");
		if(list != null && list.size() >0){
			String orgNo = list.get(0).get("id").toString();
			String orgName = list.get(0).get("name").toString();
			entity.setUnitNo(orgNo);
			entity.setUnitName(orgName);
		}
		service.insertEntity(entity);
		return ResponseData.SUCCESS_NO_DATA;
	}
	/** TODO:开通账户
	 * @Author Mr.lx
	 * @Date 2019/3/27 0027
	* @param entity
	 * @return com.unteck.tpc.framework.core.util.ResponseData
	 **/
	@RequestMapping(value = "/subsaveopen")
	@ResponseBody
	public ResponseData subsaveopen(WjwAccount entity) {
		/*
		 * if (service.getEntity(entity.getId()) != null) { return new
		 * ResponseData(false, "记录已存在"); }
		 */
		System.out.println(entity.getUnitNo() + "------------------");
		entity.setId(0l);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String time = format.format(new Date());
		entity.setCreateUser(SecurityContextUtil.getCurrentUser().getUserCode());
		entity.setCreateTime(time);
		entity.setOtherAmt(new BigDecimal(0.00));
		// 查找主账户
		List<Map<String, Object>> list = service.getMainAccoutByCode(entity.getAccCode(),entity.getInOrOut());
		if (list != null && list.size() > 0) {
			String parentAcct = list.get(0).get("accNo").toString();
			// String parentAcctName = list.get(0).get("custName").toString();
			entity.setAccParent(parentAcct);
		} else {
			return new ResponseData(false, "主账号未维护");
		}
		List<Map<String, Object>> orgList = service.getOrgInfo(entity
				.getUnitNo());
		if (orgList != null && orgList.size() > 0) {
			String orgName = orgList.get(0).get("orgName").toString();
			entity.setUnitName(orgName);
		} else {
			return new ResponseData(false, "机构信息未维护");
		}
		entity.setLevel(2);
		entity.setUpdateTime("");
		entity.setUpdateUser("");
		entity.setBankAmount(new BigDecimal(0.00));
		entity.setUnkCome(new BigDecimal(0.00));
		entity.setIntCome(new BigDecimal(0.00));
		entity.setBankUnit("");
		entity.setBankName("");
		service.insertEntity(entity);
		return ResponseData.SUCCESS_NO_DATA;
	}

	@RequestMapping(value = "/open")
	@ResponseBody
	public ResponseData open(Long[] ids) {
		WjwAccount oldEntity = (WjwAccount) service.getEntity(ids[0]);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String time = format.format(new Date());
		List<Map<String, Object>> list = service.check(oldEntity.getInOrOut()
				+ "");
		// 将主账户进行开通
		if (oldEntity.getAccFld() == 1) {
			if (list != null && list.size() > 0) {
				System.out.println("000000000000");
				return new ResponseData(false, "该账户已开通");

				// return ResponseData.FAILED_DEL_OWNROLE;
			}
			WjwAccount mainAcct = new WjwAccount();
			WjwAccount qfAcct = new WjwAccount();
			if (oldEntity.getInOrOut() == 1) {

				mainAcct.setAccNo("010000");
				qfAcct.setAccNo("019999");
				mainAcct.setAccParent(oldEntity.getAccNo());
				qfAcct.setAccParent("010000");
				mainAcct.setInOrOut(1);
				qfAcct.setInOrOut(1);
			} else if (oldEntity.getInOrOut() == 2) {
				mainAcct.setAccNo("020000");
				qfAcct.setAccNo("029999");
				mainAcct.setAccParent(oldEntity.getAccNo());
				qfAcct.setAccParent("020000");
				mainAcct.setInOrOut(2);
				qfAcct.setInOrOut(2);
			}
			mainAcct.setAccType(oldEntity.getAccType());
			qfAcct.setAccType(oldEntity.getAccType());
			mainAcct.setCustName(oldEntity.getCustName() + "_" + "主账簿");
			qfAcct.setCustName(oldEntity.getCustName() + "_" + "待清分账簿");
			mainAcct.setUnitNo(oldEntity.getUnitNo());
			mainAcct.setUnitName(oldEntity.getUnitName());
			qfAcct.setUnitNo(oldEntity.getUnitNo());
			qfAcct.setUnitName(oldEntity.getUnitName());

			mainAcct.setAmount(new BigDecimal("0.00"));
			qfAcct.setAmount(new BigDecimal("0.00"));
			mainAcct.setAccNum(new BigDecimal("0.00"));
			qfAcct.setAccNum(new BigDecimal("0.00"));

			mainAcct.setAccStatus(1);
			qfAcct.setAccStatus(1);

			mainAcct.setCreateTime(time);
			qfAcct.setCreateTime(time);

			mainAcct.setCreateUser(SecurityContextUtil.getCurrentUser()
					.getUserCode());
			qfAcct.setCreateUser(SecurityContextUtil.getCurrentUser()
					.getUserCode());

			mainAcct.setUpdateTime("");
			mainAcct.setUpdateUser("");
			qfAcct.setUpdateTime("");
			qfAcct.setUpdateUser("");

			mainAcct.setLevel(1);
			qfAcct.setLevel(2);

			mainAcct.setRate(new BigDecimal(0.00));
			qfAcct.setRate(new BigDecimal(0.00));

			mainAcct.setBankUnit("");
			mainAcct.setBankName("");
			mainAcct.setBankAmount(oldEntity.getBankAmount());
			qfAcct.setBankUnit("");
			qfAcct.setBankName("");
			qfAcct.setBankAmount(new BigDecimal(0.00));

			mainAcct.setUnkCome(new BigDecimal(0.00));
			mainAcct.setIntCome(oldEntity.getIntCome());
			mainAcct.setCurrency(oldEntity.getCurrency());
			qfAcct.setUnkCome(new BigDecimal(0.00));
			qfAcct.setIntCome(new BigDecimal(0.00));
			qfAcct.setCurrency(oldEntity.getCurrency());

			mainAcct.setAccFld(2);
			mainAcct.setAccPro(1);
			mainAcct.setIntFlag(2);
			mainAcct.setIntType(0);
			qfAcct.setAccFld(2);
			qfAcct.setAccPro(1);
			qfAcct.setIntFlag(2);
			qfAcct.setIntType(0);

			mainAcct.setDrugAmt(oldEntity.getDrugAmt());
			mainAcct.setMedicalAmt(oldEntity.getMedicalAmt());
			mainAcct.setOtherAmt(new BigDecimal(0.00));
			mainAcct.setNote1("");
			mainAcct.setNote2("");

			qfAcct.setDrugAmt(new BigDecimal(0.00));
			qfAcct.setMedicalAmt(new BigDecimal(0.00));
			qfAcct.setOtherAmt(new BigDecimal(0.00));
			qfAcct.setNote1("");
			qfAcct.setNote2("");
			
			//维护机构号
			List<Map<String,Object>> lists = service.gettopOrg("");
			if(lists != null && lists.size() >0){
				String orgNo = lists.get(0).get("id").toString();
				String orgName = lists.get(0).get("name").toString();
				mainAcct.setUnitNo(orgNo);
				mainAcct.setUnitName(orgName);
				qfAcct.setUnitNo(orgNo);
				qfAcct.setUnitName(orgName);
			}
			
			service.insertEntity(mainAcct);
			service.insertEntity(qfAcct);
			return ResponseData.SUCCESS_NO_DATA;
		} else {
			return new ResponseData(false, "只有真实账户才能开通");
		}

	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public ResponseData update(WjwAccount entity) {
		WjwAccount oldEntity = (WjwAccount) service.getEntity(entity.getId());
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

	@RequestMapping(value = "/updateopen")
	@ResponseBody
	public ResponseData updateopen(WjwAccount entity) {
		WjwAccount oldEntity = (WjwAccount) service.getEntity(entity.getId());
		if (oldEntity == null) {
			return new ResponseData(false, "记录不存在");
		}

		oldEntity.setAccNo(entity.getAccNo());
		oldEntity.setCustName(entity.getCustName());
		oldEntity.setAccCode(entity.getAccCode());
		oldEntity.setAccType(entity.getAccType());
		// oldEntity.setUnitNo(entity.getUnitNo());
		// oldEntity.setUnitName(entity.getUnitName());
		oldEntity.setAmount(entity.getAmount());
		oldEntity.setAccNum(entity.getAccNum());
		oldEntity.setAccStatus(entity.getAccStatus());
		// oldEntity.setAccParent(entity.getAccParent());
		// oldEntity.setCreateTime(entity.getCreateTime());
		// oldEntity.setCreateUser(entity.getCreateUser());
		// oldEntity.setUpdateTime(entity.getUpdateTime());
		// oldEntity.setUpdateUser(entity.getUpdateUser());
		// oldEntity.setLevel(entity.getLevel());
		oldEntity.setRate(entity.getRate());
		oldEntity.setBankUnit(entity.getBankUnit());
		oldEntity.setBankName(entity.getBankName());
		oldEntity.setBankAmount(entity.getBankAmount());
		// oldEntity.setUnkCome(entity.getUnkCome());
		// oldEntity.setIntCome(entity.getIntCome());
		oldEntity.setCurrency(entity.getCurrency());
		oldEntity.setAccFld(entity.getAccFld());
		oldEntity.setAccPro(entity.getAccPro());
		oldEntity.setIntFlag(entity.getIntFlag());
		oldEntity.setIntType(entity.getIntType());
		oldEntity.setInOrOut(entity.getInOrOut());
		oldEntity.setDrugAmt(entity.getDrugAmt());
		oldEntity.setMedicalAmt(entity.getMedicalAmt());
		// oldEntity.setOtherAmt(entity.getOtherAmt());
		oldEntity.setNote1(entity.getNote1());
		oldEntity.setNote2(entity.getNote2());
		service.updateEntity(oldEntity);
		return ResponseData.SUCCESS_NO_DATA;
	}

	@RequestMapping(value = "/subupdateopen")
	@ResponseBody
	public ResponseData subupdateopen(WjwAccount entity) {
		WjwAccount oldEntity = (WjwAccount) service.getEntity(entity.getId());
		if (oldEntity == null) {
			return new ResponseData(false, "记录不存在");
		}
		// 查找主账户
		List<Map<String, Object>> list = service.getMainAcct(""
				+ entity.getInOrOut());
		if (list != null && list.size() > 0) {
			String parentAcct = list.get(0).get("accNo").toString();
			// String parentAcctName = list.get(0).get("custName").toString();
			oldEntity.setAccParent(parentAcct);
		} else {
			return new ResponseData(false, "主账号未维护");
		}
		List<Map<String, Object>> orgList = service.getOrgInfo(entity
				.getUnitNo());
		if (orgList != null && orgList.size() > 0) {
			String orgName = orgList.get(0).get("orgName").toString();
			oldEntity.setUnitName(orgName);
		} else {
			return new ResponseData(false, "机构信息未维护");
		}
		oldEntity.setAccNo(entity.getAccNo());
		oldEntity.setCustName(entity.getCustName());
		oldEntity.setAccType(entity.getAccType());
		oldEntity.setUnitNo(entity.getUnitNo());
		// oldEntity.setUnitName(entity.getUnitName());
		oldEntity.setAmount(entity.getAmount());
		oldEntity.setAccNum(entity.getAccNum());
		oldEntity.setAccStatus(entity.getAccStatus());
		// oldEntity.setAccParent(entity.getAccParent());
		// oldEntity.setCreateTime(entity.getCreateTime());
		// oldEntity.setCreateUser(entity.getCreateUser());
		// oldEntity.setUpdateTime(entity.getUpdateTime());
		// oldEntity.setUpdateUser(entity.getUpdateUser());
		// oldEntity.setLevel(entity.getLevel());
		oldEntity.setRate(entity.getRate());
		// oldEntity.setBankUnit(entity.getBankUnit());
		// oldEntity.setBankName(entity.getBankName());
		// oldEntity.setBankAmount(entity.getBankAmount());
		// oldEntity.setUnkCome(entity.getUnkCome());
		// oldEntity.setIntCome(entity.getIntCome());
		oldEntity.setCurrency(entity.getCurrency());
		oldEntity.setAccFld(entity.getAccFld());
		oldEntity.setAccPro(entity.getAccPro());
		oldEntity.setIntFlag(entity.getIntFlag());
		oldEntity.setIntType(entity.getIntType());
		oldEntity.setInOrOut(entity.getInOrOut());
		oldEntity.setDrugAmt(entity.getDrugAmt());
		oldEntity.setMedicalAmt(entity.getMedicalAmt());
		// oldEntity.setOtherAmt(entity.getOtherAmt());
		oldEntity.setNote1(entity.getNote1());
		oldEntity.setNote2(entity.getNote2());
		service.updateEntity(oldEntity);
		return ResponseData.SUCCESS_NO_DATA;
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public ResponseData delete(Long[] ids) {
		for (Long id : ids) {
			service.deleteEntity(id);
		}
		return ResponseData.SUCCESS_NO_DATA;
	}

	@RequestMapping(value = "/deleteopen")
	@ResponseBody
	public ResponseData deleteopen(Long[] ids) {
		for (Long id : ids) {
			service.deleteEntity(id);
		}
		return ResponseData.SUCCESS_NO_DATA;
	}

	@RequestMapping(value = "/subdeleteopen")
	@ResponseBody
	public ResponseData subdeleteopen(Long[] ids) {
		for (Long id : ids) {
			service.deleteEntity(id);
		}
		return ResponseData.SUCCESS_NO_DATA;
	}

	@RequestMapping(value = "/getOrgInfo")
	@ResponseBody
	public List<Map<String, Object>> getOrgInfo() {
		return service.getOrgInfo("");
	}
	
	@RequestMapping(value = "/getSubOrgInfo")
	@ResponseBody
	public List<Map<String, Object>> getSubOrg() {
		return service.getSubOrg();
	}

	@RequestMapping(value = "/getMainAcctNo")
	@ResponseBody
	public List<Map<String, Object>> getMainAcctNo() {
		return service.getMainAcctNo();
	}

	@RequestMapping(value = "/getSubAcctNo")
	@ResponseBody
	public List<Map<String, Object>> getSubAcctNo(String acctId1) {
		System.out.println(acctId1+"---------");
		return service.getSubAcctNo(acctId1);
	}
	
	@RequestMapping(value = "/getAcctNoByOrg")
	@ResponseBody
	public List<Map<String, Object>> getAcctNoByOrg() {
		String orgNo = SecurityContextUtil.getCurrentUser().getOrgId()+"";
		return service.getAcctNoByOrg(orgNo);
	}
}
