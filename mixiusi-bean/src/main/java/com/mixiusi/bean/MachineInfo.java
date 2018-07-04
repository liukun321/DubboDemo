package com.mixiusi.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "machine_info")
public class MachineInfo {
	@Id
	@Column(length = 100)
	private String venderName;
	
	private Long timestamps;
	
	private String machineInfo;

	public String getVenderName() {
		return venderName;
	}

	public Long getTimestamps() {
		return timestamps;
	}

	public String getMachineInfo() {
		return machineInfo;
	}

	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}

	public void setTimestamps(Long timestamps) {
		this.timestamps = timestamps;
	}

	public void setMachineInfo(String machineInfo) {
		this.machineInfo = machineInfo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((machineInfo == null) ? 0 : machineInfo.hashCode());
		result = prime * result + ((timestamps == null) ? 0 : timestamps.hashCode());
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
		MachineInfo other = (MachineInfo) obj;
		if (machineInfo == null) {
			if (other.machineInfo != null)
				return false;
		} else if (!machineInfo.equals(other.machineInfo))
			return false;
		if (timestamps == null) {
			if (other.timestamps != null)
				return false;
		} else if (!timestamps.equals(other.timestamps))
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
		return "MachineInfo [venderName=" + venderName + ", timestamps=" + timestamps + ", machineInfo=" + machineInfo
				+ "]";
	}

}
