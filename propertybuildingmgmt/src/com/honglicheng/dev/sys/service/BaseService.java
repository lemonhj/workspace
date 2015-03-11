package com.honglicheng.dev.sys.service;

import java.util.List;

/**
 * BaseService接口
 */
public interface BaseService<T> {
	/* 写操作 */
	public void saveEntity(T t);
	public void saveOrUpdateEntity(T t);
	public void updateEntity(T t);
	public void deleteEntity(T t);
	public void batchEntityByHQL(String hql,Object...objects);
	
	/* 读操作 */
	public T loadEntity(Integer id);
	public T getEntity(Integer id);
	public List<T> findEntityByHQL(String hql,Object...objects);
	//单值检索(确保查询有且仅有提交记录)
	public Object uniqueResult(String hql,Object...objects);
	public void deleteEntityByIDS(String hql,Integer[] ids);
}
