package com.onemysoft.oma.portal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.onemysoft.oma.portal.entity.Menu;
import com.onemysoft.oma.portal.entity.Permission;


/**
 * 菜单数据访问接口
 * @author onemysoft
 * 
 */
@Mapper
public interface ResourceMapper {
	
	/**
	 * 通过用户名称和模块名称查询他的所有菜单，包括用户及所属角色对应的菜单
	 * find user's menus by username and moudule.
	 * @param username 用户帐号
	 * @param moudule 模块名称
	 * @return 菜单列表
	 */
	List<Menu> findMenusByUserAndMoudule(String username,String moudule);
	
	/**
	 * 通过用户名查找所有的资源（菜单和功能）
	 * find all resource(menu and permission) by username.
	 * @param username
	 * @return
	 */
	@Select("select * from sys_permission")
	List<Permission> findPermissionsByUser(String username);
}
