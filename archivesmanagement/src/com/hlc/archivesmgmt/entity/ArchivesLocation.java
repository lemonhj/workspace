package com.hlc.archivesmgmt.entity;

import java.sql.Date;
import java.util.Set;

/**
 * 档案位置类
 * @author Administrator
 *
 */
public class ArchivesLocation {
	/*位置ID*/
	private Integer locationId;

	/*位置名*/
	private String locationName;
	
	/*父节点ID*/
	private Integer parentId;
	
	/*目录类型*/
	private Integer directoryType;
	
	/*图片类型*/
	private Integer categoryView;
	
	/*创建时间*/
	private Date createTime;
	
	/*位置描述*/
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
