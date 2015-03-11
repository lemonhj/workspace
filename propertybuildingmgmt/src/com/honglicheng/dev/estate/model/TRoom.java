package com.honglicheng.dev.estate.model;

/**
 * TRoom entity. @author MyEclipse Persistence Tools
 */
public class TRoom implements java.io.Serializable {

	// Fields

	private Integer roomId;
	private Integer roomFloor;
	private Integer roomOrder;
	private String roomNo;
	private float roomArea;
	private Integer roomState;
	private Integer roomCorporation;
	private Integer roomType;
	private String roomOwner;
	private String roomOwnerTel;

	// Constructors

	/** default constructor */
	public TRoom() {
	}

	/** minimal constructor */
	public TRoom(Integer roomId, Integer roomFloor, String roomNo,
			Integer roomState) {
		super();
	}

	/** full constructor */
	public TRoom(Integer roomId, Integer roomFloor, Integer roomOrder,
			String roomNo, float roomArea, Integer roomState,
			Integer roomCorporation,Integer roomType,String roomOwner,String roomOwnerTel) {
		this.roomId = roomId;
		this.roomFloor = roomFloor;
		this.roomOrder = roomOrder;
		this.roomNo = roomNo;
		this.roomArea = roomArea;
		this.roomState = roomState;
		this.roomCorporation = roomCorporation;
		this.roomType = roomType;
		this.roomOwner = roomOwner;
		this.roomOwnerTel = roomOwnerTel;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Integer getRoomFloor() {
		return roomFloor;
	}

	public void setRoomFloor(Integer roomFloor) {
		this.roomFloor = roomFloor;
	}

	public Integer getRoomOrder() {
		return roomOrder;
	}

	public void setRoomOrder(Integer roomOrder) {
		this.roomOrder = roomOrder;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public float getRoomArea() {
		return roomArea;
	}

	public void setRoomArea(float roomArea) {
		this.roomArea = roomArea;
	}

	public Integer getRoomState() {
		return roomState;
	}

	public void setRoomState(Integer roomState) {
		this.roomState = roomState;
	}

	public Integer getRoomCorporation() {
		return roomCorporation;
	}

	public void setRoomCorporation(Integer roomCorporation) {
		this.roomCorporation = roomCorporation;
	}

	public Integer getRoomType() {
		return roomType;
	}

	public void setRoomType(Integer roomType) {
		this.roomType = roomType;
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
	
}
