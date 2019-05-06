package com.untech.mcrcb.web.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.untech.mcrcb.web.dao.WjwInterestDao;
import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.enhance.BaseService;
import com.untech.mcrcb.web.model.WjwInterest;
import com.untech.mcrcb.web.util.Constant;
import com.untech.mcrcb.web.util.Utils;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.SecurityContextUtil;
import com.unteck.tpc.framework.web.model.admin.User;
@Service
public class WjwInterestService extends BaseService<WjwInterest, Long> {
	@Autowired
	private WjwInterestDao dao;
	@Override
	public BaseDao<WjwInterest, Long> getHibernateBaseDao() {
		
		return this.dao;
	}
	public Pagination<Map<String, Object>> findInterestOut(Integer start,
			Integer limit, String startTime, String endTime) {

		return dao.findInterestOut(start,limit,startTime,endTime);
	}
	@Transactional
	public boolean interestPay(String accNo,String accName, String inAccno, String inAccname,
			String inBank, String payAmt, Integer payWay,String date) {
		boolean flag = false;
		try {
			WjwInterest interest = new WjwInterest();
			interest.setInAccno(inAccno);
			interest.setInAccname(inAccname);
			interest.setInBank(inBank);
			interest.setAmount(new BigDecimal(payAmt));
			interest.setPayTime(date);
			String inOrOut = accNo.substring(1,2);			
			Map<String,Object> map = this.findBankAccount(inOrOut);
			interest.setOutAccno(map.get("ACC_NO").toString());
			interest.setOutAccname(map.get("CUST_NAME").toString());
			interest.setOutBank(Constant.BANK);
			interest.setPayWay(payWay);
			interest.setVoucher(this.createCode());
			interest.setStatus(5);
			interest.setInOrOut(Integer.parseInt(inOrOut));
			User user = SecurityContextUtil.getCurrentUser();
			interest.setOperator(user.getUserCode());
			interest.setUnitNo(user.getOrgId().toString());
			interest.setUnitName(user.getOrgName());
			this.insertEntity(interest);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return flag;
	}
	public Map<String, Object> getInterest(Long id) {
		
		return dao.getInterest(id);
	}
	
	public Map<String,Object> findBankAccount(String inOrOut){
		return dao.findBankAccount(inOrOut);
	}
	
    /**
	 * 生成利息支出凭证
	 * @return
	 */
	public String createCode(){
		int num = dao.getMaxCode();
		String jianPin = dao.getWSYJianpin(SecurityContextUtil.getCurrentUser().getOrgId());
		return Utils.createCode(jianPin,"04",num);
	}
	public void deleteInterest(Long[] ids) {
		for(Long id:ids){
			dao.deleteInterest(id);
		}
	}

}
