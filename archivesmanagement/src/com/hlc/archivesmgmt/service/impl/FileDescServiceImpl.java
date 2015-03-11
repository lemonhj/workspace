package com.hlc.archivesmgmt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hlc.archivesmgmt.dao.BaseDao;
import com.hlc.archivesmgmt.entity.FileDescription;
import com.hlc.archivesmgmt.service.ArchiveService;
import com.hlc.archivesmgmt.service.FileDescService;
import com.hlc.archivesmgmt.service.ZtreelocationService;

/**
 * 
* @ClassName: FileDescServiceImpl 
* @Description: �ĵ���¼������
* @author linbotao
* @date 2014��8��27�� ����2:14:42 
*
 */
@Service("fileDescService")
public class FileDescServiceImpl extends BaseServiceImpl<FileDescription> implements FileDescService {
	
	@Resource
	private ZtreelocationService ztreelocationService;
	
	@Resource
	private ArchiveService archiveService;
	/**
	 * ����setDao����,ע��ָ����dao����
	 */
	@Resource(name="fileDescDao")
	public void setDao(BaseDao<FileDescription> dao) {
		super.setDao(dao);
	}

	@Override
	public List<FileDescription> findFileDescById(Integer id) {
		String hql = "from FileDescription f where f.archive.archiveId = ?";
		return findEntityByHQL(hql,id);
	}

	@Override
	public void deleteEntitys(Integer[] ids) {
			
		String filehql = "delete from FileDescription f where f.fileId in (:idd)";
		deleteEntityByIDS(filehql, ids);
	}
	
	@Override
	public void updateArchivId(Integer id,Integer arvid){
		String filehql = "update  FileDescription set archiveId = ?  where fileId = ?";
		batchEntityByHQL(filehql,arvid,id);
	}

	@Override
	public List<FileDescription> findFileDescByName(String fileName) {
		String hql = "from FileDescription f where f.fileName like (:strName)";
		return searchEntityByHQL(hql, fileName);
	}

	/*�߼���ѯ�ļ���Ϣ3������*/
	@Override
	public List<FileDescription> findFileDescByList3(Integer as_archiveName_f, Integer as_filePath_f,String as_fileName) {
		List idl = ztreelocationService.getLocaList(as_filePath_f);
		String hql = "from FileDescription f where f.archive.archiveId = ? and f.archive.archivesLocation.locationId in (:idl) and f.fileName like (:strName)";
		return findEntityByIdsAndNameNoParent(hql, as_archiveName_f, idl, as_fileName);
	}
	
	/*�߼���ѯ�ļ���Ϣ3������*/
	@Override
	public List<FileDescription> findFileDescByL(Integer as_archiveName_f, Integer as_filePath_f,String as_fileName,String as_date_f) {
		List idl = ztreelocationService.getLocaList(as_filePath_f);
		String hql = "from FileDescription f where f.archive.archiveId = ? and f.archive.archivesLocation.locationId in (:idl) and f.fileName like (:strName) and f.archive.archivedYear like (:yearStr)";
		return findEntityByIdsAndNameNoParent(hql, as_archiveName_f, idl, as_fileName, as_date_f);
	}
	
	/*�߼���ѯ�ļ���Ϣ2������*/
	@Override
	public List<FileDescription> findFileDescByList2(Integer as_archiveName_f, Integer as_filePath_f) {
		List idl = ztreelocationService.getLocaList(as_filePath_f);
		String hql = "from FileDescription f where f.archive.archiveId = ? and f.archive.archivesLocation.locationId in (:idl)";
		return findEntityByIDSnoParent(hql, as_archiveName_f, idl);
	}
	
	/*�߼���ѯ�ļ���Ϣ2������*/
	@Override
	public List<FileDescription> findFileDescByList2(Integer as_archiveName_f, Integer as_filePath_f,String yearStr) {
		List idl = ztreelocationService.getLocaList(as_filePath_f);
		String hql = "from FileDescription f where f.archive.archiveId = ? and f.archive.archivesLocation.locationId in (:idl) and f.archive.archivedYear like (:yearStr)";
		return findEntityByIDSnoParent(hql, as_archiveName_f, idl, yearStr);
	}

	/*�߼���ѯ�ļ���Ϣ3����������ָ������������*/
	@Override
	public List<FileDescription> findFileDescByList3NoName(Integer as_archiveName_f, Integer as_filePath_f, String as_fileName) {
		List ida = archiveService.getAList(as_archiveName_f);
		List idl = ztreelocationService.getLocaList(as_filePath_f);
		String hql = "from FileDescription f where f.archive.archiveId in (:idc) and f.archive.archivesLocation.locationId in (:idl) and f.fileName like (:strName)";
		return findEntityByIdsAndName(hql, ida, idl,as_fileName);
	}
	
	@Override
	public List<FileDescription> findFileDescByList3NoName(Integer as_archiveName_f, Integer as_filePath_f, String as_fileName, String yearStr) {
		List ida = archiveService.getAList(as_archiveName_f);
		List idl = ztreelocationService.getLocaList(as_filePath_f);
		String hql = "from FileDescription f where f.archive.archiveId in (:idc) and f.archive.archivesLocation.locationId in (:idl) and f.fileName like (:strName) and f.archive.archivedYear like (:yearStr)";
		return findEntityByIdsAndName(hql, ida, idl,as_fileName,yearStr);
	}

	/*�߼���ѯ�ļ���Ϣ2����������ָ������������*/
	@Override
	public List<FileDescription> findFileDescByList2NoName(Integer as_archiveName_f, Integer as_filePath_f) {
		List ida = archiveService.getAList(as_archiveName_f);
		List idl = ztreelocationService.getLocaList(as_filePath_f);
		String hql = "from FileDescription f where f.archive.archiveId in (:idl) and f.archive.archivesLocation.locationId in (:idc)";
		return findEntityByIDS(hql, ida, idl);
	}
	
	/*�߼���ѯ�ļ���Ϣ2����������ָ������������*/
	@Override
	public List<FileDescription> findFileDescByList2NoName(Integer as_archiveName_f, Integer as_filePath_f, String yearStr) {
		List ida = archiveService.getAList(as_archiveName_f);
		List idl = ztreelocationService.getLocaList(as_filePath_f);
		String hql = "from FileDescription f where f.archive.archiveId in (:idl) and f.archive.archivesLocation.locationId in (:idc) and f.archive.archivedYear like (:yearStr)";
		return findEntityByIDS(hql, ida, idl, yearStr);
	}

}
