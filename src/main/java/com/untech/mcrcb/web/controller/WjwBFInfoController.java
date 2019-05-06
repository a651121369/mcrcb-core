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

import com.untech.mcrcb.web.service.WjwBFInfoService;
import com.unteck.common.dao.support.Pagination;

@Controller
@RequestMapping(value="/mcrcb-core")
public class WjwBFInfoController {
	
	@Autowired
	private WjwBFInfoService service;
	
	@RequestMapping(value = "/wjwBFInfo")
	public void openPage() {
	}
	
	@RequestMapping(value = "/getWjwBFInfo")
	@ResponseBody
	public Pagination<Map<String, Object>> getWjwBFInfo(Integer start, Integer limit,   
			 String beginDate,String endDate,String UNIT_NO) {
		return this.service.getWjwBFInfo(start, limit,beginDate, endDate, UNIT_NO);
	}
	
	@RequestMapping(value="/getWjwBFInfoDown")
	public void getWjwBFInfoDownMx(HttpServletResponse response, 
			String beginDate,String endDate,String UNIT_NO
			) throws IOException{
		String filename = "蒙城卫健委拨付明细_"+new SimpleDateFormat("yyyyMMdd").format(new Date());
		response.setHeader("Content-disposition", "attchment;filename="
		+new String(filename.getBytes("GBK"),"ISO_8859_1")+".xls");
		response.setContentType("application/vnd.ms-excel");
		OutputStream os = response.getOutputStream();
		InputStream is = this.service.getWjwBFInfoDownMx(beginDate, endDate, UNIT_NO);
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
