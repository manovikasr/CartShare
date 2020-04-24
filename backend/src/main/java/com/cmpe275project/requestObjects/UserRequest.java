package com.cmpe275project.requestObjects;

import com.cmpe275project.model.Pool;

public class UserRequest {
	
	private long id;
	private String screenname;
	private String nickname;
	private String email;
	private String authmode;
	private String role;
	private boolean verified;
	private Long pool_id;
	private Pool pool;
	private String contributionCredits;
	private String contributionStatus;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getScreenname() {
		return screenname;
	}
	public void setScreenname(String screenname) {
		this.screenname = screenname;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAuthmode() {
		return authmode;
	}
	public void setAuthmode(String authmode) {
		this.authmode = authmode;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public Long getPool_id() {
		return pool_id;
	}
	public void setPool_id(Long pool_id) {
		this.pool_id = pool_id;
	}
	public Pool getPool() {
		return pool;
	}
	public void setPool(Pool pool) {
		this.pool = pool;
	}
	public String getContributionCredits() {
		return contributionCredits;
	}
	public void setContributionCredits(String contributionCredits) {
		this.contributionCredits = contributionCredits;
	}
	public String getContributionStatus() {
		return contributionStatus;
	}
	public void setContributionStatus(String contributionStatus) {
		this.contributionStatus = contributionStatus;
	}
	
	
}
