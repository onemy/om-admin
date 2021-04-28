package com.onemysoft.oma.portal.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onemysoft.common.entity.UUIDEntity;



@Entity
@Table(name = "sys_role")
public class Role extends UUIDEntity implements Principal {
	
	@Transient
	public static final String SYSTEM_ROLE = "admin";
	
	private String roleCode;
	
	private String roleName;
	
    /** 状态（0正常 1停用） */
    private String status;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<>();

    

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
	
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
    
}
