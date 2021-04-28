package com.onemysoft.oma.portal.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

import com.onemysoft.common.entity.UUIDEntity;


/**
 * @author onemysoft
 * 
 */
@Entity
@Table(name = "sys_acl")
public class ACL extends UUIDEntity {


    @Any(metaColumn = @Column(name = "resource_type"))
    @AnyMetaDef(idType = "string", metaType = "string", 
            metaValues = { 
             @MetaValue(targetEntity = Menu.class, value = "M"),
             @MetaValue(targetEntity = Permission.class, value = "P"),
    })
    @JoinColumn(name="resource_id")
    private Resource resource;
	
    
    @Any(metaColumn = @Column(name = "principal_type"))
    @AnyMetaDef(idType = "string", metaType = "string", 
            metaValues = { 
             @MetaValue(targetEntity = User.class, value = "U"),
             @MetaValue(targetEntity = Role.class, value = "R"),
    })
    @JoinColumn( name = "principal_id" )
    private Principal principal;



	public Resource getResource() {
		return resource;
	}



	public void setResource(Resource resource) {
		this.resource = resource;
	}



	public Principal getPrincipal() {
		return principal;
	}



	public void setPrincipal(Principal principal) {
		this.principal = principal;
	}
    
    
    
}
