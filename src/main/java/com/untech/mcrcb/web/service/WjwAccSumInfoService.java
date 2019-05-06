package com.untech.mcrcb.web.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.untech.mcrcb.web.dao.WjwAccSumInfoDao;
import com.unteck.common.dao.support.Pagination;

@Service
public class WjwAccSumInfoService {
	
	@Autowired
	private WjwAccSumInfoDao dao;
	
	public Pagination<Map<String, Object>> getWjwAccSumInfo(Integer start, Integer limit,   
			 String unitNo,String accCode) {
		return this.dao.getWjwAccSumInfo(start, limit, unitNo,accCode);
	}
	
	public InputStream getWjwAccSumInfoDownMx(String UNIT_NO) 
			throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		HSSFRow row = sheet.createRow(0);
		 
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("机构编号");
		
		cell = row.createCell(1);
		cell.setCellValue("机构名称");
		
		cell = row.createCell(2);
		cell.setCellValue("账户金额");
		
		cell = row.createCell(3);
		cell.setCellValue("收入户余额");
		
		cell = row.createCell(4);
		cell.setCellValue("支出户余额");

		List<Map<String,Object>> list = this.dao.getWjwAccSumInfoDownMx(UNIT_NO);
		
		for(int i=0;i<list.size();i++){
			Map<String,Object> map = list.get(i);
			row = sheet.createRow(i+1);
			
			cell = row.createCell(0);
			cell.setCellValue(""+map.get("UNIT_NO"));
			
			cell = row.createCell(1);
			cell.setCellValue(""+map.get("UNIT_NAME"));
			
			cell = row.createCell(2);
			cell.setCellValue(""+map.get("AMOUNT"));
			
			cell = row.createCell(3);
			cell.setCellValue(""+map.get("SAMOUNT"));
			 
			cell = row.createCell(4);
			cell.setCellValue(""+map.get("ZAMOUNT"));
			
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
