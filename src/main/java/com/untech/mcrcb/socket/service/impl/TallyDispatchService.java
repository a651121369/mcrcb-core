package com.untech.mcrcb.socket.service.impl;
import com.untech.mcrcb.socket.service.AbstractDispatchService;
import com.untech.mcrcb.tran.service.TallyService;
import com.unteck.tpc.framework.core.spring.SpringApplicationContextHolder;

public class TallyDispatchService extends AbstractDispatchService{
	/**
	 * 卫计委编号
	 */
	private String wjwCode;
	/**
	 * 记账类型 1--缴款  2---支付  3----拨付   4----利息支出  5---利息收入 6---不明来账  7---退汇
	 */
	private String tallyType;
	/**
	 * 凭证号
	 */
	private String voucher;
	/**
	 * 交易金额
	 */
	private String amount;
	/**
	 * 收入账户、支出账户标志  1--收入主账户  2--支出主账户（用于利息入账，待清分入账进收入或者支出账户的标识）
	 */
	private String inOrOut;
	/**
	 * 操作柜员号
	 */
	private String userCode;
	/**
	 * 记录待清分入账笔数
	 */
	private String count;
	/**
	 * 操作机构号
	 */
	private String unitNo;
	//操作时间
	private String time;
	//摘要或者退汇原因
	private String desc;
	private TallyService service = SpringApplicationContextHolder.getBean(TallyService.class);
	public TallyDispatchService(String type, String[] request) throws Exception {
		super(type, request);
		this.wjwCode = request[0];
		this.tallyType = request[2];
		this.inOrOut = request[3];
		this.voucher = request[4];
		this.amount = request[5];
		this.count = request[6];
		this.userCode= request[7];
		this.time = request[9];			
		this.desc = request[10];
	}

	@Override
	public void handler() throws Exception {
		try {
			
			this.response = new String[3];
			int backFlag = 0;
			//缴款
			if(this.tallyType.equals("1")){
				backFlag = service.incomeTally(voucher,amount,userCode,time); 

			}
			//支付
			else if(this.tallyType.equals("2")){
				backFlag = service.payTally(voucher,amount,userCode,time);

			}
			//拨付
			else if(this.tallyType.equals("3")){
				backFlag = service.appropriateTally(voucher,amount,userCode,time);
			}
			//利息支出
			else if(this.tallyType.equals("4")){
				
//				backFlag = service.interestOutTally(voucher,amount,userCode,time);
			}
			//利息收入---和不明来账一样
			else if(this.tallyType.equals("5")){
				backFlag = service.interestInTally(voucher,amount,userCode,inOrOut,time);
			}

			//待清分入账（不明来账）----财政拨款
			else if(this.tallyType.equals("6")){
//				byte[] utf8Bytes = desc.getBytes("GBK");
//				String utf8Str = new String(utf8Bytes, "UTF-8");
				backFlag = service.unknownIncomeTally(voucher,amount,userCode,inOrOut,count,time,desc);

			}
			//退汇记账----针对他行支付交易产生退汇的情况
			else if(this.tallyType.equals("7")){
				backFlag = service.payBackTally(voucher,amount,userCode,time,desc);

			}		
			if(backFlag==0){
				responseCode = "9999";
				responseMessage = "该笔记录或主账户不存在";
			}
			else if(backFlag==1){
				responseCode = "0000";
				responseMessage = "交易成功";
			}
			else if(backFlag==2){
				responseCode = "9999";
				responseMessage = "该笔记录是非记账状态";
			}
			else if(backFlag==3){
				responseCode = "9999";
				responseMessage = "该笔记录已经记账，不可重复操作";
			}
			else if(backFlag==4){
				responseCode = "9999";
				responseMessage = "交易金额与实际支付金额不一致";
			}
			else if(backFlag==5){
				responseCode = "9999";
				responseMessage = "数据库访问异常";
			}
			else if(backFlag==6){
				responseCode = "9999";
				responseMessage = "资金管理系统异常";
			}
			this.response[0] = wjwCode;
			this.response[1] = responseCode;
			this.response[2] = responseMessage;

		} catch (Exception e) {
			e.printStackTrace();
			responseCode = "9999";
			responseMessage = "交易失败，系统异常";
			this.response[0] = wjwCode;
			this.response[1] = responseCode;
			this.response[2] = responseMessage;
		}
		
	}
}
