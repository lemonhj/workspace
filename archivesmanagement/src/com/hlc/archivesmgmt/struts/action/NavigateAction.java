package com.hlc.archivesmgmt.struts.action;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hlc.archivesmgmt.service.ExcelService;
import com.opensymphony.xwork2.ActionSupport;


/**
 * Ìø×ªAction
 * @author linbotao
 *
 */
@Controller
@Scope("prototype")
public class NavigateAction extends ActionSupport implements SessionAware{

	/** 
	* @Fields serialVersionUID : ´®ÐÐID
	*/ 
	private static final long serialVersionUID = 3369511631049388892L;
	
	@Resource
	private ExcelService es;
	
	private Map<String, Object> sessionMap;
	
	public String naviCategories(){
		return "cate";
	}
	public String naviArchive(){
		return "archive";
	}
	public String searchArchive(){
		return "search";
	}
	
	public String importexcel(){
		es.saveFromExcel();
		return "su";
	}
	
	public String logout(){
		sessionMap.remove("user");
		return "logout";
	}
	@Override
	public void setSession(Map<String, Object> arg0) {
		sessionMap = arg0;
		
	}
	public Map<String, Object> getSessionMap() {
		return sessionMap;
	}
	public void setSessionMap(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}

	
}
