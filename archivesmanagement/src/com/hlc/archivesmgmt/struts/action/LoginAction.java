package com.hlc.archivesmgmt.struts.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hlc.archivesmgmt.entity.User;
import com.hlc.archivesmgmt.service.UserService;
import com.hlc.archivesmgmt.util.ValidateUtil;


/**
 * 登录Action
 * @author linbotao
 *
 */
@Controller
@Scope("prototype")
public class LoginAction extends BaseAction<User> implements SessionAware{
	private static final long serialVersionUID = 4126887908006817566L;
	/* 模型 */
	private User user = new User();
	
	/* 注入UserService*/
	@Resource
	private UserService userService ;
	
	/* 接受session的map集合 */
	private Map<String, Object> sessionMap;
	
	public User getModel() {
		return user;
	}
	
	/**
	 * 达到登录页面
	 */
	@SkipValidation
	public String toLoginPage(){
		return "loginView" ;
	}
	
	/**
	 * 登录
	 */
	public String login(){
		return SUCCESS;
	}
	
	public void validate(){
		
		String hql = "from User u where u.password = ?" ;
		List<User> list = userService.findEntityByHQL(hql, user.getPassword());
		//信息正确
		if(ValidateUtil.isValid(list)){
			sessionMap.put("user", list.get(0));
		}
		else{
			addActionError("密码不正确,请重新输入!");
		}
	}

	/**
	 * 注入session对象
	 */
	public void setSession(Map<String, Object> arg0) {
		this.sessionMap = arg0;
	}

}
