package com.honglicheng.dev.estate.model;

public class RoomTypeData {
	private String roomType;
	private String roomOrder;
	private String roomArea;
	
	public RoomTypeData(){}
	public RoomTypeData(String roomType,String roomOrder,String roomArea){
		this.roomType = roomType;
		this.roomOrder = roomOrder;
		this.roomArea = roomArea;
	}
	
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getRoomOrder() {
		return roomOrder;
	}
	public void setRoomOrder(String roomOrder) {
		this.roomOrder = roomOrder;
	}
	public String getRoomArea() {
		return roomArea;
	}
	public void setRoomArea(String roomArea) {
		this.roomArea = roomArea;
	}
	
}
