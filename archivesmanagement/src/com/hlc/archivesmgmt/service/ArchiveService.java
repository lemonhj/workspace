package com.hlc.archivesmgmt.service;

import java.util.List;

import com.hlc.archivesmgmt.entity.Archive;


/**
 * 操作档案类
 * @author linbotao
 *
 */
public interface ArchiveService extends BaseService<Archive> {

	public List<Archive> findArchiveById(Integer Id);
	
	/*档案模糊查询*/
	public List<Archive> findArchiveByName(String name);
	
	/*档案高级查询 3个参数*/
	public List<Archive> findArchiveByList3(Integer as_categoryName,Integer as_filePath_a,String as_archiveName_a);

	/*档案高级查询2个参数*/
	public List<Archive> findArchiveByList2(Integer as_categoryName,Integer as_filePath_a);
	
	/*获取指定类型节点下的所有文档*/
	public List findAChildNodes(Integer id);
	
	public void delArchiveById(Integer id);
}
