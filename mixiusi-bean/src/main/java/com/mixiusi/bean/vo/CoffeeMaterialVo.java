package com.mixiusi.bean.vo;

import java.io.Serializable;

/**
 * 咖啡机物料查询
 * @author liukun
 *
 */
public class CoffeeMaterialVo extends BaseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1297278642502315055L;
	
	private String machineId;
	public String getMachineId() {
		return machineId;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
}
