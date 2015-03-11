package com.honglicheng.dev.estate.manager;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.honglicheng.dev.estate.dao.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import com.honglicheng.dev.estate.model.*;
import com.honglicheng.dev.sys.dao.impl.RoleDao;

@Service("FloorTbaleFace")
public class FloorTbaleImpl implements FloorTbaleFace  {

@Resource    
public TBuildingDAO tBuildingDAO;

@Resource    
public TCommunityDAO tCommunityDAO;

@Resource    
public TFloorDAO tFloorDAO;

@Resource    
public TRoomDAO troomDAO;


@Resource    
public TCorporationDAO tCorporationDAO;
@Resource
public CorpRoomBuildComIndDAO corpRoomBuildComIndDAO;
@Resource
public CorpRoomBuildComIndChildDAO corpRoomBuildComIndChildDAO;
	
	
	public List<TBuilding> getTBuilding(){
		List buildlist= tBuildingDAO.findAll();
        return (List<TBuilding>)buildlist;
	}
	
	public Integer addCorporation(TCorporation item){
		tCorporationDAO.attachDirty(item);
		return item.getCorpId();
	}
	
	 public void updateroom(Integer id,Integer corpid,Integer stateid){
		 troomDAO.update(id, corpid,stateid);
	 }
	 
	 public void updateroominfo(Integer id,String owner,String tel){
		 troomDAO.updateroominfo(id, owner, tel);
	 }
	 
	 public void clearroominfo(Integer id,Integer corpid,Integer stateid){
		 troomDAO.clearroom(id, corpid,stateid);
	 }
	 
	 public void decorate(Integer id,Integer stateid){
		 troomDAO.decorateroomitem(id,stateid);
	 }
	 
	 public List<TCorporation> loadcorplistnow(String value,Integer id){
		   return (List<TCorporation>)tCorporationDAO.findByLikeCorpName(value,id);
	 }
	
	 public List<EasyTree> getTCommunity(boolean flag,Integer id){
			//获取区域节点
			List<TCommunity> buildlist= (List<TCommunity>)tCommunityDAO.findAllByCommid(id);
			List<EasyTree> treelist=new ArrayList<EasyTree>();
			EasyTree treenode=null;
			for(int i=0;i<buildlist.size();i++){
				treenode=new EasyTree();
				treenode.setId(buildlist.get(i).getCommId());
				treenode.setCommno(buildlist.get(i).getCommNo());
				treenode.setText(buildlist.get(i).getCommName());
				treenode.setIconCls("icon-home");
				treenode.setParentid(buildlist.get(i).getCommParent());
				treenode.setBuild(false);
				treenode.setEndNode(buildlist.get(i).getCommEndNode());
				treelist.add(treenode);
				}
			/*是否加载楼栋*/
			if(!flag){
				List<EasyTree> buildinglist=getTBuildingnode();
				for(int b=0;b<buildinglist.size();b++){
					treelist.add(buildinglist.get(b));
				}
			}
			
	        return treelist;
		}
	
	//获取楼栋节点
	public List<EasyTree> getTBuildingnode(){
		List<EasyTree> treelist=new ArrayList<EasyTree>();
		EasyTree treenode=null;
		List<TBuilding> buildinglist=getTBuilding();
		for(int i=0;i<buildinglist.size();i++){

				treenode=new EasyTree();
				treenode.setId(buildinglist.get(i).getBuilId());
				treenode.setText(buildinglist.get(i).getBuilName());
				treenode.setIconCls("icon-building");
				treenode.setParentid(buildinglist.get(i).getBuilCommunity());
				treenode.setBuild(true);
				treelist.add(treenode);

		}
		return treelist;
	}
	
	public BuildingNode loadbuilddata(Integer id,Integer type){
		BuildingNode node=new BuildingNode();
		TBuilding nodebuild=loadbulidingbyid(id);  //获取楼栋属性
		List<TFloor> floorsList=(List<TFloor>)tFloorDAO.findAllById(id);  //获取楼层属性
		List rooslist=tFloorDAO.findAllByIddata(id); // 获取房间属性
		node.setId(nodebuild.getBuilId());
		node.setName(nodebuild.getBuilName());
		node.setNo(nodebuild.getBuilNo());
		node.setOrder(nodebuild.getBuilOrder());
		node.setPath(null);
		node.setType(null);
		node.setFloors(loadfloornodelist(floorsList,rooslist,type));
		return node;
	}
	
	private List<FloorNode> loadfloornodelist(List<TFloor> floors,List rooslist,Integer floortype){
		List<FloorNode> floordata=new ArrayList<FloorNode>();
		for(int i=0;i<floors.size();i++){
			FloorNode floordatanode=new FloorNode();
			if(floors.get(i).getFlooType().equals(floortype)){  //楼层类型为floortype
			floordatanode.setId(floors.get(i).getFlooId());
			floordatanode.setNo(floors.get(i).getFlooNo());
			floordatanode.setOrder(floors.get(i).getFlooOrder());
			floordatanode.setType(floors.get(i).getFlooType());
			floordatanode.setHeight(floors.get(i).getFlooHeight());
			List<RoomsNode> roomslist=new ArrayList<RoomsNode>();
			for(int b=0;b<rooslist.size();b++){
				 Object[] bb=(Object[])rooslist.get(b);
				 if(bb[0].equals(floors.get(i).getFlooId())){
					 RoomsNode roomnode=new RoomsNode();
					 CorpNode corpitem=new CorpNode();
					 roomnode.setId(Integer.parseInt(bb[2]==null?"0":bb[2].toString()));
					 roomnode.setNo(bb[3]==null?"":bb[3].toString());
					 roomnode.setOrder(bb[4]==null?"":bb[4].toString());
					 roomnode.setState(Integer.parseInt(bb[5]==null?"0":bb[5].toString()));
					 roomnode.setArea(Float.parseFloat(bb[6]==null?"0":bb[6].toString()));
					 roomnode.setOwner(bb[16]==null?"":bb[16].toString());
					 roomnode.setOwnertel(bb[17]==null?"":bb[17].toString());
					 if(bb[7]==null){
						 roomnode.setCorp(null);
					 }else{
					 corpitem.setId(Integer.parseInt(bb[7]==null?"0":bb[7].toString()));
					 corpitem.setName(bb[8]==null?"":bb[8].toString());
					 corpitem.setAlias(bb[9]==null?"":bb[9].toString());
					 corpitem.setFullname(bb[10]==null?"":bb[10].toString());
					 corpitem.setContactor(bb[11]==null?"":bb[11].toString());
					 corpitem.setType(Integer.parseInt(bb[14]==null?"0":bb[14].toString()));
					 corpitem.setSize(Integer.parseInt(bb[12]==null?"0":bb[12].toString()));
					 corpitem.setTel(bb[13]==null?"":bb[13].toString());
					 corpitem.setState(Integer.parseInt(bb[15]==null?"0":bb[15].toString()));
					 corpitem.setStaterooms(Integer.parseInt(bb[5]==null?"0":bb[5].toString()));
					 roomnode.setCorp(corpitem);
					 }
					 roomslist.add(roomnode);
				 } 
			}
			floordatanode.setRooms(roomslist);
			floordata.add(floordatanode);
		 }
		}
		return floordata;
	}
	
	/*
	 * 根据楼栋ID获取行业分类
	 *shixin
	 * */
	  public List<Integer> LoadIndustryId(Integer id){
		 return tCorporationDAO.findIndustryByBuildId(id);
	  }
      	
	public List getTFloors(Integer id,String flag){
			return tFloorDAO.findAllByIdnow(id,flag);
	}
	
	  public List loadareainfo(Integer id,String flag){
		  return tFloorDAO.findareafloor(id,flag);
	  }
	  
	  public TBuilding loadbulidingbyid(Integer id){
// 		  String where = "buil_community = " + id;
//		  List list = tBuildingDAO.findByWhere(TBuilding.class.getName(), where);
//		  if(list.size() != 0){
//			  TBuilding tb = list.get(0);
//		  }
//		  return null;
		  return tBuildingDAO.findById(id);
	  }
	  
	  public List loadBuildhascorp(Integer id,String name){
		  return troomDAO.loadbuildcorpdao(id,name);
	  }
	  
	  public void updatecorpstate(Integer stateid,Integer buildid){
		  tCorporationDAO.updatestateitem(stateid, buildid);
	  }

	/**通过楼栋id查询其下所有公司人数规模比例*/  
    public List loadnopinfo(Integer id,String flag) {
		return tFloorDAO.findnopfloor(id,flag);
	}	  
    
    /**通过楼栋id查询其下的房间自持、租赁比例*/
    public List loadsalinfo(Integer id,String flag){
    	return tFloorDAO.findsalfloor(id,flag);
    }
	  /**通过Id查询区域信息 */
	public TCommunity findInfoById(Integer tid) {
		return tCommunityDAO.findById(tid);
	}

	/**新增区域信息*/
	public boolean insertInfo(String name, String no, Integer pid) {
		TCommunity tc = new TCommunity();
		tc.setCommName(name);
		tc.setCommNo(no);
		tc.setCommParent(pid);
		if(pid == 2){
			tc.setCommEndNode(1);
		}else{
			tc.setCommEndNode(0);
		}
		String str = tCommunityDAO.insert(tc);
		if(str != null){
			return true;
		}
		return false;
	}

	/**通过区域名查询区域是否存在*/
	public boolean isExistsByName(String name) {
		List list = tCommunityDAO.findByCommName(name);
		if(list.size() != 0){
			return false;
		}else{
			return  true;
		}
	}
	/**修改区域信息*/
	public boolean editInfo(String name, Integer tid) {
		String sql = "UPDATE t_community SET comm_name = '"+name+"' WHERE comm_id = "+tid;
		int count = tCommunityDAO.updateBySQL(sql);
		if(count == 1){
			return true;
		}
		return false;
	}

	/**删除区域信息*/
	public String deleteInfo(Integer tid) {
		TCommunity tc = tCommunityDAO.findById(tid);
		tCommunityDAO.delete(tc);
		return "1";
	}
	
	/**查询楼栋表里是否存在选择区域*/
	public boolean isExistsByTid(Integer tid) {
		String where = "buil_community = " + tid;
		List list = tBuildingDAO.findByWhere(TBuilding.class.getName(), where);
		if(list.size() > 0){
			return false;
		}
		return true;
	}

	/**获取业态查询按钮查询的数据*/
	public Map<String,List<CorpRoomBuildComInd>> uicListFindByWhere(String formData) {
		// TODO Auto-generated method stub
		JSONObject joBzc = JSONObject.fromObject(formData);
		String whereSql = " 1=1 ";
		if(joBzc.get("treeNode") != null && (!joBzc.get("treeNode").equals(""))&&joBzc.get("treeFlag").equals("false")){
			List list = tCommunityDAO.getData("SELECT comm_Id FROM t_community WHERE FIND_IN_SET(comm_Id, getChildList("+Integer.valueOf(joBzc.get("treeNode").toString())+"))");			
			if(list.size()>0){
				whereSql += " and comm_id in (";
				for(int i=0;i<list.size();i++){
					if(i>0&&i<list.size()){
						whereSql +=","+list.get(i);
					}else{
						whereSql +=list.get(i);
					}
				}
				whereSql += " ) ";	
			}
			
		}else{
			whereSql += " and buil_id ="+joBzc.get("treeNode");
		}
		if(joBzc.get("corpName") != null && (!joBzc.get("corpName").equals(""))){
			whereSql += "  and  corp_name like '%"+joBzc.get("corpName")+"%'";
		}
		if(joBzc.get("corpContacts") != null && (!joBzc.get("corpContacts").equals(""))){
			whereSql += "  and  corp_contacts like '%"+joBzc.get("corpContacts")+"%'";
		}
		Map<String,List<CorpRoomBuildComInd>> map = new HashMap<String,List<CorpRoomBuildComInd>>();
		//如果业主姓名不为空，查询以业主名为主导的所有信息，不处理成树形结果。
		if(joBzc.get("roomOwner") != null && (!joBzc.get("roomOwner").equals(""))){
			whereSql += "  and  room_owner like '%"+joBzc.get("roomOwner")+"%'";
			List <CorpRoomBuildComIndChild> list1 = corpRoomBuildComIndChildDAO.findByWhere(CorpRoomBuildComIndChild.class.getName(),whereSql);
			List <CorpRoomBuildComInd> listResult = new ArrayList<CorpRoomBuildComInd>();
			if(list1.size()>0){
				CorpRoomBuildComIndChild obj1= new CorpRoomBuildComIndChild();
				obj1 = list1.get(0);
				CorpRoomBuildComInd obj2 = new CorpRoomBuildComInd();
				obj2.set_parentId(obj1.get_parentId());
				obj2.setState("");
				obj2.setCorpId(obj1.getCorpId());;
				obj2.setCorpName(obj1.getCorpName());
				obj2.setCorpContacts(obj1.getCorpContacts());
				obj2.setCorpTel(obj1.getCorpTel());
				obj2.setInduName(obj1.getInduName());
				obj2.setBuilName(obj1.getBuilName());
				obj2.setFlooNo(obj1.getFlooNo());
				obj2.setRoomNo(obj1.getRoomNo());
				obj2.setCorpState1(obj1.getCorpState1());
				obj2.setRoomState1(obj1.getRoomState1());
				obj2.setRoomArea(obj1.getRoomArea());
				obj2.setRoomOwner(obj1.getRoomOwner());
				obj2.setRoomOwnerTel(obj1.getRoomOwnerTel());
				obj2.setCommId(obj1.getCommId());
				obj2.setBuilCommunity(obj1.getBuilCommunity());
				obj2.setCorpState(obj1.getCorpState());
				obj2.setCorpSizes(obj1.getCorpSizes());
				obj2.setBuilId(obj1.getBuilId());
				obj2.setRoomState(obj1.getRoomState());
				listResult.add(obj2);
			}
			map.put("total", null);
			map.put("rows", listResult);
			return map;
		}else{
			List <CorpRoomBuildComInd> returnList = new ArrayList<CorpRoomBuildComInd>();
			List <CorpRoomBuildComInd> list = corpRoomBuildComIndDAO.findByWhere(CorpRoomBuildComInd.class.getName(), whereSql);
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					CorpRoomBuildComInd obj = new CorpRoomBuildComInd();
					obj = list.get(i);
					List <CorpRoomBuildComIndChild> list1 = corpRoomBuildComIndChildDAO.findByWhere(CorpRoomBuildComIndChild.class.getName(), "parent_id = "+obj.getCorpId());
					if(list1.size()>1){
						obj.setState("closed");
						returnList.add(obj);
						for(int ii=0;ii<list1.size();ii++){
							CorpRoomBuildComIndChild obj1= new CorpRoomBuildComIndChild();
							obj1 = list1.get(ii);
							CorpRoomBuildComInd obj2 = new CorpRoomBuildComInd();
							obj2.set_parentId(obj1.get_parentId());
							obj2.setCorpId(obj1.getCorpId());;
							obj2.setCorpName(obj1.getCorpName());
							obj2.setCorpContacts(obj1.getCorpContacts());
							obj2.setCorpTel(obj1.getCorpTel());
							obj2.setInduName(obj1.getInduName());
							obj2.setBuilName(obj1.getBuilName());
							obj2.setFlooNo(obj1.getFlooNo());
							obj2.setRoomNo(obj1.getRoomNo());
							obj2.setCorpState1(obj1.getCorpState1());
							obj2.setRoomState1(obj1.getRoomState1());
							obj2.setRoomArea(obj1.getRoomArea());
							obj2.setRoomOwner(obj1.getRoomOwner());
							obj2.setRoomOwnerTel(obj1.getRoomOwnerTel());
							obj2.setCommId(obj1.getCommId());
							obj2.setBuilCommunity(obj1.getBuilCommunity());
							obj2.setCorpState(obj1.getCorpState());
							obj2.setCorpSizes(obj1.getCorpSizes());
							obj2.setBuilId(obj1.getBuilId());
							obj2.setRoomState(obj1.getRoomState());
							returnList.add(obj2);
						}
					}else{
						obj.setState("");
						returnList.add(obj);
					}
				}
				map.put("total", null);
				map.put("rows", returnList);
				return map;
			
			}else{
				return null;
			}
		}
	}

	/*点击业态报表中的grid时查询公司信息*/
	public Map<String,List<CorpRoomBuildComInd>> querySelGridDataToShow(String formData1) {
		// TODO Auto-generated method stub
		JSONObject joBzc1 = JSONObject.fromObject(formData1);
		String sql  = " 1=1 ";
		String where = " 1=1 ";
		if(joBzc1.get("treeFlag").equals("false")){
			List  list  = corpRoomBuildComIndDAO.getData("SELECT comm_id FROM t_community  WHERE  FIND_IN_SET(comm_Id, getChildList("+joBzc1.get("treeId")+"))");
			String inWhere = "(";
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					if(i>0&&i<list.size()){
						inWhere +=","+list.get(i);
					}else{
						inWhere +=list.get(i);
					}
				}
				inWhere += ")";
			}
			sql  += " AND buil_community IN "+inWhere;
		}else{
			sql  += " AND buil_id ="+joBzc1.get("treeId");
		}
		if(joBzc1.get("type").equals("业态户数")){
			where += " AND indu_name='"+joBzc1.get("where")+"'";
			return getResuLtData(sql,where,"treeGrid");
		}else if(joBzc1.get("type").equals("业态面积")){
			where += " AND indu_name='"+joBzc1.get("where")+"'";
			return getResuLtData(sql,where,"dataGrid");
		}else if(joBzc1.get("type").equals("入驻面积")){
			where += " AND room_state ="+Integer.valueOf(joBzc1.get("where").toString());
			return getResuLtData(sql,where,"dataGrid");
		}else if(joBzc1.get("type").equals("人数规模")){
			int num = 0;
			if(replaceBlank(joBzc1.get("where").toString()).equals("10人以内")){
				num = 1;
			}else if(replaceBlank(joBzc1.get("where").toString()).equals("10-49人")){
				num = 2;
			}else if(replaceBlank(joBzc1.get("where").toString()).equals("50-199人")){
				num= 3;
			}else if(replaceBlank(joBzc1.get("where").toString()).equals("200-499人")){
				num= 4;
			}else if(replaceBlank(joBzc1.get("where").toString()).equals("500-999人")){
				num= 5;
			}else if(replaceBlank(joBzc1.get("where").toString()).equals("1000-4999人")){
				num= 6;
			}else if(replaceBlank(joBzc1.get("where").toString()).equals("5000人以上")){
				num= 7;
			}
			where += " AND corp_sizes ="+num;
			return getResuLtData(sql,where,"treeGrid");
		}else if(replaceBlank(joBzc1.get("type").toString()).equals("自赁比例")){
			if(replaceBlank(joBzc1.get("where").toString()).equals("自持")){
				where += " AND corp_state =1";
			}else if(replaceBlank(joBzc1.get("where").toString()).equals("租赁")){
				where += " AND corp_state =2";
			}
			return getResuLtData(sql,where,"dataGrid");
		}else{
			return null;
		}
	}

	/*去除字符串中的空格、回车、换行符、制表符*/
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
    
    public void delcorption(Integer id){
    	TCorporation item=tCorporationDAO.findById(id);
    	tCorporationDAO.delete(item);
    }
    
    //处理查询条件
	public Map<String, List<CorpRoomBuildComInd>> getResuLtData(String sql,String where, String type) {
		List<CorpRoomBuildComInd> returnList = new ArrayList<CorpRoomBuildComInd>();
		Map<String, List<CorpRoomBuildComInd>> map = new HashMap<String, List<CorpRoomBuildComInd>>();
		if (type.equals("dataGrid")) {
			// dataGrid类型显示
			List<CorpRoomBuildComIndChild> list1 = corpRoomBuildComIndChildDAO
					.findByWhere(CorpRoomBuildComIndChild.class.getName(), sql+ " AND " + where+" order by corp_contacts,floo_no,room_no asc");
			if (list1.size() > 0) {
				for (int ii = 0; ii < list1.size(); ii++) {
					CorpRoomBuildComIndChild obj1 = new CorpRoomBuildComIndChild();
					obj1 = list1.get(ii);
					CorpRoomBuildComInd obj2 = new CorpRoomBuildComInd();
					obj2.set_parentId(obj1.get_parentId());
					obj2.setCorpId(obj1.getCorpId());
					obj2.setCorpName(obj1.getCorpName());
					obj2.setCorpContacts(obj1.getCorpContacts());
					obj2.setCorpTel(obj1.getCorpTel());
					obj2.setInduName(obj1.getInduName());
					obj2.setBuilName(obj1.getBuilName());
					obj2.setFlooNo(obj1.getFlooNo());
					obj2.setRoomNo(obj1.getRoomNo());
					obj2.setCorpState1(obj1.getCorpState1());
					obj2.setRoomState1(obj1.getRoomState1());
					obj2.setRoomArea(obj1.getRoomArea());
					obj2.setRoomOwner(obj1.getRoomOwner());
					obj2.setRoomOwnerTel(obj1.getRoomOwnerTel());
					obj2.setCommId(obj1.getCommId());
					obj2.setBuilCommunity(obj1.getBuilCommunity());
					obj2.setCorpState(obj1.getCorpState());
					obj2.setCorpSizes(obj1.getCorpSizes());
					obj2.setBuilId(obj1.getBuilId());
					obj2.setCorpSizes1(obj1.getCorpSizes1());
					obj2.setRoomState(obj1.getRoomState());
					returnList.add(obj2);
				}
    			map.put("total", null);
    			map.put("rows", returnList);
    			return map;
			}else{
				return null;
			}

		} else {
			// treeGrid类型显示
			List<CorpRoomBuildComInd> list = corpRoomBuildComIndDAO.findByWhere(CorpRoomBuildComInd.class.getName(), sql+" AND "+where);
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					CorpRoomBuildComInd obj = new CorpRoomBuildComInd();
					obj = list.get(i);
					List<CorpRoomBuildComIndChild> list1 = corpRoomBuildComIndChildDAO.findByWhere(CorpRoomBuildComIndChild.class.getName(),
									"parent_id = " + obj.getCorpId() + " AND "+ where+" order by floo_no,room_no asc");
						if(list1.size() >1) {
							obj.setState("closed");
							returnList.add(obj);
							for (int ii = 0; ii < list1.size(); ii++) {
								CorpRoomBuildComIndChild obj1 = new CorpRoomBuildComIndChild();
								obj1 = list1.get(ii);
								CorpRoomBuildComInd obj2 = new CorpRoomBuildComInd();
								obj2.set_parentId(obj1.get_parentId());
								obj2.setCorpId(obj1.getCorpId());
								obj2.setCorpName(obj1.getCorpName());
								obj2.setCorpContacts(obj1.getCorpContacts());
								obj2.setCorpTel(obj1.getCorpTel());
								obj2.setInduName(obj1.getInduName());
								obj2.setBuilName(obj1.getBuilName());
								obj2.setFlooNo(obj1.getFlooNo());
								obj2.setRoomNo(obj1.getRoomNo());
								obj2.setCorpState1(obj1.getCorpState1());
								obj2.setRoomState1(obj1.getRoomState1());
								obj2.setRoomArea(obj1.getRoomArea());
								obj2.setRoomOwner(obj1.getRoomOwner());
								obj2.setRoomOwnerTel(obj1.getRoomOwnerTel());
								obj2.setCommId(obj1.getCommId());
								obj2.setBuilCommunity(obj1.getBuilCommunity());
								obj2.setCorpState(obj1.getCorpState());
								obj2.setCorpSizes(obj1.getCorpSizes());
								obj2.setBuilId(obj1.getBuilId());
								obj2.setRoomState(obj1.getRoomState());
								obj2.setCorpSizes1(obj1.getCorpSizes1());
								returnList.add(obj2);
							}
						} else {
							obj.setState("");
							returnList.add(obj);
						}
				}
				map.put("total", null);
				map.put("rows", returnList);
				return map;
			} else {
				return null;
			}
		}
	}
}
