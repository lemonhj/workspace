package com.honglicheng.dev.estate.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import com.honglicheng.dev.basic.dao.BaseDao;
import com.honglicheng.dev.estate.model.ComIndustry;

/**
 * A data access object (DAO) providing persistence and search support for
 * ComIndustry entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.honglicheng.dev.estate.model.ComIndustry
 * @author MyEclipse Persistence Tools
 */

@Repository("ComIndustryDAO")
public class ComIndustryDAO extends BaseDao {
	private static final Log log = LogFactory.getLog(ComIndustryDAO.class);
	// property constants
	public static final String INDU_PARENT = "induParent";
	public static final String INDU_NAME = "induName";
	public static final String INDU_ALIAS = "induAlias";
	public static final String INDU_FULLNAME = "induFullname";

	protected void initDao() {
		// do nothing
	}
	
	public void save(ComIndustry transientInstance) {
		log.debug("saving ComIndustry instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ComIndustry persistentInstance) {
		log.debug("deleting ComIndustry instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ComIndustry findById(java.lang.Integer id) {
		log.debug("getting ComIndustry instance with id: " + id);
		try {
			ComIndustry instance = (ComIndustry) getHibernateTemplate().get(
					"com.honglicheng.dev.estate.model.ComIndustry", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ComIndustry instance) {
		log.debug("finding ComIndustry instance by example");
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
		log.debug("finding ComIndustry instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from ComIndustry as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByInduParent(Object induParent) {
		return findByProperty(INDU_PARENT, induParent);
	}

	public List findByInduName(Object induName) {
		return findByProperty(INDU_NAME, induName);
	}

	public List findByInduAlias(Object induAlias) {
		return findByProperty(INDU_ALIAS, induAlias);
	}

	public List findByInduFullname(Object induFullname) {
		return findByProperty(INDU_FULLNAME, induFullname);
	}

	public List findAll() {
		log.debug("finding all ComIndustry instances");
		try {
			String queryString = "from ComIndustry";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public List findcantDeleteIds(String del){
		try {
			String queryString = "SELECT b.`indu_name` FROM t_corporation a,com_industry b " 
								+"WHERE a.`corp_industry` = b.`indu_id` AND b.`indu_id` IN (" + del + ") " 
								+"GROUP BY b.`indu_id`";
			SQLQuery query=getSession().createSQLQuery(queryString);
			return query.list();
		} catch (RuntimeException re) {
			log.error("find failed", re);
			throw re;
		}
	}
	
	public ComIndustry merge(ComIndustry detachedInstance) {
		log.debug("merging ComIndustry instance");
		try {
			ComIndustry result = (ComIndustry) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ComIndustry instance) {
		log.debug("attaching dirty ComIndustry instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ComIndustry instance) {
		log.debug("attaching clean ComIndustry instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}