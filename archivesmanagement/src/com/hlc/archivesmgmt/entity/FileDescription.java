package com.hlc.archivesmgmt.entity;

import java.sql.Date;
import java.util.Set;

/**
 * �ļ���¼��
 * @author Administrator
 *
 */
public class FileDescription{

	/*�ļ�ID*/
	private Integer fileId;
	
	/*�ļ�����*/
	private String fileName;	
	
	/*��������*/
	private String categoryCode;
	
	/*������*/
	private String personInCharge;
	
	/*�ĺ�*/
	private String fileCode;
	
	/*�Ѿ��鵵*/
	private Integer archived;
	
	/*��������*/
	private Date writtenDate;
	
	/*�ܼ�*/
	private Integer securityLevel;
	
	/*�Ǽ�����*/
	private Date registrationTime;
	
	/*��������*/
	private String retentionPeriod;
	
	/*��ŷ�ʽ*/
	private Integer storeWay;
	
	/*ҳ��*/
	private Integer pages;
	
	/*��ע*/
	private String remarks;
	
	/*���ڵ��� */
	private Archive archive;
	
	/*�����ĵ����·��*/
	private String filePath;
	
	/*�鵵��*/
	private String code;

	private Set<ArchivesStoreFile> archivesStoreFileSet;
	
	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPersonInCharge() {
		return personInCharge;
	}

	public void setPersonInCharge(String personInCharge) {
		this.personInCharge = personInCharge;
	}

	public String getFileCode() {
		return fileCode;
	}

	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}

	public Integer getArchived() {
		return archived;
	}

	public void setArchived(Integer archived) {
		this.archived = archived;
	}

	public Date getWrittenDate() {
		return writtenDate;
	}

	public void setWrittenDate(Date writtenDate) {
		this.writtenDate = writtenDate;
	}

	public Integer getSecurityLevel() {
		return securityLevel;
	}

	public void setSecurityLevel(Integer securityLevel) {
		this.securityLevel = securityLevel;
	}

	public Date getRegistrationTime() {
		return registrationTime;
	}

	public void setRegistrationTime(Date registrationTime) {
		this.registrationTime = registrationTime;
	}

	public String getRetentionPeriod() {
		return retentionPeriod;
	}

	public void setRetentionPeriod(String retentionPeriod) {
		this.retentionPeriod = retentionPeriod;
	}

	public Integer getStoreWay() {
		return storeWay;
	}

	public void setStoreWay(Integer storeWay) {
		this.storeWay = storeWay;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Archive getArchive() {
		return archive;
	}

	public void setArchive(Archive archive) {
		this.archive = archive;
	}

	public Set<ArchivesStoreFile> getArchivesStoreFileSet() {
		return archivesStoreFileSet;
	}

	public void setArchivesStoreFileSet(Set<ArchivesStoreFile> archivesStoreFileSet) {
		this.archivesStoreFileSet = archivesStoreFileSet;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}


	
	
}
