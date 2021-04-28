package com.onemysoft.oma.portal.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.onemysoft.common.entity.UUIDEntity;

@MappedSuperclass
public abstract class Resource extends UUIDEntity {
	
	@Column(unique = true, nullable = false)
	private String code;
	
	@Column(nullable = false)
	private String name;
	
	private String url;
	
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
