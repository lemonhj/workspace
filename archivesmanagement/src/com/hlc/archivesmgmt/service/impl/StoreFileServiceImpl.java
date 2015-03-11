package com.hlc.archivesmgmt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hlc.archivesmgmt.dao.BaseDao;
import com.hlc.archivesmgmt.entity.ArchivesStoreFile;
import com.hlc.archivesmgmt.service.StoreFileService;

/**
 * 
* @ClassName: FileDescServiceImpl 
* @Description: �ĵ���¼������
* @author linbotao
* @date 2014��8��27�� ����2:14:42 
*
 */
@Service("storeFileService")
public class StoreFileServiceImpl extends BaseServiceImpl<ArchivesStoreFile> implements StoreFileService {
	/**
	 * ����setDao����,ע��ָ����dao����
	 */
	@Resource(name="fileDescDao")
	public void setDao(BaseDao<ArchivesStoreFile> dao) {
		super.setDao(dao);
	}

	@Override
	public List<ArchivesStoreFile> findStoreFileById(Integer id) {
		String hql = "from ArchivesStoreFile a where a.fileDescription.fileId = ?";
		return findEntityByHQL(hql,id);
	}

	@Override
	public void deleteEntitys(Integer[] ids) {
		String hql = "delete from ArchivesStoreFile a where a.fileDescription.fileId in (:idd)";
		deleteEntityByIDS(hql, ids);
	}

	@Override
	public void delStoreFileById(Integer id) {
		String hql = "delete from ArchivesStoreFile a where a.storeFileId = ?";
		batchEntityByHQL(hql, id);
	}

}
