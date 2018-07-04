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
 * 咖啡机物料信息
 * @author liukun
 *
 */
@Entity
public class CoffeeMaterial implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1048177076690825440L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	/**
	 *物料盒编号 
	 */
	@NotNull
	private Integer stackNumber;
	/**
	 * 咖啡机编号
	 */
	@NotNull
	private String machineId;
	/**
	 * 物料Id
	 */
	private Integer materialId;
	/**
	 * 物料种类
	 */
	@NotNull
	private String category;
	/**
	 * 物料的品牌
	 */
	private String brand;
	/**
	 * 物料状态
	 * 0-正常 1-缺料 2- 严重缺料 3-停机
	 */
	@NotNull
	private Integer status;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dangerTime;
	
	public Date getDangerTime() {
		return dangerTime;
	}
	public void setDangerTime(Date dangerTime) {
		this.dangerTime = dangerTime;
	}
	public Integer getId() {
		return id;
	}
	public Integer getStackNumber() {
		return stackNumber;
	}
	public String getMachineId() {
		return machineId;
	}
	public Integer getMaterialId() {
		return materialId;
	}
	public String getCategory() {
		return category;
	}
	public String getBrand() {
		return brand;
	}
	public Integer getStatus() {
		return status;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setStackNumber(Integer stackNumber) {
		this.stackNumber = stackNumber;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brand == null) ? 0 : brand.hashCode());
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((dangerTime == null) ? 0 : dangerTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((machineId == null) ? 0 : machineId.hashCode());
		result = prime * result + ((materialId == null) ? 0 : materialId.hashCode());
		result = prime * result + ((stackNumber == null) ? 0 : stackNumber.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		CoffeeMaterial other = (CoffeeMaterial) obj;
		if (brand == null) {
			if (other.brand != null)
				return false;
		} else if (!brand.equals(other.brand))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (dangerTime == null) {
			if (other.dangerTime != null)
				return false;
		} else if (!dangerTime.equals(other.dangerTime))
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
		if (materialId == null) {
			if (other.materialId != null)
				return false;
		} else if (!materialId.equals(other.materialId))
			return false;
		if (stackNumber == null) {
			if (other.stackNumber != null)
				return false;
		} else if (!stackNumber.equals(other.stackNumber))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CoffeeMaterial [id=" + id + ", stackNumber=" + stackNumber + ", machineId=" + machineId
				+ ", materialId=" + materialId + ", category=" + category + ", brand=" + brand + ", status=" + status
				+ ", dangerTime=" + dangerTime + "]";
	}
	
}
