package com.mixiusi.vo;

public class MachineVo {
	//咖啡机的ID
	private String materialId;
	//咖啡机的版本号
	private Integer materialVersiion;
	public String getMaterialId() {
		return materialId;
	}
	public Integer getMaterialVersiion() {
		return materialVersiion;
	}
	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	public void setMaterialVersiion(Integer materialVersiion) {
		this.materialVersiion = materialVersiion;
	}
	@Override
	public String toString() {
		return "MachineVo [materialId=" + materialId + ", materialVersiion=" + materialVersiion + "]";
	}
	
	
}
