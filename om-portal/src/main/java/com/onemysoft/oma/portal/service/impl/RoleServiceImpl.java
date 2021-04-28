package com.onemysoft.oma.portal.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.onemysoft.common.context.SystemContext;
import com.onemysoft.common.utils.BeanUtils;
import com.onemysoft.common.utils.OMAssert;
import com.onemysoft.oma.portal.entity.Role;
import com.onemysoft.oma.portal.exception.ResourceNotFoundException;
import com.onemysoft.oma.portal.repository.RoleRepository;
import com.onemysoft.oma.portal.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	

    @Autowired
    private RoleRepository roleRepository;
    
	@Override
	@Transactional
	public Role createRole(Role role) {
    	String currentUser=(String) SystemContext.getUserMap().get("username");
    	role.setCreator(currentUser);
    	role.setCreateDate(new Date());
    	role.setUpdateDate(new Date());
        
		return roleRepository.save(role);
	}



	@Override
	@Transactional
	public Role updateRole(Role role) {
        Optional < Role > roleDb = this.roleRepository.findById(role.getId());

        if (roleDb.isPresent()) {
        	Role roleUpdate = roleDb.get();

        	BeanUtils.copyProperties(role, roleUpdate);
        	String currentUser=(String) SystemContext.getUserMap().get("rolename");
            role.setUpdater(currentUser);
            role.setUpdateDate(new Date());
            
            roleRepository.save(roleUpdate);
            return roleUpdate;
        } else {
            throw new ResourceNotFoundException("记录没找到 id : " + role.getId());
        }
	}



	@Override
	@Transactional
	public void deleteRole(String id) {
        Optional < Role > roleDb = this.roleRepository.findById(id);

        if (roleDb.isPresent()) {
            this.roleRepository.delete(roleDb.get());
        } else {
            throw new ResourceNotFoundException("记录没找到 id : " + id);
        }
	}

	@Override
	@Transactional
	public void deleteRoles(String[] ids) {
		OMAssert.notNull(ids, "exception", "RoleId is null");
		
		List<String> list=new ArrayList<>();
		Collections.addAll(list,ids);
		
		roleRepository.deleteByIdIn(list);

	}



	@Override
	public Role findByRolename(String rolename) {
		
		return roleRepository.findByRoleName(rolename);
	}

	@Override
	public Page<Role> findPageRoles(Role role) {
		
		int start = SystemContext.getRequestTransferData() == null ? 0 : SystemContext.getRequestTransferData().getStart();
		int limit = SystemContext.getRequestTransferData() == null ? Integer.MAX_VALUE : SystemContext.getRequestTransferData().getRows();
		
    	Sort sort = Sort.by(Direction.ASC, "orderNo");
    	Pageable pageable = PageRequest.of((start/limit), limit, sort);
    	
    	
    	ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("roleName", ExampleMatcher.GenericPropertyMatchers.contains());//模糊查询匹配开头，即%{rolename}%

    	
    	Example<Role> ex=Example.of(role,matcher);

    	Page<Role> obj=roleRepository.findAll(ex,pageable);
    	
		return obj;
	}

	@Override
	public boolean checkRoleCodeExist(String roleCode) {
		Role role=roleRepository.findByRoleCode(roleCode);
		
		if(ObjectUtils.isEmpty(role)) {
			return false;
		}else {
			return true;
		}
	}



	@Override
	public void updateStatus(Role role) {
        Optional < Role > roleDb = this.roleRepository.findById(role.getId());
        if (roleDb.isPresent()) {
            this.roleRepository.upateStatus(role.getId(), role.getStatus());
        } else {
            throw new ResourceNotFoundException("记录没找到 id : " + role.getId());
        }
	}



	@Override
	public Role findById(String id) {
		Optional < Role > roleDb = this.roleRepository.findById(id);
		Role role=roleDb.get();
		return role;
	}









}
