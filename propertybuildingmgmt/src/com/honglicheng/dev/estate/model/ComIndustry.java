package com.honglicheng.dev.estate.model;

/**
 * ComIndustry entity. @author MyEclipse Persistence Tools
 */
public class ComIndustry implements java.io.Serializable {
	// Fields
	//行业类型Id
	private int induId;
	//行业类型所属行业Id
	private int induParent;
	//行业类型名
	private String induName;
	//行业类型别名
	private String induAlias;
	//行业类型全称
	private String induFullname;
	// Constructors

	/** default constructor */
	public ComIndustry() {
	}

	/** minimal constructor */
	public ComIndustry(Integer induId, String induName) {
		super();
	}

	/** full constructor */
	public ComIndustry(Integer induId, Integer induParent, String induName,
			String induAlias, String induFullname) {
		this.induId = induId;
		this.induParent = induParent;
		this.induName = induName;
		this.induAlias = induAlias;
		this.induFullname = induFullname;
	}

	public int getInduId() {
		return induId;
	}

	public void setInduId(int induId) {
		this.induId = induId;
	}

	public int getInduParent() {
		return induParent;
	}

	public void setInduParent(int induParent) {
		this.induParent = induParent;
	}

	public String getInduName() {
		return induName;
	}

	public void setInduName(String induName) {
		this.induName = induName;
	}

	public String getInduAlias() {
		return induAlias;
	}

	public void setInduAlias(String induAlias) {
		this.induAlias = induAlias;
	}

	public String getInduFullname() {
		return induFullname;
	}

	public void setInduFullname(String induFullname) {
		this.induFullname = induFullname;
	}

}
