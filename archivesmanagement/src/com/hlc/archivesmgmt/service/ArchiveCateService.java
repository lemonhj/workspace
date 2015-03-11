package com.hlc.archivesmgmt.service;

import java.util.List;

import com.hlc.archivesmgmt.entity.Archive;
import com.hlc.archivesmgmt.entity.ArchivesCategory;


/**
 * ��������������
 * @author linbotao
 *
 */
public interface ArchiveCateService extends BaseService<ArchivesCategory> {

	public List<ArchivesCategory> loadCategory();
		
	/*��ȡָ�����ͽڵ��°�������ڵ����ڵ������ӽڵ�*/
	public List findCateChildNodes(Integer as_categoryName);
}
