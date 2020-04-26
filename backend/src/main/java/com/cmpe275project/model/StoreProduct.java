package com.cmpe275project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="store_products")
public class StoreProduct {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private long store_id;
	
	private long product_id;
	
	@OneToOne
	@JoinColumn(name="store_id",nullable=false,insertable=false,updatable=false)
	private Store store;
	
	@OneToOne
	@JoinColumn(name="product_id",nullable=false,insertable=false,updatable=false)
	private Product product;
	
	public StoreProduct() {
		super();
	}

	public StoreProduct(long store_id, long product_id) {
		super();
		this.store_id = store_id;
		this.product_id = product_id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getStore_id() {
		return store_id;
	}

	public void setStore_id(long store_id) {
		this.store_id = store_id;
	}

	public long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	
	
}
