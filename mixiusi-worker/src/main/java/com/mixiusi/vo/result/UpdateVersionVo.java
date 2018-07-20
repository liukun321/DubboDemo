package com.mixiusi.vo.result;

public class UpdateVersionVo {
	private Integer updateType;
	private String title;
	private String content;
	private String downloadUrl;//http://47.98.164.44/APP/app-release-1.1.apk.1
	public Integer getUpdateType() {
		return updateType;
	}
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setUpdateType(Integer updateType) {
		this.updateType = updateType;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	
}
