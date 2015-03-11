package com.honglicheng.dev.sys.service.impl;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.honglicheng.dev.basic.dao.BaseDaoI;
import com.honglicheng.dev.sys.model.BaseSysUser;
import com.honglicheng.dev.sys.model.security.Role;
import com.honglicheng.dev.sys.service.UserService;
import com.honglicheng.dev.sys.utils.StringUtil;
import com.honglicheng.dev.sys.utils.ValidateUtil;

/**
 * RoleServiceImpl
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<BaseSysUser> implements UserService {
	/* 注入角色dao */
	@Resource(name="roleDao")
	private BaseDaoI<Role> roleDao ;
	/**
	 * 重新setDao方法,注入指定的dao对象
	 */
	@Resource(name="userDao")
	public void setDao(BaseDaoI<BaseSysUser> dao) {
		super.setDao(dao);
	}

	/**
	 * 查询所有用户
	 */
	public List<BaseSysUser> findAllUsers(){
		return this.findEntityByHQL("from BaseSysUser");
	}
	
	/**
	 * 查找指定用户
	 * */
	public BaseSysUser finUserById(Integer id){
		return this.getEntity(id);
	}
	
	/**
	 * 更新用户的授权
	 */
	public void updateAuthorize(BaseSysUser model, Integer[] ownRoleIds){
		BaseSysUser u = this.getEntity(model.getSuId());
		u.getRoles().clear();
		if(ValidateUtil.isValid(ownRoleIds)){
			List<Role> roles = roleDao.findEntityByHQL("from Role r where r.id in ("+StringUtil.arr2Str(ownRoleIds)+")");
			u.setRoles(new HashSet<Role>(roles));
		}
	}
	
	/**
	 * 清除用户授权
	 */
	public void clearAuthorize(Integer userid){
		BaseSysUser user = this.getEntity(userid);
		user.getRoles().clear();
	}
	
	@Override
	public void deleteUsers(Integer[] ids) {
		String hql = "delete from BaseSysUser u where u.suId in (:idd)";
		this.deleteEntityByIDS(hql, ids);
	}

	@Override
	public BaseSysUser findUserByName(String userName) {
		String hql = "from BaseSysUser u where u.suUsername = ?";
		return (BaseSysUser) this.uniqueResult(hql, userName);
	}
	
	
}
