package com.hlc.archivesmgmt.service;


import java.util.List;

import com.hlc.archivesmgmt.entity.FileDescription;


/**
 * 操作档案文件著录类
 * @author linbotao
 *
 */
public interface FileDescService extends BaseService<FileDescription> {

	public List<FileDescription> findFileDescById(Integer id);

	public void deleteEntitys(Integer[] ids);

	
	/*模糊查询文件信息*/
	public List<FileDescription> findFileDescByName(String fileName);
	
	/*高级查询文件信息3个参数，指定所属档案名*/
	public List<FileDescription> findFileDescByL(Integer as_archiveName_f,Integer as_filePath_f,String as_fileName,String as_date_f);
	
	/*高级查询文件信息3个参数，指定所属档案名*/
	public List<FileDescription> findFileDescByList3(Integer as_archiveName_f,Integer as_filePath_f,String as_fileName);
	
	/*高级查询文件信息2个参数，指定所属档案名*/
	public List<FileDescription> findFileDescByList2(Integer as_archiveName_f,Integer as_filePath_f);
	
	/*高级查询文件信息2个参数，指定所属档案名*/
	public List<FileDescription> findFileDescByList2(Integer as_archiveName_f,Integer as_filePath_f,String yearStr);
	
	/*高级查询文件信息3个参数，不指定所属档案名*/
	public List<FileDescription> findFileDescByList3NoName(Integer as_archiveName_f,Integer as_filePath_f,String as_fileName);
	
	/*高级查询文件信息2个参数，不指定所属档案名*/
	public List<FileDescription> findFileDescByList2NoName(Integer as_archiveName_f,Integer as_filePath_f);
	
	public void updateArchivId(Integer id,Integer arvid);

	List<FileDescription> findFileDescByList3NoName(Integer as_archiveName_f,Integer as_filePath_f, String as_fileName, String yearStr);

	List<FileDescription> findFileDescByList2NoName(Integer as_archiveName_f,Integer as_filePath_f, String yearStr);
	
	

	

}
