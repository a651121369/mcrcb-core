package com.untech.mcrcb.web.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.untech.mcrcb.web.common.Constants;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.untech.mcrcb.web.dao.WjwBfdetailDao;
import com.untech.mcrcb.web.dao.WjwBfhzbDao;
import com.untech.mcrcb.web.dao.WjwIncomedetailDao;
import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.enhance.BaseService;
import com.untech.mcrcb.web.model.WjwAccount;
import com.untech.mcrcb.web.model.WjwBfdetail;
import com.untech.mcrcb.web.model.WjwBfhzb;
import com.untech.mcrcb.web.util.RlateUtil;
import com.untech.mcrcb.web.util.Utils;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.ResponseData;
import com.unteck.tpc.framework.core.util.SecurityContextUtil;
import com.unteck.tpc.framework.web.model.admin.User;

/**
 * WJW_BFHZB Service
 *
 * @author            chenyong
 * @since             2015-11-04
 */

@Service
public class WjwBfhzbService extends BaseService<WjwBfhzb, Long>{
	
      @Autowired
      private WjwBfhzbDao dao;
      
      @Autowired
      private WjwBfdetailDao wjwBfdetailDao;
      
      @Autowired
      private WjwIncomedetailDao indetaildao;
      
      @Override
	  public BaseDao<WjwBfhzb, Long> getHibernateBaseDao(){
        return this.dao;
      }

	public Pagination<Map<String, Object>> getAllIncomeAccount() {
		return dao.getAllIncomeAccount();
	}

	public List<Map<String, Object>> getAllIncomeAccountList() {
		return dao.getAllIncomeAccountList();
	}
	public List<Map<String, Object>> getWjwIncomeAccountList() {
		return dao.getWjwIncomeAccountList();
	}
	public List<Map<String, Object>> getJlyIncomeAccountList() {
		return dao.getJlyIncomeAccountList();
	}

	@Transactional
	public ResponseData appropriate(HttpServletRequest request) {
		@SuppressWarnings("rawtypes")
		Enumeration paramEnu = request.getParameterNames();
		@SuppressWarnings("rawtypes")
		Enumeration param = request.getParameterNames();
		Map<String,String> accCodeMap=new HashMap<String, String>(16);
		while (paramEnu.hasMoreElements()) {
			String name =(String) paramEnu.nextElement();
			String value = request.getParameter(name);
			accCodeMap.put(name,value);
		}
		String accCode=null;
		for (Map.Entry<String,String> entry:accCodeMap.entrySet()) {
			accCode=entry.getKey();
			break;
		}
		if (StringUtils.isNotBlank(accCode)&&accCode.length()>10){
			accCode=accCode.substring(0,1);
		}
		if(!check(request,accCode)){
			return new ResponseData(false,"拨付金额不能超过账户余额");
		}
		//关联号
		String connNo = RlateUtil.getUuid();
		BigDecimal drugAll = new BigDecimal(0);
		BigDecimal medcAll = new BigDecimal(0);
		String time = Utils.getTodayString("yyyyMMdd");
        while (param.hasMoreElements()){
            String paramName = (String) param.nextElement();
            if(paramName.startsWith(accCode+"bfDrugAmt")){
            	//拨付机构号
            	String unitNo = paramName.substring(11);
            	//拨付金额
            	BigDecimal bfDrugAmt = new BigDecimal(request.getParameter(paramName));
            	BigDecimal bfMedcAmt = new BigDecimal(request.getParameter(accCode+"bfMedcAmt_"+unitNo));
            	//拨付总金额
            	BigDecimal bfAll = bfDrugAmt.add(bfMedcAmt);
            	drugAll = drugAll.add(bfDrugAmt);
            	medcAll = medcAll.add(bfMedcAmt);
            	//收入账户表 和 支出账户
            	WjwAccount in = dao.findIncomeAccountByUnitNo(unitNo);
            	WjwAccount out = dao.findOutAccountByUnitNo(unitNo);

            	WjwBfdetail detail = new WjwBfdetail();
            	detail.setUnitNo(unitNo);
            	detail.setUnitName(in.getUnitName());
            	detail.setAmount(bfAll);
            	detail.setBfTime(time);
            	detail.setDrugBfAmt(bfDrugAmt);
            	detail.setMedcBfAmt(bfMedcAmt);
				//收入账户拨付前余额
            	detail.setInBfqAmt(in.getAmount());
				//收入账户拨付后余额
            	detail.setInBfhAmt(in.getAmount().subtract(bfAll));
				//支出账户拨付前余额
            	detail.setOutBfqAmt(out.getAmount());
				//支出账户拨付后余额
            	detail.setOutBfhAmt(out.getAmount().add(bfAll));
				//收入虚拟账号名称
            	detail.setXnAcctName(in.getCustName());
				//收入虚拟账号
            	detail.setXnAcctno(in.getAccNo());
            	detail.setZcAcctname(out.getCustName());
            	detail.setZcAcctno(out.getAccNo());
            	detail.setConnNo(connNo);
            	detail.setBfItem(0);
            	wjwBfdetailDao.save(detail);
            }
        }
        //拨付总表
        WjwBfhzb zb = new WjwBfhzb(); 
        zb.setBfDrugAmt(drugAll);
        zb.setBfMedcAmt(medcAll);
        zb.setBfAmt(drugAll.add(medcAll));
        User user = SecurityContextUtil.getCurrentUser();
        zb.setOperNo(user.getUserCode());
        zb.setUnitNo(user.getOrgId()+"");
        zb.setUnitName(user.getOrgName());
        zb.setBfTim(time);
        zb.setOperTime(time);
        zb.setConnNo(connNo);
        zb.setStatus(5);
		//凭证
        zb.setCertNo(createCode());
        //卫计委真实账户
        WjwAccount mainIn = dao.findWjwMainInAccount(accCode);
        zb.setOutAccno(mainIn.getAccNo());
        zb.setOutAccname(mainIn.getCustName());
        zb.setOutBank(mainIn.getBankName());
        //
        WjwAccount mainOut = dao.findWjwMainOutAccount(accCode);
        zb.setInAccno(mainOut.getAccNo());
        zb.setInName(mainOut.getCustName());
        zb.setInBank(mainOut.getBankName());
        
        //卫计委虚拟账户
        WjwAccount xnIn = dao.findWjwXNInAccount(accCode);
        zb.setXnAcctno(xnIn.getAccNo());
        zb.setXnAcctName(xnIn.getCustName());

        WjwAccount xnOut = dao.findWjwXNOutAccount(accCode);
        zb.setZcAcctno(xnOut.getAccNo());
        zb.setZcAcctname(xnOut.getCustName());
        dao.save(zb);
        return ResponseData.SUCCESS_NO_DATA;
	}
	
	/**
	 * 生成拨付凭证
	 * @return
	 */
	public String createCode(){
		int num = dao.getMaxCode();
		String jianPin = indetaildao.getWSYJianpin(SecurityContextUtil.getCurrentUser().getOrgId());
		return Utils.createCode(jianPin,"03",num);
	}
	
	/**
	 * 检测拨付参数是否合法
	 * @param request
	 * @return false 不合法
	 */
	private boolean check(HttpServletRequest request,String accCode) {
		int acCode=Integer.parseInt(accCode);
		List<Map<String, Object>> lists =new ArrayList<Map<String, Object>>();
		if (Constants.ACC_CODE.WSY==acCode){
			lists=getWjwIncomeAccountList();
		}else if (Constants.ACC_CODE.JLY==acCode){
			lists=getJlyIncomeAccountList();
		}
		for(Map<String,Object> map : lists){
//			System.out.println(map);
			String unitNo = (String)map.get("unitNo");
			//参数值
			BigDecimal bfDrugAmt = new BigDecimal(request.getParameter(accCode+"bfDrugAmt_"+unitNo));
        	BigDecimal bfMedcAmt = new BigDecimal(request.getParameter(accCode+"bfMedcAmt_"+unitNo));
        	if(bfDrugAmt.compareTo((BigDecimal) map.get("bfDrugAmt")) > 0 || 
        			bfMedcAmt.compareTo((BigDecimal) map.get("bfMedcAmt")) > 0){
        		return false;
        	}
		}
		return true;
	}

	public static void main(String[] args) {
		BigDecimal b1 = new BigDecimal(5);
		BigDecimal b2 = new BigDecimal(2);
		b2 = b2.add(b1);
		System.out.println(b2);
	}

	@Transactional
	public void deleteBF(Long[] ids) {
		for(Long id : ids){
			String connNo = dao.getBfhzbConnNo(id);
			dao.delete(id);
			wjwBfdetailDao.deleteByConnNo(connNo);
		}
		
	}

	/**
	 * 打印所需清单信息
	 * @param id
	 * @param modelMap
	 */
	public void printList(Long id, ModelMap modelMap) {
		List<Map<String,Object>> lists = new ArrayList<Map<String,Object>>();
		lists.add(dao.getPrintList(id));
		String connNo = dao.getBfhzbConnNo(id);
		
//		List<Map<String,Object>> list2 = wjwBfdetailDao.getBfhzbConnNo(connNo);
//		String certNo = (String)list2.get(0).get("certNo");
		String certNo =  dao.getCertNo(id);
		modelMap.put("certNo",certNo);
		
		lists.addAll(wjwBfdetailDao.getBfhzbConnNo(connNo));
		modelMap.put("lists",lists);
	}

	public Pagination<Map<String,Object>> getBfHzbList(Integer start, Integer limit, String operNo, String startTime,
			String endTime) {
		return dao.getBfHzbList(start,limit,operNo,startTime,endTime);
	}

	public void zfBF(Long[] ids) {
		for (Long id : ids){
	        dao.deleteIncome(id);
	      }
	}

	public Pagination<Map<String, Object>> findAppropriateDetail(Long id,Integer start,Integer limit) {
		String conn = dao.getBfhzbConnNo(id);
		return wjwBfdetailDao.getBfhzbConnNo(conn,start,limit);
	}
	
	public InputStream dowloadAppropriateDetail(Long id) throws IOException {
		String conn = dao.getBfhzbConnNo(id);
		List<Map<String,Object>> list = wjwBfdetailDao.downLoadAppropriate(conn);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("机构编号");
		
		cell = row.createCell(1);
		cell.setCellValue("机构名称");
		
		cell = row.createCell(2);
		cell.setCellValue("拨付总金额");
		
		cell = row.createCell(3);
		cell.setCellValue("拨付药品金额");
		
		cell = row.createCell(4);
		cell.setCellValue("拨付医疗金额");
		
		cell = row.createCell(5);
		cell.setCellValue("拨付时间");
		
		for(int i=0;i<list.size();i++){
			Map<String,Object> map = list.get(i);
			row = sheet.createRow(i+1);
			cell = row.createCell(0);
			cell.setCellValue(map.get("unitNo").toString());
			
			cell = row.createCell(1);
			cell.setCellValue(map.get("unitName").toString());
			
			cell = row.createCell(2);
			cell.setCellValue(map.get("amount").toString());
			
			cell = row.createCell(3);
			cell.setCellValue(map.get("drugAmt").toString());
			
			cell = row.createCell(4);
			cell.setCellValue(map.get("medcAmt").toString());
			
			cell = row.createCell(5);
			cell.setCellValue(map.get("bfTime").toString());
		}
		
		ByteArrayInputStream in = null;
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			wb.write(out);
			byte[] content = out.toByteArray();
			in = new ByteArrayInputStream(content);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null!=out){
				out.close();
			}
		}
		return in;
	}

	public List<Map<String, Object>> appropriatePrint(Long id) {
		
		return dao.appropriatePrint(id);
	}
}

