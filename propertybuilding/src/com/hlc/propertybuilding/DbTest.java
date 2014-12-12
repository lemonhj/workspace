package com.hlc.propertybuilding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hlc.propertybuilding.dao.impl.FloorDaoImpl;
import com.hlc.propertybuilding.domain.TFloor;

@Service
public class DbTest {
	@Autowired
	private static FloorDaoImpl floorDao;
	
	public TFloor loadFloor(Integer floorId){
		return floorDao.getById(floorId);
	}
	

	public static FloorDaoImpl getFloorDao() {
		return floorDao;
	}

	public static void setFloorDao(FloorDaoImpl floorDao) {
		DbTest.floorDao = floorDao;
	}
	
	
}
