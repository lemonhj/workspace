package com.hlc.archivesmgmt.struts.action;
import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hlc.archivesmgmt.entity.Archive;
import com.hlc.archivesmgmt.entity.ArchivesStoreFile;
import com.hlc.archivesmgmt.entity.FileDescription;
import com.hlc.archivesmgmt.service.ArchiveService;
import com.hlc.archivesmgmt.service.FileDescService;
import com.hlc.archivesmgmt.service.StoreFileService;
import com.opensymphony.xwork2.ActionSupport;


/**
 * 档案文件Action
 * @author linbotao
 *
 */
@Controller
@Scope("prototype")
public class ArchivalFileAction extends ActionSupport implements SessionAware{
	/**
	 * 序列化串行ID
	 */
	private static final long serialVersionUID = -2622047221494464332L;

	private String archiveId;
	
	private String fileId;
	
	private FileDescription archiveFile;
	
	private String delNums;
	
	private String codeNums;
	
	private List<FileDescription> fileList;
	
	private Archive archive;
	
	/*模糊查询文件名*/
	private String fileName;
	
	private Map<String,Object> session;
	
	
	
	
	private boolean rep;
	
	/*高级查询文件名*/
	private String as_fileName;
	
	/*高级查询文件所属档案名*/
	private String as_archiveName_f;
	
	/*高级查询文件保存位置*/
	private String as_filePath_f;
	
	/*是否目录*/
	private String isParent;
	
	/* 上传的文件 */
	private File upload;
	
	/* 接受文件名称 */
	private String uploadFileName;
	
	/* 接受文件内容类型的 */
	private String uploadContentType;
	
	/* 注入FileDescService */
	@Resource
	private FileDescService fileDescService;
	
	/* 注入ArchiveService */
	@Resource
	private ArchiveService archiveService;
	
	/* 注入StoreFileService */
	@Resource
	private StoreFileService storeFileService;

	private String as_date_f;
	/**
	 * 获取档案文件信息
	 * @return 档案文件信息
	 */
	public String loadArchiveFile(){
		archiveFile = fileDescService.getEntity(archiveFile.getFileId());
		session.put("fileId", archiveFile.getFileId());
		return SUCCESS;
	}
	
	/**
	 * 保存和修改档案文件信息
	 */
	public String saveArchiveFile(){
		fileList= fileDescService.findFileDescById(Integer.parseInt(archiveId));
		if(fileList.size()>0){
			for(FileDescription fd : fileList){
				if(fd.getCode().equals(archiveFile.getCode()) && !fd.getFileId().equals(archiveFile.getFileId())){
					rep = true;
					break;
				}
			}
		}else{
			rep = false;
		}
		if(!rep){
			archive = archiveService.getEntity(Integer.parseInt(archiveId));
			archiveFile.setArchive(archive);
			if(null == archiveFile.getFileId()){
				fileDescService.saveEntity(archiveFile);
			}else{
				fileDescService.updateEntity(archiveFile);
			}
		}
		/*
		if(fileList.size()>0){
			List<Integer> idList = new ArrayList<Integer>();
			for(FileDescription fd : fileList){
				idList.add(fd.getCode());
			}
			if(null != archiveFile.getFileId()){
				FileDescription fd = fileDescService.getEntity(archiveFile.getFileId());
				idList.remove(fd.getCode());
			}
			rep = idList.contains(fileCode);
		}else{
			rep = false;
		}
		if(!rep){
			Archive archive = archiveService.getEntity(Integer.parseInt(archiveId));
			archiveFile.setArchive(archive);
			if(null == archiveFile.getFileId()){
				fileDescService.saveEntity(archiveFile);
			}else{
				fileDescService.updateEntity(archiveFile);
			}
		}
		*/
		return SUCCESS;
	}
	
	/*检查id是否重复*/
//	public String checkArchiveFile(){
//		List<FileDescription> list = fileDescService.findFileDescById(Integer.parseInt(archiveId));
//		if(list.size()>0){
//			List<Integer> idList = new ArrayList<Integer>();
//			for(FileDescription fd : list){
//				idList.add(fd.getCode());
//			}
//			if(null != archiveFile.getFileId()){
//				idList.remove(fileCode);
//			}
//			rep = idList.contains(fileCode);
//		}else{
//			rep = false;
//		}
//		return SUCCESS;
//	}
	
	
	/**
	 * 自动生成文件编号
	 */
//	public String generateId(){
//		fileCode = createFileCode();
//		return SUCCESS;
//	}
	
	/**
	 * 检测和生成fileCode
	 */
//	private Integer createFileCode(){
//		List<FileDescription> list = fileDescService.findFileDescById(Integer.parseInt(archiveId));
//		int code = 0;
//		if(list.size()>0){
//			List<Integer> idList = new ArrayList<Integer>();
//			for(FileDescription fd : list){
//				idList.add(fd.getCode());
//			}
//			code = list.size()+1;
//			while(idList.contains(code)){
//				code ++;
//			}
//		}else{
//			code = 1;
//		}
//		return code;
//	}
	
	

	/**
	 * 删除档案文件信息
	 */
	public String delArchiveFile(){
		String dels = delNums.substring(1, delNums.length());
		String[] nums = dels.split(",");
		Integer[] ids = new Integer[nums.length];;
		for(int i=0;i<nums.length;i++){
			ids[i] = Integer.parseInt(nums[i]);
		}
		storeFileService.deleteEntitys(ids);
		fileDescService.deleteEntitys(ids);
		return SUCCESS;
	}
	

	
	/**
	 * 文件换挡
	 */
	public String updateArchiveFilebelong(){
		fileList= fileDescService.findFileDescById(Integer.parseInt(archiveId));
		String ups = delNums.substring(0, delNums.length());
		String[] nums = ups.split(",");
		String[] codes = codeNums.split(",");
		codeNums = "";
		Integer[] ids = new Integer[nums.length];;
		List<String> repeatList = new ArrayList<String>();
		if(fileList.size()>0){
//			List<Integer> idList = new ArrayList<Integer>();
//			for(FileDescription fd : fileList){
//				idList.add(fd.getCode());
//			}
			for(int i=0;i<nums.length;i++){
//				ids[i] = Integer.parseInt(codes[i]);
//				if(idList.contains(ids[i])){
//					repeatList.add(ids[i]+"");
//					codeNums += ids[i]+",";
//				}else{
					fileDescService.updateArchivId(Integer.parseInt(nums[i]),Integer.parseInt(archiveId));
//				}
			}
		}else{
			for(int i=0;i<nums.length;i++){
				ids[i] = Integer.parseInt(nums[i]);
				fileDescService.updateArchivId(Integer.parseInt(nums[i]),Integer.parseInt(archiveId));
			}
		}
		
		
		
		return SUCCESS;
	}
	
	
	
	/**
	 * 上传电子文件信息
	 */
	public String uploadFile(){
		
		if(null != uploadFileName){
			//上传电子文件操作
			String dir = ServletActionContext.getServletContext().getRealPath("/upload");
			String ext = uploadFileName.substring(uploadFileName.lastIndexOf("."));
			ArchivesStoreFile as = new ArchivesStoreFile();
			long l = System.currentTimeMillis();
			String fileName = uploadFileName.substring(0, uploadFileName.lastIndexOf("."))+"-"+l + ext;
			File newFile = new File(dir,fileName);
			archiveFile = fileDescService.getEntity(Integer.parseInt(fileId));
			as.setFileName(uploadFileName);
			as.setUploadTime(new Date(l));
			as.setFileDescription(archiveFile);
			upload.renameTo(newFile);
			String filePath = "/upload/" + fileName;
			as.setFilePath(filePath);
			storeFileService.saveEntity(as);
		}
		return SUCCESS;
	}
	
	/**
	 * 模糊查询文件信息
	 */
	public String searchFiles(){
		fileList = fileDescService.findFileDescByName(fileName);
		setData(fileList);
		return SUCCESS;
	}
	
	/**
	 * 高级查询文件信息 
	 */
	public String advancedSearchArchive(){
		if(isParent.equals("false")){
			//选择的所属类型是指定档案
			if(!as_fileName.equals("") && as_fileName != null ){
				
				if(!as_date_f.equals("") && as_date_f != null ){
					//指定年份
					fileList = fileDescService.findFileDescByL(Integer.parseInt(as_archiveName_f),Integer.parseInt(as_filePath_f),as_fileName,as_date_f);
				}else{
					fileList = fileDescService.findFileDescByList3(Integer.parseInt(as_archiveName_f),Integer.parseInt(as_filePath_f),as_fileName);
				}
			}else{
				if(!as_date_f.equals("") && as_date_f != null ){
					//指定年份
					fileList = fileDescService.findFileDescByList2(Integer.parseInt(as_archiveName_f),Integer.parseInt(as_filePath_f),as_date_f);
				}else{
					fileList = fileDescService.findFileDescByList2(Integer.parseInt(as_archiveName_f),Integer.parseInt(as_filePath_f));
				}
			}
		}else if(isParent.equals("true")){
			//选择的所属类型是父节点
			if(!as_fileName.equals("") && as_fileName != null){
				if(!as_date_f.equals("") && as_date_f != null ){
					fileList = fileDescService.findFileDescByList3NoName(Integer.parseInt(as_archiveName_f),Integer.parseInt(as_filePath_f), as_fileName, as_date_f);
				}else{
					fileList = fileDescService.findFileDescByList3NoName(Integer.parseInt(as_archiveName_f),Integer.parseInt(as_filePath_f), as_fileName);
				}
			}else{
				if(!as_date_f.equals("") && as_date_f != null ){
					fileList = fileDescService.findFileDescByList2NoName(Integer.parseInt(as_archiveName_f),Integer.parseInt(as_filePath_f),as_date_f);
				}else{
					fileList = fileDescService.findFileDescByList2NoName(Integer.parseInt(as_archiveName_f),Integer.parseInt(as_filePath_f));
				}
			}
		}
		setData(fileList);
		return SUCCESS;
	}
	
	public void setData(List<FileDescription> list){
		if(list.size() != 0 && list != null){
			for(FileDescription f : list){
				f.setFilePath(f.getArchive().getArchivesLocation().getLocationStr());
			}
		}
	}
	
	
	/**
	 * 修改档案文件信息
	 */
	public String updateArchiveFile(){
		fileDescService.updateEntity(archiveFile);
		return SUCCESS;
	}
	
	
	public FileDescription getArchiveFile() {
		return archiveFile;
	}

	public void setArchiveFile(FileDescription archiveFile) {
		this.archiveFile = archiveFile;
	}

	public String getArchiveId() {
		return archiveId;
	}

	public void setArchiveId(String archiveId) {
		this.archiveId = archiveId;
	}

	public String getDelNums() {
		return delNums;
	}

	public void setDelNums(String delNums) {
		this.delNums = delNums;
	}

	public List<FileDescription> getList() {
		return fileList;
	}

	public void setList(List<FileDescription> list) {
		this.fileList = list;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getAs_fileName() {
		return as_fileName;
	}

	public void setAs_fileName(String as_fileName) {
		this.as_fileName = as_fileName;
	}

	public String getAs_archiveName_f() {
		return as_archiveName_f;
	}

	public void setAs_archiveName_f(String as_archiveName_f) {
		this.as_archiveName_f = as_archiveName_f;
	}

	public String getAs_filePath_f() {
		return as_filePath_f;
	}

	public void setAs_filePath_f(String as_filePath_f) {
		this.as_filePath_f = as_filePath_f;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public boolean isRep() {
		return rep;
	}

	public void setRep(boolean rep) {
		this.rep = rep;
	}

	public String getCodeNums() {
		return codeNums;
	}

	public void setCodeNums(String codeNums) {
		this.codeNums = codeNums;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;
	}

	public String getAs_date_f() {
		return as_date_f;
	}

	public void setAs_date_f(String as_date_f) {
		this.as_date_f = as_date_f;
	}

	public Archive getArchive() {
		return archive;
	}

	public void setArchive(Archive archive) {
		this.archive = archive;
	}

    

	
}
