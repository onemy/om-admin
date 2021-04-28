package com.onemysoft.oma.portal.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author onemysoft
 * 
 */
@Entity
@Table(name = "sys_menu")
public class Menu extends Resource {

	//menu icon
	private String icon;
	
	//vue menu icon svg
	private String icon_svg;
	
	//vue path
	private String path;
	
	//vue component
	private String component;
	
	//target 1: open new window;0: menu page
	private String target;
	
    /** 状态（0正常 1停用） */
    private String status;

	@OneToOne()
	private Menu parent;
	
	@OneToMany(mappedBy = "parent")
	private Set<Menu> children = new HashSet<Menu>();
	
	@ManyToOne
	private Module module;

	
	
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	
	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getIcon_svg() {
		return icon_svg;
	}

	public void setIcon_svg(String icon_svg) {
		this.icon_svg = icon_svg;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public Set<Menu> getChildren() {
		return children;
	}

	public void setChildren(Set<Menu> children) {
		this.children = children;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

  


}
