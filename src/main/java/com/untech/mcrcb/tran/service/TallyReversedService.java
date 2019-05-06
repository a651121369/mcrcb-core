package com.untech.mcrcb.tran.service;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TallyReversedService extends AccountingBaseService{

/**
 * //缴款冲正
 * @param voucher
 * @param amount
 * @param user
 * @param date
 * @return 0---凭证号不存在   1----冲正成功   2----非记账状态，不可冲正  3---冲正失败
 */
	@Transactional
	public int incomeTallyReversed(String voucher, String amount, String user,
			String date) {
		int flag = 2;
		try {
			Map<String,Object> income = dao.findIncomeByVoucher(voucher);
			if(null==income){
				 return 0;
			}
			String status = income.get("STATUS").toString();
			if("6".equals(status)){
				this.rollbackTallyByType(income, "1", voucher, date);
				dao.updateIncomeDetailStatus(voucher, 5, "", "");
				flag = 1;
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
			return 3;
		}
		return flag;
	}
/**
 * 支付冲正
 * @param voucher
 * @param amount
 * @param user
 * @param date
 * @return 0---凭证号不存在   1----冲正成功   2----非记账状态，不可冲正  3---冲正失败
 */
	@Transactional
	public int payTallyReversed(String voucher, String amount, String user,
			String date) {
		int flag = 2;
		try {
			Map<String,Object> pay = dao.findPayByVoucher(voucher);
			if(null==pay){
				return 0;
			}
			String status = pay.get("STATUS").toString();	
			Integer backFlg = (Integer) pay.get("BACK_FLG");
			if("6".equals(status)){
				if(backFlg!=null&&backFlg==1){
					this.rollbackBankBackTallyByType(pay, "2", voucher, date);
					dao.updatePayDetailStatus(voucher, 5, "", "", true);
				}else{
					this.rollbackTallyByType(pay, "2", voucher, date);
					dao.updatePayDetailStatus(voucher, 5, "", "",false);	
				}

			}
			flag = 1;
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
			return 3;
		}
		return flag;
	}

	/**
	 * 拨付冲正
	 * @param voucher
	 * @param amount
	 * @param user
	 * @param date
	 * @return 0---凭证号不存在   1----冲正成功   2----非记账状态，不可冲正  3---冲正失败
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int appropriateTallyReversed(String voucher, String amount,
			String user, String date) {
		int flag = 2;
		try {
			Map<String,Object> appropriate = dao.findAppropriateByVoucher(voucher);
			if(null==appropriate){
				return 0;
			}
			String status = appropriate.get("STATUS").toString();
			if("6".equals(status)){
				this.rollbackTallyByType(appropriate, "3", voucher, date);
				
				String conn = appropriate.get("CONN_NO").toString();
				List<Map<String,Object>> list = dao.findAppropriateDetailByConn(conn);
				for(Map<String,Object> app:list){
					this.rollbackTallyByType(app, "3", voucher, date);
				}
				dao.updateAppropriateStatus(voucher, 5, "", "");
				flag = 1;
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
			return 3;
		}
		return flag;
	}
	/**
	 * 利息收入冲正
	 * @param voucher
	 * @param amount
	 * @param user
	 * @param inOrOut
	 * @param date
	 * @return 0---凭证号不存在   1----冲正成功   2----非记账状态，不可冲正  3---冲正失败
	 */
	@Transactional
	public int interestInTallyReversed(String voucher, String tranAmt,
			String user, String inOrOut, String date) {
//		try {
//			//1、通过凭证号查找余额变更表利息收入记录
//			Map<String,Object> interest = dao.findTradeDetailByVoucher(voucher);
//			if(null==interest){
//				logger.info("利息收入冲正----记录不存在");
//				return 0;
//			}
//			BigDecimal amount = (BigDecimal) interest.get("TRAN_AMT");
//			//2、更新主账户利息收入字段，利息余额减少
//			Map<String,Object> main = dao.findMainAccount(inOrOut);
//			String accNo = main.get("ACC_NO").toString();
//			BigDecimal intCome = (BigDecimal) main.get("INT_COME");
//			dao.updateMainAccountInterest(accNo,intCome.subtract(amount));
//			logger.info("利息收入冲正----更新主账户利息字段，利息余额减少");
//			//3、删除余额变更表利息收入记录
//			dao.deleteByVoucher(voucher, false);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return 3;
//		}
//		return 1;
		
		
		try {
			//1、通过凭证编号查询账户余额变更表记录，待清分入账有2条记录，我们取其中一条记录
			Map<String,Object> map = dao.findDQFTradeByVoucher(voucher);
			if(null==map){
				logger.info("利息入账冲正----记录不存在");
				return 0;
			}
			//2、通过FLAG标志判断该笔入账是否已经清分，如果已清分，则不可以冲正
			Integer flag = (Integer) map.get("FLAG");
			if(flag==2){
				logger.info("利息入账冲正----该笔记录已经清分，不可冲正");
				return 2;
			}
			BigDecimal amount = (BigDecimal) map.get("TRAN_AMT");
			//3、更新主账户、待清分账户余额，主账户余额减少，待清分账户余额减少
			Map<String,Object> main = dao.findMainAccount(inOrOut);
			String mainAccno = main.get("ACC_NO").toString();
			BigDecimal mainAmt = (BigDecimal) main.get("AMOUNT");
			dao.updateAccountAmount(mainAccno, mainAmt.subtract(amount));
			logger.info("利息入账冲正----主账户余额减少");
			Map<String,Object> dqf = dao.findDqfAccount(inOrOut);
			String dqfAccno = dqf.get("ACC_NO").toString();
			BigDecimal dqfAmt = (BigDecimal) dqf.get("AMOUNT");
			dao.updateAccountAmount(dqfAccno, dqfAmt.subtract(amount));
			logger.info("利息入账冲正----待清分账户余额减少");
			//4、删除账户余额变更表记录
			dao.deleteByVoucher(voucher, false);
			logger.info("利息入账冲正----删除账户余额变更表记录");
		} catch (Exception e) {
			e.printStackTrace();
			return 3;
		}
		return 1;
	}
	/**
	 * 利息支出冲正
	 * @param voucher
	 * @param amount
	 * @param user
	 * @param inOrOut
	 * @param date
	 * @return 0---凭证号不存在   1----冲正成功   2----非记账状态，不可冲正  3---冲正失败
	 */
	@Transactional
	public int interestOutTallyReversed(String voucher, String tranAmt,
			String user,String date) {
		try {
			//1、通过凭证编号查询利息支出记录
			Map<String,Object> interest = dao.findInterestOutByVoucher(voucher);
			if(null==interest){
				logger.info("利息支出冲正----记录不存在");
				return 0;	
			}
			BigDecimal amount = (BigDecimal) interest.get("AMOUNT");
			Integer status = (Integer) interest.get("STATUS");
			if(status!=6){
				logger.info("利息支出冲正----该笔记录是非记账状态不可冲正");
				return 2;
			}
			String inOrOut = interest.get("IN_OR_OUT").toString();
			//2、更新主账户利息余额字段，利息余额增加
			Map<String,Object> main = dao.findMainAccount(inOrOut);
			String accNo = main.get("ACC_NO").toString();
			BigDecimal intCome = (BigDecimal) main.get("INT_COME");
			dao.updateMainAccountInterest(accNo, intCome.add(amount));
			logger.info("利息支出冲正----更新主账户利息余额字段，利息余额增加");
			//3、删除账户余额变更表记录
			dao.deleteByVoucher(voucher, false);
			logger.info("利息支出冲正----删除账户余额变更表记录");
			//4、更新利息支出表状态为5---未记账状态
			dao.updateInterestOutStatus(5, voucher);
			logger.info("利息支出冲正----更新利息支出表状态为5---未记账状态");
		} catch (Exception e) {
			e.printStackTrace();
			return 3;
		}
		return 1;
	}
	/**
	 * 待清分入账冲正
	 * @param voucher
	 * @param amount
	 * @param user
	 * @param inOrOut
	 * @param date
	 * @return 0---凭证号不存在   1----冲正成功   2----非记账状态，不可冲正  3---冲正失败
	 */
	@Transactional
	public int unknownIncomeTallyReversed(String voucher, String tranAmt,
			String user, String inOrOut, String date) {
		try {
			//1、通过凭证编号查询账户余额变更表记录，待清分入账有2条记录，我们取其中一条记录
			Map<String,Object> map = dao.findDQFTradeByVoucher(voucher);
			if(null==map){
				logger.info("待清分入账冲正----记录不存在");
				return 0;
			}
			//2、通过FLAG标志判断该笔入账是否已经清分，如果已清分，则不可以冲正
			Integer flag = (Integer) map.get("FLAG");
			if(flag==2){
				logger.info("待清分入账冲正----该笔记录已经清分，不可冲正");
				return 2;
			}
			BigDecimal amount = (BigDecimal) map.get("TRAN_AMT");
			//3、更新主账户、待清分账户余额，主账户余额减少，待清分账户余额减少
			Map<String,Object> main = dao.findMainAccount(inOrOut);
			String mainAccno = main.get("ACC_NO").toString();
			BigDecimal mainAmt = (BigDecimal) main.get("AMOUNT");
			dao.updateAccountAmount(mainAccno, mainAmt.subtract(amount));
			logger.info("待清分入账冲正----主账户余额减少");
			Map<String,Object> dqf = dao.findDqfAccount(inOrOut);
			String dqfAccno = dqf.get("ACC_NO").toString();
			BigDecimal dqfAmt = (BigDecimal) dqf.get("AMOUNT");
			dao.updateAccountAmount(dqfAccno, dqfAmt.subtract(amount));
			logger.info("待清分入账冲正----待清分账户余额减少");
			//4、删除账户余额变更表记录
			dao.deleteByVoucher(voucher, false);
			logger.info("待清分入账冲正----删除账户余额变更表记录");
		} catch (Exception e) {
			e.printStackTrace();
			return 3;
		}
		return 1;
	}
	/**
	 * 退汇撤销
	 * @param voucher
	 * @param amount
	 * @param user
	 * @param inOrOut
	 * @param date
	 * @return 0---凭证号不存在   1----冲正成功   2----非记账状态，不可冲正  3---冲正失败
	 */
	@Transactional
	public int payBackTallyReversed(String voucher, String amount, String user,
			String date) {
		//1、通过凭证编号和退汇状态查找支付明细表记录  backFlg=3---退汇
		Map<String,Object> bankPay = dao.findPayBackByVoucher(voucher,3);		
		if(null==bankPay){
			logger.info("退汇撤销----记录不存在");
			return 0;
		}
		Map<String,Object> newPay = dao.findNewPayByBackVoucher(voucher);
		String newVoucher = newPay.get("OPER_NO").toString();
		Integer status = (Integer) newPay.get("STATUS");
		//2、判断新生成的支付记录是否已经记账，如果已记账，则不可冲正
		if(6==status){
			logger.info("退汇撤销----新记录已经记账，该笔退汇不可冲正");
			return 2;
		}
		//3、变更主账户、待清分账户余额，主账户余额减少，待清分账户余额减少
		Map<String,Object> pay = dao.findPayByVoucher(voucher);
		BigDecimal payAmount = (BigDecimal) pay.get("AMOUNT");
		BigDecimal payMedcAmt = (BigDecimal) pay.get("MEDICAL_AMT"); 
		BigDecimal payDrugAmt = (BigDecimal) pay.get("DRUG_AMT");
		Map<String,Object> dqf = dao.findDqfAccount("2");
		String dqfAccNo = dqf.get("ACC_NO").toString();
		BigDecimal dqfAmount = (BigDecimal) dqf.get("AMOUNT");
		BigDecimal dqfMedcAmt = (BigDecimal) dqf.get("MEDICAL_AMT"); 
		BigDecimal dqfDrugAmt = (BigDecimal) dqf.get("DRUG_AMT"); 
		
		dao.updateAccountAmount(dqfAmount.subtract(payAmount), dqfDrugAmt.subtract(payDrugAmt),
				dqfMedcAmt.subtract(payMedcAmt),dqfAccNo);
		logger.info("退汇撤销---更新待清分账户余额，各项余额减少");
		Map<String,Object> main = dao.findMainAccount("2");
		String mainAccNo = main.get("ACC_NO").toString();
		BigDecimal mainAmount = (BigDecimal) main.get("AMOUNT");
		BigDecimal mainMedcAmt = (BigDecimal) main.get("MEDICAL_AMT"); 
		BigDecimal mainDrugAmt = (BigDecimal) main.get("DRUG_AMT");
		dao.updateAccountAmount(mainAmount.subtract(payAmount), mainDrugAmt.subtract(payDrugAmt),
				mainMedcAmt.subtract(payMedcAmt), mainAccNo);
		logger.info("退汇---更新主账户余额，各项余额减少");
		//4、删除账户余额变更表
		dao.deleteByVoucher(voucher, true);
		
		//5、删除新记录、
		dao.deleteNewPayByVoucher(newVoucher);	
		logger.info("退汇---删除新生产记录");
		//6、原始记录退汇状态变更为0--正常状态
		dao.updatePayBackFlag(0, voucher,"");
		logger.info("退汇---更新原始记录退汇状态为0--正常状态");
		return 1;
	}
	
}
