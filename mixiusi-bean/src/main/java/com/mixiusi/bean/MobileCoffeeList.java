package com.mixiusi.bean;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class MobileCoffeeList {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "mid")
	private MobilePay mid;
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cid")
	private CoffeeList cid;
	public Integer getId() {
		return id;
	}
	public MobilePay getMid() {
		return mid;
	}
	public CoffeeList getCid() {
		return cid;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setMid(MobilePay mid) {
		this.mid = mid;
	}
	public void setCid(CoffeeList cid) {
		this.cid = cid;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mid == null) ? 0 : mid.hashCode());
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
		MobileCoffeeList other = (MobileCoffeeList) obj;
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
		if (mid == null) {
			if (other.mid != null)
				return false;
		} else if (!mid.equals(other.mid))
			return false;
		return true;
	}
	
	
}
