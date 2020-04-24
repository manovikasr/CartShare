package com.cmpe275project.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="User")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@NotNull(message = "Screen Name is Mandatory")
	@NotEmpty(message = "Screen Name is Mandatory")
	@NotBlank(message = "Screen Name is Mandatory")
	@Column(name = "screen_name")
	private String screenname;
	
	@NotNull(message = "Nick Name is Mandatory")
	@NotEmpty(message = "Nick Name is Mandatory")
	@NotBlank(message = "Nick Name is Mandatory")
	@Column(name = "nick_name")
	private String nickname;
	
	@NotNull(message = "Email is Mandatory")
	@NotEmpty(message = "Email is Mandatory")
	@NotBlank(message = "Email is Mandatory")
	@Column(name = "email")
	private String email;
	
	
	@NotNull(message = "Auth mode is Mandatory")
	@NotEmpty(message = "Auth mode is Mandatory")
	@NotBlank(message = "Auth mode is Mandatory")
	@Column(name = "auth_mode")
	private String authmode;
	
	@NotNull(message = "Role is Mandatory")
	@NotEmpty(message = "Role is Mandatory")
	@NotBlank(message = "Role is Mandatory")
	@Column(name = "role")
	private String role;
	
	@NotNull(message = "verified is Mandatory")
	@Column(name = "verified")
	private boolean verified;
	
	@NotNull(message = "access_code is Mandatory")
	@NotEmpty(message = "access_code is Mandatory")
	@NotBlank(message = "access_code is Mandatory")
	@Column(name = "access_code")
	private String accesscode;
	
	/*
	 * @Column(name = "pool_id",nullable=true,insertable=true,updatable=true)
	 * 
	 * @JsonIgnore private Long poolid;
	 */
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "member_pool", 
      joinColumns = { @JoinColumn(name = "user_id") },
      inverseJoinColumns = { @JoinColumn(name = "pool_id") })
	@JsonIgnore
    private Pool pool;
	
	@NotNull(message = "contribution_credits mode is Mandatory")
	@NotEmpty(message = "contribution_credits mode is Mandatory")
	@NotBlank(message = "contribution_credits mode is Mandatory")
	@Column(name = "contribution_credits")
	private String contributioncredits;
	
	@NotNull(message = "contribution_status mode is Mandatory")
	@NotEmpty(message = "contribution_status mode is Mandatory")
	@NotBlank(message = "contribution_status mode is Mandatory")
	@Column(name = "contribution_status")
	private String contributionstatus;
	
	
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

	public String getAccesscode() {
		return accesscode;
	}

	public void setAccesscode(String accesscode) {
		this.accesscode = accesscode;
	}

	/*
	 * public Long getPoolid() { return poolid; }
	 * 
	 * public void setPoolid(Long poolid) { this.poolid = poolid; }
	 */
	public Pool getPool() {
		return pool;
	}

	public void setPool(Pool pool) {
		this.pool = pool;
	}

	public String getContributioncredits() {
		return contributioncredits;
	}

	public void setContributioncredits(String contributioncredits) {
		this.contributioncredits = contributioncredits;
	}

	public String getContributionstatus() {
		return contributionstatus;
	}

	public void setContributionstatus(String contributionstatus) {
		this.contributionstatus = contributionstatus;
	}

	
	
	
}
