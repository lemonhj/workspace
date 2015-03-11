package com.honglicheng.dev.estate.model;

public class RoomsNode {

	private Integer id;
	private String order;
	private String no;
	private Integer state;
	private Float area;
	private CorpNode corp;
	private String owner;
	private String ownertel;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	
	
	public Float getArea() {
		return area;
	}
	public void setArea(Float area) {
		this.area = area;
	}
	public CorpNode getCorp() {
		return corp;
	}
	public void setCorp(CorpNode corp) {
		this.corp = corp;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getOwnertel() {
		return ownertel;
	}
	public void setOwnertel(String ownertel) {
		this.ownertel = ownertel;
	}
	
	
	
}
