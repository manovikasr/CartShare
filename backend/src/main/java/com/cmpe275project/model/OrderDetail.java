package com.cmpe275project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="order_details")
public class OrderDetail {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "order_id")
	private long order_id;
	
	@Column(name = "product_id")
	private Long product_id;
	
	@Column(name = "quantity")
	private long quantity;

	@NotNull(message = "Product Price is Mandatory")
	@Column(name = "product_price")
	private Double product_price;

	@Column(name = "total_price")
	private Double total_price;
	
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "order_id",insertable=false,updatable=false)
	private Order order;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(long order_id) {
		this.order_id = order_id;
	}

	public Long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public Double getProduct_price() {
		return product_price;
	}

	public void setProduct_price(Double product_price) {
		this.product_price = product_price;
	}

	public Double getTotal_price() {
		return total_price;
	}

	public void setTotal_price(Double total_price) {
		this.total_price = total_price;
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	
	
}
