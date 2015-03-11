package com.honglicheng.dev.sys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.honglicheng.dev.basic.dao.BaseDaoI;
import com.honglicheng.dev.sys.service.BaseService;


/**
 * BaseServiceImpl实现类,专门用继承使用
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

	private BaseDaoI<T> dao ;
	
	@Resource
	public void setDao(BaseDaoI<T> daoI) {
		this.dao = daoI;
	}

	public void batchEntityByHQL(String hql, Object... objects) {
		dao.batchEntityByHQL(hql, objects);
	}

	public void deleteEntity(T t) {
		dao.deleteEntity(t);
	}
	
	public void deleteEntityByIDS(String hql,Integer[] ids){
		dao.deleteEntityByIDS(hql, ids);
	}

	public List<T> findEntityByHQL(String hql, Object... objects) {
		return dao.findEntityByHQL(hql, objects);
	}
	
	//单值检索(确保查询有且仅有提交记录)
	public Object uniqueResult(String hql,Object...objects){
		return dao.uniqueResult(hql, objects);
	}

	public T getEntity(Integer id) {
		return dao.getEntity(id);
	}

	public T loadEntity(Integer id) {
		return dao.loadEntity(id);
	}

	public void saveEntity(T t) {
		dao.saveEntity(t);
	}

	public void saveOrUpdateEntity(T t) {
		dao.saveOrUpdateEntity(t);
	}

	public void updateEntity(T t) {
		dao.updateEntity(t);
	}
}