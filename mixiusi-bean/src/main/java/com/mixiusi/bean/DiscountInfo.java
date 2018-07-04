package com.mixiusi.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DiscountInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 76678717992571676L;
	@Id
	private String venderName;
	@Column(length = 100)
	private String discount;
	@Column(length = 100)
	private String reduction;
	public String getVenderName() {
		return venderName;
	}
	public String getDiscount() {
		return discount;
	}
	public String getReduction() {
		return reduction;
	}
	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public void setReduction(String reduction) {
		this.reduction = reduction;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discount == null) ? 0 : discount.hashCode());
		result = prime * result + ((reduction == null) ? 0 : reduction.hashCode());
		result = prime * result + ((venderName == null) ? 0 : venderName.hashCode());
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
		DiscountInfo other = (DiscountInfo) obj;
		if (discount == null) {
			if (other.discount != null)
				return false;
		} else if (!discount.equals(other.discount))
			return false;
		if (reduction == null) {
			if (other.reduction != null)
				return false;
		} else if (!reduction.equals(other.reduction))
			return false;
		if (venderName == null) {
			if (other.venderName != null)
				return false;
		} else if (!venderName.equals(other.venderName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "DiscountInfo [venderName=" + venderName + ", discount=" + discount + ", reduction=" + reduction + "]";
	}
	
	
}
