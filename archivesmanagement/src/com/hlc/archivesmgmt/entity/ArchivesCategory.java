package com.hlc.archivesmgmt.entity;

import java.sql.Date;

/**
 * ��������ʵ����
 * @author linbotao
 *
 */
public class ArchivesCategory {
	
	/*����ID*/
	private Integer categoryId;

	/*������*/
	private String categoryName;
	
	/*���ڵ�ID*/
	private Integer parentId;
	
	/*ͼƬ����*/
	private Integer categoryView;
	
	/*���ͱ��*/
	private String categoryCode;
	
	/*����ʱ��*/
	private Date createTime;
	
	/*�㼶��������*/
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
