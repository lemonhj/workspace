package com.hlc.archivesmgmt.service;

import java.util.List;

import com.hlc.archivesmgmt.entity.Archive;
import com.hlc.archivesmgmt.entity.ArchivesCategory;


/**
 * 操作档案类型类
 * @author linbotao
 *
 */
public interface ArchiveCateService extends BaseService<ArchivesCategory> {

	public List<ArchivesCategory> loadCategory();
		
	/*获取指定类型节点下包括本身节点在内的所有子节点*/
	public List findCateChildNodes(Integer as_categoryName);
}
