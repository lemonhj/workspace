package com.honglicheng.dev.estate.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;

import org.apache.struts2.json.JSONUtil;
import org.hibernate.mapping.Array;
import org.springframework.stereotype.Service;

import sun.org.mozilla.javascript.internal.ObjArray;

import com.honglicheng.dev.estate.dao.ComIndustryDAO;
import com.honglicheng.dev.estate.dao.TBuildingDAO;
import com.honglicheng.dev.estate.dao.TCommunityDAO;
import com.honglicheng.dev.estate.dao.TCorporationDAO;
import com.honglicheng.dev.estate.dao.TFloorDAO;
import com.honglicheng.dev.estate.dao.TIndustryColorDAO;
import com.honglicheng.dev.estate.dao.TRoomDAO;
import com.honglicheng.dev.estate.model.ComIndustry;
import com.honglicheng.dev.estate.model.IndustryTypeInfo;
import com.honglicheng.dev.estate.model.RoomTypeData;
import com.honglicheng.dev.estate.model.TBuilding;
import com.honglicheng.dev.estate.model.TCommunity;
import com.honglicheng.dev.estate.model.TCommunityComBo;
import com.honglicheng.dev.estate.model.TCorporation;
import com.honglicheng.dev.estate.model.TFloor;
import com.honglicheng.dev.estate.model.TIndustryColor;
import com.honglicheng.dev.estate.model.TRoom;
import com.honglicheng.dev.estate.model.TbuilTfloorTroom;
import com.sun.org.apache.bcel.internal.generic.LUSHR;
@Service("FormatsTypeMgrFacade")
public class FormatsTypeMgrImpl implements FormatsTypeMgrFacade{
	
	@Resource
	TBuildingDAO tBuildingDAO;
	@Resource
	TFloorDAO tFloorDAO;
	@Resource
	TRoomDAO tRoomDAO;
	
	@Resource
	ComIndustryDAO comIndustryDAO;
	
	@Resource
	TIndustryColorDAO tIndustryColorDAO;
	@Resource
	TCommunityDAO tCommunityDAO;
	
	@Resource
	TCorporationDAO tCorporationDAO;
	public  List<TFloor> inserterData(TBuilding tbu) {
		// TODO Auto-generated method stub
		if(tbu.getBuilId() == null){
			tBuildingDAO.insert(tbu);
			List<TFloor> list = new ArrayList<TFloor>();
			list = saveTFloor(tbu);
			return list;
		}else{
			tBuildingDAO.saveOrUpdate(tbu);
			List<TFloor> list = new ArrayList<TFloor>();
			list = tBuildingDAO.findByWhere(TFloor.class.getName(),"floo_building="+tbu.getBuilId());
			return list;
		}
	}
	private  List<TFloor> saveTFloor(TBuilding tbu) {
		// TODO Auto-generated method stub
		List<TFloor> list = (List<TFloor>) tBuildingDAO.findByWhere(TFloor.class.getName(), "floo_building="+tbu.getBuilId());
		if(list.size()>0){
			String whereIn  = "(";
			for(int ii = 0 ; ii <list.size();ii++){
				TFloor  objTf = new TFloor();
				objTf = list.get(ii);
				if(ii>0&&ii<list.size()){
					whereIn +=","+objTf.getFlooId();
				}else{
					whereIn +=objTf.getFlooId();
				}
			}
			whereIn += ")";
			List<TRoom> listRoom = (List<TRoom>) tBuildingDAO.findByWhere(TRoom.class.getName(), " room_floor in "+whereIn);
			if(listRoom.size()>0){
				tRoomDAO.deleteAll(listRoom);
			}
			tBuildingDAO.deleteAll(list);
		}
		//地下层
		for(int j = tbu.getBuildBottom(); j>0;j--){
			TFloor tf = new TFloor();
			tf.setFlooId(j);
			tf.setFlooBuilding(tbu.getBuilId());
			tf.setFlooOrder(-(j));
			tf.setFlooNo("");
			tf.setFlooType(1);
			tBuildingDAO.insert(tf);
		}
		//标准层
		for(int i=0;i<tbu.getBuilFloor();i++){
			TFloor tf = new TFloor();
			tf.setFlooId(i);
			tf.setFlooBuilding(tbu.getBuilId());
			tf.setFlooOrder((i)+1);
			tf.setFlooNo("");
			tf.setFlooType(1);
			tBuildingDAO.insert(tf);
//			if(tf.getFlooId() !=  null){
//				for(int j = 1;j<11;j++){
//					TRoom  obj = new TRoom();
//					obj.setRoomFloor(tf.getFlooId());
//					obj.setRoomOrder(j);
//					obj.setRoomNo(String.valueOf(((i+1)*100+j)));
//					obj.setRoomArea(20);
//					obj.setRoomState(0);
//					tRoomDAO.insert(obj);
//				}
//			}
		}
		List<TFloor> list1 = (List<TFloor>) tBuildingDAO.findByWhere(TFloor.class.getName(), "floo_building="+tbu.getBuilId());
		return list1;
	}
	
	public String deleteData(String id, String result) {
		// TODO Auto-generated method stub
		String where = id;
		TBuilding obj = (TBuilding) this.tBuildingDAO.findById(TBuilding.class.getName(), id);
		if(obj == null && obj.equals("")){
			return "该条记录不存在";
		}else{
			this.tBuildingDAO.delete(obj);
			List<TFloor> list = this.tBuildingDAO.findByWhere(TFloor.class.getName(), "floo_building="+id);
			if(list.size()>0){
				this.tBuildingDAO.deleteAll(list);
			}
			return "删除成功";
		}
	}
	
	/**查询行业信息类型表*/
	public List<IndustryTypeInfo> getFormatsData() {
		List<ComIndustry> listc = comIndustryDAO.findByWhere(ComIndustry.class.getName(), "1=1");
		List<IndustryTypeInfo> list = new ArrayList<IndustryTypeInfo>();
		for(int i=0;i<listc.size();i++){
			IndustryTypeInfo iti = new IndustryTypeInfo();
			iti.setId(listc.get(i).getInduId());
			iti.setName(listc.get(i).getInduName());
			iti.setColor(tIndustryColorDAO.findById(listc.get(i).getInduId()).getInduColor());
			list.add(iti);
		}
		return list;
	}
	
	/**查询颜色是否存在*/
	public boolean isColorExists(String color) {
		String where  =  " indu_color ='"+color.toUpperCase()+"'";
		List<TIndustryColor> list = tIndustryColorDAO.findByWhere(TIndustryColor.class.getName(), where);
		if(list.size() == 0){
			return true;
		}
		return false;
	}
	
	/**查询类型是否存在*/
	public boolean isNameExists(String name) {
		String where = " indu_name = '" + name + "'";
		List<ComIndustry> list = comIndustryDAO.findByWhere(ComIndustry.class.getName(), where);
		if(list.size() == 0){
			return true;
		}
		return false;
	}
	
	/**新增类型*/
	public boolean addType(String name, String color) {
		String sql = "INSERT INTO hlc_estate.com_industry (indu_parent,indu_name,indu_alias,indu_fullname)VALUES(0,'"+name+"','"+name+"','"+name+"');";
		//插入类型表
		if(comIndustryDAO.updateBySQL(sql) == 1){						//插入类型成功
			List<ComIndustry> list = comIndustryDAO.findByInduName(name);
			String sql1 = "INSERT INTO hlc_estate.t_industry_color (indu_id, indu_color) VALUES("+list.get(0).getInduId()+",'"+color+"');";
			//插入颜色表
			if(tIndustryColorDAO.updateBySQL(sql1) == 1){ 				//插入颜色表成功
				return true;
			}else{ 														//插入颜色表失败
				//删除类型表中数据
				comIndustryDAO.delete(list.get(0));
			}
		}
		return false;
	}
	
	/** 删除类型 */
	public Integer deleteTypes(Integer[] ids) {
		int count = 0;
		for(int i=0;i<ids.length;i++){
			Object tempc = comIndustryDAO.findByCompId(ComIndustry.class.getName(), ids[i]);
			comIndustryDAO.delete(tempc);
			Object tempt = tIndustryColorDAO.findByCompId(TIndustryColor.class.getName(), ids[i]);
			tIndustryColorDAO.delete(tempt);
			count++;
		}
		return count;
	}
	
	/** 通过Id查询类型 */
	public IndustryTypeInfo findByCid(Integer cid) {
		ComIndustry ci = (ComIndustry) comIndustryDAO.findByCompId(ComIndustry.class.getName(), cid);
		TIndustryColor tc = tIndustryColorDAO.findById(cid);
		IndustryTypeInfo iti = new IndustryTypeInfo();
		if(ci != null && tc != null){
			iti.setId(cid);
			iti.setName(ci.getInduName());
			iti.setColor(tc.getInduColor());
			return iti;
		}
		return null;
	}
	
	/** 修改选择类型 */
	public boolean editType(Integer id, String name, String color) {
		String sql1 = "UPDATE hlc_estate.com_industry set indu_name = '"+name+"',indu_alias = '"+name+"',indu_fullname = '"+name+"' WHERE indu_id = "+id;
		int countc = comIndustryDAO.updateBySQL(sql1);
		String sql2 = "UPDATE hlc_estate.t_industry_color SET indu_color = '"+color+"' WHERE indu_id = "+id;
		int countt = tIndustryColorDAO.updateBySQL(sql2);
		if(countc == 1 && countt == 1){
			return true;
		}
		return false;
	}
	
	/** 查询除了某个ID的类型是否存在 */
	public boolean isNameExistsNoId(String name, Integer id) {
		String where = " indu_name = '" + name + "' and indu_id not in (" + id + ")";
		List<ComIndustry> list = comIndustryDAO.findByWhere(ComIndustry.class.getName(), where);
		if(list.size() == 0){
			return true;
		}
		return false;
	}
	
	/** 查询除了某个ID的颜色是否存在 */
	public boolean isColorExistsNoId(String color, Integer id) {
		String where  =  " indu_color ='"+color.toUpperCase()+"' and indu_id not in (" + id + ")";
		List<TIndustryColor> list = tIndustryColorDAO.findByWhere(TIndustryColor.class.getName(), where);
		if(list.size() == 0){
			return true;
		}
		return false;
	}
	
	/**查询是否有公司绑定了要删除的业态类型*/
	public List canDeleteTypes(String del) {
		List list = comIndustryDAO.findcantDeleteIds(del);
		return list;
	}
	
	public List<TBuilding> getTbuildingDataList(String treeId) {
		// TODO Auto-generated method stub
		String whereSql = "1=1";
		List<TBuilding> resultData = new ArrayList<TBuilding>();
		if(!treeId.equals("")){
			List list = tBuildingDAO.getData("SELECT comm_Id FROM t_community WHERE FIND_IN_SET(comm_Id, getChildList("+treeId+"))");
			if(list.size()>0){
				whereSql += " and buil_Community in (";
				for(int i=0;i<list.size();i++){
					if(i>0&&i<list.size()){
						whereSql +=","+list.get(i);
					}else{
						whereSql +=list.get(i);
					}
				}
				whereSql +=")";
			}
		}
		List<TBuilding> list = this.tBuildingDAO.findByWhere(TBuilding.class.getName(), whereSql);
		for(int i=0;i<list.size();i++){
			TBuilding obj = new TBuilding();
			obj = list.get(i);
			obj.setBuilParentId(tCommunityDAO.findById(obj.getBuilCommunity()).getCommParent());
			List getArea = tRoomDAO.getData("SELECT IF(SUM(room_area) IS NULL,0,SUM(room_area)) FROM  hlc_estate.t_room  WHERE room_floor IN ( SELECT 	floo_id FROM  hlc_estate.t_floor   WHERE floo_building = (SELECT buil_id FROM  hlc_estate.t_building   WHERE buil_id = "+obj.getBuilId()+"))");
			obj.setBuilScal(Float.valueOf(getArea.get(0).toString()));
			List getFloorCount = tFloorDAO.getData(" SELECT count(*) FROM  hlc_estate.t_floor   WHERE floo_building = (SELECT buil_id FROM  hlc_estate.t_building   WHERE buil_id = "+obj.getBuilId()+") AND floo_order >0");
			obj.setFloorCount(Integer.valueOf(getFloorCount.get(0).toString()));
			resultData.add(obj);
		}
		return resultData;
	}
	
	//搜索功能实现
	public List<TBuilding> queryDataByWhere(TBuilding tbu) {
		// TODO Auto-generated method stub
		String where = " 1=1 ";
		String inWhere  = "";
		if((tbu.getBuilCommunity()!= null && tbu.getBuilCommunity().toString() != "")&&(!tbu.getBuilName().equals("") && tbu.getBuilName() != null)){
			List list = tBuildingDAO.getData("SELECT comm_Id FROM t_community WHERE FIND_IN_SET(comm_Id, getChildList("+tbu.getBuilCommunity()+"))");
			if(list.size()>0){
				inWhere += " in (";
				for(int i=0;i<list.size();i++){
					if(i>0&&i<list.size()){
						inWhere +=","+list.get(i);
					}else{
						inWhere +=list.get(i);
					}
				}
				inWhere +=")";
			}
			if(inWhere  == ""){
				inWhere = "1=1";
			}
			where = " buil_Community "+inWhere+"  and buil_Name  like '%"+tbu.getBuilName().toString()+"%'";
		}else{
			if(tbu.getBuilCommunity()!= null && tbu.getBuilCommunity().toString() != ""){
				List list1 = tBuildingDAO.getData("SELECT comm_Id FROM t_community WHERE FIND_IN_SET(comm_Id, getChildList("+tbu.getBuilCommunity()+"))");
				if(list1.size()>0){
					inWhere += " in (";
					for(int i=0;i<list1.size();i++){
						if(i>0&&i<=list1.size()){
							inWhere +=","+list1.get(i);
						}else{
							inWhere +=list1.get(i);
						}
					}
					inWhere += ")";
				}
				where = " buil_Community"+inWhere;
			}else if(!tbu.getBuilName().equals("") && tbu.getBuilName() != null){
				where = " buil_Name  like '%"+tbu.getBuilName().toString()+"%'";
			}
		}
		List<TBuilding>  listData2  = new ArrayList<TBuilding>();
		List<TBuilding>  listData1 = this.tBuildingDAO.findByWhere(TBuilding.class.getName(), where);
		for(int i=0;i<listData1.size();i++){
			TBuilding obj = new TBuilding();
			obj = listData1.get(i);
			obj.setBuilParentId(tCommunityDAO.findById(obj.getBuilCommunity()).getCommParent());
			List getArea = tRoomDAO.getData("SELECT IF(SUM(room_area) IS NULL,0,SUM(room_area)) FROM  hlc_estate.t_room  WHERE room_floor IN ( SELECT 	floo_id FROM  hlc_estate.t_floor   WHERE floo_building = (SELECT buil_id FROM  hlc_estate.t_building   WHERE buil_id = "+obj.getBuilId()+"))");
			obj.setBuilScal(Float.valueOf(getArea.get(0).toString()));
			List getFloorCount = tFloorDAO.getData(" SELECT count(*) FROM  hlc_estate.t_floor   WHERE floo_building = (SELECT buil_id FROM  hlc_estate.t_building   WHERE buil_id = "+obj.getBuilId()+") AND floo_order >0");
			obj.setFloorCount(Integer.valueOf(getFloorCount.get(0).toString()));
			listData2.add(obj);
		}
		return listData2;
	}
	public TBuilding findByIdGetDataList(String builId) {
		// TODO Auto-generated method stub
		TBuilding tbList = tBuildingDAO.findById(Integer.valueOf(builId));
		List<TFloor>  list1 = tFloorDAO.findByWhere(TFloor.class.getName(), "floo_building="+tbList.getBuilId()+" AND floo_order >0");
		List<TFloor>  list2 = tFloorDAO.findByWhere(TFloor.class.getName(), "floo_building="+tbList.getBuilId()+" AND floo_order <0");
		tbList.setBuildBottom(list2.size());
		tbList.setBuilFloor(list1.size());
		return tbList;
	}
	public List<TFloor> findByWhereGetList(String builId) {
		// TODO Auto-generated method stub
		List<TFloor> tfList = tFloorDAO.findByWhere(TFloor.class.getName(), "floo_building="+Integer.valueOf(builId));
		return tfList;
	}
	public String deleteTbuildingData(String arrs) {
		// TODO Auto-generated method stub
		List<TBuilding> deleteList1 = tBuildingDAO.findByWhere(TBuilding.class.getName(), " buil_id  in ("+arrs+")");
		if(deleteList1.size()>0){
			List<TFloor>  deleteList2 = tFloorDAO.findByWhere(TFloor.class.getName(), " floo_building in ("+arrs+")");
			if(deleteList2.size()>0){
				String delWhere = "";
				for(int i = 0;i<deleteList2.size();i++){
					TFloor tfObj = new TFloor();
					tfObj = deleteList2.get(i);
					if(i>0&&i<deleteList2.size()){
						delWhere +=","+tfObj.getFlooId();
					}else{
						delWhere +=tfObj.getFlooId();
					}
				}
				List<TRoom> roomList = tRoomDAO.findByWhere(TRoom.class.getName(), " room_floor in  ("+delWhere+")");
				if(roomList.size()>0){
					for(int ii=0;ii<roomList.size();ii++){
						TRoom tf = new TRoom();
						tf = roomList.get(ii);
						if(tf.getRoomCorporation() != null){
							TCorporation obj = new TCorporation();
							obj = tCorporationDAO.findById(Integer.valueOf(tf.getRoomCorporation()));
							if(obj != null){
								tCorporationDAO.delete(obj);
							}
						}
					}
					tRoomDAO.deleteAll(roomList);
				}
				tFloorDAO.deleteAll(deleteList2);
			}
			tBuildingDAO.deleteAll(deleteList1);
			return "SCUESS";
		}else{
			return "ERROR";
		}
	}
	public List<TCommunityComBo> findByAllToTCommunity() {
		// TODO Auto-generated method stub
		List<TCommunity>  list = tCommunityDAO.findAll();
		List<TCommunityComBo> list1 = new ArrayList<TCommunityComBo>();
		if(list.size()>0){
			for(int i = 0 ; i < list.size();i ++){
				TCommunity obj = new TCommunity();
				TCommunityComBo obj1 = new TCommunityComBo();
				obj = list.get(i);
				obj1.setCommId(obj.getCommId());
				obj1.setCommName(obj.getCommName());
				list1.add(obj1);
			}
		}
		return list1;
	}
	//保存楼栋的所有信息
	public TbuilTfloorTroom saveOrUpdataTBTfTRoom(String tBuilfloorRoom) {
		List  list = getListFromJsonArrStr("["+tBuilfloorRoom+"]",TbuilTfloorTroom.class);//获取数据
		TbuilTfloorTroom returnBuiltfloortroom = new TbuilTfloorTroom();
		TbuilTfloorTroom obj = (TbuilTfloorTroom) list.get(0);
		if(obj != null){
			TBuilding tbuilding = obj.getTbuil();//获取楼栋基本信息
			if(tbuilding.getBuilId() != null){
				tBuildingDAO.saveOrUpdate(tbuilding);				
			}else{
				tBuildingDAO.insert(tbuilding);
			}

			returnBuiltfloortroom.setTbuil(tbuilding);
			List<TFloor> tFloorList = obj.gettFloor(); //获取楼层信息
			if(tFloorList.size()>0){
				List<TFloor> returnTfloor = new ArrayList<TFloor>();
				for(int i=0;i<tFloorList.size();i++){
					TFloor tf = new TFloor();
					JSONObject jo = JSONObject.fromObject(tFloorList.get(i));
					tf = (TFloor) JSONObject.toBean(jo, TFloor.class);
					tf.setFlooBuilding(tbuilding.getBuilId());
					if(tf.getFlooId() != null){
						tFloorDAO.saveOrUpdate(tf);
					}else{
						tFloorDAO.insert(tf);
					}
					TFloor tfs = tFloorDAO.findById(tf.getFlooId());
					returnTfloor.add(tf);
					switch(tf.getFlooType()){
						case 1://标准层信息
							List<TRoom> bzc = new ArrayList<TRoom>();
							//获取前台组装的数据
							bzc =  obj.getBzc();
							if(bzc.size()>0 && (tf.getFlooType() == 1)){
								//改变楼层类型，删除以前楼层类型所建的房间
								if(!tf.getFlooType().equals(tfs.getFlooType())){
									List<TRoom> bzcDel = tRoomDAO.findByWhere(TRoom.class.getName(), "room_floor  = "+tf.getFlooId() );
									if(bzcDel.size()>0)tRoomDAO.deleteAll(bzcDel);
								}
								List<TRoom> bzcReturn = new ArrayList<TRoom>();
								for(int bzcCount=0;bzcCount< bzc.size();bzcCount ++){
									TRoom troomValue = new TRoom();
									JSONObject joBzc = JSONObject.fromObject(bzc.get(bzcCount));
									troomValue = (TRoom) JSONObject.toBean(joBzc, TRoom.class);
									//从前台传回来的数据，如果ID不为空，仅修改了房间面积，那么该楼层类型相同的对应房间的房间面积改变
									if(troomValue.getRoomId()!= null){
										List<TRoom> listRoom = tRoomDAO.findByWhere(TRoom.class.getName(), "room_order="
												+troomValue.getRoomOrder()+" AND room_floor IN (SELECT flooId FROM TFloor  WHERE flooType="+tf.getFlooType()+" AND flooBuilding="+tf.getFlooBuilding()+")");
										if(listRoom.size()>0){
											for(int ii=0;ii<listRoom.size();ii++){
												TRoom newObj = new TRoom();
												newObj = listRoom.get(ii);
												if(newObj.getRoomArea() != troomValue.getRoomArea()){
													newObj.setRoomArea(troomValue.getRoomArea());
													tRoomDAO.saveOrUpdate(newObj);
												}
												bzcReturn.add(newObj);
											}
										}
									}else{
										//没有主键ID插入数据
										troomValue.setRoomFloor(tf.getFlooId());
										String roomNoBzc = "";
										if(tf.getFlooOrder()>0){
											if(troomValue.getRoomOrder()<10){
												roomNoBzc = String.valueOf(tf.getFlooOrder())+"-0"+String.valueOf(troomValue.getRoomOrder());
											}else{
												roomNoBzc = String.valueOf(tf.getFlooOrder())+"-"+String.valueOf(troomValue.getRoomOrder());
											}
										}else{
											if(troomValue.getRoomOrder()<10){
												roomNoBzc = "负"+String.valueOf(-tf.getFlooOrder())+"-0"+String.valueOf(troomValue.getRoomOrder());
											}
											roomNoBzc = "负"+String.valueOf(-tf.getFlooOrder())+"-"+String.valueOf(troomValue.getRoomOrder());
										}
										troomValue.setRoomNo(roomNoBzc);
										troomValue.setRoomState(4);
										troomValue.setRoomArea(Float.valueOf(troomValue.getRoomArea()));
										tRoomDAO.insert(troomValue);
										bzcReturn.add(troomValue);
									}
								}
								returnBuiltfloortroom.setBzc(bzcReturn);
							}
							break;
						case 2:
							List<TRoom> bnc = new ArrayList<TRoom>();
							bnc = obj.getBnc();
							if(bnc.size()>0 && (tf.getFlooType() == 2)){
								List<TRoom> bncReturn = new ArrayList<TRoom>();
								if(!tf.getFlooType().equals(tfs.getFlooType())){
									List<TRoom> bncDel =  tRoomDAO.findByWhere(TRoom.class.getName(), "room_floor  = "+tf.getFlooId() );
									if(bncDel.size()>0)tRoomDAO.deleteAll(bncDel);
								}
								for(int bncCount=0;bncCount< bnc.size();bncCount ++){
									TRoom troomBnc = new TRoom();
									JSONObject joBzc = JSONObject.fromObject(bnc.get(bncCount));
									troomBnc = (TRoom) JSONObject.toBean(joBzc, TRoom.class);
									if(troomBnc.getRoomId() != null){
										List<TRoom> listRoom = tRoomDAO.findByWhere(TRoom.class.getName(), "room_order="
												+troomBnc.getRoomOrder()+" AND room_floor IN (SELECT flooId FROM TFloor WHERE flooType="+tf.getFlooType()+" AND flooBuilding="+tf.getFlooBuilding()+")");
										if(listRoom.size()>0){
											for(int ii=0;ii<listRoom.size();ii++){
												TRoom newObj = new TRoom();
												newObj = listRoom.get(ii);
												if(troomBnc.getRoomArea()!=newObj.getRoomArea() ){
													newObj.setRoomArea(troomBnc.getRoomArea());
													tRoomDAO.saveOrUpdate(newObj);
												}
												bncReturn.add(newObj);
											}
										}
									}else{
										troomBnc.setRoomFloor(tf.getFlooId());
										String roomNoBnc = "";
										if(tf.getFlooOrder()>0){
											if(troomBnc.getRoomOrder()<10){
												roomNoBnc = String.valueOf(tf.getFlooOrder())+"-0"+String.valueOf(troomBnc.getRoomOrder());
											}else{
												roomNoBnc = String.valueOf(tf.getFlooOrder())+"-"+String.valueOf(troomBnc.getRoomOrder());
											}
										}else{
											if(troomBnc.getRoomOrder()<10){
												roomNoBnc = "负"+String.valueOf(-tf.getFlooOrder())+"-0"+String.valueOf(troomBnc.getRoomOrder());
											}else{
												roomNoBnc = "负"+String.valueOf(-tf.getFlooOrder())+"-"+String.valueOf(troomBnc.getRoomOrder());
											}
											
										}
										troomBnc.setRoomNo(roomNoBnc);
										troomBnc.setRoomState(4);
										troomBnc.setRoomArea(Float.valueOf(troomBnc.getRoomArea()));
										tRoomDAO.insert(troomBnc);
										bncReturn.add(troomBnc);
									}
								}
								returnBuiltfloortroom.setBnc(bncReturn);
							}
							break;
						case 3:
							//3类型的数据删除
							List<TRoom> room3 =  tRoomDAO.findByWhere(TRoom.class.getName(), "room_floor  = "+tf.getFlooId() );
							if(room3.size()>0){
								tRoomDAO.deleteAll(room3);
							}
							break;
						case 4:
							List<TRoom> tsc1 = new ArrayList<TRoom>();
							tsc1 = obj.getTsc1();
							if(tsc1.size()>0 && (tf.getFlooType() == 4)){
								if(!tf.getFlooType().equals(tfs.getFlooType())){
									List<TRoom> tsc1Del =  tRoomDAO.findByWhere(TRoom.class.getName(), "room_floor  = "+tf.getFlooId() );
									if(tsc1Del.size()>0)tRoomDAO.deleteAll(tsc1Del);
								}
								List<TRoom> tsc1Return = new ArrayList<TRoom>();
								for(int tsc1Count=0;tsc1Count< tsc1.size();tsc1Count ++){
									TRoom troomTsc1 = new TRoom();
									JSONObject joTsc1 = JSONObject.fromObject(tsc1.get(tsc1Count));
									troomTsc1 = (TRoom) JSONObject.toBean(joTsc1, TRoom.class);
									if(troomTsc1.getRoomId()!= null){
										List<TRoom> listRoom = tRoomDAO.findByWhere(TRoom.class.getName(), "room_order="
												+troomTsc1.getRoomOrder()+" AND room_floor IN (SELECT flooId FROM TFloor t WHERE flooType="+tf.getFlooType()+" AND flooBuilding="+tf.getFlooBuilding()+")");
										if(listRoom.size()>0){
											for(int ii=0;ii<listRoom.size();ii++){
												TRoom newObj = new TRoom();
												newObj = listRoom.get(ii);
												if(troomTsc1.getRoomArea()!=newObj.getRoomArea()){
													newObj.setRoomArea(troomTsc1.getRoomArea());
													tRoomDAO.saveOrUpdate(troomTsc1);
												}
												tsc1Return.add(newObj);
											}
										}
									}else{
										troomTsc1.setRoomFloor(tf.getFlooId());
										String roomNoBzc = "";
										if(tf.getFlooOrder()>0){
											if(troomTsc1.getRoomOrder()<10){
												roomNoBzc = String.valueOf(tf.getFlooOrder())+"-0"+String.valueOf(troomTsc1.getRoomOrder());
											}else{
												roomNoBzc = String.valueOf(tf.getFlooOrder())+"-"+String.valueOf(troomTsc1.getRoomOrder());
											}
											
										}else{
											if(troomTsc1.getRoomOrder()<10){
												roomNoBzc = "负"+String.valueOf(-tf.getFlooOrder())+"-0"+String.valueOf(troomTsc1.getRoomOrder());
											}else{
												roomNoBzc = "负"+String.valueOf(-tf.getFlooOrder())+"-"+String.valueOf(troomTsc1.getRoomOrder());
											}
										}
										troomTsc1.setRoomNo(roomNoBzc);
										troomTsc1.setRoomState(4);
										troomTsc1.setRoomArea(Float.valueOf(troomTsc1.getRoomArea()));
										tRoomDAO.insert(troomTsc1);
										tsc1Return.add(troomTsc1);
									}
								}
								returnBuiltfloortroom.setTsc1(tsc1Return);
							}
							break;
						case 5:
							List<TRoom> tsc2 = new ArrayList<TRoom>();
							tsc2 = obj.getTsc2();
							if(tsc2.size()>0 && (tf.getFlooType() == 5)){
							if(!tf.getFlooType().equals(tfs.getFlooType())){
									List<TRoom> tsc2Del =  tRoomDAO.findByWhere(TRoom.class.getName(), "room_floor  = "+tf.getFlooId() );
									if((tsc2Del.size()>0)){
										tRoomDAO.deleteAll(tsc2Del);
									}
								}
								List<TRoom> tsc2Return = new ArrayList<TRoom>();
								for(int tsc2Count=0;tsc2Count< tsc2.size();tsc2Count ++){
									TRoom troomTsc2 = new TRoom();
									JSONObject joTsc1 = JSONObject.fromObject(tsc2.get(tsc2Count));
									troomTsc2 = (TRoom) JSONObject.toBean(joTsc1, TRoom.class);
									if(troomTsc2.getRoomId()!=null){
										List<TRoom> listRoom = tRoomDAO.findByWhere(TRoom.class.getName(), "room_order="
												+troomTsc2.getRoomOrder()+" AND room_floor IN (SELECT flooId FROM TFloor  WHERE flooType="+tf.getFlooType()+" AND flooBuilding="+tf.getFlooBuilding()+")");
										if(listRoom.size()>0){
											for(int ii=0;ii<listRoom.size();ii++){
												TRoom newObj = new TRoom();
												newObj = listRoom.get(ii);
												if(troomTsc2.getRoomArea() != newObj.getRoomArea()){
													newObj.setRoomArea(troomTsc2.getRoomArea());
													tRoomDAO.saveOrUpdate(newObj);
												}
												tsc2Return.add(newObj);
											}
										}
										
									}else{
										troomTsc2.setRoomFloor(tf.getFlooId());
										String roomNoBzc = "";
										if(tf.getFlooOrder()>0){
											if(troomTsc2.getRoomOrder()<10){
												roomNoBzc = String.valueOf(tf.getFlooOrder())+"-0"+String.valueOf(troomTsc2.getRoomOrder());
											}else{
												roomNoBzc = String.valueOf(tf.getFlooOrder())+"-"+String.valueOf(troomTsc2.getRoomOrder());
											}
										}else{
											if(troomTsc2.getRoomOrder()<10){
												roomNoBzc = "负"+String.valueOf(-tf.getFlooOrder())+"-0"+String.valueOf(troomTsc2.getRoomOrder());
											}else{
												roomNoBzc = "负"+String.valueOf(-tf.getFlooOrder())+"-"+String.valueOf(troomTsc2.getRoomOrder());
											}
										}
										troomTsc2.setRoomNo(roomNoBzc);
										troomTsc2.setRoomState(4);
										troomTsc2.setRoomArea(Float.valueOf(troomTsc2.getRoomArea()));
										tRoomDAO.insert(troomTsc2);
										tsc2Return.add(troomTsc2);
									}
								}
								returnBuiltfloortroom.setTsc2(tsc2Return);
							}
							break;
						default :
								break;
					}
				}
			}
		}
		return returnBuiltfloortroom;
	}
	
    /**
     * 将string转换成listBean
     * @param jsonArrStr 需要反序列化的字符串
     * @param clazz 被反序列化之后的类
     * @return 实体list
     */
    @SuppressWarnings("unchecked")
    public static List getListFromJsonArrStr(String jsonArrStr, Class clazz) {  
        JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);  
        List list = new ArrayList();  
        for (int i = 0; i < jsonArr.size(); i++) 
        {  
            list.add(JSONObject.toBean(jsonArr.getJSONObject(i), clazz));  
        }  
        return list;  
    }
    //获取房间信息
	public List<TRoom> findByWhereGetTRoomList(String builId) {
		// TODO Auto-generated method stub
		List<TRoom> trList = tRoomDAO.findByWhere(TRoom.class.getName(), " roomFloor in (select flooId from  TFloor where flooBuilding="+Integer.valueOf(builId)+")");
		return trList;
	}
	//根据值返回房间的LIST值
	public List<TRoom> findByWhereTRoomList(List<TFloor> tFloor) {
		// TODO Auto-generated method stub
		String whereSql = "(";
		for(int i=0;i<tFloor.size();i++){
			TFloor tfObj = new TFloor();
			tfObj = tFloor.get(i);
			if(i>0&&i<tFloor.size()){
				whereSql +=","+tfObj.getFlooId();
			}else{
				whereSql +=tfObj.getFlooId();
			}
		}
		whereSql += ")";
		List<TRoom> list = tRoomDAO.findByWhere(TRoom.class.getName(), " room_floor in "+whereSql);
		return list;
	}
	//保存修改的t_room的值	
	public String saveOrUpdataTRoom(String troomUpdate, String getMainBuildId) {
		// TODO Auto-generated method stub
  		int getTFloorId = 0;
		 JSONObject jsonObj = JSONObject.fromObject(troomUpdate);
		 String[] textValue = {"bzc","bnc","none","tsc1","tsc2"};
		 for(int k=0;k<textValue.length;k++){
			 JSONArray obj = new JSONArray();
			 obj = JSONArray.fromObject(jsonObj.get(textValue[k]));
			 if(obj.size()>1){
				 List<TFloor> listTfl = tFloorDAO.findByWhere(TFloor.class.getName(), " floo_building="+Integer.valueOf(getMainBuildId)+"  and floo_type="+(k+1));
				 if(listTfl.size()>0){
					 for(int i=0;i<listTfl.size();i++){
						 TFloor tf = new TFloor();
						 tf= listTfl.get(i);
						 List<TRoom> listTr = tRoomDAO.findByWhere(TRoom.class.getName(), " room_floor = "+tf.getFlooId());
						 if(listTr.size()>0){
							 tRoomDAO.deleteAll(listTr);
						 }
						 for(int j=0;j<obj.size();j++){
							 TRoom tr = new TRoom();
							 Map<String,String> map = new HashMap<String, String>();
							 map = (Map<String, String>) obj.get(j);
							 tr.setRoomFloor(tf.getFlooId());
							 tr.setRoomOrder(Integer.valueOf(map.get("roomOrder").toString()));
							 String getNumStr = "0";
							 if(tf.getFlooOrder()<0){
								 if(tf.getFlooOrder()<10){
									 getNumStr = "—"+ String.valueOf((-(tf.getFlooOrder()*100))+(j+1));
								 }else{
									 getNumStr = "—"+ String.valueOf((-(tf.getFlooOrder()*1000))+(j+1));
								 }
								 
							 }else{
								 if(tf.getFlooOrder()<10){
									 getNumStr = String.valueOf((tf.getFlooOrder()*100)+(j+1));
								 }else{
									 getNumStr = String.valueOf(((tf.getFlooOrder()*1000))+(j+1));
								 }
							 }
							 tr.setRoomNo(getNumStr);
							 tr.setRoomArea(Float.valueOf(map.get("roomArea").toString()));
							 tr.setRoomState(0);
							 tRoomDAO.insert(tr);
						 }
					 }
				 }
			 }
		 }
		return troomUpdate;
	}
	
	public String deleteTRoomData(String arrRooms) {
		// TODO Auto-generated method stub
		List<TRoom> list = tRoomDAO.findByWhere(TRoom.class.getName(), "room_id in ("+arrRooms+")");
		if(list.size()>0){
			tRoomDAO.deleteAll(list);
			return "SCUESS";
		}else{
			return "ERROR";
		}
		
	}
	public List<TRoom> getTRoomDataDataList(String arrRooms) {
		// TODO Auto-generated method stub
		List<TRoom> list = tRoomDAO.findByWhere(TRoom.class.getName(), "room_id in ("+arrRooms+")");
		return list;
	}
	//动态获取楼层类型
	public List<TCommunityComBo> getComboboxTypeDataList(String getTypeMainId) {
		// TODO Auto-generated method stub
		if(getTypeMainId == null) return null;
		List<TCommunityComBo> list1 = new ArrayList<TCommunityComBo>();
		List list = tFloorDAO.getData("SELECT DISTINCT floo_type FROM t_floor WHERE floo_building='"+Integer.valueOf(getTypeMainId)+"' order by floo_type  asc ");
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				TCommunityComBo obj1 = new TCommunityComBo();
				String getData = list.get(i).toString();
				if(getData.equals("1")){
					obj1.setCommId(Integer.valueOf(getData));
					obj1.setCommName("标准层");
					list1.add(obj1);
				}else
				if(getData.equals("2")){
					obj1.setCommId(Integer.valueOf(getData));
					obj1.setCommName("避难层");
					list1.add(obj1);
				}else
				if(getData.equals("4")){
					obj1.setCommId(Integer.valueOf(getData));
					obj1.setCommName("特殊层1");
					list1.add(obj1);
				}else
				if(getData.equals("5")){
					obj1.setCommId(Integer.valueOf(getData));
					obj1.setCommName("特殊层2");
					list1.add(obj1);
				}
			}
		}
		return list1;
	}
	public Map<String,List<TRoom>> getReturnTRoomDataList(String getTypeMainId) {
		// TODO Auto-generated method stub
		String[] textValue1 = {"bzc","bnc","none","tsc1","tsc2"};
		Map<String,List<TRoom>> map = new HashMap<String, List<TRoom>>();
		for(int i=0;i<textValue1.length;i++){
			if(textValue1[i].equals("none")) continue;
			List list1 = tRoomDAO.getData("SELECT DISTINCT room_order,room_id,room_floor,room_no,room_area,room_state,"
								+"room_corporation,room_owner,room_owner_tel FROM t_room  WHERE room_floor "
								+"IN (SELECT  floo_id  FROM t_floor WHERE floo_building ="+getTypeMainId+" AND floo_type="+(i+1)+") GROUP BY room_order ORDER BY  room_order ASC");
			List<TRoom> listData = new ArrayList<TRoom>();
			for(int j=0;j<list1.size();j++){
				Object[] arr = (Object[])list1.get(j);
				if(arr.length>0){
					TRoom obj = new TRoom();
					obj.setRoomOrder(Integer.valueOf(arr[0].toString()));
					obj.setRoomId(Integer.valueOf(arr[1].toString()));
					obj.setRoomFloor(Integer.valueOf(arr[2].toString()));
					obj.setRoomNo(arr[3].toString());
					obj.setRoomArea(Float.valueOf(arr[4].toString()));
					obj.setRoomState(Integer.valueOf(arr[5].toString()));
					obj.setRoomCorporation(Integer.valueOf((arr[6] != null?arr[6].toString():"0")));
					obj.setRoomOwner(arr[7] != null ?arr[7].toString():"");
					obj.setRoomOwnerTel(arr[8] !=null?arr[8].toString():"");
					obj.setRoomType(Integer.valueOf(String.valueOf(i+1)));
					listData.add(obj);				
				}
			}
			map.put(textValue1[i], listData);
		}
		return map;
	}
	
    /**
     * 将string转换成listBean 属性中包含实体类等 如List<Student> 而Student中含有属性List<Teacher>
     * @param jsonArrStr 需要反序列化的字符串
     * @param clazz 反序列化后的类
     * @param classMap 将属性中包含的如Teacher加入到一个Map中，格式如map.put("teacher",Teacher.class)
     * @return 反序列化后的字符串
     * 使用示例：
        Map classMap = new HashMap();
        //必须要对Parent进行初始化 否则不识别
        Teacher p = new Teacher();
        classMap.put("teacher", p.getClass());
        List mlist = JSONTransfer.getListFromJsonArrStr(resultStr, Student.class, classMap);
     */
    public static List getListFromJsonArrStr(String jsonArrStr, Class clazz, Map classMap) 
    {  
       JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);  
       List list = new ArrayList();  
       for (int i = 0; i < jsonArr.size(); i++) 
       {           
           list.add(JSONObject.toBean(jsonArr.getJSONObject(i), clazz, classMap));  
       }  
       return list;  
    }
    
    /*通过树节点ID获取楼栋信息*/
	public List<TBuilding> getTbuildDataToTreeid(String treeId) {
		// TODO Auto-generated method stub
		List<TBuilding> list = this.tBuildingDAO.findByWhere(TBuilding.class.getName(), " buil_community="+treeId);
		List<TBuilding> resultData = new ArrayList<TBuilding>();
		for(int i=0;i<list.size();i++){
			TBuilding obj = new TBuilding();
			obj = list.get(i);
			obj.setBuilParentId(tCommunityDAO.findById(obj.getBuilCommunity()).getCommParent());
			List getArea = tRoomDAO.getData("SELECT IF(SUM(room_area) IS NULL,0,SUM(room_area)) FROM  hlc_estate.t_room  WHERE room_floor IN ( SELECT 	floo_id FROM  hlc_estate.t_floor   WHERE floo_building = (SELECT buil_id FROM  hlc_estate.t_building   WHERE buil_id = "+obj.getBuilId()+"))");
			obj.setBuilScal(Float.valueOf(getArea.get(0).toString()));
			List getFloorCount = tFloorDAO.getData(" SELECT count(*) FROM  hlc_estate.t_floor   WHERE floo_building = (SELECT buil_id FROM  hlc_estate.t_building   WHERE buil_id = "+obj.getBuilId()+") AND floo_order >0");
			obj.setFloorCount(Integer.valueOf(getFloorCount.get(0).toString()));
			resultData.add(obj);
		}
		return resultData;
	}
	public List<TBuilding> getOnClickTreeNodeDataList(String treeId) {
		// TODO Auto-generated method stub
		String inWhere = " 1=1 ";
		List list = tBuildingDAO.getData("SELECT comm_Id FROM t_community WHERE FIND_IN_SET(comm_Id, getChildList("+treeId+"))");
		if(list.size()>0){
			inWhere += " and buil_Community in (";
			for(int i=0;i<list.size();i++){
				if(i>0&&i<list.size()){
					inWhere +=","+list.get(i);
				}else{
					inWhere +=list.get(i);
				}
			}
			inWhere +=")";
		}
		if(inWhere != ""){
			List<TBuilding>  listData2 = new ArrayList<TBuilding>();
			List<TBuilding>  listData1 = tBuildingDAO.findByWhere(TBuilding.class.getName(),inWhere);
			for(int i=0;i<listData1.size();i++){
				TBuilding obj = new TBuilding();
				obj = listData1.get(i);
				obj.setBuilParentId(tCommunityDAO.findById(obj.getBuilCommunity()).getCommParent());
				List getArea = tRoomDAO.getData("SELECT IF(SUM(room_area) IS NULL,0,SUM(room_area)) FROM  hlc_estate.t_room  WHERE room_floor IN ( SELECT 	floo_id FROM  hlc_estate.t_floor   WHERE floo_building = (SELECT buil_id FROM  hlc_estate.t_building   WHERE buil_id = "+obj.getBuilId()+"))");
				obj.setBuilScal(Float.valueOf(getArea.get(0).toString()));
				List getFloorCount = tFloorDAO.getData(" SELECT count(*) FROM  hlc_estate.t_floor   WHERE floo_building = (SELECT buil_id FROM  hlc_estate.t_building   WHERE buil_id = "+obj.getBuilId()+") AND floo_order >0");
				obj.setFloorCount(Integer.valueOf(getFloorCount.get(0).toString()));
				listData2.add(obj);
			}
			return listData2;
		}else{
			return null;
		}
		
	}
}

