package com.onemysoft.oma.portal.service;


import java.util.List;

import org.springframework.data.domain.Page;

import com.onemysoft.oma.portal.entity.Permission;
import com.onemysoft.oma.portal.entity.Role;


/**
 * @author onemysoft
 * 
 */
public interface PermissionService {

	/**
	 * save Permission
	 * @param id
	 * @return
	 */
	public int savePermission(String id);

	/**
	 * update Permission
	 * @param id
	 * @return
	 */
	public int updatePermission(String id);

	/**
	 * delete Permission
	 * @param id
	 * @return
	 */
	public int deletePermission(String id);


	public List<Permission> findPermissionsByUsername(String username);
	
	public List<Permission> findPermissionsByRole(Role role);
	
	public List<Permission> findAll();
	
	
	public Page<Permission> findAll(Permission permission);
	
}
