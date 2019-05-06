package com.untech.mcrcb.web.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.untech.mcrcb.web.dao.WjwAccchangeDao;
import com.untech.mcrcb.web.dao.WjwAccountDao;
import com.untech.mcrcb.web.model.WjwAccchange;
import com.untech.mcrcb.web.enhance.BaseDao;
import com.untech.mcrcb.web.enhance.BaseService;
import com.unteck.common.dao.support.Pagination;

/**
 * WJW_ACCCHANGE Service
 *
 * @author            chenyong
 * @since             2015-11-04
 */

@Service
public class WjwAccchangeService
      extends BaseService<WjwAccchange, Long>
    {
      @Autowired
      private WjwAccchangeDao dao;
      @Autowired 
      private WjwAccountDao accDao;
      
      public BaseDao<WjwAccchange, Long> getHibernateBaseDao()
      {
        return this.dao;
      }

	public Pagination<Map<String, Object>> findDqfDetails(Integer start,
			Integer limit, String startTime, String endTime) {

		return dao.findDqfDetails(start, limit, startTime, endTime);
	}
	public Pagination<Map<String, Object>> findDqfDetailsByAccCode(Integer start,
														  Integer limit,int accCode, String startTime, String endTime) {

		return dao.findDqfDetailsByAccCode(start, limit,accCode, startTime, endTime);
	}

	public Pagination<Map<String, Object>> findQfMx(String uuid, Integer start,
			Integer limit, String startTime, String endTime) {
		
		return dao.findQfMx(uuid, start, limit, startTime, endTime);
	}

	public Pagination<Map<String, Object>> findLxDetails(Integer start,
			Integer limit, String startTime, String endTime) {
		
		return dao.findLxDetails(start,limit,startTime,endTime);
	}

	public Pagination<Map<String, Object>> queryTradeDetail(Integer start,
			Integer limit, String startTime, String endTime, String accNo,String unitNo) {
		return dao.queryTradeDetail(start,limit,startTime,endTime,accNo,unitNo);
	}

	public Pagination<Map<String, Object>> queryTradeDetailChanage(Integer start, Integer limit,
																   	String startTime, String endTime, String accNo,String unitNo,int accCode) {
		return dao.queryTradeDetailChanage(start,limit,startTime,endTime,accNo,unitNo,accCode);
	}

	public InputStream downloadTradeDetail(String startTime, String endTime,String accNo) throws IOException {
		List<Map<String,Object>> list = dao.downloadTradeDetail(startTime,endTime,accNo);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		HSSFRow row = sheet.createRow(0);
		
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("机构编号");
		
		cell = row.createCell(1);
		cell.setCellValue("机构名称");
		cell = row.createCell(2);
		cell.setCellValue("己方账户名");
		cell = row.createCell(3);
		cell.setCellValue("对方账户名");
		cell = row.createCell(4);
		cell.setCellValue("账户余额");
		cell = row.createCell(5);
		cell.setCellValue("交易金额");
		cell = row.createCell(6);
		cell.setCellValue("药品金额");
		cell = row.createCell(7);
		cell.setCellValue("医疗金额");
		cell = row.createCell(8);
		cell.setCellValue("交易时间");
		cell = row.createCell(9);
		cell.setCellValue("收入/支出");
		
		for(int i=0;i<list.size();i++){
			Map<String,Object> map = list.get(i);
			row = sheet.createRow(i+1);
			cell = row.createCell(0);
			cell.setCellValue(map.get("unitNo").toString());
			
			cell = row.createCell(1);
			cell.setCellValue(map.get("unitName").toString());
			cell = row.createCell(2);
			cell.setCellValue(map.get("accName").toString());
			cell = row.createCell(3);
			cell.setCellValue(map.get("dfAccname").toString());
			cell = row.createCell(4);
			cell.setCellValue(map.get("amount").toString());
			cell = row.createCell(5);
			cell.setCellValue(map.get("tranAmt").toString());
			cell = row.createCell(6);
			cell.setCellValue(map.get("drugAmt")==null?"0.00":map.get("drugAmt").toString());
			cell = row.createCell(7);
			cell.setCellValue(map.get("medcAmt")==null?"0.00":map.get("medcAmt").toString());
			cell = row.createCell(8);
			cell.setCellValue(map.get("tranTime").toString());
			cell = row.createCell(9);
			if("1".equals(map.get("inOrOut").toString())){
				cell.setCellValue("收入");
			}else if("2".equals(map.get("inOrOut").toString())){
				cell.setCellValue("支出");
			}
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
			out.close();
		}
		return in;
	}

	public Map<String, Object> getOrganization(String unitNo) {
		
		return dao.getOrganization(unitNo);
	}

//	@Transactional
//	public boolean dqfRollback(WjwAccchange entity, Long status) {
//		try {
//			//状态为1---入账
//			if(status==1){
//				/**1、入账状态，需将待清分账户金额减去，主账户金额也对应减去*/
//				String accNo = entity.getAccNo();//待清分账户号
//				String dfAccno = entity.getDfAccno();//主账户号
//				BigDecimal amount = entity.getTranAmt();
//				BigDecimal medcAmt = entity.getMedcAmt();
//				BigDecimal drugAmt = entity.getDrugAmt();
////				BigDecimal otherAmt = entity.getOtherAmt();
//				//根据待清分账号查找账户表
//				Map<String,Object> dqfAccount = accDao.findByAccNo(accNo);
//				if(dqfAccount==null){
//					return false;
//				}
//				BigDecimal dqfAmount = (BigDecimal) dqfAccount.get("AMOUNT");
//				BigDecimal dqfDrugAmt = (BigDecimal) dqfAccount.get("DRUG_AMT");
//				BigDecimal dqfMedcAmt = (BigDecimal) dqfAccount.get("MEDICAL_AMT");
////				BigDecimal dqfOtherAmt = (BigDecimal) dqfAccount.get("OTHER_AMT");
//				//更新待清分账户各项余额
////				accDao.updateAccount(dqfAmount.subtract(amount), dqfDrugAmt.subtract(drugAmt), 
////						dqfMedcAmt.subtract(medcAmt), dqfOtherAmt.subtract(otherAmt), accNo);
//				accDao.updateAccount(dqfAmount.subtract(amount), dqfDrugAmt.subtract(drugAmt), 
//						dqfMedcAmt.subtract(medcAmt), accNo);
//				logger.info("待清分账务入账撤销---更新待清分账户各项余额减少");
//				
//				//根据主账户号查找账户表
//				Map<String,Object> mainAccount = accDao.findByAccNo(dfAccno);
//				if(mainAccount==null){
//					return false;
//				}
//				BigDecimal mainAmount = (BigDecimal) mainAccount.get("AMOUNT");
//				BigDecimal mainDrugAmt = (BigDecimal) mainAccount.get("DRUG_AMT");
//				BigDecimal mainMedcAmt = (BigDecimal) mainAccount.get("MEDICAL_AMT");
////				BigDecimal mainOtherAmt = (BigDecimal) mainAccount.get("OTHER_AMT");
//				//更新主账户各项余额
////				accDao.updateAccount(mainAmount.subtract(amount), mainDrugAmt.subtract(drugAmt),
////						mainMedcAmt.subtract(medcAmt), mainOtherAmt.subtract(otherAmt), dfAccno);
//				accDao.updateAccount(mainAmount.subtract(amount), mainDrugAmt.subtract(drugAmt),
//						mainMedcAmt.subtract(medcAmt),dfAccno);
//				logger.info("待清分账务入账撤销---更新主账户各项余额减少");
//				/** 2、删除余额变更表记录*/
//				
//				String conn = entity.getNote2();
//				dao.deleteByConn(conn,"flag is not null");
//				logger.info("待清分账务入账撤销---删除账户余额变更表对应的记录");
//			}else if(status==2){//状态为2--清分状态
//				//根据关联号找出清分的记录
//				String accNo = entity.getAccNo();//待清分账户号
////				String dfAccno = entity.getDfAccno();//主账户号
//				String conn = entity.getNote2();
//				List<Map<String,Object>> list = dao.findByConn(conn,accNo);
//				if(list==null){
//					return false;
//				}
//				for(int i=0;i<list.size();i++){
//					Map<String,Object> map = list.get(i);
//					String dqfAccno = map.get("ACC_NO").toString();
//					String childAccno = (String) map.get("DF_ACCNO");
//
//					BigDecimal amount = (BigDecimal) map.get("TRAN_AMT");
//					Integer inOrOut = (Integer) map.get("IN_OR_OUT");
//					if(inOrOut==2){//2--支出，交易金额为负数
//						amount = amount.multiply(new BigDecimal(-1));//交易金额为负数，需转换为正数进行运算
//					}					
//					BigDecimal medcAmt = (BigDecimal) map.get("MEDC_AMT");
//					BigDecimal drugAmt = (BigDecimal) map.get("DRUG_AMT");
////					BigDecimal otherAmt = (BigDecimal) map.get("OTHER_AMT");
//					
//					Map<String,Object> dqfAccount = accDao.findByAccNo(dqfAccno);
//					BigDecimal dqfAmount = (BigDecimal) dqfAccount.get("AMOUNT");
//					BigDecimal dqfDrugAmt = (BigDecimal) dqfAccount.get("DRUG_AMT");
//					BigDecimal dqfMedcAmt = (BigDecimal) dqfAccount.get("MEDICAL_AMT");
////					BigDecimal dqfOtherAmt = (BigDecimal) dqfAccount.get("OTHER_AMT");
//					//更新待清分账户各项余额，各项余额增加
////					accDao.updateAccount(dqfAmount.add(amount), dqfDrugAmt.add(drugAmt),
////							dqfMedcAmt.add(medcAmt), dqfOtherAmt.add(otherAmt), dqfAccno);
//					accDao.updateAccount(dqfAmount.add(amount), dqfDrugAmt.add(drugAmt),
//							dqfMedcAmt.add(medcAmt), dqfAccno);
//					logger.info("待清分账务清分撤销----待清分账户各项余额增加");
//					
//					Map<String,Object> childAccount = accDao.findByAccNo(childAccno);
//					BigDecimal childAmount = (BigDecimal) childAccount.get("AMOUNT");
//					BigDecimal childDrugAmt = (BigDecimal) childAccount.get("DRUG_AMT");
//					BigDecimal childMedcAmt = (BigDecimal) childAccount.get("MEDICAL_AMT");
////					BigDecimal childOtherAmt = (BigDecimal) childAccount.get("OTHER_AMT");	
//					//更新子账户各项余额，各项余额减少
////					accDao.updateAccount(childAmount.subtract(amount), childDrugAmt.subtract(drugAmt),
////							childMedcAmt.subtract(medcAmt), childOtherAmt.subtract(otherAmt), childAccno);
//					accDao.updateAccount(childAmount.subtract(amount), childDrugAmt.subtract(drugAmt),
//							childMedcAmt.subtract(medcAmt),childAccno);
//					logger.info("待清分账务清分撤销----子账户各项余额减少");
//				}
//				
//				//根据关联号，删除账户余额变更表对应的所有记录
//				dao.deleteByConn(conn, null);
//				logger.info("待清分账务清分撤销----删除账户余额变更表对应的清分记录");
//				//更改该笔记录的状态，由清分变为入账状态,FLAG 由2变1
//				dao.updateFlag(conn,1);
//				return true;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.info(e.getMessage());
//			return false;
//		}
//		return false;
//	}
	
	@Transactional
	public boolean dqfRollback(WjwAccchange entity, Long status) {
		try {
			//状态为1---入账
			if(status==1){
				/**1、入账状态，需将待清分账户金额减去，主账户金额也对应减去*/
				String accNo = entity.getAccNo();//待清分账户号
				String dfAccno = entity.getDfAccno();//主账户号
				BigDecimal amount = entity.getTranAmt();
				BigDecimal medcAmt = entity.getMedcAmt();
				BigDecimal drugAmt = entity.getDrugAmt();
//				BigDecimal otherAmt = entity.getOtherAmt();
				//根据待清分账号查找账户表
				Map<String,Object> dqfAccount = accDao.findByAccNo(accNo);
				if(dqfAccount==null){
					return false;
				}
				BigDecimal dqfAmount = (BigDecimal) dqfAccount.get("AMOUNT");
				BigDecimal dqfDrugAmt = (BigDecimal) dqfAccount.get("DRUG_AMT");
				BigDecimal dqfMedcAmt = (BigDecimal) dqfAccount.get("MEDICAL_AMT");
//				BigDecimal dqfOtherAmt = (BigDecimal) dqfAccount.get("OTHER_AMT");
				//更新待清分账户各项余额

				accDao.updateAccount(dqfAmount.subtract(amount), dqfDrugAmt,dqfMedcAmt, accNo);
				logger.info("待清分账务入账撤销---更新待清分账户各项余额减少");
				
				//根据主账户号查找账户表
				Map<String,Object> mainAccount = accDao.findByAccNo(dfAccno);
				if(mainAccount==null){
					return false;
				}
				BigDecimal mainAmount = (BigDecimal) mainAccount.get("AMOUNT");
				BigDecimal mainDrugAmt = (BigDecimal) mainAccount.get("DRUG_AMT");
				BigDecimal mainMedcAmt = (BigDecimal) mainAccount.get("MEDICAL_AMT");
//				BigDecimal mainOtherAmt = (BigDecimal) mainAccount.get("OTHER_AMT");
				//更新主账户各项余额
				accDao.updateAccount(mainAmount.subtract(amount), mainDrugAmt,
						mainMedcAmt,dfAccno);
				logger.info("待清分账务入账撤销---更新主账户各项余额减少");
				/** 2、删除余额变更表记录*/
				
				String conn = entity.getNote2();
				dao.deleteByConn(conn,"flag is not null");
				logger.info("待清分账务入账撤销---删除账户余额变更表对应的记录");
			}else if(status==2){//状态为2--清分状态
				//根据关联号找出清分的记录
				String accNo = entity.getAccNo();//待清分账户号
				String mainAccno = entity.getDfAccno();//主账户号
				String conn = entity.getNote2();
				List<Map<String,Object>> list = dao.findByConn(conn,accNo);
				if(list==null){
					return false;
				}
				BigDecimal medc = new BigDecimal(0.00);
				BigDecimal drug = new BigDecimal(0.00);
				for(int i=0;i<list.size();i++){
					Map<String,Object> map = list.get(i);
					String dqfAccno = map.get("ACC_NO").toString();
					String childAccno = (String) map.get("DF_ACCNO");

					BigDecimal amount = (BigDecimal) map.get("TRAN_AMT");
					Integer inOrOut = (Integer) map.get("IN_OR_OUT");
					if(inOrOut==2){//2--支出，交易金额为负数
						amount = amount.multiply(new BigDecimal(-1));//交易金额为负数，需转换为正数进行运算
					}					
					BigDecimal medcAmt = (BigDecimal) map.get("MEDC_AMT");
					BigDecimal drugAmt = (BigDecimal) map.get("DRUG_AMT");
//					BigDecimal otherAmt = (BigDecimal) map.get("OTHER_AMT");
					//将清分的医疗和药品金额累加，用来最后更新主账户对应的科目金额
					medc = medc.add(medcAmt);
					drug = drug.add(drugAmt);
					
					Map<String,Object> dqfAccount = accDao.findByAccNo(dqfAccno);
					BigDecimal dqfAmount = (BigDecimal) dqfAccount.get("AMOUNT");
					BigDecimal dqfDrugAmt = (BigDecimal) dqfAccount.get("DRUG_AMT");
					BigDecimal dqfMedcAmt = (BigDecimal) dqfAccount.get("MEDICAL_AMT");
//					BigDecimal dqfOtherAmt = (BigDecimal) dqfAccount.get("OTHER_AMT");
					//更新待清分账户各项余额，各项余额增加

					accDao.updateAccount(dqfAmount.add(amount), dqfDrugAmt,
							dqfMedcAmt, dqfAccno);
					logger.info("待清分账务清分撤销----待清分账户各项余额增加");
					
					Map<String,Object> childAccount = accDao.findByAccNo(childAccno);
					BigDecimal childAmount = (BigDecimal) childAccount.get("AMOUNT");
					BigDecimal childDrugAmt = (BigDecimal) childAccount.get("DRUG_AMT");
					BigDecimal childMedcAmt = (BigDecimal) childAccount.get("MEDICAL_AMT");
//					BigDecimal childOtherAmt = (BigDecimal) childAccount.get("OTHER_AMT");	
					//更新子账户各项余额，各项余额减少
//					accDao.updateAccount(childAmount.subtract(amount), childDrugAmt.subtract(drugAmt),
//							childMedcAmt.subtract(medcAmt), childOtherAmt.subtract(otherAmt), childAccno);
					accDao.updateAccount(childAmount.subtract(amount), childDrugAmt.subtract(drugAmt),
							childMedcAmt.subtract(medcAmt),childAccno);
					logger.info("待清分账务清分撤销----子账户各项余额减少");
				}
				
				//根据关联号，删除账户余额变更表对应的所有记录
				dao.deleteByConn(conn, null);
				logger.info("待清分账务清分撤销----删除账户余额变更表对应的清分记录");
				//更改该笔记录的状态，由清分变为入账状态,FLAG 由2变1
				dao.updateFlag(conn,1);
				logger.info("待清分账务清分撤销----变更待清分入账标志为1--入账状态");
				Map<String,Object> mainAccount = accDao.findByAccNo(mainAccno);
				if(mainAccount==null){
					return false;
				}
				BigDecimal mainAmount = (BigDecimal) mainAccount.get("AMOUNT");
				BigDecimal mainDrugAmt = (BigDecimal) mainAccount.get("DRUG_AMT");
				BigDecimal mainMedcAmt = (BigDecimal) mainAccount.get("MEDICAL_AMT");

				accDao.updateAccount(mainAmount, mainDrugAmt.subtract(drug), mainMedcAmt.subtract(medc), mainAccno);
				logger.info("待清分账务清分撤销----更新主账户的医疗和药品科目余额，余额减少");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return false;
		}
		return false;
	}
	@Transactional
	public boolean lxRollback(WjwAccchange entity, Long status) {
		try {
			String accNo = entity.getAccNo();//主账户号
			BigDecimal amount = entity.getTranAmt();//交易金额
			Map<String,Object> map = accDao.findByAccNo(accNo);
			BigDecimal intCome = (BigDecimal) map.get("INT_COME");
			intCome = intCome.subtract(amount);//当前账户利息金额减去入账时的交易金额
			//更新主账户利息字段
			accDao.updateLx(accNo,intCome);
			logger.info("撤销利息入账----更新利息字段，利息金额减少");
			dao.delete(entity.getId());
			logger.info("撤销利息入账----删除账户余额变更表对应的记录");
			return true;
		} catch (Exception e) {
			
			e.printStackTrace();
			return false;
		}
		
	}

}

