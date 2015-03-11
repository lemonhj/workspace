package com.honglicheng.dev.estate.model;

/**
 * TCorporation entity. @author MyEclipse Persistence Tools
 */
public class TCorporation  implements java.io.Serializable {
	// Fields

	private Integer corpId;
	private String corpName;
	private String corpAlias;
	private String corpFullname;
	private Integer corpIndustry;
	private Integer corpSizes;
	private String corpcontacts;
	private String corpContactor;
	private String corpTel;
	private String corpState;
	private Integer corpBuilding;

	// Constructors

	/** default constructor */
	public TCorporation() {
	}

	/** minimal constructor */
	public TCorporation(Integer corpId, String corpName, Integer corpIndustry) {
		super();
	}

	/** full constructor */
	public TCorporation(Integer corpId, String corpName, String corpAlias,
			String corpFullname, Integer corpIndustry, Integer corpSizes,
			String corpContactor, String corpTel) {
		this.corpId = corpId;
		this.corpName = corpName;
		this.corpAlias = corpAlias;
		this.corpFullname = corpFullname;
		this.corpIndustry = corpIndustry;
		this.corpSizes = corpSizes;
		this.corpContactor = corpContactor;
		this.corpTel = corpTel;
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

	public String getCorpAlias() {
		return corpAlias;
	}

	public void setCorpAlias(String corpAlias) {
		this.corpAlias = corpAlias;
	}

	public String getCorpFullname() {
		return corpFullname;
	}

	public void setCorpFullname(String corpFullname) {
		this.corpFullname = corpFullname;
	}

	public Integer getCorpIndustry() {
		return corpIndustry;
	}

	public void setCorpIndustry(Integer corpIndustry) {
		this.corpIndustry = corpIndustry;
	}

	public Integer getCorpSizes() {
		return corpSizes;
	}

	public void setCorpSizes(Integer corpSizes) {
		this.corpSizes = corpSizes;
	}

	public String getCorpContactor() {
		return corpContactor;
	}

	public void setCorpContactor(String corpContactor) {
		this.corpContactor = corpContactor;
	}

	public String getCorpTel() {
		return corpTel;
	}

	public void setCorpTel(String corpTel) {
		this.corpTel = corpTel;
	}

	public String getCorpcontacts() {
		return corpcontacts;
	}

	public void setCorpcontacts(String corpcontacts) {
		this.corpcontacts = corpcontacts;
	}

	public String getCorpState() {
		return corpState;
	}

	public void setCorpState(String corpState) {
		this.corpState = corpState;
	}

	public Integer getCorpBuilding() {
		return corpBuilding;
	}

	public void setCorpBuilding(Integer corpBuilding) {
		this.corpBuilding = corpBuilding;
	}
	

}
