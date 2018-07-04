package com.mixiusi.bean;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 用户权限关联表
 * @author liukun
 *
 */
@Entity
@Table(name = "user_permission")
public class UserPermission implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 33644966493538295L;
	private Integer id;
    private User user;
    private Permission permission;

    public UserPermission() {
    }

    public UserPermission(User user, Permission permission) {
        this.user = user;
        this.permission = permission;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "uid")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "pid")
    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((permission == null) ? 0 : permission.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserPermission other = (UserPermission) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (permission == null) {
			if (other.permission != null)
				return false;
		} else if (!permission.equals(other.permission))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserPermission [id=" + id + ", user=" + user + ", permission=" + permission + "]";
	}
    
    
}
