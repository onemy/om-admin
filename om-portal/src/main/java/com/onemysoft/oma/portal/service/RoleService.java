package com.onemysoft.oma.portal.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.onemysoft.common.web.DataTransferObject;
import com.onemysoft.oma.portal.entity.Role;
import com.onemysoft.oma.portal.entity.User;
import com.onemysoft.oma.portal.model.RoleTable;

public interface RoleService{
	
	/**
	 * create role
	 */
	public Role createRole(Role role);
	
	/**
	 * update role
	 */
	public Role updateRole(Role role);
	
	/**
	 * 更新状态
	 * @param role
	 */
	public void updateStatus(Role role);
	/**
	 * 
	 * @param id
	 */
	public void deleteRole(String id);
	/**
	 * delete role
	 */
	public void deleteRoles(String[] ids);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Role findById(String id);
	
	/**
	 * 通过角色对象查询分页列表
	 * find role list by role(condition)
	 * @param role
	 * @return
	 */
	public Page<Role> findPageRoles(Role role);
	
	/**
	 * 
	 * @param rolename
	 * @return role
	 */
	public Role findByRolename(String rolename);
	
	/**
	 * 
	 * @param roleCode
	 * @return
	 */
	public boolean checkRoleCodeExist(String roleCode);

}
