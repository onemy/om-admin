package com.onemysoft.oma.portal.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.onemysoft.oma.portal.entity.Menu;
import com.onemysoft.oma.portal.entity.Permission;
import com.onemysoft.oma.portal.repository.dao.ResourceDao;

@Repository
public class ResourceRepositoryImpl implements ResourceDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Menu> findMenusByUserAndMoudule(String username,String module) {
		
		String userId="select u.id from User u where u.username= :username";
		
		String userRolesIds="select r.id from Role r join r.users u where u.username = :username";
		
		String menuIds="select DISTINCT acl.resource.id from ACL acl where (acl.resource).class='M' "+
				"and (acl.principal.id=("+userId+") or acl.principal.id in ("+userRolesIds+"))";
		
		String HQL="from Menu m where m.module.name=:moudule and m.id in ("+menuIds+") ";

		
		Query query=em.createQuery(HQL);
		
		List list=query.setParameter("username", username).setParameter("moudule", module).getResultList();
		
		
		return list;
	}

	@Override
	public List<Permission> findPermissionsByUser(String username) {
		
		String userId="select u.id from User u where u.username= :username";
		
		String userRolesIds="select r.id from Role r join r.users u where u.username = :username";
		
		String permissionIds="select DISTINCT acl.resource.id from ACL acl where (acl.resource).class='P' "+
				"and (acl.principal.id=("+userId+") or acl.principal.id in ("+userRolesIds+"))";
		
		String HQL="from Permission p where p.id in ("+permissionIds+") ";
		
		Query query=em.createQuery(HQL);
		
		List list=query.setParameter("username", username).getResultList();
		
		
		return list;
	}

}
