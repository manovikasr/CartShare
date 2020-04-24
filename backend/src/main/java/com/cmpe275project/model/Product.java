package com.cmpe275project.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Product")
public class Product {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull(message = "Product Name is Mandatory")
	@NotEmpty(message = "Product Name is Mandatory")
	@NotBlank(message = "Product Name is Mandatory")
	@Column(name = "product_name")
	private String productname;
	
	@NotNull(message = "SKU is Mandatory")
	@NotEmpty(message = "SKU is Mandatory")
	@NotBlank(message = "SKU is Mandatory")
	@Column(name = "SKU")
	private String SKU;
	
	@NotNull(message = "Product Desc")
	@NotEmpty(message = "Product Desc is Mandatory")
	@NotBlank(message = "Product Desc is Mandatory")
	@Column(name = "product_desc")
	private String desc;
	
	/*
	 * @NotNull(message = "Product Image")
	 * 
	 * @NotEmpty(message = "Product Image is Mandatory")
	 * 
	 * @NotBlank(message = "Product Image is Mandatory")
	 */
	@Column(name = "product_img")
	private String productimg;
	
	
	@Column(name = "product_brand")
	private String productbrand;
	
	@NotNull(message = "Unit type is Mandatory")
	@NotEmpty(message = "Unit type is Mandatory")
	@NotBlank(message = "Unit Type is Mandatory")
	@Column(name = "unit_type")
	private String unittype;
	
	@NotNull(message = "Price is Mandatory")
	@NotEmpty(message = "Price is Mandatory")
	@NotBlank(message = "Price is Mandatory")
	@Column(name = "price")
	private int price;
	
	@ManyToMany(mappedBy = "products")
	Set<Store> stores;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getSKU() {
		return SKU;
	}

	public void setSKU(String sKU) {
		SKU = sKU;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getProductimg() {
		return productimg;
	}

	public void setProductimg(String productimg) {
		this.productimg = productimg;
	}

	public String getProductbrand() {
		return productbrand;
	}

	public void setProductbrand(String productbrand) {
		this.productbrand = productbrand;
	}

	public String getUnittype() {
		return unittype;
	}

	public void setUnittype(String unittype) {
		this.unittype = unittype;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Set<Store> getStores() {
		return stores;
	}

	public void setStores(Set<Store> stores) {
		this.stores = stores;
	}
	
	
}
