package com.untech.mcrcb.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.untech.mcrcb.balance.service.CronBalanceDispatchService;

@Controller
@RequestMapping(value="/WjwPaymain")
public class BalanceTestController{
	@Autowired
	private CronBalanceDispatchService service;
	@RequestMapping(value="/balance")
	public void testBalance(){
		System.out.println("=========test==========");
		service.dispatch();
	}
}
