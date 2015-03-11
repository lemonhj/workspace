package com.honglicheng.dev.estate.model;

/**
 * TCommunity entity. @author MyEclipse Persistence Tools
 */
public class TCommunity implements java.io.Serializable {

	// Fields

	private Integer commId;
	private Integer commParent;
	private Integer commOrder;
	private String commNo;
	private String commName;
	private Integer commEndNode;

	// Constructors

	/** default constructor */
	public TCommunity() {
	}

	/** minimal constructor */
	public TCommunity(Integer commId, String commName) {
		super();
	}

	/** full constructor */
	public TCommunity(Integer commId, Integer commParent, Integer commOrder,
			String commNo, String commName,Integer commEndNode) {
		this.commId = commId;
		this.commParent = commParent;
		this.commOrder = commOrder;
		this.commNo = commNo;
		this.commName = commName;
		this.commEndNode = commEndNode;
	}

	public Integer getCommId() {
		return commId;
	}

	public void setCommId(Integer commId) {
		this.commId = commId;
	}

	public Integer getCommParent() {
		return commParent;
	}

	public void setCommParent(Integer commParent) {
		this.commParent = commParent;
	}

	public Integer getCommOrder() {
		return commOrder;
	}

	public void setCommOrder(Integer commOrder) {
		this.commOrder = commOrder;
	}

	public String getCommNo() {
		return commNo;
	}

	public void setCommNo(String commNo) {
		this.commNo = commNo;
	}

	public String getCommName() {
		return commName;
	}

	public void setCommName(String commName) {
		this.commName = commName;
	}

	public Integer getCommEndNode() {
		return commEndNode;
	}

	public void setCommEndNode(Integer commEndNode) {
		this.commEndNode = commEndNode;
	}
	
}
