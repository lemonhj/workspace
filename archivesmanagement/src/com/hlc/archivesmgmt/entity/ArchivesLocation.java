package com.hlc.archivesmgmt.entity;

import java.sql.Date;
import java.util.Set;

/**
 * ����λ����
 * @author Administrator
 *
 */
public class ArchivesLocation {
	/*λ��ID*/
	private Integer locationId;

	/*λ����*/
	private String locationName;
	
	/*���ڵ�ID*/
	private Integer parentId;
	
	/*Ŀ¼����*/
	private Integer directoryType;
	
	/*ͼƬ����*/
	private Integer categoryView;
	
	/*����ʱ��*/
	private Date createTime;
	
	/*λ������*/
	private String locationStr;
	
	//private String naviStr;
	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getDirectoryType() {
		return directoryType;
	}

	public void setDirectoryType(Integer directoryType) {
		this.directoryType = directoryType;
	}

	public Integer getCategoryView() {
		return categoryView;
	}

	public void setCategoryView(Integer categoryView) {
		this.categoryView = categoryView;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLocationStr() {
		return locationStr;
	}

	public void setLocationStr(String locationStr) {
		this.locationStr = locationStr;
	}
	
	
	
}
