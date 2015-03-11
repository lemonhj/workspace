package com.hlc.archivesmgmt.service;

import java.util.List;

import com.hlc.archivesmgmt.entity.Archive;


/**
 * ����������
 * @author linbotao
 *
 */
public interface ArchiveService extends BaseService<Archive> {

	public List<Archive> findArchiveById(Integer Id);
	
	/*����ģ����ѯ*/
	public List<Archive> findArchiveByName(String name);
	
	/*�����߼���ѯ 3������*/
	public List<Archive> findArchiveByList3(Integer as_categoryName,Integer as_filePath_a,String as_archiveName_a);

	/*�����߼���ѯ2������*/
	public List<Archive> findArchiveByList2(Integer as_categoryName,Integer as_filePath_a);
	
	/*��ȡָ�����ͽڵ��µ������ĵ�*/
	public List findAChildNodes(Integer id);
	
	public void delArchiveById(Integer id);
}
