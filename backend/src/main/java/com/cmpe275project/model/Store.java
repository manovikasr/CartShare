package com.cmpe275project.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="stores")
public class Store {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull(message = "Store Name is Mandatory")
	@NotEmpty(message = "Store Name is Mandatory")
	@NotBlank(message = "Store Name is Mandatory")
	@Column(name = "store_name")
	private String store_name;
 
	@NotNull(message = "Address is Mandatory")
	@NotEmpty(message = "Address is Mandatory")
	@NotBlank(message = "Address is Mandatory")
	@Column(name = "address")
	private String address;
 
	@NotNull(message = "State is Mandatory")
	@NotEmpty(message = "State is Mandatory")
	@NotBlank(message = "State is Mandatory")
    @Column(name = "state")
	private String state;
	
	@NotNull(message = "City is Mandatory")
	@NotEmpty(message = "City is Mandatory")
	@NotBlank(message = "City is Mandatory")
	@Column(name = "city")
	private String city;
	
	@Range(min=5, message="Zip code is Manadatory(Min 5 Digits)")
	@Column(name = "zip")
	private Integer zip;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "store_products", 
    joinColumns = { @JoinColumn(name = "store_id") }, 
    inverseJoinColumns = { @JoinColumn(name = "product_id") })
	@JsonIgnore
	private List<Product> store_products;

	
	public Store() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getZip() {
		return zip;
	}

	public void setZip(Integer zip) {
		this.zip = zip;
	}

	public List<Product> getStore_products() {
		return store_products;
	}

	public void setStore_products(List<Product> store_products) {
		this.store_products = store_products;
	}
	
	
	 
}
