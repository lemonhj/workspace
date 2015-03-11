package com.hlc.archivesmgmt.entity;

import java.sql.Date;


/**
 * 档案电子文件类
 * @author linbotao
 *
 */
public class ArchivesStoreFile {
	/*电子文件ID*/
	private Integer storeFileId;

	/*电子文件名称*/
	private String fileName;
	
	/*电子文件路径*/
	private String filePath;
	
	/*文件著录ID*/
	private FileDescription fileDescription;
	
	/*上传时间*/
	private Date uploadTime;
	
	public Integer getStoreFileId() {
		return storeFileId;
	}

	public void setStoreFileId(Integer storeFileId) {
		this.storeFileId = storeFileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public FileDescription getFileDescription() {
		return fileDescription;
	}

	public void setFileDescription(FileDescription fileDescription) {
		this.fileDescription = fileDescription;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	

}
