package com.honglicheng.dev.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.honglicheng.dev.basic.dao.BaseDaoI;
import com.honglicheng.dev.estate.model.MenuTree;
import com.honglicheng.dev.sys.model.SysMenu;
import com.honglicheng.dev.sys.service.SysMenuService;
import com.honglicheng.dev.sys.utils.TreeViewUtil;
@Service("sysMenuService")
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenu> implements SysMenuService{
	/**
	 * 查询全部菜单
	 */
	@Override
	public List<SysMenu> findAllSysMenu() {
		return this.findEntityByHQL("from SysMenu");
	}
	
	/**
	 * 重新setDao方法,注入指定的dao对象
	 */
	@Resource(name="sysMenuDao")
	public void setDao(BaseDaoI<SysMenu> dao) {
		super.setDao(dao);
	}
	
	public List<MenuTree> findAllMenuTree(){
		List<SysMenu> sysMenus = this.findEntityByHQL("from SysMenu");
		List<MenuTree> menuTreeList = new ArrayList<MenuTree>();
		menuTreeList = TreeViewUtil.copyModle(sysMenus,menuTreeList);
		return menuTreeList;
	}
	
	public List<SysMenu> findMenusByIds(String menuList){
		String hql = "from SysMenu m where m.menuId in ("+menuList.substring(0,menuList.length() - 1)+")";
		return this.findEntityByHQL(hql);
	}
	
}
