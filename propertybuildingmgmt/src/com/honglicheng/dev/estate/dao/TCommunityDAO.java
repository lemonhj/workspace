package com.honglicheng.dev.estate.dao;

import java.util.List;




import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import com.honglicheng.dev.basic.dao.BaseDao;
import com.honglicheng.dev.estate.model.TCommunity;

/**
 * A data access object (DAO) providing persistence and search support for
 * TCommunity entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.honglicheng.dev.estate.model.TCommunity
 * @author MyEclipse Persistence Tools
 */
@Repository("TCommunityDAO")
public class TCommunityDAO extends BaseDao {
	private static final Log log = LogFactory.getLog(TCommunityDAO.class);
	// property constants
	public static final String COMM_PARENT = "commParent";
	public static final String COMM_ORDER = "commOrder";
	public static final String COMM_NO = "commNo";
	public static final String COMM_NAME = "commName";

	protected void initDao() {
		// do nothing
	}

	public void save(TCommunity transientInstance) {
		log.debug("saving TCommunity instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TCommunity persistentInstance) {
		log.debug("deleting TCommunity instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TCommunity findById(java.lang.Integer id) {
		log.debug("getting TCommunity instance with id: " + id);
		try {
			TCommunity instance = (TCommunity) getHibernateTemplate().get(
					"com.honglicheng.dev.estate.model.TCommunity", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TCommunity instance) {
		log.debug("finding TCommunity instance by example");
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
		log.debug("finding TCommunity instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TCommunity as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCommParent(Object commParent) {
		return findByProperty(COMM_PARENT, commParent);
	}

	public List findByCommOrder(Object commOrder) {
		return findByProperty(COMM_ORDER, commOrder);
	}

	public List findByCommNo(Object commNo) {
		return findByProperty(COMM_NO, commNo);
	}

	public List findByCommName(Object commName) {
		return findByProperty(COMM_NAME, commName);
	}

	public List findAll() {
		log.debug("finding all TCommunity instances");
		try {
			String queryString = "from TCommunity";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List findAllByCommid(Integer id) {
		try {
		    String queryString = "SELECT comm_id,comm_parent,comm_order,comm_no,comm_name,comm_endNode FROM t_community WHERE FIND_IN_SET(comm_Id, getChildList("+id+"))";
		    SQLQuery query=getSession().createSQLQuery(queryString).addEntity(TCommunity.class);
		    return query.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TCommunity merge(TCommunity detachedInstance) {
		log.debug("merging TCommunity instance");
		try {
			TCommunity result = (TCommunity) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TCommunity instance) {
		log.debug("attaching dirty TCommunity instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TCommunity instance) {
		log.debug("attaching clean TCommunity instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TCommunityDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TCommunityDAO) ctx.getBean("TCommunityDAO");
	}
}