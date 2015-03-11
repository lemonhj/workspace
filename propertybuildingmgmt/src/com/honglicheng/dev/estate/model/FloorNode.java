package com.honglicheng.dev.estate.model;

import java.util.List;

public class FloorNode {

	private Integer id;
	private Integer order;
	private String no;
	private Integer type;
	private float height;
	private List<RoomsNode> rooms;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public List<RoomsNode> getRooms() {
		return rooms;
	}
	public void setRooms(List<RoomsNode> rooms) {
		this.rooms = rooms;
	}
	
	

}
