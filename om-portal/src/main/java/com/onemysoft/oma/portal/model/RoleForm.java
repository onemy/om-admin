package com.onemysoft.oma.portal.model;

import com.onemysoft.common.entity.UUIDEntity;

public class RoleForm extends UUIDEntity {
	
	
	private String roleCode;
	
	private String roleName;
	
    private String status;
    
    
    
    

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    
}
