package com.honglicheng.dev.sys.service;

import java.util.List;
import java.util.Set;

import com.honglicheng.dev.sys.model.security.Role;

public interface RoleService extends BaseService<Role>{
	/**
	 * 查询所有角色
	 */
	public List<Role> findAllRoles();

	/**
	 * 保存/更新角色
	 */
	public void saveOrUpdateRole(Role role, Integer[] ownRightIds);

	/**
	 * 查询排除指定id的角色集合
	 */
	public List<Role> findRolesExcludeIds(Set<Role> roles);

	/**
	 * 删除角色
	 */
	public void deleteRole(Integer roleid);

	void deleteRoles(Integer[] ids);
}
