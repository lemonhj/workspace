package com.honglicheng.dev.sys.contral.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.honglicheng.dev.estate.model.MenuTree;
import com.honglicheng.dev.sys.service.SysMenuService;
import com.honglicheng.dev.sys.utils.TreeViewUtil;
import com.opensymphony.xwork2.ActionSupport;


@Controller
@Scope("prototype")
public class SysMenuAction extends ActionSupport {

	/** 
	* 串行化ID
	*/ 
	private static final long serialVersionUID = -2460536108286912212L;
	
	@Resource
	private SysMenuService sysMenuService;
	
	private List<MenuTree> allMenus ;
	
	private List<MenuTree> menuTreeList;
	
	

	public String loadTreeListnode(){
		allMenus=new ArrayList<MenuTree>();
		allMenus= sysMenuService.findAllMenuTree();
		menuTreeList=TreeViewUtil.getalltree(allMenus,allMenus.get(0).getParentid());
		return SUCCESS;
	}
	
	

	public List<MenuTree> getAllMenus() {
		return allMenus;
	}

	public void setAllMenus(List<MenuTree> allMenus) {
		this.allMenus = allMenus;
	}

	public List<MenuTree> getMenuTreeList() {
		return menuTreeList;
	}

	public void setMenuTreeList(List<MenuTree> menuTreeList) {
		this.menuTreeList = menuTreeList;
	}
	
	
	

	
	
}
