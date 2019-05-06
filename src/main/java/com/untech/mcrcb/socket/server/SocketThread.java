package com.untech.mcrcb.socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * Socket服务端线程
 * 
 * @author bajnokw
 * 
 */
public class SocketThread extends Thread {
	Logger logger = LoggerFactory.getLogger(SocketThread.class);
	private ServerSocket serverSocket = null;

	public SocketThread(ShortConnection serverScoket) {
		try {
			ClassPathResource resource = new ClassPathResource("META-INF/res/socket.properties");
			Properties properties = PropertiesLoaderUtils.loadProperties(resource);
			String socketServerPort = properties.getProperty("socket.local.prot").trim();
			if (null == serverSocket) {
				int port = Integer.parseInt(socketServerPort);
				this.serverSocket = new ServerSocket(port);
				logger.info("【SocketThread】Socket服务端启动，开始监听端口：" + port);
			}
		} catch (Exception e) {
			logger.error("【SocketThread】创建socket服务出错:" + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (!this.isInterrupted()) {
			try {
				Socket socket = serverSocket.accept();
				if (null != socket && !socket.isClosed()) {
					new ShortConnection(socket).start();
				}
				socket.setSoTimeout(30000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void closeSocketServer() {
		try {
			if (null != serverSocket && !serverSocket.isClosed()) {
				serverSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
