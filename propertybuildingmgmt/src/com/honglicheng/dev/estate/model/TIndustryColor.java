package com.honglicheng.dev.estate.model;

public class TIndustryColor {
	// Fields

	private Integer induId;
	private String induColor;

	// Constructors
	
	public TIndustryColor() {
	};
	
	public TIndustryColor(int induId, String induColor) {
		this.induId = induId;
		this.induColor = induColor;
	}

	public int getInduId() {
		return induId;
	}

	public void setInduId(int induId) {
		this.induId = induId;
	}

	public String getInduColor() {
		return induColor;
	}

	public void setInduColor(String induColor) {
		this.induColor = induColor;
	}
}
