package com.onemysoft.oma.portal.service;


import java.util.List;

import com.onemysoft.oma.portal.entity.Group;
import com.onemysoft.oma.portal.model.GroupTree;

/**
 * @author onemysoft
 * 
 */
public interface GroupService {

	/**
	 * create group
	 */
	public Group createGroup(Group group);
	
	/**
	 * update group
	 */
	public Group updateGroup(Group group);
	
	/**
	 * 更新状态
	 * @param group
	 */
	public void updateStatus(Group group);
	/**
	 * 
	 * @param id
	 */
	public void deleteGroup(String id);
	/**
	 * delete group
	 */
	public void deleteGroups(String[] ids);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Group findById(String id);
	

	
	/**
	 * 
	 * @param groupCode
	 * @return
	 */
	public boolean checkGroupCodeExist(String groupCode);

	/**
	 * find groups by group
	 * @param group
	 * @return
	 */
	public List<Group> findGroupList(Group group);
	
	/**
	 * 
	 * @param group
	 * @return
	 */
	public List<GroupTree> findGroupTree(Group group);
}
