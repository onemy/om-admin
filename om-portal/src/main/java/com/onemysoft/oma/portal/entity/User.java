package com.onemysoft.oma.portal.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.onemysoft.common.entity.UUIDEntity;

/**
 * @author onemysoft
 * 
 */
@Entity
@Table(name = "sys_user")
public class User extends UUIDEntity implements Principal {

    @Column(name = "username",unique = true, nullable = false)
    private String username;
    
    @Column(name = "nickname")
    private String nickname;

    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "startDate")
    private Date startDate;
    
    @Column(name = "endDate")
    private Date endDate;
    
    @Column(name = "type")
    private String type;

    @Column(name = "email")
    private String email;
    
    @Column(name = "mobile")
    private String mobile;
    
    /** 状态（0正常 1停用） */
    @Column(name = "status")
    private String status;
    
    /** 用户性别（0=男,1=女,2=未知） */
    @Column(name = "sex")
    private String sex;
    
	@Lob
	@Column
	private String pic;
	
	
    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Role> roles = new HashSet<>();

    
    @ManyToOne
    private Group group ;
    
    


	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
	
    public boolean isAdmin() {
        return isAdmin(this.getUsername());
    }

    public static boolean isAdmin(String username) {
        return username != null && username.equals("admin");
    }



	@Override
    public String toString() {
        return "User{" +
                "userId=" + super.getId() +
                ", userName='" + username + '\'' +
                ", roles=" + roles +
                '}';
    }


}
