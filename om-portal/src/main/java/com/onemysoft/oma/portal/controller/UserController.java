package com.onemysoft.oma.portal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.onemysoft.common.context.SystemContext;
import com.onemysoft.common.springmvc.intercept.annotation.JsonDataRequestBody;
import com.onemysoft.common.utils.BeanUtils;
import com.onemysoft.common.utils.OMAssert;
import com.onemysoft.common.web.DataTransferObject;
import com.onemysoft.common.web.Result;
import com.onemysoft.oma.portal.entity.User;
import com.onemysoft.oma.portal.model.UserForm;
import com.onemysoft.oma.portal.model.UserTable;
import com.onemysoft.oma.portal.service.UserService;

/**
 * @author onemysoft
 * 
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;



    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('user/save') or authentication.name=='admin'")
    public Result saveUser(@Validated @RequestBody User user){
    	
    	if (OMAssert.isNotNull(user.getId())) {
    		userService.updateUser(user);
    	}else {
        	if(userService.checkUsernameExist(user.getUsername())) {
        		Result.error().message("该帐号己存在！");
        	}
        	userService.createUser(user);
    	}

        return Result.ok();
    }

    @PostMapping("/disable")
    @PreAuthorize("hasAnyAuthority('user/save') or authentication.name=='admin'")
    public Result disableUser(@Validated @RequestBody User user){
    	
    	if (OMAssert.isNotNull(user.getId())) {
//    		//忽略更新关联集合
//    		user.setRoles(null);
//    		user.setGroups(null);
//    		userService.updateUser(user);
    		userService.updateStatus(user);
    	}

        return Result.ok();
    }

    
    /**
     * 重置密码
     */
    @PostMapping("/resetPwd")
    @PreAuthorize("authentication.name=='admin'")
    @ResponseBody
    public Result resetPwd(@Validated @RequestBody User user) {
    	if (OMAssert.isNotNull(user.getId())) {
    		//忽略更新关联集合
//    		user.setRoles(null);
//    		user.setGroups(null);
//    		userService.updateUser(user);
    		userService.updatePwd(user);
    		
    	}

        return Result.ok();
    }
    
    @GetMapping("/getInfo")
    public Result getUserByName(){
    	
    	User user=userService.findByCurrentUser();
    	Map<String,Object> map=new HashMap<String,Object>();
    	map.put("user", user);
    	List<String> perm=new ArrayList<String>();
    	perm.add("*:*:*");
    	map.put("permissions", perm);
    	
        return Result.ok().data(map);
    }
    
    @PostMapping("/getUser")
    public Result getUserById(String id){
    	UserForm userForm=new UserForm();
    	User user=userService.findById(id);
    	BeanUtils.copyProperties(user,userForm);
    	
        return Result.ok().data(userForm);
    }
    
    @PostMapping("/getUserByUsername")
    public Result getUserByUsername(String username){
    	UserForm userForm=new UserForm();
    	User user=userService.findByUsername(username);
    	BeanUtils.copyProperties(user,userForm);
    	
        return Result.ok().data(userForm);
    }
    
    @PostMapping("/list")
    @JsonDataRequestBody(start="start", rows="length")
    @PreAuthorize("hasAnyAuthority('user/list') or authentication.name=='admin'")
    public Result getUserList(@ModelAttribute User user){
    	
    	if(!StringUtils.hasText(user.getGroup().getId())){
    		user.setGroup(null);
    	}
    	
    	Page<User> pageUser=userService.findPageUsers(user);

    	List<UserTable> userList=new ArrayList<UserTable>();
    	
    	List<User> users=pageUser.getContent();
    	for(User u : users) {
    		UserTable userTable=new UserTable();
    		
    		userTable.setId(u.getId());
    		userTable.setNickname(u.getNickname());
    		userTable.setUsername(u.getUsername());
    		userTable.setStatus(u.getStatus());
    		userTable.setOrderNo(u.getOrderNo());
    		userList.add(userTable);
    	}
    	
    	DataTransferObject<List<UserTable>> dto=new DataTransferObject<List<UserTable>>();
    	dto.setDatas(userList);
    	dto.setTotalRecordNums(pageUser.getTotalElements());
    	dto.setDraw(SystemContext.getRequestTransferData().getDraw());
    	
        return Result.ok().data(dto);
    }
    
	/**
	 * 批量删除对象
	 * 
	 * @param ids
	 * @return
	 */
    @PostMapping(value = "/batchDelete")
    @PreAuthorize("hasAnyAuthority('user/delete') or authentication.name=='admin'")
	public Result batchDelete(@RequestParam(value="ids[]") String[] ids) {
    	this.userService.batchDeleteUsers(ids);
    	
		return Result.ok();
	}
    
	/**
	 * 删除对象
	 * 
	 * @param id
	 * @return
	 */
    @PostMapping(value = "/delete")
    @PreAuthorize("hasAnyAuthority('user/delete') or authentication.name=='admin'")
	public Result delete(String id) {
    	this.userService.deleteUser(id);
		return Result.ok();
	}
    
    /**
     * 校验用户名
     */
    @PostMapping("/checkUsernameExsit")
    @ResponseBody
    public Result checkUsernameExsit(String username) {
    	if(userService.checkUsernameExist(username)) {
    		return Result.error();
    	}else {
    		return Result.ok();
    	}
    }


}
