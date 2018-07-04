package com.mixiusi.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rollbackindent")
public class RollbackIndent implements Serializable{
	
	@Id
	@Column(length = 200)
	private String indentId;
	
	private Double price;
	@Column(length = 100)
	private String reason;
	public String getIndentId() {
		return indentId;
	}
	public Double getPrice() {
		return price;
	}
	public String getReason() {
		return reason;
	}
	public void setIndentId(String indentId) {
		this.indentId = indentId;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((indentId == null) ? 0 : indentId.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
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
		RollbackIndent other = (RollbackIndent) obj;
		if (indentId == null) {
			if (other.indentId != null)
				return false;
		} else if (!indentId.equals(other.indentId))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "RollbackIndent [indentId=" + indentId + ", price=" + price + ", reason=" + reason + "]";
	}
	

}
