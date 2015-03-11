package com.honglicheng.dev.sys.contral.interceptors;

import java.util.Map;

import com.honglicheng.dev.sys.contral.UserAware;
import com.honglicheng.dev.sys.contral.action.LoginAction;
import com.honglicheng.dev.sys.model.BaseSysUser;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class LoginInterceptor implements Interceptor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1796431157405677555L;

	public void destroy() {
		
	}

	public void init() {
		
	}

	public String intercept(ActionInvocation arg0) throws Exception {
		//对于某些action直接放行
		Object action = arg0.getAction();
		if(action instanceof LoginAction){
			return arg0.invoke();
		}
		
		Map<String,Object> sessionMap = arg0.getInvocationContext().getSession();
		BaseSysUser bsu = (BaseSysUser)sessionMap.get("user");
		//没有登录
		if(bsu == null){
			return "temp";
		}
		//登陆放行
		else{
			if(action instanceof UserAware){
				((UserAware)action).setUser(bsu);
			}
			return arg0.invoke();
		}
	}

	
}
