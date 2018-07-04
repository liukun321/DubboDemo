package com.mixiusi.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 咖啡机错误记录
 * @author liukun
 *
 */
@Entity
public class ErrorRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7872924753500764460L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	/**
	 *咖啡机编号
	 */
	@NotNull
	private String machineId;
	/**
	 * 错误开始时间
	 */
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	/**
	 * 错误结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	/**
	 * 错误持续时长/分
	 */
	private Long durationTime;
	
	/**
	 * 错误类型
	 * 0 缺料预警
	 * 1  严重警告
	 * 2 停机
	 */
	@NotNull
	private Integer type;
	//运营人员编号
	@NotNull
	private String workerId;
	/**
	 * 错误出现的次数统计,默认为0
	 */
	@NotNull
	private Integer sumError = 0;
	public Integer getId() {
		return id;
	}
	public String getMachineId() {
		return machineId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public Long getDurationTime() {
		return durationTime;
	}
	public Integer getType() {
		return type;
	}
	public Integer getSumError() {
		return sumError;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setDurationTime(Long durationTime) {
		this.durationTime = durationTime;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setSumError(Integer sumError) {
		this.sumError = sumError;
	}
	
	public String getWorkerId() {
		return workerId;
	}
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((durationTime == null) ? 0 : durationTime.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((machineId == null) ? 0 : machineId.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((sumError == null) ? 0 : sumError.hashCode());
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
		ErrorRecord other = (ErrorRecord) obj;
		if (durationTime == null) {
			if (other.durationTime != null)
				return false;
		} else if (!durationTime.equals(other.durationTime))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (machineId == null) {
			if (other.machineId != null)
				return false;
		} else if (!machineId.equals(other.machineId))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (sumError == null) {
			if (other.sumError != null)
				return false;
		} else if (!sumError.equals(other.sumError))
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
		return "ErrorRecord [id=" + id + ", machineId=" + machineId + ", startTime=" + startTime + ", endTime="
				+ endTime + ", durationTime=" + durationTime + ", type=" + type + ", workerId=" + workerId
				+ ", sumError=" + sumError + "]";
	}

}
