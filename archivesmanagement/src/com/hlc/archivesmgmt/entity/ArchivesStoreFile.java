package com.hlc.archivesmgmt.entity;

import java.sql.Date;


/**
 * ���������ļ���
 * @author linbotao
 *
 */
public class ArchivesStoreFile {
	/*�����ļ�ID*/
	private Integer storeFileId;

	/*�����ļ�����*/
	private String fileName;
	
	/*�����ļ�·��*/
	private String filePath;
	
	/*�ļ���¼ID*/
	private FileDescription fileDescription;
	
	/*�ϴ�ʱ��*/
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
