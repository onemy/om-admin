package com.onemysoft.oma.portal.model;

import com.onemysoft.common.entity.UUIDEntity;

public class PermissionTable extends UUIDEntity {
	
	
	private String code;
	
	private String name;
	
    private String status;
    
    

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

    
}
