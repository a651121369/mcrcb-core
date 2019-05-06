package com.untech.mcrcb.socket.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author bajnokw
 * 
 */
public class InitDataServer implements ServletContextListener {
	
	Logger logger = LoggerFactory.getLogger(InitDataServer.class);
	// socket server 线程
	private SocketThread socketThread;

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			// new MyServer();
			if (null == socketThread) {
				// 新建线程类
				logger.info("【InitDataServer】新建线程类");
				System.out.println("====first step======");
				socketThread = new SocketThread(null);
				// 启动线程
				logger.info("【InitDataServer】启动线程");
				socketThread.start();
			}
		} catch (Exception e) {
			logger.error("【InitDataServer】启动Socket服务器端异常：" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		if (null != socketThread && !socketThread.isInterrupted()) {
			socketThread.closeSocketServer();
			socketThread.interrupt();
		}
	}
}
