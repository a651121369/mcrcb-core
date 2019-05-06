package com.untech.mcrcb.web.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.untech.mcrcb.web.dao.DownloadBackupDao;
import com.untech.mcrcb.web.model.DownloadBackup;
import com.unteck.common.dao.support.Pagination;
import com.unteck.tpc.framework.core.util.SecurityContextUtil;
import com.unteck.tpc.framework.web.model.admin.User;

@Service
public class DownloadBackupService{
	@Autowired
    private DownloadBackupDao dao;
	
	public Pagination<Map<String,Object>> getAll(Integer start,Integer limit){
		return dao.getAll(start,limit);
	}
	
    public void insert(String time,String add,String fileName){
    	User user = SecurityContextUtil.getCurrentUser();
    	DownloadBackup rec = new DownloadBackup();
    	rec.setName(user.getOrgName());
    	rec.setTime(time);
    	rec.setAddress(add);
    	rec.setFileName(fileName);
  	  	dao.insert(rec);
    }
    
    
    //下载
  	public void download(HttpServletResponse response,Long id) throws IOException{
  		 Map map = (Map)this.dao.getSelect(id).get(0);
  	    String add = map.get("address").toString();
  	    String fileName = map.get("fileName").toString();

  	    response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

  	    InputStream is = new FileInputStream(add);
  	    OutputStream os = response.getOutputStream();
  	    byte[] buf = new byte[2048];
  	    try {
  	      while (is.read(buf) != -1) {
  	        os.write(buf);
  	        os.flush();
  	      }

  	      if (null != os) {
  	        os.close();
  	      }
  	      if (null != is){
  	        is.close();
  	      }
  	    }catch (Exception e){
  	      e.printStackTrace();

  	      if (null != os) {
  	        os.close();
  	      }
  	      if (null != is){
  	        is.close();
  	      }
  	    }
  	    finally{
  	      if (null != os) {
  	        os.close();
  	      }
  	      if (null != is){
  	        is.close();
  	      }
  	    }
  	}
}
