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
import com.untech.mcrcb.web.dao.WjwJKInfoDao;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.SecurityContextUtil;
import com.unteck.tpc.framework.web.model.admin.User;


@Service
public class WjwJKInfoService {
	
	@Autowired
    private WjwJKInfoDao dao;
	
	@Autowired
    private WjwIncomedetailDao incomedao;

	@Autowired
	private PublicService publicService;


	
	public Pagination<Map<String, Object>> getWjwJKInfo(Integer start, Integer limit,   
			 String BEGINTIME,String ENDTIME,String UNIT_NO) {
		User user = SecurityContextUtil.getCurrentUser();
		Integer parentId = incomedao.getOrgParentId(user.getOrgId());
		if(parentId==0){
			//判断用户是否拥有卫计委权限的用户
			int userCode=publicService.judgeUserForm(user);
			//是卫计委用户，则查看全部交易记录
			if (Constants.ACC_CODE.WJW == userCode){
				return this.dao.getWjwJKInfo(start, limit,BEGINTIME, ENDTIME, UNIT_NO,parentId,user.getOrgId());
				//非卫计委用户
			}else{
				int accCode=publicService.judgeAccountType(user);
				return this.dao.getWjwJKInfoByAccCode(start, limit,BEGINTIME, ENDTIME, UNIT_NO,parentId,user.getOrgId(),accCode);
			}
		}
		return this.dao.getWjwJKInfo(start, limit,BEGINTIME, ENDTIME, UNIT_NO,parentId,user.getOrgId());
	}
	
	public InputStream getWjwJKInfoDownMx(String beginDate,String endDate,String UNIT_NO) 
			throws IOException {
		User user = SecurityContextUtil.getCurrentUser();
		Integer parentId = incomedao.getOrgParentId(user.getOrgId());
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		HSSFRow row = sheet.createRow(0);
		 
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("机构名称");
		
		cell = row.createCell(1);
		cell.setCellValue("缴款时间");
		
		cell = row.createCell(2);
		cell.setCellValue("缴款金额");
		
		cell = row.createCell(3);
		cell.setCellValue("药品收入");
		
		cell = row.createCell(4);
		cell.setCellValue("医疗收入");
		
		cell = row.createCell(5);
		cell.setCellValue("缴款人");

		cell = row.createCell(6);
		cell.setCellValue("备注");

		List<Map<String,Object>> list = this.dao.getWjwJKInfoDownMx(beginDate, endDate, UNIT_NO,parentId,user.getOrgId());
		
		for(int i=0;i<list.size();i++){
			Map<String,Object> map = list.get(i);
			row = sheet.createRow(i+1);
			
			cell = row.createCell(0);
			cell.setCellValue(""+map.get("UNIT_NAME"));
			
			cell = row.createCell(1);
			cell.setCellValue(""+map.get("INC_TIME"));
			
			cell = row.createCell(2);
			cell.setCellValue(""+map.get("AMOUNT"));
			
			cell = row.createCell(3);
			cell.setCellValue(""+map.get("ITEM1_AMT"));
			 
			cell = row.createCell(4);
			cell.setCellValue(""+map.get("ITEM2_AMT"));
			
			cell = row.createCell(5);
			cell.setCellValue(""+map.get("OUT_ACCNAME"));
			
			cell = row.createCell(6);
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
