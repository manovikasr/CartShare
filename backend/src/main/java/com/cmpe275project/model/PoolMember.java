package com.cmpe275project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name="member_pool")
public class PoolMember {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "user_id", nullable = false)
	@NotNull(message= "User id is mandatory")
	@Range(min = 1)
	private long userid;
 
	@Column(name = "pool_id", nullable = false)
	@NotNull(message= "Pool id is mandatory")
	@Range(min = 1)
	private long poolid;

	@OneToOne
	@JoinColumn(name="user_id",nullable=false,insertable=false,updatable=false)
	private User user;
	
	public PoolMember() {
		super();
	}
	
	public PoolMember(@NotNull(message = "User id is mandatory") @Range(min = 1) long user_id,
			@NotNull(message = "Pool id is mandatory") @Range(min = 1) long pool_id) {
		super();
		this.userid = user_id;
		this.poolid = pool_id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUser_id() {
		return userid;
	}

	public void setUser_id(long user_id) {
		this.userid = user_id;
	}

	public long getPool_id() {
		return poolid;
	}

	public void setPool_id(long pool_id) {
		this.poolid = pool_id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
}
