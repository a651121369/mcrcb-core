package com.untech.mcrcb.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
/**
 * @des 对ftp服务器进行文件上传、下载、删除操作
 * @author WayneWong
 *
 */
public class FtpClientUtil {
	/** 
     * Description: 向FTP服务器上传文件 
     * @param url FTP服务器hostname 
     * @param port FTP服务器端口，如果默认端口请写-1 
     * @param username FTP登录账号 
     * @param password FTP登录密码 
     * @param path FTP服务器保存目录 
     * @param filename 上传到FTP服务器上的文件名 
     * @param input 输入流 
     * @return 成功返回true，否则返回false 
     */  
	public static boolean uploadFile(String url, int port, String username, String password, String path, String filename, InputStream input) {  
			boolean success = false;  
			FTPClient ftp = new FTPClient();  
			try{  
				int reply;  
				// 连接FTP服务器  
				if (port > -1){  
					ftp.connect(url, port);  
				} else{  
					ftp.connect(url);  
				}  
				// 登录FTP  
				ftp.login(username, password);  
				reply = ftp.getReplyCode();  
				if (!FTPReply.isPositiveCompletion(reply)){  
					ftp.disconnect();  
					return success;  
				}  
				ftp.changeWorkingDirectory(path);  
				ftp.storeFile(filename, input);  
				input.close();  
				ftp.logout();  
				success = true;  
			} catch (IOException e){  
				success = false;  
			}finally{  
				if (ftp.isConnected()){  
						try {
							ftp.disconnect();
						} catch (IOException e) {
							success = false; 
						}  
				}  
			}  
			return success;  
	}  

	/** 
     * Description: 从FTP服务器下载文件 
     * @param url FTP服务器hostname 
     * @param port FTP服务器端口 
     * @param username FTP登录账号 
     * @param password FTP登录密码 
     * @param remotePath FTP服务器上的相对路径 
     * @param fileName 要下载的文件名 
     * @param localPath 下载后保存到本地的路径 
     * @return 
     */  
    public static boolean downloadFile(String url, int port, String username, String password, String remotePath, String fileName, String localPath){  
	        boolean success = false;  
	        FTPClient ftp = new FTPClient();  
	        try {  
	            int reply;  
	            // 连接FTP服务器  
	            if (port > -1) {  
	                ftp.connect(url, port);  
	            } else{  
	                ftp.connect(url);  
	            }  
	
	            ftp.login(username, password);//登录  
	            reply = ftp.getReplyCode();  
	            if (!FTPReply.isPositiveCompletion(reply)){  
	                ftp.disconnect();  
	                return success;  
	            }  
	            ftp.changeWorkingDirectory(remotePath);//转移到FTP服务器目录  
	            FTPFile[] fs = ftp.listFiles();  
	            //判断下载路径是否存在，不存在则创建
	            File downPath= new File(localPath);
	            if(!downPath.exists()){
	            	downPath.mkdirs();
	            }
	            for (FTPFile ff : fs) {  
	                if (ff.getName().equals(fileName)) {  
	                    File localFile = new File(localPath +File.separator+ ff.getName());  
	                    OutputStream is = new FileOutputStream(localFile);  
	                    ftp.retrieveFile(ff.getName(), is);  
	                    is.close();  
	                }  
	            }  
	            ftp.logout();  
	            success = true;  
	        } catch (IOException e){  
	        	System.out.println("=========="+e.getMessage());
	        	success = false;
	        }finally{  
	            if (ftp.isConnected()) {  
	                try{  
	                    ftp.disconnect();  
	                } catch (IOException e){  
	                	System.out.println("-------------"+e.getMessage());
	                	success = false;
	                }  
	            }  
	        }  
	        return success;  
    } 
    
    
    /** 
     * 删除FTP上的文件
     * @param url FTP服务器IP地址 
     * @param port FTP服务器端口 
     * @param username FTP服务器登录名 
     * @param password FTP服务器密码 
     * @param remotePath 远程文件路径 
     * @param fileName 待删除的文件名 
     * @return 
     */  
    public static boolean deleteFtpFile(String url, int port, String username, String password, String remotePath, String fileName){  
	        boolean success = false;  
	        FTPClient ftp = new FTPClient();  
	        try{  
	            int reply;  
	            // 连接FTP服务器  
	            if (port > -1){  
	                ftp.connect(url, port);  
	            }else{  
	                ftp.connect(url);  
	            }  
	            // 登录  
	            ftp.login(username, password);  
	            reply = ftp.getReplyCode();  
	            if (!FTPReply.isPositiveCompletion(reply)) {  
	                ftp.disconnect();  
	                return success;  
	            }  
	            // 转移到FTP服务器目录  
	            ftp.changeWorkingDirectory(remotePath);  
	            success = ftp.deleteFile(remotePath + "/" + fileName);  
	            ftp.logout();  
	        } catch (IOException e) {  
	            success = false;  
	        }finally{  
	            if (ftp.isConnected()){  
	                try {  
	                    ftp.disconnect();  
	                } catch (IOException e){
	                	success =false;
	                }  
	            }  
	        }  
	        return success;  
    }
}
