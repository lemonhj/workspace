package com.honglicheng.dev.sys.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.honglicheng.dev.basic.dao.BaseDaoI;
import com.honglicheng.dev.sys.model.security.Right;
import com.honglicheng.dev.sys.model.security.Role;
import com.honglicheng.dev.sys.service.RoleService;
import com.honglicheng.dev.sys.utils.StringUtil;
import com.honglicheng.dev.sys.utils.ValidateUtil;

/**
 * RoleServiceImpl
 */
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
	
	/* 注入权限dao */
	@Resource(name="rightDao")
	private BaseDaoI<Right> rightDao ;
	
	/**
	 * 重新setDao方法,注入指定的dao对象
	 */
	@Resource(name="roleDao")
	public void setDao(BaseDaoI<Role> dao) {
		super.setDao(dao);
	}
	
	/**
	 * 查询所有角色
	 */
	public List<Role> findAllRoles(){
		return this.findEntityByHQL("from Role");
	}
	
	/**
	 * 保存/更新角色
	 */
	public void saveOrUpdateRole(Role role, Integer[] ownRightIds){
		if(ValidateUtil.isValid(ownRightIds)){
			//通过id数组查询权限集合
			List<Right> rights = rightDao.findEntityByHQL("from Right r where r.id in ("+StringUtil.arr2Str(ownRightIds)+")");
			role.setRights(new HashSet<Right>(rights));
		}
		this.saveOrUpdateEntity(role);
	}
	
	/**
	 * 查询排除指定id的角色集合
	 */
	public List<Role> findRolesExcludeIds(Set<Role> roles){
		if(!ValidateUtil.isValid(roles)){
			return this.findAllRoles();
		}
		else{
			String hql = "from Role r where r.id not in ("+ extractIds(roles) +")";
			return this.findEntityByHQL(hql);
		}
	}
	
	/**
	 * 抽取所有id的字符串
	 */
	private String extractIds(Set<Role> roles){
		String temp = "";
		for(Role r:roles){
			temp = temp + r.getId() + ",";
		}
		return temp.substring(0,temp.length() - 1) ;
	}
	
	/**
	 * 删除角色
	 */
	public void deleteRole(Integer roleid){
		String hql = "delete from Role r where r.id = ?" ;
		this.batchEntityByHQL(hql, roleid);
	}
	
	@Override
	public void deleteRoles(Integer[] ids) {
		String hql = "delete from Role r where r.id in (:idd)";
		this.deleteEntityByIDS(hql, ids);
	}
}
