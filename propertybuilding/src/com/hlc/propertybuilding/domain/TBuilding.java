package com.hlc.propertybuilding.domain;

import java.io.Serializable;

/**
 * TBuilding entity. @author MyEclipse Persistence Tools
 */
public class TBuilding implements Serializable {

	// Fields

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -8193505025464423930L;
	private Integer builId;
	private Integer builCommunity;
	private Integer builOrder;
	private String builNo;
	private String builName;
	private String builCreate;
	private Integer builFloor;
	private String builMemo;
	private Integer buildBottom;
	private Float builScal;
	private Integer floorCount;
	private Integer builParentId;

	// Constructors
	/** default constructor */
	public TBuilding() {
	}

	/** minimal constructor */
	public TBuilding(Integer builCommunity) {
		super();
	}

	/** full constructor */
	public TBuilding(Integer builId, Integer builCommunity, Integer builOrder,String builNo, String builName,
			String builCreate,Integer builFloor,String  builMemo,Integer buildBottom,Float builScal,Integer floorCount) {
		this.builId = builId;
		this.builCommunity = builCommunity;
		this.builOrder = builOrder;
		this.builNo = builNo;
		this.builName = builName;
		this.builCreate = builCreate;
		this.builFloor = builFloor;
		this.builMemo = builMemo;
		this.buildBottom = buildBottom;
		this.builScal = builScal;
		this.floorCount = floorCount;
	}

	public Integer getBuilId() {
		return builId;
	}

	public void setBuilId(Integer builId) {
		this.builId = builId;
	}

	public Integer getBuilCommunity() {
		return builCommunity;
	}

	public void setBuilCommunity(Integer builCommunity) {
		this.builCommunity = builCommunity;
	}

	public Integer getBuilOrder() {
		return builOrder;
	}

	public void setBuilOrder(Integer builOrder) {
		this.builOrder = builOrder;
	}

	public String getBuilNo() {
		return builNo;
	}

	public void setBuilNo(String builNo) {
		this.builNo = builNo;
	}

	public String getBuilName() {
		return builName;
	}

	public void setBuilName(String builName) {
		this.builName = builName;
	}

	public String getBuilCreate() {
		return builCreate;
	}

	public void setBuilCreate(String builCreate) {
		this.builCreate = builCreate;
	}

	public Integer getBuilFloor() {
		return builFloor;
	}

	public void setBuilFloor(Integer builFloor) {
		this.builFloor = builFloor;
	}

	public String getBuilMemo() {
		return builMemo;
	}

	public void setBuilMemo(String builMemo) {
		this.builMemo = builMemo;
	}

	public Integer getBuildBottom() {
		return buildBottom;
	}

	public void setBuildBottom(Integer buildBottom) {
		this.buildBottom = buildBottom;
	}

	public Float getBuilScal() {
		return builScal;
	}

	public void setBuilScal(Float builScal) {
		this.builScal = builScal;
	}

	public Integer getFloorCount() {
		return floorCount;
	}

	public void setFloorCount(Integer floorCount) {
		this.floorCount = floorCount;
	}

	public Integer getBuilParentId() {
		return builParentId;
	}

	public void setBuilParentId(Integer builParentId) {
		this.builParentId = builParentId;
	}

}
