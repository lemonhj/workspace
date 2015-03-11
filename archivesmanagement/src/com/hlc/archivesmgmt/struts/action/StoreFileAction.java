package com.hlc.archivesmgmt.struts.action;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hlc.archivesmgmt.entity.ArchivesStoreFile;
import com.hlc.archivesmgmt.service.StoreFileService;
import com.opensymphony.xwork2.ActionSupport;


/**
 * �����ļ�����Action
 * @author linbotao
 *
 */
@Controller
@Scope("prototype")
public class StoreFileAction extends ActionSupport{

	/** 
	* @Fields serialVersionUID : ���л�����ID
	*/ 
	private static final long serialVersionUID = -8883007201782037700L;
	
	@Resource
	private StoreFileService sfs;
	
	private List<ArchivesStoreFile> asfList;
	
	/*ɾ���ļ���id*/
	private String delNum;
	
	/**
	 * �����ļ�id
	 */
	private String fileId;

	private boolean operateState;
	
	private String fileName;
	
	/**
	 * �����ϴ��ļ�
	* @Title: loadStoreFile 
	* @param @return    �趨�ļ� 
	* @return String    �������� 
	* @throws
	 */
	public String loadStoreFile(){
		asfList = sfs.findStoreFileById(Integer.parseInt(fileId));
		return SUCCESS;
	}
	
	/**
	 * ɾ���ϴ��ļ�
	* @Title: delStoreFile 
	* @param @return    �趨�ļ� 
	* @return String    �������� 
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
