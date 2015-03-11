package com.honglicheng.dev.basic.dao;

import java.util.List;

/**
 * 
* @ClassName: BaseDaoI 
* @Description: 基类操作数据库接口 
* @author linbotao
* @date 2014年12月15日 下午4:34:41 
* 
* @param <T>
 */
public interface BaseDaoI<T> {
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
	/**
	 * 通过数组，批量删除
	 */
	public void deleteEntityByIDS(String hql,Integer[] ids);

}
