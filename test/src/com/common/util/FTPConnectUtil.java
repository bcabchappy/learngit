package com.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.CopyStreamListener;

public class FTPConnectUtil implements CopyStreamListener {

	public final static String SHARE_DATA = ConfigProperties.getByKey("ftp.shareData.dir");
	
	private FTPClient ftp = new FTPClient();

	private String rootDir = "";

	private String server = "";
	private int port = -1;
	private String username = "";
	private String password = "";

	{
		server = ConfigProperties.getByKey("ftp.config.server");
		port = Integer.parseInt(ConfigProperties.getByKey("ftp.config.port"));
		username = ConfigProperties.getByKey("ftp.config.username");
		password = ConfigProperties.getByKey("ftp.config.password");
	}
	
	public FTPConnectUtil(String dir){
		this.rootDir = changeISOEncode(dir);
		boolean flag = connectFTPServer();
		try {
			if(!ftp.changeWorkingDirectory("/"+this.rootDir)){
				try {
					ftp.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		ftp.setBufferSize(1024);
//		ftp.setControlKeepAliveTimeout(600000);
//		ftp.setControlKeepAliveReplyTimeout(600000);
		// Use passive mode as default because most of us are behind firewalls these days.
		ftp.enterLocalPassiveMode();
		try {
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		ftp.setCopyStreamListener(this);
	}
	
	
	/**
	 * 创建ftp连接
	 * @return
	 */
	public boolean connectFTPServer() {
		boolean login = false;
		ftp = new FTPClient();
		try {
			int reply;
			if (port > 0) {
				ftp.connect(server, port);
			} else {
				ftp.connect(server);
			}
			// 进行连接测试
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return login;
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//当重试5次都连接不上时。返回false;
		int time = 5;
__main: try {
			if (!ftp.login(username, password)) {
				if (time <= 0) {
					return login;
				}
				ftp.logout();
				time--;
				break __main;
			}else{
				login = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//断开连接
		if(!login){
			try {
				ftp.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return login;
	}
	
	/**
	 * 上传文件到FTP服务器
	 * @param remoteFile 服务器相对根目录的路径
	 * @param is 上传文件流
	 * @return String 返回上传后的文件名 上传失败则返回空字符串
	 */
	public String upload(String remoteFile,InputStream is){
		return upload(remoteFile, is,false);
	}
	/**
	 * 上传文件到FTP服务器
	 * @param remoteFile 服务器相对根目录的路径
	 * @param is 上传文件流
	 * @param isCover 上服务器上存在相同文件时，是否执行覆盖
	 * @return String 返回上传后的文件名(仅文件名)
     *                上传失败则返回null
	 */
	public String upload(String remoteFile,InputStream is,boolean isCover){
		try {
			if(is == null){
				return null;
			}
			//remoteFile = "/upload/test";
			remoteFile = filterFTPFile(remoteFile);
			//remoteFile = changeISOEncode(remoteFile);//转码
			//创建文件夹
			String dir[] = remoteFile.split("/");
			//检查并生成目录
			String tmp = "";
			for(int i = 0 ; i < dir.length-1;i++){
				if(i == 0){
					tmp = dir[i];
				}else{
					tmp = tmp + "/" + dir[i];
				}
				if (!checkFileExit(tmp)) {
					ftp.makeDirectory(changeISOEncode(tmp));
				} 
			}
			/*if(StringUtils.isNotBlank(tmp)){
				ftp.changeWorkingDirectory(tmp);
			}*/
			if(!isCover){
				//将中文转成英文名称,并检查文件是否存在
				int index = remoteFile.lastIndexOf(".");
				String fileType = remoteFile.substring(index);
				remoteFile = remoteFile.substring(0,index);
				tmp = remoteFile + fileType;
				int il = 0;
				do{
					if(il > 0){
						delete(tmp);
					}else{
						tmp = remoteFile + fileType;
					}
					il++;
				}
				while(this.checkFileExit(tmp));
				
				remoteFile = tmp;
			}

			ftp.storeFile(changeUTF8Encode(remoteFile), is);
			if(is != null){
				try {
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return tmp.substring(tmp.lastIndexOf("/")+1);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 下载FTP文件到指定的流
	 * @param remoteFile 服务器相对根目录的路径
	 * @param os
	 */
	public boolean download(String remoteFile,OutputStream os){
		try {
			boolean flag = false;
			remoteFile = filterFTPFile(remoteFile);
			if(checkFileExit(remoteFile)){
				ftp.retrieveFile(changeUTF8Encode(remoteFile), os);
				flag = true;
			}
			else{
				flag = false;
				if(os != null){
					os.close();
				}
			}
			return flag;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 删除FTP服务器上的文件
	 * @param remoteFile
	 */
	public boolean delete(String remoteFile){
		try {
			boolean flag = false;
			remoteFile = filterFTPFile(remoteFile);
			if(checkFileExit(remoteFile)){
				ftp.deleteFile(changeUTF8Encode(remoteFile));
				flag = true;
			}else{
				//不存在也返回true，反正也不存在
				flag = true;
			}
			return flag;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 判断    ----指定文件(包括文件夹)是否存在
	 * @param remoteFile
	 * @return
	 */
	public boolean checkFileExit(String remoteFile){
		boolean flag = true;
		try {
			remoteFile = this.filterFTPFile(remoteFile);
			FTPFile[] ss = ftp.listFiles(changeUTF8Encode(remoteFile));
			if(ss!= null && ss.length>0){
				flag = true;
			}else{
				flag = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 过滤文件路径
	 * @param str
	 * @return
	 */
	private String filterFTPFile(String str){
		if(str==null){
			return "";
		}
		str.replace("\\", "/");
		str.replace("//", "/");
		if(str.startsWith("/")){
			str = str.substring(1);
		}
		String ss[] = str.split("/");
		String tmp = "";
		for(int i = 0 ; i < ss.length;i++){
			if(i == 0){
				tmp = ss[i].trim();
			}else{
				tmp = tmp + "/" + ss[i].trim();
			}
		}
		return tmp;
	}
	
	
	/**
	 * 将 GBK 转成 ISO-8859-1 编码
	 * @param str
	 * @return
	 */
	private static String changeISOEncode(String str){
		try {
			str = new String(str.getBytes("GBK"),"ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	/**
	 * 将 UTF-8 转成 ISO-8859-1 编码
	 * @param str
	 * @return
	 */
	private static String changeUTF8Encode(String str){
		try {
			str = new String(str.getBytes("UTF-8"),"ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	/**
	 * 将 ISO-8859-1转成   UTF-8编码
	 * @param str
	 * @return
	 */
	private static String changeISO1Encode(String str){
		try {
			str = new String(str.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 将 ISO-8859-1 转成 GBK 编码
	 * @param str
	 * @return
	 */
	private static String changeGBKEncode(String str){
		try {
			str = new String(str.getBytes("iso-8859-1"),"GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 获取当前根目录
	 * @return
	 */
	private String getRootDir(){
		return changeGBKEncode(this.rootDir);
	}
	
	public void close() {
		try {
			if (ftp != null){
				ftp.disconnect();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String s[]){
		
	}

	public void bytesTransferred(CopyStreamEvent event) {
		bytesTransferred(event.getTotalBytesTransferred(), event.getBytesTransferred(), event.getStreamSize());
	}

	public void bytesTransferred(long total, int bytes, long streamSize) {
		// TODO Auto-generated method stub
		
	}
}