package com.honglicheng.dev.sys.dao;

import org.springframework.stereotype.Repository;
import com.honglicheng.dev.basic.dao.BaseDao;

/**
 	* A data access object (DAO) providing persistence and search support for BaseSysUser entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.honglicheng.dev.sys.model.BaseSysUser
  * @author MyEclipse Persistence Tools 
 */
@Repository("baseSysUserDAO")
public class BaseSysUserDAO extends BaseDao  {

}