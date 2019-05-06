package com.untech.mcrcb.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.untech.mcrcb.web.service.WjwAccMxInfoService;
import com.unteck.common.dao.support.Pagination;

@Controller
@RequestMapping(value="/mcrcb-core")
public class WjwAccMxInfoController {
	@Autowired
	private WjwAccMxInfoService service;
	
	@RequestMapping(value = "/wjwAccMxInfo")
	public void openPage() {
	}
	
	@RequestMapping(value = "/getWjwAccMxInfo")
	@ResponseBody
	public Pagination<Map<String, Object>> getWjwAccMxInfo(Integer start, Integer limit,   
			 String UNIT_NO,String accCode) {
		return this.service.getWjwAccMxInfo(start, limit, UNIT_NO,accCode);
	}
	
	
	@RequestMapping(value="/getWjwAccMxInfoDown")
	public void getWjwAccMxInfoDownMx(HttpServletResponse response, 
			String UNIT_NO
			) throws IOException{
		String filename = "蒙城卫健委明细汇总_"+new SimpleDateFormat("yyyyMMdd").format(new Date());
		response.setHeader("Content-disposition", "attchment;filename="
		+new String(filename.getBytes("GBK"),"ISO_8859_1")+".xls");
		response.setContentType("application/vnd.ms-excel");
		OutputStream os = response.getOutputStream();
		InputStream is = this.service.getWjwAccMxInfoDownMx(UNIT_NO);
		byte[] buf = new byte[2048];
		try {
			while((is.read(buf))!=-1){
				os.write(buf);
				os.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();		
		}finally{
			if(null!=os){
				os.close();
			}
			if(null!=is){
				is.close();
			}
		}
	}

}
