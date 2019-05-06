package com.untech.mcrcb.web.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.untech.mcrcb.web.dao.AccountDayEndDao;
import com.unteck.common.dao.support.Pagination;

@Service
public class AccountDayEndService {
	
	@Autowired
	private AccountDayEndDao dao;
	
	public Pagination<Map<String, Object>> getAccDayEndInfo(Integer start, Integer limit,String PAY_ACC_TRUE
			,String PAY_ACCNAME_TRUE,String GET_ACC_TRUE,String GET_ACCNAME_TRUE){
    	return dao.getAccDayEndInfo(start, limit, PAY_ACC_TRUE,PAY_ACCNAME_TRUE,GET_ACC_TRUE,GET_ACCNAME_TRUE);
    }
	
	public Map<String,Object> getAccountInfo(){
		List<Map<String,Object>> list = dao.getAccountInfo();
		Map<String,Object> map = new HashMap<String,Object>();
		if(list != null && list.size() > 0){
			map = list.get(0);
		}
        return map;
    }
	
	/**
	 * 
	 * @param PAY_ACC_TRUE    		收入主账户
	 * @param PAY_ACCNAME_TRUE		收入主账户名
	 * @param PAY_ACCMONEY_TRUE		收入主账户余额
	 * @param GET_ACC_TRUE			支出主账户
	 * @param GET_ACCNAME_TRUE		支出主账户名
	 * @param GET_ACCMONEY_TRUE		支出主账户余额
	 * @return
	 */
	public String addAccDayEndInfo(String PAY_ACC_TRUE,String PAY_ACCNAME_TRUE,String PAY_ACCMONEY_TRUE,
			String GET_ACC_TRUE,String GET_ACCNAME_TRUE,String GET_ACCMONEY_TRUE){
		System.out.println("进入日结业务处理");
		SimpleDateFormat sim = new SimpleDateFormat("yyyyMMdd");
        String inDay = sim.format(new Date());
		//初始化参数
		String flag = "1";
		String xPayAcc = "";//虚拟支出主账户
		String xPayAccName = "";//虚拟支出主账户名
		BigDecimal xPayAmount = new BigDecimal(0.00);//虚拟支出主账户余额
		String xGetAcc = "";//虚拟收入主账户
		String xGetAccName = "";//虚拟收入主账户名
		BigDecimal xGetAmont = new BigDecimal(0.00);//虚拟收入主账户余额
		//根据支出主账户查找虚拟支出主账户,判断支出主账户与虚拟支出主账户余额
		List<Map<String,Object>> list = dao.getParAccSql(PAY_ACC_TRUE);
		if(list != null && list.size() > 0){
			xPayAcc = list.get(0).get("ACC_NO").toString();
			xPayAccName = list.get(0).get("CUST_NAME").toString();
			//根据虚拟支出主账户获取账户余额
			List<Map<String,Object>> xPayList = dao.getAccMoney(xPayAcc);
			if(xPayList != null && xPayList.size() > 0){
				xPayAmount = (BigDecimal) xPayList.get(0).get("AMOUNT");
				if(new BigDecimal(PAY_ACCMONEY_TRUE).compareTo(xPayAmount) != 0){
					flag = "2";//"支出主账户余额与虚拟支出主账户余额不符！";
				}
				//根据虚拟主账户获取各级子账户余额
				List<Map<String,Object>> xChildPayList = dao.getChildAccMoney(xPayAcc);
				if(xChildPayList != null && xChildPayList.size() > 0){
					BigDecimal xChildPayMoney = new BigDecimal(0.00);
					for(int i = 0;i < xChildPayList.size();i++){
						xChildPayMoney = xChildPayMoney.add((BigDecimal)xChildPayList.get(i).get("AMOUNT"));
					}
					if(xPayAmount.compareTo(xChildPayMoney) != 0){
						flag = "3";//"虚拟支出主账户余额与虚拟支出子账户账户余额不符！";
					}
				}
			}
			//根据收入主账户查找虚拟收入主账户,判断收入主账户与虚拟收入主账户余额
			List<Map<String,Object>> list1 = dao.getParAccSql(GET_ACC_TRUE);
			System.out.println("list1.size() = " + list1.size());
			if(list1 != null && list1.size() > 0){
				xGetAcc = list1.get(0).get("ACC_NO").toString();
				xGetAccName = list1.get(0).get("CUST_NAME").toString();
				//根据虚拟收入主账户获取账户余额
				List<Map<String,Object>> xGetList = dao.getAccMoney(xGetAcc);
				if(xGetList != null && xGetList.size() > 0){
					xGetAmont = (BigDecimal) xGetList.get(0).get("AMOUNT");
					System.out.println("GET_ACCMONEY_TRUE = " + GET_ACCMONEY_TRUE);
					if(xGetAmont.compareTo(new BigDecimal(GET_ACCMONEY_TRUE)) != 0){
						flag = "4";//"收入主账户余额与虚拟支出主账户余额不符";
					}
				}
				
				//根据虚拟收入主账户获取各级子账户余额
				List<Map<String,Object>> xChildGetList = dao.getChildAccMoney(xGetAcc);
				if(xChildGetList != null && xChildGetList.size() > 0){
					BigDecimal xChildGetMoney = new BigDecimal(0.00);
					for(int i = 0;i < xChildGetList.size();i++){
						xChildGetMoney = xChildGetMoney.add((BigDecimal)xChildGetList.get(i).get("AMOUNT"));
					}
					if(xGetAmont.compareTo(xChildGetMoney) != 0){
						flag = "5";//"虚拟收入主账户余额与虚拟收入子账户账户余额不符！";
					}
				}
				//向账户日结表WJW_ACCOUNT_DAYEND中新增一条数据
				dao.addAccDayEnd(PAY_ACC_TRUE, PAY_ACCNAME_TRUE, new BigDecimal(PAY_ACCMONEY_TRUE), 
						GET_ACC_TRUE, GET_ACCNAME_TRUE, new BigDecimal(GET_ACCMONEY_TRUE), 
						xPayAcc, xPayAccName, xPayAmount, 
						xGetAcc, xGetAccName, xGetAmont,flag,inDay);
				System.out.println("日结结束");
			}else{
				flag = "0";
			}
		}else{
			flag = "0";
		}
		return flag;
	}

}
