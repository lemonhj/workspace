package com.honglicheng.dev.sys.utils;

import java.util.Comparator;

import com.honglicheng.dev.sys.model.SysMenu;

public class TreeMenuCompare implements Comparable<SysMenu>{

	 public int compare(Object o1,Object o2) {
	  SysMenu m1=(SysMenu)o1;
	  SysMenu m2=(SysMenu)o2;  
	  if(m1.getMenuId()>m2.getMenuId())
	   return 1;
	  else
	   return 0;
	 }

	@Override
	public int compareTo(SysMenu o) {
		// TODO Auto-generated method stub
		return 0;
	}
}