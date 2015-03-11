package com.honglicheng.dev.estate.model;

public class countNode {
    //行业类型名	
    private String name;
    //行业类型户数
    private Integer countname;
    //行业类型面积
    private float countArea;
    //行业类型颜色
    private String countColor;
   
    private String ratio;
   
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCountname() {
		return countname;
	}
	public void setCountname(Integer countname) {
		this.countname = countname;
	}
	public float getCountArea() {
		return countArea;
	}
	public void setCountArea(float countArea) {
		this.countArea = countArea;
	}
	public String getRatio() {
		return ratio;
	}
	public void setRatio(String ratio) {
		this.ratio = ratio;
	}
	public String getCountColor() {
		return countColor;
	}
	public void setCountColor(String countColor) {
		this.countColor = countColor;
	}

}
