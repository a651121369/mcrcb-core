package com.untech.mcrcb.web.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.untech.mcrcb.web.common.Constants;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.untech.mcrcb.web.dao.WjwIncomedetailDao;
import com.untech.mcrcb.web.dao.WjwZFInfoDao;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.SecurityContextUtil;
import com.unteck.tpc.framework.web.model.admin.User;

@Service
public class WjwZFInfoService {
	@Autowired
	private WjwZFInfoDao dao;

	@Autowired
	private WjwIncomedetailDao incomedao;

	@Autowired
	private PublicService publicService;


	public Pagination<Map<String, Object>> getWjwZFInfo(Integer start, Integer limit,   
			 String beginDate,String endDate,String UNIT_NO) {
		User user = SecurityContextUtil.getCurrentUser();
		Integer parentId = incomedao.getOrgParentId(user.getOrgId());

		if(parentId==0){
			//判断用户是否拥有卫计委权限的用户
			int userCode=publicService.judgeUserForm(user);
			//是卫计委用户，则查看全部交易记录
			if (Constants.ACC_CODE.WJW == userCode){
				return this.dao.getWjwZFInfo(start, limit,beginDate, endDate, UNIT_NO,parentId,user.getOrgId());
				//非卫计委用户
			}else{
				int accCode=publicService.judgeAccountType(user);
				return this.dao.getWjwZFInfoByAccCode(start, limit,beginDate, endDate, UNIT_NO,parentId,user.getOrgId(),accCode);
			}
		}

		return this.dao.getWjwZFInfo(start, limit,beginDate, endDate, UNIT_NO,parentId,user.getOrgId());
	}
	
	
	public InputStream getWjwZFInfoDownMx(String beginDate,String endDate,String UNIT_NO) 
			throws IOException {
		User user = SecurityContextUtil.getCurrentUser();
		Integer parentId = incomedao.getOrgParentId(user.getOrgId());
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		HSSFRow row = sheet.createRow(0);
		 
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("机构名称");
		
		cell = row.createCell(1);
		cell.setCellValue("支付时间");
		
		cell = row.createCell(2);
		cell.setCellValue("收款人");
		
		cell = row.createCell(3);
		cell.setCellValue("收款账户");
		
		cell = row.createCell(4);
		cell.setCellValue("申请金额");
		
		cell = row.createCell(5);
		cell.setCellValue("科目");

		cell = row.createCell(6);
		cell.setCellValue("用途");
		
		cell = row.createCell(7);
		cell.setCellValue("备注");

		List<Map<String,Object>> list = this.dao.getWjwZFInfoDownMx(beginDate, endDate, UNIT_NO,parentId,user.getOrgId());
		
		for(int i=0;i<list.size();i++){
			Map<String,Object> map = list.get(i);
			row = sheet.createRow(i+1);
			
			cell = row.createCell(0);
			cell.setCellValue(""+map.get("UNIT_NAME"));
			
			cell = row.createCell(1);
			cell.setCellValue(""+map.get("PAY_TIME"));
			
			cell = row.createCell(2);
			cell.setCellValue(""+map.get("IN_NAME"));
			
			cell = row.createCell(3);
			cell.setCellValue(""+map.get("IN_ACCNO"));
			 
			cell = row.createCell(4);
			cell.setCellValue(""+map.get("AMOUNT"));
			
			cell = row.createCell(5);
			if("1".equals(map.get("ITEM").toString())){
				cell.setCellValue("医疗");
			}else if("2".equals(map.get("ITEM").toString())){
				cell.setCellValue("药品");
			}

			
			
			cell = row.createCell(6);
			cell.setCellValue(""+map.get("YT"));
			
			cell = row.createCell(7);
			if(map.get("NOTE1")==null||"".equals(map.get("NOTE1").toString())){	
				cell.setCellValue("");
			}else{
				cell.setCellValue(""+map.get("NOTE1"));
			}
			
			
		}
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		try {
			wb.write(os);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		byte[] content = os.toByteArray();
		
		os.close();
		
		InputStream is = new ByteArrayInputStream(content);
		
		return is;
	}

}
