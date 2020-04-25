package com.cmpe275project.model;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="OrderInfo")
public class Order {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "user_id")
	private long userid;
	
	@Column(name = "pool_id")
	private long poolid;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "order_time")
	private Date ordertime;
	
	@Column(name = "store_id")
	private long storeid;
	
	@Column(name = "picker_user_id")
	private long pickeruserid;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "picked_up_status")
	private String pickedupstatus;
	
	@Column(name = "store_name")
	private String deliverystatus;

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public long getPoolid() {
		return poolid;
	}

	public void setPoolid(long poolid) {
		this.poolid = poolid;
	}

	public Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

	public long getStoreid() {
		return storeid;
	}

	public void setStoreid(long storeid) {
		this.storeid = storeid;
	}

	public long getPickeruserid() {
		return pickeruserid;
	}

	public void setPickeruserid(long pickeruserid) {
		this.pickeruserid = pickeruserid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPickedupstatus() {
		return pickedupstatus;
	}

	public void setPickedupstatus(String pickedupstatus) {
		this.pickedupstatus = pickedupstatus;
	}

	public String getDeliverystatus() {
		return deliverystatus;
	}

	public void setDeliverystatus(String deliverystatus) {
		this.deliverystatus = deliverystatus;
	}
	
	
}
