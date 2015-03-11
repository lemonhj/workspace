package com.hlc.archivesmgmt.struts.action;

import java.io.File;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hlc.archivesmgmt.entity.ArchivesStoreFile;
import com.hlc.archivesmgmt.entity.FileDescription;
import com.hlc.archivesmgmt.service.FileDescService;
import com.hlc.archivesmgmt.service.StoreFileService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 上传操作action
 * 
 * @ClassName: DownloadAction
 * @author linbotao
 * @date 2014年9月3日 上午10:25:52
 *
 */
@Controller
@Scope("prototype")
public class UploadFileAction extends ActionSupport implements ServletRequestAware,SessionAware{
	
	/* 注入FileDescService */
	@Resource
	private FileDescService fileDescService;
	
	/* 注入StoreFileService */
	@Resource
	private StoreFileService storeFileService;
	
	private String fileId;

	private static final long serialVersionUID = -1896915260152387341L;
	private HttpServletRequest request;

	public void setServletRequest(HttpServletRequest req) {
		this.request = req;
	}

	private Map<String,Object> session;
	private List<File> fileName;// 这里的"fileName"一定要与表单中的文件域名相同
	private List<String> fileNameContentType;// 格式同上"fileName"+ContentType
	private List<String> fileNameFileName;// 格式同上"fileName"+FileName
	private String savePath;// 文件上传后保存的路径

	public List<File> getFileName() {
		return fileName;
	}

	public void setFileName(List<File> fileName) {
		this.fileName = fileName;
	}

	public List<String> getFileNameContentType() {
		return fileNameContentType;
	}

	public void setFileNameContentType(List<String> fileNameContentType) {
		this.fileNameContentType = fileNameContentType;
	}

	public List<String> getFileNameFileName() {
		return fileNameFileName;
	}

	public void setFileNameFileName(List<String> fileNameFileName) {
		this.fileNameFileName = fileNameFileName;
	}

	@SuppressWarnings("deprecation")
	public String getSavePath() {
		return request.getRealPath(savePath);
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	@Override
	public String execute() throws Exception {
		File dir = new File(getSavePath());
		if (!dir.exists()) {
			dir.mkdirs();
		}
		List<File> files = getFileName();
		for (int i = 0; i < files.size(); i++) {
			String uploadFileName = fileNameFileName.get(i);
			String ext = uploadFileName.substring(uploadFileName.lastIndexOf("."));
			ArchivesStoreFile as = new ArchivesStoreFile();
			long l = System.currentTimeMillis();
			String fName = uploadFileName.substring(0, uploadFileName.lastIndexOf("."))+"-"+l + ext;
			File newFile = new File(dir,fName);
			FileDescription archiveFile = fileDescService.getEntity(Integer.parseInt(fileId));
			as.setFileName(uploadFileName);
			as.setUploadTime(new Date(l));
			as.setFileDescription(archiveFile);
			fileName.get(i).renameTo(newFile);
			String filePath = "/upload/" + fName;
			as.setFilePath(filePath);
			storeFileService.saveEntity(as);
//			FileOutputStream fos = new FileOutputStream(getSavePath() + "//"
//					+ getFileNameFileName().get(i));
//			FileInputStream fis = new FileInputStream(getFileName().get(i));
//			byte[] buffers = new byte[1024];
//			int len = 0;
//			while ((len = fis.read(buffers)) != -1) {
//				fos.write(buffers, 0, len);
//			}
		}
		return SUCCESS;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		this.session = arg0;
	}
	
	
}