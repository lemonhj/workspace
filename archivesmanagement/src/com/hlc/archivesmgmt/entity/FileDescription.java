package com.hlc.archivesmgmt.entity;

import java.sql.Date;
import java.util.Set;

/**
 * 文件著录类
 * @author Administrator
 *
 */
public class FileDescription{

	/*文件ID*/
	private Integer fileId;
	
	/*文件名称*/
	private String fileName;	
	
	/*档案类别号*/
	private String categoryCode;
	
	/*责任人*/
	private String personInCharge;
	
	/*文号*/
	private String fileCode;
	
	/*已经归档*/
	private Integer archived;
	
	/*成文日期*/
	private Date writtenDate;
	
	/*密级*/
	private Integer securityLevel;
	
	/*登记日期*/
	private Date registrationTime;
	
	/*保管期限*/
	private String retentionPeriod;
	
	/*存放方式*/
	private Integer storeWay;
	
	/*页数*/
	private Integer pages;
	
	/*备注*/
	private String remarks;
	
	/*属于档案 */
	private Archive archive;
	
	/*所属文档存放路径*/
	private String filePath;
	
	/*归档号*/
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
