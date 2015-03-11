package com.hlc.archivesmgmt.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hlc.archivesmgmt.dao.BaseDao;
import com.hlc.archivesmgmt.entity.User;
import com.hlc.archivesmgmt.service.UserService;


/**
 * �û������ʵ����
 * @author linbotao
 *
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements
		UserService {

	/**
	 * ����setDao����,ע��ָ����dao����
	 */
	@Resource(name="userDao")
	public void setDao(BaseDao<User> dao) {
		super.setDao(dao);
	}

}