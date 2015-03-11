package com.hlc.archivesmgmt.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hlc.archivesmgmt.dao.BaseDao;
import com.hlc.archivesmgmt.entity.ArchivesCategory;
import com.hlc.archivesmgmt.service.ArchiveCateService;

/**
 * ���������ʵ����
 * @author linbotao
 *
 */
@Service("archiveCateService")
public class ArchiveCateServiceImpl extends BaseServiceImpl<ArchivesCategory> implements
ArchiveCateService{
	/**
	 * ����setDao����,ע��ָ����dao����
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
	
	/*��ȡָ���ڵ��°�������ڵ����ڵ������ӽڵ�*/
	@Override
	public List findCateChildNodes(Integer as_categoryName) {
		return getCateList(as_categoryName);
	}
}
