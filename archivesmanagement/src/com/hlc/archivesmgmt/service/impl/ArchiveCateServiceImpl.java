package com.hlc.archivesmgmt.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hlc.archivesmgmt.dao.BaseDao;
import com.hlc.archivesmgmt.entity.ArchivesCategory;
import com.hlc.archivesmgmt.service.ArchiveCateService;

/**
 * 档案服务的实现类
 * @author linbotao
 *
 */
@Service("archiveCateService")
public class ArchiveCateServiceImpl extends BaseServiceImpl<ArchivesCategory> implements
ArchiveCateService{
	/**
	 * 重新setDao方法,注入指定的dao对象
	 */
	@Resource(name="archiveCateDao")
	public void setDao(BaseDao<ArchivesCategory> dao) {
		super.setDao(dao);
	}

	@Override
	public List<ArchivesCategory> loadCategory() {
		String hql = "from ArchivesCategory";
		return findEntityByHQL(hql);
	}
	
	/*获取指定节点下包括自身节点在内的所有子节点*/
	@Override
	public List findCateChildNodes(Integer as_categoryName) {
		return getCateList(as_categoryName);
	}
}
