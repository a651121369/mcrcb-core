package com.untech.mcrcb.tran.service;

import com.untech.mcrcb.web.common.Constants;
import com.untech.mcrcb.web.model.WjwAccchange;
import com.untech.mcrcb.web.model.WjwPaydetail;
import com.untech.mcrcb.web.util.RlateUtil;
import com.untech.mcrcb.web.util.Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TallyService extends AccountingBaseService{


	
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
			//对于银行记账成功、资金平台没有记账的记录进行补记账
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
			Map<String,Object> account = accountDao.findAccountByAccNo((String) pay.get("ZC_ACCTNO"));
			if(account.get("ACCOUNT_SYS_CODE")!=null){
				accCode=Integer.parseInt(account.get("ACCOUNT_SYS_CODE").toString());
			}else{
				String e="未知账号！";
				throw new RuntimeException(e);
			}
			if(backFlag!=null&&backFlag==1){
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
			
			//记录收款人的姓名、账户、开户行
			String unitNo = pay.get("UNIT_NO").toString();
			String inName = pay.get("IN_NAME").toString();
			String inAccno = pay.get("IN_ACCNO").toString();
			String inBank = pay.get("IN_BANK").toString();
			Map<String,Object> payeeInfo = dao.findPayeeInfoByAccno(unitNo,inAccno);
			if(null==payeeInfo){
				dao.savePayeeInfo(unitNo,inName,inAccno,inBank);
				logger.info("----保存收款人信息（收款人姓名、账号、开户行）成功----");
			}else{
				dao.updatePayeeInfo(unitNo,inName,inAccno,inBank);
				logger.info("----更新收款人信息（收款人姓名、账号、开户行）成功----");
			}
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
	/**
	 * 利息入账---记账
	 * @param voucher
	 * @param amount
	 * @param userCode
	 * @param inOrOut
	 * @param time
	 * @return 0--账户不存在 ，1--交易成功  6--系统异常
	 */
//	@Transactional
//	public int interestInTally(String voucher, String tranAmt, String userCode,
//			String inOrOut, String time) {
//		int tallySuccess = 0;
//		//1、判断利息入收入主账户还是支出主账户？  inOrOut :1-收入主账户；2-支出主账户
//		//2、查询账户信息
//		try {
//			//通过凭证号查找账户余额变更表，如果有记录，则该笔利息已经录入
//			Map<String,Object> map = dao.findTradeDetailByVoucher(voucher);
//			if(map!=null){
//				logger.info("利息入账----该笔利息已经入账！");
//				return 3;
//			}
//			Map<String,Object> main = dao.findMainAccount(inOrOut);
//			if(null==main){
//				logger.info("利息入账----查找账户失败！");
//				return 0;
//			}
//			String accNo = main.get("ACC_NO").toString();
//			String unitNo = main.get("UNIT_NO").toString();
//			String unitName = main.get("UNIT_NAME").toString();
//			String custName = main.get("CUST_NAME").toString();
//			BigDecimal amount = (BigDecimal) main.get("AMOUNT");
//			BigDecimal interest = (BigDecimal) main.get("INT_COME");
//			interest = interest.add(new BigDecimal(tranAmt));
//			//3、更新该账户的利息字段金额，金额增加
//			dao.updateMainAccountInterest(accNo, interest);
//			logger.info("利息入账---更新主账户利息字段，利息余额增加");
//			//4、新增一条记录到账户余额变更表
//			
//			WjwAccchange entity = new WjwAccchange();
//			entity.setUnitNo(unitNo);
//			entity.setUnitName(unitName);
//			entity.setAccNo(accNo);
//			entity.setAccName(custName);
//			entity.setDfAccno("");
//			entity.setDfAccname("利息");
//			entity.setAmount(amount);
//			entity.setDrugAmt(new BigDecimal(0.00));
//			entity.setMedcAmt(new BigDecimal(0.00));
//			entity.setTranAmt(new BigDecimal(tranAmt));
//			entity.setTranTime(time);
//			entity.setInOrOut(1);
//			entity.setOtherAmt(new BigDecimal(0.00));
//			entity.setNote1(voucher);
//			entity.setFlag(3);//    /**1 入账 2 清分 3 利息收入*/
//			this.insertEntity(entity);
//			logger.info("利息入账---新增账户余额变更表记录成功");
//			tallySuccess = 1;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return 6;
//		}
//		return tallySuccess;
//	}
	@Transactional
	public int interestInTally(String voucher, String tranAmt, String userCode,
			String inOrOut, String time) {
		int tallySuccess = 0;
		//1、查询主账户和待清分账户信息
		try {
			Map<String,Object> map = dao.findTradeDetailByVoucher(voucher);
			if(map!=null){
				logger.info("利息入账----该笔交易已经入账！");
				return 3;
			}
			
			Map<String,Object> main = dao.findMainAccount(inOrOut);
			if(null==main){
				return 0;
			}
			String mainAccNo = main.get("ACC_NO").toString();
			String mainAccName = main.get("CUST_NAME").toString();
			String mainUnitNo = main.get("UNIT_NO").toString();
			String mainUnitName = main.get("UNIT_NAME").toString();
			BigDecimal mainAmount = (BigDecimal) main.get("AMOUNT");
			
			Map<String,Object> dqf = dao.findDqfAccount(inOrOut);
			if(null==dqf){
				return 0;
			}
			String dqfAccNo = dqf.get("ACC_NO").toString();
			String dqfAccName = dqf.get("CUST_NAME").toString();
			String dqfUnitNo = dqf.get("UNIT_NO").toString();
			String dqfUnitName = dqf.get("UNIT_NAME").toString();
			BigDecimal dqfAmount = (BigDecimal) dqf.get("AMOUNT");

			//2、更新主账户和待清分账户余额（科目对应的金额不变）
			dao.updateAccountAmount(mainAccNo,mainAmount.add(new BigDecimal(tranAmt)));
			logger.info("利息入账---主账户总余额增加");
			dao.updateAccountAmount(dqfAccNo,dqfAmount.add(new BigDecimal(tranAmt)));
			logger.info("利息入账---待清分账户总余额增加");
			//3、新增账户余额变更表2条记录
			
			String uuid = RlateUtil.getUuid();
			WjwAccchange parent = new WjwAccchange();
			parent.setUnitNo(mainUnitNo);
			parent.setUnitName(mainUnitName);
			parent.setAccNo(mainAccNo);
			parent.setAccName(mainAccName);
			parent.setDfAccno(dqfAccNo);
			parent.setDfAccname(dqfAccName);
			parent.setAmount(mainAmount.add(new BigDecimal(tranAmt)));
			parent.setTranAmt(new BigDecimal(tranAmt));
			parent.setTranTime(time);
			parent.setInOrOut(1);
			parent.setMedcAmt(new BigDecimal(0.00));
			parent.setDrugAmt(new BigDecimal(0.00));
			parent.setOtherAmt(new BigDecimal(0.00));
			parent.setFlag(1);//**1 入账 2 清分 3 利息收入*/
			parent.setNote1(voucher);
			parent.setNote2(uuid);
//			parent.setTradeCount(Integer.parseInt(count));
			parent.setUnkType(1);//1利息，2不明来账
			this.insertEntity(parent);
			logger.info("利息入账---新增账户余额变更表记录1次");
			
			WjwAccchange child = new WjwAccchange();
			child.setUnitNo(dqfUnitNo);
			child.setUnitName(dqfUnitName);
			child.setAccNo(dqfAccNo);
			child.setAccName(dqfAccName);
			child.setDfAccno(mainAccNo);
			child.setDfAccname(mainAccName);
			child.setAmount(dqfAmount.add(new BigDecimal(tranAmt)));
			child.setTranAmt(new BigDecimal(tranAmt));
			child.setTranTime(time);
			child.setInOrOut(1);
			child.setMedcAmt(new BigDecimal(0.00));
			child.setDrugAmt(new BigDecimal(0.00));
			child.setOtherAmt(new BigDecimal(0.00));
			child.setFlag(1);//**1 入账 2 清分 3 利息*/
			child.setNote1(voucher);
			child.setNote2(uuid);
//			child.setTradeCount(Integer.parseInt(count));
			child.setUnkType(1);//1利息，2不明来账
			this.insertEntity(child);
			logger.info("利息入账---新增账户余额变更表记录2次");
			tallySuccess = 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 6;
		}
		return tallySuccess;
	}
	
	/*** TODO: ---待清分入账（财政拨款、补贴）--入账
	 * @Date 2019/4/8 0008
	* @param voucher （交易凭证）
	* @param tranAmt （总金额）
	* @param userCode 操作用户（银行柜员）
	* @param inOrOut 收入or支出 ---因新增医养中心体系，王瑞不同意在中台进行改动，所以，使用蹩脚方案，添加一个下拉框的选项说明是敬老院
	 *                1-收入，2-支出， 4-医养中心，当为（1，2）时，默认为卫生院，4为医养中心
	* @param count 总笔
	* @param time 时间
	* @param desc 描述
	 * @return int 0--账户不存在  1--交易成功  3--已入账  6--系统异常
	 **/
	@Transactional
	public int unknownIncomeTally(String voucher, String tranAmt, String userCode,
			String inOrOut,String count, String time,String desc) {
		int tallySuccess = 0;
		//账号体系，0-什么都不是
		int accCode=0;
		//1、查询主账户和待清分账户信息
		try {
			Map<String,Object> map = dao.findTradeDetailByVoucher(voucher);
			if(map!=null){
				logger.info("待清分入账----该笔交易已经入账！");
				return 3;
			}
			if (Constants.IN_OUT.YLY.equals(inOrOut)){
				accCode=Constants.ACC_CODE.JLY;
				//医养中心是直接待清分到支出账户
				inOrOut=Constants.IN_OUT.OUT;
			}else{
				accCode=Constants.ACC_CODE.WSY;
			}
			Map<String,Object> main = dao.findMainAccountChange(inOrOut,accCode);
			if(null==main){
				return 0;
			}
			String mainAccNo = main.get("ACC_NO").toString();
			String mainAccName = main.get("CUST_NAME").toString();
			String mainUnitNo = main.get("UNIT_NO").toString();
			String mainUnitName = main.get("UNIT_NAME").toString();
			BigDecimal mainAmount = (BigDecimal) main.get("AMOUNT");
			Map<String,Object> dqf = dao.findDqfAccountChange(inOrOut,accCode);

			if(null==dqf){
				return 0;
			}
			String dqfAccNo = dqf.get("ACC_NO").toString();
			String dqfAccName = dqf.get("CUST_NAME").toString();
			String dqfUnitNo = dqf.get("UNIT_NO").toString();
			String dqfUnitName = dqf.get("UNIT_NAME").toString();
			BigDecimal dqfAmount = (BigDecimal) dqf.get("AMOUNT");

			//2、更新主账户和待清分账户余额（科目对应的金额不变）
			dao.updateAccountAmount(mainAccNo,mainAmount.add(new BigDecimal(tranAmt)));
			logger.info("待清分入账---主账户总余额增加");
			dao.updateAccountAmount(dqfAccNo,dqfAmount.add(new BigDecimal(tranAmt)));
			logger.info("待清分入账---待清分账户总余额增加");
			//3、新增账户余额变更表2条记录
			String uuid = RlateUtil.getUuid();
			WjwAccchange parent = new WjwAccchange();
			parent.setUnitNo(mainUnitNo);
			parent.setUnitName(mainUnitName);
			parent.setAccNo(mainAccNo);
			parent.setAccName(mainAccName);
			parent.setDfAccno(dqfAccNo);
			parent.setDfAccname(dqfAccName);
			parent.setAmount(mainAmount.add(new BigDecimal(tranAmt)));
			parent.setTranAmt(new BigDecimal(tranAmt));
			parent.setTranTime(time);
			//账户变动详情，不管医养中心还是卫生院，都是收入
			parent.setInOrOut(1);
			parent.setMedcAmt(new BigDecimal(0.00));
			parent.setDrugAmt(new BigDecimal(0.00));
			parent.setOtherAmt(new BigDecimal(0.00));
			//**1 入账 2 清分 3 利息收入*/
			parent.setFlag(1);
			parent.setNote1(voucher);
			parent.setNote2(uuid);
			parent.setDescstr(desc);
			parent.setTradeCount(Integer.parseInt(count));
			//1利息，2不明来账
			parent.setUnkType(2);
			this.insertEntity(parent);
			logger.info("待清分入账---新增账户余额变更表记录1次");
			
			WjwAccchange child = new WjwAccchange();
			child.setUnitNo(dqfUnitNo);
			child.setUnitName(dqfUnitName);
			child.setAccNo(dqfAccNo);
			child.setAccName(dqfAccName);
			child.setDfAccno(mainAccNo);
			child.setDfAccname(mainAccName);
			child.setAmount(dqfAmount.add(new BigDecimal(tranAmt)));
			child.setTranAmt(new BigDecimal(tranAmt));
			child.setTranTime(time);
			//账户变动详情，不管医养中心还是卫生院，都是收入
			child.setInOrOut(1);
			child.setMedcAmt(new BigDecimal(0.00));
			child.setDrugAmt(new BigDecimal(0.00));
			child.setOtherAmt(new BigDecimal(0.00));
			//**1 入账 2 清分 3 利息*/
			child.setFlag(1);
			child.setNote1(voucher);
			child.setNote2(uuid);
			child.setDescstr(desc);
			child.setTradeCount(Integer.parseInt(count));
			//1利息，2不明来账
			child.setUnkType(2);
			this.insertEntity(child);
			logger.info("待清分入账---新增账户余额变更表记录2次");
			tallySuccess = 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 6;
		}
		return tallySuccess;
	}
	/**
	 * 利息支出
	 * @param voucher
	 * @param amount
	 * @param userCode
	 * @param inOrOut
	 * @param time
	 * @return int 0--失败  1--成功  2--非记账状态   3--已记账   4--记账金额不正确  5--数据库访问异常  6--系统异常
	 */
	@Transactional
	public int interestOutTally(String voucher, String tranAmt, 
			String userCode,String time) {
		
		try {
			//1、通过凭证编号查找利息支出表记录，如果有记录，判断记账状态和金额
			Map<String,Object> interest = dao.findInterestOutByVoucher(voucher);
			if(null==interest){
				logger.info("利息支出---记录不存在");
				return 0;
			}
			Integer status = (Integer) interest.get("STATUS");
			if(status==6){
				logger.info("利息支出---该笔记录已经记账");
				return 3;
			}
			if(status!=5){
				logger.info("利息支出---该笔记录是非记账状态，不能记账");
				return 2;
			}
			BigDecimal amt = new BigDecimal(tranAmt);
			BigDecimal amount = (BigDecimal) interest.get("AMOUNT");
			if(amt.compareTo(amount)!=0){
				logger.info("利息支出---交易金额与实际支出金额不一致");
				return 4;
			}
			String inOrOut = interest.get("IN_OR_OUT").toString();
			//2、更新主账户利息字段---金额减少
			Map<String,Object> main = dao.findMainAccount(inOrOut);
			String accNo = main.get("ACC_NO").toString();
			String accName = main.get("CUST_NAME").toString();
			BigDecimal mainAmt = (BigDecimal) main.get("AMOUNT");
			BigDecimal intCome = (BigDecimal) main.get("INT_COME");
			dao.updateMainAccountInterest(accNo,intCome.subtract(amount));
			logger.info("利息支出---更新主账户利息字段，利息余额减少");
			//3、新增余额变更表1条记录
			String unitNo = interest.get("UNIT_NO").toString();
			String unitName = interest.get("UNIT_NAME").toString();
			String inAccno = interest.get("IN_ACCNO").toString();
			String inAccname = interest.get("IN_ACCNAME").toString();
			
			WjwAccchange entity = new WjwAccchange();
			entity.setUnitNo(unitNo);
			entity.setUnitName(unitName);
			entity.setAccNo(accNo);
			entity.setAccName(accName);
			entity.setDfAccno(inAccno);
			entity.setDfAccname(inAccname);
			entity.setAmount(mainAmt);
			entity.setTranAmt(new BigDecimal(tranAmt));
			entity.setTranTime(time);
			entity.setInOrOut(2);
			entity.setMedcAmt(new BigDecimal(0.00));
			entity.setDrugAmt(new BigDecimal(0.00));
			entity.setOtherAmt(new BigDecimal(0.00));
			entity.setFlag(4);//**1 入账 2 清分 3 利息收入 4利息支出  5退汇未处理  6退汇记账完成*/
			entity.setNote1(voucher);
			this.insertEntity(entity);
			logger.info("利息支出---新增账户余额变更表记录");
			//4、更新利息支出表记账状态为6--已记账
			dao.updateInterestOutStatus(6,voucher);
			logger.info("利息支出---更新利息支出表记账状态为6已记账");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("利息支出---系统异常");
			return 6;
		}	
		return 1;
	}
	/**
	 * 退汇---处理
	 * @param voucher
	 * @param amount
	 * @param userCode
	 * @param inOrOut
	 * @param time
	 * @return int 0--失败  1--成功  2--非记账状态   3--已记账   4--记账金额不正确  5--数据库访问异常  6--系统异常
	 */
	@Transactional
	public int payBackTally(String voucher, String amount, String userCode,
			String time, String desc) {
		int tallySuccess = 0;
		int accCode=0;
		//1、通过凭证号查询支付明细表，找出该笔支付记录
		try {
			Map<String,Object> pay = dao.findPaydetailByVoucher(voucher);
			if(null==pay){
				logger.info("退汇----记录不存在");
				return 0;
			}
			Integer status = (Integer) pay.get("STATUS");
			if(status!=6){
				logger.info("退汇----该笔支付是非记账状态");
				return 2;
			}
			Integer backFlag = (Integer) pay.get("BACK_FLG");
			if(backFlag!=null&&backFlag==3){
				logger.info("退汇----该笔支付已经退汇");
				return 3;
			}
			//2、重新生成一笔与之关联的支付申请信息，状态为状态，该笔新的支付信息是错误的，需要重新修改收款人基本信息
			WjwPaydetail entity = this.MaptoEntity(pay);
			String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
			entity.setPayTime(date);
			entity.setBackFlg(1);//退汇标志：1--退汇未处理  2--退汇已处理   3--退汇
			entity.setBackVoucher(voucher);
			entity.setNote1(desc);
			entity.setOperNo(createCode());//生成新的凭证号                          
			entity.setStatus(5);
			dao.addPayDetail(entity);
			logger.info("退汇---新增支付明细表记录成功");
			//将原始记录退汇标志变更为3--退汇状态
			dao.updatePayBackFlag(3,voucher,desc);
			logger.info("退汇---变更原始记录，更改退汇标志成功");
			Map<String,Object> account = accountDao.findAccountByAccNo(entity.getZcAcctno());
			if(account.get("ACCOUNT_SYS_CODE")!=null){
				accCode=Integer.parseInt(account.get("ACCOUNT_SYS_CODE").toString());
			}else{
				String e="未知账号！";
				throw new RuntimeException(e);
			}
			//3、将退汇的支付款入待清分账户，同时更新待清分账户各项余额、主账户各项余额
			pay = dao.findPayByVoucher(voucher);
			this.bankBackPayTally(pay, "1", voucher, "2", date,accCode);
			tallySuccess = 1;
		} catch (Exception e) {			
			e.printStackTrace();
			return 6;
		}
		return tallySuccess;
	}

	
    /**
	 * 生成支付凭证
	 * @return
	 */
	private String createCode(){
		int num = dao.getMaxCode();
//		String jianPin = dao.getWSYJianpin(SecurityContextUtil.getCurrentUser().getOrgId());
		return Utils.createCode("","02",num);
	}
}


