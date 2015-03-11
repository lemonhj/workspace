package com.honglicheng.dev.sys.utils;

import java.util.ArrayList;
import java.util.List;
import com.honglicheng.dev.estate.model.MenuTree;
import com.honglicheng.dev.estate.model.ViewMenus;
import com.honglicheng.dev.sys.model.SysMenu;

public class TreeViewUtil {
	
	public static List<MenuTree> getalltree(List<MenuTree> listnode,Integer id){
		
		   List<MenuTree> realList = listnode;
		    //获取孩子节点列表
		   List<MenuTree> viewList = new ArrayList<MenuTree>(); 
        for (int i = 0; i < realList.size(); i++)
        {
     	   
     	   if (id.equals(realList.get(i).getParentid()))
            {
     		   MenuTree temp = realList.get(i);
                viewList.add(temp);
                //利用递归获取子孙节点
             	   temp.setChildren(TreeViewUtil.getalltree(realList,temp.getId()));
            }

        }
        return viewList;
	}
	public static List<ViewMenus> getallMenu(List<ViewMenus> listnode,Integer id){
		
		   List<ViewMenus> realList = listnode;
		    //获取孩子节点列表
		   List<ViewMenus> viewList = new ArrayList<ViewMenus>(); 
     for (int i = 0; i < realList.size(); i++)
     {
  	   
  	   if (id.equals(realList.get(i).getParentMId()))
         {
  		   	ViewMenus temp = realList.get(i);
             viewList.add(temp);
             //利用递归获取子孙节点
          	   temp.setMenus(TreeViewUtil.getallMenu(realList,temp.getMenuid()));
         }

     }
     return viewList;
	}

	public static List<MenuTree> copyModle(List<SysMenu> sysMenus,
			List<MenuTree> menuTreeList) {
		for(SysMenu menu : sysMenus){
			MenuTree menuTree = new MenuTree();
			menuTree.setCommon(menu.getMenuDesc());
			menuTree.setIconCls(menu.getMenuIcon());
			menuTree.setId(menu.getMenuId());
			menuTree.setParentid(menu.getParentMId());
			menuTree.setText(menu.getMenuName());
			menuTreeList.add(menuTree);
		}
		return menuTreeList;
	}
	
	
}
