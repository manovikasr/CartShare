package com.cmpe275project.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "user_id")
	private long user_id;
	
	@NotNull(message = "Store Id is Mandatory")
	@Range(min=1, message="Store Id is Manadatory(Min 1 Digit)")
	@Column(name = "store_id")
	private long store_id;
	
	@Column(name = "store_name")
	private String store_name;
	
	@Column(name = "pool_id")
	private long pool_id;
	
	@Column(name = "picker_user_id")
	private Long picker_user_id;

	@NotNull(message = "Type of Pickup is Mandatory")
	@NotEmpty(message = "Type of Pickup is Mandatory")
	@NotBlank(message = "Type of Pickup is Mandatory")
	@Column(name = "type_of_pickup")
	private String type_of_pickup;
	
	@Column(name = "status")
	private String status;
		
	@CreationTimestamp
	@Column(name = "created_on")
	private Date created_on;
	
	@UpdateTimestamp
	@Column(name = "updated_on")
	private Date updated_on;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy="order")
	@JsonManagedReference
	private Set<OrderDetail> order_details;

	@OneToOne
	@JoinColumn(name = "user_id",insertable=false,updatable=false)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "store_id",nullable=false,insertable=false,updatable=false)
	private Store store;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public long getStore_id() {
		return store_id;
	}

	public void setStore_id(long store_id) {
		this.store_id = store_id;
	}

    public String getStore_name() {
		return store_name;
	}
	
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	

	public long getPool_id() {
		return pool_id;
	}

	public void setPool_id(long pool_id) {
		this.pool_id = pool_id;
	}

	public Long getPicker_user_id() {
		return picker_user_id;
	}

	public void setPicker_user_id(Long picker_user_id) {
		this.picker_user_id = picker_user_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreated_on() {
		return created_on;
	}

	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}

	public Date getUpdated_on() {
		return updated_on;
	}

	public void setUpdated_on(Date updated_on) {
		this.updated_on = updated_on;
	}

	public Set<OrderDetail> getOrder_details() {
		return order_details;
	}

	public void setOrder_details(Set<OrderDetail> order_details) {
		this.order_details = order_details;
	}

	public String getType_of_pickup() {
		return type_of_pickup;
	}

	public void setType_of_pickup(String type_of_pickup) {
		this.type_of_pickup = type_of_pickup;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
	
}
