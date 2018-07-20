package com.mixiusi.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 咖啡机信息
 * @author liukun
 *
 */
@Entity
public class CoffeeMachine implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4997312329115966677L;
	@Id
	@Column(length = 100)
	private String machineId;
	/**
	 * 咖啡机类型/0初代机、1二代机
	 */
	@NotNull
	private Integer type;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date downTime;
	/**
	 * 咖啡机编号，标明初代机和二代机的编号
	 */
	@NotNull
	private String machineCode;
	
	/**
	 * 咖啡机的状态
	 * int SYS_STATUS_INIT = 100;
	 * int SYS_STATUS_DEBUG = 200;
	 * int SYS_STATUS_WAIT = 300;
	 * int SYS_STATUS_WASH = 400;
	 * int SYS_STATUS_LOAD = 500;
	 * int SYS_STATUS_PAY = 600;
	 * int SYS_STATUS_WORK = 700;
	 * 
	 */
	@NotNull
	private Integer status;
	/**
	 * 咖啡机的是否正常运行
	 */
	@NotNull
	private Boolean is_running;
	/**
	 * 咖啡机设立时间
	 */
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 咖啡机 物料更新时间
	 */
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	/**
	 * 咖啡机修复时间, 即重新正常运行时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date repaireTime;
	/**
	 * 咖啡机负责的运维人员编号,在创建时允许为空
	 */
	private String employeeCode;
	
	public CoffeeMachine() {
	}
	public String getMachineId() {
		return machineId;
	}
	public Integer getType() {
		return type;
	}
	public Integer getStatus() {
		return status;
	}
	public Boolean getIs_running() {
		return is_running;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public Date getRepaireTime() {
		return repaireTime;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setIs_running(Boolean is_running) {
		this.is_running = is_running;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public void setRepaireTime(Date repaireTime) {
		this.repaireTime = repaireTime;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	public String getMachineCode() {
		return machineCode;
	}
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	
	public Date getDownTime() {
		return downTime;
	}
	public void setDownTime(Date downTime) {
		this.downTime = downTime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((downTime == null) ? 0 : downTime.hashCode());
		result = prime * result + ((employeeCode == null) ? 0 : employeeCode.hashCode());
		result = prime * result + ((is_running == null) ? 0 : is_running.hashCode());
		result = prime * result + ((machineCode == null) ? 0 : machineCode.hashCode());
		result = prime * result + ((machineId == null) ? 0 : machineId.hashCode());
		result = prime * result + ((repaireTime == null) ? 0 : repaireTime.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
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
		CoffeeMachine other = (CoffeeMachine) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (downTime == null) {
			if (other.downTime != null)
				return false;
		} else if (!downTime.equals(other.downTime))
			return false;
		if (employeeCode == null) {
			if (other.employeeCode != null)
				return false;
		} else if (!employeeCode.equals(other.employeeCode))
			return false;
		if (is_running == null) {
			if (other.is_running != null)
				return false;
		} else if (!is_running.equals(other.is_running))
			return false;
		if (machineCode == null) {
			if (other.machineCode != null)
				return false;
		} else if (!machineCode.equals(other.machineCode))
			return false;
		if (machineId == null) {
			if (other.machineId != null)
				return false;
		} else if (!machineId.equals(other.machineId))
			return false;
		if (repaireTime == null) {
			if (other.repaireTime != null)
				return false;
		} else if (!repaireTime.equals(other.repaireTime))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CoffeeMachine [machineId=" + machineId + ", type=" + type + ", downTime=" + downTime + ", machineCode="
				+ machineCode + ", status=" + status + ", is_running=" + is_running + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", repaireTime=" + repaireTime + ", employeeCode=" + employeeCode
				+ "]";
	}
	
	
}
