package com.hlc.archivesmgmt.struts.action;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hlc.archivesmgmt.entity.ArchivesStoreFile;
import com.hlc.archivesmgmt.service.StoreFileService;
import com.opensymphony.xwork2.ActionSupport;


/**
 * 电子文件操作Action
 * @author linbotao
 *
 */
@Controller
@Scope("prototype")
public class StoreFileAction extends ActionSupport{

	/** 
	* @Fields serialVersionUID : 序列化串行ID
	*/ 
	private static final long serialVersionUID = -8883007201782037700L;
	
	@Resource
	private StoreFileService sfs;
	
	private List<ArchivesStoreFile> asfList;
	
	/*删除文件的id*/
	private String delNum;
	
	/**
	 * 所属文件id
	 */
	private String fileId;

	private boolean operateState;
	
	private String fileName;
	
	/**
	 * 加载上传文件
	* @Title: loadStoreFile 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String loadStoreFile(){
		asfList = sfs.findStoreFileById(Integer.parseInt(fileId));
		return SUCCESS;
	}
	
	/**
	 * 删除上传文件
	* @Title: delStoreFile 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String delStoreFile(){
		try{
			sfs.delStoreFileById(Integer.parseInt(delNum));
		}catch(Exception e){
			operateState = false;
		}
		operateState = true;
		return SUCCESS;
	}
	
	public String downloadFile(){
		return SUCCESS;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public List<ArchivesStoreFile> getAsfList() {
		return asfList;
	}

	public void setAsfList(List<ArchivesStoreFile> asfList) {
		this.asfList = asfList;
	}

	public String getDelNum() {
		return delNum;
	}

	public void setDelNum(String delNum) {
		this.delNum = delNum;
	}

	public boolean isOperateState() {
		return operateState;
	}

	public void setOperateState(boolean operateState) {
		this.operateState = operateState;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	
	
}
