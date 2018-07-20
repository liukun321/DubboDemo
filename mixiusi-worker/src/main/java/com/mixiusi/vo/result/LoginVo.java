package com.mixiusi.vo.result;

public class LoginVo {
	private String token;
	private String photo;
	private String workerId;
	private String nickname;
	private String realname;
	private String company;
	private String sessionId;
	public String getToken() {
		return token;
	}
	public String getPhoto() {
		return photo;
	}
	public String getWorkerId() {
		return workerId;
	}
	public String getNickname() {
		return nickname;
	}
	public String getRealname() {
		return realname;
	}
	public String getCompany() {
		return company;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	@Override
	public String toString() {
		return "LoginVo [token=" + token + ", photo=" + photo + ", workerId=" + workerId + ", nickname=" + nickname
				+ ", realname=" + realname + ", company=" + company + ", sessionId=" + sessionId + "]";
	}
	
	
}
