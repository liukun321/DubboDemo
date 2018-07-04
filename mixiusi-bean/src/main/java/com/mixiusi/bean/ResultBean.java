package com.mixiusi.bean;

import com.mixiusi.bean.utils.MxsConstants;

public class ResultBean {
	private String code;
	private String msg;
	private Integer limit;
	private Integer page;
	private Long totalNumber;
	private Object data;
	
	
	public ResultBean(String code, String msg, Integer limit, Integer page, Long totalNumber, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.limit = limit;
		this.page = page;
		this.totalNumber = totalNumber;
		this.data = data;
	}

	public ResultBean(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public ResultBean(String code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public ResultBean() {
	}
	
	public static ResultBean ok() {
		return new ResultBean(MxsConstants.CODE0, MxsConstants.SUCCESS);
	}
	
	public static ResultBean ok(String msg) {
		return new ResultBean(MxsConstants.CODE0, msg);
	}
	
	public static ResultBean ok(Object data) {
		return new ResultBean(MxsConstants.CODE0, MxsConstants.SUCCESS, data);
	}
	
	public static ResultBean ok(Integer limit, Integer page, Long totalNumber, Object data) {
		return new ResultBean(MxsConstants.CODE0, MxsConstants.SUCCESS, limit, page, totalNumber, data);
	}
	
	public static ResultBean error(String code, String message) {
		return new ResultBean(code, message, "");
	}
	
	public static ResultBean error() {
		return new ResultBean(MxsConstants.CODE1, MxsConstants.ERROR, "");
	}
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public Integer getLimit() {
		return limit;
	}

	public Integer getPage() {
		return page;
	}

	public Long getTotalNumber() {
		return totalNumber;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setTotalNumber(Long totalNumber) {
		this.totalNumber = totalNumber;
	}
	
}
