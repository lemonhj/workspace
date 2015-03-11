package com.hlc.archivesmgmt.service;


import java.util.List;

import com.hlc.archivesmgmt.entity.ArchivesStoreFile;


/**
 * ���������ļ���¼��
 * @author linbotao
 *
 */
public interface StoreFileService extends BaseService<ArchivesStoreFile> {

	public List<ArchivesStoreFile> findStoreFileById(Integer id);

	void deleteEntitys(Integer[] ids);

	public void delStoreFileById(Integer id);
	
}
