package com.untech.mcrcb.web.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.untech.mcrcb.tran.service.AccountingBaseService;
import com.untech.mcrcb.web.dao.WjwAccountHXDao;
import com.unteck.common.dao.support.Pagination;

@Service
public class WjwAccountHXService extends AccountingBaseService{

	@Autowired
	private WjwAccountHXDao hxdao;
	
 
	public Pagination<Map<String, Object>> getCertNoInfo(Integer start, Integer limit,String startTime,String endTime,
			String tranType,String status,String connNo,String amount){
		//缴款
		if("1".equals(tranType)){
			return hxdao.incomeDetail(start,limit,startTime,endTime,status,connNo,amount);
		}
		//支付
		else if("2".equals(tranType)){
			return hxdao.payDetail(start,limit,startTime,endTime,status,connNo,amount);
		}
		//拨付
		else if("3".equals(tranType)){
			return hxdao.appropriate(start,limit,startTime,endTime,status,connNo,amount);
		}else{
			return hxdao.getCertNoInfo(start, limit, startTime,endTime,status, connNo,amount);
		}
		
    	
    }
//	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
//	public int saveInfo(String connNo,
//			String STATE,String dspUserno,String dspUserName,String time){
//		String UNIT_NAME1 = "";
//		String UNIT_NO1 = "";
//		String UNIT_NAME2 = "";
//		String UNIT_NO2 = "";
//		System.out.println("STATE = " + STATE );
//		if("1".equals(STATE)){//支付凭证为缴款
//			//根据凭证编号获取凭证状态
//			String status = dao.selectStatus(connNo);
//			if(!"5".equals(status)){
//				return 1;
//			}
//			//更新凭证状态
//			dao.update(dspUserno, dspUserName, connNo,time);
//			
//			//缴款明细表更新成功后,查询相关转账信息,更新多级账户表,并在余额变更表中新增账户余额变更记录
//			List<Map<String,Object>> list =  dao.getWjwIncomedetail(connNo);
//			if(list != null && list.size()>0){
////				String UNIT_NAME = list.get(0).get("UNIT_NAME")== null ? "":list.get(0).get("UNIT_NAME").toString();
////				String UNIT_NO = list.get(0).get("UNIT_NO")== null ? "":list.get(0).get("UNIT_NO").toString();
//				BigDecimal AMOUNT =  (BigDecimal) list.get(0).get("AMOUNT")== null ? new BigDecimal(0):(BigDecimal)list.get(0).get("AMOUNT");
//				BigDecimal DRUG_AMT = (BigDecimal)list.get(0).get("DRUG_AMT")== null ? new BigDecimal(0):(BigDecimal)list.get(0).get("DRUG_AMT");
//				BigDecimal MEDICAL_AMT = (BigDecimal)list.get(0).get("MEDICAL_AMT")== null ? new BigDecimal(0):(BigDecimal)list.get(0).get("MEDICAL_AMT");
//				BigDecimal OTHER_AMT = (BigDecimal)list.get(0).get("OTHER_AMT")== null ? new BigDecimal(0):(BigDecimal)list.get(0).get("OTHER_AMT");
//				String ZcAcctno = list.get(0).get("ZC_ACCTNO") == null ? "":list.get(0).get("ZC_ACCTNO").toString();
//				String XnAccno = list.get(0).get("XN_ACCTNO")== null ? "":list.get(0).get("XN_ACCTNO").toString();
//				String ZC_ACCTNAME = list.get(0).get("ZC_ACCTNAME")== null ? "":list.get(0).get("ZC_ACCTNAME").toString();
//				String XN_ACCTNAME = list.get(0).get("XN_ACCTNAME")== null ? "":list.get(0).get("XN_ACCTNAME").toString();
////				String FH_TIME = list.get(0).get("FH_TIME")== null ? "":list.get(0).get("FH_TIME").toString();
//				
//				List<Map<String,Object>> list1 = dao.getMoneySql(ZcAcctno);
//				BigDecimal UpZAmount = new BigDecimal(0);
//				BigDecimal zAmount = new BigDecimal(0);
//				BigDecimal zDrugAmt = new BigDecimal(0);
//				BigDecimal zMedicalAmt = new BigDecimal(0);
//				BigDecimal UpZDrugAmt = new BigDecimal(0);
//				BigDecimal UpZMedicalAmt = new BigDecimal(0);
//				if(list1 != null && list1.size()>0){
//					System.out.println("更新主账户余额开始......");
//					zAmount = (BigDecimal) list1.get(0).get("AMOUNT");//总账户余额
//					zDrugAmt = (BigDecimal) list1.get(0).get("DRUG_AMT");//总账户药品金额
//					zMedicalAmt = (BigDecimal) list1.get(0).get("MEDICAL_AMT");//总账户医疗金额
//					UNIT_NO1 = list1.get(0).get("UNIT_NO").toString();//总账户机构号
//					UNIT_NAME1 = list1.get(0).get("UNIT_NAME").toString();//总账户机构名
//					
//					UpZAmount = AMOUNT.add(zAmount);//账户余额
//					UpZDrugAmt = DRUG_AMT.add(zDrugAmt);//药品金额
//					UpZMedicalAmt = MEDICAL_AMT.add(zMedicalAmt).add(OTHER_AMT);//医疗金额+其他金额
//					
//					//更新虚拟总账户余额
//					System.out.println("更新主账户余额成功");
//					dao.updateZmoney(UpZAmount,UpZDrugAmt,UpZMedicalAmt,ZcAcctno);
//					
//				}
//				
//				List<Map<String,Object>> list2 = dao.getMoneySql(XnAccno);
//				BigDecimal UpXAmount = new BigDecimal(0);
//				BigDecimal UpXDrugAmt = new BigDecimal(0);
//				BigDecimal UpXMedicalAmt = new BigDecimal(0);
//				
//				BigDecimal xAmount = new BigDecimal(0);
//				BigDecimal xDrugAmt = new BigDecimal(0);
//				BigDecimal xMedicalAmt = new BigDecimal(0);
//				if(list2 != null && list2.size()>0){
//					xAmount = (BigDecimal) list2.get(0).get("AMOUNT");//子账户余额
//					xDrugAmt = (BigDecimal) list2.get(0).get("DRUG_AMT");//子账户药品金额
//					xMedicalAmt = (BigDecimal) list2.get(0).get("MEDICAL_AMT");//子账户医疗金额
//					UNIT_NO2 = list2.get(0).get("UNIT_NO").toString();//子账户机构号
//					UNIT_NAME2 = list2.get(0).get("UNIT_NAME").toString();//子账户机构名
//					
//					UpXAmount = AMOUNT.add(xAmount);//账户余额
//					UpXDrugAmt = DRUG_AMT.add(xDrugAmt);//药品金额
//					UpXMedicalAmt = MEDICAL_AMT.add(xMedicalAmt).add(OTHER_AMT);//医疗金额+其他金额
//					//更新虚拟子账户余额
//					System.out.println("更新子账户余额成功");
//					dao.updateZmoney(UpXAmount,UpXDrugAmt,UpXMedicalAmt,XnAccno);
//					
//				}
//				
//				//账户余额变更表中新增余额变更记录
//				//新增总账户余额变更记录
//				WjwAccchange accChange = new WjwAccchange();
//				accChange.setUnitNo(UNIT_NO1);//机构号
//				accChange.setUnitName(UNIT_NAME1);//机构名称
//				accChange.setAccNo(ZcAcctno);//账户号(总账户)
//				accChange.setAccName(ZC_ACCTNAME);//账户名称(总账户)
//				accChange.setDfAccno(XnAccno);//对方账户(子账户)
//				accChange.setDfAccname(XN_ACCTNAME);//对账户名称(子账户)
//				accChange.setAmount(UpZAmount);//余额(总账户)
//				accChange.setDrugAmt(DRUG_AMT);//药品收入
//				accChange.setMedcAmt(MEDICAL_AMT.add(OTHER_AMT));//医疗收入+其他收入
//				accChange.setTranAmt(AMOUNT);//交易金额
//				accChange.setTranTime(time);//交易时间
//				accChange.setInOrOut(1);//收入1或支出2
//				accChange.setOtherAmt(new BigDecimal(0));
//				accChange.setNote1(connNo);//凭证编号
//				System.out.println(accChange.getUnitNo());
//				this.insertEntity(accChange);
//				System.out.println("新增主账户余额变更记录成功");
//				
//				//新增子账户余额变更记录
//				WjwAccchange XaccChange = new WjwAccchange();
//				XaccChange.setUnitNo(UNIT_NO2);//机构号
//				XaccChange.setUnitName(UNIT_NAME2);//机构名称
//				XaccChange.setAccNo(XnAccno);//账户号(子账户)
//				XaccChange.setAccName(XN_ACCTNAME);//账户名称(子账户)
//				XaccChange.setDfAccno(ZcAcctno);//对方账户(总账户)
//				XaccChange.setDfAccname(ZC_ACCTNAME);//对账户名称(总账户)
//				XaccChange.setAmount(UpXAmount);//余额(子账户)
//				XaccChange.setDrugAmt(DRUG_AMT);//药品收入
//				XaccChange.setMedcAmt(MEDICAL_AMT.add(OTHER_AMT));//医疗收入+其他收入
//				XaccChange.setTranAmt(AMOUNT);//交易金额
//				XaccChange.setTranTime(time);//交易时间
//				XaccChange.setInOrOut(1);//收入1或支出2
//				XaccChange.setOtherAmt(new BigDecimal(0));
//				XaccChange.setNote1(connNo);//凭证编号
//				this.insertEntity(XaccChange);
//				System.out.println("新增子账户余额变更记录成功");
//				
//				return 0;
//				
//			}
//		}else if("2".equals(STATE)){//支付凭证为支付
//			//根据凭证编号获取凭证状态
//			
//			List<Map<String,Object>> list0 = dao.selectZFStatus(connNo);
//			if(list0 != null && list0.size()>0){
//				String status =  list0.get(0).get("STATUS")+"";
//				System.out.println("1".equals(status));
//				if(!"5".equals(status)){
//					return 1;
//				}
//				String CONN_NO = (String) list0.get(0).get("CONN_NO");
//			
//				//更新支付明细表凭证状态
//				dao.updateZFStatus(dspUserno, dspUserName, connNo,time);
//				//更新支付主表凭证状态
//				dao.updateZFZBStatus(CONN_NO,time);
//				//支付明细表、支付主表状态更新成功后,查询相关转账信息,更新多级账户表,并在余额变更表中新增账户余额变更记录
//				List<Map<String,Object>> list =  dao.getWjwPaydetail(connNo);
//				if(list != null && list.size()>0){
////					String UNIT_NAME = list.get(0).get("UNIT_NAME")== null ? "":list.get(0).get("UNIT_NAME").toString();
////					String UNIT_NO = list.get(0).get("UNIT_NO")== null ? "":list.get(0).get("UNIT_NO").toString();
//					BigDecimal AMOUNT =  (BigDecimal) list.get(0).get("AMOUNT")== null ? new BigDecimal(0):(BigDecimal)list.get(0).get("AMOUNT");
//					BigDecimal DRUG_AMT = (BigDecimal)list.get(0).get("DRUG_AMT")== null ? new BigDecimal(0): (BigDecimal)list.get(0).get("DRUG_AMT");
//					BigDecimal MEDICAL_AMT = (BigDecimal)list.get(0).get("MEDICAL_AMT")== null ? new BigDecimal(0):(BigDecimal)list.get(0).get("MEDICAL_AMT");
//					String ZC_ACCTNO = list.get(0).get("ZC_ACCTNO") == null ? "":list.get(0).get("ZC_ACCTNO").toString();
//					String ZC_ACCTNAME = list.get(0).get("ZC_ACCTNAME")== null ? "":list.get(0).get("ZC_ACCTNAME").toString();
//					String inAccno = list.get(0).get("IN_ACCNO")==null?"":list.get(0).get("IN_ACCNO").toString();
//					String inAccname = list.get(0).get("IN_NAME")==null?"":list.get(0).get("IN_NAME").toString();
//					String XN_ACCTNO = list.get(0).get("XN_ACCTNO")== null ? "":list.get(0).get("XN_ACCTNO").toString();
//					String XN_ACCTNAME = list.get(0).get("XN_ACCTNAME")== null ? "":list.get(0).get("XN_ACCTNAME").toString();
//	//				String FH_TIME = list.get(0).get("FH_TIME")== null ? "":list.get(0).get("FH_TIME").toString();
//					List<Map<String,Object>> list1 = dao.getMoneySql(ZC_ACCTNO);
//					BigDecimal UpZAmount = new BigDecimal(0);
//					BigDecimal zAmount = new BigDecimal(0);
//					BigDecimal zDrugAmt = new BigDecimal(0);
//					BigDecimal zMedicalAmt = new BigDecimal(0);
//					BigDecimal UpZDrugAmt = new BigDecimal(0);
//					BigDecimal UpZMedicalAmt = new BigDecimal(0);
//					if(list1 != null && list1.size()>0){
//						zAmount = (BigDecimal) list1.get(0).get("AMOUNT");//主账户余额
//						zDrugAmt = (BigDecimal) list1.get(0).get("DRUG_AMT");//主账户药品金额
//						zMedicalAmt = (BigDecimal) list1.get(0).get("MEDICAL_AMT");//主账户医疗金额
//						UNIT_NO1 = list1.get(0).get("UNIT_NO").toString();//总账户机构号
//						UNIT_NAME1 = list1.get(0).get("UNIT_NAME").toString();//总账户机构名
//						
//						UpZAmount = zAmount.subtract(AMOUNT);
//						UpZDrugAmt = zDrugAmt.subtract(DRUG_AMT);
//						UpZMedicalAmt = zMedicalAmt.subtract(MEDICAL_AMT);
//						//更新虚拟总账户余额
//						System.out.println("更新主账户余额成功");
//						dao.updateZmoney(UpZAmount,UpZDrugAmt,UpZMedicalAmt,ZC_ACCTNO);
//						
//						
//					}
//					List<Map<String,Object>> list2 = dao.getMoneySql(XN_ACCTNO);
//					BigDecimal UpXAmount = new BigDecimal(0);
//					BigDecimal UpXDrugAmt = new BigDecimal(0);
//					BigDecimal UpXMedicalAmt = new BigDecimal(0);
//					
//					BigDecimal xAmount = new BigDecimal(0);
//					BigDecimal xDrugAmt = new BigDecimal(0);
//					BigDecimal xMedicalAmt = new BigDecimal(0);
//					if(list2 != null && list2.size()>0){
//						xAmount = (BigDecimal) list2.get(0).get("AMOUNT");//子账户余额
//						xDrugAmt = (BigDecimal) list2.get(0).get("DRUG_AMT");//子账户药品金额
//						xMedicalAmt = (BigDecimal) list2.get(0).get("MEDICAL_AMT");//子账户医疗金额
//						UNIT_NO2 = list2.get(0).get("UNIT_NO").toString();//子账户机构号
//						UNIT_NAME2 = list2.get(0).get("UNIT_NAME").toString();//子账户机构名
//						
//						UpXAmount = xAmount.subtract(AMOUNT);
//						UpXDrugAmt = xDrugAmt.subtract(DRUG_AMT);
//						UpXMedicalAmt = xMedicalAmt.subtract(MEDICAL_AMT);
//						//更新虚拟子账户余额
//						System.out.println("更新子账户余额成功");
//						dao.updateZmoney(UpXAmount,UpXDrugAmt,UpXMedicalAmt,XN_ACCTNO);
//
//					}
//					
//					//账户余额变更表中新增余额变更记录
//					//新增总账户余额变更记录
//					WjwAccchange accChange = new WjwAccchange();
//					accChange.setUnitNo(UNIT_NO1);//机构号
//					accChange.setUnitName(UNIT_NAME1);//机构名称
//					accChange.setAccNo(ZC_ACCTNO);//账户号(总账户)
//					accChange.setAccName(ZC_ACCTNAME);//账户名称(总账户)
////					accChange.setDfAccno(XN_ACCTNO);//对方账户(子账户)
////					accChange.setDfAccname(XN_ACCTNAME);//对账户名称(子账户)
//					accChange.setDfAccno(inAccno);		//此处对方账号应为实际收款人账号-----------------------
//					accChange.setDfAccname(inAccname);//此处对方账号应为实际收款人账户名称----------------------
//					accChange.setAmount(UpZAmount);//余额(总账户)
//					accChange.setDrugAmt(DRUG_AMT);//药品收入
//					accChange.setMedcAmt(MEDICAL_AMT);//医疗收入
//					accChange.setTranAmt(AMOUNT.multiply(new BigDecimal(-1)));//交易金额
//					accChange.setTranTime(time);//交易时间
//					accChange.setInOrOut(2);//收入1或支出2
//					accChange.setOtherAmt(new BigDecimal(0));
//					accChange.setNote1(connNo);//凭证编号
//					System.out.println(accChange.getUnitNo());
//					this.insertEntity(accChange);
//					System.out.println("新增主账户余额变更记录成功");
//					
//					//新增子账户余额变更记录
//					WjwAccchange XaccChange = new WjwAccchange();
//					XaccChange.setUnitNo(UNIT_NO2);//机构号
//					XaccChange.setUnitName(UNIT_NAME2);//机构名称
//					XaccChange.setAccNo(XN_ACCTNO);//账户号(子账户)
//					XaccChange.setAccName(XN_ACCTNAME);//账户名称(子账户)
////					XaccChange.setDfAccno(ZC_ACCTNO);//对方账户(总账户)
////					XaccChange.setDfAccname(ZC_ACCTNAME);//对账户名称(总账户)
//					XaccChange.setDfAccno(inAccno);		//此处对方账号应为实际收款人账号-----------------------
//					XaccChange.setDfAccname(inAccname);//此处对方账号应为实际收款人账户名称----------------------
//					XaccChange.setAmount(UpXAmount);//余额(子账户)
//					XaccChange.setDrugAmt(DRUG_AMT);//药品收入
//					XaccChange.setMedcAmt(MEDICAL_AMT);//医疗收入
//					XaccChange.setTranAmt(AMOUNT.multiply(new BigDecimal(-1)));//交易金额
//					XaccChange.setTranTime(time);//交易时间
//	//				XaccChange.setInOrOut(1);//收入1或支出2
//					XaccChange.setInOrOut(2);//收入1或支出2
//					XaccChange.setOtherAmt(new BigDecimal(0));
//					XaccChange.setNote1(connNo);//凭证编号
//					this.insertEntity(XaccChange);
//					System.out.println("新增子账户余额变更记录成功");
//					return 0;
//				}
//			}
//		}else if("3".equals(STATE)){//支付凭证为拨付
//			//根据凭证编号获取凭证状态
//			List<Map<String,Object>> list0 = dao.selectBFStatus(connNo);
//			if(list0 != null && list0.size()>0){
//				String status = list0.get(0).get("STATUS")+"";
//					if(!"5".equals(status)){
//						return 1;
//					}
//				String CONN_NO = (String) list0.get(0).get("CONN_NO");//拨付主表与拨付明细表关联号
//				//根据关联号查询拨付明细表中查询各收入和支出子账户拨付明细
//				List<Map<String,Object>> childList = dao.getChildAccInfo(CONN_NO);
//				if(childList != null && childList.size()>0){
//					for(int i = 0;i<childList.size();i++){
//						System.out.println("i = " + i);
////						String UNIT_NAME = childList.get(i).get("UNIT_NAME")== null ? "":childList.get(i).get("UNIT_NAME").toString();
////						String UNIT_NO = childList.get(i).get("UNIT_NO")== null ? "":childList.get(i).get("UNIT_NO").toString();
//						BigDecimal AMOUNT =  (BigDecimal) childList.get(i).get("AMOUNT")== null ? new BigDecimal(0):(BigDecimal)childList.get(i).get("AMOUNT");
//						BigDecimal DRUG_BF_AMT = (BigDecimal)childList.get(i).get("DRUG_BF_AMT")== null ? new BigDecimal(0):(BigDecimal)childList.get(i).get("DRUG_BF_AMT");
//						BigDecimal MEDC_BF_AMT = (BigDecimal)childList.get(i).get("MEDC_BF_AMT")== null ? new BigDecimal(0):(BigDecimal)childList.get(i).get("MEDC_BF_AMT");
//						String ZC_ACCTNO = childList.get(i).get("ZC_ACCTNO") == null ? "":childList.get(i).get("ZC_ACCTNO").toString();
//						String ZC_ACCTNAME = childList.get(i).get("ZC_ACCTNAME")== null ? "":childList.get(i).get("ZC_ACCTNAME").toString();
//						String XN_ACCTNO = childList.get(i).get("XN_ACCTNO")== null ? "":childList.get(i).get("XN_ACCTNO").toString();
//						String XN_ACCTNAME = childList.get(i).get("XN_ACCTNAME")== null ? "":childList.get(i).get("XN_ACCTNAME").toString();
////						String BF_TIME = childList.get(i).get("BF_TIME")== null ? "":childList.get(i).get("BF_TIME").toString();
//						
//						if(AMOUNT.compareTo(new BigDecimal(0.00)) > 0){
//							//更新多级账户表支出子账户余额
//							List<Map<String,Object>> childList1 = dao.getMoneySql(ZC_ACCTNO);
//							BigDecimal UpZAmount = new BigDecimal(0);
//							BigDecimal zAmount = new BigDecimal(0);
//							BigDecimal zDrugAmt = new BigDecimal(0);
//							BigDecimal zMedicalAmt = new BigDecimal(0);
//							BigDecimal UpZDrugAmt = new BigDecimal(0);
//							BigDecimal UpZMedicalAmt = new BigDecimal(0);
//							if(childList1 != null && childList1.size()>0){
//								zAmount = (BigDecimal) childList1.get(0).get("AMOUNT");//支出账户余额
//								zDrugAmt = (BigDecimal) childList1.get(0).get("DRUG_AMT");//支出账户药品金额
//								zMedicalAmt = (BigDecimal) childList1.get(0).get("MEDICAL_AMT");//支出账户医疗金额
//								UNIT_NO1 = childList1.get(0).get("UNIT_NO").toString();//子账户机构号
//								UNIT_NAME1 = childList1.get(0).get("UNIT_NAME").toString();//子账户机构名
//								
//								UpZAmount = zAmount.add(AMOUNT);//支出账户增加本次拨付金额
//								UpZDrugAmt = zDrugAmt.add(DRUG_BF_AMT);//支付药品金额增加本次拨付的药品金额
//								UpZMedicalAmt = zMedicalAmt.add(MEDC_BF_AMT);//支出医疗金额增加本次拨付的医疗金额
//								//更新虚拟总账户余额
//								System.out.println("更新支出子账户余额成功");
//								dao.updateZmoney(UpZAmount,UpZDrugAmt,UpZMedicalAmt,ZC_ACCTNO);
//								
//								
//							}
//							
//							//更新多级账户表中收入子账户余额
//							List<Map<String,Object>> childList2 = dao.getMoneySql(XN_ACCTNO);
//							BigDecimal UpXAmount = new BigDecimal(0);
//							BigDecimal UpXDrugAmt = new BigDecimal(0);
//							BigDecimal UpXMedicalAmt = new BigDecimal(0);
//							
//							BigDecimal xAmount = new BigDecimal(0);
//							BigDecimal xDrugAmt = new BigDecimal(0);
//							BigDecimal xMedicalAmt = new BigDecimal(0);
//							
//							if(childList2 != null && childList2.size()>0){
//								xAmount = (BigDecimal) childList2.get(0).get("AMOUNT");//收入账户余额
//								xDrugAmt = (BigDecimal) childList2.get(0).get("DRUG_AMT");//收入账户药品金额
//								xMedicalAmt = (BigDecimal) childList2.get(0).get("MEDICAL_AMT");//收入账户医疗金额
//								UNIT_NO2 = childList2.get(0).get("UNIT_NO").toString();//子账户机构号
//								UNIT_NAME2 = childList2.get(0).get("UNIT_NAME").toString();//子账户机构名
//								
//								UpXAmount = xAmount.subtract(AMOUNT);//收入账户减去本次拨付金额
//								UpXDrugAmt = xDrugAmt.subtract(DRUG_BF_AMT);//收入药品金额减去本次拨付的药品金额
//								UpXMedicalAmt = xMedicalAmt.subtract(MEDC_BF_AMT);//收入医疗金额减去本次拨付的医疗金额
//								//更新虚拟总账户余额
//								System.out.println("更新收入子账户余额成功");
//								dao.updateZmoney(UpXAmount,UpXDrugAmt,UpXMedicalAmt,XN_ACCTNO);
//								
//							}
//							
//							//账户余额变更表中新增余额变更记录
//							//新增支出账户余额变更记录
//							WjwAccchange accChange = new WjwAccchange();
//							accChange.setUnitNo(UNIT_NO1);//机构号
//							accChange.setUnitName(UNIT_NAME1);//机构名称
//							accChange.setAccNo(ZC_ACCTNO);//账户号(支出账户)
//							accChange.setAccName(ZC_ACCTNAME);//账户名称(支出账户)
//							accChange.setDfAccno(XN_ACCTNO);//对方账户(收入账户)
//							accChange.setDfAccname(XN_ACCTNAME);//对账户名称(收入账户)
//							accChange.setAmount(UpZAmount);//余额(支出账户)
//							accChange.setDrugAmt(DRUG_BF_AMT);//药品支出
//							accChange.setMedcAmt(MEDC_BF_AMT);//医疗支出
//							accChange.setTranAmt(AMOUNT);//交易金额
//							accChange.setTranTime(time);//交易时间
//							accChange.setInOrOut(1);//收入1或支出2
//							accChange.setOtherAmt(new BigDecimal(0));
//							accChange.setNote1(connNo);//凭证编号
//							System.out.println(accChange.getUnitNo());
//							this.insertEntity(accChange);
//							System.out.println("新增支出子账户余额变更记录成功");
//							
//							//新增收入账户余额变更记录
//							WjwAccchange XaccChange = new WjwAccchange();
//							XaccChange.setUnitNo(UNIT_NO2);//机构号
//							XaccChange.setUnitName(UNIT_NAME2);//机构名称
//							XaccChange.setAccNo(XN_ACCTNO);//账户号(收入账户)
//							XaccChange.setAccName(XN_ACCTNAME);//账户名称(收入账户)
//							XaccChange.setDfAccno(ZC_ACCTNO);//对方账户(支出账户)
//							XaccChange.setDfAccname(ZC_ACCTNAME);//对账户名称(支出账户)
//							XaccChange.setAmount(UpXAmount);//余额(支出账户)
//							XaccChange.setDrugAmt(DRUG_BF_AMT);//药品收入
//							XaccChange.setMedcAmt(MEDC_BF_AMT);//医疗收入
//							XaccChange.setTranAmt(AMOUNT.multiply(new BigDecimal(-1)));//交易金额
//							XaccChange.setTranTime(time);//交易时间
//							XaccChange.setInOrOut(2);//收入1或支出2
//							XaccChange.setOtherAmt(new BigDecimal(0));
//							XaccChange.setNote1(connNo);//凭证编号
//							this.insertEntity(XaccChange);
//							System.out.println("新增收入子账户余额变更记录成功");
//						}
//					}
//				}
// 			}
//			
//			//更新拨付主表凭证状态
//			dao.updateBFStatus(dspUserno, dspUserName, connNo,time);
//			
//			//拨付主表状态更新成功后,查询相关转账信息,更新多级账户表,并在余额变更表中新增账户余额变更记录
//			List<Map<String,Object>> list =  dao.getWjwBFHZ(connNo);
//			if(list != null && list.size()>0){
////				String UNIT_NAME = list.get(0).get("UNIT_NAME")== null ? "":list.get(0).get("UNIT_NAME").toString();
////				String UNIT_NO = list.get(0).get("UNIT_NO")== null ? "":list.get(0).get("UNIT_NO").toString();
//				BigDecimal AMOUNT =  (BigDecimal) list.get(0).get("AMOUNT")== null ? new BigDecimal(0):(BigDecimal)list.get(0).get("AMOUNT");
//				BigDecimal DRUG_AMT = (BigDecimal)list.get(0).get("DRUG_AMT")== null ? new BigDecimal(0):(BigDecimal)list.get(0).get("DRUG_AMT");
//				BigDecimal MEDICAL_AMT = (BigDecimal)list.get(0).get("MEDICAL_AMT")== null ? new BigDecimal(0):(BigDecimal)list.get(0).get("MEDICAL_AMT");
//				String ZC_ACCTNO = list.get(0).get("ZC_ACCTNO") == null ? "":list.get(0).get("ZC_ACCTNO").toString();//支出户  
//				String ZC_ACCTNAME = list.get(0).get("ZC_ACCTNAME")== null ? "":list.get(0).get("ZC_ACCTNAME").toString();
//				String XN_ACCTNO = list.get(0).get("XN_ACCTNO")== null ? "":list.get(0).get("XN_ACCTNO").toString();//收入户
//				String XN_ACCTNAME = list.get(0).get("XN_ACCTNAME")== null ? "":list.get(0).get("XN_ACCTNAME").toString();
//				
//				List<Map<String,Object>> list1 = dao.getMoneySql(ZC_ACCTNO);
//				BigDecimal UpZAmount = new BigDecimal(0);
//				BigDecimal zAmount = new BigDecimal(0);
//				BigDecimal zDrugAmt = new BigDecimal(0);
//				BigDecimal zMedicalAmt = new BigDecimal(0);
//				BigDecimal UpZDrugAmt = new BigDecimal(0);
//				BigDecimal UpZMedicalAmt = new BigDecimal(0);
//				
//				if(list1 != null && list1.size()>0){
//					zAmount = (BigDecimal) list1.get(0).get("AMOUNT");//支出总账户余额
//					zDrugAmt = (BigDecimal) list1.get(0).get("DRUG_AMT");//支出总账户药品金额
//					zMedicalAmt = (BigDecimal) list1.get(0).get("MEDICAL_AMT");//支出总账户医疗金额
//					UNIT_NO1 = list1.get(0).get("UNIT_NO").toString();//机构号
//					UNIT_NAME1 = list1.get(0).get("UNIT_NAME").toString();//机构名
//					
//					UpZAmount = zAmount.add(AMOUNT);//支出总账户增加本次拨付总金额
//					UpZDrugAmt = zDrugAmt.add(DRUG_AMT);//支付药品总金额增加本次拨付的药品总金额
//					UpZMedicalAmt = zMedicalAmt.add(MEDICAL_AMT);//支出医疗总金额增加本次拨付的医疗总金额
//					//更新虚拟支出总账户余额
//					System.out.println("更新支出总账户余额成功");
//					dao.updateZmoney(UpZAmount,UpZDrugAmt,UpZMedicalAmt,ZC_ACCTNO);
//					
//				}
//				
//				List<Map<String,Object>> list2 = dao.getMoneySql(XN_ACCTNO);
//				BigDecimal UpXAmount = new BigDecimal(0);
//				BigDecimal UpXDrugAmt = new BigDecimal(0);
//				BigDecimal UpXMedicalAmt = new BigDecimal(0);
//				
//				BigDecimal xAmount = new BigDecimal(0);
//				BigDecimal xDrugAmt = new BigDecimal(0);
//				BigDecimal xMedicalAmt = new BigDecimal(0);
//				
//				if(list2 != null && list2.size()>0){
//					xAmount = (BigDecimal) list2.get(0).get("AMOUNT");//收入总账户余额
//					xDrugAmt = (BigDecimal) list2.get(0).get("DRUG_AMT");//收入总账户药品金额
//					xMedicalAmt = (BigDecimal) list2.get(0).get("MEDICAL_AMT");//收入总账户医疗金额
//					UNIT_NO2 = list2.get(0).get("UNIT_NO").toString();//总账户机构号
//					UNIT_NAME2 = list2.get(0).get("UNIT_NAME").toString();//总账户机构名
//					
//					UpXAmount = xAmount.subtract(AMOUNT);//收入总账户减去本次拨付总金额
//					UpXDrugAmt = xDrugAmt.subtract(DRUG_AMT);//收入药品总金额减去本次拨付的药品总金额
//					UpXMedicalAmt = xMedicalAmt.subtract(MEDICAL_AMT);//收入医疗总金额减去本次拨付的医疗总金额
//					//更新虚拟总账户余额
//					System.out.println("更新收入总账户余额成功");
//					dao.updateZmoney(UpXAmount,UpXDrugAmt,UpXMedicalAmt,XN_ACCTNO);
//					
//				}
//				
//				//账户余额变更表中新增余额变更记录
//				//新增支出账户余额变更记录
//				WjwAccchange accChange = new WjwAccchange();
//				accChange.setUnitNo(UNIT_NO1);//机构号
//				accChange.setUnitName(UNIT_NAME1);//机构名称
//				accChange.setAccNo(ZC_ACCTNO);//账户号(支出账户)
//				accChange.setAccName(ZC_ACCTNAME);//账户名称(支出账户)
//				accChange.setDfAccno(XN_ACCTNO);//对方账户(收入账户)
//				accChange.setDfAccname(XN_ACCTNAME);//对账户名称(支出账户)
//				accChange.setAmount(UpZAmount);//余额(支出账户)
//				accChange.setDrugAmt(DRUG_AMT);//药品支出
//				accChange.setMedcAmt(MEDICAL_AMT);//医疗支出
//				accChange.setTranAmt(AMOUNT);//交易金额
//				accChange.setTranTime(time);//交易时间
////				accChange.setInOrOut(2);//收入1或支出2
//				accChange.setInOrOut(1);//收入1或支出2
//				accChange.setOtherAmt(new BigDecimal(0));
//				accChange.setNote1(connNo);//凭证编号
//				System.out.println(accChange.getUnitNo());
//				this.insertEntity(accChange);
//				System.out.println("新增支出主账户余额变更记录成功");
//				
//				//新增收入账户余额变更记录
//				WjwAccchange XaccChange = new WjwAccchange();
//				XaccChange.setUnitNo(UNIT_NO2);//机构号
//				XaccChange.setUnitName(UNIT_NAME2);//机构名称
//				XaccChange.setAccNo(XN_ACCTNO);//账户号(收入账户)
//				XaccChange.setAccName(XN_ACCTNAME);//账户名称(收入账户)
//				XaccChange.setDfAccno(ZC_ACCTNO);//对方账户(支出账户)
//				XaccChange.setDfAccname(ZC_ACCTNAME);//对账户名称(支出账户)
//				XaccChange.setAmount(UpXAmount);//余额(支出账户)
//				XaccChange.setDrugAmt(DRUG_AMT);//药品收入
//				XaccChange.setMedcAmt(MEDICAL_AMT);//医疗收入
//				XaccChange.setTranAmt(AMOUNT.multiply(new BigDecimal(-1)));//交易金额
//				XaccChange.setTranTime(time);//交易时间
////				XaccChange.setInOrOut(1);//收入1或支出2
//				XaccChange.setInOrOut(2);//收入1或支出2
//				XaccChange.setOtherAmt(new BigDecimal(0));
//				XaccChange.setNote1(connNo);//凭证编号
//				this.insertEntity(XaccChange);
//				System.out.println("新增收入主账户余额变更记录成功");
//				return 0;
//			}
//			
//		}else{
//			return 9;
//		}
//    	return 9;
//    }
	@Transactional
	public int tally(String voucher,
			String state,String tranAmt,String user,String time){
		int flg = 0;
		if("1".equals(state)){//缴款
			flg = this.incomeTally(voucher, tranAmt, user, time);
		}else if("2".equals(state)){//支付
			flg = this.payTally(voucher, tranAmt, user, time);
		}else if("3".equals(state)){//拨付
			flg = this.appropriateTally(voucher, tranAmt, user, time);
		}
		return flg;
	}

	/**
	 * 缴款-----记账
	 * @param voucher
	 * @param tranAmt
	 * @return int  0--失败  1--成功  2--非记账状态   3--已记账   4--记账金额不正确  5--数据库访问异常  6--系统异常
	 */
	@Transactional
	public  int incomeTally(String voucher,String tranAmt,String user,String date){
		int tallySuccess = 0;
		BigDecimal amt = new BigDecimal(tranAmt);

		try {
			//根据缴款凭证号，查询缴款明细表，找出对应的缴款记录
			Map<String,Object> income = dao.findIncomeByVoucher(voucher);
			if(null==income){
				logger.info("缴款----该笔记录不存在！");
				return 0;
			}
			
			//判断凭证状态，对已经做过记账处理的，不能重复记账
			String status = income.get("STATUS").toString();
			if("6".equals(status)){
				logger.info("缴款----该笔记录已经记账！");
				return 3;
			}
			if(!"5".equals(status)){
				logger.info("缴款----该笔记录是非记账状态！");
				return 2;
			}
			BigDecimal amount =  (BigDecimal)income.get("AMOUNT");//缴款总金额
			//判断交易金额是否等于缴款金额
			if(amount.compareTo(amt)!=0){
				logger.info("交易金额与实际缴款金额不一致！");
				return 4;
			}
			this.tallyByType(income, "1", voucher, date);
			//更新缴款明细表status状态为6---记账完成
			dao.updateIncomeDetailStatus(voucher, 6, date, user);
			tallySuccess = 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 6;
		}
		
		return tallySuccess;
	}
	/**
	 * 支付-----记账
	 * @param voucher
	 * @param tranAmt
	 * @return int 0--失败  1--成功  2--非记账状态   3--已记账   4--记账金额不正确  5--数据库访问异常  6--系统异常
	 */
	@Transactional
	public int payTally(String voucher, String tranAmt,String user,String date) {
		int tallySuccess = 0;
		int accCode=0;
		BigDecimal amt = new BigDecimal(tranAmt);
		try {
			//根据凭证编号获取支付明细表该条记录	
			Map<String,Object> pay = dao.findPaydetailByVoucher(voucher);
			Map<String,Object> account =accountDao.findAccountByAccNo((String) pay.get("ZC_ACCTNO"));
			if(account.get("ACCOUNT_SYS_CODE")!=null){
				accCode=Integer.parseInt(account.get("ACCOUNT_SYS_CODE").toString());
			}else{
				String e="未知账号！";
				throw new RuntimeException(e);
			}
			if(null==pay){
				logger.info("支付----该笔记录不存在！");
				return 0;
			}

			//获取凭证状态
			String status = pay.get("STATUS").toString();
			if("6".equals(status)){
				logger.info("支付----该笔记录已经记账！");
				return 3;
			}
			if(!"5".equals(status)){
				logger.info("支付----该笔记录是非记账状态！");
				return 2;
			}
			//获取关联号
			String connNo = (String) pay.get("CONN_NO");					
			BigDecimal amount =  (BigDecimal) pay.get("AMOUNT");//支付总金额
			if(amount.compareTo(amt)!=0){
				logger.info("支付----交易金额与实际支付金额不一致！");
				return 4;
			}
			//对于退汇重新生成的凭证，支付款从待清分账户扣除
			Integer backFlag = (Integer) pay.get("BACK_FLG");//退汇标志
			pay = dao.findPayByVoucher(voucher);
			if(backFlag != null && 1==backFlag){
				this.bankBackPayTally(pay, "2", voucher, "2", date,accCode);
				//更新支付明细表凭证状态
				dao.updatePayDetailStatus(voucher, 6, date, user,true);
			}else{						
				this.tallyByType(pay, "2", voucher, date);
				//更新支付明细表凭证状态
				dao.updatePayDetailStatus(voucher, 6, date, user,false);
			}
		
			//更新支付主表凭证状态
			dao.updatePayMainStatus(connNo, date);
			tallySuccess = 1;
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("支付----系统异常");
			return 6;
		}
				
		return tallySuccess;
	}
	/**
	 * 拨付---记账
	 * @param voucher
	 * @param tranAmt
	 * @param userCode
	 * @param time
	 * @return int 0--失败  1--成功  2--非记账状态   3--已记账   4--记账金额不正确  5--数据库访问异常  6--系统异常
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int appropriateTally(String voucher, String tranAmt,String user,String date) {
		int tallySuccess = 0;
		BigDecimal amt = new BigDecimal(tranAmt);
		try {
			//根据凭证号查询拨付主表信息
			Map<String,Object> appropriate = dao.findAppropriateByVoucher(voucher);
			if(null==appropriate){
				logger.info("拨付----拨付记录不存在！");
				return 0;
			}
			//获取凭证状态
			String status = appropriate.get("STATUS").toString();
			if("6".equals(status)){
				logger.info("拨付----该笔记录已经记账！");
				return 3;
			}
			if(!"5".equals(status)){
				logger.info("拨付----该笔记录是非记账状态！");
				return 2;
			}
			//获取拨付主表关联号
			String connNo = appropriate.get("CONN_NO").toString();
			BigDecimal amountAll = (BigDecimal)appropriate.get("AMOUNT");//拨付总金额
			//判断交易金额是否正确
			if(amt.compareTo(amountAll)!=0){
				logger.info("拨付----交易金额与拨付总金额不一致！");
				return 4;
			}
			this.tallyByType(appropriate, "3", voucher, date);
			dao.updateAppropriateStatus(voucher,6, date, user);
			//通过关联号，查找拨付明细表数据
			List<Map<String,Object>> list = dao.findAppropriateDetailByConn(connNo);
			for(Map<String,Object> app:list){
				BigDecimal bfAmount =  (BigDecimal)app.get("AMOUNT");//拨付总金额
				if(bfAmount.compareTo(new BigDecimal(0.00))>0){
					this.tallyByType(app, "3", voucher, date);					
				}
			}
			tallySuccess = 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 6;
		}
		return tallySuccess;
	}
//	public BaseDao<WjwAccchange, Long> getHibernateBaseDao() {
//		return this.dao;
//	}
}
