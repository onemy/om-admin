package com.onemysoft.oma.portal.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.onemysoft.common.context.SystemContext;
import com.onemysoft.common.utils.BeanUtils;
import com.onemysoft.common.utils.OMAssert;
import com.onemysoft.oma.portal.entity.Menu;
import com.onemysoft.oma.portal.entity.Role;
import com.onemysoft.oma.portal.exception.ResourceNotFoundException;
import com.onemysoft.oma.portal.repository.AclRepository;
import com.onemysoft.oma.portal.repository.MenuRepository;
import com.onemysoft.oma.portal.repository.ResourceRepository;
import com.onemysoft.oma.portal.repository.UserRepository;
import com.onemysoft.oma.portal.service.MenuService;;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private AclRepository aclRepository;
    

	


	@Override
	public List<Menu> findACLByUser(String username) {
		List<Menu> list=new ArrayList<Menu>();
		
		list=resourceRepository.findMenusByUserAndMoudule(username,"sys");
		    	
		return list;
	}
	
	@Override
	public List<Menu> findACLByRole(Role role) {
		List<Menu> list=aclRepository.findMenuByPrincipal(role);
		return list;
	}

	@Override
	public List<Menu> findMenuList(Menu menu) {
    	Example<Menu> ex=Example.of(menu);
    	
    	List<Menu> list=menuRepository.findAll(ex);

		return list;
	}


	@Override
	@Transactional
	public Menu createMenu(Menu menu) {
    	String currentUser=(String) SystemContext.getUserMap().get("username");
    	menu.setCreator(currentUser);
    	menu.setCreateDate(new Date());
    	menu.setUpdateDate(new Date());
        
		return menuRepository.save(menu);
	}

	@Override
	@Transactional
	public Menu updateMenu(Menu menu) {
        Optional < Menu > menuDb = this.menuRepository.findById(menu.getId());

        if (menuDb.isPresent()) {
        	Menu menuUpdate = menuDb.get();

        	BeanUtils.copyProperties(menu, menuUpdate);
        	String currentUser=(String) SystemContext.getUserMap().get("menuname");
            menu.setUpdater(currentUser);
            menu.setUpdateDate(new Date());
            
            menuRepository.save(menuUpdate);
            return menuUpdate;
        } else {
            throw new ResourceNotFoundException("记录没找到 id : " + menu.getId());
        }
	}

	@Override
	@Transactional
	public void updateStatus(Menu menu) {
        Optional < Menu > menuDb = this.menuRepository.findById(menu.getId());
        if (menuDb.isPresent()) {
            this.menuRepository.upateStatus(menu.getId(), menu.getStatus());
        } else {
            throw new ResourceNotFoundException("记录没找到 id : " + menu.getId());
        }
	}

	@Override
	@Transactional
	public void deleteMenu(String id) {
        Optional < Menu > menuDb = this.menuRepository.findById(id);

        if (menuDb.isPresent()) {
            this.menuRepository.delete(menuDb.get());
        } else {
            throw new ResourceNotFoundException("记录没找到 id : " + id);
        }
	}

	@Override
	@Transactional
	public void deleteMenus(String[] ids) {
		OMAssert.notNull(ids, "exception", "MenuId is null");
		
		List<String> list=new ArrayList<>();
		Collections.addAll(list,ids);
		
		menuRepository.deleteByIdIn(list);
	}

	@Override
	public Menu findById(String id) {
		Optional < Menu > menuDb = this.menuRepository.findById(id);
		Menu menu=menuDb.get();
		return menu;
	}

	@Override
	public boolean checkMenuCodeExist(String menuCode) {
		Menu menu=menuRepository.findByCode(menuCode);
		
		if(ObjectUtils.isEmpty(menu)) {
			return false;
		}else {
			return true;
		}
	}


}
