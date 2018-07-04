package com.mixiusi.bean.vo;

import java.io.Serializable;

/**
 * 咖啡机物料查询
 * @author liukun
 *
 */
public class CoffeeMaterialVo implements Serializable{
	private Integer page;
	private Integer size;
	private String sort;
	private String machineId;
	public Integer getPage() {
		return page;
	}
	public Integer getSize() {
		return size;
	}
	public String getSort() {
		return sort;
	}
	public String getMachineId() {
		return machineId;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	
	
}
