package com.honglicheng.dev.estate.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import com.honglicheng.dev.basic.dao.BaseDao;
import com.honglicheng.dev.estate.model.CorpNode;
import com.honglicheng.dev.estate.model.TRoom;

/**
 * A data access object (DAO) providing persistence and search support for TRoom
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.honglicheng.dev.estate.model.TRoom
 * @author MyEclipse Persistence Tools
 */
@Repository("TRoomDAO")
public class TRoomDAO extends BaseDao {
	private static final Log log = LogFactory.getLog(TRoomDAO.class);
	// property constants
	public static final String ROOM_FLOOR = "roomFloor";
	public static final String ROOM_ORDER = "roomOrder";
	public static final String ROOM_NO = "roomNo";
	public static final String ROOM_AREA = "roomArea";
	public static final String ROOM_STATE = "roomState";
	public static final String ROOM_CORPORATION = "roomCorporation";

	protected void initDao() {
		// do nothing
	}

	public void save(TRoom transientInstance) {
		log.debug("saving TRoom instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
    
	public void update(Integer roomid,Integer corpid,Integer stateid) {
		log.debug("saving TRoom instance");
		try {
			String queryString="update t_room set room_corporation = '"+corpid+"',room_state = '"+stateid+"' "
					+"where room_id = '"+roomid+"'";
			 SQLQuery query=getSession().createSQLQuery(queryString);
			 query.executeUpdate();
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}
	
	public List loadbuildcorpdao(Integer id,String name){
		log.debug("finding all TFloor instances");
		try {
			String queryString = "SELECT DISTINCT d.* FROM t_corporation AS d "
   +" WHERE building_id = '"+id+"' AND corp_name LIKE '%"+name+"%' AND corp_id != '' AND corp_id NOT IN( "
   +"SELECT b.`room_corporation` FROM  t_floor AS a LEFT JOIN t_room AS b ON b.`room_floor` = a.`floo_id` WHERE floo_building = '"+id+"' AND b.`room_corporation` !='') "
   +" UNION "
+"SELECT DISTINCT d.* FROM t_corporation AS d "
+"WHERE building_id = '"+id+"' AND corp_name LIKE '%"+name+"%' AND corp_id != '' AND corp_id IN( " 
+"SELECT b.`room_corporation` FROM  t_floor AS a LEFT JOIN t_room AS b ON b.`room_floor` = a.`floo_id` WHERE floo_building = '"+id+"' AND b.`room_corporation` !='' "
+")";
		    SQLQuery query=getSession().createSQLQuery(queryString);
			return query.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public void updateroominfo(Integer roomid,String owner,String tel) {
		log.debug("saving TRoom instance");
		try {
			String queryString="update t_room set room_owner = '"+owner+"',room_owner_tel = '"+tel+"' "
					+"where room_id = '"+roomid+"'";
			 SQLQuery query=getSession().createSQLQuery(queryString);
			 query.executeUpdate();
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}
	
	public void clearroom(Integer roomid,Integer corpid,Integer stateid) {
		log.debug("saving TRoom instance");
		try {
			String queryString="update t_room set room_corporation = '"+corpid+"',room_state = '"+stateid+"' "
					+"where room_id = '"+roomid+"'";
			 SQLQuery query=getSession().createSQLQuery(queryString);
			 query.executeUpdate();
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}
	
	
	
	public void decorateroomitem(Integer roomid,Integer corpid) {
		log.debug("saving TRoom instance");
		try {
			String queryString="update t_room set room_state = '"+corpid+"' "
					+"where room_id = '"+roomid+"'";
			 SQLQuery query=getSession().createSQLQuery(queryString);
			 query.executeUpdate();
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}
	
	public void delete(TRoom persistentInstance) {
		log.debug("deleting TRoom instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TRoom findById(java.lang.Integer id) {
		log.debug("getting TRoom instance with id: " + id);
		try {
			TRoom instance = (TRoom) getHibernateTemplate().get(
					"com.honglicheng.dev.estate.model.TRoom", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TRoom instance) {
		log.debug("finding TRoom instance by example");
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
		log.debug("finding TRoom instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TRoom as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByRoomFloor(Object roomFloor) {
		return findByProperty(ROOM_FLOOR, roomFloor);
	}

	public List findByRoomOrder(Object roomOrder) {
		return findByProperty(ROOM_ORDER, roomOrder);
	}

	public List findByRoomNo(Object roomNo) {
		return findByProperty(ROOM_NO, roomNo);
	}

	public List findByRoomArea(Object roomArea) {
		return findByProperty(ROOM_AREA, roomArea);
	}

	public List findByRoomState(Object roomState) {
		return findByProperty(ROOM_STATE, roomState);
	}

	public List findByRoomCorporation(Object roomCorporation) {
		return findByProperty(ROOM_CORPORATION, roomCorporation);
	}

	public List findAll() {
		log.debug("finding all TRoom instances");
		try {
			String queryString = "from TRoom";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TRoom merge(TRoom detachedInstance) {
		log.debug("merging TRoom instance");
		try {
			TRoom result = (TRoom) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TRoom instance) {
		log.debug("attaching dirty TRoom instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TRoom instance) {
		log.debug("attaching clean TRoom instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TRoomDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TRoomDAO) ctx.getBean("TRoomDAO");
	}
}