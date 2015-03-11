package com.hlc.archivesmgmt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.hlc.archivesmgmt.dao.BaseDao;
import com.hlc.archivesmgmt.entity.Archive;
import com.hlc.archivesmgmt.service.BaseService;


/**
 * BaseServiceImpl实现类,专门用继承使用
 * @author linbotao
 *
 * @param <T>
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

	private BaseDao<T> dao ;
	
	@Resource
	public void setDao(BaseDao<T> dao) {
		this.dao = dao;
	}
	
	public List getCateList(Integer cateId){
		return dao.getCateList(cateId);
	}
	
	public List getLocaList(Integer locaId){
		return dao.getLocaList(locaId);
	}
	
	public List getAList(Integer aId){
		return dao.getAList(aId);
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
	
	public List<T> searchEntityByHQL(String hql, Object...objects) {
		return dao.searchEntityByHQL(hql, objects);
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

	public T saveOrUpdateEntity(T t) {
		dao.saveOrUpdateEntity(t);
		return t;
	}

	public void updateEntity(T t) {
		dao.updateEntity(t);
	}
	
	public List<T> findEntityByIDS(String hql,List idl,List idc){
		return dao.findEntityByIDS(hql,idl,idc);
	}
	
	public List<T> findEntityByIDS(String hql,List idl,List idc,String yearStr){
		return dao.findEntityByIDS(hql,idl,idc,yearStr);
	}
	
	public List<T> findEntityByIdsAndName(String hql,List idc,List idl,String name){
		return dao.findEntityByIdsAndName(hql, idc, idl, name);
	}
	
	public List<T> findEntityByIdsAndName(String hql,List idc,List idl,String name,String yearStr){
		return dao.findEntityByIdsAndName(hql, idc, idl, name, yearStr);
	}
	
	public List<T> findEntityByIDSnoParent(String hql,Integer id,List idl){
		return dao.findEntityByIDSnoParent(hql, id, idl);
	}
	
	public List<T> findEntityByIDSnoParent(String hql,Integer id,List idl,String yearStr){
		return dao.findEntityByIDSnoParent(hql, id, idl, yearStr);
	}
	
	public List<T> findEntityByIdsAndNameNoParent(String hql,Integer id,List idl,String name){
		return dao.findEntityByIdsAndNameNoParent(hql, id, idl, name);
	}
	
	public List<T> findEntityByIdsAndNameNoParent(String hql,Integer id,List idl,String name,String yearStr){
		return dao.findEntityByIdsAndNameNoParent(hql, id, idl, name, yearStr);
	}
}