package com.onemysoft.oma.portal.service.impl;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onemysoft.common.context.SystemContext;
import com.onemysoft.oma.portal.entity.ACL;
import com.onemysoft.oma.portal.entity.Principal;
import com.onemysoft.oma.portal.entity.User;
import com.onemysoft.oma.portal.repository.AclRepository;
import com.onemysoft.oma.portal.service.AclService;

@Service
public class AclServiceImpl implements AclService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    private AclRepository aclRepository;

	@Override
	@Transactional
	public ACL createACL(ACL acl) {
		
    	String currentUser=(String) SystemContext.getUserMap().get("username");
    	acl.setCreator(currentUser);
    	acl.setCreateDate(new Date());
    	acl.setUpdateDate(new Date());
        
		return aclRepository.save(acl);
	}


	@Override
	@Transactional
	public ACL updateACL(ACL acl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void deleteACL(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public void batchDeleteACLs(String[] ids) {
		// TODO Auto-generated method stub
		
	}


	@Override
	@Transactional
	public void deleteACLs(Principal principal) {
		aclRepository.deleteACLs(principal);
	}




}
