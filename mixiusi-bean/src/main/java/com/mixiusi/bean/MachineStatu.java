package com.mixiusi.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MachineStatu implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8374841380276655215L;
	@Id
	@Column(length = 100)
	private String machineId;
	private Long timestamps;
	@Column(length = 100)
	private String statusjson;
	public String getMachineId() {
		return machineId;
	}
	public Long getTimestamps() {
		return timestamps;
	}
	public String getStatusjson() {
		return statusjson;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	public void setTimestamps(Long timestamps) {
		this.timestamps = timestamps;
	}
	public void setStatusjson(String statusjson) {
		this.statusjson = statusjson;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((machineId == null) ? 0 : machineId.hashCode());
		result = prime * result + ((statusjson == null) ? 0 : statusjson.hashCode());
		result = prime * result + ((timestamps == null) ? 0 : timestamps.hashCode());
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
		MachineStatu other = (MachineStatu) obj;
		if (machineId == null) {
			if (other.machineId != null)
				return false;
		} else if (!machineId.equals(other.machineId))
			return false;
		if (statusjson == null) {
			if (other.statusjson != null)
				return false;
		} else if (!statusjson.equals(other.statusjson))
			return false;
		if (timestamps == null) {
			if (other.timestamps != null)
				return false;
		} else if (!timestamps.equals(other.timestamps))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MachineStatu [machineId=" + machineId + ", timestamps=" + timestamps + ", statusjson=" + statusjson
				+ "]";
	}
	
	
}
