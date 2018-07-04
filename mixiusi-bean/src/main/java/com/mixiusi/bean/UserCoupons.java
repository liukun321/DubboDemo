package com.mixiusi.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserCoupons implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3505710084239270530L;
	//用户Id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "uid")
	private WechatUser uid;
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cid")
	private Coupons cid;
	public Integer getId() {
		return id;
	}
	public WechatUser getUid() {
		return uid;
	}
	public Coupons getCid() {
		return cid;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setUid(WechatUser uid) {
		this.uid = uid;
	}
	public void setCid(Coupons cid) {
		this.cid = cid;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
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
		UserCoupons other = (UserCoupons) obj;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "UserCoupons [id=" + id + ", uid=" + uid + ", cid=" + cid + "]";
	}
	
	
}
