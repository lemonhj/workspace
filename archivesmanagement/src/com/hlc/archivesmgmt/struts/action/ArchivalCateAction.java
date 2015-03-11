package com.hlc.archivesmgmt.struts.action;



import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.hlc.archivesmgmt.entity.Archive;
import com.hlc.archivesmgmt.entity.ArchivesCategory;
import com.hlc.archivesmgmt.entity.EasyTreeNode;
import com.hlc.archivesmgmt.entity.ZTreeNode;
import com.hlc.archivesmgmt.service.ArchiveCateService;
import com.hlc.archivesmgmt.service.ArchiveService;
import com.opensymphony.xwork2.ActionSupport;


/**
 * 档案类型Action
 * @author linbotao
 *
 */
@Controller
@Scope("prototype")
public class ArchivalCateAction extends ActionSupport{
	private static final long serialVersionUID = 1137218533282621196L;

	/* 模型 */
	private ArchivesCategory archivesCategory;
	
	private String delId;
	
	private EasyTreeNode easytreelist;
	
	private EasyTreeNode easytreelist1;
	
	/* 注入ArchiveCateService */
	@Resource
	private ArchiveCateService archiveCateService ;
	
	/* 注入ArchiveService */
	@Resource
	private ArchiveService archiveService;
	
	private List<ZTreeNode> treeList;
	
	private EasyTreeNode itemview=new EasyTreeNode();
	
	private EasyTreeNode itemview1=new EasyTreeNode();
	
	private List<EasyTreeNode> viewlist=new ArrayList<EasyTreeNode>();
	
	private List<EasyTreeNode> viewlist1=new ArrayList<EasyTreeNode>();
	
	/*
	 * 加载档案类型树结构
	 */
	public String loadCategoryTree() throws Exception{
		
		treeList = new ArrayList<ZTreeNode>();
		List<ArchivesCategory> cateList = archiveCateService.loadCategory();
		
		for(int i = 0; i < cateList.size(); i++ ){
			
			ArchivesCategory archivesCategory = cateList.get(i);
			treeList.add(new ZTreeNode(archivesCategory.getCategoryId(),archivesCategory.getCategoryName(),archivesCategory.getParentId(),archivesCategory.getCreateTime(),"./img/folder_bell.png", true, true,archivesCategory.getCategoryCode()));
			List<Archive> list = archiveService.findArchiveById(archivesCategory.getCategoryId());
			if(!list.isEmpty()){
				for(int j =0; j<list.size();j++){
					Archive archive = list.get(j);
					treeList.add(new ZTreeNode(archivesCategory.getCategoryId()+"-"+archive.getArchiveId(),archive.getDirectoryId(),archivesCategory.getCategoryId(),archivesCategory.getCreateTime(),"./img/box_picture.png",true,false,archivesCategory.getCategoryCode()));
				}
			}
			
		}
		//this.outputJson(response, treeList);
		return SUCCESS;
	}
	

	
	/*
	 * easyui_tree请求
	 * */
	public String loadEasycategory() throws Exception{
		treeList = new ArrayList<ZTreeNode>();
		List<ArchivesCategory> cateList = archiveCateService.loadCategory();
		for(int i = 0; i < cateList.size(); i++ ){
			ArchivesCategory archivesCategory = cateList.get(i);
			treeList.add(new ZTreeNode(archivesCategory.getCategoryId(),archivesCategory.getCategoryName(),archivesCategory.getParentId(),archivesCategory.getCreateTime(), true, true));
			List<Archive> list = archiveService.findArchiveById(archivesCategory.getCategoryId());
			if(!list.isEmpty()){
				for(int j =0; j<list.size();j++){
					Archive archive = list.get(j);
					treeList.add(new ZTreeNode(archivesCategory.getCategoryId()+"-"+archive.getArchiveId(),archive.getDirectoryId(),archivesCategory.getCategoryId(),archivesCategory.getCreateTime(),true,false));
				}
			}
		}
		easytreelist=GetTreeParentData(cateList,0);
		easytreelist1=GetTreeParentData1(treeList,0);
		return SUCCESS;
	}
	
	private EasyTreeNode GetTreeParentData(List<ArchivesCategory> itemList,Integer parentid){
		itemview.setId(itemList.get(0).getCategoryId());
		itemview.setText(itemList.get(0).getCategoryName());
		GetTreeData(itemList,itemList.get(0).getCategoryId());
		itemview.setChildren(viewlist);
		itemview.setParents(true);
		return itemview;
	}
	
	private EasyTreeNode GetTreeParentData1(List<ZTreeNode> itemList,Integer parentid){
		itemview1.setId((int)itemList.get(0).getId());
		itemview1.setText(itemList.get(0).getName());
		GetTreeData1(itemList,(int)itemList.get(0).getId());
		itemview1.setChildren(viewlist1);
		itemview1.setParents(true);
		return itemview1;
	}


	private void GetTreeData(List<ArchivesCategory> itemList,Integer parentid){
		EasyTreeNode item=null;
			for(int i=0;i<itemList.size();i++){
				if(itemList.get(i).getParentId().equals(parentid)){
					item=new EasyTreeNode();
					item.setId(itemList.get(i).getCategoryId());
					item.setText(itemList.get(i).getCategoryName());
					List<EasyTreeNode> childlist=new ArrayList<EasyTreeNode>();
					for(int b=0;b<itemList.size();b++){
						if(itemList.get(b).getParentId().equals(itemList.get(i).getCategoryId())){
							 EasyTreeNode childitem=new EasyTreeNode();
							 childitem.setId(itemList.get(b).getCategoryId());
							 childitem.setText(itemList.get(b).getCategoryName());
							 childlist.add(childitem);		
							 List<EasyTreeNode> childlists=new ArrayList<EasyTreeNode>();
							 for(int c=0;c<itemList.size();c++){
								 if(itemList.get(c).getParentId().equals(itemList.get(b).getCategoryId())){
									 EasyTreeNode childitems=new EasyTreeNode();
									 childitems.setId(itemList.get(c).getCategoryId());
									 childitems.setText(itemList.get(c).getCategoryName());
									 childlists.add(childitems);
								 } 
							 }
							 childitem.setChildren(childlists);
						}
					}
					 item.setChildren(childlist);
					 if(item.getChildren().size()==0){
						 item.setParents(false);
					 }else{
						 item.setParents(true);
					 }
					 viewlist.add(item);
				}	
			}
	}
	
	private void GetTreeData1(List<ZTreeNode> itemList,Integer parentid){
		EasyTreeNode item=null;
			for(int i=0;i<itemList.size();i++){
				if(itemList.get(i).getpId().equals(parentid)){
					item=new EasyTreeNode();
					item.setId((int)itemList.get(i).getId());
					item.setText(itemList.get(i).getName());
					List<EasyTreeNode> childlist=new ArrayList<EasyTreeNode>();
					for(int b=0;b<itemList.size();b++){
						if(itemList.get(b).getpId().equals(itemList.get(i).getId())){
							 EasyTreeNode childitem=new EasyTreeNode();
							 String s = itemList.get(b).getId()+"";
							 String[] strs = s.split("-");
							 childitem.setId(Integer.parseInt(strs[strs.length-1]));
							 childitem.setText(itemList.get(b).getName());
							 childlist.add(childitem);
							 List<EasyTreeNode> childlists=new ArrayList<EasyTreeNode>();
//							 for(int c=0;c<itemList.size();c++){
//								 if(itemList.get(c).getpId().equals(itemList.get(i).getId())){
//									 EasyTreeNode childitems=new EasyTreeNode();
//									 String ss = itemList.get(c).getId()+"";
//									 String[] strss = ss.split("-");
//									 childitems.setId(Integer.parseInt(strss[strss.length-1]));
//									 childitems.setText(itemList.get(c).getName());
//									 childlists.add(childitems);
//								 } 
//							 }
							 childitem.setChildren(childlists);
						}
					}
					 item.setChildren(childlist);
					 if(item.getChildren().size()==0){
						 item.setParents(false);
					 }else{
						 item.setParents(true);
					 }
					 viewlist1.add(item);
				}	
			}
	}
	
	
	
//	public String delCategoryTree() throws Exception{
//		String backJson=archiveService.delArchiveById(Integer.parseInt(delId));
//		this.outputJson(response, backJson);
//		return null;
//	}
	
	private String formatDid(Integer dId){
		String reId = "0000" + dId;
		return reId.substring(reId.length()-4,reId.length());
	}


	public String getDelId() {
		return delId;
	}

	public void setDelId(String delId) {
		this.delId = delId;
	}

	public EasyTreeNode getEasytreelist() {
		return easytreelist;
	}

	public void setEasytreelist(EasyTreeNode easytreelist) {
		this.easytreelist = easytreelist;
	}

	public ArchivesCategory getArchivesCategory() {
		return archivesCategory;
	}

	public void setArchivesCategory(ArchivesCategory archivesCategory) {
		this.archivesCategory = archivesCategory;
	}

	public List<ZTreeNode> getTreeList() {
		return treeList;
	}

	public void setTreeList(List<ZTreeNode> treeList) {
		this.treeList = treeList;
	}

	public EasyTreeNode getEasytreelist1() {
		return easytreelist1;
	}

	public void setEasytreelist1(EasyTreeNode easytreelist1) {
		this.easytreelist1 = easytreelist1;
	}

	
	
	
	

}
