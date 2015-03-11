package com.honglicheng.dev.sys.model;

import java.util.List;


/**
 * 菜单类
 */
public class SysMenu implements Comparable<SysMenu>{
	private Integer menuId;
	private String menuIcon;
	private String menuName;
	private Integer parentMId;
	private String menuUrl;
	private String menuDesc;
	private List<SysMenu> children;
	
	
	public String getMenuDesc() {
		return menuDesc;
	}
	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getMenuIcon() {
		return menuIcon;
	}
	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public Integer getParentMId() {
		return parentMId;
	}
	public void setParentMId(Integer parentMId) {
		this.parentMId = parentMId;
	}
	public List<SysMenu> getChildren() {
		return children;
	}
	public void setChildren(List<SysMenu> children) {
		this.children = children;
	}
	@Override
	public int compareTo(SysMenu o) {
		return menuId-o.getMenuId();
	}
	
			
}

