package com.untech.mcrcb.tran.service;

import java.math.BigDecimal;
import java.util.Map;

import com.untech.mcrcb.web.dao.WjwAccchangeDao;
import com.untech.mcrcb.web.dao.WjwAccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.untech.mcrcb.web.dao.BalanceAccountDao;
import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.enhance.BaseService;
import com.untech.mcrcb.web.model.WjwAccchange;
import com.untech.mcrcb.web.model.WjwPaydetail;
@Service
public class AccountingBaseService extends BaseService<WjwAccchange,Long>{
	@Autowired
	protected BalanceAccountDao dao;

	@Autowired
	protected WjwAccountDao accountDao;

	@Override
	public BaseDao<WjwAccchange, Long> getHibernateBaseDao() {
		
		return this.dao;
	}
	
	/*****
	 * 对银行冲正的、资金平台却记账的记录进行账务回滚
	 * @param map
	 * @param type
	 * @param voucher
	 * @param date
	 */
	@Transactional
	public void rollbackTallyByType(Map<String,Object> map,String type,String voucher,String date){
		/****第1步：更新账户余额表****/
	
		String incomeAccno = map.get("XN_ACCTNO").toString();//收入账户号
//		String childAccName = map.get("XN_ACCTNAME").toString();//支出账户名
		String payOutAccno =map.get("ZC_ACCTNO").toString();//支出账户号
//		String parentAccName = map.get("ZC_ACCTNAME").toString();//支出账户名
		BigDecimal amount = (BigDecimal) map.get("AMOUNT");//总金额
		BigDecimal drugAmt = (BigDecimal) map.get("DRUG_AMT");//药品金额
		BigDecimal medcAmt = (BigDecimal) map.get("MEDICAL_AMT");//医疗金额
		BigDecimal otherAmt = new BigDecimal(0.00);
		if("1".equals(type)){
			//缴款才有其他金额
			otherAmt = (BigDecimal) map.get("OTHER_AMT");//其他金额
			
		}
		
		//根据账户号，查找对应账户的各项余额
		Map<String,Object> incomeAccount = dao.findAccountByAccountNo(incomeAccno);
//		String childUnitNo = childAccount.get("UNIT_NO").toString();
//		String childUnitName = childAccount.get("UNIT_NAME").toString();
		BigDecimal incomeAmt = (BigDecimal) incomeAccount.get("AMOUNT");//总金额
		BigDecimal incomeDrugAmt = (BigDecimal) incomeAccount.get("DRUG_AMT");//药品金额
		BigDecimal incomeMedcAmt = (BigDecimal) incomeAccount.get("MEDICAL_AMT");//医疗金额
		//更新子账户各项余额
		if("1".equals(type)){
			//缴款
			dao.updateAccountAmount(incomeAmt.subtract(amount),incomeDrugAmt.subtract(drugAmt),
					incomeMedcAmt.subtract(medcAmt).subtract(otherAmt),incomeAccno);
		}else if("2".equals(type)){
			//支付
			dao.updateAccountAmount(incomeAmt.add(amount), incomeDrugAmt.add(drugAmt),
					incomeMedcAmt.add(medcAmt), incomeAccno);
		}else if("3".equals(type)){
			//拨付时，这里的子账户对应收入子账户和收入总账户
			dao.updateAccountAmount(incomeAmt.add(amount), incomeDrugAmt.add(drugAmt),
					incomeMedcAmt.add(medcAmt), incomeAccno);
		}
	
		//根据账户号，查找对应账户的各项余额
		Map<String,Object> payOutAccount = dao.findAccountByAccountNo(payOutAccno);
//		String parentUnitNo = childAccount.get("UNIT_NO").toString();
//		String parentUnitName = childAccount.get("UNIT_NAME").toString();
		BigDecimal payOutAmt = (BigDecimal) payOutAccount.get("AMOUNT");//总金额
		BigDecimal payOutDrugAmt = (BigDecimal) payOutAccount.get("DRUG_AMT");//药品金额
		BigDecimal payOutMedcAmt = (BigDecimal) payOutAccount.get("MEDICAL_AMT");//医疗金额
		if("1".equals(type)){
			//缴款
			dao.updateAccountAmount(payOutAmt.subtract(amount), payOutDrugAmt.subtract(drugAmt), 
					payOutMedcAmt.subtract(medcAmt).subtract(otherAmt), payOutAccno);
		}else if("2".equals(type)){
			//支付
			dao.updateAccountAmount(payOutAmt.add(amount), payOutDrugAmt.add(drugAmt),
					payOutMedcAmt.add(medcAmt), payOutAccno);
		}else if("3".equals(type)){
			//拨付 
			dao.updateAccountAmount(payOutAmt.subtract(amount), payOutDrugAmt.subtract(drugAmt), 
					payOutMedcAmt.subtract(medcAmt), payOutAccno);
		}
	
		/****第2步：删除账户余额变更表记录****/
		dao.deleteByVoucher(voucher,false);	
	}
	
	/*****
	 * 对于银行记账成功、资金平台没有记账的记录进行补记账
	 * @param map
	 * @param type
	 * @param voucher
	 * @param date
	 */
	@Transactional
	public void tallyByType(Map<String,Object> map,String type,String voucher,String date){
		/****第1步：更新账户余额表****/
//		String inAccno = "";//收款人账户
//		String inAccname = "";//收款人姓名
//		if("2".equals(type)){
//			//支付的时候，在插入账户余额变更表时，对方帐号位收款人账号、姓名
//			inAccno = map.get("IN_ACCNO").toString();//收款人账户
//			inAccname = map.get("IN_NAME").toString();//收款人姓名
//		}
		String incomeAccno = map.get("XN_ACCTNO").toString();//子账户号
		String incomeAccName = map.get("XN_ACCTNAME").toString();//子账户名
		String payOutAccno =map.get("ZC_ACCTNO").toString();//总账户号
		String payOutAccName = map.get("ZC_ACCTNAME").toString();//总账户名
		BigDecimal amount = (BigDecimal) map.get("AMOUNT");//总金额
		BigDecimal drugAmt = (BigDecimal) map.get("DRUG_AMT");//药品金额
		BigDecimal medcAmt = (BigDecimal) map.get("MEDICAL_AMT");//医疗金额
		BigDecimal otherAmt = new BigDecimal(0.00);
		if("1".equals(type)){
			otherAmt = (BigDecimal) map.get("OTHER_AMT");//其他金额
			//缴款才有其他金额
		}
		
		//根据子账户号，查找对应账户的各项余额
		Map<String,Object> incomeAccount = dao.findAccountByAccountNo(incomeAccno);
		if(null==incomeAccount){
			logger.info("根据账号："+incomeAccno+"查询账户失败");
		}
		String incomeUnitNo = incomeAccount.get("UNIT_NO").toString();
		String incomeUnitName = incomeAccount.get("UNIT_NAME").toString();
		BigDecimal incomeAmt = (BigDecimal) incomeAccount.get("AMOUNT");//总金额
		BigDecimal incomeDrugAmt = (BigDecimal) incomeAccount.get("DRUG_AMT");//药品金额
		BigDecimal incomeMedcAmt = (BigDecimal) incomeAccount.get("MEDICAL_AMT");//医疗金额
		//更新收入账户各项余额，收入账户余额增加
		if("1".equals(type)){
			//缴款
			dao.updateAccountAmount(incomeAmt.add(amount),incomeDrugAmt.add(drugAmt),
					incomeMedcAmt.add(medcAmt).add(otherAmt),incomeAccno);
			logger.info("缴款----更新收入账户各项余额成功");
		}else if("2".equals(type)){
			//支付
			dao.updateAccountAmount(incomeAmt.subtract(amount), incomeDrugAmt.subtract(drugAmt),
					incomeMedcAmt.subtract(medcAmt), incomeAccno);
			logger.info("支付----更新收入账户各项余额成功");
		}else if("3".equals(type)){
			//拨付时，这里的子账户对应收入子账户和收入总账户
			dao.updateAccountAmount(incomeAmt.subtract(amount), incomeDrugAmt.subtract(drugAmt),
					incomeMedcAmt.subtract(medcAmt), incomeAccno);
			logger.info("拨付----更新收入账户各项余额成功");
		}
	
		//根据账户号，查找对应账户的各项余额
		Map<String,Object> payOutAccount = dao.findAccountByAccountNo(payOutAccno);
		if(null==payOutAccount){
			logger.info("根据账号："+payOutAccno+"查询账户失败");
		}
		String payOutUnitNo = payOutAccount.get("UNIT_NO").toString();
		String payOutUnitName = payOutAccount.get("UNIT_NAME").toString();
		BigDecimal payOutAmt = (BigDecimal) payOutAccount.get("AMOUNT");//总金额
		BigDecimal payOutDrugAmt = (BigDecimal) payOutAccount.get("DRUG_AMT");//药品金额
		BigDecimal payOutMedcAmt = (BigDecimal) payOutAccount.get("MEDICAL_AMT");//医疗金额
		if("1".equals(type)){
			//缴款
			dao.updateAccountAmount(payOutAmt.add(amount), payOutDrugAmt.add(drugAmt), 
					payOutMedcAmt.add(medcAmt).add(otherAmt), payOutAccno);
			logger.info("缴款----更新支出账户各项余额成功");
		}else if("2".equals(type)){
			//支付
			dao.updateAccountAmount(payOutAmt.subtract(amount), payOutDrugAmt.subtract(drugAmt),
					payOutMedcAmt.subtract(medcAmt), payOutAccno);
			logger.info("支付----更新支出账户各项余额成功");
		}else if("3".equals(type)){
			//拨付 
			dao.updateAccountAmount(payOutAmt.add(amount), payOutDrugAmt.add(drugAmt), 
					payOutMedcAmt.add(medcAmt), payOutAccno);
			logger.info("拨付----更新支出账户各项余额成功");
		}
	
		/****第2步：新增账户余额变更表记录****/
		/*********************************新增账户余额变更表记录****************************************/
		//新增账户余额变更记录----1次
		WjwAccchange in = new WjwAccchange();
		in.setUnitNo(incomeUnitNo);//机构号
		in.setUnitName(incomeUnitName);//机构名称
		in.setAccNo(incomeAccno);//收入账户号
		in.setAccName(incomeAccName);//收入账户名称
//		if("2".equals(type)){
//			//支付时，对方帐号为收款人帐号、姓名
//			in.setDfAccno(inAccno);//对方账户
//			in.setDfAccname(inAccname);//对账户名称
//		}else{
//			in.setDfAccno(payOutAccno);//对方账户(支出账户)
//			in.setDfAccname(payOutAccName);//对账户名称(支出账户)	
//		}
		in.setDfAccno(payOutAccno);//对方账户(支出账户)
		in.setDfAccname(payOutAccName);//对账户名称(支出账户)	
	
		if("1".equals(type)){
			//缴款
			in.setAmount(incomeAmt.add(amount));//余额
			in.setDrugAmt(drugAmt);//药品
			in.setMedcAmt(medcAmt.add(otherAmt));//医疗收入+其他收入
			in.setTranAmt(amount);//交易金额
			in.setInOrOut(1);//收入1或支出2
		}else if("2".equals(type)){
			//支付
			in.setAmount(incomeAmt.subtract(amount));//余额
			in.setDrugAmt(drugAmt);//药品
			in.setMedcAmt(medcAmt);//医疗
			in.setTranAmt(amount.multiply(new BigDecimal(-1)));//交易金额
			in.setInOrOut(2);//收入1或支出2
		}else if("3".equals(type)){
			//拨付
			in.setAmount(incomeAmt.subtract(amount));//余额
			in.setDrugAmt(drugAmt);//药品
			in.setMedcAmt(medcAmt);//医疗
			in.setTranAmt(amount.multiply(new BigDecimal(-1)));//交易金额
			in.setInOrOut(2);//收入1或支出2
		}
		in.setTranTime(date);//交易时间		
		in.setOtherAmt(new BigDecimal(0));
		in.setNote1(voucher);//凭证编号
		this.insertEntity(in);
		logger.info("------新增收入账户各项余额变更记录成功");
		
		
		//----新增账户余额变更记录----2次
		WjwAccchange out = new WjwAccchange();
		out.setUnitNo(payOutUnitNo);//机构号
		out.setUnitName(payOutUnitName);//机构名称
		out.setAccNo(payOutAccno);//支出账户号
		out.setAccName(payOutAccName);//支出账户名称
//		if("2".equals(type)){
//			//支付时，对方帐号为收款人帐号、姓名
//			out.setDfAccno(inAccno);//对方账户
//			out.setDfAccname(inAccname);//对账户名称
//		}else{
//			out.setDfAccno(incomeAccno);//对方账户(收入账户)
//			out.setDfAccname(incomeAccName);//对账户名称(收入账户)
//		}
		in.setDfAccno(payOutAccno);//对方账户(支出账户)
		in.setDfAccname(payOutAccName);//对账户名称(支出账户)	
		if("1".equals(type)){
			//缴款
			out.setAmount(payOutAmt.add(amount));//余额
			out.setDrugAmt(drugAmt);//药品收入
			out.setMedcAmt(medcAmt.add(otherAmt));//医疗+其他
			out.setTranAmt(amount);//交易金额
			out.setInOrOut(1);//收入1或支出2
		}else if("2".equals(type)){
			//支付
			out.setAmount(payOutAmt.subtract(amount));//余额
			out.setDrugAmt(drugAmt);//药品
			out.setMedcAmt(medcAmt);//医疗
			out.setTranAmt(amount.multiply(new BigDecimal(-1)));//交易金额
			out.setInOrOut(2);//收入1或支出2
		}else if("3".equals(type)){
			//拨付
			out.setAmount(payOutAmt.add(amount));//余额
			out.setDrugAmt(drugAmt);//药品
			out.setMedcAmt(medcAmt);//医疗
			out.setTranAmt(amount);//交易金额
			out.setInOrOut(1);//收入1或支出2
		}
		
		out.setTranTime(date);//交易时间
		out.setOtherAmt(new BigDecimal(0));
		out.setNote1(voucher);//凭证编号
		this.insertEntity(out);
		logger.info("------新增支出账户各项余额变更记录成功");

	}
	
	public WjwPaydetail MaptoEntity(Map<String, Object> pay) {
		WjwPaydetail entity = new WjwPaydetail();
		entity.setAmount((BigDecimal) pay.get("AMOUNT"));
		entity.setZjFld(dataConvert(pay.get("ZJ_FLD")));
		entity.setZcAcctno(dataConvert(pay.get("ZC_ACCTNO")));
		entity.setZcAcctname(dataConvert(pay.get("ZC_ACCTNAME")));
		entity.setZbDetail(dataConvert(pay.get("ZB_DETAIL")));
		entity.setYt(dataConvert(pay.get("YT")));
		entity.setXnAcctno(dataConvert(pay.get("XN_ACCTNO")));
		entity.setXnAcctName(dataConvert(pay.get("XN_ACCTNAME")));
		entity.setUnitNo(dataConvert(pay.get("UNIT_NO")));
		entity.setUnitName(dataConvert(pay.get("UNIT_NAME")));
		entity.setTopYsdw(dataConvert(pay.get("TOP_YSDW")));
		entity.setStatus((Integer) pay.get("STATUS"));
		entity.setPayWay((Integer) pay.get("PAY_WAY"));
		entity.setPayTime(dataConvert(pay.get("PAY_TIME")));
		entity.setOutBank(dataConvert(pay.get("OUT_BANK")));
		entity.setOutAccno(dataConvert(pay.get("OUT_ACCNO")));
		entity.setOutAccname(dataConvert(pay.get("OUT_ACCNAME")));
		entity.setOperNo(dataConvert(pay.get("OPER_NO")));
		entity.setNote2(dataConvert(pay.get("NOTE2")));
		entity.setNote1(dataConvert(pay.get("NOTE1")));
		entity.setItmeYs(dataConvert(pay.get("ITME_YS")));
		entity.setItem((Integer) pay.get("ITEM"));
		entity.setInName(dataConvert(pay.get("IN_NAME")));
		entity.setInBank(dataConvert(pay.get("IN_BANK")));
		entity.setInAccno(dataConvert(pay.get("IN_ACCNO")));
		entity.setFuncFl(dataConvert(pay.get("FUNC_FL")));
		entity.setFootYsdw(dataConvert(pay.get("FOOT_YSDW")));
		entity.setFhUser(dataConvert(pay.get("FH_USER")));
		entity.setFhTime(dataConvert(pay.get("FH_TIME")));
		entity.setEcnoFl(dataConvert(pay.get("ECNO_FL")));
		entity.setCurrency(dataConvert(pay.get("CURRENCY")));
		entity.setConnNo(dataConvert(pay.get("CONN_NO")));
		entity.setBackFlg((Integer) pay.get("BACK_FLG"));
		entity.setBackVoucher(dataConvert(pay.get("BACK_VOUCHER")));
		return entity;
	}
	
	private String dataConvert(Object obj){
		if(obj==null){
			return "";
		}else{
			return (String)obj;
		}
	}
	/**
	 * 退汇处理
	 * @param pay
	 * @param type  1---原始退汇处理    2---退汇新纪录处理
 	 * @param voucher
	 * @param inOrOut
	 * @param date
	 */
	public void bankBackPayTally(Map<String,Object> pay,String type,String voucher,String inOrOut,String date,int accCode){

//		String inAccno = pay.get("IN_ACCNO").toString();//收款人账户
//		String inAccname = pay.get("IN_NAME").toString();//收款人姓名
		BigDecimal payAmount = (BigDecimal) pay.get("AMOUNT");
		BigDecimal payMedcAmt = (BigDecimal) pay.get("MEDICAL_AMT"); 
		BigDecimal payDrugAmt = (BigDecimal) pay.get("DRUG_AMT");
//		String payOutAccno = pay.get("ZC_ACCTNO").toString();
//		String payOutAccname = pay.get("ZC_ACCTNAME").toString();
				
		Map<String,Object> main = dao.findMainAccountChange(inOrOut,accCode);
		String mainAccNo = main.get("ACC_NO").toString();
		String mainAccName = main.get("CUST_NAME").toString();
		String mainUnitNo = main.get("UNIT_NO").toString();
		String mainUnitName = main.get("UNIT_NAME").toString();
		BigDecimal mainAmount = (BigDecimal) main.get("AMOUNT");
		BigDecimal mainMedcAmt = (BigDecimal) main.get("MEDICAL_AMT"); 
		BigDecimal mainDrugAmt = (BigDecimal) main.get("DRUG_AMT"); 
		if("1".equals(type)){
			dao.updateAccountAmount(mainAmount.add(payAmount), mainDrugAmt.add(payDrugAmt),
					mainMedcAmt.add(payMedcAmt), mainAccNo);
			logger.info("退汇---更新主账户余额，各项余额增加");
		}else if("2".equals(type)){
			dao.updateAccountAmount(mainAmount.subtract(payAmount), mainDrugAmt.subtract(payDrugAmt),
					mainMedcAmt.subtract(payMedcAmt), mainAccNo);
			logger.info("支付/退汇---更新主账户余额，各项余额减少");
		}
		
		Map<String,Object> dqf = dao.findDqfAccountChange(inOrOut,accCode);
		String dqfAccNo = dqf.get("ACC_NO").toString();
		String dqfAccName = dqf.get("CUST_NAME").toString();
		String dqfUnitNo = dqf.get("UNIT_NO").toString();
		String dqfUnitName = dqf.get("UNIT_NAME").toString();
		BigDecimal dqfAmount = (BigDecimal) dqf.get("AMOUNT");
		BigDecimal dqfMedcAmt = (BigDecimal) dqf.get("MEDICAL_AMT"); 
		BigDecimal dqfDrugAmt = (BigDecimal) dqf.get("DRUG_AMT"); 
		if("1".equals(type)){//1---原始退汇处理    2---退汇新纪录处理
			dao.updateAccountAmount(dqfAmount.add(payAmount), dqfDrugAmt.add(payDrugAmt),
					dqfMedcAmt.add(payMedcAmt),dqfAccNo);
			logger.info("退汇---更新待清分账户余额，各项余额增加");
		}else if("2".equals(type)){
			dao.updateAccountAmount(dqfAmount.subtract(payAmount), dqfDrugAmt.subtract(payDrugAmt),
					dqfMedcAmt.subtract(payMedcAmt),dqfAccNo);
			logger.info("支付/退汇---更新待清分账户余额，各项余额减少");
		}
	
		//4、新增账户余额变更表2条记录		
		WjwAccchange parent = new WjwAccchange();
		parent.setUnitNo(mainUnitNo);
		parent.setUnitName(mainUnitName);
		parent.setAccNo(mainAccNo);
		parent.setAccName(mainAccName);
		parent.setDfAccno(dqfAccNo);
		parent.setDfAccname(dqfAccName);
		if("1".equals(type)){
			parent.setAmount(mainAmount.add(payAmount));
			parent.setTranAmt(payAmount);
			parent.setInOrOut(1);
			parent.setFlag(5);//**1 入账 2 清分 3 利息*  5--退汇入账  6--退汇支出/
		}else if("2".equals(type)){
			parent.setAmount(mainAmount.subtract(payAmount));
			parent.setTranAmt(payAmount.multiply(new BigDecimal(-1)));
			parent.setInOrOut(2);
			parent.setFlag(6);//**1 入账 2 清分 3 利息*  5--退汇入账  6--退汇支出/
		}		
				
		parent.setTranTime(date);		
		parent.setMedcAmt(payMedcAmt);
		parent.setDrugAmt(payDrugAmt);
		parent.setOtherAmt(new BigDecimal(0.00));		
		parent.setNote2(voucher);//退汇的凭证号记入note2而不失note1
		this.insertEntity(parent);
		logger.info("新增账户余额变更表记录1次");
		
		WjwAccchange child = new WjwAccchange();
		child.setUnitNo(dqfUnitNo);
		child.setUnitName(dqfUnitName);
		child.setAccNo(dqfAccNo);
		child.setAccName(dqfAccName);
		child.setDfAccno(mainAccNo);
		child.setDfAccname(mainAccName);
		if("1".equals(type)){
			child.setAmount(dqfAmount.add(payAmount));
			child.setTranAmt(payAmount);
			child.setInOrOut(1);
			child.setFlag(5);//**1 入账 2 清分 3 利息*  5--退汇入账  6--退汇支出/
		}else if("2".equals(type)){
			child.setAmount(dqfAmount.subtract(payAmount));
			child.setTranAmt(payAmount.multiply(new BigDecimal(-1)));
			child.setInOrOut(2);
			child.setFlag(6);//**1 入账 2 清分 3 利息*  5--退汇入账  6--退汇支出/
		}

		child.setTranTime(date);
		child.setMedcAmt(payMedcAmt);
		child.setDrugAmt(payDrugAmt);
		child.setOtherAmt(new BigDecimal(0.00));
		child.setNote2(voucher);
		this.insertEntity(child);
		logger.info("新增账户余额变更表记录2次");
	}
	/**
	 * 退汇支付账务回滚
	 * @param pay
	 * @param type
	 * @param voucher
	 * @param date
	 */
	public void rollbackBankBackTallyByType(Map<String, Object> pay, String type,
			String voucher, String date) {
		BigDecimal payAmount = (BigDecimal) pay.get("AMOUNT");
		BigDecimal payMedcAmt = (BigDecimal) pay.get("MEDICAL_AMT"); 
		BigDecimal payDrugAmt = (BigDecimal) pay.get("DRUG_AMT");
				
		Map<String,Object> main = dao.findMainAccount("2");
		String mainAccNo = main.get("ACC_NO").toString();
//		String mainAccName = main.get("CUST_NAME").toString();
//		String mainUnitNo = main.get("UNIT_NO").toString();
//		String mainUnitName = main.get("UNIT_NAME").toString();
		BigDecimal mainAmount = (BigDecimal) main.get("AMOUNT");
		BigDecimal mainMedcAmt = (BigDecimal) main.get("MEDICAL_AMT"); 
		BigDecimal mainDrugAmt = (BigDecimal) main.get("DRUG_AMT"); 
		dao.updateAccountAmount(mainAmount.add(payAmount), mainDrugAmt.add(payDrugAmt),
				mainMedcAmt.add(payMedcAmt), mainAccNo);
		logger.info("退汇支付对账，账务回滚---更新主账户余额，各项余额增加");
		
		Map<String,Object> dqf = dao.findDqfAccount("2");
		String dqfAccNo = dqf.get("ACC_NO").toString();
//		String dqfAccName = dqf.get("CUST_NAME").toString();
//		String dqfUnitNo = dqf.get("UNIT_NO").toString();
//		String dqfUnitName = dqf.get("UNIT_NAME").toString();
		BigDecimal dqfAmount = (BigDecimal) dqf.get("AMOUNT");
		BigDecimal dqfMedcAmt = (BigDecimal) dqf.get("MEDICAL_AMT"); 
		BigDecimal dqfDrugAmt = (BigDecimal) dqf.get("DRUG_AMT"); 
		dao.updateAccountAmount(dqfAmount.add(payAmount), dqfDrugAmt.add(payDrugAmt),
				dqfMedcAmt.add(payMedcAmt),dqfAccNo);
		logger.info("退汇支付对账，账务回滚---更新待清分账户余额，各项余额增加");
		
		//删除账户余额变更表纪录
		dao.deleteByVoucher(voucher,true);
	}
	
}
