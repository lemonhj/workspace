package com.hlc.archivesmgmt.dao;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hlc.archivesmgmt.entity.User;
import com.hlc.archivesmgmt.service.UserService;


public class UserDaoTest {
	@Test
	public void insertUser(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		UserService us = (UserService) ac.getBean("userService");
		us.saveEntity(new User());
		
	}
}
