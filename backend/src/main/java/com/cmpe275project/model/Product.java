package com.cmpe275project.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="products")
public class Product {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull(message = "Product Name is Mandatory")
	@NotEmpty(message = "Product Name is Mandatory")
	@NotBlank(message = "Product Name is Mandatory")
	@Column(name = "product_name")
	private String product_name;
	
	@NotNull(message = "SKU is Mandatory")
	@NotEmpty(message = "SKU is Mandatory")
	@NotBlank(message = "SKU is Mandatory")
	@Column(name = "sku")
	private String sku;
	
	@Column(name = "store_id")
	private Long store_id;
	
	@NotNull(message = "Product Desc")
	@NotEmpty(message = "Product Desc is Mandatory")
	@NotBlank(message = "Product Desc is Mandatory")
	@Column(name = "product_desc")
	private String product_desc;
	
	@Column(name = "product_img")
	private String product_img;
	
	
	@Column(name = "product_brand")
	private String product_brand;
	
	@NotNull(message = "Unit type is Mandatory")
	@NotEmpty(message = "Unit type is Mandatory")
	@NotBlank(message = "Unit Type is Mandatory")
	@Column(name = "unit_type")
	private String unit_type;
	
	@NotNull(message = "Price is Mandatory")
	@Column(name = "price")
	private Double price;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "store_id", nullable=true, updatable=false, insertable=false)
	private Store store;

	public Product() {
		super();
	}
	
	public Product(
			@NotNull(message = "Product Name is Mandatory") @NotEmpty(message = "Product Name is Mandatory") @NotBlank(message = "Product Name is Mandatory") String product_name,
			@NotNull(message = "SKU is Mandatory") @NotEmpty(message = "SKU is Mandatory") @NotBlank(message = "SKU is Mandatory") String sku,
			Long store_id,
			@NotNull(message = "Product Desc") @NotEmpty(message = "Product Desc is Mandatory") @NotBlank(message = "Product Desc is Mandatory") String product_desc,
			String product_img, String product_brand,
			@NotNull(message = "Unit type is Mandatory") @NotEmpty(message = "Unit type is Mandatory") @NotBlank(message = "Unit Type is Mandatory") String unit_type,
			@NotNull(message = "Price is Mandatory") Double price) {
		super();
		this.product_name = product_name;
		this.sku = sku;
		this.store_id = store_id;
		this.product_desc = product_desc;
		this.product_img = product_img;
		this.product_brand = product_brand;
		this.unit_type = unit_type;
		this.price = price;
	}





	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getProduct_desc() {
		return product_desc;
	}

	public void setProduct_desc(String product_desc) {
		this.product_desc = product_desc;
	}

	public String getProduct_img() {
		return product_img;
	}

	public void setProduct_img(String product_img) {
		this.product_img = product_img;
	}

	public String getProduct_brand() {
		return product_brand;
	}

	public void setProduct_brand(String product_brand) {
		this.product_brand = product_brand;
	}

	public String getUnit_type() {
		return unit_type;
	}

	public void setUnit_type(String unit_type) {
		this.unit_type = unit_type;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getStore_id() {
		return store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	
	
	/*public Set<Store> getStores() {
		return stores;
	}

	public void setStores(Set<Store> stores) {
		this.stores = stores;
	}*/
	
	
}
