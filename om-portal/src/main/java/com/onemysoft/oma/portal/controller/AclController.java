package com.onemysoft.oma.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onemysoft.common.web.Result;
import com.onemysoft.oma.portal.entity.ACL;
import com.onemysoft.oma.portal.entity.Menu;
import com.onemysoft.oma.portal.entity.Permission;
import com.onemysoft.oma.portal.entity.Role;
import com.onemysoft.oma.portal.entity.User;
import com.onemysoft.oma.portal.model.AclForm;
import com.onemysoft.oma.portal.service.AclService;

/**
 * @author onemysoft
 * 
 */
@RestController
@RequestMapping("/api/acl")
public class AclController {

    @Autowired
    private AclService aclService;
    
    
    @PostMapping("/save")
    public Result saveUserAcl(@Validated @RequestBody AclForm aclForm){
    	
    	User user=aclForm.getUser();
    	List<Menu> menus=aclForm.getMenus();
    	List<Permission> permissions=aclForm.getPermissions();
    	
    	aclService.deleteACLs(user);
    	
    	for(Menu m : menus) {
        	ACL acl=new ACL();
        	acl.setOrderNo(0);
        	acl.setPrincipal(user);
        	acl.setResource(m);
        	aclService.createACL(acl);
    	}
    	
    	for(Permission p : permissions) {
        	ACL acl=new ACL();
        	acl.setOrderNo(0);
        	acl.setPrincipal(user);
        	acl.setResource(p);
        	aclService.createACL(acl);
    	}


        return Result.ok();
    }

    @PostMapping("/saveRole")
    public Result saveRoleAcl(@Validated @RequestBody AclForm aclForm){
    	
    	Role role=aclForm.getRole();
    	List<Menu> menus=aclForm.getMenus();
    	List<Permission> permissions=aclForm.getPermissions();
    	
    	aclService.deleteACLs(role);
    	
    	for(Menu m : menus) {
        	ACL acl=new ACL();
        	acl.setOrderNo(0);
        	acl.setPrincipal(role);
        	acl.setResource(m);
        	aclService.createACL(acl);
    	}
    	
    	for(Permission p : permissions) {
        	ACL acl=new ACL();
        	acl.setOrderNo(0);
        	acl.setPrincipal(role);
        	acl.setResource(p);
        	aclService.createACL(acl);
    	}


        return Result.ok();
    }
}
