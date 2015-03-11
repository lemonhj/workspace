package com.hlc.archivesmgmt.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hlc.archivesmgmt.dao.BaseDao;
import com.hlc.archivesmgmt.entity.User;
import com.hlc.archivesmgmt.service.UserService;


/**
 * 用户服务的实现类
 * @author linbotao
 *
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements
		UserService {

	/**
	 * 重新setDao方法,注入指定的dao对象
	 */
	@Resource(name="userDao")
	public void setDao(BaseDao<User> dao) {
		super.setDao(dao);
	}

}