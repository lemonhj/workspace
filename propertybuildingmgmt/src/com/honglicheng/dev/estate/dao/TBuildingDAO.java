package com.honglicheng.dev.estate.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import com.honglicheng.dev.basic.dao.BaseDao;
import com.honglicheng.dev.estate.model.TBuilding;

/**
 	* A data access object (DAO) providing persistence and search support for TBuilding entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.honglicheng.dev.estate.model.TBuilding
  * @author MyEclipse Persistence Tools 
 */
@Repository("TBuildingDAO")
public class TBuildingDAO extends BaseDao  {
		 private static final Log log = LogFactory.getLog(TBuildingDAO.class);
		//property constants
	public static final String BUIL_COMMUNITY = "builCommunity";
	public static final String BUIL_ORDER = "builOrder";
	public static final String BUIL_NO = "builNo";
	public static final String BUIL_NAME = "builName";
	public static final String BUIL_CREATE = "builCreate";
	public static final String BUIL_FLOOR = "builFloor";
	public static final String BUIL_MEMO = "builMemo";
	public static final String BUILD_BOTTOM = "buildBottom";



	protected void initDao() {
		//do nothing
	}
    
    public void save(TBuilding transientInstance) {
        log.debug("saving TBuilding instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(TBuilding persistentInstance) {
        log.debug("deleting TBuilding instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public TBuilding findById( java.lang.Integer id) {
        log.debug("getting TBuilding instance with id: " + id);
        try {
            TBuilding instance = (TBuilding) getHibernateTemplate()
                    .get("com.honglicheng.dev.estate.model.TBuilding", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(TBuilding instance) {
        log.debug("finding TBuilding instance by example");
        try {
            List results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding TBuilding instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from TBuilding as model where model." 
         						+ propertyName + "= ?";
		 return getHibernateTemplate().find(queryString, value);
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByBuilCommunity(Object builCommunity
	) {
		return findByProperty(BUIL_COMMUNITY, builCommunity
		);
	}
	
	public List findByBuilOrder(Object builOrder
	) {
		return findByProperty(BUIL_ORDER, builOrder
		);
	}
	
	public List findByBuilNo(Object builNo
	) {
		return findByProperty(BUIL_NO, builNo
		);
	}
	
	public List findByBuilName(Object builName
	) {
		return findByProperty(BUIL_NAME, builName
		);
	}
	
	public List findByBuilCreate(Object builCreate
	) {
		return findByProperty(BUIL_CREATE, builCreate
		);
	}
	
	public List findByBuilFloor(Object builFloor
	) {
		return findByProperty(BUIL_FLOOR, builFloor
		);
	}
	
	public List findByBuilMemo(Object builMemo
	) {
		return findByProperty(BUIL_MEMO, builMemo
		);
	}
	
	public List findByBuildBottom(Object buildBottom
	) {
		return findByProperty(BUILD_BOTTOM, buildBottom
		);
	}
	

	public List findAll() {
		log.debug("finding all TBuilding instances");
		try {
			String queryString = "from TBuilding";
		 	return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List findAllbyId(String areaid) {
		log.debug("finding all TBuilding instances");
		try {
			String queryString = "from TBuilding where buil_community in ("+areaid+")";
		 	return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public TBuilding merge(TBuilding detachedInstance) {
        log.debug("merging TBuilding instance");
        try {
            TBuilding result = (TBuilding) getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(TBuilding instance) {
        log.debug("attaching dirty TBuilding instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(TBuilding instance) {
        log.debug("attaching clean TBuilding instance");
        try {
                      	getHibernateTemplate().lock(instance, LockMode.NONE);
                        log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static TBuildingDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (TBuildingDAO) ctx.getBean("TBuildingDAO");
	}

}