package com.honglicheng.dev.sys.dao.impl;

import org.springframework.stereotype.Repository;

import com.honglicheng.dev.basic.dao.BaseDaoImpl;
import com.honglicheng.dev.sys.model.BaseSysUser;
import com.honglicheng.dev.sys.model.security.Right;

@Repository("userDao")
public class UserDao extends BaseDaoImpl<BaseSysUser> {

}
