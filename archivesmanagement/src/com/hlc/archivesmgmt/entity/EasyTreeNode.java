package com.hlc.archivesmgmt.entity;
import java.util.List;

public class EasyTreeNode {

	private Integer id;
	
	private String text;
	
	private boolean isParents;
	
	private List<EasyTreeNode> children;

	public List<EasyTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<EasyTreeNode> children) {
		this.children = children;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isParents() {
		return isParents;
	}

	public void setParents(boolean isParents) {
		this.isParents = isParents;
	}
	
	
}
