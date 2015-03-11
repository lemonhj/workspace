package com.hlc.archivesmgmt.service;


import java.util.List;

import com.hlc.archivesmgmt.entity.FileDescription;


/**
 * ���������ļ���¼��
 * @author linbotao
 *
 */
public interface FileDescService extends BaseService<FileDescription> {

	public List<FileDescription> findFileDescById(Integer id);

	public void deleteEntitys(Integer[] ids);

	
	/*ģ����ѯ�ļ���Ϣ*/
	public List<FileDescription> findFileDescByName(String fileName);
	
	/*�߼���ѯ�ļ���Ϣ3��������ָ������������*/
	public List<FileDescription> findFileDescByL(Integer as_archiveName_f,Integer as_filePath_f,String as_fileName,String as_date_f);
	
	/*�߼���ѯ�ļ���Ϣ3��������ָ������������*/
	public List<FileDescription> findFileDescByList3(Integer as_archiveName_f,Integer as_filePath_f,String as_fileName);
	
	/*�߼���ѯ�ļ���Ϣ2��������ָ������������*/
	public List<FileDescription> findFileDescByList2(Integer as_archiveName_f,Integer as_filePath_f);
	
	/*�߼���ѯ�ļ���Ϣ2��������ָ������������*/
	public List<FileDescription> findFileDescByList2(Integer as_archiveName_f,Integer as_filePath_f,String yearStr);
	
	/*�߼���ѯ�ļ���Ϣ3����������ָ������������*/
	public List<FileDescription> findFileDescByList3NoName(Integer as_archiveName_f,Integer as_filePath_f,String as_fileName);
	
	/*�߼���ѯ�ļ���Ϣ2����������ָ������������*/
	public List<FileDescription> findFileDescByList2NoName(Integer as_archiveName_f,Integer as_filePath_f);
	
	public void updateArchivId(Integer id,Integer arvid);

	List<FileDescription> findFileDescByList3NoName(Integer as_archiveName_f,Integer as_filePath_f, String as_fileName, String yearStr);

	List<FileDescription> findFileDescByList2NoName(Integer as_archiveName_f,Integer as_filePath_f, String yearStr);
	
	

	

}
