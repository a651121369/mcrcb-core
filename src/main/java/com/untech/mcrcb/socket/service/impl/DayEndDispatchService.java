package com.untech.mcrcb.socket.service.impl;

import java.util.Map;

import com.untech.mcrcb.socket.service.AbstractDispatchService;
import com.untech.mcrcb.tran.service.DayEndService;
import com.unteck.tpc.framework.core.spring.SpringApplicationContextHolder;

public class DayEndDispatchService extends AbstractDispatchService{
	private String wjwCode;//卫计委编号
	private String inAccno;//真实收入账号
	private String inAccname;//真实收入账户名
	private String inAmount;//真实收入余额
	private String outAccno;//真实支出账号
	private String outAccname;//真实支出账户名
	private String outAmount;//真实支出余额
	private String date;
	
	private DayEndService service = SpringApplicationContextHolder.getBean(DayEndService.class);
	public DayEndDispatchService(String type, String[] request)
			throws Exception {
		super(type, request);
		this.wjwCode = request[0];
		this.inAccno = request[2];
		this.inAccname = request[3];
		this.inAmount = request[4];
		this.outAccno = request[5];
		this.outAccname = request[6];
		this.outAmount = request[7];
		this.date = request[8];
	}

	@Override
	public void handler() throws Exception {
		try {
			this.response = new String[11];
			Map<String,Object> map = service.dayEnd(inAccno,inAccname,inAmount,
					outAccno,outAccname,outAmount,date);
			if(map==null){
				responseCode = "9999";
				responseMessage = "交易失败,系统异常";
			}else{
				String result = map.get("result").toString();
				if("0".endsWith(result)){
					responseCode = "0001";
					responseMessage = "交易成功,"+map.get("dayEndMsg");
				}else if("1".endsWith(result)){
					responseCode = "0000";
					responseMessage = "交易成功";
					this.response[3] = map.get("dayEndMsg1").toString();
					this.response[4] = map.get("dayEndMsg2").toString();
					this.response[5] = map.get("dayEndMsg3").toString();
					this.response[6] = map.get("dayEndMsg4").toString();
					this.response[7] = inAmount;
					this.response[8] = map.get("IN_AMOUNT").toString();
					this.response[9] =outAmount;
					this.response[10] = map.get("OUT_AMOUNT").toString();
				}else if("2".endsWith(result)){
					responseCode = "9999";
					responseMessage = "交易失败,"+map.get("dayEndMsg");
				}
		
					this.response[0] = wjwCode;
					this.response[1] = responseCode;
					this.response[2] = responseMessage;
			}
			
	
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
