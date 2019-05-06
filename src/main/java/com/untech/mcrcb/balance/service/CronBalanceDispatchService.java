package com.untech.mcrcb.balance.service;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import com.untech.mcrcb.socket.client.SocketClientThread;
import com.untech.mcrcb.web.util.Utils;
@Service(value="tradingJob")
public class CronBalanceDispatchService{
	private String wjwCode;
	private String tranCode;
	private String request;

	public CronBalanceDispatchService(){
		try {
			ClassPathResource resource = new ClassPathResource("META-INF/res/socket.properties");
			Properties pp = PropertiesLoaderUtils.loadProperties(resource);
			this.wjwCode = pp.getProperty("wjw.code");
			this.tranCode = pp.getProperty("tran.code");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void dispatch(){
		System.out.print("========================每日系统对账==========================对账日期："+Utils.getTodayString("yyyyMMdd"));
		this.request = "|"+this.tranCode+"|"+Utils.getTodayString("yyyyMMdd")+"|"+this.wjwCode+"|";
		SocketClientThread client = new SocketClientThread(request);
		client.start();
	}
	
}
