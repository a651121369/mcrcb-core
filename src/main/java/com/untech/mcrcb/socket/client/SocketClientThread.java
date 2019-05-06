package com.untech.mcrcb.socket.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.Assert;
import com.untech.mcrcb.balance.service.BalanceAccountService;

public class SocketClientThread extends Thread{
	Logger logger = LoggerFactory.getLogger(SocketClientThread.class);
	private Socket socket;
	private InputStream in;
	private OutputStream out;
	private byte[] request;
	public SocketClientThread(String request){
		
		this.request = request.getBytes();
		try {
			ClassPathResource resource = new ClassPathResource("META-INF/res/socket.properties");
			Properties properties = PropertiesLoaderUtils.loadProperties(resource);
			String host = properties.getProperty("socket.server.ip").trim();
			int port = Integer.parseInt(properties.getProperty("socket.server.port").trim());
			if(socket==null){
				this.socket = new Socket(host,port);
				this.in = this.socket.getInputStream();
				this.out = socket.getOutputStream();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void run(){
		logger.info("===向中间业务平台发送对账交易===");
		try {
			this.out.write(request);
			out.flush();
			this.socket.setSoTimeout(60000);
			int readNum;
			byte[] firstByte = new byte[1];
			readNum = this.in.read(firstByte);
			// 未读取到数据
			if(readNum < firstByte.length) {
				logger.info("read empty request");
			}

			byte[] response = null;
			String responseStr = null;
//			String type = null;
			// 获取报文
			byte[] buff = new byte[1024];
			readNum = this.in.read(buff);
			Assert.isTrue(readNum > 0, "too short socket message");
			
			if(readNum > 1024) {
				
			} else {
				response = new byte[readNum + 1];
				response[0] = firstByte[0];
				System.arraycopy(buff, 0, response, 1, readNum);
			}		
			responseStr = new String(response,"gbk");
			logger.info("responseStr:{}", responseStr);
			responseStr = StringUtils.strip(responseStr, "|");
			System.out.println("responseStr"+responseStr);
			String[] res = responseStr.split("\\|");
			String returnCode = res[1];
			if("0000".equals(returnCode)){
				String fileName = res[3];
				BalanceAccountService service = new BalanceAccountService(fileName);
				service.BalanceAccount();
				logger.info("====对账结束====");
			}else{
				logger.info("返回信息："+res[2]);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(this!=null&&!this.isInterrupted()){
					out.close();
					in.close();
					this.socket.close();
					this.interrupt();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		
			
		}
	}
}
