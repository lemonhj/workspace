package com.honglicheng.dev.estate.model;

/**
 * TFloor entity. @author MyEclipse Persistence Tools
 */
public class TFloor  implements java.io.Serializable {

	// Fields

	private Integer flooId;
	private Integer flooBuilding;
	private Integer flooOrder;
	private String flooNo;
	private Integer flooType;
	private float flooHeight;

	// Constructors
	/** default constructor */
	public TFloor() {
	}

	/** minimal constructor */
	public TFloor(Integer flooId, Integer flooBuilding, String flooNo,
			Integer flooType) {
		super();
	}

	/** full constructor */
	public TFloor(Integer flooId, Integer flooBuilding, Integer flooOrder,
			String flooNo, Integer flooType, float flooHeight) {
		this.flooId = flooId;
		this.flooBuilding = flooBuilding;
		this.flooOrder = flooOrder;
		this.flooNo = flooNo;
		this.flooType = flooType;
		this.flooHeight = flooHeight;
	}

	public Integer getFlooId() {
		return flooId;
	}

	public void setFlooId(Integer flooId) {
		this.flooId = flooId;
	}

	public Integer getFlooBuilding() {
		return flooBuilding;
	}

	public void setFlooBuilding(Integer flooBuilding) {
		this.flooBuilding = flooBuilding;
	}

	public Integer getFlooOrder() {
		return flooOrder;
	}

	public void setFlooOrder(Integer flooOrder) {
		this.flooOrder = flooOrder;
	}

	public String getFlooNo() {
		return flooNo;
	}

	public void setFlooNo(String flooNo) {
		this.flooNo = flooNo;
	}

	public Integer getFlooType() {
		return flooType;
	}

	public void setFlooType(Integer flooType) {
		this.flooType = flooType;
	}

	public float getFlooHeight() {
		return flooHeight;
	}

	public void setFlooHeight(float flooHeight) {
		this.flooHeight = flooHeight;
	}

}
