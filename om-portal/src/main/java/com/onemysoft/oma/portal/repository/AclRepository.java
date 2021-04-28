package com.onemysoft.oma.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.onemysoft.oma.portal.entity.ACL;
import com.onemysoft.oma.portal.entity.Menu;
import com.onemysoft.oma.portal.entity.Permission;
import com.onemysoft.oma.portal.entity.Principal;

public interface AclRepository extends JpaRepository<ACL, String>  {
	
	@Query("from Menu m where m.id in (select a.resource.id from ACL a where a.principal = :principal and (a.resource).class='M')")
	List<Menu> findMenuByPrincipal(Principal principal);
	
	@Query("from Permission p where p.id in (select a.resource.id from ACL a where a.principal = :principal and (a.resource).class='P')")
	List<Permission> findPermissionByPrincipal(Principal principal);
	
	@Query("select a.resource.id from ACL a where a.principal = :principal")
	List<String> findACLsByPrincipal(Principal principal);
	
    
    @Modifying
    @Query("delete from ACL a where a.principal = :principal")
    void deleteACLs(Principal principal);
}
