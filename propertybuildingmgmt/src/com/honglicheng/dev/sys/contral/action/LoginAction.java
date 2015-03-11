package com.honglicheng.dev.sys.contral.action;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.honglicheng.dev.estate.model.ViewMenus;
import com.honglicheng.dev.sys.manager.BaseSysUserFacade;
import com.honglicheng.dev.sys.model.BaseSysUser;
import com.honglicheng.dev.sys.model.SysMenu;
import com.honglicheng.dev.sys.model.security.Role;
import com.honglicheng.dev.sys.service.SysMenuService;
import com.honglicheng.dev.sys.service.UserService;
import com.honglicheng.dev.sys.utils.CopyDataUtils;
import com.honglicheng.dev.sys.utils.MyMD5Util;
import com.honglicheng.dev.sys.utils.TreeViewUtil;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class LoginAction extends ActionSupport implements SessionAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JdbcTemplate jdbcTemplate;
	
	private BaseSysUser user = new BaseSysUser();
	
	private Map<String,Object> session;
	
	private String suUsername;
	
	private String suPassword;
	
	/* 注入UserService*/
	@Resource
	private UserService userService ;
	
	@Resource
	private SysMenuService sysMenuService;
	
	@Resource
	private BaseSysUserFacade baseSysUserFacade; 
	
	public BaseSysUser getModel() {
		return user;
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public String login(){
			return SUCCESS;
	}
	
	
	/**
	 * 登陆验证
	 */
	public void validate(){
		if("".equals(suUsername.trim())){
			addActionError("请输入用户名!");
			return;
		}
		user = userService.findUserByName(suUsername);
		boolean effective = false;
		if(null == user){
			addActionError("此用户名不存在!");
			return;
		}
		try {
			effective = MyMD5Util.validPassword(suPassword, user.getSuPassword());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//信息正确
		if(effective){
			user = initUserMenu(user);
			//登录有效
			session.put("user",user);
			//初始化权限总值
			String hqlmax = "select max(r.rightPos) from Right r";
			//查询权限位的最大值,初始化权限总值的数组长度
			Integer pos = (Integer) userService.uniqueResult(hqlmax);
			if(pos != null){
				user.setRightSum(new long[pos + 1]);
			}
			//计算权限总值
			user.calculateRigthSum();
			//清除role集合,
			//user.setRoles(null);
		}
		else{
			addActionError("用户名或密码有误!");
		}
	}				

	private BaseSysUser initUserMenu(BaseSysUser user) {
		TreeViewUtil treeViewUtil = new TreeViewUtil();
		Set<Role> roles = user.getRoles();
		List<ViewMenus> treeList = new ArrayList<ViewMenus>();
		for(Role r : roles){
			Set<SysMenu> menus = r.getSysMenus();
			List<SysMenu> menuList = new ArrayList(menus);
			Collections.sort(menuList);
 			treeList = copyMenuModle(menuList, treeList);
 			
			treeList = TreeViewUtil.getallMenu(treeList,treeList.get(0).getParentMId());
		}
		Gson gson = new Gson();
		user.setMenuRight(gson.toJson(treeList));
		return user;
	}

	public void setSession(Map<String, Object> arg0) {
		session = arg0;
	}
	
	private  List<ViewMenus> copyMenuModle(List<SysMenu> sysMenus,
			List<ViewMenus> menuTreeList) {
		List<SysMenu> menus = new ArrayList<SysMenu>();
		CopyDataUtils.copyList(menus, sysMenus);
		for(SysMenu menu : sysMenus){
			if(menu.getMenuUrl()!=null && !menu.getMenuUrl().equals("") && !isContainParent(menus,menu.getParentMId())){
				SysMenu sysMenu = sysMenuService.getEntity(menu.getParentMId());
				ViewMenus menuTree = new ViewMenus();
				menuTree.setIcon(sysMenu.getMenuIcon());
				menuTree.setMenuid(sysMenu.getMenuId());
				menuTree.setParentMId(sysMenu.getParentMId());
				menuTree.setMenuname(sysMenu.getMenuName());
				menus.add(sysMenu);
				menuTreeList.add(menuTree);
			}
			ViewMenus menuTree = new ViewMenus();
			menuTree.setIcon(menu.getMenuIcon());
			menuTree.setMenuid(menu.getMenuId());
			menuTree.setParentMId(menu.getParentMId());
			menuTree.setMenuname(menu.getMenuName());
			menuTree.setUrl(menu.getMenuUrl());
			menuTreeList.add(menuTree);
		}
		return menuTreeList;
	}

	
	private static boolean isContainParent(List<SysMenu> sysMenus,Integer id){
		for(SysMenu m : sysMenus){
			if(m.getMenuId().equals(id)){
				return true;
			}
		}
		return false;
	}

	public String getSuUsername() {
		return suUsername;
	}

	public void setSuUsername(String suUsername) {
		this.suUsername = suUsername;
	}

	public String getSuPassword() {
		return suPassword;
	}

	public void setSuPassword(String suPassword) {
		this.suPassword = suPassword;
	}
	
	
	
	
}
