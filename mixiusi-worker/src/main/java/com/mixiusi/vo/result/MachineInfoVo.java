package com.mixiusi.vo.result;

import java.util.List;
/**
 * 咖啡机详情List元素 VO
 * @author liukun
 *
 */
public class MachineInfoVo {
	//咖啡机的ID
	private String machineId;
	//咖啡机的版本号
	private Integer machineVersion;
	
	private Boolean is_running;
	
	private List<InfoData> list;
	
	public List<InfoData> getList() {
		return list;
	}
	public void setList(List<InfoData> list) {
		this.list = list;
	}
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
	
	
	public Boolean getIs_running() {
		return is_running;
	}
	public void setIs_running(Boolean is_running) {
		this.is_running = is_running;
	}
	@Override
	public String toString() {
		return "MachineVo [machineId=" + machineId + ", machineVersion=" + machineVersion + "]";
	}
	
}
