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
 * ��¼Action
 * @author linbotao
 *
 */
@Controller
@Scope("prototype")
public class LoginAction extends BaseAction<User> implements SessionAware{
	private static final long serialVersionUID = 4126887908006817566L;
	/* ģ�� */
	private User user = new User();
	
	/* ע��UserService*/
	@Resource
	private UserService userService ;
	
	/* ����session��map���� */
	private Map<String, Object> sessionMap;
	
	public User getModel() {
		return user;
	}
	
	/**
	 * �ﵽ��¼ҳ��
	 */
	@SkipValidation
	public String toLoginPage(){
		return "loginView" ;
	}
	
	/**
	 * ��¼
	 */
	public String login(){
		return SUCCESS;
	}
	
	public void validate(){
		
		String hql = "from User u where u.password = ?" ;
		List<User> list = userService.findEntityByHQL(hql, user.getPassword());
		//��Ϣ��ȷ
		if(ValidateUtil.isValid(list)){
			sessionMap.put("user", list.get(0));
		}
		else{
			addActionError("���벻��ȷ,����������!");
		}
	}

	/**
	 * ע��session����
	 */
	public void setSession(Map<String, Object> arg0) {
		this.sessionMap = arg0;
	}

}
