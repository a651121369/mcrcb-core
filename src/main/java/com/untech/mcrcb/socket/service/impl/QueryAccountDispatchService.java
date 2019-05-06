package com.untech.mcrcb.socket.service.impl;

import java.util.List;
import java.util.Map;

import com.untech.mcrcb.socket.service.AbstractDispatchService;
import com.untech.mcrcb.tran.service.QueryAccountService;
import com.unteck.tpc.framework.core.spring.SpringApplicationContextHolder;

public class QueryAccountDispatchService extends AbstractDispatchService{
	
	private String wjwCode;//卫计委编号
	private String tallyType;//收入支出标志
	private String accountNo;  //虚拟主账户号
	
	private QueryAccountService service = SpringApplicationContextHolder.getBean(QueryAccountService.class);

	public QueryAccountDispatchService(String type, String[] request) throws Exception {
		super(type, request);
		this.wjwCode = request[0];
		this.tallyType = request[2];
		if(request.length==3){
			this.accountNo = null;
		}else if(request.length==4){
			this.accountNo = request[3];
		}
	}

	@Override
	public void handler() throws Exception {
		try {
			if(accountNo==null||"".equals(accountNo)){
				List<Map<String,Object>> list = service.QueryAccount(tallyType);
				this.response = new String[3+list.size()*7];
				if(list.size()==0){
					responseCode = "9999";
					responseMessage = "没有找到相关记录";
				}else{
					this.listToResponse(list);
					responseCode = "0000";
					responseMessage = "交易成功";
				}
				this.response[0] = wjwCode;
				this.response[1] = responseCode;
				this.response[2] = responseMessage;
			}else{
				Map<String,Object> map = service.QueryAccount(tallyType,accountNo);
				this.response = new String[10];
				if(null==map){
					responseCode = "9999";
					responseMessage = "没有找到相关记录";
				}else{
					this.response[3] = map.get("UNIT_NAME").toString();
					this.response[4] = map.get("ACC_NO").toString();
					this.response[5] = map.get("CUST_NAME").toString();
					this.response[6] = map.get("AMOUNT").toString();
					this.response[7] = map.get("DRUG_AMT").toString();
					this.response[8] = map.get("MEDICAL_AMT").toString();
					this.response[9] = map.get("OTHER_AMT").toString();
					responseCode = "0000";
					responseMessage = "交易成功";
				}
				this.response[0] = wjwCode;
				this.response[1] = responseCode;
				this.response[2] = responseMessage;
			}

		} catch (Exception e) {
			e.printStackTrace();
			responseCode = "9999";
			responseMessage = "系统异常";
			this.response[0] = wjwCode;
			this.response[1] = responseCode;
			this.response[2] = responseMessage;
		}
	}

	private void listToResponse(List<Map<String, Object>> list) {
		int len = list.size()*7;
		String[] res = new String[len];
		for(int i=0;i<list.size();i++){
			Map<String,Object> map = list.get(i);
			System.out.println(map);
			String[] child = new String[7];
			child[0] = map.get("UNIT_NAME").toString();
			child[1] = map.get("ACC_NO").toString();
			child[2] = map.get("CUST_NAME").toString();
			child[3] = map.get("AMOUNT").toString();
			child[4] = map.get("DRUG_AMT").toString();
			child[5] = map.get("MEDICAL_AMT").toString();
			child[6] = map.get("OTHER_AMT").toString();
			System.arraycopy(child, 0, res, i*child.length, child.length);
		}
		System.arraycopy(res, 0, this.response, 3, res.length);
	}
	
//	public static void main(String[] args){
//		int[] a= new int[10];
//		a[0] = 0;
//		int[] b = new int[2];
//		b[0] = 1;
//		b[1] = 2;
//		System.arraycopy(b, 0, a, 1, b.length);
//		for(int i=0;i<a.length;i++){
//			System.out.println(a[i]);
//		}
//	}

}
