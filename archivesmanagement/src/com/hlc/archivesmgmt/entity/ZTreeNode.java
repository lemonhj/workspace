package com.hlc.archivesmgmt.entity;

import java.sql.Date;

/**
 * Ê÷½Úµã
 * @author linbotao
 *
 */
public class ZTreeNode {
	private Object id;
	private String name;
	private Object pId;
	private String remark;
	private String code;
	private String locationStr;
	private String icon;
	private Object viewtype;
	
	private boolean isParent=true; 
	
	private boolean open;
	
	private Date createTime;
	
	public ZTreeNode(){
		
	}
	public ZTreeNode(Object id, String name, Object parentId,Date createTime,boolean open,boolean isParent){
		this.id=id;
		this.name=name;
		this.pId=parentId;
		this.open=open;
		this.isParent=isParent;
		this.createTime=createTime;
	}
	
	public ZTreeNode(Object id, String name, Object parentId,boolean open,boolean isParent,String remark){
		this.id=id;
		this.name=name;
		this.pId=parentId;
		this.open=open;
		this.isParent=isParent;
		this.remark = remark;
	}
	
	public ZTreeNode(Object id, String name, Object parentId,Date createTime,String locationStr,String icon,boolean open,boolean isParent){
		this.id=id;
		this.name=name;
		this.pId=parentId;
		this.open=open;
		this.isParent=isParent;
		this.createTime=createTime;
		this.locationStr=locationStr;
		this.icon=icon;
	}
	
	public ZTreeNode(Object id, String name, Object parentId,Date createTime,String locationStr,Object viewType,String icon,boolean open,boolean isParent){
		this.id=id;
		this.name=name;
		this.pId=parentId;
		this.open=open;
		this.isParent=isParent;
		this.createTime=createTime;
		this.locationStr=locationStr;
		this.icon=icon;
		this.viewtype=viewType;
	}
	
	public ZTreeNode(Object id, String name, Object parentId,Date createTime,String icon,boolean open,boolean isParent,String code){
		this.id=id;
		this.name=name;
		this.pId=parentId;
		this.open=open;
		this.isParent=isParent;
		this.createTime=createTime;
		this.icon=icon;
		this.code = code;
	}
	
	public Object getId() {
		return id;
	}
	public void setId(Object id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Object getpId() {
		return pId;
	}
	public void setpId(Object parentId) {
		this.pId = parentId;
	}

	public boolean isIsParent() {
		return isParent;
	}
	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Object getViewtype() {
		return viewtype;
	}
	public void setViewtype(Object viewtype) {
		this.viewtype = viewtype;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	
	
	
	
	
	
}