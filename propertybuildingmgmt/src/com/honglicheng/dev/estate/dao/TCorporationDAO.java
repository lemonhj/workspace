package com.honglicheng.dev.estate.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import com.honglicheng.dev.basic.dao.BaseDao;
import com.honglicheng.dev.estate.model.TCorporation;

/**
 * A data access object (DAO) providing persistence and search support for
 * TCorporation entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.honglicheng.dev.estate.model.TCorporation
 * @author MyEclipse Persistence Tools
 */
@Repository("TCorporationDAO")
public class TCorporationDAO extends BaseDao {
	private static final Log log = LogFactory.getLog(TCorporationDAO.class);
	// property constants
	public static final String CORP_NAME = "corpName";
	public static final String CORP_ALIAS = "corpAlias";
	public static final String CORP_FULLNAME = "corpFullname";
	public static final String CORP_INDUSTRY = "corpIndustry";
	public static final String CORP_SIZES = "corpSizes";
	public static final String CORP_CONTACTOR = "corpContactor";
	public static final String CORP_TEL = "corpTel";

	protected void initDao() {
		// do nothing
	}

	public void save(TCorporation transientInstance) {
		log.debug("saving TCorporation instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TCorporation persistentInstance) {
		log.debug("deleting TCorporation instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public void updatestateitem(Integer id,Integer buildid){
		try {
			String queryString = "update t_corporation set corp_state = '"+id+"' where corp_id = '"+buildid+"'" ;          
			 SQLQuery query=getSession().createSQLQuery(queryString);
			 query.executeUpdate();
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}

	public TCorporation findById(java.lang.Integer id) {
		log.debug("getting TCorporation instance with id: " + id);
		try {
			TCorporation instance = (TCorporation) getHibernateTemplate().get(
					"com.honglicheng.dev.estate.model.TCorporation", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TCorporation instance) {
		log.debug("finding TCorporation instance by example");
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
		log.debug("finding TCorporation instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TCorporation as model where model."
					+ propertyName + " = ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public List findByLikeCorpName(Object... value){
		try {
			String queryString = "select DISTINCT * from t_corporation  where building_id = '"+value[1]+"' AND corp_name like '%"+value[0]+"%'" ;          
			 SQLQuery query=getSession().createSQLQuery(queryString);
				return query.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public List<Integer> findIndustryByBuildId(Integer id){
		try {
			String queryString = "SELECT DISTINCT a.`corp_industry` FROM t_corporation AS a WHERE a.`building_id` = '"+id+"'";         
			 SQLQuery query=getSession().createSQLQuery(queryString);
				return query.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	
	

	public List<TCorporation> findByCorpName(Object corpName) {
		return findByProperty(CORP_NAME, corpName);
	}

	public List findByCorpAlias(Object corpAlias) {
		return findByProperty(CORP_ALIAS, corpAlias);
	}

	public List findByCorpFullname(Object corpFullname) {
		return findByProperty(CORP_FULLNAME, corpFullname);
	}

	public List findByCorpIndustry(Object corpIndustry) {
		return findByProperty(CORP_INDUSTRY, corpIndustry);
	}

	public List findByCorpSizes(Object corpSizes) {
		return findByProperty(CORP_SIZES, corpSizes);
	}

	public List findByCorpContactor(Object corpContactor) {
		return findByProperty(CORP_CONTACTOR, corpContactor);
	}

	public List findByCorpTel(Object corpTel) {
		return findByProperty(CORP_TEL, corpTel);
	}

	public List findAll() {
		log.debug("finding all TCorporation instances");
		try {
			String queryString = "from TCorporation";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TCorporation merge(TCorporation detachedInstance) {
		log.debug("merging TCorporation instance");
		try {
			TCorporation result = (TCorporation) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TCorporation instance) {
		log.debug("attaching dirty TCorporation instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TCorporation instance) {
		log.debug("attaching clean TCorporation instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TCorporationDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (TCorporationDAO) ctx.getBean("TCorporationDAO");
	}
}