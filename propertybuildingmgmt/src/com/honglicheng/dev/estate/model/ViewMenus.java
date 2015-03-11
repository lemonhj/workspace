package com.honglicheng.dev.estate.model;

import java.util.List;

/**
 * 
* @ClassName: ViewMenus 
* @Description: 用于生成菜单的模型
* @author 林波涛 
* @date 2014年12月30日 下午5:58:16 
*
 */
public class ViewMenus {
	private Integer menuid;
	private String icon;
	private String menuname;
	private Integer parentMId;
	private String url;
	private List<ViewMenus> menus;
	public Integer getMenuid() {
		return menuid;
	}
	public void setMenuid(Integer menuid) {
		this.menuid = menuid;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getMenuname() {
		return menuname;
	}
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	public Integer getParentMId() {
		return parentMId;
	}
	public void setParentMId(Integer parentMId) {
		this.parentMId = parentMId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<ViewMenus> getMenus() {
		return menus;
	}
	public void setMenus(List<ViewMenus> menus) {
		this.menus = menus;
	}
	
	
}
