package com.hlc.archivesmgmt.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.hlc.archivesmgmt.dao.BaseDao;
import com.hlc.archivesmgmt.entity.Archive;
import com.mchange.v2.c3p0.ComboPooledDataSource;


/**
 * �����DAOʵ��
 * @author linbotao
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public abstract class BaseDaoImpl<T> implements BaseDao<T> {
	//ʵ�����������
	private Class<T> clazz ;
	//�Ự����
	private SessionFactory sessionFactory ;
	
	private ComboPooledDataSource ds;
	
	private Connection con;
	
	public BaseDaoImpl(){
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class<T>) type.getActualTypeArguments()[0];
	}
	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory; 
	}
	
	@Resource
	public void setDs(ComboPooledDataSource ds) {
		this.ds = ds;
	}
	/**
	 * ����hql������
	 */
	public void batchEntityByHQL(String hql,Object...objects) {
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		for(int i = 0 ; i < objects.length ; i++){
			q.setParameter(i, objects[i]);
		}
		q.executeUpdate();
	}
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.hlc.archivesmgmt.dao.BaseDao#deleteEntity(java.lang.Object)
	 */
	public void deleteEntity(T t) {
		sessionFactory.getCurrentSession().delete(t);
	}
	
	/**
	 * ͨ�����飬����ɾ��
	 */
	public void deleteEntityByIDS(String hql,Integer[] ids){
		sessionFactory.getCurrentSession().createQuery(hql).setParameterList("idd", ids).executeUpdate();
	}
	
	/*������ѯ ��ָ��������������λ����*/
	public List<T> findEntityByIDS(String hql,List idl,List idc){
		return sessionFactory.getCurrentSession().createQuery(hql).setParameterList("idl", idl).setParameterList("idc", idc).list();
	}
	
	/*������ѯ ��ָ��������������λ����*/
	public List<T> findEntityByIDS(String hql,List idl,List idc,String yearStr){
		return sessionFactory.getCurrentSession().createQuery(hql).setParameterList("idl", idl).setParameterList("idc", idc).setString("yearStr","%"+yearStr+"%").list();
	}
	
	/*������ѯ ��ָ��������������λ������ָ��������*/
	public List<T> findEntityByIdsAndName(String hql,List idc,List idl,String name){
		return sessionFactory.getCurrentSession().createQuery(hql).setParameterList("idc", idc).setParameterList("idl", idl).setString("strName","%"+name+"%").list();
	}
	
	/*������ѯ ��ָ��������������λ������ָ��������*/
	public List<T> findEntityByIdsAndName(String hql,List idc,List idl,String name,String yearStr){
		return sessionFactory.getCurrentSession().createQuery(hql).setParameterList("idc", idc).setParameterList("idl", idl).setString("strName","%"+name+"%").setString("yearStr","%"+yearStr+"%").list();
	}
	
	/*��ѯ�ļ� ָ����������������ָ��λ����*/
	public List<T> findEntityByIDSnoParent(String hql,Integer id,List idl){
		return sessionFactory.getCurrentSession().createQuery(hql).setInteger(0,id).setParameterList("idl",idl).list();
	}
	
	/*��ѯ�ļ� ָ����������������ָ��λ����*/
	public List<T> findEntityByIDSnoParent(String hql,Integer id,List idl,String yearStr){
		return sessionFactory.getCurrentSession().createQuery(hql).setInteger(0,id).setParameterList("idl",idl).setString("yearStr","%"+yearStr+"%").list();
	}
	
	/*��ѯ�ļ� ָ���������������ļ�������ָ��λ����*/
	public List<T> findEntityByIdsAndNameNoParent(String hql,Integer id,List idl,String name){
		return sessionFactory.getCurrentSession().createQuery(hql).setInteger(0,id).setParameterList("idl",idl).setString("strName","%"+name+"%").list();
	}
	
	/*��ѯ�ļ� ָ���������������ļ�������ָ��λ����*/
	public List<T> findEntityByIdsAndNameNoParent(String hql,Integer id,List idl,String name,String yearStr){
		return sessionFactory.getCurrentSession().createQuery(hql).setInteger(0,id).setParameterList("idl",idl).setString("strName","%"+name+"%").setString("yearStr","%"+yearStr+"%").list();
	}
	/*
	 * (�� Javadoc) 
	* <p>Title: findEntityByHQL</p> 
	* <p>Description: </p> 
	* @param hql ��ѯ���
	* @param objects ��ѯ����
	* @return ��ѯ���
	* @see com.hlc.archivesmgmt.dao.BaseDao#findEntityByHQL(java.lang.String, java.lang.Object[])
	 */
	public List<T> findEntityByHQL(String hql,Object...objects) {
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		for(int i = 0 ; i < objects.length ; i++){
			q.setParameter(i, objects[i]);
		}
		return q.list();
	}
	
	/**
	 * 
	* @Title: searchEntityByHQL ������ģ����ѯ
	* @param @param hql 
	* @param @param searchStr
	* @param @return    �趨�ļ� 
	* @return List<T>    �������� 
	* @throws
	 */
	public List<T> searchEntityByHQL(String hql,Object...objects) {	
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		
		for(int i=0;i<objects.length;i++){
			if(objects.length-1==i){
				q.setString("strName", "%"+objects[i]+"%");
			}else{
				q.setParameter(i, objects[i]);
			}
		}
		return q.list();
	}
	
	
	
	//��ֵ����(ȷ����ѯ���ҽ����ύ��¼)
	public Object uniqueResult(String hql,Object...objects){
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		for(int i = 0 ; i < objects.length ; i++){
			q.setParameter(i, objects[i]);
		}
		return q.uniqueResult();
	}
	
	public List getCateList(Integer cateId){
		List list = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			String sql = "SELECT categoryId FROM busi_archives_category WHERE FIND_IN_SET(parentId, getAChildList(?)) or categoryId = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,cateId);
			pstmt.setInt(2,cateId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				list.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				rs.close();
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public List getLocaList(Integer locaId){
		List list = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			String sql = "SELECT locationId FROM busi_archives_location WHERE FIND_IN_SET(parentId, getLChildList (?)) or locationId = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,locaId);
			pstmt.setInt(2,locaId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				list.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				rs.close();
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public List getAList(Integer aId){
		List list = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			String sql = "SELECT archiveId FROM busi_archive WHERE categoryId IN (SELECT categoryId FROM busi_archives_category WHERE FIND_IN_SET(parentId, getAChildList (?)) OR categoryId = ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, aId);
			pstmt.setInt(2, aId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				list.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				rs.close();
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public T getEntity(Integer id) {
		return (T) sessionFactory.getCurrentSession().get(clazz,id);
	}

	public T loadEntity(Integer id) {
		return (T) sessionFactory.getCurrentSession().load(clazz,id);
	}

	public void saveEntity(T t) {
		sessionFactory.getCurrentSession().save(t);
	}

	public T saveOrUpdateEntity(T t) {
		sessionFactory.getCurrentSession().saveOrUpdate(t);
		return t;
	}

	public void updateEntity(T t) {
		sessionFactory.getCurrentSession().update(t);
	}
	
	
}
