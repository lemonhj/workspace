package com.honglicheng.dev.sys.model.security;

/**
 * 权限
 */
public class Right {
	private Integer id;
	private String rightName = "未命名";
	private String rightUrl ;
	private String rightDesc = "超链接";
	
	/* 权限码,解决url资源过多的情况 */
	private int rightPos ;
	
	/* 权限码,通过1依次向左移位产生 */
	private long rightCode ;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRightName() {
		return rightName;
	}
	public void setRightName(String rightName) {
		this.rightName = rightName;
	}
	public String getRightUrl() {
		return rightUrl;
	}
	public void setRightUrl(String rightUrl) {
		this.rightUrl = rightUrl;
	}
	public String getRightDesc() {
		return rightDesc;
	}
	public void setRightDesc(String rightDesc) {
		this.rightDesc = rightDesc;
	}
	public long getRightCode() {
		return rightCode;
	}
	public void setRightCode(long rightCode) {
		this.rightCode = rightCode;
	}
	public int getRightPos() {
		return rightPos;
	}
	public void setRightPos(int rightPos) {
		this.rightPos = rightPos;
	}
}

