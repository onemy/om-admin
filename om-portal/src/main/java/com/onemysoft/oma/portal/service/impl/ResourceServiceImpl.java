package com.onemysoft.oma.portal.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onemysoft.oma.portal.entity.Menu;
import com.onemysoft.oma.portal.entity.Permission;
import com.onemysoft.oma.portal.mapper.ResourceMapper;
import com.onemysoft.oma.portal.service.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService {


    @Autowired
    private ResourceMapper resourceMapper;
	
	@Override
	public List<Menu> findMenusByUserAndMoudule(String username, String moudule) {
		
		return resourceMapper.findMenusByUserAndMoudule(username, moudule);
	}

	@Override
	public List<Permission> findPermissionsByUser(String username) {
		return resourceMapper.findPermissionsByUser(username);
	}
	
	

}
