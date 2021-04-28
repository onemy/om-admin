package com.onemysoft.oma.portal.service;

import com.onemysoft.oma.portal.entity.ACL;
import com.onemysoft.oma.portal.entity.Principal;

public interface AclService{
	
	/**
	 * 创建ACL
	 * create acl
	 * @param acl
	 * @return
	 */
	public ACL createACL(ACL acl);
	
	/**
	 * 更新ACL
	 * update acl
	 * @param acl
	 * @return
	 */
	public ACL updateACL(ACL acl);
	
	/**
	 * 删除ACL
	 * delete acl
	 * @param id
	 */
	public void deleteACL(String id);
	
	/**
	 * 批量删除ACL
	 * batch delete acl
	 * @param ids
	 */
	public void batchDeleteACLs(String[] ids);

	
	/**
	 * 删除ACL通过主体
	 * @param id
	 * @return
	 */
	public void deleteACLs(Principal principal);
}
