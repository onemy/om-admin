package com.onemysoft.oma.portal.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onemysoft.oma.portal.entity.Permission;
import com.onemysoft.oma.portal.entity.User;

/**
 * @author zongshuo
 * 
 */
public class LoginUser implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
    private String username;
    
    private String password;
    private List<GrantedAuthority> authorities;
    private String token;

    public LoginUser() {
    }

    // 写一个能直接使用user创建jwtUser的构造器
    public LoginUser(User user,List<GrantedAuthority> permissions) {
        id = user.getId();
        username = user.getUsername();
        password = user.getPassword();
        this.authorities=permissions;
        
//        for(Permission perm:permissions) {
//        	if(StringUtils.hasText(perm.getUrl())) {
//        		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(perm.getUrl());
//        		//authorities.add(grantedAuthority);
//        	}
//        }
        
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
    
    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "JwtUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }

}
