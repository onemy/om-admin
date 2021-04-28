package com.onemysoft.oma.portal.repository;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.onemysoft.oma.portal.entity.Permission;

/**
 * @author zongshuo
 * 
 */
public interface PermissionRepository extends JpaRepository<Permission, String> {
	
    @EntityGraph(type = EntityGraphType.FETCH,attributePaths = {"menu"})
    Page<Permission> findAll(Example example,Pageable pageable);
    
    @EntityGraph(type = EntityGraphType.FETCH,attributePaths = {"menu"})
    List<Permission> findAll();
}
