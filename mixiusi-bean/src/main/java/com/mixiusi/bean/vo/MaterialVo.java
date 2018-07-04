package com.mixiusi.bean.vo;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public class MaterialVo implements Serializable{
	@NotEmpty
	private Integer page;
	@NotEmpty
	private Integer size;
	/**
	 *物料盒编号 
	 */
	private String stackNumber;
	/**
	 * 咖啡机编号
	 */
	private String machineCode;
	/**
	 * 物料Id
	 */
	private Integer materialId;
	/**
	 * 物料种类
	 */
	private String category;
	/**
	 * 物料的品牌
	 */
	private String brand;
	/**
	 * 物料剩余量
	 */
	private Double remainAccount;
	/**
	 * 物料状态
	 * 0-正常 1-缺料 2- 严重缺料 3-停机
	 */
	private String status;
	public String getStackNumber() {
		return stackNumber;
	}
	public String getMachineCode() {
		return machineCode;
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
	public Double getRemainAccount() {
		return remainAccount;
	}
	public String getStatus() {
		return status;
	}
	public void setStackNumber(String stackNumber) {
		this.stackNumber = stackNumber;
	}
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
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
	public void setRemainAccount(Double remainAccount) {
		this.remainAccount = remainAccount;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "MaterialVo [stackNumber=" + stackNumber + ", machineCode=" + machineCode + ", materialId=" + materialId
				+ ", category=" + category + ", brand=" + brand + ", remainAccount=" + remainAccount + ", status="
				+ status + "]";
	}
	
	
}
