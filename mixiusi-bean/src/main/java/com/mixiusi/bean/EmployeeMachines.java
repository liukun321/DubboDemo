package com.mixiusi.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EmployeeMachines implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3832914362082669055L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer Id;
	/**
	 * 运维人员的Id
	 */
	private String workerId;
	/**
	 * 咖啡机Id
	 */
	private String machineId;
	/**
	 * 咖啡机的类型
	 * 0 初代机
	 * 1 二代机
	 */
	private Integer type;
	public String getWorkerId() {
		return workerId;
	}
	public String getMachineId() {
		return machineId;
	}
	public Integer getType() {
		return type;
	}
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Id == null) ? 0 : Id.hashCode());
		result = prime * result + ((machineId == null) ? 0 : machineId.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((workerId == null) ? 0 : workerId.hashCode());
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
		EmployeeMachines other = (EmployeeMachines) obj;
		if (Id == null) {
			if (other.Id != null)
				return false;
		} else if (!Id.equals(other.Id))
			return false;
		if (machineId == null) {
			if (other.machineId != null)
				return false;
		} else if (!machineId.equals(other.machineId))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (workerId == null) {
			if (other.workerId != null)
				return false;
		} else if (!workerId.equals(other.workerId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "EmployeeMachines [Id=" + Id + ", workerId=" + workerId + ", machineId=" + machineId + ", type=" + type
				+ "]";
	}
	
}
