package com.honglicheng.dev.sys.contral.interceptors;

import javax.servlet.jsp.PageContext;

import org.apache.struts2.ServletActionContext;

import com.honglicheng.dev.sys.contral.UserAware;
import com.honglicheng.dev.sys.model.BaseSysUser;
import com.honglicheng.dev.sys.utils.RightUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 权限拦截器
 */
public class RightInterceptor implements Interceptor {
	private static final long serialVersionUID = -5699829944718587645L;

	public void destroy() {
	}

	public void init() {
	}

	public String intercept(ActionInvocation arg0) throws Exception {
		String ns = arg0.getProxy().getNamespace();
		String actionName = arg0.getProxy().getActionName();
		PageContext pc = ServletActionContext.getPageContext();
		if(RightUtil.hasRight(ns, actionName, ServletActionContext.getRequest())){
			
			BaseSysUser user = (BaseSysUser) ServletActionContext.getRequest().getSession().getAttribute("user");
			if(user != null){
				Object action = arg0.getAction() ;
				if(action instanceof UserAware){
					((UserAware)action).setUser(user);
				}
			}
			return arg0.invoke();
		}
		return "login";
	}
}
