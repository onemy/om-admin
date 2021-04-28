package com.onemysoft.oma.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.onemysoft.common.utils.OMAssert;
import com.onemysoft.common.web.Result;
import com.onemysoft.oma.portal.entity.User;
import com.onemysoft.oma.portal.service.UserService;

/**
 * @author onemysoft
 * 
 */
@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    @Autowired
    private UserService userService;



    @PostMapping("/update")
    public Result updateUser(@Validated @RequestBody User user){
    	
    	if (OMAssert.isNotNull(user.getId())) {
    		//忽略更新关联集合
    		user.setRoles(null);
    		//user.setGroups(null);
    		userService.updateUser(user);
    	}

        return Result.ok();
    }


    
    /**
     * 更新密码
     */
    @PostMapping("/updatePwd")
    @ResponseBody
    public Result updatePwd(String oldPassword, String newPassword) {
    	
    	User user=userService.findByCurrentUser();
    	
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    	
        if( passwordEncoder.matches(oldPassword, user.getPassword())) {
        	user.setPassword(newPassword);
    		userService.updatePwd(user);
    		return Result.ok();
        }else {
        	return Result.error().message("修改密码失败，旧密码错误！");
        }

    }
    
}
