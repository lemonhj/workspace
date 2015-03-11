package com.honglicheng.dev.sys.model.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.honglicheng.dev.sys.model.SysMenu;

/**
 * 角色
 */
public class Role {
	private Integer id;
	private String roleName = "未命名";
	private String roleDesc;
	private String roleValue ;
	private String menuRights;
	private String commIds;
	private Integer operator;
	
	/* 权限集合 */
	private Set<Right> rights = new HashSet<Right>();
	
	private Set<SysMenu> sysMenus = new HashSet<SysMenu>();
	
	public Set<Right> getRights() {
		return rights;
	}
	public void setRights(Set<Right> rights) {
		this.rights = rights;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public String getRoleValue() {
		return roleValue;
	}
	public void setRoleValue(String roleValue) {
		this.roleValue = roleValue;
	}
	public String getMenuRights() {
		return menuRights;
	}
	public void setMenuRights(String menuRights) {
		this.menuRights = menuRights;
	}
	public Set<SysMenu> getSysMenus() {
		return sysMenus;
	}
	public void setSysMenus(Set<SysMenu> sysMenus) {
		this.sysMenus = sysMenus;
	}
	public String getCommIds() {
		return commIds;
	}
	public void setCommIds(String commIds) {
		this.commIds = commIds;
	}
	public Integer getOperator() {
		return operator;
	}
	public void setOperator(Integer operator) {
		this.operator = operator;
	}
	
	
}
