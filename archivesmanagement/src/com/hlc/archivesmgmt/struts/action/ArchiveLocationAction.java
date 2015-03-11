package com.hlc.archivesmgmt.struts.action;
import com.hlc.archivesmgmt.entity.ArchivesLocation;
import com.hlc.archivesmgmt.service.ZtreelocationService;
import com.hlc.archivesmgmt.entity.ZTreeNode;
import com.opensymphony.xwork2.ActionSupport;
import com.hlc.archivesmgmt.entity.EasyTreeNode;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;






import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.spi.http.HttpContext;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class ArchiveLocationAction extends ActionSupport{

	private static final long serialVersionUID = 8374023895054718068L;

	@Resource
	public ZtreelocationService ztreelocationService;
	
	private ArchivesLocation archivesLocation = new ArchivesLocation();
	
    private List<ZTreeNode> treelist=new ArrayList<ZTreeNode>();
    
    private Integer delId;
	
	private String backJson;
	
	private String name;    //前台传递name
	
	private String locationStrname; //前台传递路径
	
	private String parentId;    //前台传递pId
	
	private String treeid; //前台传递ID
	
	private Date createtime;
	
	private String viewtypes;
	
	private EasyTreeNode easytreelist;
	
	private EasyTreeNode itemview=new EasyTreeNode();
	private List<EasyTreeNode> viewlist=new ArrayList<EasyTreeNode>();
	
	
	/*
	 * 加载存放位置管理树形
	 * */
public String loadarchivalcation() throws Exception{
		
		List<ArchivesLocation> cateList = ztreelocationService.ztreelocationload();
        
		for(int i=0;i<cateList.size();i++){
			
			ArchivesLocation ac=cateList.get(i);
			
			if(ac.getCategoryView().equals(0)){
				treelist.add(new ZTreeNode(ac.getLocationId(), ac.getLocationName(), ac.getParentId(),ac.getCreateTime(),ac.getLocationStr(),ac.getCategoryView(),"./img/folder_bell.png",true,true));
			}else if(ac.getCategoryView().equals(1)){
				treelist.add(new ZTreeNode(ac.getLocationId(), ac.getLocationName(), ac.getParentId(),ac.getCreateTime(),ac.getLocationStr(),ac.getCategoryView(),"./img/box_picture.png",true,true));	
			}else{
				treelist.add(new ZTreeNode(ac.getLocationId(), ac.getLocationName(), ac.getParentId(),ac.getCreateTime(),ac.getLocationStr(),ac.getCategoryView(),"./img/box.png",true,true));	
			}
		}
		
		return SUCCESS;

	}

/*
 * easyui_tree请求
 * */
public String loadEasylcation() throws Exception{
	List<ArchivesLocation> cateList = ztreelocationService.ztreelocationload();
	easytreelist=GetTreeParentData(cateList,0);
	return SUCCESS;

}


private EasyTreeNode GetTreeParentData(List<ArchivesLocation> itemList,Integer parentid){
	for(int i=0;i<itemList.size();i++){
	if(itemList.get(i).getParentId().equals(parentid)){
	   itemview.setId(itemList.get(i).getLocationId());
	   itemview.setText(itemList.get(i).getLocationName());
	   GetTreeData(itemList,itemList.get(i).getLocationId());
	   itemview.setChildren(viewlist);  
	}
	}
	return itemview;
}

private void GetTreeData(List<ArchivesLocation> itemList,Integer parentid){
	EasyTreeNode item=null;
		for(int i=0;i<itemList.size();i++){
			if(itemList.get(i).getParentId().equals(parentid)){
				item=new EasyTreeNode();
				item.setId(itemList.get(i).getLocationId());
				item.setText(itemList.get(i).getLocationName());
				List<EasyTreeNode> childlist=new ArrayList<EasyTreeNode>();
				for(int b=0;b<itemList.size();b++){
					if(itemList.get(b).getParentId().equals(itemList.get(i).getLocationId())){
						 EasyTreeNode childitem=new EasyTreeNode();
						 childitem.setId(itemList.get(b).getLocationId());
						 childitem.setText(itemList.get(b).getLocationName());
						 childlist.add(childitem);
						 List<EasyTreeNode> childlists=new ArrayList<EasyTreeNode>();
						 for(int c=0;c<itemList.size();c++){
							 if(itemList.get(c).getParentId().equals(itemList.get(b).getLocationId())){
								 EasyTreeNode childitems=new EasyTreeNode();
								 childitems.setId(itemList.get(c).getLocationId());
								 childitems.setText(itemList.get(c).getLocationName());
								 childlists.add(childitems);
							 } 
						 }
						 childitem.setChildren(childlists);
					}
				}
				 item.setChildren(childlist);
				 viewlist.add(item);
			}	
		}
}





/*
 * 新增节点请求
 * */
public String addTreeNode() throws Exception{
	archivesLocation.setLocationId(null);
	archivesLocation.setLocationName(name);
	archivesLocation.setParentId(Integer.parseInt(parentId));
    archivesLocation.setCategoryView(Integer.parseInt(viewtypes));
	archivesLocation.setCreateTime(createtime);
	archivesLocation.setLocationStr(locationStrname);
	String id=ztreelocationService.addlocZtreeNodeFace(archivesLocation);
	if(Integer.parseInt(id)>0){
    if(viewtypes.equals("1")){
	for(int i=1;i<7;i++){
		addTreeNodeCeng(i,id);
	}
}
		this.backJson="00";
		}else{
			this.backJson="06";
		}
	return SUCCESS;
}


public void addTreeNodeCeng(Integer i,String pid) throws Exception{
	archivesLocation.setLocationId(null);
	archivesLocation.setLocationName(i+"层");
	archivesLocation.setParentId(Integer.parseInt(pid));
    archivesLocation.setCategoryView(Integer.parseInt("2"));
	archivesLocation.setCreateTime(createtime);
	archivesLocation.setLocationStr(locationStrname+"-"+i+"层");
	ztreelocationService.addlocZtreeNodeCengFace(archivesLocation);
}
/*
 * 更新节点请求
 * */

public String updateTreeNode() throws Exception{
	archivesLocation.setLocationId(Integer.parseInt(treeid));
	archivesLocation.setLocationName(name);
	archivesLocation.setParentId(Integer.parseInt(parentId));
	archivesLocation.setCategoryView(Integer.parseInt(viewtypes));
	archivesLocation.setCreateTime(createtime);
	archivesLocation.setLocationStr(locationStrname);
	this.backJson=ztreelocationService.updatelocZtreeNodeFace(archivesLocation);
	return SUCCESS;
}
/*
 * 删除节点请求
 * */

public String delTreeNode() throws Exception{
	this.backJson=ztreelocationService.dellocZtreeNodeFace(delId);
	return SUCCESS;
}

public List<ZTreeNode> getTreelist() {
	return treelist;
}


public void setTreelist(List<ZTreeNode> treelist) {
	this.treelist = treelist;
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

public EasyTreeNode getEasytreelist() {
	return easytreelist;
}

public void setEasytreelist(EasyTreeNode easytreelist) {
	this.easytreelist = easytreelist;
}

public String getLocationStrname() {
	return locationStrname;
}

public void setLocationStrname(String locationStrname) {
	this.locationStrname = locationStrname;
}

public Date getCreatetime() {
	return createtime;
}

public void setCreatetime(Date createtime) {
	this.createtime = createtime;
}

public String getViewtypes() {
	return viewtypes;
}

public void setViewtypes(String viewtypes) {
	this.viewtypes = viewtypes;
}














	
	
}
