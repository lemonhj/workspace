package com.honglicheng.dev.estate.model;

public class TCommunityComBo {
	private Integer commId;
	private String commName;
	
	public TCommunityComBo(){};
	public TCommunityComBo(Integer commId,String commName){
		this.commId = commId;
		this.commName = commName;
	}
	public Integer getCommId() {
		return commId;
	}
	public void setCommId(Integer commId) {
		this.commId = commId;
	}
	public String getCommName() {
		return commName;
	}
	public void setCommName(String commName) {
		this.commName = commName;
	}
	
}
