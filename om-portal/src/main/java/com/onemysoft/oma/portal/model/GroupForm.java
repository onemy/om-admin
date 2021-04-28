package com.onemysoft.oma.portal.model;

import com.onemysoft.common.entity.UUIDEntity;
import com.onemysoft.oma.portal.entity.Group;

public class GroupForm extends UUIDEntity {
	
	
	private String groupCode;
	
	private String groupName;
	
    private String status;
    
    private GroupForm parent;
    
    

	public GroupForm getParent() {
		return parent;
	}

	public void setParent(GroupForm parent) {
		this.parent = parent;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    
}
