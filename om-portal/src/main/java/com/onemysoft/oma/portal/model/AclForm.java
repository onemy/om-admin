package com.onemysoft.oma.portal.model;

import java.util.List;

import com.onemysoft.oma.portal.entity.Menu;
import com.onemysoft.oma.portal.entity.Permission;
import com.onemysoft.oma.portal.entity.Role;
import com.onemysoft.oma.portal.entity.User;

public class AclForm {
	private User user;
	private Role role;
	
	private List<Menu> menus;
	private List<Permission> permissions;
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public List<Menu> getMenus() {
		return menus;
	}
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	public List<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}


	
	
}
