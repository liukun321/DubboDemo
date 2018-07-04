package com.mixiusi.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class WorkerFeedback implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6924496078134153934L;
	@Id
	private Integer id;
	@NotNull
	private String workerId;
	@NotNull
	private Date createTime;
	@NotNull
	private String description;
	public Integer getId() {
		return id;
	}
	public String getWorkerId() {
		return workerId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		WorkerFeedback other = (WorkerFeedback) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		return "WorkerFeedback [id=" + id + ", workerId=" + workerId + ", createTime=" + createTime + ", description="
				+ description + "]";
	}
	
}
