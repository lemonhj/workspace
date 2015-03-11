package com.honglicheng.dev.sys.contral.interceptors;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.honglicheng.dev.sys.service.RightService;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 
* @ClassName: CatchUrlInterceptor 
* @Description: 捕获权限URL
* @author 林波涛
* @date 2014年12月15日 下午5:06:08 
*
 */
public class CatchUrlInterceptor implements Interceptor {
	private static final long serialVersionUID = -8835829514257764285L;
	/* 是否启用该拦截器 */
	private boolean enable ;
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public void destroy() {
	}
	public void init() {
	}
	public String intercept(ActionInvocation arg0) throws Exception {
		//如果启用开启,则运行捕获
		if(enable){
			String ns = arg0.getProxy().getNamespace();
			String actionName = arg0.getProxy().getActionName();
			//ns== null ns="/" ns="/aa" 
			String prefix ="/";
			if(ns != null && !ns.equals("/")){
				prefix = prefix + "/" ;
			}
			String url = prefix + actionName;
			//得到spring容器
			ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
			RightService rs = (RightService) ac.getBean("rightService");
			//通过url追加权限
			rs.addRightByURL(url);
		}
		return arg0.invoke();
	}
}