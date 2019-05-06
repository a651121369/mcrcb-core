package com.untech.mcrcb.tran.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BalanceService extends AccountingBaseService{


/**
 * 缴款对账
 * @param map
 */
	@Transactional
	public void balanceIncome(Map<String, String> map) {
		String voucher = map.get("voucher");
		String type = map.get("type");
		String date = map.get("date");
//		String amount = map.get("amount");
		String user = map.get("user");
		String status = map.get("status");//核心记账状态：1--未记账，2--银行记账，3--卫计委--记账，4--冲正
		//查询缴款明细表，获取缴款记录
		Map<String,Object> income = this.dao.findIncomeByVoucher(voucher);
		if(null==income){
			logger.info("缴款对账----凭证号:"+voucher+",该笔记录不存在");
			return;
		}
		String incomeSt = income.get("STATUS").toString();
		if("1".equals(status)){
			//银行未记账
			
		}else if("2".equals(status)){
			//核心记账，卫计委没有记账
			if("5".equals(incomeSt)){
				//1、更新账户余额表
				//2、增加账户余额变更表记录
				this.tallyByType(income, type, voucher, date);
				logger.info("缴款对账----凭证号:"+voucher+",该笔缴款记账成功");
				//3、更新缴款明细表状态status为6---表示已记账
				dao.updateIncomeDetailStatus(voucher,6, date, user);
				logger.info("缴款对账----凭证号:"+voucher+",更新缴款明细表status为6（记账状态）");
			}
		}else if("3".equals(status)){
			//银行成功、卫计委成功
		}else if("4".equals(status)){
			//核心未记账、卫计委记账
			if("6".equals(incomeSt)){
				//1、更新账户余额表				
				//2、删除账户余额变更表记录
				this.rollbackTallyByType(income, type, voucher, date);
				logger.info("缴款对账----凭证号:"+voucher+",该笔缴款记录回滚成功");
				//3、更新缴款明细表中status状态为5---表示未记账
				dao.updateIncomeDetailStatus(voucher, 5, "", "");
				logger.info("缴款对账----凭证号:"+voucher+",更新缴款明细表status为5（未记账状态）");
			}
		}
		
		
	}
/**
 * 支付对账
 * @param map
 */
	@Transactional
	public void balancePay(Map<String, String> map) {
		String voucher = map.get("voucher");
		String type = map.get("type");
		String date = map.get("date");
//		String amount = map.get("amount");
		String user = map.get("user");
		//核心记账状态：1--未记账，2--银行记账，3--卫计委--记账，4--冲正
		String status = map.get("status");
		int accCode=0;
		Map<String,Object> pay = dao.findPayByVoucher(voucher);
		Map<String,Object> account =accountDao.findAccountByAccNo((String) pay.get("ZC_ACCTNO"));
		if(account.get("ACCOUNT_SYS_CODE")!=null){
			accCode=Integer.parseInt(account.get("ACCOUNT_SYS_CODE").toString());
		}else{
			String e="未知账号！";
			throw new RuntimeException(e);
		}
		if(null==pay){
			logger.info("支付对账----凭证号:"+voucher+",该笔记录不存在");
		}
		Integer backFlg = (Integer) pay.get("BACK_FLG");
		String paySt = pay.get("STATUS").toString();
		String conn = pay.get("CONN_NO").toString();
		if("1".equals(status)){
			//银行未记账
			
		}else if("2".equals(status)){
			
			//核心记账，卫计委没有记账
			if("5".equals(paySt)){
				//1、更新账户余额表
				//2、增加账户余额变更表记录
				if(backFlg!=null&&backFlg==1){//退汇未处理
					this.bankBackPayTally(pay, "2", voucher, "2", date,accCode);
					logger.info("退汇支付对账----凭证号:"+voucher+",该笔退汇支付记账成功");
					dao.updatePayDetailStatus(voucher, 6, date, user, true);
				}else{
					this.tallyByType(pay, type, voucher, date);
					logger.info("支付对账----凭证号:"+voucher+",该笔支付记账成功");
					//3、更新支付明细表状态status为6---表示已记账
					dao.updatePayDetailStatus(voucher,6, date, user,false);
				}					
				logger.info("支付对账----凭证号:"+voucher+",更新支付明细表status为6（记账状态）");
				
			}
		}else if("3".equals(status)){
			//银行成功、卫计委成功
		}else if("4".equals(status)){
			//核心未记账、卫计委记账
			if("6".equals(paySt)){
				//1、更新账户余额表				
				//2、删除账户余额变更表记录
				if(backFlg!=null&&backFlg==2){
					this.rollbackBankBackTallyByType(pay, "2", voucher, date);
					logger.info("支付退汇对账----凭证号:"+voucher+",该笔支付退汇记录回滚成功");
					dao.updatePayDetailStatus(voucher, 5, "", "",true);
				}else{
					this.rollbackTallyByType(pay, type, voucher, date);
					logger.info("支付对账----凭证号:"+voucher+",该笔支付记录回滚成功");
					//3、更新支付明细表中status状态为5---表示未记账
					dao.updatePayDetailStatus(voucher, 5, "", "",false);
				}

				logger.info("支付对账----凭证号:"+voucher+",更新支付明细表status为5（未记账状态）");
			
			}
		}
		
	}

/**
 * 拨付对账
 * @param map
 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void balanceAppropriate(Map<String, String> map) {
		String voucher = map.get("voucher");
		String type = map.get("type");
		String date = map.get("date");
//		String amount = map.get("amount");
		String user = map.get("user");
		String status = map.get("status");//核心记账状态：1--未记账，2--银行记账，3--卫计委--记账，4--冲正
		Map<String,Object> appropriate = dao.findAppropriateByVoucher(voucher);
		if(null==appropriate){
			logger.info("拨付对账----凭证号:"+voucher+",该笔记录不存在");
		}
		String appSt = appropriate.get("STATUS").toString();
		String conn = appropriate.get("CONN_NO").toString();
		
		if("1".equals(status)){
			//银行未记账
			
		}else if("2".equals(status)){
			//核心记账，卫计委没有记账
			if("5".equals(appSt)){
				//1、更新账户余额表
				//2、增加账户余额变更表记录
				this.tallyByType(appropriate, type, voucher, date);

				//3、根据拨付主表关联号查找拨付明细表记录
				//4、对拨付明细表中的每条记录进行第1、第2步骤操作
				List<Map<String,Object>> list = dao.findAppropriateDetailByConn(conn);
				for(Map<String,Object> app:list){
					this.tallyByType(app, type, voucher, date);
				}
				logger.info("拨付对账----凭证号:"+voucher+",该笔拨付记账成功");
				//5、更新拨付主表状态status为6---表示已记账
				dao.updateAppropriateStatus(voucher,6, date, user);
				logger.info("拨付对账----凭证号:"+voucher+",更新拨付主表status为6（记账状态）");
			}
		}else if("3".equals(status)){
			//银行成功、卫计委成功
		}else if("4".equals(status)){
			//核心未记账、卫计委记账
			if("6".equals(appSt)){
				//1、更新账户余额表				
				//2、删除账户余额变更表记录
				this.rollbackTallyByType(appropriate, type, voucher, date);				
				List<Map<String,Object>> list = dao.findAppropriateDetailByConn(conn);
				for(Map<String,Object> app:list){
					this.rollbackTallyByType(app, type, voucher, date);
				}
		
				logger.info("拨付对账----凭证号:"+voucher+",该笔拨付记录回滚成功");
				//3、更新拨付主表中status状态为5---表示未记账
				dao.updateAppropriateStatus(voucher, 5, "", "");
				logger.info("拨付对账----凭证号:"+voucher+",更新拨付主表status为5（未记账状态）");
			}
		}
		
	}
	
	
}
