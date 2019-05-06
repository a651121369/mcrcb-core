package com.untech.mcrcb.socket.service.impl;

import com.untech.mcrcb.socket.service.AbstractDispatchService;
import com.untech.mcrcb.tran.service.TallyReversedService;
import com.unteck.tpc.framework.core.spring.SpringApplicationContextHolder;


public class TallyReversedDispatchService extends AbstractDispatchService{
	
	private String wjwCode;
	private String tallyType;//记账类型 1--缴款  2---支付  3----拨付   4----利息  5---不明来账   6---退汇
	private String voucher;
	private String amount;
	private String inOrOut;//收入支出标志
	private String user;
	private String unit;
	private String date;
	
	private TallyReversedService service = SpringApplicationContextHolder.getBean(TallyReversedService.class);
	public TallyReversedDispatchService(String type, String[] request) throws Exception {
		super(type, request);
		this.wjwCode = request[0];
		this.tallyType = request[2];
		this.voucher = request[3];
		this.amount = request[4];
		this.inOrOut = request[5];
		this.user = request[6];
		this.unit = request[7];
		this.date = request[8];
		}
		
	

	@Override
	public void handler() throws Exception {
		this.response = new String[3];
		int retFlag = 0;
			try {
				//缴款冲正
				if("1".equals(tallyType)){
					retFlag = service.incomeTallyReversed(voucher,amount,user,date);
				}
				//支付冲正
				else if("2".equals(tallyType)){
					retFlag =service.payTallyReversed(voucher,amount,user,date);
				}
				//拨付冲正
				else if("3".equals(tallyType)){
					retFlag =service.appropriateTallyReversed(voucher,amount,user,date);
				}
				//利息支出冲正
//				else if("4".equals(tallyType)){			
//					retFlag = service.interestOutTallyReversed(voucher,amount,user,date);
//				}
				//利息收入冲正
				else if("5".equals(tallyType)){
					retFlag = service.interestInTallyReversed(voucher,amount,user,inOrOut,date);
				}
				//不明来账冲正
				else if("6".equals(tallyType)){
					retFlag = service.unknownIncomeTallyReversed(voucher,amount,user,inOrOut,date);
				}
				//退汇冲正
				else if("7".equals(tallyType)){
					retFlag = service.payBackTallyReversed(voucher,amount,user,date);
				}
				if(retFlag==0){
					responseCode = "9999";
					responseMessage = "记录不存在";
				}else if(retFlag==1){
					responseCode = "0000";
					responseMessage = "冲正成功";
				}else if(retFlag==2){
					responseCode = "9999";
					responseMessage = "非记账完成状态，不可冲正";
				}else if(retFlag==3){
					responseCode = "9999";
					responseMessage = "冲正失败";
				}
			} catch (Exception e) {
				responseCode = "9999";
				responseMessage = "交易失败，系统异常";
				this.response[0] = this.wjwCode;
				this.response[1] = this.responseCode;
				this.response[2] = this.responseMessage;
				e.printStackTrace();
			}
		
			this.response[0] = this.wjwCode;
			this.response[1] = this.responseCode;
			this.response[2] = this.responseMessage;
	}

}
