package com.onemysoft.oma.portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onemysoft.common.context.SystemContext;
import com.onemysoft.common.springmvc.intercept.annotation.JsonDataRequestBody;
import com.onemysoft.common.web.DataTransferObject;
import com.onemysoft.common.web.Result;
import com.onemysoft.oma.portal.entity.Permission;
import com.onemysoft.oma.portal.model.PermissionTable;
import com.onemysoft.oma.portal.service.PermissionService;

/**
 * @author onemysoft
 * 
 */
@RestController
@RequestMapping("/api/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;
    
    
    @PostMapping("/list")
    @JsonDataRequestBody(start="start", rows="length")
    public Result getPermissionList(@ModelAttribute Permission permission){
    	
    	Page<Permission> list=permissionService.findAll(permission);
    	
    	List<PermissionTable> permissionList=new ArrayList<PermissionTable>();
    	
    	for(Permission p : list) {
    		PermissionTable permissionTable=new PermissionTable();
    		
    		permissionTable.setId(p.getId());
    		permissionTable.setCode(p.getCode());
    		permissionTable.setName(p.getName());
    		permissionTable.setOrderNo(p.getOrderNo());
    		permissionList.add(permissionTable);
    	}
    	
    	DataTransferObject<List<PermissionTable>> dto=new DataTransferObject<List<PermissionTable>>();
    	dto.setDatas(permissionList);
    	dto.setTotalRecordNums(list.getTotalElements());
    	dto.setDraw(SystemContext.getRequestTransferData().getDraw());
    	
        return Result.ok().data(dto);
    }

 
}
