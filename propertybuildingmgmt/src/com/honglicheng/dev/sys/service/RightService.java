package com.honglicheng.dev.sys.service;

import java.util.List;
import java.util.Set;

import com.honglicheng.dev.sys.model.security.Right;

public interface RightService extends BaseService<Right>{
	/**
	 * 查询全部权限
	 */
	public List<Right> findAllRights();

	/**
	 * 保存权限
	 */
	public void saveOrUpdateRight(Right right);

	/**
	 * url
	 */
	public void addRightByURL(String url);

	/**
	 * 按照id删除权限
	 */
	public void deleteRight(Integer rightid);
	
	public void deleteRights(Integer[] ids);

	/**
	 * //查询所有没有的权限
	 */
	public List<Right> findRightExcludeIds(Set<Right> rights);

}
