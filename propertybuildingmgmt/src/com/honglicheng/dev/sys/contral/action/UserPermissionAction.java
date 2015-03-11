package com.honglicheng.dev.sys.contral.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.honglicheng.dev.sys.model.BaseSysUser;
import com.opensymphony.xwork2.ActionSupport;
@Controller
@Scope("prototype")
public class UserPermissionAction extends ActionSupport implements SessionAware{

	private static final long serialVersionUID = 1908303100529471144L;

	private String usertype;
	
	private Map sessionmap; 
	
	public String loadUserType(){
		BaseSysUser getuser=(BaseSysUser)sessionmap.get("user");
		usertype=getuser.getSuType().toString();
		return SUCCESS;
	}

	public void setSession(Map<String, Object> arg0) {
		this.sessionmap=arg0;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
	
}
