package com.untech.mcrcb.web.service;

import com.untech.mcrcb.web.dao.WjwAccchangeDao;
import com.untech.mcrcb.web.dao.WjwAccountDao;
import com.untech.mcrcb.web.dao.WjwAccountHXDao;
import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.enhance.BaseService;
import com.untech.mcrcb.web.model.WjwAccchange;
import com.untech.mcrcb.web.model.WjwAccount;
import com.untech.mcrcb.web.util.RlateUtil;
import com.untech.mcrcb.web.util.Utils;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.SecurityContextUtil;
import com.unteck.tpc.framework.web.model.admin.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * WJW_ACCOUNT Service
 *
 * @author            chenyong
 * @since             2015-11-04
 */

@Service
public class WjwAccountService extends BaseService<WjwAccount, Long>{
      @Autowired
      private WjwAccountDao dao;
      @Autowired
      private WjwAccchangeDao wacDao;
      @Autowired
      private WjwAccountHXDao hxDao;
      @Override
	  public BaseDao<WjwAccount, Long> getHibernateBaseDao()
      {
        return this.dao;
      }

	public Pagination<Map<String, Object>> findDqfAccounts(Integer start,Integer limit) {
		
		return dao.findDqfAccounts(start,limit);
	}

	public List<Map<String, Object>> findDqfChildren(String accNo,String parent) {
		
		return dao.findDqfChildren(accNo, parent);
	}


	/**
	 * 待清分账户余额清分至子账户
	 * @param id
	 * @param accNoParams
	 * @param amountParams
	 * @return
	 */
	@Transactional
	public boolean dqfAccountQf(String id,String accNo,String uuid,String qfAmt,
			String itemId1,String itemId2,
			Map<String, String> accNoParams,Map<String, String> amountParams,
			Map<String,String> item1AmtParams,Map<String,String> item2AmtParams,
			BigDecimal medc,BigDecimal drug,String unkType) {
		
		
		try {
			WjwAccount dqf = this.findByAccNo(accNo);
			Iterator<Entry<String,String>> it = accNoParams.entrySet().iterator();
			while(it.hasNext()){
				Entry<String,String> entry = it.next();
				String key = entry.getKey();
				String childAccno = entry.getValue(); 
				String amt = amountParams.get("amount"+key.substring(5));//清分到相应子账户总金额
				String amt1 = item1AmtParams.get("item1Amt"+key.substring(5));
				String amt2 = item2AmtParams.get("item2Amt"+key.substring(5));
				//根据账户号查找子账户
				WjwAccount child = this.findByAccNo(childAccno);
				//根据子账户的账号找出对应的清分金额
				BigDecimal amount = new BigDecimal(amt);
				BigDecimal item1Amt = new BigDecimal(amt1);
				BigDecimal item2Amt = new BigDecimal(amt2);
//				BigDecimal item3Amt = BigDecimal.valueOf(Long.parseLong(amt3));
				//按金额减去待清分账户余额，同时子账户增加相应金额
				dqf.setAmount(dqf.getAmount().subtract(amount));
				
				child.setAmount(child.getAmount().add(amount));
//				dqf.setMedicalAmt(dqf.getMedicalAmt().subtract(item1Amt));
				child.setMedicalAmt(child.getMedicalAmt().add(item1Amt));				
//				dqf.setDrugAmt(dqf.getDrugAmt().subtract(item2Amt));
				child.setDrugAmt(child.getDrugAmt().add(item2Amt));

				String time = Utils.getTodayString("yyyyMMdd");
				User user = SecurityContextUtil.getCurrentUser();				
				dqf.setUpdateUser(user.getUserCode());
				dqf.setUpdateTime(time);
				child.setUpdateUser(user.getUserCode());
				child.setUpdateTime(time);
			
				hxDao.updateZmoney(dqf.getAmount(), dqf.getDrugAmt(), 
						dqf.getMedicalAmt(),accNo);
				logger.info("待清分账户清分------待清分账户余额减少");
				hxDao.updateZmoney(child.getAmount(), child.getDrugAmt(),
						child.getMedicalAmt(),childAccno);
				logger.info("待清分账户清分------子账户余额增加");
				//更新账户余额变更明细表-----1次
				
				WjwAccchange entity = new WjwAccchange();
				entity.setUnitNo(dqf.getUnitNo());
				entity.setUnitName(dqf.getUnitName());
				entity.setAccNo(dqf.getAccNo());
				entity.setAccName(dqf.getCustName());
				entity.setDfAccno(child.getAccNo());
				entity.setDfAccname(child.getCustName());
				entity.setAmount(dqf.getAmount());
				entity.setMedcAmt(item1Amt);
				entity.setDrugAmt(item2Amt);
				entity.setTranAmt(amount.multiply(new BigDecimal(-1)));
				entity.setTranTime(dqf.getUpdateTime());
				entity.setInOrOut(2);// 1收入 2支出
				entity.setOtherAmt(new BigDecimal(0.00));
//				entity.setFlag(2);//**1 入账 2 清分 3 利息*/
				entity.setNote2(uuid);
				entity.setUnkType(2);
				wacDao.save(entity);
				logger.info("待清分账户清分------账户余额变更表新增记录1条");
//				wacDao.inSertEntity(entity);
				
				//更新账户余额变更明细表-----2次
				WjwAccchange ent = new WjwAccchange();
				ent.setUnitNo(child.getUnitNo());
				ent.setUnitName(child.getUnitName());
				ent.setAccNo(child.getAccNo());
				ent.setAccName(child.getCustName());
				ent.setDfAccno(dqf.getAccNo());
				ent.setDfAccname(dqf.getCustName());
				ent.setAmount(child.getAmount());
				ent.setMedcAmt(item1Amt);
				ent.setDrugAmt(item2Amt);
				ent.setTranAmt(amount);
				ent.setTranTime(child.getUpdateTime());
				ent.setInOrOut(1);// 1收入 2支出
				ent.setOtherAmt(new BigDecimal(0.00));
//				ent.setFlag(2);//**1 入账 2 清分 3 利息*/
				ent.setNote2(uuid);
				ent.setUnkType(2);
				wacDao.save(ent);
				logger.info("待清分账户清分------账户余额变更表新增记录2条");
//				wacDao.inSertEntity(ent);
			}
			//找出清分的该笔记录
			WjwAccchange wa = wacDao.get(Long.parseLong(id));
			//将标志位更改为2---清分状态
			wa.setFlag(2);
			wacDao.update(wa);
			logger.info("待清分账户清分------更新待清分状态为2--清分状态");
			//更新主账户药品科目、医疗科目余额
			
			String parent = dqf.getAccParent();
			WjwAccount main = this.findByAccNo(parent);
			dao.updateAccount(main.getAmount(), main.getDrugAmt().add(drug), 
					main.getMedicalAmt().add(medc), parent);
			logger.info("待清分账户清分------主账户对应的医疗和药品科目余额增加");
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
			return false;
		} catch (DataAccessException e) {
			
			e.printStackTrace();
			return false;
		}

		return true;
	}
/**
 * 利息入账
 * @param id
 * @param rzAmt
 * @return
 */
	@Transactional
	public boolean lxRz(String accNo, String rzAmt,String date) {
		
		BigDecimal amount = new BigDecimal(rzAmt);
		//通过待清分子账户找出总帐户
		WjwAccount main = this.findByAccNo(accNo);
		//将利息金额amount增加入总帐户余额，同时更新总帐户利息余额字段
//		main.setAmount(main.getAmount().add(amount));
		main.setIntCome(main.getIntCome().add(amount));
		String time = Utils.getTodayString("yyyyMMdd");
		User user = SecurityContextUtil.getCurrentUser();
		main.setUpdateUser(user.getId().toString());
		main.setUpdateTime(time);
		this.updateEntity(main);
		
		//更新账户余额变更明细表
		WjwAccchange entity = new WjwAccchange();
		entity.setUnitNo(main.getUnitNo());
		entity.setUnitName(main.getUnitName());
		entity.setAccNo(main.getAccNo());
		entity.setAccName(main.getCustName());
		entity.setDfAccno("");
		entity.setDfAccname("利息");
		entity.setAmount(main.getAmount());
		entity.setDrugAmt(new BigDecimal(0.00));
		entity.setMedcAmt(new BigDecimal(0.00));
		entity.setTranAmt(amount);
//		entity.setTranTime(main.getUpdateTime());
		entity.setTranTime(date);
		entity.setInOrOut(1);
		entity.setOtherAmt(new BigDecimal(0.00));
		entity.setFlag(3);//    /**1 入账 2 清分 3 利息*/
		wacDao.save(entity);
		
		return true;
	}

	public Pagination<Map<String, Object>> findMainAccounts(Integer start,
			Integer limit) {
		
		return dao.findMainAccounts(start,limit);
	}
	
	public List<Map<String, Object>> findDqfAccs() {
		
		return dao.findDqfAccs();
	}
	
	public List<Map<String, Object>> findMain() {
		
		return dao.findMain();
	}
	
	public List<Map<String, Object>> findMainForInterest() {
		
		return dao.findMainForInterest();
	}

	public List<Map<String, Object>> findAllAccounts(String unitNo) {

		return dao.findAllAccounts(unitNo);
	}
	public List<Map<String, Object>> findAllAccountsByAccCode(String unitNo,int accCode) {

		return dao.findAllAccountsByAccCode(unitNo,accCode);
	}
	
	public Map<String, Object> getOrganization(Long orgId) {
		
		return dao.getOrganization(orgId.toString());
	}
	public WjwAccount findByAccNo(String accNo) {
		//System.out.println(accNo);
		//String sql = "select * from WJW_ACCOUNT where acc_no ='"+accNo+"'";
		Map<String,Object> map = dao.findByAccNo(accNo);
		
		WjwAccount account = new WjwAccount();
		account.setId(Long.parseLong(( map.get("ID")+"")));
		account.setAccNo((String) map.get("ACC_NO"));
		account.setCustName((String) map.get("CUST_NAME"));
		account.setAccType((Integer) map.get("ACC_TYPE"));
		account.setUnitNo((String) map.get("UNIT_NO"));
		account.setUnitName((String) map.get("UNIT_NAME"));
		account.setAmount((BigDecimal) map.get("AMOUNT"));
		account.setAccNum((BigDecimal) map.get("ACC_NUM"));
		account.setAccStatus((Integer) map.get("ACC_STATUS"));
		account.setAccParent((String) map.get("ACC_PARENT"));
		account.setCreateTime((String) map.get("CREATE_TIME"));
		account.setCreateUser((String) map.get("CREATE_USER"));
		account.setUpdateTime((String) map.get("UPDATE_TIME"));
		account.setUpdateUser((String) map.get("UPDATE_USER"));
		account.setLevel((Integer) map.get("LEVEL"));
		account.setRate((BigDecimal) map.get("RATE"));
		account.setBankUnit((String) map.get("BANK_UNIT"));
		account.setBankName((String) map.get("BANK_NAME"));
		account.setBankAmount((BigDecimal) map.get("BANK_AMOUNT"));
		account.setUnkCome((BigDecimal) map.get("UNK_COME"));
		account.setIntCome((BigDecimal) map.get("INT_COME"));
		account.setCurrency((String) map.get("CURRENCY"));
		account.setAccFld((Integer) map.get("ACC_FLD"));
		account.setAccPro((Integer) map.get("ACC_PRO"));
		account.setIntFlag((Integer) map.get("INT_FLAG"));
		account.setIntType((Integer) map.get("INT_TYPE"));
		account.setInOrOut((Integer) map.get("IN_OR_OUT"));
		account.setDrugAmt((BigDecimal) map.get("DRUG_AMT"));
		account.setMedicalAmt((BigDecimal) map.get("MEDICAL_AMT"));
		account.setOtherAmt((BigDecimal) map.get("OTHER_AMT"));
		account.setNote1((String) map.get("NOTE1"));
		account.setNote2((String) map.get("NOTE2"));
		return account;
	}
	/**
	 * 待清分账户余额入总账户
	 * @param id
	 * @param amount
	 * @return
	 */
	@Transactional
	public boolean dqfAccountRz(String accNo, String rzAmt,String unkType ,String date) {
		try {
			WjwAccount dqfAccount = this.findByAccNo(accNo);
				if(null==dqfAccount){
					return false;
			}
			//将待清帐户余额增加金额
			BigDecimal amount = new BigDecimal(rzAmt);
			dqfAccount.setAmount(dqfAccount.getAmount().add(amount));			
			String time = Utils.getTodayString("yyyyMMdd");
			User user = SecurityContextUtil.getCurrentUser();
			dqfAccount.setUpdateUser(user.getUserCode());
			dqfAccount.setUpdateTime(time);
			hxDao.updateZmoney(dqfAccount.getAmount(),
					dqfAccount.getDrugAmt(), dqfAccount.getMedicalAmt(), 
					accNo);
	
			//将该待清帐户的上级帐户余额增加金额
			WjwAccount parentAccount = this.findByAccNo(dqfAccount.getAccParent());
			parentAccount.setAmount(parentAccount.getAmount().add(amount));
			parentAccount.setUpdateUser(user.getUserCode());
			parentAccount.setUpdateTime(time);
			hxDao.updateZmoney(parentAccount.getAmount(),
					parentAccount.getDrugAmt(), 
					parentAccount.getMedicalAmt(), 
					parentAccount.getAccNo());
						
			//更新账户余额变更明细表----1次
			String uuid = RlateUtil.getUuid();

			WjwAccchange entity = new WjwAccchange();
			entity.setUnitNo(dqfAccount.getUnitNo());
			entity.setUnitName(dqfAccount.getUnitName());
			entity.setAccNo(dqfAccount.getAccNo());
			entity.setAccName(dqfAccount.getCustName());
			entity.setDfAccno(dqfAccount.getAccParent());
			entity.setDfAccname(parentAccount.getCustName());
			entity.setAmount(dqfAccount.getAmount());//这是账户表余额 		
			entity.setTranAmt(amount);
			entity.setTranTime(date);
			entity.setInOrOut(1);
			entity.setMedcAmt(new BigDecimal(0.00));
			entity.setDrugAmt(new BigDecimal(0.00));
			entity.setOtherAmt(new BigDecimal(0.00));
			entity.setFlag(1);//**1 入账 2 清分 3 利息*/
			entity.setNote2(uuid);
			entity.setUnkType(Integer.parseInt(unkType));
			wacDao.save(entity);
			
			
			System.out.println("entityId:"+entity.getId());
			//更新账户余额变更明细表-----2次
			WjwAccchange ent = new WjwAccchange();
			ent.setUnitNo(parentAccount.getUnitNo());
			ent.setUnitName(parentAccount.getUnitName());
			ent.setAccNo(parentAccount.getAccNo());
			ent.setAccName(parentAccount.getCustName());
			ent.setDfAccno(dqfAccount.getAccNo());
			ent.setDfAccname(dqfAccount.getCustName());
			ent.setAmount(parentAccount.getAmount());
			ent.setTranAmt(amount);
			ent.setTranTime(date);
			ent.setInOrOut(1);
			ent.setMedcAmt(new BigDecimal(0.00));
			ent.setDrugAmt(new BigDecimal(0.00));
			ent.setOtherAmt(new BigDecimal(0.00));
			ent.setFlag(1);//**1 入账 2 清分 3 利息*/
			ent.setNote2(uuid);
			ent.setUnkType(Integer.parseInt(unkType));
			wacDao.save(ent);
		} catch (Exception e) {
			e.printStackTrace();
			 return false;
		}		
		return true;
	}
}

