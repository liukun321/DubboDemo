package com.mixiusi.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class CoffeeMachineAddress implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 807246063202354369L;
	//咖啡机Id
	@Id
    private String machineId;
    //时间戳
	@NotNull
    private long time;
    //咖啡机地址
	@NotNull
    private String address;
    //纬度
	@NotNull
    private double latitude;
    //经度
	@NotNull
    private double longitude;
	//所在城市
	@NotNull
	private String city;
	public String getMachineId() {
		return machineId;
	}
	public long getTime() {
		return time;
	}
	public String getAddress() {
		return address;
	}
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public String getCity() {
		return city;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((machineId == null) ? 0 : machineId.hashCode());
		result = prime * result + (int) (time ^ (time >>> 32));
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
		CoffeeMachineAddress other = (CoffeeMachineAddress) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		if (machineId == null) {
			if (other.machineId != null)
				return false;
		} else if (!machineId.equals(other.machineId))
			return false;
		if (time != other.time)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CoffeeMachineAddress [machineId=" + machineId + ", time=" + time + ", address=" + address
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", city=" + city + "]";
	}
	
	
}
