package com.honglicheng.dev.estate.model;

public class CorpRoomBuildComIndChild {
	// Fields

	private Integer _parentId;
	private Integer corpId;
	private String corpName;
	private String corpContacts;
	private String corpTel;
	private String induName;
	private String builName;
	private String flooNo;
	private String roomNo;
	private String corpState1;
	private String roomState1;
	private float roomArea;
	private String roomOwner;
	private String roomOwnerTel;
	private Integer commId;
	private Integer builCommunity;
	private Integer corpState;
	private Integer corpSizes;
	private Integer builId;
	private Integer roomState;
	private String corpSizes1;//公司人数规模

	// Constructors

	/** default constructor */
	public CorpRoomBuildComIndChild() {
	}

	/** minimal constructor 
	 */
	public CorpRoomBuildComIndChild(
			Integer _parentId,Integer corpId,String corpName, String corpContacts,
			String corpTel, String induName,String builName,String flooNo,String corpSizes1,
			String roomNo,String corpState1,String roomState1,float roomArea,
			String roomOwner,String roomOwnerTel,Integer commId,Integer builCommunity,
			Integer corpState,Integer corpSizes,Integer builId,Integer roomState) {
		this._parentId=_parentId;
		this.corpId=corpId;
		this.corpName=corpName;
		this.corpContacts=corpContacts;
		this.corpTel=corpTel;
		this.induName=induName;
		this.builName=builName;
		this.flooNo=flooNo;
		this.roomNo=roomNo;
		this.corpState1=corpState1;
		this.roomState1=roomState1;
		this.roomArea=roomArea;
		this.roomOwner=roomOwner;
		this.roomOwnerTel=roomOwnerTel;
		this.commId=commId;
		this.builCommunity=builCommunity;
		this.corpState=corpState;
		this.corpSizes=corpSizes;
		this.builId=builId;
		this.roomState=roomState;
		this.corpSizes1 = corpSizes1;
	}

	public Integer get_parentId() {
		return _parentId;
	}

	public void set_parentId(Integer _parentId) {
		this._parentId = _parentId;
	}

	public Integer getCorpId() {
		return corpId;
	}

	public void setCorpId(Integer corpId) {
		this.corpId = corpId;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getCorpContacts() {
		return corpContacts;
	}

	public void setCorpContacts(String corpContacts) {
		this.corpContacts = corpContacts;
	}

	public String getCorpTel() {
		return corpTel;
	}

	public void setCorpTel(String corpTel) {
		this.corpTel = corpTel;
	}

	public String getInduName() {
		return induName;
	}

	public void setInduName(String induName) {
		this.induName = induName;
	}

	public String getBuilName() {
		return builName;
	}

	public void setBuilName(String builName) {
		this.builName = builName;
	}

	public String getFlooNo() {
		return flooNo;
	}

	public void setFlooNo(String flooNo) {
		this.flooNo = flooNo;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getCorpState1() {
		return corpState1;
	}

	public void setCorpState1(String corpState1) {
		this.corpState1 = corpState1;
	}

	public String getRoomState1() {
		return roomState1;
	}

	public void setRoomState1(String roomState1) {
		this.roomState1 = roomState1;
	}

	public float getRoomArea() {
		return roomArea;
	}

	public void setRoomArea(float roomArea) {
		this.roomArea = roomArea;
	}

	public String getRoomOwner() {
		return roomOwner;
	}

	public void setRoomOwner(String roomOwner) {
		this.roomOwner = roomOwner;
	}

	public String getRoomOwnerTel() {
		return roomOwnerTel;
	}

	public void setRoomOwnerTel(String roomOwnerTel) {
		this.roomOwnerTel = roomOwnerTel;
	}

	public Integer getCommId() {
		return commId;
	}

	public void setCommId(Integer commId) {
		this.commId = commId;
	}

	public Integer getBuilCommunity() {
		return builCommunity;
	}

	public void setBuilCommunity(Integer builCommunity) {
		this.builCommunity = builCommunity;
	}

	public Integer getCorpState() {
		return corpState;
	}

	public void setCorpState(Integer corpState) {
		this.corpState = corpState;
	}

	public Integer getCorpSizes() {
		return corpSizes;
	}

	public void setCorpSizes(Integer corpSizes) {
		this.corpSizes = corpSizes;
	}

	public Integer getBuilId() {
		return builId;
	}

	public void setBuilId(Integer builId) {
		this.builId = builId;
	}

	public Integer getRoomState() {
		return roomState;
	}

	public void setRoomState(Integer roomState) {
		this.roomState = roomState;
	}

	public String getCorpSizes1() {
		return corpSizes1;
	}

	public void setCorpSizes1(String corpSizes1) {
		this.corpSizes1 = corpSizes1;
	}

}
