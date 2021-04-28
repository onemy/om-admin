package com.onemysoft.oma.portal.model.enums;

public enum ResourceType {
	
	MENU("MENU"),
	FUNCTION("FUNCTION");
	
	private ResourceType(String value) {
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
