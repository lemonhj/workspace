package com.honglicheng.dev.estate.contral.action;

import com.opensymphony.xwork2.ActionSupport;
import com.caucho.hessian.io.ArraySerializer;
import com.honglicheng.dev.estate.dao.TCommunityDAO;
import com.honglicheng.dev.estate.model.*;
import com.honglicheng.dev.estate.manager.FloorTbaleFace;
import com.honglicheng.dev.sys.model.*;
import com.honglicheng.dev.sys.model.security.Role;

import java.awt.geom.Area;
import java.lang.reflect.Array;
import java.security.acl.Owner;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.mapping.Collection;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


@Controller
@Scope("prototype")
public class FloorTableAction extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8989758120807030622L;
	
	@Resource
	private FloorTbaleFace floortableface;
	
	private List<EasyTree> buildlist;
	
	private List<TCommunity> communitylist;
	
	private List<EasyTree> treelist;
	
	private List<TFloor> floorlist;
	
	private TCorporation tcorporation;
	
	private String buildidt;
	
	private String usertype;
	
	private String companylike;
	
	//业态比例查询结果
	private List<countNode> countRatiolist;
	
	//入驻面积比例查询结果
	private List<AreaNode> countarealist;
	
    private String yzname;
    
    private String telnum;
    
    private String corpid;
	
	//人数规模查询结果列表
	private List<NopNode> countnoplist;
	
	//自持、租赁比例查询结果列表
	private List<SalNode> countsallist;
	
	//判断点击节点是否是楼栋(true为楼栋 false为区域)
	private String buildFlag;
	
	private BuildingNode builddata;
	
	private BuildingNode builddatabn;
	
	public BuildingNode getBuilddatabn() {
		return builddatabn;
	}

	public void setBuilddatabn(BuildingNode builddatabn) {
		this.builddatabn = builddatabn;
	}

	private String corpstateradio;
	
	private String roomdata;
	
	private String corpdata;
	
	private Integer backcorpid;
	
	private String selecttj;
	
	private List<CorpNode> corplist;
	
	private Map sessionmap; 
	
	private boolean flag;
	
	private String roomid;
    
	private String roomradioid;
	
	private List<Integer> industrybyid;
	
	//区域信息名称
	private String name;
	
	//区域所属区域Id
	private String tpid;
	
	//区域Id
	private String tid;
	
	//获取楼栋类型
	private String floortypeid;
	
	/*
	 * 删除返回值
	 * 0:区域下有楼栋存在
	 * 1:删除区域信息成功
	 */
	private String code;
	//接收从前台搜索传过来的参数
	private String formData;
	//返回搜索结果
	private Map<String,List <CorpRoomBuildComInd>> uicList;
	

	
    public String loadTreeListnode(){
		
    	//用户登陆
    	BaseSysUser userinfo = (BaseSysUser)sessionmap.get("user");
    	
	    //登陆获取用户信息
    
        Role roleinfo=new ArrayList<Role>(userinfo.getRoles()).get(0);

       
		List<EasyTree> buildlist=new ArrayList<EasyTree>();
		
		buildlist= floortableface.getTCommunity(flag,Integer.parseInt(roleinfo.getCommIds()));
		
		treelist=getalltree(buildlist,buildlist.get(0).getParentid());
		
		return SUCCESS;
	}
	
	private List<EasyTree> getalltree(List<EasyTree> listnode,Integer id){
		
		   List<EasyTree> realList = listnode;
		    //获取孩子节点列表
          
		   List<EasyTree> viewList = new ArrayList<EasyTree>(); 
           for (int i = 0; i < realList.size(); i++)
           {
        	   
        	   if (id.equals(realList.get(i).getParentid()))
               {
        		   EasyTree temp = realList.get(i);
                   viewList.add(temp);
                   //利用递归获取子孙节点
                   if(!realList.get(i).getBuild()){
                	   temp.setChildren(this.getalltree(realList,temp.getId()));
                   }
               }

           }
           return viewList;
	}
	
	
	
	public String savecompany(){
		backcorpid=floortableface.addCorporation(tcorporation);
		JSONArray roomsidparm= JSONArray.fromObject(roomid);
		Integer stateid=Integer.parseInt(roomradioid);
		if(stateid!=0){
		for(int i=0;i<roomsidparm.size();i++){
		    floortableface.updateroom(roomsidparm.getInt(i),backcorpid,stateid);
		   }
		}
		return SUCCESS;
	}
	
	public String loadcorpnow(){
		
		List corpnow=floortableface.loadcorplistnow(selecttj,Integer.parseInt(buildidt));
		corplist=new ArrayList<CorpNode>();
		for(int i=0;i<corpnow.size();i++){
			CorpNode item=new CorpNode();
			Object[] bb=(Object[])corpnow.get(i);
			item.setId(Integer.parseInt(bb[0].toString()));
			item.setType(Integer.parseInt(bb[1]==null?"0":bb[1].toString()));
			item.setName(bb[2]==null?"":bb[2].toString());
			item.setAlias(bb[3]==null?"":bb[3].toString());
			item.setFullname(bb[4]==null?"":bb[4].toString());
			item.setSize(Integer.parseInt(bb[5]==null?"0":bb[5].toString()));
			item.setContactor(bb[6]==null?"":bb[6].toString());
			item.setTel(bb[7]==null?"":bb[7].toString());
			item.setState(Integer.parseInt(bb[9]==null?"":bb[9].toString()));
			item.setStaterooms(null);
			corplist.add(item);
		}		
		return SUCCESS;
	} 
	
	public String loadfloit(){
		
		//用户登陆
    	BaseSysUser userinfo = (BaseSysUser)sessionmap.get("user");
		 //登陆获取用户信息
		Role roleinfo=new ArrayList<Role>(userinfo.getRoles()).get(0);
		
        usertype = roleinfo.getOperator()==null?"":roleinfo.getOperator().toString();
        
        if(floortypeid.equals("1")){
    
		builddata=floortableface.loadbuilddata(Integer.parseInt(buildidt),Integer.parseInt(floortypeid));
		}else if(floortypeid.equals("2")){

		builddatabn=floortableface.loadbuilddata(Integer.parseInt(buildidt),Integer.parseInt(floortypeid));
		}
		
		industrybyid=floortableface.LoadIndustryId(Integer.parseInt(buildidt));
		
		return SUCCESS;
	}
	
	/**首页4个饼状图数据查询*/
	public String fourList(){
		//用户登陆
    	BaseSysUser userinfo = (BaseSysUser)sessionmap.get("user");
    	
    	if(userinfo.equals(null)){}else{
		 //登陆获取用户信息
		Role roleinfo=new ArrayList<Role>(userinfo.getRoles()).get(0);
		//业态类型
		List lists=floortableface.getTFloors(Integer.parseInt(roleinfo.getCommIds()),"false");
		countRatiolist = new ArrayList<countNode>();
		countRatiolist=countRatio(lists);
		//入驻面积
		List area=floortableface.loadareainfo(Integer.parseInt(roleinfo.getCommIds()),"false");
		countarealist=new ArrayList<AreaNode>();
		areaData(area);
		//人数规模比例
		List nop=floortableface.loadnopinfo(Integer.parseInt(roleinfo.getCommIds()),"false");
		countnoplist = new ArrayList<NopNode>();
		nopData(nop);
		//自持、租赁比例
		List sal = floortableface.loadsalinfo(Integer.parseInt(roleinfo.getCommIds()),"false");
		countsallist = new ArrayList<SalNode>();
		salData(sal);
		}
		return SUCCESS;
	}
	
	/** 获取点击节点所需的4个饼图数据*/
	public String loadTfloorList(){
		
		//业态比例
		List lists=floortableface.getTFloors(Integer.parseInt(buildidt),buildFlag);
		countRatiolist = new ArrayList<countNode>();
		countRatiolist = countRatio(lists);
		
		//入驻面积比例
		List area=floortableface.loadareainfo(Integer.parseInt(buildidt),buildFlag);
		countarealist=new ArrayList<AreaNode>();
		areaData(area);
		
		//人数规模比例
		List nop = floortableface.loadnopinfo(Integer.parseInt(buildidt),buildFlag);
		countnoplist = new ArrayList<NopNode>();
		nopData(nop);
		
		//自持、租赁比例
		List sal = floortableface.loadsalinfo(Integer.parseInt(buildidt),buildFlag);
		countsallist = new ArrayList<SalNode>();
		salData(sal);
		
		return SUCCESS;
	}

	/**业态类型比例数据*/
	private List<countNode> countRatio(List datalist){
		List<countNode> coutlist=new ArrayList<countNode>();
		countNode item=null;
		for(int i=0;i<datalist.size();i++){
			 Object[] bb=(Object[])datalist.get(i);
			 if(bb[0]==null) continue;
			 item=new countNode();
             item.setName(bb[0].toString());
             item.setCountname(Integer.parseInt(bb[1].toString()));
             String temp = String.format("%.2f", bb[2]);
             item.setCountArea(Float.parseFloat(temp));
             item.setCountColor(bb[3].toString());
             coutlist.add(item);
		}
		return coutlist;
	}
	
	/**入驻面积数据转换*/
	public void areaData(List area){
		List<AreaNode> listnode=new ArrayList<AreaNode>();
		countarealist=loadareaAtt(area);
		if(countarealist.size()!=0){
			for(int i=0;i<countarealist.size();i++){
				if(countarealist.get(i).getAreanum()>-0.000001 && countarealist.get(i).getAreanum()<0.000001){
					countarealist.remove(i);
				}else{
					String temp = String.format("%.2f", countarealist.get(i).getAreanum());
					countarealist.get(i).setAreanum(Float.parseFloat(temp));
				}
			}
		}		
	}
	
	/**人数规模数据转换*/
	public void nopData(List nop){
		NopNode item = null;
		for(int i=0;i<nop.size();i++){
			Object[] bb=(Object[])nop.get(i);
			if(bb[0] == null) continue;
			item = new NopNode();
			item.setNopName(bb[0].toString());
			item.setNopCount(Integer.parseInt(bb[1].toString()));
			countnoplist.add(item);
		}
	}
	
	/**自持、租赁数据转换*/
	public void salData(List sal){
		SalNode item = null;
		for(int i=0;i<sal.size();i++){
			Object[] bb=(Object[])sal.get(i);
			if(bb[0] == null || bb[1] == null) continue;
			item = new SalNode(); 
			item.setSalName(bb[0].toString());
			String temp = String.format("%.2f", bb[1]);
			item.setSalCount(Float.parseFloat(temp));
			countsallist.add(item);
		}
	}
	
	/*
	 * 统计每栋楼面积比例
	 * */
	private List<AreaNode> loadareaAtt(List datalist){
		List<AreaNode> coutlist=new ArrayList<AreaNode>();
		AreaNode item=null;
		for(int i=0;i<datalist.size();i++){
			 Object[] bb=(Object[])datalist.get(i);
			 if(bb[1] == null) continue;
			 item=new AreaNode();
             item.setAreaname(bb[0]==null?"0":bb[0].toString());
             item.setAreanum(Float.valueOf(bb[1].toString()));
             if(!item.getAreaname().equals("0"))coutlist.add(item);
		}
		return coutlist;
	}
	
	/*
	 * 引入公司
	 * */
	public String roomys(){
//		JSONArray roomitem=JSONArray.fromObject(roomdata);
//		JSONObject corpitemobject=JSONObject.fromObject(corpdata);
//		Integer roomstateid=Integer.parseInt(corpitemobject.get("staterooms").toString());
//		backcorpid=Integer.parseInt(corpitemobject.get("id").toString());
//		for(int i=0;i<roomitem.size();i++){
//			floortableface.updateroom(roomitem.getInt(i), backcorpid,roomstateid);
//		}
		floortableface.updateroom(Integer.parseInt(roomid), Integer.parseInt(corpid),Integer.parseInt(roomradioid));
		floortableface.updatecorpstate(Integer.parseInt(corpstateradio), Integer.parseInt(corpid));
		return SUCCESS;
	}
	
	/*
	 * 编辑房间属性
	 * */
	public String roomupdate(){
		JSONArray corpitemobject=JSONArray.fromObject(corpdata);
		for(int i=0;i<corpitemobject.size();i++){
			floortableface.updateroominfo(corpitemobject.getInt(i),yzname,telnum);
		}
		return SUCCESS;
	}
	
	/*
	 * 删除公司
	 * */
	public void delcorption(){
		floortableface.delcorption(Integer.parseInt(corpid));
	}

	
	/*
	 * 装修
	 * */
	public String roomdecorate(){
		JSONArray roomitem=JSONArray.fromObject(roomdata);
		for(int i=0;i<roomitem.size();i++){
			floortableface.decorate(roomitem.getInt(i), 2);
		}
		return SUCCESS;
	}
	
	/*
	 * 清除
	 * */
	public String roomdel(){
		JSONArray roomitem=JSONArray.fromObject(roomdata);
		for(int i=0;i<roomitem.size();i++){
			floortableface.clearroominfo(roomitem.getInt(i),0,4);
		}
		return SUCCESS;
	}
	
	public void roomdeltable(){
	    floortableface.clearroominfo(Integer.parseInt(roomid),0,4);
	}
	
	/*
	 * 转换公司属性
	 * */
	private TCorporation  itemchange(JSONObject corpitemobject){
		TCorporation corpitem=new TCorporation();
		if(corpitemobject.get("id").equals("")|| corpitemobject.get("id").equals(null)){}else{
		corpitem.setCorpId(Integer.parseInt(corpitemobject.get("id").toString()));
		}
		corpitem.setCorpAlias(corpitemobject.get("alias").toString());
		corpitem.setCorpName(corpitemobject.get("name").toString());
		corpitem.setCorpFullname(corpitemobject.get("fullname").toString());
		corpitem.setCorpTel(corpitemobject.get("tel").toString());
		corpitem.setCorpSizes(Integer.parseInt(corpitemobject.get("sizes")==""?"0":corpitemobject.get("sizes").toString()));
		corpitem.setCorpIndustry(Integer.parseInt(corpitemobject.get("type").toString()));
		corpitem.setCorpcontacts(corpitemobject.get("contactor").toString());
		corpitem.setCorpState(corpitemobject.get("state").toString());
		return corpitem;
	}
	
	/*
	 * 加载公司列表
	 * */
	 public String loadCompanyList(){
    	 CorpNode corpnode=null;
    	 corplist=new ArrayList<CorpNode>();
    	 List corps=floortableface.loadBuildhascorp(Integer.parseInt(buildidt),companylike);
    	 for(int i=0;i<corps.size();i++){
    		 Object[] corp=(Object[])corps.get(i);
    		  corpnode=new CorpNode();
    		  corpnode.setId(Integer.parseInt(corp[0].toString()));
    		  corpnode.setName(corp[2]==null?"":corp[2].toString());
    		  corpnode.setAlias(corp[3]==null?"":corp[3].toString());
    		  corpnode.setFullname(corp[4]==null?"":corp[4].toString());
    		  corpnode.setSize(Integer.parseInt(corp[5]==null?"0":corp[5].toString()));
    		  corpnode.setContactor(corp[6]==null?"":corp[6].toString());
    		  corpnode.setTel(corp[7]==null?"":corp[7].toString());
    		  corpnode.setState(Integer.parseInt(corp[9]==null?"0":corp[9].toString()));
    		  corpnode.setType(Integer.parseInt(corp[1]==null?"0":corp[1].toString()));
    		  corplist.add(corpnode);
    	 }
    	 return SUCCESS;
     }
	public void setSession(Map<String, Object> arg0) {
		// TODO 自动生成的方法存根
		this.sessionmap=arg0;
	
	}
	
	/**新增区域信息*/
	public String addTcInfo(){
		flag = floortableface.insertInfo(name, null, Integer.parseInt(tpid));
		return SUCCESS;
	}
	
	/**修改区域信息*/
	public String editTcInfo(){
		flag = floortableface.editInfo(name, Integer.parseInt(tid));
		return SUCCESS;
	}
	
	/**删除区域信息*/
	public String deleteTcInfo(){
		flag = floortableface.isExistsByTid(Integer.parseInt(tid));
		//选择区域下不存在楼栋,可以删除区域
		if(flag){
			code = floortableface.deleteInfo(Integer.parseInt(tid));
		}else{
			code = "0";
		}
		return SUCCESS;
	}
	
	/*查询功能的实现*/
	
	public String ytQueryData(){
		uicList = floortableface.uicListFindByWhere(formData);
		return SUCCESS;
	}
	
	/*点击业态报表中的grid时查询公司信息*/
	public String querySelGridDataToShow(){
		uicList = null;
		uicList = floortableface.querySelGridDataToShow(formData);
		return SUCCESS;
	}
	
	/*
	 * 根据楼栋Id获取楼栋行业分类
	 * */
	public String LoadIndustry(){
        industrybyid=floortableface.LoadIndustryId(Integer.parseInt(buildidt));
		return SUCCESS;
	}
	public FloorTbaleFace getFloortableface() {
		return floortableface;
	}

	public void setFloortableface(FloorTbaleFace floortableface) {
		this.floortableface = floortableface;
	}

	public List<EasyTree> getBuildlist() {
		return buildlist;
	}

	public void setBuildlist(List<EasyTree> buildlist) {
		this.buildlist = buildlist;
	}

	public List<TCommunity> getCommunitylist() {
		return communitylist;
	}

	public void setCommunitylist(List<TCommunity> communitylist) {
		this.communitylist = communitylist;
	}

	public List<EasyTree> getTreelist() {
		return treelist;
	}

	public void setTreelist(List<EasyTree> treelist) {
		this.treelist = treelist;
	}

	public List<TFloor> getFloorlist() {
		return floorlist;
	}

	public void setFloorlist(List<TFloor> floorlist) {
		this.floorlist = floorlist;
	}

	public String getBuildidt() {
		return buildidt;
	}

	public void setBuildidt(String buildidt) {
		this.buildidt = buildidt;
	}

	public List<countNode> getCountRatiolist() {
		return countRatiolist;
	}

	public void setCountRatiolist(List<countNode> countRatiolist) {
		this.countRatiolist = countRatiolist;
	}

	public List<AreaNode> getCountarealist() {
		return countarealist;
	}

	public void setCountarealist(List<AreaNode> countarealist) {
		this.countarealist = countarealist;
	}

	

	public BuildingNode getBuilddata() {
		return builddata;
	}

	public void setBuilddata(BuildingNode builddata) {
		this.builddata = builddata;
	}

	public String getRoomdata() {
		return roomdata;
	}

	public void setRoomdata(String roomdata) {
		this.roomdata = roomdata;
	}

	public String getCorpdata() {
		return corpdata;
	}

	public void setCorpdata(String corpdata) {
		this.corpdata = corpdata;
	}

	public Integer getBackcorpid() {
		return backcorpid;
	}

	public void setBackcorpid(Integer backcorpid) {
		this.backcorpid = backcorpid;
	}

	public String getSelecttj() {
		return selecttj;
	}

	public void setSelecttj(String selecttj) {
		this.selecttj = selecttj;
	}

	public List<CorpNode> getCorplist() {
		return corplist;
	}

	public void setCorplist(List<CorpNode> corplist) {
		this.corplist = corplist;
	}

	public boolean getFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTpid() {
		return tpid;
	}

	public void setTpid(String tpid) {
		this.tpid = tpid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public List<NopNode> getCountnoplist() {
		return countnoplist;
	}

	public void setCountnoplist(List<NopNode> countnoplist) {
		this.countnoplist = countnoplist;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		
		this.usertype = usertype;
	}

	public List<SalNode> getCountsallist() {
		return countsallist;
	}

	public void setCountsallist(List<SalNode> countsallist) {
		this.countsallist = countsallist;
	}

	public String getFormData() {
		return formData;
	}

	public void setFormData(String formData) {
		this.formData = formData;
	}

	public Map<String, List<CorpRoomBuildComInd>> getUicList() {
		return uicList;
	}

	public void setUicList(Map<String, List<CorpRoomBuildComInd>> uicList) {
		this.uicList = uicList;
	}

	public String getYzname() {
		return yzname;
	}

	public void setYzname(String yzname) {
		this.yzname = yzname;
	}

	public String getTelnum() {
		return telnum;
	}

	public void setTelnum(String telnum) {
		this.telnum = telnum;
	}

	public TCorporation getTcorporation() {
		return tcorporation;
	}

	public void setTcorporation(TCorporation tcorporation) {
		this.tcorporation = tcorporation;
	}

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public String getRoomradioid() {
		return roomradioid;
	}

	public void setRoomradioid(String roomradioid) {
		this.roomradioid = roomradioid;
	}

	public String getBuildFlag() {
		return buildFlag;
	}

	public void setBuildFlag(String buildFlag) {
		this.buildFlag = buildFlag;
	}

	public String getCompanylike() {
		return companylike;
	}

	public void setCompanylike(String companylike) {
		this.companylike = companylike;
	}

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public List<Integer> getIndustrybyid() {
		return industrybyid;	}

	public void setIndustrybyid(List<Integer> industrybyid) {
		this.industrybyid = industrybyid;
	}

	public String getCorpstateradio() {
		return corpstateradio;
	}

	public void setCorpstateradio(String corpstateradio) {
		this.corpstateradio = corpstateradio;
	}

	public String getFloortypeid() {
		return floortypeid;
	}

	public void setFloortypeid(String floortypeid) {
		this.floortypeid = floortypeid;
	}
	
	
	
	
	
	
	


}
