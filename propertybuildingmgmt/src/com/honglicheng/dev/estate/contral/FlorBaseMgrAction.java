package com.honglicheng.dev.estate.contral;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;






import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import com.honglicheng.dev.estate.manager.FormatsTypeMgrImpl;
import com.honglicheng.dev.estate.model.RoomTypeData;
import com.honglicheng.dev.estate.model.TBuilding;
import com.honglicheng.dev.estate.model.TCommunityComBo;
import com.honglicheng.dev.estate.model.TFloor;
import com.honglicheng.dev.estate.model.TRoom;
import com.honglicheng.dev.estate.model.TbuilTfloorTroom;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport; 
@Controller
@Scope("prototype")
public class FlorBaseMgrAction  extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	FormatsTypeMgrImpl FormatsTypeMgrFacade;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@JSON(serialize=false)
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	//定义对象，并且返回保存时的对象
	TBuilding tbu;

	public TBuilding getTbu() {
		return tbu;
	}

	public void setTbu(TBuilding tbu) {
		this.tbu = tbu;
	}

	List<TFloor> tFloor;
	public List<TFloor> gettFloor() {
		return tFloor;
	}

	public void settFloor(List<TFloor> tFloor) {
		this.tFloor = tFloor;
	}

	List<TRoom> troomList;
	public List<TRoom> getTroomList() {
		return troomList;
	}

	public void setTroomList(List<TRoom> troomList) {
		this.troomList = troomList;
	}

	//新增保存后返回TBuilding数据ID
	private String returnAddTbId;

	public String getReturnAddTbId() {
		return returnAddTbId;
	}

	public void setReturnAddTbId(String returnAddTbId) {
		this.returnAddTbId = returnAddTbId;
	}

	public String saveOrUpdata(){
		if(tbu.getBuilFloor() == null){
			return Action.ERROR; 
		}else{
			try{
				tFloor = FormatsTypeMgrFacade.inserterData(tbu);
				returnAddTbId = tFloor.get(0).getFlooBuilding().toString();
//				troomList = FormatsTypeMgrFacade.findByWhereTRoomList(tFloor);
			}catch(Exception e){
				e.printStackTrace();
				return Action.ERROR;
			}
		}
        return Action.SUCCESS; 
	}
	
	//返回数据定义
	List<TBuilding> tbuList;
	public List<TBuilding> getTbuList() {
		return tbuList;
	}

	public void setTbuList(List<TBuilding> tbuList) {
		this.tbuList = tbuList;
	}

	public String getTbuildingDataList(){
		tbuList = FormatsTypeMgrFacade.getTbuildingDataList("");
		return Action.SUCCESS; 
	}
	
	//按条件进行查询
	List<TBuilding> tbuListQuery;
	public List<TBuilding> getTbuListQuery() {
		return tbuListQuery;
	}

	public void setTbuListQuery(List<TBuilding> tbuListQuery) {
		this.tbuListQuery = tbuListQuery;
	}

	public String queryDataList(){
//		String where = " 1=1 ";
//		System.out.println("tbu.getBuilCommunity().toString()="+tbu.getBuilCommunity()+"  tbu.getBuilName()="+tbu.getBuilName());
//		if((tbu.getBuilCommunity()!= null && tbu.getBuilCommunity().toString() != "")&&(!tbu.getBuilName().equals("") && tbu.getBuilName() != null)){
//			where = " buil_Community in (SELECT commId FROM TCommunity WHERE FIND_IN_SET(commId, getChildList("+tbu.getBuilCommunity()+")))  and buil_Name  like '%"+tbu.getBuilName().toString()+"%'";
//		}else{
//			if(tbu.getBuilCommunity()!= null && tbu.getBuilCommunity().toString() != ""){
//				where = " buil_Community in (SELECT commId FROM TCommunity WHERE FIND_IN_SET(commId, getChildList("+tbu.getBuilCommunity()+")))";
//			}else if(!tbu.getBuilName().equals("") && tbu.getBuilName() != null){
//				where = " buil_Name  like '%"+tbu.getBuilName().toString()+"%'";
//			}
//		}
//		System.out.println("where="+where);
		tbuListQuery = FormatsTypeMgrFacade.queryDataByWhere(tbu);
		return Action.SUCCESS; 
	}
	
	
	//获取楼栋信息数据及楼层信息数据
	public String builId;
	public String getBuilId() {
		return builId;
	}

	public void setBuilId(String builId) {
		this.builId = builId;
	}

	//点击修改时获取获取楼栋信息及楼栋层数信息
	TBuilding tbuQuery;
	List<TFloor> tFloorQuery;
	List<TRoom> troom;
	
	public List<TRoom> getTroom() {
		return troom;
	}

	public void setTroom(List<TRoom> troom) {
		this.troom = troom;
	}

	public TBuilding getTbuQuery() {
		return tbuQuery;
	}

	public void setTbuQuery(TBuilding tbuQuery) {
		this.tbuQuery = tbuQuery;
	}

	public List<TFloor> gettFloorQuery() {
		return tFloorQuery;
	}

	public void settFloorQuery(List<TFloor> tFloorQuery) {
		this.tFloorQuery = tFloorQuery;
	}

	public String getTFoolAndTBuildDataList(){
		if(builId != null){
			tbuQuery = FormatsTypeMgrFacade.findByIdGetDataList(builId);
			tFloorQuery = FormatsTypeMgrFacade.findByWhereGetList(builId);
			troom = FormatsTypeMgrFacade.findByWhereGetTRoomList(builId);
		}
		return Action.SUCCESS;
	}
	
	//删除楼层信息
	public String arrs;
	//获取树节点ID
	public String getArrar() {
		return arrs;
	}

	public void setArrar(String arrs) {
		this.arrs = arrs;
	}
	
	public String deleteTbuildingData(){
		String message = "SCUESS";
		message = FormatsTypeMgrFacade.deleteTbuildingData(arrs);
		arrs = message;
		if(message == "ERROR"){
			tbuList = FormatsTypeMgrFacade.getTbuildingDataList(treeNodeId);
			return Action.ERROR;
		}else{
			tbuList = FormatsTypeMgrFacade.getTbuildingDataList(treeNodeId);
			return Action.SUCCESS;
		}
	}
	
	//获取楼栋区域JSON，用于combobox的JSON
	public List<TCommunityComBo> getTCommunityJson;  

	public List<TCommunityComBo> getGetTCommunityJson() {
		return getTCommunityJson;
	}

	public void setGetTCommunityJson(List<TCommunityComBo> getTCommunityJson) {
		this.getTCommunityJson = getTCommunityJson;
	}
	
	public String getComboboxData(){
		getTCommunityJson = FormatsTypeMgrFacade.findByAllToTCommunity();
		return Action.SUCCESS;
	}
	
	//保存楼栋所有信息
	private String builfloorRoom;
	private TbuilTfloorTroom tbuiltfloortroom;

	public String getBuilfloorRoom() {
		return builfloorRoom;
	}

	public void setBuilfloorRoom(String builfloorRoom) {
		this.builfloorRoom = builfloorRoom;
	}

	
	public TbuilTfloorTroom getTbuiltfloortroom() {
		return tbuiltfloortroom;
	}

	public void setTbuiltfloortroom(TbuilTfloorTroom tbuiltfloortroom) {
		this.tbuiltfloortroom = tbuiltfloortroom;
	}

	public String saveOrUpdataTBTfTRoom(){
		tbuiltfloortroom =  FormatsTypeMgrFacade.saveOrUpdataTBTfTRoom(builfloorRoom);
		return Action.SUCCESS;
	}

	//保存楼层信息
	public String troomUpdate;
	private String getMainBuildId;
	
	public String getGetMainBuildId() {
		return getMainBuildId;
	}

	public void setGetMainBuildId(String getMainBuildId) {
		this.getMainBuildId = getMainBuildId;
	}

	public String getTroomUpdate() {
		return troomUpdate;
	}

	public void setTroomUpdate(String troomUpdate) {
		this.troomUpdate = troomUpdate;
	}
	
	//保存修改的t_room的值
	public String saveOrUpdataTRoom(){
		troomUpdate = FormatsTypeMgrFacade.saveOrUpdataTRoom(troomUpdate,getMainBuildId);
		return Action.SUCCESS;
	}
	
	//删除楼层房间信息数据
	public String  arrRooms;
	public String getArrRooms() {
		return arrRooms;
	}

	public void setArrRooms(String arrRooms) {
		this.arrRooms = arrRooms;
	}

	public String deleteTRoomData(){
		String message = "SCUESS";
		String troomIds = arrRooms;
		message = FormatsTypeMgrFacade.deleteTRoomData(arrRooms);
		arrRooms = message;
		if(message == "ERROR"){
			troomList = FormatsTypeMgrFacade.getTRoomDataDataList(troomIds);
			return Action.ERROR;
		}else{
			troomList = FormatsTypeMgrFacade.getTRoomDataDataList(troomIds);
			return Action.SUCCESS;
		}
	}
	
	//动态获取楼层类型
	private String getTypeMainId;
	public List<TCommunityComBo> getTypeList;
	private Map<String,List<TRoom>> returnTRoom;
	
	public Map<String,List<TRoom>> getReturnTRoom() {
		return returnTRoom;
	}

	public void setReturnTRoom(Map<String,List<TRoom>> returnTRoom) {
		this.returnTRoom = returnTRoom;
	}

	public String getGetTypeMainId() {
		return getTypeMainId;
	}

	public void setGetTypeMainId(String getTypeMainId) {
		this.getTypeMainId = getTypeMainId;
	}
	
	public List<TCommunityComBo> getGetTypeList() {
		return getTypeList;
	}

	public void setGetTypeList(List<TCommunityComBo> getTypeList) {
		this.getTypeList = getTypeList;
	}

	public String getComboboxTypeData(){
		getTypeList = FormatsTypeMgrFacade.getComboboxTypeDataList(getTypeMainId);
		returnTRoom = FormatsTypeMgrFacade.getReturnTRoomDataList(getTypeMainId);
		return Action.SUCCESS;
	}
	
	/*通过树节点ID获取楼栋信息*/
	
	private String treeId;
	
	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getTbuildDataToTreeid(){
		tbuList = FormatsTypeMgrFacade.getTbuildDataToTreeid(treeId);
		return Action.SUCCESS;
	}
	//点击树节点时显示所有数据
	private String treeNodeId;

	public String getTreeNodeId() {
		return treeNodeId;
	}

	public void setTreeNodeId(String treeNodeId) {
		this.treeNodeId = treeNodeId;
	}

	public String queryOnClickNodeGetList(){
		tbuListQuery = FormatsTypeMgrFacade.getOnClickTreeNodeDataList(treeNodeId);
		return Action.SUCCESS;
	}
}
