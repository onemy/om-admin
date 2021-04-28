package com.onemysoft.oma.portal.service;

import org.springframework.data.domain.Page;
import com.onemysoft.oma.portal.entity.User;

public interface UserService{
	
	/**
	 * 创建用户
	 * create user
	 * @param user
	 * @return
	 */
	public User createUser(User user);
	/**
	 * 更新用户
	 * update user
	 * @param user
	 * @return
	 */
	public User updateUser(User user);
	
	/**
	 * 更新密码
	 * @param user
	 */
	public void updatePwd(User user);
	
	/**
	 * 更新状态
	 * @param user
	 */
	public void updateStatus(User user);
	
	/**
	 * 删除用户
	 * delete user
	 * @param id
	 */
	public void deleteUser(String id);
	
	/**
	 * 批量删除用户
	 * batch delete user
	 * @param ids
	 */
	public void batchDeleteUsers(String[] ids);
	/**
	 * 通过用户对象查询分页用户列表
	 * find user list by user(condition)
	 * @param user
	 * @return
	 */
	public Page<User> findPageUsers(User user);
	
	/**
	 * 用名称查询用户
	 * @param username
	 * @return user
	 */
	public User findByUsername(String username);
	
	/**
	 * 用ID查询用户
	 * @param id
	 * @return
	 */
	public User findById(String id);
	/**
	 * 查询当前用户
	 * find user by current context user
	 * @return
	 */
	public User findByCurrentUser();
	
    /**
     * 校验用户名称是否唯一
     * 
     * @param username 用户名称
     * @return 结果
     */
    public boolean checkUsernameExist(String username);
}
