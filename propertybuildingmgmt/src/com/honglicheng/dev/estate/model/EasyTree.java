package com.honglicheng.dev.estate.model;
import java.util.List;

public class EasyTree {
	
	private Integer id;
	private String text;
	private Integer parentid;
	private String commno;
	private String iconCls;
	//判断节点是否是楼栋
	private boolean isBuild;
	//判断区类型
	private Integer endNode;
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	private List<EasyTree> children;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public Integer getParentid() {
		return parentid;
	}
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	public List<EasyTree> getChildren() {
		return children;
	}
	public void setChildren(List<EasyTree> children) {
		this.children = children;
	}
	public String getCommno() {
		return commno;
	}
	public void setCommno(String commno) {
		this.commno = commno;
	}
	public boolean getBuild() {
		return isBuild;
	}
	public void setBuild(boolean isBuild) {
		this.isBuild = isBuild;
	}
	public Integer getEndNode() {
		return endNode;
	}
	public void setEndNode(Integer endNode) {
		this.endNode = endNode;
	}
}
