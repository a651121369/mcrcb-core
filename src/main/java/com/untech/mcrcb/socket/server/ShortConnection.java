package com.untech.mcrcb.socket.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.untech.mcrcb.socket.server.support.ServiceFactoryBean;
import com.untech.mcrcb.socket.service.AbstractDispatchService;

//import com.untech.socket.service.AbstractDispatchService;
//import com.untech.socket.support.ServiceFactoryBean;
//import com.untech.socket.support.SocketConstant;
//import com.untech.socket.util.MyArrayUtil;

/**
 * 短连接
 * 
 * @author wing
 */
public class ShortConnection extends Thread {
	
	Logger logger = LoggerFactory.getLogger(ShortConnection.class);
	
	private Socket clientSocket;
	private InetAddress inetAddress;
	private String requestIp;
	private InputStream in;
	private OutputStream out;
	
	private boolean flag = false;
	
	public ShortConnection(Socket socket) {
		try {
			this.clientSocket = socket;
			this.inetAddress = socket.getInetAddress();
			this.requestIp = this.inetAddress.getHostAddress();
			logger.info("new request in 12002--{}", this.requestIp);
			
			this.in = socket.getInputStream();
			this.out = socket.getOutputStream();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		byte[] response = null;
		try {
			this.clientSocket.setSoTimeout(3000000);
			response = this.requestHandlerBuff();
			
		} catch (Exception e) {
			// close socket when error
			this.closeSocket();
			e.printStackTrace();
		} finally {
			try {
				this.response(response);
				if(!this.flag) {
					this.closeSocket();
					return;
				}
				this.closeSocket();
			} catch (Exception e2) {
				e2.printStackTrace();
			} finally {
			}
		}
	}
	/**
	 * 读取socket客户端发送报文,并作业务处理
	 * 
	 * @Description: 读取socket客户端发送报文,并作业务处理
	 * @author jin.congshan
	 * @throws IOException 
	 * @throws ReadSocketRequestDataException 
	 * @throws CentralPlatformException 
	 * @throws MisPosServerException 
	 * @throws ConnectException 
	 */
	

	public byte[] requestHandlerBuff() throws Exception {
		int readNum;
		byte[] firstByte = new byte[1];
		readNum = this.in.read(firstByte);
		// 未读取到数据
		if(readNum < firstByte.length) {
			throw new Exception("read empty request");
		}

		byte[] request = null;
		String requestStr = null;
		String type = null;
		// 获取报文
		byte[] buff = new byte[1024];
		readNum = this.in.read(buff);
		Assert.isTrue(readNum > 0, "too short socket message");
		
		if(readNum > 1024) {
			
		} else {
			request = new byte[readNum + 1];
			request[0] = firstByte[0];
			System.arraycopy(buff, 0, request, 1, readNum);
		}		
		requestStr = new String(request,"gbk");
		logger.info("requestStr:{}", requestStr);
		/*requestStr = StringUtils.strip(requestStr, "|");
		System.out.println("requestStr--------"+requestStr);*/
		String[] req = requestStr.split("\\|",-1);
		type = req[1];
		AbstractDispatchService service = ServiceFactoryBean.getServiceBean(type, req);
		byte[] response = service.dispatch();
		return response;
	}
	/**
	 * @Description ：返回处理数据
	 * @author		：jin.congshan
	 * @date		：2014-9-28 上午11:10:21
	 * @param response
	 * @throws WriteResponseException
	 */
	private void response(byte[] response) throws Exception {
		this.out.write(response);
		this.out.flush();
	}

	
	/**
	 * 关闭socket连接
	 * 
	 * @author jin.congshan
	 */
	private void closeSocket() {
		try {
			if(null != this.out) {
				this.out.close();
			}
			if(null != this.in) {
				this.in.close();
			}
			if(null != this.clientSocket) {
				this.clientSocket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}