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
import org.apache.poi.hssf.util.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.untech.mcrcb.web.dao.WjwAccMxInfoDao;
import com.unteck.common.dao.support.Pagination;

@Service
public class WjwAccMxInfoService {
	
	@Autowired
	private WjwAccMxInfoDao dao;
	
	public Pagination<Map<String, Object>> getWjwAccMxInfo(Integer start, Integer limit,   
			String UNIT_NO,String accCode) {
		return this.dao.getWjwAccMxInfo(start, limit,UNIT_NO,accCode);
	}
	
	
	public InputStream getWjwAccMxInfoDownMx(String UNIT_NO) 
			throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);//第一行第一列
		cell.setCellValue("机构编号");
		cell = row.createCell(1);//第一行第二列
		cell.setCellValue("机构名称");
		cell = row.createCell(2);//第一行第三列
		cell.setCellValue("余额");
		cell = row.createCell(3);//第一行第四列
		cell.setCellValue("收入账户余额");
		cell = row.createCell(4);//第一行第五列
		cell = row.createCell(5);//第一行第六列
		cell.setCellValue("支出账户余额");
		cell = row.createCell(6);//第一行第七列
		
		
		HSSFRow row1 = sheet.createRow(1);
		HSSFCell cell1 = row1.createCell(0);//第二行第一列
		cell1 = row1.createCell(1);//第二行第二列
		cell1 = row1.createCell(2);//第二行第三列
		cell1 = row1.createCell(3);//第二行第四列
		cell1.setCellValue("药品");
		cell1 = row1.createCell(4);//第二行第五列
		cell1.setCellValue("医疗");
		cell1 = row1.createCell(5);//第二行第六列
		cell1.setCellValue("药品");
		cell1 = row1.createCell(6);//第二行第七列
		cell1.setCellValue("医疗");
		
		//合并单元格,如下所示(fromRow,fromCol,toRow,toCol)
		Region region1 = new Region(0, (short)0, 1, (short)0);   
        Region region2 = new Region(0, (short)1, 1, (short)1);   
        Region region3 = new Region(0, (short)2, 1, (short)2);   
        Region region4 = new Region(0, (short)3, 0, (short)4);   
        Region region5 = new Region(0, (short)5, 0, (short)6);   
        
        sheet.addMergedRegion(region1);   
        sheet.addMergedRegion(region2);   
        sheet.addMergedRegion(region3);   
        sheet.addMergedRegion(region4);   
        sheet.addMergedRegion(region5);   
		
		List<Map<String,Object>> list = this.dao.getWjwAccMxInfoDownMx(UNIT_NO);
		
		for(int i=0;i<list.size();i++){
			Map<String,Object> map = list.get(i);
			row = sheet.createRow(i+2);
			
			cell = row.createCell(0);
			cell.setCellValue(""+map.get("UNIT_NO"));
			
			cell = row.createCell(1);
			cell.setCellValue(""+map.get("UNIT_NAME"));
			
			cell = row.createCell(2);
			cell.setCellValue(""+map.get("AMOUNT"));
			
			cell = row.createCell(3);
			cell.setCellValue(""+map.get("DRUG_AMT_IN"));
			 
			cell = row.createCell(4);
			cell.setCellValue(""+map.get("MEDC_AMT_IN"));
			
			cell = row.createCell(5);
			cell.setCellValue(""+map.get("DRUG_AMT_OUT"));
			
			cell = row.createCell(6);
			cell.setCellValue(""+map.get("MEDC_AMT_OUT"));
			
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
