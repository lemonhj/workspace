package com.honglicheng.dev.sys.manager;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.honglicheng.dev.sys.model.BaseSysUser;


public interface BaseSysUserFacade {

	List login(BaseSysUser bsu);
	public  boolean loginValid(String pwdInDb,String password)   
            throws NoSuchAlgorithmException, UnsupportedEncodingException;
}
