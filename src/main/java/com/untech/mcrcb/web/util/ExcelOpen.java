package com.untech.mcrcb.web.util;

/**
 * Copyright (c) 2012,USTC E-BUSINESS TECHNOLOGY CO.LTD All Rights Reserved.
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * 读取EXCEL工具类
 * @author jin.congshan
 * @date 2012-9-28 10:15:20
 */
public class ExcelOpen {
	
	private static final Logger _logger = LoggerFactory.getLogger(ExcelOpen.class);
	
	private DecimalFormat formatter = new DecimalFormat("#");
	
	// workBook [includes sheet]
	private Workbook wb = null;

	private Sheet sheet = null;

	private Row row = null;

	// 第sheetnum个工作表
	private int sheetNum = 0;

	private int rowNum = 0;

	private InputStream fis = null;

	private MultipartFile multipartFile = null;
	
	private File file = null;

	public ExcelOpen() {
		
	}

	public ExcelOpen(File file) {
		this.file = file;
	}
	
	public ExcelOpen(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

	/**
	 * 读取excel文件获得HSSFWorkbook对象
	 * @throws IOException 
	 */
	public void open() throws IOException {
		try {
				fis = new FileInputStream(file);
				String fileName = file.getName().toLowerCase();
				if(fileName.endsWith(".xls")){
					wb = new HSSFWorkbook(fis);
				} else {
					wb = new XSSFWorkbook(fis);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(e.getMessage());
		} finally {
			if(null != fis){
				fis.close();
			}
		}
	}
	
	/**
	 * 读取excel文件获得HSSFWorkbook对象
	 * @throws IOException 
	 */
	public void openMultipartFile() throws IOException{
		try {
			fis = multipartFile.getInputStream();
			String fileName = multipartFile.getOriginalFilename().toLowerCase();
			if(fileName.endsWith(".xls")){
				wb = new HSSFWorkbook(fis);
			} else {
				wb = new XSSFWorkbook(fis);
			}
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(e.getMessage());
		} finally {
			if(null != fis){
				fis.close();
			}
		}
	}

	/**
	 * 返回sheet表数目
	 * @return int
	 */
	public int getSheetCount() {
		int sheetCount = -1;
		sheetCount = wb.getNumberOfSheets();
		return sheetCount;
	}

	/**
	 * sheetNum下的记录行数
	 * @return int
	 */
	public int getRowCount() {
		if (wb == null){
			System.out.println("=============>WorkBook为空");
			_logger.debug("=============>WorkBook为空");
			return 0;
		}
		sheet = wb.getSheetAt(this.sheetNum);
		int rowCount = -1;
		rowCount = sheet.getLastRowNum();
		return rowCount;
	}

	/**
	 * 读取指定sheetNum的rowCount
	 * @param sheetNum
	 * @return int
	 */
	public int getRowCount(int sheetNum) {
		sheet = wb.getSheetAt(sheetNum);
		int rowCount = -1;
		rowCount = sheet.getLastRowNum();
		return rowCount;
	}

	/**
	 * 得到指定行的内容
	 * @param lineNum
	 * @return String[]
	 */
	public String[] readExcelLine(int lineNum) {
		return readExcelLine(this.sheetNum, lineNum);
	}

	/**
	 * 得到指定行的内容
	 * @param lineNum
	 * @return Object[]
	 */
	public Object[] readExcelOjbectLine(int lineNum) {
		return readExcelObjectLine(this.sheetNum, lineNum);
	}
	
	/**
	 * 指定工作表和行数的内容
	 * @param sheetNum
	 * @param lineNum
	 * @return String[]
	 */
	public String[] readExcelLine(int sheetNum, int lineNum) {
		if (sheetNum < 0 || lineNum < 0){
			return null;
		}
		String[] strExcelLine = null;
		try {
			sheet = wb.getSheetAt(sheetNum);
			row = sheet.getRow(lineNum);

			int cellCount = row.getLastCellNum();
			strExcelLine = new String[cellCount];
			for (int i = 0; i < cellCount; i++) {
				strExcelLine[i] = readStringExcelCell(lineNum, i);
			}
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(e.getMessage());
		}
		return strExcelLine;
	}
	
	/**
	 * 指定工作表和行数的内容
	 * @param sheetNum
	 * @param lineNum
	 * @return Object[]
	 */
	public Object[] readExcelObjectLine(int sheetNum, int lineNum) {
		if (sheetNum < 0 || lineNum < 0){
			return null;
		}
		Object[] strExcelLine = null;
		try {
			sheet = wb.getSheetAt(sheetNum);
			row = sheet.getRow(lineNum);

			int cellCount = row.getLastCellNum();
			strExcelLine = new Object[cellCount];
			for (int i = 0; i < cellCount; i++) {
				strExcelLine[i] = this.readExcelCell(lineNum, i);
			}
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(e.getMessage());
		}
		return strExcelLine;
	}

	/**
	 * 读取指定列的内容
	 * @param cellNum
	 * @return String
	 */
	public String readStringExcelCell(int cellNum) {
		return readStringExcelCell(this.rowNum, cellNum);
	}

	/**
	 * 指定行和列编号的内容
	 * @param rowNum
	 * @param cellNum
	 * @return String
	 */
	public String readStringExcelCell(int rowNum, int cellNum) {
		return readStringExcelCell(this.sheetNum, rowNum, cellNum);
	}
	
	/**
	 * 指定行和列编号的内容
	 * @param rowNum
	 * @param cellNum
	 * @return Object
	 */
	public Object readExcelCell(int rowNum, int cellNum) {
		return readExcelCell(this.sheetNum, rowNum, cellNum);
	}

	/**
	 * 指定工作表、行、列下的内容
	 * @param sheetNum
	 * @param rowNum
	 * @param cellNum
	 * @return String
	 */
	public String readStringExcelCell(int sheetNum, int rowNum, int cellNum) {
		if (sheetNum < 0 || rowNum < 0){
			return "";
		}
		String strExcelCell = "";
		try {
			sheet = wb.getSheetAt(sheetNum);
			row = sheet.getRow(rowNum);
			if (null != row.getCell(cellNum)) { // add this condition
				// judge
				switch (row.getCell(cellNum).getCellType()) {
					case HSSFCell.CELL_TYPE_FORMULA : strExcelCell = "FORMULA "; break;
					case HSSFCell.CELL_TYPE_STRING : strExcelCell = row.getCell(cellNum).getStringCellValue(); break;
					case HSSFCell.CELL_TYPE_NUMERIC : strExcelCell = formatter.format(row.getCell(cellNum).getNumericCellValue()); break;
					case HSSFCell.CELL_TYPE_BLANK : strExcelCell = ""; break;
					default: strExcelCell = ""; break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(e.getMessage());
		}
		return strExcelCell;
	}
	
	/**
	 * 指定工作表、行、列下的内容
	 * @param sheetNum
	 * @param rowNum
	 * @param cellNum
	 * @return String
	 */
	public Object readExcelCell(int sheetNum, int rowNum, int cellNum) {
		if (sheetNum < 0 || rowNum < 0){
			return "";
		}
		Object strExcelCell = null;
		try {
			sheet = wb.getSheetAt(sheetNum);
			row = sheet.getRow(rowNum);
			if (null != row.getCell(cellNum)) { // add this condition
				// judge
				switch (row.getCell(cellNum).getCellType()) {
					case HSSFCell.CELL_TYPE_FORMULA : strExcelCell = "FORMULA "; break;
					case HSSFCell.CELL_TYPE_STRING : strExcelCell = row.getCell(cellNum).getStringCellValue(); break;
					case HSSFCell.CELL_TYPE_NUMERIC : strExcelCell = row.getCell(cellNum).getNumericCellValue(); break;
					case HSSFCell.CELL_TYPE_BLANK : strExcelCell = ""; break;
					default: strExcelCell = ""; break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			_logger.error(e.getMessage());
		}
		return strExcelCell;
	}
	
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public void setSheetNum(int sheetNum) {
		this.sheetNum = sheetNum;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public static void main(String args[]) {
		DecimalFormat formatter = new DecimalFormat("#.##");
		File file = new File("C:\\Users\\fanHua\\Desktop\\新建 Microsoft Excel 工作表.xls");
		ExcelOpen readExcel = new ExcelOpen(file);
		try {
			readExcel.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
		readExcel.setSheetNum(0); // 设置读取索引为0的工作表
		// 总行数
		int count = readExcel.getRowCount();
		
		for (int i = 1; i <= count; i++) {
			Object[] rows = readExcel.readExcelOjbectLine(i);
			for (int j = 0; j < rows.length; j++) {
				if(rows[j] instanceof Double){
					((Double)rows[j]).intValue();
					System.out.print(((Double)rows[j]).longValue() + "\t");
				} else {
					System.out.print(rows[j] + "\t");
				}
			}
			System.out.print("\n");
		}
		// ==========================>
		/*MultipartFile filex = null;
		ExcelOpen readExcelx = new ExcelOpen(filex);
		try {
			readExcelx.openMultipartFile();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}