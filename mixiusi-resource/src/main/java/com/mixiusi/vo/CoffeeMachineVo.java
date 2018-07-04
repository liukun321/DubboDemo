package com.mixiusi.vo;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CoffeeMachineVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6630920587874435060L;
	@NotEmpty(message = "The machineCode is empty")
	private String machineCode;
	/**
	 * 咖啡机的状态
	 * 0:闲置状态；
	  * 1：清洗阶段；
	  * 2：清洗完成；
	  * 3：缺料状态；
	  * 4：填料完成；
	  * 5：填料未完成；
	  * 6：支付阶段
	  * 7：支付成功
	  * 8：支付失败
	  * 9：二维码传输
	  * 10：磨豆机启动
	  * 11：冲泡状态
	  * 12：加粉料状态
	  * 13：加辅料状态
	 * 
	 */
	@NotEmpty(message = "The status is empty")
	private Integer status;
	/**
	 * 咖啡机的是否正常运行
	 */
	@NotEmpty(message = "The is_running is empty")
	private Boolean is_running;
	/**
	 * 咖啡机设立时间
	 */
	@NotEmpty(message = "The createTime is empty")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 咖啡机 物料更新时间
	 */
	@NotEmpty(message = "The updateTime is empty")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	/**
	 * 咖啡机修复时间, 即重新正常运行时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date repaireTime;
	/**
	 * 咖啡机负责的运维人员编号
	 */
	private String employeeCode;
	public String getMachineCode() {
		return machineCode;
	}
	public Integer getStatus() {
		return status;
	}
	public Boolean getIs_running() {
		return is_running;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public Date getRepaireTime() {
		return repaireTime;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setIs_running(Boolean is_running) {
		this.is_running = is_running;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public void setRepaireTime(Date repaireTime) {
		this.repaireTime = repaireTime;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	
}
