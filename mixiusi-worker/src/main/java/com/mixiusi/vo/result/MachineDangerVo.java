package com.mixiusi.vo.result;

import java.util.Date;
import java.util.List;

/**
 * 月度缺料记录
 * @author liukun
 *
 */
public class MachineDangerVo {
	private String startTime;
	
	private String endTime;
	
	private String machineId;
	
	private List<DataInfo> typeList;

	public String getMachineId() {
		return machineId;
	}

	public List<DataInfo> getTypeList() {
		return typeList;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public void setTypeList(List<DataInfo> typeList) {
		this.typeList = typeList;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
