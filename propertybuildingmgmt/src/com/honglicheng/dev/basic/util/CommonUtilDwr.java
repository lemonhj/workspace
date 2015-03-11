package com.honglicheng.dev.basic.util;


import javax.servlet.http.HttpSession;

import com.honglicheng.dev.basic.env.Constant;



public class CommonUtilDwr {
	/**
	 * 变更登录用户的项目
	 * @param newPid
	 * @param newPname
	 * @param session
	 * @author: yanglh
	 * @createDate: 09-07, 2014
	 */
	public void changeCurrentAppPid(String newPid, String newPname, HttpSession session){
		session.setAttribute(Constant.CURRENTAPPPID, newPid);
		session.setAttribute(Constant.CURRENTAPPPNAME, newPname);
	}
}
