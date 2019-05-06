package com.untech.mcrcb.web.controller;

import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;









import javax.servlet.http.HttpServletRequest;

import com.untech.mcrcb.web.common.Constants;
import com.untech.mcrcb.web.service.PublicService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.untech.mcrcb.web.service.WjwAccountService;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.ResponseData;
import com.unteck.tpc.framework.core.util.SecurityContextUtil;
import com.unteck.tpc.framework.web.controller.BaseController;
import com.unteck.tpc.framework.web.model.admin.User;

/**
 * WJW_ACCOUNT Controller
 * @author            chenyong
 * @since             2015-11-04
 */
@Controller
@RequestMapping(value="/WjwAccount")
public class WjwAccountController extends BaseController {
    @Autowired
    private WjwAccountService service;

    @Autowired
    private PublicService publicService;



    @RequestMapping(value = "/index")
    public String index(){
        return "/mcrcb-core/WjwAccount";
    }
    @RequestMapping(value="/dqfzw")
    public String dqfzw(){
//    	return "/mcrcb-core/WjwAccountDqf";
    	return "/mcrcb-core/WjwAccountDqf2";
    }
    
    @RequestMapping(value="/lxrz")
    public String lxrz(){
    	return "/mcrcb-core/WjwAccountLx";
    }

    @RequestMapping(value="/dqfAccountRz")    					   
    @ResponseBody
    public ResponseData dqfAccountRz(String accNo,String rzAmt,String unkType,String date){
    	if(!service.dqfAccountRz(accNo,rzAmt,unkType,date)){
    		return new ResponseData(false,"操作失败！");
    	}    	
    	return new ResponseData(true,"操作成功！");
    }
    
    @RequestMapping(value="/findDqfAccounts")
    @ResponseBody
    public Pagination<Map<String,Object>> findDqfAccount(Integer start,Integer limit){
    	return service.findDqfAccounts(start,limit);
    }
    
    @RequestMapping(value="/findMainAccounts")
    @ResponseBody
    public Pagination<Map<String,Object>> findMainAccounts(Integer start,Integer limit){
    	return service.findMainAccounts(start,limit);
    }
    @RequestMapping(value="/dqfDetail")
    @ResponseBody
    public List<Map<String,Object>> findDqfChildren(String accNo,String parent){
    	return service.findDqfChildren(accNo, parent);
    }

    @RequestMapping(value="/dqfAccountQf")
    @ResponseBody
    public ResponseData dqfAccountQf(HttpServletRequest request){
    	Map<String,String> accNoParams = new HashMap<String,String>();
    	Map<String,String> amountParams = new HashMap<String,String>();
    	Map<String,String> item1AmtParams = new HashMap<String,String>();
    	Map<String,String> item2AmtParams = new HashMap<String,String>();
    	String id = request.getParameter("id");
    	String amount = request.getParameter("amount");
    	String accNo = request.getParameter("accNo");
    	String note2 = request.getParameter("note2");
    	String itemId1 = request.getParameter("itemId1");
    	String itemId2 = request.getParameter("itemId2");
    	String unkType = request.getParameter("unkType");

    	Enumeration<String> enumeration = request.getParameterNames();
    	int i = 0;
        while (enumeration.hasMoreElements()){
           enumeration.nextElement();
//	   		if(null!=request.getParameter("amount"+i)&&!"".equals(request.getParameter("amount"+i))){
            //总金额
            if(StringUtils.isNotBlank(request.getParameter("amount"+i))){
                accNoParams.put("accNo"+i,request.getParameter("accNo"+i));
				amountParams.put("amount"+i, request.getParameter("amount"+i));
//				if(null==request.getParameter("item1Amt"+i)||"".equals(request.getParameter("item1Amt"+i))){
                //医疗金额
                if(StringUtils.isBlank(request.getParameter("item1Amt"+i))){
					item1AmtParams.put("item1Amt"+i, "0");
				}else{
					item1AmtParams.put("item1Amt"+i, request.getParameter("item1Amt"+i));
				}
				
//				if(null==request.getParameter("item2Amt"+i)||"".equals(request.getParameter("item2Amt"+i))){
                //药品金额
                if(StringUtils.isBlank(request.getParameter("item2Amt"+i))){
                    item2AmtParams.put("item2Amt"+i, "0");
				}else{
					item2AmtParams.put("item2Amt"+i, request.getParameter("item2Amt"+i));
				}
				
//				item3AmtParams.put("item3Amt"+i, request.getParameter("item3Amt"+i));
//				System.out.println("账户："+request.getParameter("accNo"+i)+"金额："+request.getParameter("amount"+i));
			}
	   		i++;
        }
        BigDecimal amt = new BigDecimal(0.00);
        Iterator<Entry<String,String>> ir = amountParams.entrySet().iterator();
        while(ir.hasNext()){
        	Entry<String,String> en = ir.next();
        	String key = en.getKey();
        	String val = en.getValue();
        	amt = amt.add(new BigDecimal(val));
        }
        if(new BigDecimal(amount).compareTo(amt)!=0){
        	return  new ResponseData(false,"清分总金额与清分到各机构金额总和不一致！");
        }
        BigDecimal medc = new BigDecimal(0.00);
        Iterator<Entry<String,String>> itm = item1AmtParams.entrySet().iterator();
        
        while(itm.hasNext()){
        	Entry<String,String> en = itm.next();
        	String key = en.getKey();
        	String value = en.getValue();
        	medc = medc.add(new BigDecimal(value));
        }
        
//        if(new BigDecimal(medcAmt).compareTo(medc)!=0){
//        	return  new ResponseData(false,"入账医疗总金额与清分总金额不一致！");
//        }
        
        Iterator<Entry<String,String>> itd = item2AmtParams.entrySet().iterator();
        BigDecimal drug = new BigDecimal(0.00);;
        while(itd.hasNext()){
        	Entry<String,String> en = itd.next();
        	String key = en.getKey();
        	String value = en.getValue();
        	drug = drug.add(new BigDecimal(value));
        }
//        if(new BigDecimal(drugAmt).compareTo(drug)!=0){
//        	return  new ResponseData(false,"入账药品总金额与清分总金额不一致！");
//        }
        if(new BigDecimal(amount).compareTo(medc.add(drug))!=0){
        	return  new ResponseData(false,"清分总金额与清分各机构明细金额不一致！");
        }

    	service.dqfAccountQf(id,accNo,note2,amount,itemId1,itemId2,accNoParams,
    			amountParams,item1AmtParams,item2AmtParams,medc,drug,unkType);
    	return new ResponseData(true,"清分成功！");
    }
    
    @RequestMapping(value="/LxRz")
    @ResponseBody
    public ResponseData lxRz(String accNo,String rzAmt,String date){
     	if(!service.lxRz(accNo,rzAmt,date)){
    		return new ResponseData(false,"操作失败！");
    	}
    	return new ResponseData(true,"操作成功！");
    }
    
    @RequestMapping(value="/findDqfAccs")
    @ResponseBody
    public List<Map<String,Object>> findDqfAccs(){
    	return service.findDqfAccs();
    }
    
    @RequestMapping(value="/findMain")
    @ResponseBody
    public List<Map<String,Object>> findMain(){
    	return service.findMain();
    }
    
    @RequestMapping(value="/findMainForInterest")
    @ResponseBody
    public List<Map<String,Object>> findMainForInterest(){
    	return service.findMainForInterest();
    }
    
    @RequestMapping(value="/findAllAccounts")
    @ResponseBody
    public List<Map<String,Object>> findAllAccounts(){
    	User user = SecurityContextUtil.getCurrentUser();
    	Map<String,Object> map = service.getOrganization(user.getOrgId());
    	Integer parentId = (Integer) map.get("PARENTID");
        if(parentId==0){
            //判断用户是否拥有卫计委权限的用户
            int userCode=publicService.judgeUserForm(user);
            //是卫计委用户，则查看全部交易记录
            if (Constants.ACC_CODE.WJW == userCode){
                return service.findAllAccounts("");
                //非卫计委用户
            }else{
                int accCode=publicService.judgeAccountType(user);
                return service.findAllAccountsByAccCode("",accCode);
            }
        }
    	return service.findAllAccounts(user.getOrgId().toString());
    }

}



//    public static void main(String[] args){
//    	BigDecimal a = new BigDecimal("0.97");
//    	BigDecimal b = new BigDecimal("0.7");
//    	BigDecimal c = new BigDecimal(0.00);
//    	for(int i=0;i<30;i++){
//    		c = c.add(a.add(b));
//    		System.out.println(c);
//    	}
//    	BigDecimal d = BigDecimal.valueOf(Double.parseDouble("0.5"));
//    	BigDecimal e = BigDecimal.valueOf(Double.parseDouble("0.5"));
//    	System.out.println(d);
//    	System.out.println(e);
//    	System.out.println(d.add(e));
//    	System.out.println(d.compareTo(e));
//    }



//    @RequestMapping(value="/dqfAccountQf")
//    @ResponseBody
//    public ResponseData dqfAccountQf(HttpServletRequest request){
//    	Map<String,String> accNoParams = new HashMap<String,String>();
//    	Map<String,String> amountParams = new HashMap<String,String>();
//    	Map<String,String> item1AmtParams = new HashMap<String,String>();
//    	Map<String,String> item2AmtParams = new HashMap<String,String>();
////    	Map<String,String> item3AmtParams = new HashMap<String,String>();
//    	String id = request.getParameter("id");
//    	String amount = request.getParameter("amount");
//    	String accNo = request.getParameter("accNo");
//    	String note2 = request.getParameter("note2");
//    	String itemId1 = request.getParameter("itemId1");
//    	String itemId2 = request.getParameter("itemId2");
////    	String itemId3 = request.getParameter("itemId3");
//    	String drugAmt = request.getParameter("drugAmt");
//    	String medcAmt = request.getParameter("medcAmt");
//    	Enumeration<String> enumeration = request.getParameterNames();
//    	int i = 0;
//        while (enumeration.hasMoreElements()){
//           enumeration.nextElement();
//	   		if(null!=request.getParameter("amount"+i)&&!"".equals(request.getParameter("amount"+i))){
//				accNoParams.put("accNo"+i,request.getParameter("accNo"+i));
//				amountParams.put("amount"+i, request.getParameter("amount"+i));
//				if(null==request.getParameter("item1Amt"+i)||"".equals(request.getParameter("item1Amt"+i))){
//					item1AmtParams.put("item1Amt"+i, "0");
//				}else{
//					item1AmtParams.put("item1Amt"+i, request.getParameter("item1Amt"+i));
//				}
//
//				if(null==request.getParameter("item2Amt"+i)||"".equals(request.getParameter("item2Amt"+i))){
//					item2AmtParams.put("item2Amt"+i, "0");
//				}else{
//					item2AmtParams.put("item2Amt"+i, request.getParameter("item2Amt"+i));
//				}
//
////				item3AmtParams.put("item3Amt"+i, request.getParameter("item3Amt"+i));
////				System.out.println("账户："+request.getParameter("accNo"+i)+"金额："+request.getParameter("amount"+i));
//			}
//	   		i++;
//        }
//
//        BigDecimal medc = new BigDecimal(0.00);
//        Iterator<Entry<String,String>> itm = item1AmtParams.entrySet().iterator();
//
//        while(itm.hasNext()){
//        	Entry<String,String> en = itm.next();
//        	String key = en.getKey();
//        	String value = en.getValue();
//        	medc = medc.add(new BigDecimal(value));
//        }
//
//        if(new BigDecimal(medcAmt).compareTo(medc)!=0){
//        	return  new ResponseData(false,"入账医疗总金额与清分总金额不一致！");
//        }
//
//        Iterator<Entry<String,String>> itd = item2AmtParams.entrySet().iterator();
//        BigDecimal drug = new BigDecimal(0.00);;
//        while(itd.hasNext()){
//        	Entry<String,String> en = itd.next();
//        	String key = en.getKey();
//        	String value = en.getValue();
//        	drug = drug.add(new BigDecimal(value));
//        }
//        if(new BigDecimal(drugAmt).compareTo(drug)!=0){
//        	return  new ResponseData(false,"入账药品总金额与清分总金额不一致！");
//        }
//
//    	service.dqfAccountQf(id,accNo,note2,amount,itemId1,itemId2,accNoParams,
//    			amountParams,item1AmtParams,item2AmtParams);
//    	return new ResponseData(true,"成功！");
//    }