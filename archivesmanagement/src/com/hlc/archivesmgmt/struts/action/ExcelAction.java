package com.hlc.archivesmgmt.struts.action;

import java.io.InputStream;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hlc.archivesmgmt.service.ExcelService;
import com.hlc.archivesmgmt.service.impl.ExcelServiceImpl;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 导出excelAction
* @ClassName: ExcelAction 
* @author linbotao 
* @date 2014年9月4日 下午2:21:19 
*
 */
@Controller
@Scope("prototype")
public class ExcelAction extends ActionSupport {

	/** 
	* @Fields serialVersionUID : 序列化ID
	*/ 
	private static final long serialVersionUID = -5183993964454998490L;
	
	private String fileName;
	
	InputStream excelStream;  
	
	private String archiveId;
	
	@Resource
	private ExcelService excelService;
    
    public String execute(){  
    	
        excelStream = excelService.getExcelInputStream(archiveId);  
        return "excel";  
    }

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getArchiveId() {
		return archiveId;
	}

	public void setArchiveId(String archiveId) {
		this.archiveId = archiveId;
	} 
	
	
}
