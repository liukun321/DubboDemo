package com.mixiusi.vo.result;

import java.util.List;
import java.util.Map;
/**
 * 运维人员主页List元素VO
 * @author liukun
 *
 */
public class MainPageVo {
	private String machineId;
    //时间戳
    private String time;
    //0:正常 1:预警 2:危险
    private Integer danger;
    //纬度
    private double latitude;
    //经度
    private double longitude;
	
	private Boolean is_running;
	
	private List<DataInfo> typeList;

	public String getMachineId() {
		return machineId;
	}

	public Integer getDanger() {
		return danger;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public Boolean getIs_running() {
		return is_running;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public void setDanger(Integer danger) {
		this.danger = danger;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void setIs_running(Boolean is_running) {
		this.is_running = is_running;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<DataInfo> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<DataInfo> typeList) {
		this.typeList = typeList;
	}

//	public static void main(String[] args) {
//		Map<Integer, String> typeList = new HashMap();
//		
//		typeList.put(1, "咖啡豆");
//		typeList.put(2, "低音咖啡豆");
//		
//		JSONObject itemJSONObj = JSONObject.parseObject(JSON.toJSONString(typeList));
//		System.out.println(itemJSONObj.toJSONString());
//		
//	}
}
