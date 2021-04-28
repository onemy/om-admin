package com.onemysoft.oma.portal.service;


import java.util.List;

import com.onemysoft.oma.portal.entity.Menu;
import com.onemysoft.oma.portal.entity.Menu;
import com.onemysoft.oma.portal.entity.Role;
import com.onemysoft.oma.portal.model.MenuTree;

/**
 * @author onemysoft
 * 
 */
public interface MenuService {

	/**
	 * create menu
	 */
	public Menu createMenu(Menu menu);
	
	/**
	 * update menu
	 */
	public Menu updateMenu(Menu menu);
	
	/**
	 * 更新状态
	 * @param menu
	 */
	public void updateStatus(Menu menu);
	/**
	 * 
	 * @param id
	 */
	public void deleteMenu(String id);
	/**
	 * delete menu
	 */
	public void deleteMenus(String[] ids);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Menu findById(String id);
	

	
	/**
	 * 
	 * @param menuCode
	 * @return
	 */
	public boolean checkMenuCodeExist(String menuCode);

	
	/**
	 * find menu tree by user
	 * @param user
	 * @return
	 */
	public List<Menu> findACLByUser(String username);
	
	
	public List<Menu> findACLByRole(Role role);
	/**
	 * find menus by menu
	 * @param menu
	 * @return
	 */
	public List<Menu> findMenuList(Menu menu);
}
