package com.hlc.archivesmgmt.entity;

/**
 * ������
 * @author linbotao
 *
 */
public class Archive {
	
	/*����ID*/
	private Integer archiveId;
	
	/*�кţ�������Ŀ¼*/
	private String directoryId;
	
	/*��������*/
	private String categoryCode;
	
	/*�鵵���*/
	private String archivedYear;
	
	/*��������*/
	private String retentionPeriod;
	
	/*��ע*/
	private String remarks;
	
	/*�ĵ���ϸ��ַ*/
	private String locationStr;
	
	/*��������*/
	private ArchivesCategory archivesCategory;
	
	/*�Ѿ��鵵*/
	private Integer archived;
	
	/*����λ��*/
	private ArchivesLocation archivesLocation;
	

	/*�ĵ������������ļ���*/
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
