package com.honglicheng.dev.sys.contral.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import com.honglicheng.dev.estate.manager.FormatsTypeMgrFacade;
import com.honglicheng.dev.estate.model.IndustryTypeInfo;
import com.honglicheng.dev.sys.model.security.Right;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class NavigateAction extends ActionSupport implements SessionAware{

	private static final long serialVersionUID = -978351217688511943L;

	private JdbcTemplate jdbcTemplate;
	
	private Map<String,Object> session;
	
	private String where;
	
	private String editId;
	
	private String ecolor;
	
	private String ename;
	
	
	@Resource
	FormatsTypeMgrFacade formatsTypeMgrFacade;
	public String logout(){
		session.remove("user");
		return "logout";
	}
	
	public String floor_basic_mgr(){
		return "floor_basic_mgr";
	}
	
	public String floor_basic_mgr_addorupdata(){
		return "floor_basic_mgr_addorupdata";
	}
	
	public String formats_type(){
		return "formats_type";
	}
	
	public String menuMgmt(){
		return "sysMenu_edit";
	}
	
	public String rightsEdit(){
		return "sys_rights_edit";
	}
	
	public String rolesEdit(){
		return "sys_roles_edit";
	}
	
	public String usersMgmt(){
		return "sys_users_mgmt";
	}
	
	public String floorsd(){
		return "floorsd";
	}
	
	
	public String floor_yt(){
		return "floor_yt";
	}
	
	public String floor_yz(){
		return "floor_yz";
	}
	
	
	public String addOrUpdateRights(){
		return "addOrUpdateRights";
	}
	
	public String addOrUpdateRoles(){
		return "addOrUpdateRoles";
	}
	
	public String floor_basic_mgr_addroom(){
		return "floor_basic_mgr_addroom";
	}
	
	public String tree_edit(){
		return "tree_edit";
	}
	
	public String formats_type_addorupdata(){
		if(editId != null && !editId.equals("")){
			IndustryTypeInfo iti = formatsTypeMgrFacade.findByCid(Integer.parseInt(editId));
			if(iti != null){
				ecolor = iti.getColor();
				ename = iti.getName();
			}
		}else{
			ecolor = "#000000";
		}
		if(where.equals("add")){
			where = "isAdd";
		}else{
			where = "isMod";
		}
			
		return "formats_type_addorupdata";
	}
	
	public void setSession(Map<String, Object> arg0) {
		session = arg0;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getEditId() {
		return editId;
	}

	public void setEditId(String editId) {
		this.editId = editId;
	}

	public String getEcolor() {
		return ecolor;
	}

	public void setEcolor(String ecolor) {
		this.ecolor = ecolor;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	
}
