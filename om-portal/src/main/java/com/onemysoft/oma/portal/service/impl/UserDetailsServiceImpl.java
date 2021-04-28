package com.onemysoft.oma.portal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.onemysoft.common.exception.BusinessException;
import com.onemysoft.oma.portal.entity.Permission;
import com.onemysoft.oma.portal.entity.User;
import com.onemysoft.oma.portal.model.LoginUser;
import com.onemysoft.oma.portal.model.enums.UserStatus;
import com.onemysoft.oma.portal.service.PermissionService;
import com.onemysoft.oma.portal.service.UserService;

/**
 * @author zongshuo
 * implement spring security UserDetailsService interface 
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userServcie;
    
    @Autowired
    private PermissionService permissionServcie;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
        User user = userServcie.findByUsername(username);
        
        if (user==null){
        	
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
            
        }else if (UserStatus.DELETED.getCode().equals(user.getDel())){
        	
            log.info("登录用户：{} 已被删除.", username);
            throw new BusinessException("对不起，您的账号：" + username + " 已被删除");
            
        }else if (UserStatus.DISABLE.getCode().equals(user.getStatus())){
        	
            log.info("登录用户：{} 已被停用.", username);
            throw new BusinessException("对不起，您的账号：" + username + " 已停用");
        }
        
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<Permission> list = permissionServcie.findPermissionsByUsername(username);

        for (Permission authority : list){
            if (!("").equals(authority.getUrl()) & authority.getUrl() !=null){
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getUrl());
                grantedAuthorities.add(grantedAuthority);
            }
        }
        
        return new LoginUser(user,grantedAuthorities);
    }

}
