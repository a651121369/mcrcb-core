package com.untech.mcrcb.socket.service;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDispatchService implements DispatcherService{

	Logger logger = LoggerFactory.getLogger(AbstractDispatchService.class);
	
	protected String type;
	protected byte[] requestByte;
	protected String[] request;
	protected String[] response;
	protected byte[] responseByte;
	
	protected String responseCode;
	protected String responseMessage;
	
	public AbstractDispatchService(String type, String[] request) throws Exception{
		this.type = type;
		this.request = request;
	}
	@Override
	public byte[] dispatch() throws Exception {
		this.handler();
		this.assemblyResponseData();
		return this.responseByte;
	}
	private void assemblyResponseData() {
		String responseStr = "|" + StringUtils.join(response, "|") + "|";
		logger.info("responseStr:{}", responseStr);
		try {
			this.responseByte = responseStr.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			
		}
		
	}
	public abstract void handler() throws Exception;
}
