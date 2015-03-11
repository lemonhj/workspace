package com.honglicheng.dev.estate.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import com.honglicheng.dev.basic.dao.BaseDao;

/**
 * A data access object (DAO) providing persistence and search support for
 * CorpRoomBuildComInd entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.honglicheng.dev.estate.dao.CorpRoomBuildComInd
 * @author MyEclipse Persistence Tools
 */

@Repository("CorpRoomBuildComIndDAO")
public class CorpRoomBuildComIndDAO extends BaseDao {
	private static final Log log = LogFactory
			.getLog(CorpRoomBuildComIndDAO.class);

	// property constants

	protected void initDao() {
		// do nothing
	}
}