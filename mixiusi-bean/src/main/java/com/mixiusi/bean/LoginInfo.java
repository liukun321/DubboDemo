package com.mixiusi.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "logininfo")
public class LoginInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1039732487479617974L;
	@Id
	@Column(name="VenderName", nullable=false, length=50)
	private String venderName;
	@Column(name="VenderPassword", nullable=false, length=100)
	private String venderPassword;
	@Column(name = "longitude")
	private Float longitude;
	@Column(name = "latitude")
	private Float latitude;
	public String getVenderName() {
		return venderName;
	}
	public String getVenderPassword() {
		return venderPassword;
	}
	public Float getLongitude() {
		return longitude;
	}
	public Float getLatitude() {
		return latitude;
	}
	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}
	public void setVenderPassword(String venderPassword) {
		this.venderPassword = venderPassword;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((venderName == null) ? 0 : venderName.hashCode());
		result = prime * result + ((venderPassword == null) ? 0 : venderPassword.hashCode());
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
		LoginInfo other = (LoginInfo) obj;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (venderName == null) {
			if (other.venderName != null)
				return false;
		} else if (!venderName.equals(other.venderName))
			return false;
		if (venderPassword == null) {
			if (other.venderPassword != null)
				return false;
		} else if (!venderPassword.equals(other.venderPassword))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "LoginInfo [venderName=" + venderName + ", venderPassword=" + venderPassword + ", longitude=" + longitude
				+ ", latitude=" + latitude + "]";
	}
	
	

}
