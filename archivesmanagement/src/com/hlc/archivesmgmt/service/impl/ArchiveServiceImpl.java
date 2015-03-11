package com.hlc.archivesmgmt.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hlc.archivesmgmt.dao.BaseDao;
import com.hlc.archivesmgmt.dao.impl.archiveCateDaoImpl;
import com.hlc.archivesmgmt.entity.Archive;
import com.hlc.archivesmgmt.service.ArchiveCateService;
import com.hlc.archivesmgmt.service.ArchiveService;
import com.hlc.archivesmgmt.service.ZtreelocationService;

/**
 * ���������ʵ����
 * @author linbotao
 *
 */
@Service("archiveService")
public class ArchiveServiceImpl extends BaseServiceImpl<Archive> implements
ArchiveService{
	
	@Resource
	private ArchiveCateService archiveCateService;
	
	@Resource
	private ZtreelocationService archivesLocationService;
	/**
	 * ����setDao����,ע��ָ����dao����
	 */
	@Resource(name="archiveDao")
	public void setDao(BaseDao<Archive> dao) {
		super.setDao(dao);
	}

	@Override
	public List<Archive> findArchiveById(Integer id) {
		String hql = "from Archive a where a.archivesCategory.categoryId = ? ORDER BY directoryId ASC";
		return findEntityByHQL(hql,id);
	}

	/*����ģ����ѯ*/
	@Override
	public List<Archive> findArchiveByName(String name) {
		String hql = "from Archive a where a.archiveName like (:strName)";
		return searchEntityByHQL(hql, name);
	}
	
	/*�����߼���ѯ3������*/
	@Override
	public List<Archive> findArchiveByList3(Integer as_categoryName,Integer as_filePath_a,String as_archiveName_a) {
		List idc = archiveCateService.findCateChildNodes(as_categoryName);
		List idl = archivesLocationService.findLocaChildNodes(as_filePath_a);
		String hql = "from Archive a where a.archivesCategory.categoryId in (:idc) and a.archivesLocation.locationId in (:idl) and a.archiveName like (:strName)";
		return findEntityByIdsAndName(hql, idc, idl, as_archiveName_a);
	}
	
	/*�����߼���ѯ2������*/
	@Override
	public List<Archive> findArchiveByList2(Integer as_categoryName,
			Integer as_filePath_a) {
		List idc = archiveCateService.findCateChildNodes(as_categoryName);
		List idl = archivesLocationService.findLocaChildNodes(as_filePath_a);
		String hql = "from Archive a where a.archivesLocation.locationId in (:idl) and a.archivesCategory.categoryId in (:idc)";
		return findEntityByIDS(hql,idl,idc);
	}
	
	/*��ȡָ�����ͽڵ��µ������ĵ�*/
	@Override
	public List findAChildNodes(Integer id){
		return getAList(id);
	}
	
	@Override
	public void delArchiveById(Integer id){
		String hql= "delete from Archive where archiveId = ?";
		batchEntityByHQL(hql,id);
	}


	
//	@Override
//	public String delArchiveById(Integer id){
//		try{
//		List<Archive> viewlist=loadArchiveById(id);
//		String hql = "delete from Archive where archiveId = ?";
//		if(viewlist.size()>0){
//			for(int i=0;i<viewlist.size();i++){
//				batchEntityByHQL(hql,viewlist.get(i).getArchiveId());
//			}
//		}
//		}catch(Exception e){
//			return "06";
//		}
//		
//		
//		return "00";
//	}
	
	
	
}
