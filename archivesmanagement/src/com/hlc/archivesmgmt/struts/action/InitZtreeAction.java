package com.hlc.archivesmgmt.struts.action;
import com.hlc.archivesmgmt.entity.ArchivesCategory;
import com.hlc.archivesmgmt.service.ArchiveCateService;
import com.hlc.archivesmgmt.service.ZtreeService;
import com.hlc.archivesmgmt.entity.ZTreeNode;
import com.opensymphony.xwork2.ActionSupport;
import com.hlc.archivesmgmt.service.ArchiveService;
import com.hlc.archivesmgmt.entity.Archive;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;









import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class InitZtreeAction extends ActionSupport  {
	private static final long serialVersionUID = 6085782251900503785L;

	
	@Resource
	public ZtreeService ztreeService;
	
	@Resource
	private ArchiveCateService archiveCateService;
	
	@Resource
	public ArchiveService archiveService;
	
	private ArchivesCategory archivesCategory=new ArchivesCategory();
	
	private List<ZTreeNode> treeList;
	
	private Integer delId;
	
	private String backJson;
	
	private ZTreeNode treen;
	
	/*ǰ̨����name*/
	private String name;    
	
	/*ǰ̨����pId*/
	private String parentId;    
	
	/*ǰ̨����ID*/
	private String treeid; 
	
	/*ǰ̨���ݴ���ʱ��*/
	private java.sql.Date createtime; 
	
	private String code;
	
	/*
	 * ���ص�����������
	 * */
	public String loadarchivalCategories() throws Exception{
		
		treeList=new ArrayList<ZTreeNode>();
		
		List<ArchivesCategory> cateList = ztreeService.Ztreeload();
        
		for(int i=0;i<cateList.size();i++){
			
			ArchivesCategory ac=cateList.get(i);
			
			if(ac.getCategoryView().equals(0)){
				treeList.add(new ZTreeNode(ac.getCategoryId(), ac.getCategoryName(), ac.getParentId(),ac.getCreateTime(),"./img/folder_bell.png",true,true,ac.getCategoryCode()));	
			}else{
				treeList.add(new ZTreeNode(ac.getCategoryId(), ac.getCategoryName(), ac.getParentId(),ac.getCreateTime(),"./img/folder_bell.png",true,true,ac.getCategoryCode()));	
			}
			
			
		}
		
		return SUCCESS;

	}
	
	/*
	 * �����ڵ�����
	 * */
	public String addTreeNode() throws Exception{
		archivesCategory.setCategoryId(null);
		archivesCategory.setCategoryName(name);
		archivesCategory.setCategoryCode(code);
		archivesCategory.setParentId(Integer.parseInt(parentId));
		archivesCategory.setCategoryView(0);
		
		if(!parentId.equals(0)){
			ArchivesCategory archivesCate = archiveCateService.getEntity(Integer.parseInt(parentId));
			archivesCategory.setNaviCode(archivesCate.getNaviCode() +"-"+ code);
		}
		archivesCategory.setCreateTime(createtime);
		this.backJson=ztreeService.addZtreeNodeFace(archivesCategory);
		return SUCCESS;
	}
	
	/*
	 * ���½ڵ�����
	 * */
	
	public String updateTreeNode() throws Exception{
		archivesCategory.setCategoryId(Integer.parseInt(treeid));
		archivesCategory.setCategoryCode(code);
		archivesCategory.setCategoryName(name);
		archivesCategory.setParentId(Integer.parseInt(parentId));
		archivesCategory.setCategoryView(0);
		archivesCategory.setCreateTime(createtime);
		this.backJson=ztreeService.updateZtreeNodeFace(archivesCategory);
		return SUCCESS;
	}
	/*
	 * ɾ���ڵ�����
	 * */
	
	public String delTreeNode() throws Exception{	
		this.backJson=ztreeService.delZtreeNodeFace(delId);
		return SUCCESS;
	}
	
	public String selectHaveArchives() throws Exception{
		if(this.isHaveArchives(delId)){
			this.backJson="02";   //��ʾ���ڵ���
		}else{
			this.backJson="00";
		}
		return SUCCESS;
	}
	
	/*
	 * ��ѯ�Ƿ���ڵ���
	 * */
	private Boolean isHaveArchives(Integer id){
		List<Archive> archivelist=archiveService.findArchiveById(id);
		if(archivelist.size()>0){
			return true;
		}
		return false;
	}

	public List<ZTreeNode> getTreeList() {
		return treeList;
	}

	public void setTreeList(List<ZTreeNode> treeList) {
		this.treeList = treeList;
	}


	public Integer getDelId() {
		return delId;
	}

	public void setDelId(Integer delId) {
		this.delId = delId;
	}

	public String getBackJson() {
		return backJson;
	}


	public void setBackJson(String backJson) {
		this.backJson = backJson;
	}


	public ZTreeNode getTreen() {
		return treen;
	}


	public void setTreen(ZTreeNode treen) {
		this.treen = treen;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getTreeid() {
		return treeid;
	}

	public void setTreeid(String treeid) {
		this.treeid = treeid;
	}

	public java.sql.Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(java.sql.Date createtime) {
		this.createtime = createtime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


}
