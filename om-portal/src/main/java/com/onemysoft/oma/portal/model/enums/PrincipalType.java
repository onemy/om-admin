package com.onemysoft.oma.portal.model.enums;

public enum PrincipalType {

	ROLE("ROLE"),
	USER("USER");
	
	private PrincipalType(String value) {
		this.value = value;
	}
	
	private String value;

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
