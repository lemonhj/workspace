package com.honglicheng.dev.estate.model;

import java.util.List;


public class BuildingNode {

	private Integer id;
	private Integer order;
	private String no;
	private String path;
	private String Name;
	private Integer type;
	private List<FloorNode> floors;
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public List<FloorNode> getFloors() {
		return floors;
	}
	public void setFloors(List<FloorNode> floors) {
		this.floors = floors;
	}
	
	
}
