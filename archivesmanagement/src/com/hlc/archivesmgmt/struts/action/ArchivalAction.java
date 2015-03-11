package com.hlc.archivesmgmt.struts.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hlc.archivesmgmt.entity.Archive;
import com.hlc.archivesmgmt.entity.FileDescription;
import com.hlc.archivesmgmt.entity.ArchivesLocation;
import com.hlc.archivesmgmt.service.ArchiveCateService;
import com.hlc.archivesmgmt.service.ArchiveService;
import com.hlc.archivesmgmt.service.FileDescService;
import com.hlc.archivesmgmt.service.ZtreelocationService;
import com.hlc.archivesmgmt.util.ExcelUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.hlc.archivesmgmt.entity.ArchivesCategory;


/**
 * ��������Action
 * @author linbotao
 *
 */
@Controller
@Scope("prototype")
public class ArchivalAction extends ActionSupport{
	private static final long serialVersionUID = 1137218533282621196L;

	private Archive archive;
	
	private List<Archive> list;
	
	private List<FileDescription> fileList;
	
	/*ģ����ѯ������*/
	private String fileName;
	
	/*�߼���ѯ������*/
	private String as_archiveName_a;
	
	/*�߼���ѯ������*/
	private String as_categoryName;
	
	/*�߼���ѯ����λ��*/
	private String as_filePath_a;
	
	private String archivLocationId;
	
	private String archivesCategoryId;
	
	private String delarchiveId;
	
	private String backJson;
	
	private int dId; 
	
	private boolean didRep;
	

	/* ע��ArchiveCateService */
	@Resource
	private ArchiveCateService archiveCateService ;
	
	@Resource
	private FileDescService fileDescService;
	
	/* ע��ArchiveService */
	@Resource
	private ArchiveService archiveService;
	
	//List<FileDescription>  fileDesclist;
	@Resource
	public ZtreelocationService ztreelocationService;
	/**
	 * ��ȡ������Ϣ
	 * @return ������Ϣ
	 */
	public String loadArchive(){
		archive = archiveService.getEntity(archive.getArchiveId());
		fileList = fileDescService.findFileDescById(archive.getArchiveId());
		//fileDesclist = fileDescService.findFileDescById(archive.getArchiveId());
		return SUCCESS;
	}
	
	/**
	 * ����ģ����ѯ
	 */
	public String searchArchive() throws IOException{
		list = archiveService.findArchiveByName(fileName);
		setData(list);
		return SUCCESS;
	}
	/**
	 * �����߼���ѯ
	 */
	public String advancedSearchArchive(){
		if(!as_archiveName_a.equals("") && as_archiveName_a != null){
			list = archiveService.findArchiveByList3(Integer.parseInt(as_categoryName),Integer.parseInt(as_filePath_a),as_archiveName_a);
		}else{
			list = archiveService.findArchiveByList2(Integer.parseInt(as_categoryName),Integer.parseInt(as_filePath_a));
		}
		setData(list);
		return SUCCESS;
	}
	
	public void setData(List<Archive> list){
		for(Archive a : list){
			if(a != null){
				//�����ļ����λ��
				a.setLocationStr(a.getArchivesLocation().getLocationStr());
			}
		}
	}
//	public List<FileDescription> getFileDesclist() {
//		return fileDesclist;
//	}
//
//	public void setFileDesclist(List<FileDescription> fileDesclist) {
//		this.fileDesclist = fileDesclist;
//	}
	
	/**
	 * ������޸ĵ���
	 */
    
	public String savaArchiveFlie() throws Exception{
		ArchivesLocation location=ztreelocationService.getEntity(Integer.parseInt(archivLocationId));
		ArchivesCategory category=archiveCateService.getEntity(Integer.parseInt(archivesCategoryId));
		List<Archive> archiveList = archiveService.findArchiveById(Integer.parseInt(archivesCategoryId));
		fileList = fileDescService.findFileDescById(archive.getArchiveId());
		if(archiveList.size()>0){
			for(Archive a : archiveList){
				if(a.getDirectoryId().equals(archive.getDirectoryId())){
					didRep = true;
					break;
				}
			}
		}else{
			didRep = false;
		}
		if(!didRep){
			archive.setArchivesCategory(category);
			archive.setArchivesLocation(location);
			if(archive.getArchiveId()==null){
				archiveService.saveEntity(archive);
			}else{
				archiveService.updateEntity(archive);
			}
		}
		
		
		/* �����޸� ����Ҫ�Զ��������ɺк�
		List<Archive> archiveList = archiveService.findArchiveById(Integer.parseInt(archivesCategoryId));
		fileList = fileDescService.findFileDescById(archive.getArchiveId());
		if(archiveList.size()>0){
			List<Integer> idList = new ArrayList<Integer>();
			for(Archive archive : archiveList){
				idList.add(archive.getDirectoryId());
			}
			if(null != archive.getArchiveId()){
				Archive a = archiveService.getEntity(archive.getArchiveId());
				idList.remove(a.getDirectoryId());
			}
			didRep = idList.contains(archive.getDirectoryId());
		}else{
			didRep = false;
		}
		if(!didRep){
			archive.setArchivesCategory(category);
			archive.setArchivesLocation(location);
			if(archive.getArchiveId()==null){
				archiveService.saveEntity(archive);
			}else{
				archiveService.updateEntity(archive);
			}
		}
		*/
		
		
		return SUCCESS;
	}
	
	/**
	 * �Զ����ɵ�������
	 */
	public String createCategoryCode(){
		archive = archiveService.getEntity(archive.getArchiveId());
		int categoryID = archive.getArchivesCategory().getCategoryId();
		String naviCode = calCategoryCode(categoryID,"");
		archive.getArchivesCategory().setNaviCode(naviCode);
		return SUCCESS;
	}
	
	/**
	 * 
	* @Title: calCategoryCode 
	* @Description: �������͵�������
	* @param @param categoryID    �趨�ļ� 
	* @return void    ��������
	* @throws
	 */
	private String calCategoryCode(int categoryID,String code) {
		ArchivesCategory archivesCategory = archiveCateService.getEntity(categoryID);
		if(code.equals("")){
			code = archivesCategory.getCategoryCode();
		}
		if(archivesCategory.getParentId() == 1){
			return code;
		}
		archivesCategory = archiveCateService.getEntity(archivesCategory.getParentId());
		code = archivesCategory.getCategoryCode() +"-" + code;
		
		return calCategoryCode(archivesCategory.getCategoryId(),code);
	}
	
	/**
	 * ��ʼ�������к�
	 */
//	public String initDid(){
//		List<Archive> archiveList = archiveService.findArchiveById(Integer.parseInt(archivesCategoryId));
//		dId = 1;
//		if(archiveList.size()>0){
//			List<Integer> idList = new ArrayList<Integer>();
//			for(Archive archive : archiveList){
//				idList.add(archive.getDirectoryId());
//			}
//			dId = idList.size()+1;
//			while(idList.contains(dId)){
//				dId ++;
//			}
//		}
//		return SUCCESS;
//	}
	
	/**
	 * ɾ������
	 */
    public String delArchiveFlie() throws Exception{
    	List<FileDescription> viewlist=fileDescService.findFileDescById(Integer.parseInt(delarchiveId));
    	if(viewlist.size()>0){
    		this.backJson="06";
    	}else{
    		archiveService.delArchiveById(Integer.parseInt(delarchiveId));
    		this.backJson="00";
    	}
    	return SUCCESS;
    }
	

	public Archive getArchive() {
		return archive;
	}

	public void setArchive(Archive archive) {
		this.archive = archive;
	}

	public String getArchivLocationId() {
		return archivLocationId;
	}

	public void setArchivLocationId(String archivLocationId) {
		this.archivLocationId = archivLocationId;
	}

	public String getDelarchiveId() {
		return delarchiveId;
	}

	public void setDelarchiveId(String delarchiveId) {
		this.delarchiveId = delarchiveId;
	}

	public String getBackJson() {
		return backJson;
	}

	public void setBackJson(String backJson) {
		this.backJson = backJson;
	}

	public String getArchivesCategoryId() {
		return archivesCategoryId;
	}

	public void setArchivesCategoryId(String archivesCategoryId) {
		this.archivesCategoryId = archivesCategoryId;
	}
	
	public List<Archive> getList() {
		return list;
	}

	public void setList(List<Archive> list) {
		this.list = list;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getAs_archiveName_a() {
		return as_archiveName_a;
	}

	public void setAs_archiveName_a(String as_archiveName_a) {
		this.as_archiveName_a = as_archiveName_a;
	}

	public String getAs_categoryName() {
		return as_categoryName;
	}

	public void setAs_categoryName(String as_categoryName) {
		this.as_categoryName = as_categoryName;
	}

	public String getAs_filePath_a() {
		return as_filePath_a;
	}

	public void setAs_filePath_a(String as_filePath_a) {
		this.as_filePath_a = as_filePath_a;
	}

	public int getdId() {
		return dId;
	}

	public void setdId(int dId) {
		this.dId = dId;
	}

	public boolean isDidRep() {
		return didRep;
	}

	public void setDidRep(boolean didRep) {
		this.didRep = didRep;
	}

	public List<FileDescription> getFileList() {
		return fileList;
	}

	public void setFileList(List<FileDescription> fileList) {
		this.fileList = fileList;
	}


	
}
