package com.honglicheng.dev.basic.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 * 抽象的dao实现
 */
@Repository("daoI")
public class BaseDaoImpl<T> implements BaseDaoI<T> {
		//实体类的描述符
		private Class<T> clazz ;
		//会话工厂
		private SessionFactory sessionFactory ;
		
		public BaseDaoImpl(){
			ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
			clazz = (Class<T>) type.getActualTypeArguments()[0];
		}
		@Resource
		public void setSessionFactory(SessionFactory sessionFactory) {
			this.sessionFactory = sessionFactory;
		}

		/**
		 * 按照hql语句更新
		 */
		public void batchEntityByHQL(String hql,Object...objects) {
			Query q = sessionFactory.getCurrentSession().createQuery(hql);
			for(int i = 0 ; i < objects.length ; i++){
				q.setParameter(i, objects[i]);
			}
			q.executeUpdate();
		}

		public void deleteEntity(T t) {
			sessionFactory.getCurrentSession().delete(t);
		}
		
		/**
		 * 通过数组，批量删除
		 */
		public void deleteEntityByIDS(String hql,Integer[] ids){
			sessionFactory.getCurrentSession().createQuery(hql).setParameterList("idd", ids).executeUpdate();
		}

		/**
		 * 按照hql语句查询
		 */
		public List<T> findEntityByHQL(String hql,Object...objects) {
			Query q = sessionFactory.getCurrentSession().createQuery(hql);
			for(int i = 0 ; i < objects.length ; i++){
				q.setParameter(i, objects[i]);
			}
			return q.list();
		}
		
		//单值检索(确保查询有且仅有提交记录)
		public Object uniqueResult(String hql,Object...objects){
			Query q = sessionFactory.getCurrentSession().createQuery(hql);
			for(int i = 0 ; i < objects.length ; i++){
				q.setParameter(i, objects[i]);
			}
			return q.uniqueResult();
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

		public void saveOrUpdateEntity(T t) {
			sessionFactory.getCurrentSession().saveOrUpdate(t);
		}

		public void updateEntity(T t) {
			sessionFactory.getCurrentSession().update(t);
		}

}
