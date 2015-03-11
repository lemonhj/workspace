package com.hlc.archivesmgmt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hlc.archivesmgmt.dao.BaseDao;
import com.hlc.archivesmgmt.entity.ArchivesCategory;
import com.hlc.archivesmgmt.entity.Archive;
import com.hlc.archivesmgmt.service.ZtreeService;
import com.hlc.archivesmgmt.dao.impl.ZtreeDaoImpl;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;



/**
 * 档案分类管理的实现类
 * @author shixin
 *
 */
@Service("ZtreeService")
public class ZtreeServiceImpl extends BaseServiceImpl<ArchivesCategory> implements ZtreeService {
   
	/**
	 * 注入指定的dao对象
	 */
	@Resource(name="ZtreeDao")
	public void setDao(BaseDao<ArchivesCategory> dao) {
		super.setDao(dao);
	}
	
	@Override
	public List<ArchivesCategory> Ztreeload(){
		String hql="from ArchivesCategory";
		return findEntityByHQL(hql);
	}
	
	@Override
	public String delZtreeNodeFace(Integer id){
		String hql="delete from ArchivesCategory a where a.categoryId = ?";
		try{
			batchEntityByHQL(hql,id);
		}catch(Exception e){
			return "06";
		}
		return "00";
	}

	
	@Override
	public String addZtreeNodeFace(ArchivesCategory archivesCategory){
		try{
			saveOrUpdateEntity(archivesCategory);
		}catch(Exception e){
			return "06";
		}
		return "00";
	}
	
	@Override
	public String updateZtreeNodeFace(ArchivesCategory archivesCategory){
		try{
			updateEntity(archivesCategory);
		}catch(Exception e){
			return "06";
		}
		return "00";
	}
	
	
}
