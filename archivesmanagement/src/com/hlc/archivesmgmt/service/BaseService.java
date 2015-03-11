package com.hlc.archivesmgmt.service;

import java.util.List;

import com.hlc.archivesmgmt.entity.Archive;

/**
 * 服务接口
 * @author linbotao
 *
 * @param <T>
 */
public interface BaseService<T> {
	/* 写操作 */
	public void saveEntity(T t);
	public T saveOrUpdateEntity(T t);
	public void updateEntity(T t);
	public void deleteEntity(T t);
	public void batchEntityByHQL(String hql,Object...objects);
	public void deleteEntityByIDS(String hql,Integer[] ids);
	
	/* 读操作 */
	public T loadEntity(Integer id);
	public T getEntity(Integer id);
	public List<T> findEntityByHQL(String hql,Object...objects);
	//单值检索(确保查询有且仅有提交记录)
	public Object uniqueResult(String hql,Object...objects);
	
	public List getCateList(Integer cateId);
	
	public List getLocaList(Integer locaId);
	
	public List getAList(Integer aId);
	
	public List<T> findEntityByIDS(String hql,List idl,List idc);
	
	public List<T> findEntityByIdsAndName(String hql,List idc,List idl,String name);
	
	public List<T> findEntityByIdsAndName(String hql,List idc,List idl,String name, String yearStr);
	
	public List<T> findEntityByIDSnoParent(String hql,Integer id,List idl);
	
	public List<T> findEntityByIDSnoParent(String hql,Integer id,List idl,String yearStr);
	
	public List<T> findEntityByIdsAndNameNoParent(String hql,Integer id,List idl,String name,String yearStr);
}
