package com.cmpe275project.requestObjects;

import com.cmpe275project.model.Pool;

public class UserRequest {
	
	private long id;
	private String screenname;
	private String nickname;
	private String email;
	private String role;
	private boolean email_verified;
	private Integer access_code;
	private Long pool_id;
	private Pool pool;
	private String contributionCredits;
	private String contributionStatus;
	private String address;
	private String city;
	private String state;
	private String zip;
	
	
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isEmail_verified() {
		return email_verified;
	}
	public void setVerified(boolean email_verified) {
		this.email_verified = email_verified;
	}
	public Integer getAccess_code() {
		return access_code;
	}
	public void setAccess_code(Integer access_code) {
		this.access_code = access_code;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
}
