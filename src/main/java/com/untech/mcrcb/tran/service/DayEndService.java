package com.untech.mcrcb.tran.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.untech.mcrcb.web.dao.AccountDayEndDao;
import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.enhance.BaseService;

@Service
public class DayEndService extends BaseService<T,Long>{
	@Autowired
	private AccountDayEndDao dao;
	@Override
	public BaseDao<T, Long> getHibernateBaseDao() {
		
		return this.getHibernateBaseDao();
	}
	public Map<String, Object> dayEnd(String inAccno, String inAccname,String inAmount,
			String outAccno, String outAccname,String outAmount,String date) {
		try {
			//根据日期查询日结记录，如果已经日结，则不再作日结处理

			Map<String,Object> map = dao.dayEnd(inAccno,outAccno);
			if(null==map){
				map = new HashMap<String, Object>();
				map.put("result", "2");
				map.put("dayEndMsg", "没有找到相关记录");
			}else{
				map.put("result", "1");
				BigDecimal inZsAmt = BigDecimal.valueOf(Double.parseDouble(inAmount));//真实收入账户余额
				BigDecimal outZsAmt = BigDecimal.valueOf(Double.parseDouble(outAmount));//真实支出账户余额
				String inXnAccno = map.get("IN_ACCNO").toString();//虚拟收入账户号
				String inXnAccname = map.get("IN_ACCNAME").toString();//虚拟收入账户名称
				BigDecimal inXnAmt = (BigDecimal) map.get("IN_AMOUNT");//虚拟收入账户余额
				String outXnAccno = map.get("OUT_ACCNO").toString();//虚拟支出账户号
				String outXnAccname = map.get("OUT_ACCNAME").toString();//虚拟支出账户名称
				BigDecimal outXnAmt = (BigDecimal) map.get("OUT_AMOUNT");//虚拟支出账户余额
				//收入真实帐户与虚拟账户核对
				if(inZsAmt.compareTo(inXnAmt)==0){
					map.put("dayEndMsg1", "收入户银行余额与虚拟收入户余额相等");
				}else if(inZsAmt.compareTo(inXnAmt)>0){
					map.put("dayEndMsg1", "收入户银行余额大于虚拟收入户余额,差额："+inZsAmt.subtract(inXnAmt));
				}else{
					map.put("dayEndMsg1", "收入户银行余额小于虚拟收入户余额,差额："+inXnAmt.subtract(inZsAmt));
				}
				//支出真实帐户与虚拟账户核对
				if(outZsAmt.compareTo(outXnAmt)==0){
					map.put("dayEndMsg2", "支出户银行余额与虚拟支出户余额相等");
				}else if(outZsAmt.compareTo(outXnAmt)>0){
					map.put("dayEndMsg2", "支出户银行余额大于虚拟支出户余额,差额："+outZsAmt.subtract(outXnAmt));
				}else{
					map.put("dayEndMsg2", "支出户银行余额小于虚拟支出户余额,差额："+outXnAmt.subtract(outZsAmt));
				}
//				//获取虚拟收入、支出的主账户号
//				String inXnAccno = map.get("IN_ACCNO").toString();
//				String outXnAccno = map.get("OUT_ACCNO").toString();
				Map<String,Object> parent = dao.queryParentAmount(inXnAccno,outXnAccno);
				if(parent==null){
					logger.info("查找主帐户余额失败！");
					map.put("dayEndMsg3","查找主帐户余额失败");
					return map;
				}
				Map<String,Object> child = dao.queryChildAmountAll(inXnAccno,outXnAccno);
				if(child==null){
					logger.info("查找子帐户汇总余额失败！");
					map.put("dayEndMsg3", "查找子帐户汇总余额失败");
					return map;
				}
				
				BigDecimal parentInAmt = (BigDecimal) parent.get("PARENT_IN_AMT");
				BigDecimal parentOutAmt = (BigDecimal) parent.get("PARENT_OUT_AMT");
				BigDecimal childInAmt = (BigDecimal) child.get("CHILD_IN_AMT");
				BigDecimal childOutAmt = (BigDecimal) child.get("CHILD_OUT_AMT");
				
				if(parentInAmt.compareTo(childInAmt)==0){
					map.put("dayEndMsg3", "虚拟收入总账户余额与子账户余额相等");
				}else if(parentInAmt.compareTo(childInAmt)>0){
					map.put("dayEndMsg3", "虚拟收入总账户余额大于子账户余额,差额："+parentInAmt.subtract(childInAmt));
				}else{
					map.put("dayEndMsg3", "虚拟收入总账户余额小于子账户余额,差额："+childInAmt.subtract(parentInAmt));
				}
				
				if(parentOutAmt.compareTo(childOutAmt)==0){
					map.put("dayEndMsg4", "虚拟支出总账户余额与子账户余额相等");
				}else if(parentOutAmt.compareTo(childOutAmt)>0){
					map.put("dayEndMsg4", "虚拟支出总账户余额大于子账户余额,差额："+parentOutAmt.subtract(childOutAmt));
				}else{
					map.put("dayEndMsg4", "虚拟支出总账户余额小于子账户余额,差额："+childOutAmt.subtract(parentOutAmt));
				}
				
				Long count = dao.checkDayEnd(date);
				if(count>0){
					dao.updateDayEnd(outAccno, outAccname, outZsAmt, inAccno, inAccname, inZsAmt,
							outXnAccno, outXnAccname, outXnAmt, inXnAccno, inXnAccname, inXnAmt, "1", date);
				}else{
					dao.addAccDayEnd(outAccno, outAccname, outZsAmt, inAccno, inAccname, inZsAmt,
							outXnAccno, outXnAccname, outXnAmt, inXnAccno, inXnAccname, inXnAmt, "1", date);
				}
				


			
			}
			return map;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}

	public static void main(String[] args){
		BigDecimal a = new BigDecimal(1);
		BigDecimal b = new BigDecimal(2);
		BigDecimal c = new BigDecimal(3);
		BigDecimal d = new BigDecimal(2);
		System.out.println(b.compareTo(a));
		System.out.println(b.compareTo(c));
		System.out.println(b.compareTo(d));
		
	}
}
