package com.onemysoft.oma.portal.model;

import com.onemysoft.common.entity.UUIDEntity;
import com.onemysoft.oma.portal.entity.Group;

public class MenuForm extends UUIDEntity {
	
	private String icon;
	private String url;
	private String path;
	private String component;
	
	private String code;
	
	private String name;
	
    private String status;
    
    private MenuForm parent;
    
    

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public MenuForm getParent() {
		return parent;
	}

	public void setParent(MenuForm parent) {
		this.parent = parent;
	}
    
    


    
    
}
