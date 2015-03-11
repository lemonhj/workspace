package com.hlc.archivesmgmt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hlc.archivesmgmt.dao.BaseDao;
import com.hlc.archivesmgmt.entity.ArchivesCategory;
import com.hlc.archivesmgmt.service.ZtreeService;
import com.hlc.archivesmgmt.service.ZtreelocationService;
import com.hlc.archivesmgmt.dao.impl.ZtreeDaoImpl;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;
import com.hlc.archivesmgmt.entity.ArchivesLocation;


/**
 * ���λ�÷�������ʵ����
 * @author shixin
 *
 */
@Service("ZtreelocationService")
public class ZtreelocationServiceImpl extends BaseServiceImpl<ArchivesLocation> implements ZtreelocationService{
	/**
	 * ע��ָ����dao����
	 */
	@Resource(name="ZtreeLocationDao")
	public void setDao(BaseDao<ArchivesLocation> dao) {
		super.setDao(dao);
	}

	@Override
	public List<ArchivesLocation> ztreelocationload(){
		String hql="from ArchivesLocation";
		List<ArchivesLocation> listshow= findEntityByHQL(hql);
		return listshow;
	}
	
	public List<ArchivesLocation> islocationnode(Integer id){
		String hql="from ArchivesLocation where parentId = ?";
		List<ArchivesLocation> listshow= findEntityByHQL(hql,id);
		return listshow;
	}
	
	
	@Override
	public String dellocZtreeNodeFace(Integer id){
		String hql="delete from ArchivesLocation a where a.locationId = ?";
		try{
			List<ArchivesLocation> listshow = islocationnode(id);
			if(listshow.size()>0){
				for(int i=0;i<listshow.size();i++){
					deleteEntity(listshow.get(i));
				}
			}
			batchEntityByHQL(hql,id);
		}catch(Exception e){
			return "06";
		}
		return "00";
	}

	
	@Override
	public String addlocZtreeNodeFace(ArchivesLocation archivesLocation){

			ArchivesLocation it=saveOrUpdateEntity(archivesLocation);
			return it.getLocationId().toString();

	}
	
	@Override
	public String updatelocZtreeNodeFace(ArchivesLocation archivesLocation){
		try{
			updateEntity(archivesLocation);
		}catch(Exception e){
			return "06";
		}
		return "00";
	}
	
	/*��ȡָ���ڵ��°�������ڵ����ڵ������ӽڵ�*/
	@Override
	public List findLocaChildNodes(Integer as_filePath_a) {
		return getLocaList(as_filePath_a);
	}
	
	
	/*
	 * �Զ���������
	 * */
	@Override
	public void addlocZtreeNodeCengFace(ArchivesLocation archivesLocation){
		try{
			saveOrUpdateEntity(archivesLocation);
		}catch(Exception e){
			
		}
	}
	
}
