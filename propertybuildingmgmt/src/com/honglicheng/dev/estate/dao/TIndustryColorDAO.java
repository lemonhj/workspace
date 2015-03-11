package com.honglicheng.dev.estate.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.honglicheng.dev.basic.dao.BaseDao;
import com.honglicheng.dev.estate.model.TIndustryColor;

/**
 * A data access object (DAO) providing persistence and search support for
 * TIndustryColor entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.honglicheng.dev.estate.model.TIndustryColor
 * @author MyEclipse Persistence Tools
 */
@Repository("TIndustryColorDAO")
public class TIndustryColorDAO extends BaseDao {
	private static final Log log = LogFactory.getLog(TIndustryColorDAO.class);
	// property constants
	public static final String INDU_COLOR = "induColor";

	protected void initDao() {
		// do nothing
	}

	public void save(TIndustryColor transientInstance) {
		log.debug("saving TIndustryColor instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TIndustryColor persistentInstance) {
		log.debug("deleting TIndustryColor instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TIndustryColor findById(java.lang.Integer id) {
		log.debug("getting TIndustryColor instance with id: " + id);
		try {
			TIndustryColor instance = (TIndustryColor) getHibernateTemplate()
					.get("com.honglicheng.dev.estate.model.TIndustryColor", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TIndustryColor instance) {
		log.debug("finding TIndustryColor instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding TIndustryColor instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TIndustryColor as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByInduColor(Object induColor) {
		return findByProperty(INDU_COLOR, induColor);
	}

	public List findAll() {
		log.debug("finding all TIndustryColor instances");
		try {
			String queryString = "from TIndustryColor";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TIndustryColor merge(TIndustryColor detachedInstance) {
		log.debug("merging TIndustryColor instance");
		try {
			TIndustryColor result = (TIndustryColor) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TIndustryColor instance) {
		log.debug("attaching dirty TIndustryColor instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TIndustryColor instance) {
		log.debug("attaching clean TIndustryColor instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

}