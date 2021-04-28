package com.onemysoft.oma.portal.model;

/**
 * 
 * @author onemysoft
 *
 */
public class MenuMeta {
	
	private String title;
	private String icon;
	private boolean noCache=false;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public boolean isNoCache() {
		return noCache;
	}
	public void setNoCache(boolean noCache) {
		this.noCache = noCache;
	}
	
	
}
