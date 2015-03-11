package com.honglicheng.dev.sys.service;

import java.util.List;

import com.honglicheng.dev.sys.model.BaseSysUser;
public interface UserService extends BaseService<BaseSysUser>{

	/**
	 * 查询所有用户
	 */
	public List<BaseSysUser> findAllUsers();

	/**
	 * 更新用户的授权
	 */
	public void updateAuthorize(BaseSysUser model, Integer[] ownRoleIds);

	/**
	 * 清除用户授权
	 */
	public BaseSysUser finUserById(Integer id);
	
	public void clearAuthorize(Integer userid);
	
	public BaseSysUser findUserByName(String userName);

	public void deleteUsers(Integer[] ids);
}
