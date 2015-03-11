package com.honglicheng.dev.estate.dao;

import org.springframework.stereotype.Repository;
import com.honglicheng.dev.basic.dao.BaseDao;

/**
 	* A data access object (DAO) providing persistence and search support for CorpRoomBuildComIndChild entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.honglicheng.dev.estate.model.CorpRoomBuildComIndChild
  * @author MyEclipse Persistence Tools 
 */
@Repository("CorpRoomBuildComIndChildDAO")
public class CorpRoomBuildComIndChildDAO extends BaseDao  {
	

	protected void initDao() {
		//do nothing
	}
}