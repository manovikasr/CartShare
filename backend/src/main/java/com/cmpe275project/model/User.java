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

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull(message = "Screen Name is Mandatory")
	@NotEmpty(message = "Screen Name is Mandatory")
	@NotBlank(message = "Screen Name is Mandatory")
	@Column(name = "screen_name",updatable=false)
	private String screen_name;
	
	@NotNull(message = "Nick Name is Mandatory")
	@NotEmpty(message = "Nick Name is Mandatory")
	@NotBlank(message = "Nick Name is Mandatory")
	@Column(name = "nick_name")
	private String nick_name;
	
	@NotNull(message = "Email is Mandatory")
	@NotEmpty(message = "Email is Mandatory")
	@NotBlank(message = "Email is Mandatory")
	@Column(name = "email")
	private String email;
	
	@NotNull(message = "Role is Mandatory")
	@NotEmpty(message = "Role is Mandatory")
	@NotBlank(message = "Role is Mandatory")
	@Column(name = "role")
	private String role="pooler";
	
	@Column(name = "email_verified")
	private boolean email_verified=false;
	
	@Column(name = "access_code")
	private Integer access_code;
	
	@NotNull(message = "Address is Mandatory")
	@NotEmpty(message = "Address is Mandatory")
	@NotBlank(message = "Address is Mandatory")
	@Column(name = "address")
	private String address;
	
	@NotNull(message = "City is Mandatory")
	@NotEmpty(message = "City is Mandatory")
	@NotBlank(message = "City is Mandatory")
	@Column(name = "city")
	private String city;
	
	@NotNull(message = "State is Mandatory")
	@NotEmpty(message = "State is Mandatory")
	@NotBlank(message = "State is Mandatory")
	@Column(name = "state")
	private String state;
	
	@NotNull(message = "Zip Code is Mandatory")
	@Range(min=5, message="Zip code is Manadatory(Min 5 Digits)")
	@Column(name = "zip",nullable=false)
	private Integer zip;
	
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
	
	@Column(name = "contribution_credits")
	private Integer contribution_credits = 0;
	
	@Column(name = "contribution_status")
	private String contribution_status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getScreen_name() {
		return screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
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

	public void setEmail_verified(boolean email_verified) {
		this.email_verified = email_verified;
	}

	public Integer getAccess_code() {
		return access_code;
	}

	public void setAccess_code(Integer access_code) {
		this.access_code = access_code;
	}

	public Pool getPool() {
		return pool;
	}

	public void setPool(Pool pool) {
		this.pool = pool;
	}

	public Integer getContribution_credits() {
		return contribution_credits;
	}

	public void setContribution_credits(Integer contribution_credits) {
		this.contribution_credits = contribution_credits;
	}

	public String getContribution_status() {
		return contribution_status;
	}

	public void setContribution_status(String contribution_status) {
		this.contribution_status = contribution_status;
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
	
	public Integer getZip() {
		return zip;
	}
	
	public void setZip(Integer zip) {
		this.zip = zip;
	}
		
}
