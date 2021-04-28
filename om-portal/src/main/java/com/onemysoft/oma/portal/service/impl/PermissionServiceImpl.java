package com.onemysoft.oma.portal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.onemysoft.common.context.SystemContext;
import com.onemysoft.oma.portal.entity.Permission;
import com.onemysoft.oma.portal.entity.Role;
import com.onemysoft.oma.portal.repository.AclRepository;
import com.onemysoft.oma.portal.repository.PermissionRepository;
import com.onemysoft.oma.portal.repository.ResourceRepository;
import com.onemysoft.oma.portal.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private ResourceRepository resourceRepository;
    
    @Autowired
    private PermissionRepository permissionRepository;
    
    @Autowired
    private AclRepository aclRepository;
    
	@Override
	public int savePermission(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updatePermission(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deletePermission(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Permission> findPermissionsByUsername(String username) {

		return resourceRepository.findPermissionsByUser(username);
	}

	@Override
	public Page<Permission> findAll(Permission permission) {
		
		int start = SystemContext.getRequestTransferData() == null ? 0 : SystemContext.getRequestTransferData().getStart();
		int limit = SystemContext.getRequestTransferData() == null ? Integer.MAX_VALUE : SystemContext.getRequestTransferData().getRows();
		
    	Sort sort = Sort.by(Direction.ASC, "orderNo");
    	Pageable pageable = PageRequest.of((start/limit), limit, sort);
    	
    	Example<Permission> ex=Example.of(permission);
    	
    	Page<Permission> list=permissionRepository.findAll(ex,pageable);
    	
		return list;
	}

	@Override
	public List<Permission> findAll() {
		return permissionRepository.findAll();
	}

	@Override
	public List<Permission> findPermissionsByRole(Role role) {
		return aclRepository.findPermissionByPrincipal(role);
	}

}
