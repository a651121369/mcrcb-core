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

import com.untech.mcrcb.web.dao.WjwBFInfoDao;
import com.untech.mcrcb.web.dao.WjwIncomedetailDao;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.SecurityContextUtil;
import com.unteck.tpc.framework.web.model.admin.User;

@Service
public class WjwBFInfoService {
	
	@Autowired
	private WjwBFInfoDao dao;
	
	@Autowired
    private WjwIncomedetailDao incomedao;
	
	public Pagination<Map<String, Object>> getWjwBFInfo(Integer start, Integer limit,   
			 String beginDate,String endDate,String UNIT_NO) {
		User user = SecurityContextUtil.getCurrentUser();
		Integer parentId = incomedao.getOrgParentId(user.getOrgId());
		return this.dao.getWjwBFInfo(start, limit,beginDate, endDate, UNIT_NO,parentId,user.getOrgId());
	}
	
	public InputStream getWjwBFInfoDownMx(String beginDate,String endDate,String UNIT_NO) 
			throws IOException {
		User user = SecurityContextUtil.getCurrentUser();
		Integer parentId = incomedao.getOrgParentId(user.getOrgId());
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);//第一行第一列
		cell.setCellValue("机构");
		cell = row.createCell(1);//第一行第二列
		cell.setCellValue("拨付时间");
		cell = row.createCell(2);//第一行第三列
		cell.setCellValue("拨付金额");
		cell = row.createCell(3);//第一行第四列
		cell.setCellValue("科目");
		cell = row.createCell(4);//第一行第五列
		cell.setCellValue("收入账户余额");
		cell = row.createCell(5);//第一行第六列
		cell = row.createCell(6);//第一行第七列
		cell.setCellValue("支出账户余额");
		cell = row.createCell(7);//第一行第八列
		
		
		HSSFRow row1 = sheet.createRow(1);
		HSSFCell cell1 = row1.createCell(0);//第二行第一列
		cell1 = row1.createCell(1);//第二行第二列
		cell1 = row1.createCell(2);//第二行第三列
		cell1 = row1.createCell(3);//第二行第四列
		cell1 = row1.createCell(4);//第二行第五列
		cell1.setCellValue("拨付前");
		cell1 = row1.createCell(5);//第二行第六列
		cell1.setCellValue("拨付后");
		cell1 = row1.createCell(6);//第二行第七列
		cell1.setCellValue("拨付前");
		cell1 = row1.createCell(7);//第一行第八列
		cell1.setCellValue("拨付后");
		
		//合并单元格,如下所示(fromRow,fromCol,toRow,toCol)
		Region region1 = new Region(0, (short)0, 1, (short)0);   
        Region region2 = new Region(0, (short)1, 1, (short)1);   
        Region region3 = new Region(0, (short)2, 1, (short)2);   
        Region region4 = new Region(0, (short)3, 1, (short)3);   
        Region region5 = new Region(0, (short)4, 0, (short)5);   
        Region region6 = new Region(0, (short)6, 0, (short)7);   
        
        sheet.addMergedRegion(region1);   
        sheet.addMergedRegion(region2);   
        sheet.addMergedRegion(region3);   
        sheet.addMergedRegion(region4);   
        sheet.addMergedRegion(region5);   
        sheet.addMergedRegion(region6);
		
		List<Map<String,Object>> list = this.dao.getWjwBFInfoDownMx(beginDate, endDate, UNIT_NO,parentId,user.getOrgId());
		
		for(int i=0;i<list.size();i++){
			Map<String,Object> map = list.get(i);
			row = sheet.createRow(i+2);
			
			cell = row.createCell(0);
			cell.setCellValue(""+map.get("UNIT_NAME"));
			
			cell = row.createCell(1);
			cell.setCellValue(""+map.get("BF_TIME"));
			
			cell = row.createCell(2);
			cell.setCellValue(""+map.get("AMOUNT"));
			
			cell = row.createCell(3);
			cell.setCellValue(""+map.get("BF_ITEM"));
			 
			cell = row.createCell(4);
			cell.setCellValue(""+map.get("IN_BFQ_AMT"));
			
			cell = row.createCell(5);
			cell.setCellValue(""+map.get("IN_BFH_AMT"));
			
			cell = row.createCell(6);
			cell.setCellValue(""+map.get("OUT_BFQ_AMT"));
			
			cell = row.createCell(7);
			cell.setCellValue(""+map.get("OUT_BFH_AMT"));
			
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
