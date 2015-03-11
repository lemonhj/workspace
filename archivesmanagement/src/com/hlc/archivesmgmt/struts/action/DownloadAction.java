package com.hlc.archivesmgmt.struts.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 下载操作action
* @ClassName: DownloadAction 
* @author linbotao
* @date 2014年9月3日 上午10:25:52 
*
 */
@Controller
@Scope("prototype")
public class DownloadAction extends ActionSupport {

	/** 
	* @Fields serialVersionUID : 串行id
	*/ 
	private static final long serialVersionUID = 1498205329984916028L;
	
	private String fileName;
    
    public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
            this.fileName = fileName;
    }
	
    public InputStream getInputStream() {
    	try{
    		return ServletActionContext.getServletContext().getResourceAsStream(new String(fileName.getBytes("ISO-8859-1"),"utf-8"));
    	}catch(Exception e){
    		return null;
    	}
    }
   
    public String execute(){
            return "success";
    }
}
