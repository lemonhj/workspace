package com.hlc.propertybuilding.dao;


import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDao<T> {
	private Class<T> entityClass;
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public BaseDao(){
		Type genType =getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
		entityClass = (Class)params[0];
	}
	
	public T load(Serializable id){
		return null;
	}
	
	
}
