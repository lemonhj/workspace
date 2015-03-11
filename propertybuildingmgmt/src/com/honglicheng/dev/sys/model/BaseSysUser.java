package com.honglicheng.dev.sys.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.honglicheng.dev.sys.model.security.Right;
import com.honglicheng.dev.sys.model.security.Role;


public class BaseSysUser {

	//账户ID
	private Integer suId;
	
	//账户昵称
	private String suNick;
	
	//账户名
	private String suUsername;
	
	//账户密码
	private String suPassword;
	
	//账户类型
	private Integer suType;
	
	//账户状态
	private Integer suState;
	
	//账户在线状态
	private Integer suOnline;
	
	//账户注册日期
	private Date suCreate;
	
	//账户最后活动日期
	private Date suActive;
	
	/* 用户的权限总值 */
	private long[] rightSum = new long[1];
	
	/* 角色集合 */
	private Set<Role> roles = new HashSet<Role>();
	
	/*菜单权限*/
	private String menuRight;
	
	/* 是否是超级管理员(roleValue == -1) */
	private boolean superAdmin ;
	
	
	/**
	 * 计算权限总值
	 */
	public void calculateRigthSum(){
		int pos = 0 ;
		long code = 0 ;
		for(Role role:roles){
			//是否是超级管理员
			if("-1".equals(role.getRoleValue())){
				this.setSuperAdmin(true);
			}
			for(Right right : role.getRights()){
				pos = right.getRightPos();
				code = right.getRightCode();
				rightSum[pos] = rightSum[pos] | code;
			}
		}
	}

	public Integer getSuId() {
		return suId;
	}
	
	public void setSuId(Integer suId) {
		this.suId = suId;
	}

	public String getSuNick() {
		return suNick;
	}

	public void setSuNick(String suNick) {
		this.suNick = suNick;
	}

	public String getSuUsername() {
		return suUsername;
	}

	public void setSuUsername(String suUsername) {
		this.suUsername = suUsername;
	}

	public String getSuPassword() {
		return suPassword;
	}

	public void setSuPassword(String suPassword) {
		this.suPassword = suPassword;
	}

	public Integer getSuType() {
		return suType;
	}

	public void setSuType(Integer suType) {
		this.suType = suType;
	}

	public Integer getSuState() {
		return suState;
	}

	public void setSuState(Integer suState) {
		this.suState = suState;
	}

	public Integer getSuOnline() {
		return suOnline;
	}

	public void setSuOnline(Integer suOnline) {
		this.suOnline = suOnline;
	}

	public Date getSuCreate() {
		return suCreate;
	}

	public void setSuCreate(Date suCreate) {
		this.suCreate = suCreate;
	}

	public Date getSuActive() {
		return suActive;
	}

	public void setSuActive(Date suActive) {
		this.suActive = suActive;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getMenuRight() {
		return menuRight;
	}

	public void setMenuRight(String menuRight) {
		this.menuRight = menuRight;
	}

	public boolean isSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
	}

	public long[] getRightSum() {
		return rightSum;
	}

	public void setRightSum(long[] rightSum) {
		this.rightSum = rightSum;
	}
	
	
	
}
