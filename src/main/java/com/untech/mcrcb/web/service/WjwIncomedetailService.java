package com.untech.mcrcb.web.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.untech.mcrcb.web.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.untech.mcrcb.web.dao.WjwBfhzbDao;
import com.untech.mcrcb.web.dao.WjwIncomedetailDao;
import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.enhance.BaseService;
import com.untech.mcrcb.web.model.WjwAccount;
import com.untech.mcrcb.web.model.WjwIncomedetail;
import com.untech.mcrcb.web.util.Utils;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.ResponseData;
import com.unteck.tpc.framework.core.util.SecurityContextUtil;
import com.unteck.tpc.framework.web.model.admin.User;

/**
 * WJW_INCOMEDETAIL Service
 *
 * @author            chenyong
 * @since             2015-11-04
 */

@Service
public class WjwIncomedetailService extends BaseService<WjwIncomedetail, Long> {
      @Autowired
      private WjwIncomedetailDao dao;
      
      @Autowired
      private WjwBfhzbDao wjwdao;

      @Autowired
	  private PublicService publicService;
      
      @Override
	  public BaseDao<WjwIncomedetail, Long> getHibernateBaseDao()
      {
        return this.dao;
      }
    /**
     * 获得缴款信息列表
     * @param start
     * @param limit
     * @param unitNo
     * @return
     */
	public Pagination<Map<String, Object>> getWjwIncomedetailList(Integer start,Integer limit,
    		String unitNo,String certNo,String startTime,String endTime){
		User user = SecurityContextUtil.getCurrentUser();
		Integer parentId = dao.getOrgParentId(user.getOrgId());
		return dao.getWjwIncomedetailList(start,limit,unitNo,certNo,startTime,endTime,parentId,user.getOrgId());
	}
	
	/**
	 * 保存缴款信息
	 */
//	@Transactional
//	public ResponseData save(WjwIncomedetail detail){
//		//设置相关信息
//		User user = SecurityContextUtil.getCurrentUser();
//		Integer parentId = dao.getOrgParentId(user.getOrgId());
//		if(parentId == 0){
//			return new ResponseData(false,"您的身份无法进行此操作");
//		}
//		detail.setOperNo(user.getUserCode());
//		detail.setOperName(user.getUserName());
//		detail.setUnitNo(user.getOrgId()+"");
//		detail.setUnitName(user.getOrgName());
////		detail.setIncTime(Utils.getTodayString("yyyyMMdd HH:mm:ss"));
//		detail.setIncTime(Utils.getTodayString("yyyyMMdd"));
//		detail.setStatus(5);//1-申请，2-初审，3-复审，4-作废，5-完成，6-核销完成
//		detail.setCertNo(createCode());//凭证
//		//
//		BigDecimal drug = new BigDecimal(0);
//		BigDecimal medc = new BigDecimal(0);
//		BigDecimal other = new BigDecimal(0);
//		if(detail.getItem1() !=  null && !detail.getItem1().equals("")){
//			if(detail.getItem1().equals("1")){
//				medc = medc.add(detail.getItem1Amt());
//
//			}else if(detail.getItem1().equals("2")){
//				drug = drug.add(detail.getItem1Amt());
//			}if(detail.getItem1().equals("3")){
//				other = other.add(detail.getItem1Amt());
//			}
//		}
//		if(detail.getItem2() !=  null && !detail.getItem2().equals("")){
//			if(detail.getItem2().equals("1")){
//				medc = medc.add(detail.getItem2Amt());
//
//			}else if(detail.getItem2().equals("2")){
//				drug = drug.add(detail.getItem2Amt());
//			}if(detail.getItem2().equals("3")){
//				other = other.add(detail.getItem2Amt());
//			}
//		}
//		if(detail.getItem3() !=  null && !detail.getItem3().equals("")){
//			if(detail.getItem3().equals("1")){
//				medc = medc.add(detail.getItem3Amt());
//
//			}else if(detail.getItem3().equals("2")){
//				drug = drug.add(detail.getItem3Amt());
//			}if(detail.getItem3().equals("3")){
//				other = other.add(detail.getItem3Amt());
//			}
//		}
//		detail.setDrugAmt(drug);
//		detail.setMedicalAmt(medc);
//		detail.setOtherAmt(other);
//		detail.setAmount(drug.add(medc).add(other));
//		// 虚拟账户    当前机构虚拟收入账户
//		WjwAccount oin = wjwdao.findIncomeAccountByUnitNo(user.getOrgId()+"");
//		detail.setXnAcctno(oin.getAccNo());
//		detail.setXnAcctName(oin.getCustName());
//		//主账户  卫计委虚拟收入账户
//		WjwAccount win = wjwdao.findWjwXNInAccount();
//		detail.setZcAcctno(win.getAccNo());
//		detail.setZcAcctname(win.getCustName());
//		dao.save(detail);
//		return ResponseData.SUCCESS_NO_DATA;
//	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String save(HttpServletRequest request){
		User user = SecurityContextUtil.getCurrentUser();
		int accCode= publicService.judgeAccountType(user);
		Integer parentId = dao.getOrgParentId(user.getOrgId());
		if(parentId == 0){
			return "1";
		} 
		String outAccno = request.getParameter("outAccno");
		String outAccname = request.getParameter("outAccname");
		String outBank = request.getParameter("outBank");
		String inAccno = request.getParameter("inAccno");
		String inName = request.getParameter("inName");
		String inBank = request.getParameter("inBank");
		String[] itemCode = request.getParameterValues("itemCode");
		String[] item = request.getParameterValues("item");
		String[] itemDw = request.getParameterValues("itemDw");
		String[] itemNum = request.getParameterValues("itemNum");
		String[] itemSt = request.getParameterValues("itemSt");
		String[] itemAmt = request.getParameterValues("itemAmt");
		WjwIncomedetail income = new WjwIncomedetail();
		income.setOperNo(user.getUserCode());
		income.setOperName(user.getUserName());
		income.setUnitNo(user.getOrgId()+"");
		income.setUnitName(user.getOrgName());
		income.setIncTime(Utils.getTodayString("yyyyMMdd"));
		income.setStatus(5);//1-申请，2-初审，3-复审，4-作废，5-完成，6-核销完成
		income.setCertNo(createCode());//凭证
		BigDecimal drug = new BigDecimal(0);//药品收入
		BigDecimal medc = new BigDecimal(0);//医疗收入
		BigDecimal other = new BigDecimal(0);//其他收入
		
		String item1 = "";
		BigDecimal item1Amt = new BigDecimal(0.00);
		String item1Code = "";
		String item1Dw = "";
		int item1Num = 0;
		String item1St = "";
		
		String item2 = "";
		BigDecimal item2Amt = new BigDecimal(0.00);
		String item2Code = "";
		String item2Dw = "";
		int item2Num = 0;
		String item2St = "";
		
		String item3 = "";
		BigDecimal item3Amt = new BigDecimal(0.00);
		String item3Code = "";
		String item3Dw = "";
		int item3Num = 0;
		String item3St = "";
		
		for(int i=0;i<item.length;i++){
			if(item[i] != null && !"".equals(item[i])){
				if("医疗".equals(item[i])){//医疗收入
					medc = medc.add(new BigDecimal(itemAmt[i]));
				}else if("药品".equals(item[i])){//药品收入
					drug = drug.add(new BigDecimal(itemAmt[i]));
				}else{//其他收入
					other = other.add(new BigDecimal(itemAmt[i]));
				}
				if(i == 0){
					if("医疗".equals(item[i])){
						item1 = "1";
					}else if("药品".equals(item[i])){
						item1 = "2";
					}else{
						item1 = "3";
					}
					item1Amt = new BigDecimal(itemAmt[i]);
					item1Code = itemCode[i];
					item1Dw = itemDw[i];
					item1Num = itemNum[i] == "" ? 0 : Integer.parseInt(itemNum[i]);
					item1St = itemSt[i];
				}
				if(i == 1){
					if("医疗".equals(item[i])){
						item2 = "1";
					}else if("药品".equals(item[i])){
						item2 = "2";
					}else{
						item2 = "3";
					}
					item2Amt = new BigDecimal(itemAmt[i]);
					item2Code = itemCode[i];
					item2Dw = itemDw[i];
					item2Num = itemNum[i] == "" ? 0 : Integer.parseInt(itemNum[i]);
					item2St = itemSt[i];
				}
				if(i == 2){
					if("医疗".equals(item[i])){
						item3 = "1";
					}else if("药品".equals(item[i])){
						item3 = "2";
					}else{
						item3 = "3";
					}
					item3Amt = new BigDecimal(itemAmt[i]);
					item3Code = itemCode[i];
					item3Dw = itemDw[i];
					item3Num = itemNum[i] == "" ? 0 : Integer.parseInt(itemNum[i]);
					item3St = itemSt[i];
				}
			}
			
		}
		income.setOutAccno(outAccno == null ? "":outAccno);
		income.setOutAccname(outAccname == null ? "":outAccname);
		income.setOutBank(outBank == null ? "":outBank);
		income.setInAccno(inAccno);
		income.setInName(inName);
		income.setInBank(inBank);
		
		income.setItem1(item1);
		income.setItem1Amt(item1Amt);
		income.setItem1Code(item1Code);
		income.setItem1Dw(item1Dw);
		income.setItem1Num(item1Num);
		income.setItem1St(item1St);
		
		income.setItem2(item2);
		income.setItem2Amt(item2Amt);
		income.setItem2Code(item2Code);
		income.setItem2Dw(item2Dw);
		income.setItem2Num(item2Num);
		income.setItem2St(item2St);
		
		income.setItem3(item3);
		income.setItem3Amt(item3Amt);
		income.setItem3Code(item3Code);
		income.setItem3Dw(item3Dw);
		income.setItem3Num(item3Num);
		income.setItem3St(item3St);
		
		income.setDrugAmt(drug);
		income.setMedicalAmt(medc);
		income.setOtherAmt(other);
		income.setAmount(drug.add(medc).add(other));
		// 虚拟账户    当前机构虚拟收入账户
		WjwAccount oin = null;
		//主账户  卫计委虚拟收入账户
		WjwAccount win =null;
		if (Constants.ACC_CODE.JLY == accCode){
			oin =wjwdao.findOutcomeAccountByUnitNo(user.getOrgId()+"");
			win = wjwdao.findWjwOutAccount(oin.getAccCode());
		}else{
			oin =wjwdao.findIncomeAccountByUnitNo(user.getOrgId()+"");
			win = wjwdao.findWjwInAccount(oin.getAccCode());
		}
		income.setXnAcctno(oin.getAccNo());
		income.setXnAcctName(oin.getCustName());
		income.setZcAcctno(win.getAccNo());
		income.setZcAcctname(win.getCustName());
		dao.insertAll(income);
		return "0";
	}
	
	/**
	 * 更新缴款 信息
	 * @param entity
	 * @return
	 */
	@Transactional
	public boolean update(WjwIncomedetail entity){
		WjwIncomedetail oldEntity = (WjwIncomedetail)getEntity(entity.getId());
	      if (oldEntity == null) {
	          //return new ResponseData(false, "记录不存在");
	          return false;
	      }
	      oldEntity.setAmount(entity.getAmount());
	      oldEntity.setOutAccno(entity.getOutAccno());
	      oldEntity.setOutAccname(entity.getOutAccname());
	      oldEntity.setOutBank(entity.getOutBank());
	      oldEntity.setInAccno(entity.getInAccno());
	      oldEntity.setInName(entity.getInName());
	      oldEntity.setInBank(entity.getInBank());
	      oldEntity.setItem1(entity.getItem1());
	      oldEntity.setItem2(entity.getItem2());
	      oldEntity.setItem3(entity.getItem3());
	      oldEntity.setItem1Dw(entity.getItem1Dw());
	      oldEntity.setItem2Dw(entity.getItem2Dw());
	      oldEntity.setItem3Dw(entity.getItem3Dw());
	      oldEntity.setItem1Num(entity.getItem1Num());
	      oldEntity.setItem2Num(entity.getItem2Num());
	      oldEntity.setItem3Num(entity.getItem3Num());
	      oldEntity.setItem1St(entity.getItem1St());
	      oldEntity.setItem2St(entity.getItem2St());
	      oldEntity.setItem3St(entity.getItem3St());
	      oldEntity.setItem1Amt(entity.getItem1Amt());
	      oldEntity.setItem2Amt(entity.getItem2Amt());
	      oldEntity.setItem3Amt(entity.getItem3Amt());
	      oldEntity.setItem1Code(entity.getItem1Code());
	      oldEntity.setItem2Code(entity.getItem2Code());
	      oldEntity.setItem3Code(entity.getItem3Code());
	      oldEntity.setCurrency(entity.getCurrency());
	      oldEntity.setYt(entity.getYt());	      
	      
	      //更新时间
//	      oldEntity.setIncTime(Utils.getTodayString("yyyyMMdd HH:mm:ss"));
	      oldEntity.setIncTime(Utils.getTodayString("yyyyMMdd"));
	      dao.update(oldEntity);
	      //
	      return true;
	}
	
	/**
	 * 生成缴款凭证
	 * @return
	 */
	public String createCode(){
		int num = dao.getMaxCode();
		String jianPin = dao.getWSYJianpin(SecurityContextUtil.getCurrentUser().getOrgId());
		return Utils.createCode(jianPin,"01",num);
	}
	
	/**
	 * 缴款打印所需信息
	 * @param id
	 * @return
	 */
	public Map<String, Object> getWjwIncomedetailById(Integer id) {
		Map<String, Object> map = dao.getWjwIncomedetailById(id);
		String time = (String)map.get("incTime");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy 年  MM 月 dd 日 ");
		try {
			map.put("fmtTime",sdf2.format(sdf.parse(time)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return map;
		
	}
	public Map<String, Object> getWjwInAccount() {
		return dao.getWjwInAccount();
	}

	public Map<String, Object> getWjwYlyInAccount(User user) {
		int accCode= publicService.judgeAccountType(user);
		return dao.getWjwYlyInAccount(accCode);
	}

	/**
	 * 作废
	 * @param ids
	 */
	public void deleteIncome(Long[] ids) {
		for (Long id : ids){
	        dao.deleteIncome(id);
	      }
	}
	
}

