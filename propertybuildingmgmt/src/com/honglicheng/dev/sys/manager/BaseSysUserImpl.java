package com.honglicheng.dev.sys.manager;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.honglicheng.dev.sys.dao.BaseSysUserDAO;
import com.honglicheng.dev.sys.model.BaseSysUser;
import com.honglicheng.dev.sys.service.UserService;
import com.honglicheng.dev.sys.utils.MyMD5Util;

@Service("baseSysUserFacade")
public class BaseSysUserImpl implements BaseSysUserFacade{

	@Resource
	private BaseSysUserDAO baseSysUserDAO;
	public List login(BaseSysUser bsu) {
		String where = "su_username = '"+bsu.getSuUsername() + "' and su_password = '" + bsu.getSuPassword() + "'";
		List users = baseSysUserDAO.findByWhere("BaseSysUser", where);
		return users;
	}
	
	/** 
     * 验证登陆 
     *  
     * @param userName 
     * @param password 
     * @return 
     * @throws UnsupportedEncodingException  
     * @throws NoSuchAlgorithmException  
     */  
    public  boolean loginValid(String pwdInDb,String password)   
                throws NoSuchAlgorithmException, UnsupportedEncodingException{  
        if(null!=pwdInDb){ // 该用户存在  
                return MyMD5Util.validPassword(password, pwdInDb);  
        }else{  
            System.out.println("不存在该用户！！！");  
            return false;  
        }  
    }  
}
