package com.honglicheng.dev.sys.service;

import java.util.List;

import com.honglicheng.dev.estate.model.MenuTree;
import com.honglicheng.dev.sys.model.SysMenu;

public interface SysMenuService extends BaseService<SysMenu>{
	/**
	 * 查询全部权限
	 */
	public List<SysMenu> findAllSysMenu();
	public List<MenuTree> findAllMenuTree();
	public List<SysMenu> findMenusByIds(String menuList);

}
