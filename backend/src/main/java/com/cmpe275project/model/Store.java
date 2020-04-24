package com.cmpe275project.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Store")
public class Store {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull(message = "Store Name is Mandatory")
	@NotEmpty(message = "Store Name is Mandatory")
	@NotBlank(message = "Store Name is Mandatory")
	@Column(name = "store_name")
	private String storename;
	
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
	
	@NotNull(message = "Zip is Mandatory")
	@Column(name = "zip")
	private int zip;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
	  name = "store_product", 
	  joinColumns = @JoinColumn(name = "store_id"), 
	  inverseJoinColumns = @JoinColumn(name = "product_id"))
	Set<Product> products;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStorename() {
		return storename;
	}

	public void setStorename(String storename) {
		this.storename = storename;
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

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
	
	
}
