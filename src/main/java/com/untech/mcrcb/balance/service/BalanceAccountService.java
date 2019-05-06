package com.untech.mcrcb.balance.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import com.untech.mcrcb.ftp.FtpClientUtil;
import com.untech.mcrcb.tran.service.BalanceService;
import com.unteck.tpc.framework.core.spring.SpringApplicationContextHolder;

public class BalanceAccountService {

	Logger logger = LoggerFactory.getLogger(BalanceAccountService.class);
	private String url ;
	private int port;
	private String username;
	private String password;
	private String remotePath;
	private String fileName;
	private String localPath;
	private BalanceService service = SpringApplicationContextHolder.getBean(BalanceService.class);
	public BalanceAccountService(){
		
	}
	public BalanceAccountService (String fileName){
		try {
			ClassPathResource resource = new ClassPathResource("META-INF/res/socket.properties");
			Properties pp = PropertiesLoaderUtils.loadProperties(resource);
			this.url = pp.getProperty("ftp.url");
			this.port = Integer.parseInt(pp.getProperty("ftp.port"));
			this.username = pp.getProperty("ftp.username");
			this.password = pp.getProperty("ftp.password");
			this.remotePath = pp.getProperty("ftp.remotePath");
			this.fileName = fileName;
			this.localPath = pp.getProperty("ftp.localPath");
		} catch (IOException e) {
			logger.info("BalanceAccountService===53");
			logger.info(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void BalanceAccount(){
		String str = readFileToString();
		List<Map<String,String>> list = stringToList(str);
		balanceAccount(list);
	}
	private void balanceAccount(List<Map<String, String>> list) {
		if(list==null){
			return;
		}
		try {
			for(int i=0;i<list.size();i++){
				Map<String,String> map = list.get(i);
				String tallyType = map.get("type");
				//缴款对账
				if("1".equals(tallyType)){
					service.balanceIncome(map);
				}
				//支付对账
				else if("2".equals(tallyType)){
					service.balancePay(map);
				}
				//拨付对账
				else if("3".equals(tallyType)){
					service.balanceAppropriate(map);
				}
				//利息支出
				else if("4".equals(tallyType)){
					
				}
				//利息收入
				else if("5".equals(tallyType)){
					
				}
				//待清分入账
				else if("6".equals(tallyType)){
					
				}
				//退汇
				else if("7".equals(tallyType)){
					
				}
			}
			
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}

	}

	public List<Map<String,String>> stringToList(String str){
		if("".equals(str.trim())||null==str){
			logger.info("对账文件为空！");
			return null;
		}
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		System.out.println("str:"+str);
		String data = StringUtils.strip(str, "|");
		System.out.println("data:"+data);
		String[] result = data.split("\\|");
		
		for(int i=0;i<(result.length/6);i++){
			Map<String,String> map = new HashMap<String, String>();
			map.put("voucher", result[i*6]);
			map.put("type", result[i*6+1]);
			map.put("date", result[i*6+2]);
			map.put("amount", result[i*6+3]);
			map.put("status", result[i*6+4]);
			map.put("user", result[i*6+5]);
			list.add(map);
			logger.info("List--map:"+map.toString());
		}
		return list;
	}
	
//	public String readFileToString(){
//		boolean exist = FtpClientUtil.downloadFile(url, port, username, password, remotePath, fileName, localPath);
//		if(exist){
//			File file = new File(this.localPath+File.separator+fileName);
//			StringBuffer sb = new StringBuffer();
//			FileInputStream fis = null;
//			InputStreamReader isr = null;
//			BufferedReader br = null;
//			try {
//				String read = "";
//				fis = new FileInputStream(file);
//				isr = new InputStreamReader(fis);
//				br = new BufferedReader(isr);
//				while((read=br.readLine())!=null){
//						sb.append(read);
//				}
//			} catch (FileNotFoundException e) {
//				logger.info("没有找到文件！");
//			}catch (IOException e) {
//				logger.info("读取文件失败");
//			}finally{
//				try {
//					br.close();
//					isr.close();
//					fis.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			return sb.toString();
//		}else{
//			logger.info("下载对账文件失败");
//			return null;
//		}
//	}
	public String readFileToString(){
//		boolean exist = FtpClientUtil.downloadFile(url, port, username, password, remotePath, fileName, localPath);		
			File file = new File(this.remotePath+File.separator+fileName);
		    if(!file.exists()){
		    	logger.info("文件不存在");
            	return null;
            }
			StringBuffer sb = new StringBuffer();
			FileInputStream fis = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
			try {
				String read = "";
				fis = new FileInputStream(file);
				isr = new InputStreamReader(fis);
				br = new BufferedReader(isr);
				while((read=br.readLine())!=null){
						sb.append(read);
				}
			} catch (FileNotFoundException e) {
				logger.info("没有找到文件！");
			}catch (IOException e) {
				logger.info("读取文件失败");
			}finally{
				try {
					br.close();
					isr.close();
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return sb.toString();

	}
}
