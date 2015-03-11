package com.hlc.archivesmgmt.struts.interceptor;

import java.util.Map;

import com.hlc.archivesmgmt.entity.User;
import com.hlc.archivesmgmt.struts.UserAware;
import com.hlc.archivesmgmt.struts.action.LoginAction;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 登录拦截器
 * @author linbotao
 *
 */
public class LoginInterceptor implements Interceptor {
	private static final long serialVersionUID = -5259259624406021055L;

	public void destroy() {
	}

	public void init() {
	}

	public String intercept(ActionInvocation arg0) throws Exception {
		//对于某些aciton需要直接放行
		Object action = arg0.getAction();
		if(action instanceof LoginAction){
			return arg0.invoke();
		}
		
		Map<String, Object> sessionMap = arg0.getInvocationContext().getSession();
		User user = (User) sessionMap.get("user");
		//没有登录
		if(user == null){
			return Action.LOGIN ;
		}
		//登录,放行
		else{
			if(action instanceof UserAware){
				((UserAware)action).setUser(user);
			}
			return arg0.invoke();
		}
	}

}
