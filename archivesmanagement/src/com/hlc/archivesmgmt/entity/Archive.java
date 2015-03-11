package com.hlc.archivesmgmt.entity;

/**
 * 档案类
 * @author linbotao
 *
 */
public class Archive {
	
	/*档案ID*/
	private Integer archiveId;
	
	/*盒号，导航到目录*/
	private String directoryId;
	
	/*档案类别号*/
	private String categoryCode;
	
	/*归档年度*/
	private String archivedYear;
	
	/*保管期限*/
	private String retentionPeriod;
	
	/*备注*/
	private String remarks;
	
	/*文档详细地址*/
	private String locationStr;
	
	/*档案类型*/
	private ArchivesCategory archivesCategory;
	
	/*已经归档*/
	private Integer archived;
	
	/*档案位置*/
	private ArchivesLocation archivesLocation;
	

	/*文档包含的所有文件号*/
	private String fileCodes;
	
	public String getFileCodes() {
		return fileCodes;
	}

	public void setFileCodes(String fileCodes) {
		this.fileCodes = fileCodes;
	}

	public Integer getArchiveId() {
		return archiveId;
	}

	public void setArchiveId(Integer archiveId) {
		this.archiveId = archiveId;
	}
	public String getRetentionPeriod() {
		return retentionPeriod;
	}

	public void setRetentionPeriod(String retentionPeriod) {
		this.retentionPeriod = retentionPeriod;
	}
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getArchived() {
		return archived;
	}

	public void setArchived(Integer archived) {
		this.archived = archived;
	}

	public ArchivesCategory getArchivesCategory() {
		return archivesCategory;
	}

	public void setArchivesCategory(ArchivesCategory archivesCategory) {
		this.archivesCategory = archivesCategory;
	}

	public ArchivesLocation getArchivesLocation() {
		return archivesLocation;
	}

	public void setArchivesLocation(ArchivesLocation archivesLocation) {
		this.archivesLocation = archivesLocation;
	}

	public String getArchivedYear() {
		return archivedYear;
	}

	public void setArchivedYear(String archivedYear) {
		this.archivedYear = archivedYear;
	}

	public String getLocationStr() {
		return locationStr;
	}

	public void setLocationStr(String locationStr) {
		this.locationStr = locationStr;
	}

	public String getDirectoryId() {
		return directoryId;
	}

	public void setDirectoryId(String directoryId) {
		this.directoryId = directoryId;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	
}
