package com.onemysoft.oma.portal.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.onemysoft.common.context.SystemContext;
import com.onemysoft.common.utils.BeanUtils;
import com.onemysoft.common.utils.OMAssert;
import com.onemysoft.oma.portal.entity.Group;
import com.onemysoft.oma.portal.exception.ResourceNotFoundException;
import com.onemysoft.oma.portal.model.GroupTree;
import com.onemysoft.oma.portal.repository.GroupRepository;
import com.onemysoft.oma.portal.service.GroupService;;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    


	@Override
	public List<Group> findGroupList(Group group) {

    	Example<Group> ex=Example.of(group);
    	
    	List<Group> list=groupRepository.findAll(ex);
    	

		return list;
	}

	@Override
	public List<GroupTree> findGroupTree(Group group) {
		
    	Example<Group> ex=Example.of(group);
    	
    	List<Group> list=groupRepository.findAll(ex);
    	
		// 递归树形结构
		List<GroupTree> gtList = new ArrayList<GroupTree>();
		Map<String, Object> groupList = new HashMap<String, Object>();
		for (Group d : list) {
			GroupTree gt = new GroupTree();

			gt.setId(d.getId());
			gt.setCode(d.getGroupCode());
			gt.setName(d.getGroupName());
			gt.setStatus(d.getStatus());
			gt.setOrderNo(d.getOrderNo());
			
			if (d.getParent() != null) {
				gt.setPid(d.getParent().getId());
				GroupTree parentV = new GroupTree();
				parentV.setId(d.getParent().getId());
				parentV.setCode(d.getParent().getGroupCode());
				parentV.setName(d.getParent().getGroupName());
				parentV.setStatus(d.getParent().getStatus());
				parentV.setOrderNo(d.getParent().getOrderNo());
				gt.setParent(parentV);
			}
			groupList.put(gt.getId(), gt);
			gtList.add(gt);
		}

		
		List<GroupTree> root = new ArrayList<GroupTree>();
		Set entrySet = groupList.entrySet();
		
		for (Iterator it = entrySet.iterator(); it.hasNext();) {
			@SuppressWarnings("unchecked")
			GroupTree groupVo = (GroupTree) ((Map.Entry<String, Object>) it.next()).getValue();
			if (groupVo.getParent() == null || groupVo.getParent().getId().equals(""))
				root.add(groupVo);
			else {
				GroupTree v = (GroupTree) groupList.get(groupVo.getParent().getId());
				v.getChildren().add(groupVo);
			}
		}


		return root;
	}

	@Override
	@Transactional
	public Group createGroup(Group group) {
    	String currentUser=(String) SystemContext.getUserMap().get("username");
    	group.setCreator(currentUser);
    	group.setCreateDate(new Date());
    	group.setUpdateDate(new Date());
        
		return groupRepository.save(group);
	}

	@Override
	@Transactional
	public Group updateGroup(Group group) {
        Optional < Group > groupDb = this.groupRepository.findById(group.getId());

        if (groupDb.isPresent()) {
        	Group groupUpdate = groupDb.get();

        	BeanUtils.copyProperties(group, groupUpdate);
        	String currentUser=(String) SystemContext.getUserMap().get("groupname");
            group.setUpdater(currentUser);
            group.setUpdateDate(new Date());
            
            groupRepository.save(groupUpdate);
            return groupUpdate;
        } else {
            throw new ResourceNotFoundException("记录没找到 id : " + group.getId());
        }
	}

	@Override
	@Transactional
	public void updateStatus(Group group) {
        Optional < Group > groupDb = this.groupRepository.findById(group.getId());
        if (groupDb.isPresent()) {
            this.groupRepository.upateStatus(group.getId(), group.getStatus());
        } else {
            throw new ResourceNotFoundException("记录没找到 id : " + group.getId());
        }
	}

	@Override
	@Transactional
	public void deleteGroup(String id) {
        Optional < Group > groupDb = this.groupRepository.findById(id);

        if (groupDb.isPresent()) {
            this.groupRepository.delete(groupDb.get());
        } else {
            throw new ResourceNotFoundException("记录没找到 id : " + id);
        }
	}

	@Override
	@Transactional
	public void deleteGroups(String[] ids) {
		OMAssert.notNull(ids, "exception", "GroupId is null");
		
		List<String> list=new ArrayList<>();
		Collections.addAll(list,ids);
		
		groupRepository.deleteByIdIn(list);
	}

	@Override
	public Group findById(String id) {
		Optional < Group > groupDb = this.groupRepository.findById(id);
		Group group=groupDb.get();
		return group;
	}

	@Override
	public boolean checkGroupCodeExist(String groupCode) {
		Group group=groupRepository.findByGroupCode(groupCode);
		
		if(ObjectUtils.isEmpty(group)) {
			return false;
		}else {
			return true;
		}
	}





}
