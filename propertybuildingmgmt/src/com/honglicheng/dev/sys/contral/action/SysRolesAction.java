package com.honglicheng.dev.sys.contral.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.honglicheng.dev.estate.model.EasyTree;
import com.honglicheng.dev.estate.model.MenuTree;
import com.honglicheng.dev.sys.model.BaseSysUser;
import com.honglicheng.dev.sys.model.SysMenu;
import com.honglicheng.dev.sys.model.security.Role;
import com.honglicheng.dev.sys.service.RoleService;
import com.honglicheng.dev.sys.service.SysMenuService;
import com.honglicheng.dev.sys.service.UserService;
import com.honglicheng.dev.sys.utils.TreeViewUtil;
import com.opensymphony.xwork2.ActionSupport;


@Controller
@Scope("prototype")
public class SysRolesAction extends ActionSupport {

	/** 
	* 串行化ID
	*/ 
	private static final long serialVersionUID = -6974663968187381015L;

	@Resource
	private RoleService roleService;
	
	@Resource
	private SysMenuService sysMenuService;
	
	@Resource
	private UserService userService;
	
	private List<Role> allRoles ;
	
	private List<EasyTree> userhasroles;
	
	private Role role;

	private String delNums;
	
	private List<MenuTree> allMenus ;
	
	private List<MenuTree> menuTreeList;
	
	private List<EasyTree> roleTreeList;
	
	private String menuList;
	
	private SysMenu sysMenu;
	
	private String userid;
	
	private String roleId;
	
	private String error;
	/**
	 * 
	* @Title: getAllRights 
	* @Description: 获取当前权限列表 
	* @return String    返回类型 
	 */
	public String loadAllRoles(){
		allRoles = roleService.findAllRoles();
		allMenus=new ArrayList<MenuTree>();
		allMenus= sysMenuService.findAllMenuTree();
		menuTreeList=TreeViewUtil.getalltree(allMenus,allMenus.get(0).getParentid());
		return SUCCESS;
	}
	
	public String loadRolesTree(){
		allRoles = roleService.findAllRoles();
		roleTreeList=roletree(allRoles);
		return SUCCESS;
	}
	
	public String loadRolesByUpdate(){
		BaseSysUser user=userService.finUserById(Integer.parseInt(userid));
		List<Role> list=new ArrayList<Role>(user.getRoles());
		userhasroles=roletree(list);
		allRoles=roleService.findRolesExcludeIds(user.getRoles());
		roleTreeList=roletree(allRoles);
		return SUCCESS;
	}
	
	/*
	 * 树形结构角色列表
	 * */
	private List<EasyTree> roletree(List<Role> roles){
		EasyTree roletreenode=null;
      List<EasyTree> roleTreeLists=new ArrayList<EasyTree>();
	      for(int i=0;i<roles.size();i++){
	    	  if(!roles.get(i).equals(null)){
	    		  roletreenode=new EasyTree();
	    		  roletreenode.setId(roles.get(i).getId());
	    		  roletreenode.setText(roles.get(i).getRoleName());
	    		  roleTreeLists.add(roletreenode);
	    	  }
	      }
	      return roleTreeLists;
	}

	
	public String saveOrUpdateRole(){
		List<SysMenu> menus = sysMenuService.findMenusByIds(menuList);
		role.setSysMenus(new HashSet<SysMenu>(menus));
		if(role.getId() == null){
			roleService.saveEntity(role);
		}else{
			roleService.updateEntity(role);
		}
		allRoles = roleService.findAllRoles();
		return SUCCESS;
	}
	
	/**
	 * 删除角色
	 */
	public String deleteRoles(){
		String dels = delNums.substring(1, delNums.length());
		String[] nums = dels.split(",");
		Integer[] ids = new Integer[nums.length];
		for(int i=0;i<nums.length;i++){
			ids[i] = Integer.parseInt(nums[i]);
		}
		roleService.deleteRoles(ids);
		return SUCCESS;
	}
	
	public String deleteRole(){
		try{
			roleService.deleteEntity(role);
		}catch(Exception e){
			error = "error";
		}
		return SUCCESS;
	}
	
	
	
	/**
	 * 查询角色
	 * @return
	 */
	public String loadRole(){
		role = roleService.getEntity(role.getId());
		return SUCCESS;
	}

	public List<Role> getAllRoles() {
		return allRoles;
	}

	public void setAllRoles(List<Role> allRoles) {
		this.allRoles = allRoles;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getDelNums() {
		return delNums;
	}

	public void setDelNums(String delNums) {
		this.delNums = delNums;
	}

	public List<MenuTree> getMenuTreeList() {
		return menuTreeList;
	}

	public void setMenuTreeList(List<MenuTree> menuTreeList) {
		this.menuTreeList = menuTreeList;
	}

	public String getMenuList() {
		return menuList;
	}

	public void setMenuList(String menuList) {
		this.menuList = menuList;
	}

	public SysMenu getSysMenu() {
		return sysMenu;
	}

	public void setSysMenu(SysMenu sysMenu) {
		this.sysMenu = sysMenu;
	}

	public List<EasyTree> getRoleTreeList() {
		return roleTreeList;
	}

	public void setRoleTreeList(List<EasyTree> roleTreeList) {
		this.roleTreeList = roleTreeList;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public List<EasyTree> getUserhasroles() {
		return userhasroles;
	}

	public void setUserhasroles(List<EasyTree> userhasroles) {
		this.userhasroles = userhasroles;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	
	
	
	
	



	
	



	
	
}
