package com.onemysoft.oma.portal.model;

public class MenuTree extends TreeModel<MenuTree> {


	private String icon;
	private String url;
	private String path;
	private String component;
	private Integer orderNo;
	
	private MenuMeta meta;

	

	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	

	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	
	
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public MenuMeta getMeta() {
		return meta;
	}
	public void setMeta(MenuMeta meta) {
		this.meta = meta;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}


}
