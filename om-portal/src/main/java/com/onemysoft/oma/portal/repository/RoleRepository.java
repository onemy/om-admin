package com.onemysoft.oma.portal.repository;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.onemysoft.oma.portal.entity.Role;
import com.onemysoft.oma.portal.entity.User;

/**
 * @author zongshuo
 * 
 */
public interface RoleRepository extends JpaRepository<Role, String> {
	
    Role findByRoleName(String rolename);
    Role findByRoleCode(String roleCode);
    
    @EntityGraph(type = EntityGraphType.FETCH,attributePaths = {"users"})
    Page<Role> findAll(Example example,Pageable pageable);
    
    @EntityGraph(type = EntityGraphType.FETCH,attributePaths = {"users"})
    Page<Role> findByRoleNameLike(String rolename,Pageable pageable);
    

    @Modifying
    @Query("delete from Role where id in ?1")
    void deleteByIdIn(List<String> ids);
    

    @Modifying
    @Query("update Role r set r.status=?2  where id=?1")
    void upateStatus(String id,String status);
}
