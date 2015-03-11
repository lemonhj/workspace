package com.hlc.archivesmgmt.entity;

import java.sql.Date;

/**
 * 档案类型实体类
 * @author linbotao
 *
 */
public class ArchivesCategory {
	
	/*类型ID*/
	private Integer categoryId;

	/*类型名*/
	private String categoryName;
	
	/*父节点ID*/
	private Integer parentId;
	
	/*图片类型*/
	private Integer categoryView;
	
	/*类型编号*/
	private String categoryCode;
	
	/*创建时间*/
	private Date createTime;
	
	/*层级导航编码*/
	private String naviCode;


	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
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

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getNaviCode() {
		return naviCode;
	}

	public void setNaviCode(String naviCode) {
		this.naviCode = naviCode;
	}

	
}
