package com.untech.mcrcb.socket.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.untech.mcrcb.socket.service.AbstractDispatchService;
import com.untech.mcrcb.tran.service.QueryVoucherService;
import com.untech.mcrcb.web.util.Utils;
import com.unteck.tpc.framework.core.spring.SpringApplicationContextHolder;


public class VoucherDispatchService extends AbstractDispatchService{
	private String wjwCode;//卫计委编号
	private String tallyType;//记账类型 1--缴款  2---支付  3----拨付
	private String voucher;  //凭证号
	
	private QueryVoucherService service = SpringApplicationContextHolder.getBean(QueryVoucherService.class);
	public VoucherDispatchService(String type, String[] request)
			throws Exception {
		super(type, request);
		this.wjwCode = request[0];
		this.tallyType = request[2];
		this.voucher = request[3];
	}

	@Override
	public void handler() throws Exception {
		try {
			this.response = new String[15];
			//缴款
			if("1".equals(this.tallyType)){
				Map<String,Object> map = service.queryIncomeByVoucher(voucher);
				
				if(map==null){
					responseCode = "9999";
					responseMessage = "该笔记录不存在";
				}else{
					String appDate = map.get("TIME").toString();
					String today = Utils.getTodayString("yyyyMMdd");
					Long number = Utils.dateSubstract(today, appDate);	
					if(number<5){
						this.mapToResponse(map);
						responseCode = "0000";
						responseMessage = "交易成功";
					}else{
						responseCode = "9999";
						responseMessage = "该笔缴款已过期";
					}
					
				}
			
							
			}
			//支付
			else if("2".equals(this.tallyType)){
				Map<String,Object> map = service.queryPayByVoucher(voucher);
				
				if(map==null){
					responseCode = "9999";
					responseMessage = "该笔记录不存在";
				}else{
					String printtime = map.get("PRINT_TIME")==null?"":map.get("PRINT_TIME").toString();
					int t = daysBetween(printtime);
					System.out.println("时间相差。。。====="+t);
					if(t>10){
						responseCode = "9999";
						responseMessage = "凭证已过期";							
					}else{
						this.payMapToResponse(map);
						responseCode = "0000";
						responseMessage = "交易成功";		
					}
				}
			}
			//拨付
			else if("3".equals(this.tallyType)){
				Map<String,Object> map = service.queryAppropriateByVoucher(voucher);
				if(map==null){
					responseCode = "9999";
					responseMessage = "该笔记录不存在";
				}else{
					this.mapToResponse(map);
					responseCode = "0000";
					responseMessage = "交易成功";
				}

			}
			//利息支出
			else if("4".equals(this.tallyType)){
//				Map<String,Object> map = service.queryInterestOutByVoucher(voucher);
//				if(map==null){
//					responseCode = "9999";
//					responseMessage = "该笔记录不存在";
//				}else{
//					this.mapToResponse(map);
//					responseCode = "0000";
//					responseMessage = "交易成功";
//				}
			}
			//利息收入
			else if("5".equals(this.tallyType)){
				Map<String,Object> map = service.queryInterestByVoucher(voucher);
				if(map==null){
					responseCode = "9999";
					responseMessage = "该笔记录不存在";
				}else{
					this.mapToResponse(map);
					responseCode = "0000";
					responseMessage = "交易成功";
				}
			}
			//待清分
			else if("6".equals(this.tallyType)){
				Map<String,Object> map = service.queryUnknownIncomeByVoucher(voucher);
				if(map==null){
					responseCode = "9999";
					responseMessage = "该笔记录不存在";
				}else{
					this.mapToResponse(map);
					responseCode = "0000";
					responseMessage = "交易成功";
				}
			}
			//退汇
			else if("7".equals(this.tallyType)){
				Map<String,Object> map = service.queryPayByVoucher(voucher);
				if(map==null){
					responseCode = "9999";
					responseMessage = "该笔记录不存在";
				}else{
					this.payMapToResponse(map);
					responseCode = "0000";
					responseMessage = "交易成功";
				}
			}
			this.response[0] = wjwCode;
			this.response[1] = responseCode;
			this.response[2] = responseMessage;
			this.response[3] = voucher;
		} catch (Exception e) {
			e.printStackTrace();
			responseCode = "9999";
			responseMessage = "系统异常";
			this.response[0] = wjwCode;
			this.response[1] = responseCode;
			this.response[2] = responseMessage;
			this.response[3] = voucher;
		}
	}
	
	private void mapToResponse(Map<String,Object> map){
		this.response[4] = map.get("OUT_ACCNO").toString();
		this.response[5] = map.get("OUT_ACCNAME").toString();
		this.response[6] = map.get("OUT_BANK").toString();
		this.response[7] = map.get("IN_ACCNO").toString();
		this.response[8] = map.get("IN_NAME").toString();
		this.response[9] = map.get("IN_BANK").toString();
		this.response[10] = map.get("UNIT_NAME").toString();
		this.response[11] = map.get("AMOUNT").toString();
		this.response[12] = map.get("STATUS").toString();
		this.response[13] = "";
		this.response[14] = "";
	}
	
	private void payMapToResponse(Map<String,Object> map){
		this.response[4] = map.get("OUT_ACCNO").toString();
		this.response[5] = map.get("OUT_ACCNAME").toString();
		this.response[6] = map.get("OUT_BANK").toString();
		this.response[7] = map.get("IN_ACCNO").toString();
		this.response[8] = map.get("IN_NAME").toString();
		this.response[9] = map.get("IN_BANK").toString();
		this.response[10] = map.get("UNIT_NAME").toString();
		this.response[11] = map.get("AMOUNT").toString();
		this.response[12] = map.get("STATUS").toString();
		this.response[13] = map.get("BACK_FLG")==null?"":map.get("BACK_FLG").toString();
		this.response[14] = map.get("YT")==null?"":map.get("YT").toString();
	}

    public int daysBetween(String smdate){
    	int t = 0;
    	if(!smdate.equals("")){
	    	try {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
				Calendar cal = Calendar.getInstance();  
				cal.setTime(sdf.parse(smdate));  
				long time1 = cal.getTimeInMillis();               
				cal.setTime(new Date());  
				long time2 = cal.getTimeInMillis();       
				long between_days=(time2-time1)/(1000*3600*24);
				t = Integer.parseInt(String.valueOf(between_days));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	}
        return t; 
    }	
}
