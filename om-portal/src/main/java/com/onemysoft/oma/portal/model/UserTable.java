package com.onemysoft.oma.portal.model;

import com.onemysoft.common.entity.UUIDEntity;

public class UserTable extends UUIDEntity {
	
	
	private String nickname;
	
	private String username;
	
    private String status;

    

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}




    
    
}
