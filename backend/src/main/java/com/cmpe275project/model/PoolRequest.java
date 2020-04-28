package com.cmpe275project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PoolRequest")
public class PoolRequest {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "requester_user_id")
	private long requserid;
	
	@Column(name = "requester_screen_name")
	private String requserscreenname;
	
	@Column(name = "requested_pool_id")
	private long reqpoolid;
	
	@Column(name = "reference_user_name")
	private String refusername;
	
	@Column(name = "reference_support_status")
	private boolean refsupportstatus;

	public long getRequserid() {
		return requserid;
	}

	public void setRequserid(long requserid) {
		this.requserid = requserid;
	}

	
	public String getRequserscreenname() {
		return requserscreenname;
	}

	public void setRequserscreenname(String requserscreenname) {
		this.requserscreenname = requserscreenname;
	}

	public long getReqpoolid() {
		return reqpoolid;
	}

	public void setReqpoolid(long reqpoolid) {
		this.reqpoolid = reqpoolid;
	}

	public String getRefusername() {
		return refusername;
	}

	public void setRefusername(String refusername) {
		this.refusername = refusername;
	}

	public boolean getRefsupportstatus() {
		return refsupportstatus;
	}

	public void setRefsupportstatus(boolean refsupportstatus) {
		this.refsupportstatus = refsupportstatus;
	}
	
	
	
}
