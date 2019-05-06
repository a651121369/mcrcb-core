package com.untech.mcrcb.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.untech.mcrcb.web.service.DownloadBackupService;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.ResponseData;
import com.unteck.tpc.framework.web.controller.BaseController;

@Controller
@RequestMapping(value = "/DownloadBackup")
public class DownloadBackupController extends BaseController{
	@Autowired
    private DownloadBackupService service;
	
	//打开页面
	@RequestMapping(value="/open")
    public ModelAndView open(){
    	return new ModelAndView("/mcrcb-core/DownloadBackup");
    }
	
	//获取数据
    @RequestMapping(value = "/pager")
    @ResponseBody
    public Pagination<Map<String,Object>> pager(Integer start,Integer limit){
    	return service.getAll(start,limit);
    }
	
	//备份
	@RequestMapping(value = "/Backup")
	@ResponseBody
	public ResponseData backup() throws IOException { 
        	String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        	String[] cmd =new String[] {
//        			"ln -s /usr/local/mysql/bin/mysqldump /usr/bin/mysqldump",
        			"/bin/sh", 
        			"-c",
        			"mysqldump -uroot -p123456 wjwdb | gzip > /home/back/" + time + "wjw_mc.sql.gz"
        			};
        	Process ps = Runtime.getRuntime().exec(cmd);
			// 取得命令结果的输出流
        	InputStream is = ps.getInputStream();
        	// 用一个读输出流类去读
        	InputStreamReader isr = new InputStreamReader(is);
        	// 用缓冲器读行
        	BufferedReader br = new BufferedReader(isr);
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				sb.append(line);
			}
			is.close();
			isr.close();
			br.close();
            
			service.insert(time,"/home/back/" + time + "wjw_mc.sql.gz","" + time + "wjw_mc.sql.gz");
            return ResponseData.SUCCESS_NO_DATA;
    }  
	//下载
	@RequestMapping(value = "/download")
	public void download(HttpServletResponse response,Long id) throws IOException{
		service.download(response,id);
	}
	
}
