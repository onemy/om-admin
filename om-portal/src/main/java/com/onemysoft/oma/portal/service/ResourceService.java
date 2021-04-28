package com.onemysoft.oma.portal.service;


import java.util.List;

import com.onemysoft.oma.portal.entity.Menu;
import com.onemysoft.oma.portal.entity.Permission;
import com.onemysoft.oma.portal.entity.User;
import com.onemysoft.oma.portal.model.MenuTree;

/**
 * @author onemysoft
 * 
 */
public interface ResourceService {


	public List<Menu> findMenusByUserAndMoudule(String username,String moudule);
	
	public List<Permission> findPermissionsByUser(String username);
}
