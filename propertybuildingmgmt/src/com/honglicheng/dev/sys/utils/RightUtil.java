package com.honglicheng.dev.sys.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.honglicheng.dev.sys.model.BaseSysUser;
import com.honglicheng.dev.sys.model.security.Right;
import com.honglicheng.dev.sys.service.listener.IniRightListener;

/**
 * 权限工具类
 */
public class RightUtil {
	/**
	 * 是否用拥有权限
	 */
	public static boolean hasRight(String namespace,String actionName,PageContext pc){
		return hasRight(namespace,actionName,(HttpServletRequest)pc.getRequest());
	}
	
	/**
	 * 
	 */
	public static boolean hasRight(String namespace,String actionName,HttpServletRequest req){
		//取得url
		//ns== null ns="/" ns="/aa" 
		String prefix ="/";
		if(namespace != null && !namespace.equals("/")){
			prefix = prefix + "/" ;
		}
		String url = prefix + actionName;
		//判断是否是公共资源
		Map<String, Right> map = (Map<String, Right>) req.getSession().getServletContext().getAttribute(IniRightListener.ALL_RIGHTS);
		Right r = map.get(url);
		//公共资源
		if(r == null){
			return true;
		}
		//判断是否是超级管理员
		BaseSysUser user = (BaseSysUser) req.getSession().getAttribute("user");
		//已经登录
		if(user != null){
			if(user.isSuperAdmin()){
				return true;
			}
			//需要权限认证
			else{
				int res = (int)(user.getRightSum()[r.getRightPos()] & r.getRightCode());
				//没有改权限
				if(res == 0){
					return false;
				}
				else{
					return true ;
				}
			}
		}
		else{
			return false ;
		}
	}
}
