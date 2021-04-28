package com.onemysoft.oma.portal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.onemysoft.oma.portal.entity.Role;
import com.onemysoft.oma.portal.model.RoleForm;
import com.onemysoft.oma.portal.model.RoleTable;
import com.onemysoft.oma.portal.service.RoleService;

/**
 * @author onemysoft
 * 
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;


    @PostMapping("/save")
    public Result saveRole(@Validated @RequestBody Role role){
    	
    	if (OMAssert.isNotNull(role.getId())) {
    		roleService.updateRole(role);
    	}else {
        	if(roleService.checkRoleCodeExist(role.getRoleCode())) {
        		Result.error().message("该帐号己存在！");
        	}
        	roleService.createRole(role);
    	}

        return Result.ok();
    }
    

    
    @GetMapping("/getInfo")
    public Result getRoleByName(){
    	
    	Role role=roleService.findByRolename("");
    	Map<String,Object> map=new HashMap<String,Object>();

    	
        return Result.ok().data(map);
    }
    
    @PostMapping("/getRole")
    public Result getRoleById(String id){
    	RoleForm roleForm=new RoleForm();
    	Role role=roleService.findById(id);
    	BeanUtils.copyProperties(role,roleForm);
    	
        return Result.ok().data(roleForm);
    }
    
    @PostMapping("/list")
    @JsonDataRequestBody(start="start", rows="length")
//    @PreAuthorize("hasAnyAuthority('role/list')")
    public Result getRoleList(@ModelAttribute Role role){
    	
    	Page<Role> pageRole=roleService.findPageRoles(role);

    	List<RoleTable> roleList=new ArrayList<RoleTable>();
    	
    	List<Role> roles=pageRole.getContent();
    	for(Role r : roles) {
    		RoleTable roleTable=new RoleTable();
    		
    		roleTable.setId(r.getId());
    		roleTable.setRoleCode(r.getRoleCode());
    		roleTable.setRoleName(r.getRoleName());
    		roleTable.setStatus(r.getStatus());
    		roleTable.setOrderNo(r.getOrderNo());
    		roleList.add(roleTable);
    	}
    	
    	DataTransferObject<List<RoleTable>> dto=new DataTransferObject<List<RoleTable>>();
    	dto.setDatas(roleList);
    	dto.setTotalRecordNums(pageRole.getTotalElements());
    	dto.setDraw(SystemContext.getRequestTransferData().getDraw());
    	
        return Result.ok().data(dto);
    }
    
	/**
	 * 批量删除对象
	 * 
	 * @param id
	 * @return
	 */
    @PostMapping(value = "/batchDelete")
	public Result batchDelete(@RequestParam(value="ids[]") String[] ids) {
    	this.roleService.deleteRoles(ids);
    	
		return Result.ok();
	}
    
	/**
	 * 删除对象
	 * 
	 * @param id
	 * @return
	 */
    @PostMapping(value = "/delete")
	public Result delete(String id) {
    	this.roleService.deleteRole(id);
		return Result.ok();
	}
    
    /**
     * 校验角色编码
     */
    @PostMapping("/checkRoleCodeExsit")
    @ResponseBody
    public Result checkUsernameExsit(String roleCode) {
    	if(roleService.checkRoleCodeExist(roleCode)) {
    		return Result.error();
    	}else {
    		return Result.ok();
    	}
    }
    
    @PostMapping("/disable")
    public Result disableRole(@Validated @RequestBody Role role){
    	
    	if (OMAssert.isNotNull(role.getId())) {
    		roleService.updateStatus(role);
    	}

        return Result.ok();
    }
}
