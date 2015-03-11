package com.honglicheng.dev.estate.model;
/**
 * 行业类型信息表 
 */
public class IndustryTypeInfo {
	
	//行业类型Id
	private Integer id;
	
	//行业类型名字
	private String name;
	
	//行业类型对应颜色
	private String color;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
}
