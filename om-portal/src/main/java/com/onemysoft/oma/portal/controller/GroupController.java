package com.onemysoft.oma.portal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.onemysoft.common.springmvc.intercept.annotation.JsonDataRequestBody;
import com.onemysoft.common.utils.BeanUtils;
import com.onemysoft.common.utils.OMAssert;
import com.onemysoft.common.web.Result;
import com.onemysoft.oma.portal.entity.Group;
import com.onemysoft.oma.portal.entity.Role;
import com.onemysoft.oma.portal.model.GroupForm;
import com.onemysoft.oma.portal.service.GroupService;

/**
 * @author onemysoft
 * 
 */
@RestController
@RequestMapping("/api/group")
public class GroupController {

    @Autowired
    private GroupService groupService;
    
    
    
    @PostMapping("/list")
    @JsonDataRequestBody(start="start", rows="length")
//    @PreAuthorize("hasAnyAuthority('group/list')")
    public Result getGroupList(@ModelAttribute Group group){
    	
    	List<Group> list=groupService.findGroupList(group);
    	List<Map<String, String>> tree = new ArrayList<Map<String, String>>();
    	for(Group g:list) {
    		Map<String, String> m = new HashMap<String, String>();
			m.put("id", g.getId());
			m.put("pid", g.getParent() == null ? "-1" : g.getParent().getId());
			m.put("name", g.getGroupName());
    		
			tree.add(m);
    	}
    	
        return Result.ok().data(tree);
    }

    @PostMapping("/tree")
//    @PreAuthorize("hasAnyAuthority('group/list')")
    public Result getGroupTree(@ModelAttribute Group group){
    	
        return Result.ok().data(groupService.findGroupTree(group));
    }
    
    @PostMapping("/save")
    public Result save(Group group){
    	
    	if (OMAssert.isNotNull(group.getId())) {
    		groupService.updateGroup(group);
    	}else {
        	if(groupService.checkGroupCodeExist(group.getGroupCode())) {
        		Result.error().message("该帐号己存在！");
        	}
        	groupService.createGroup(group);
    	}

        return Result.ok();
    }
    
    @PostMapping("/getGroup")
    public Result getGroupById(String id){
    	GroupForm groupForm=new GroupForm();
    	Group group=groupService.findById(id);
    	
//    	BeanUtils.copyProperties(group,groupForm);
    	
    	groupForm.setId(group.getId());
    	groupForm.setDescription(group.getDescription());
    	groupForm.setOrderNo(group.getOrderNo());
    	groupForm.setGroupCode(group.getGroupCode());
    	groupForm.setGroupName(group.getGroupName());
    	groupForm.setStatus(group.getStatus());
    	
    	GroupForm parentGroup=new GroupForm();
    	if(!ObjectUtils.isEmpty(group.getParent())) {
	    	parentGroup.setId(group.getParent().getId());
	    	parentGroup.setGroupCode(group.getParent().getGroupCode());
	    	parentGroup.setGroupName(group.getParent().getGroupName());
	    	parentGroup.setStatus(group.getParent().getStatus());
	    	groupForm.setParent(parentGroup);
    	}
    	
        return Result.ok().data(groupForm);
    }
    
    
	/**
	 * 批量删除对象
	 * 
	 * @param id
	 * @return
	 */
    @PostMapping(value = "/batchDelete")
	public Result batchDelete(@RequestParam(value="ids[]") String[] ids) {
    	this.groupService.deleteGroups(ids);
    	
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
    	this.groupService.deleteGroup(id);
		return Result.ok();
	}
    
    /**
     * 校验角色编码
     */
    @PostMapping("/checkGroupCodeExsit")
    @ResponseBody
    public Result checkUsernameExsit(String groupCode) {
    	if(groupService.checkGroupCodeExist(groupCode)) {
    		return Result.error();
    	}else {
    		return Result.ok();
    	}
    }

    @PostMapping("/disable")
    public Result disableRole(@Validated @RequestBody Group group){
    	
    	if (OMAssert.isNotNull(group.getId())) {
    		groupService.updateStatus(group);
    	}

        return Result.ok();
    }
}
