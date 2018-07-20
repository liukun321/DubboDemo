package com.mixiusi.vo.result;

import java.util.List;
/**
 * 缺料预警List 元素VO
 * @author liukun
 *
 */
public class MachineWaringVo {
	private String machineId;
    //0:正常 1:预警 2:危险
    private Integer danger;
	
	private List<DataInfo> typeList;

	public String getMachineId() {
		return machineId;
	}

	public Integer getDanger() {
		return danger;
	}

	public List<DataInfo> getTypeList() {
		return typeList;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public void setDanger(Integer danger) {
		this.danger = danger;
	}

	public void setTypeList(List<DataInfo> typeList) {
		this.typeList = typeList;
	}
	
	
}
