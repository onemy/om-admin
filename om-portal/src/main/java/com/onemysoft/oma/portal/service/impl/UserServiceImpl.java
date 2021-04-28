package com.onemysoft.oma.portal.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.onemysoft.common.context.SystemContext;
import com.onemysoft.common.utils.BeanUtils;
import com.onemysoft.common.utils.OMAssert;
import com.onemysoft.oma.portal.entity.User;
import com.onemysoft.oma.portal.exception.ResourceNotFoundException;
import com.onemysoft.oma.portal.repository.UserRepository;
import com.onemysoft.oma.portal.service.UserService;
import com.onemysoft.oma.portal.utils.ContextUtils;

@Service
public class UserServiceImpl implements UserService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private UserRepository userRepository;
    
	@Override
	@Transactional
	public User createUser(User user) {
		
    	String currentUser=(String) SystemContext.getUserMap().get("username");
        user.setCreator(currentUser);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        
		return userRepository.save(user);
	}

	@Override
	@Transactional
	public User updateUser(User user) {
        Optional < User > userDb = this.userRepository.findById(user.getId());

        if (userDb.isPresent()) {
        	User userUpdate = userDb.get();

        	BeanUtils.copyProperties(user, userUpdate);
        	String currentUser=(String) SystemContext.getUserMap().get("username");
            user.setUpdater(currentUser);
            if(StringUtils.hasText(user.getPassword())){
            	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            }
            
            user.setUpdateDate(new Date());
            
            userRepository.save(userUpdate);
            return userUpdate;
        } else {
            throw new ResourceNotFoundException("记录没找到 id : " + user.getId());
        }
        
	}
	@Override
	@Transactional
	public void deleteUser(String id) {
        Optional < User > userDb = this.userRepository.findById(id);

        if (userDb.isPresent()) {
            this.userRepository.delete(userDb.get());
        } else {
            throw new ResourceNotFoundException("记录没找到 id : " + id);
        }
	}
	
	@Override
	@Transactional
	public void batchDeleteUsers(String[] ids) {
		OMAssert.notNull(ids, "exception", "UserId is null");
		
		List<String> list=new ArrayList<>();
		Collections.addAll(list,ids);
		
		userRepository.deleteByIdIn(list);

	}

	
	public Page<User> findPageUsers(User user){
		
		int start = SystemContext.getRequestTransferData() == null ? 0 : SystemContext.getRequestTransferData().getStart();
		int limit = SystemContext.getRequestTransferData() == null ? Integer.MAX_VALUE : SystemContext.getRequestTransferData().getRows();
		
    	Sort sort = Sort.by(Direction.ASC, "orderNo");
    	Pageable pageable = PageRequest.of((start/limit), limit, sort);
    	
    	
    	ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.startsWith())//模糊查询匹配开头，即{username}%
                .withMatcher("email" ,ExampleMatcher.GenericPropertyMatchers.contains())//全部模糊查询，即%{email}%
                .withIgnorePaths("password");//忽略字段，即不管password是什么值都不加入查询条件
    	
    	Example<User> ex=Example.of(user,matcher);

    	Page<User> pageUser=userRepository.findAll(ex,pageable);

		return pageUser;
	}

	@Override
	public User findByUsername(String username) {
		
		return userRepository.findByUsername(username);
	}
	
	@Override
	public User findById(String id) {
		Optional < User > userDb = this.userRepository.findById(id);
		User user=userDb.get();
//		User user=userRepository.findByUserId(id);
		return user;
	}

	@Override
	public User findByCurrentUser() {
		String username=ContextUtils.getCurrentUserName();
		
		return userRepository.findByUsername(username);
	}

	@Override
	public boolean checkUsernameExist(String username) {
		User user=findByUsername(username);
		
		if(ObjectUtils.isEmpty(user)) {
			return false;
		}else {
			return true;
		}

	}

	@Override
	public void updatePwd(User user) {
        Optional < User > userDb = this.userRepository.findById(user.getId());
        if (userDb.isPresent()) {
        	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            this.userRepository.upatePwd(user.getId(), user.getPassword());
        } else {
            throw new ResourceNotFoundException("记录没找到 id : " + user.getId());
        }
	}

	@Override
	public void updateStatus(User user) {
        Optional < User > userDb = this.userRepository.findById(user.getId());
        if (userDb.isPresent()) {
            this.userRepository.upateStatus(user.getId(), user.getStatus());
        } else {
            throw new ResourceNotFoundException("记录没找到 id : " + user.getId());
        }
	}


}
