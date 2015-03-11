package com.honglicheng.dev.estate.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.honglicheng.dev.basic.dao.BaseDao;
import com.honglicheng.dev.estate.model.TFloor;

/**
 * A data access object (DAO) providing persistence and search support for
 * TFloor entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.honglicheng.dev.estate.model.TFloor
 * @author MyEclipse Persistence Tools
 */
@Repository("TFloorDAO")
public class TFloorDAO extends BaseDao {
	private static final Log log = LogFactory.getLog(TFloorDAO.class);
	// property constants
	public static final String FLOO_BUILDING = "flooBuilding";
	public static final String FLOO_ORDER = "flooOrder";
	public static final String FLOO_NO = "flooNo";
	public static final String FLOO_TYPE = "flooType";
	public static final String FLOO_HEIGHT = "flooHeight";

	protected void initDao() {
		// do nothing
	}

	public void save(TFloor transientInstance) {
		log.debug("saving TFloor instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TFloor persistentInstance) {
		log.debug("deleting TFloor instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TFloor findById(java.lang.Integer id) {
		log.debug("getting TFloor instance with id: " + id);
		try {
			TFloor instance = (TFloor) getHibernateTemplate().get(
					"com.honglicheng.dev.estate.model.TFloor", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TFloor instance) {
		log.debug("finding TFloor instance by example");
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
		log.debug("finding TFloor instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TFloor as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByFlooBuilding(Object flooBuilding) {
		return findByProperty(FLOO_BUILDING, flooBuilding);
	}

	public List findByFlooOrder(Object flooOrder) {
		return findByProperty(FLOO_ORDER, flooOrder);
	}

	public List findByFlooNo(Object flooNo) {
		return findByProperty(FLOO_NO, flooNo);
	}

	public List findByFlooType(Object flooType) {
		return findByProperty(FLOO_TYPE, flooType);
	}

	public List findByFlooHeight(Object flooHeight) {
		return findByProperty(FLOO_HEIGHT, flooHeight);
	}

	public List findAll() {
		log.debug("finding all TFloor instances");
		try {
			String queryString = "from TFloor";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List findAllById(Integer id){
		log.debug("finding all TFloor instances");
		try {
			String queryString = "from TFloor where flooBuilding = ? order by floo_order desc";
			return getHibernateTemplate().find(queryString,id);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List findAllByIddata(Integer id){
		try {
			String queryString = "SELECT a.`floo_id`,a.`floo_order`,b.`room_id`,b.`room_no`,b.`room_order`,b.`room_state`"
					+",b.`room_area`,c.`corp_id`,c.`corp_name`,c.`corp_alias`,c.`corp_fullname`,c.`corp_contacts`,c.`corp_sizes`,c.`corp_tel`,c.`corp_industry`,c.`corp_state`,b.`room_owner`,b.`room_owner_tel` FROM t_floor AS a " 
					+"LEFT JOIN t_room AS b ON b.`room_floor` = a.`floo_id` " 
					+"LEFT JOIN t_corporation AS c ON c.`corp_id` = b.`room_corporation` " 
					+"WHERE a.`floo_building` = '"+id+"' ORDER BY a.`floo_id`,b.`room_order` ASC";

	    SQLQuery query=getSession().createSQLQuery(queryString);
		return query.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List findAllByIdnow(Integer id,String flag){
		log.debug("finding all TFloor instances");
		try {
//			String where  = "select buil_id from t_building where buil_community in (SELECT comm_id FROM t_community  WHERE  FIND_IN_SET(comm_Id, getChildList("+id+")))";
//			SQLQuery query1=getSession().createSQLQuery(where);
//			if(query1.list().size()>0){
			if(flag.equals("false")){
				String queryString = "select b.indu_name,SUM(a.amount),SUM(a.areas),c.`indu_color`  from t_comm_industry a, com_industry b ,t_industry_color c  WHERE b.indu_id = a.indu_id AND b.`indu_id` = c.`indu_id` AND (a.amount) <> 0 "
									+"AND (a.areas) <> 0 AND a.comm_id in (SELECT comm_id FROM t_community  WHERE  FIND_IN_SET(comm_Id, getChildList("+id+"))) GROUP BY  a.indu_id";
				SQLQuery query=getSession().createSQLQuery(queryString);
				return query.list();
			}else{
				String queryString = "SELECT c.`indu_name`,COUNT(1),SUM(a.all_area),d.`indu_color`  FROM " 
						+"(SELECT SUM(t.`room_area`) AS all_area,t.room_corporation FROM t_room t, t_floor tt "
						+"WHERE t.`room_floor`=tt.`floo_id` AND tt.`floo_building`="+id 
						+" AND t.`room_corporation` IS NOT NULL GROUP BY room_corporation ) AS a,t_corporation b,com_industry c ,t_industry_color d "
						+"WHERE a.room_corporation = b.`corp_id` AND b.`corp_industry` = c.`indu_id` AND c.`indu_id` = d.`indu_id` GROUP BY c.`indu_id`";
			    SQLQuery query=getSession().createSQLQuery(queryString);
				return query.list();
			}
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public List findareafloor(Integer id,String flag){
		log.debug("finding all TFloor instances");
		try {
//			String where  = "select buil_id from t_building where buil_community in (SELECT comm_id FROM t_community  WHERE  FIND_IN_SET(comm_Id, getChildList("+id+")))";
//			SQLQuery query1=getSession().createSQLQuery(where);
//			if(query1.list().size()>0){
			if(flag.equals("false")){
				/*
				 *2表示在装修面积(有业主,有商户)
				 *3表示已入驻面积(有业主,有商户)
				 *4表示待售面积    (无业主,无商户)
				 *5表示空置面积    (有业主,无商户)
				 */
				String queryString ="SELECT 2 as a, SUM(IFNULL(t.ornamented,0))-SUM(IFNULL(t.entered, 0)) FROM t_community_report  t  WHERE comm_id IN (SELECT comm_id FROM t_community  WHERE  FIND_IN_SET(comm_Id, getChildList("+id+"))) GROUP BY  a  " 
									+"UNION ALL "
									+"SELECT 3 as a ,SUM(t.entered) FROM t_community_report  t  WHERE comm_id IN (SELECT comm_id FROM t_community  WHERE  FIND_IN_SET(comm_Id, getChildList("+id+"))) GROUP BY  a "
									+"UNION ALL "
									+"SELECT 5 as a ,SUM(IFNULL(t.notEntered,0))-SUM(IFNULL(t.workblank, 0)) FROM t_community_report  t  WHERE comm_id IN (SELECT comm_id FROM t_community  WHERE  FIND_IN_SET(comm_Id, getChildList("+id+"))) GROUP BY  a "
									+"UNION ALL "
									+"SELECT 4 as a ,SUM(t.workblank) FROM t_community_report  t  WHERE comm_id IN (SELECT comm_id FROM t_community  WHERE  FIND_IN_SET(comm_Id, getChildList("+id+"))) GROUP BY  a ";
				
				SQLQuery query=getSession().createSQLQuery(queryString);
				return query.list();
			}else{
				String queryString = "SELECT a.`room_state` AS state ,SUM(a.`room_area`) FROM t_room a,t_floor b,t_building c WHERE a.`room_floor` = b.`floo_id` "
						+ " AND b.`floo_building` = c.`buil_id` AND c.`buil_id` = " + id + " AND a.`room_state` <> 4 GROUP BY a.`room_state` "
						+ " UNION ALL "
						+ " SELECT 4 AS state,SUM(a.`room_area`) FROM t_room a,t_floor b,t_building c WHERE a.`room_floor` = b.`floo_id` AND b.`floo_building` = c.`buil_id` "
						+ " AND c.`buil_id` = " + id + " AND a.`room_state` = 4 AND (a.`room_corporation` IS NULL OR a.`room_corporation` = 0) AND (a.`room_owner` IS NULL OR a.`room_owner` = '') GROUP BY a.`room_state` "
						+ " UNION ALL "
						+ " SELECT 5 AS state,SUM(a.`room_area`) FROM t_room a,t_floor b,t_building c WHERE a.`room_floor` = b.`floo_id` AND b.`floo_building` = c.`buil_id` "
						+ " AND c.`buil_id` = " + id + " AND a.`room_state` = 4 AND (a.`room_corporation` IS NULL OR a.`room_corporation` = 0) AND ( a.`room_owner` <> '') GROUP BY a.`room_state` ";
			    SQLQuery query=getSession().createSQLQuery(queryString);
				return query.list();
			}
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List findnopfloor(Integer id,String flag){
		log.debug("finding nop instances");
		try{
//			String where  = "select buil_id from t_building where buil_community in (SELECT comm_id FROM t_community  WHERE  FIND_IN_SET(comm_Id, getChildList("+id+")))";
//			SQLQuery query1=getSession().createSQLQuery(where);
//			if(query1.list().size()>0){
			if(flag.equals("false")){
				/*
				 *1表示10以下人数规模
				 *2表示10到50人数规模
				 *3表现50到100人数规模
				 *...
				 *7表示5000以上人数规模 
				 */
				String queryString = "SELECT 1 as a, SUM(t.Lessthan10) FROM t_community_population  t WHERE t.Lessthan10 <> 0 and  t.comm_id in (SELECT comm_id FROM t_community  WHERE  FIND_IN_SET(comm_Id, getChildList("+id+")))  GROUP BY a "
									 +"UNION ALL "
									 +"SELECT 2 as a, SUM(t.10to49) FROM t_community_population  t WHERE t.10to49 <> 0 and  t.comm_id in (SELECT comm_id FROM t_community  WHERE  FIND_IN_SET(comm_Id, getChildList("+id+")))  GROUP BY a "
									 +"UNION ALL "
									 +"SELECT 3 as a, SUM(t.50to199) FROM t_community_population  t WHERE t.50to199 <> 0 and t.comm_id in (SELECT comm_id FROM t_community  WHERE  FIND_IN_SET(comm_Id, getChildList("+id+")))  GROUP BY a "
									 +"UNION ALL "
									 +"SELECT 4 as a, SUM(t.200to499) FROM t_community_population  t WHERE t.200to499 <> 0 and t.comm_id in (SELECT comm_id FROM t_community  WHERE  FIND_IN_SET(comm_Id, getChildList("+id+")))  GROUP BY a "
									 +"UNION ALL "
									 +"SELECT 5 as a, SUM(t.500to999) FROM t_community_population  t WHERE t.500to999 <> 0 and t.comm_id in (SELECT comm_id FROM t_community  WHERE  FIND_IN_SET(comm_Id, getChildList("+id+")))  GROUP BY a "
									 +"UNION ALL "
									 +"SELECT 6 as a, SUM(t.1000to4999) FROM t_community_population  t WHERE t.1000to4999 <> 0 and t.comm_id in (SELECT comm_id FROM t_community  WHERE  FIND_IN_SET(comm_Id, getChildList("+id+")))  GROUP BY a "
									 +"UNION ALL "
									 +"SELECT 7 as a, SUM(t.morethan5000) FROM t_community_population  t WHERE t.morethan5000 <> 0 and t.comm_id in (SELECT comm_id FROM t_community  WHERE  FIND_IN_SET(comm_Id, getChildList("+id+")))  GROUP BY a";
				SQLQuery query=getSession().createSQLQuery(queryString);
				return query.list();
			}else{
				String queryString = "SELECT b.`corp_sizes`,COUNT(1) FROM "
						+ " (SELECT t.room_corporation FROM t_room t, t_floor tt " 
						+ " WHERE t.`room_floor`=tt.`floo_id` AND tt.`floo_building`= " + id
						+ " AND t.`room_corporation` IS NOT NULL GROUP BY room_corporation) AS a,t_corporation b " 
						+ "	WHERE a.room_corporation = b.`corp_id` GROUP BY b.`corp_sizes` ;";
				SQLQuery query=getSession().createSQLQuery(queryString);
				return query.list();
			}
		} catch (RuntimeException re) {
			log.error("find failed", re);
			throw re;
		}
	}
	
	public List findsalfloor(Integer id,String flag){
		log.debug("finding nop instances");
		try{
//			String where  = "select buil_id from t_building where buil_community in (SELECT comm_id FROM t_community  WHERE  FIND_IN_SET(comm_Id, getChildList("+id+")))";
//			SQLQuery query1=getSession().createSQLQuery(where);
//			if(query1.list().size()>0){
			if(flag.equals("false")){
				/**
				 * 1表示自持
				 * 2表示租赁
				 */
				String queryString ="SELECT 1 as a,  SUM(t.self) FROM t_community_report  t  WHERE comm_id in (SELECT comm_id FROM t_community  WHERE  FIND_IN_SET(comm_Id, getChildList("+id+"))) GROUP BY a "
									+"UNION ALL "
									+"SELECT 2 as a, SUM(t.hire) FROM t_community_report  t  WHERE comm_id   in (SELECT comm_id FROM t_community  WHERE  FIND_IN_SET(comm_Id, getChildList("+id+")))  GROUP BY a ";
				SQLQuery query=getSession().createSQLQuery(queryString);
				return query.list();
			}else{
				String queryString = "SELECT c.`corp_state`,SUM(b.`room_area`) FROM t_floor AS a "
									+"LEFT JOIN t_room AS b ON a.`floo_id` = b.`room_floor` "
									+"LEFT JOIN t_corporation AS c ON b.`room_corporation` = c.`corp_id` "
									+"WHERE a.`floo_building` = "+id+" GROUP BY c.`corp_state`";
				SQLQuery query=getSession().createSQLQuery(queryString);
				return query.list();
			}
		} catch (RuntimeException re) {
			log.error("find failed", re);
			throw re;
		}
	}
	
	public List findInfoBySql(String sql){
		log.debug("finding info instances");
		try{
			SQLQuery query=getSession().createSQLQuery(sql);
			return query.list();
		} catch (RuntimeException re) {
			log.error("find failed", re);
			throw re;
		}
	}
	
	public TFloor merge(TFloor detachedInstance) {
		log.debug("merging TFloor instance");
		try {
			TFloor result = (TFloor) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TFloor instance) {
		log.debug("attaching dirty TFloor instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TFloor instance) {
		log.debug("attaching clean TFloor instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TFloorDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TFloorDAO) ctx.getBean("TFloorDAO");
	}
}