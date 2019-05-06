package com.untech.mcrcb.socket.service.impl;

import com.untech.mcrcb.socket.service.AbstractDispatchService;

public class QueryMainAccountDispatchService extends AbstractDispatchService{
	private String wjwCode;//卫计委编号
	private String inOrOut;//收入支出标志
	
	public QueryMainAccountDispatchService(String type, String[] request)
			throws Exception {
		super(type, request);
		this.wjwCode = request[0];
		this.inOrOut = request[2];
	}

	@Override
	public void handler() throws Exception {

		
	}

}
