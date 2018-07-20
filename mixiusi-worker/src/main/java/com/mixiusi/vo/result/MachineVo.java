package com.mixiusi.vo.result;
/**
 * 咖啡机列表元素 VO
 * @author liukun
 *
 */
public class MachineVo {
	//咖啡机的ID
	private String machineId;
	//咖啡机的版本号
	private Integer machineVersion;
	public String getMachineId() {
		return machineId;
	}
	public Integer getMachineVersion() {
		return machineVersion;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	public void setMachineVersion(Integer machineVersion) {
		this.machineVersion = machineVersion;
	}
	@Override
	public String toString() {
		return "MachineVo [machineId=" + machineId + ", machineVersion=" + machineVersion + "]";
	}
	
}
