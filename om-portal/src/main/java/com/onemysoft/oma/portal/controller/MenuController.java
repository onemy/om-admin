package com.onemysoft.oma.portal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.onemysoft.common.context.SystemContext;
import com.onemysoft.common.utils.OMAssert;
import com.onemysoft.common.web.Result;
import com.onemysoft.oma.portal.entity.Menu;
import com.onemysoft.oma.portal.entity.Permission;
import com.onemysoft.oma.portal.entity.Role;
import com.onemysoft.oma.portal.entity.User;
import com.onemysoft.oma.portal.model.MenuForm;
import com.onemysoft.oma.portal.model.MenuMeta;
import com.onemysoft.oma.portal.model.MenuTree;
import com.onemysoft.oma.portal.service.MenuService;
import com.onemysoft.oma.portal.service.PermissionService;
import com.onemysoft.oma.portal.utils.ContextUtils;

/**
 * @author onemysoft
 * 
 */
@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;
    
    @Autowired
    private PermissionService permessionService;
    
    @PostMapping("/tree")
//    @PreAuthorize("hasAnyAuthority('menu/list')")
    public Result getMenuList(@ModelAttribute Menu menu){
    	
    	List<MenuTree> menuTree=new ArrayList<MenuTree>();
		List<Menu> menus=menuService.findMenuList(menu);
		if(!menus.isEmpty()) {
			menuTree=buildMenuTree(menus);
		}
		
        return Result.ok().data(menuTree);
    }
    
    @PostMapping("/userTree")
//  @PreAuthorize("hasAnyAuthority('menu/list')")
	public Result getMenuList(){
    	List<MenuTree> menuTree=new ArrayList<MenuTree>();
    	String username=ContextUtils.getCurrentUserName();
    	
    	if(User.isAdmin(username)) {
    		List<Menu> menus=menuService.findMenuList(new Menu());
    		if(!menus.isEmpty()) {
    			menuTree=buildMenuTree(menus);
    		}
    	}else {
    		List<Menu> menus=menuService.findACLByUser(username);
    		if(!menus.isEmpty()) {
    			menuTree=buildMenuTree(menus);
    		}
    	}
    	return Result.ok().data(menuTree);
	}
    
    private List<MenuTree> buildMenuTree(List<Menu> menus){
		// 递归树形结构
		List<MenuTree> mtList = new ArrayList<MenuTree>();
		Map<String, Object> menuList = new HashMap<String, Object>();
		for (Menu d : menus) {
			MenuTree mt = new MenuTree();
			MenuMeta mm = new MenuMeta();
			mm.setTitle(d.getName());
			mm.setIcon(d.getIcon_svg());
			mt.setMeta(mm);
			mt.setId(d.getId());
			mt.setCode(d.getCode());
			mt.setIcon(d.getIcon());
			mt.setName(d.getName());
			mt.setUrl(d.getUrl());
			mt.setPath(d.getPath());
			mt.setComponent(d.getComponent());
			mt.setOrderNo(d.getOrderNo());
			if (d.getParent() != null) {
				mt.setPid(d.getParent().getId());
				MenuTree parentV = new MenuTree();
				parentV.setId(d.getParent().getId());
				parentV.setCode(d.getParent().getCode());
				parentV.setIcon(d.getParent().getIcon());
				parentV.setName(d.getParent().getName());
				parentV.setUrl(d.getParent().getTarget());
				parentV.setOrderNo(d.getParent().getOrderNo());
				mt.setParent(parentV);
			}
			menuList.put(mt.getId(), mt);
			mtList.add(mt);
		}

		MenuTree root = null;
		Set entrySet = menuList.entrySet();
		
		for (Iterator it = entrySet.iterator(); it.hasNext();) {
			@SuppressWarnings("unchecked")
			MenuTree menuVo = (MenuTree) ((Map.Entry<String, Object>) it.next()).getValue();
			if (menuVo.getParent() == null || menuVo.getParent().getId().equals(""))
				root = menuVo;
			else {
				MenuTree v = (MenuTree) menuList.get(menuVo.getParent().getId());
				v.getChildren().add(menuVo);
			}
		}


		return root.getChildren();
    }
    
    /**
     * 获取所有菜单
     * @param menu
     * @return
     */
    @PostMapping("/all")
//    @PreAuthorize("hasAnyAuthority('menu/list')")
    public Result getAll(@ModelAttribute Menu menu){
    	
    	List<Menu> list=menuService.findMenuList(menu);
    	List<Map<String, String>> tree = new ArrayList<Map<String, String>>();
    	for(Menu g:list) {
    		Map<String, String> m = new HashMap<String, String>();
			m.put("id", g.getId());
			m.put("pid", g.getParent() == null ? "-1" : g.getParent().getId());
			m.put("name", g.getName());
    		
			tree.add(m);
    	}
    	
        return Result.ok().data(tree);
    }
    
    
    @PostMapping("/userMenus")
//  @PreAuthorize("hasAnyAuthority('menu/list')")
  public Result getUserMenus(String username){
    
  	//定义返回数据
  	Map<String, Object> map =new HashMap<String, Object>();
  	List<Map<String, Object>> menu = new ArrayList<Map<String, Object>>();
  	List<Map<String, Object>> permission = new ArrayList<Map<String, Object>>();
  	
  	//处理菜单，查询菜单，及有权菜单
    List<Menu> menus=menuService.findACLByUser(username);
    
  	List<Menu> allMenus=menuService.findMenuList(new Menu());
  	
  	for(Menu g : allMenus) {
  		Map<String, Object> m = new HashMap<String, Object>();
			m.put("id", g.getId());
			m.put("pid", g.getParent() == null ? "-1" : g.getParent().getId());
			m.put("name", g.getName());
			if(menus.contains(g)) {
				m.put("checked", true);
			}
			
			menu.add(m);
  	}
  	
  	//处理功能
  	
  	List<Permission> permissions=permessionService.findPermissionsByUsername(username);
  	List<Permission> allPermissions=permessionService.findAll();
  	
  	for(Permission p : allPermissions) {
  		Map<String, Object> m = new HashMap<String, Object>();
  		m.put("id", p.getId());
  		m.put("name", p.getName());
  		m.put("menuId", p.getMenu().getId());
		if(permissions.contains(p)) {
			m.put("checked", true);
		}else {
			m.put("checked", false);
		}
		permission.add(m);
  	}
  	
  	
  	//构建返回map数据
  	map.put("menus", menu);
  	map.put("permissions", permission);
  	
	return Result.ok().data(map);
  }


    @PostMapping("/roleMenus")
//  @PreAuthorize("hasAnyAuthority('menu/list')")
  public Result getRoleMenus(Role role){
    
  	//定义返回数据
  	Map<String, Object> map =new HashMap<String, Object>();
  	List<Map<String, Object>> menu = new ArrayList<Map<String, Object>>();
  	List<Map<String, Object>> permission = new ArrayList<Map<String, Object>>();
  	
  	//处理菜单，查询菜单，及有权菜单
    List<Menu> menus=menuService.findACLByRole(role);
    
  	List<Menu> allMenus=menuService.findMenuList(new Menu());
  	
  	for(Menu g : allMenus) {
  		Map<String, Object> m = new HashMap<String, Object>();
			m.put("id", g.getId());
			m.put("pid", g.getParent() == null ? "-1" : g.getParent().getId());
			m.put("name", g.getName());
			if(menus.contains(g)) {
				m.put("checked", true);
			}
			
			menu.add(m);
  	}
  	
  	//处理功能
  	
  	List<Permission> permissions=permessionService.findPermissionsByRole(role);
  	List<Permission> allPermissions=permessionService.findAll();
  	
  	for(Permission p : allPermissions) {
  		Map<String, Object> m = new HashMap<String, Object>();
  		m.put("id", p.getId());
  		m.put("name", p.getName());
  		m.put("menuId", p.getMenu().getId());
		if(permissions.contains(p)) {
			m.put("checked", true);
		}else {
			m.put("checked", false);
		}
		permission.add(m);
  	}
  	
  	
  	//构建返回map数据
  	map.put("menus", menu);
  	map.put("permissions", permission);
  	
	return Result.ok().data(map);
  }
    
    
    
    @PostMapping("/save")
    public Result save(Menu menu){
    	
    	if (OMAssert.isNotNull(menu.getId())) {
    		menuService.updateMenu(menu);
    	}else {
        	if(menuService.checkMenuCodeExist(menu.getCode())) {
        		Result.error().message("该帐号己存在！");
        	}
        	menuService.createMenu(menu);
    	}

        return Result.ok();
    }
    
    @PostMapping("/getMenu")
    public Result getMenuById(String id){
    	MenuForm menuForm=new MenuForm();
    	Menu menu=menuService.findById(id);
    	
//    	BeanUtils.copyProperties(menu,menuForm);
    	
    	menuForm.setId(menu.getId());
    	menuForm.setDescription(menu.getDescription());
    	menuForm.setOrderNo(menu.getOrderNo());
    	menuForm.setCode(menu.getCode());
    	menuForm.setName(menu.getName());
    	menuForm.setStatus(menu.getStatus());
    	menuForm.setIcon(menu.getIcon());
    	menuForm.setUrl(menu.getUrl());
    	menuForm.setPath(menu.getPath());
    	menuForm.setComponent(menu.getComponent());
    	
    	MenuForm parentMenu=new MenuForm();
    	if(!ObjectUtils.isEmpty(menu.getParent())) {
	    	parentMenu.setId(menu.getParent().getId());
	    	parentMenu.setCode(menu.getParent().getCode());
	    	parentMenu.setName(menu.getParent().getName());
	    	parentMenu.setStatus(menu.getParent().getStatus());
	    	parentMenu.setIcon(menu.getParent().getIcon());
	    	parentMenu.setUrl(menu.getParent().getUrl());
	    	parentMenu.setPath(menu.getParent().getPath());
	    	parentMenu.setComponent(menu.getParent().getComponent());
	    	menuForm.setParent(parentMenu);
    	}
    	
        return Result.ok().data(menuForm);
    }
    
    
	/**
	 * 批量删除对象
	 * 
	 * @param id
	 * @return
	 */
    @PostMapping(value = "/batchDelete")
	public Result batchDelete(@RequestParam(value="ids[]") String[] ids) {
    	this.menuService.deleteMenus(ids);
    	
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
    	this.menuService.deleteMenu(id);
		return Result.ok();
	}
    
    /**
     * 校验角色编码
     */
    @PostMapping("/checkMenuCodeExsit")
    @ResponseBody
    public Result checkUsernameExsit(String menuCode) {
    	if(menuService.checkMenuCodeExist(menuCode)) {
    		return Result.error();
    	}else {
    		return Result.ok();
    	}
    }
    
    @PostMapping("/disable")
    public Result disableRole(@Validated @RequestBody Menu menu){
    	
    	if (OMAssert.isNotNull(menu.getId())) {
    		menuService.updateStatus(menu);
    	}

        return Result.ok();
    }
}
