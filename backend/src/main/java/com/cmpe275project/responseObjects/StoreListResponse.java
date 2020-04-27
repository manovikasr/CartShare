package com.cmpe275project.responseObjects;

import java.util.List;
import java.util.Map;

import com.cmpe275project.model.Store;
import com.cmpe275project.model.Product;

public class StoreListResponse {

	private String message;
    
	private Map<String, String> errors; 
	
	private List<Store> stores;
	
	private List<Product> storeProducts;
	
	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Store> getStores() {
		return stores;
	}

	public void setStore(List<Store> stores) {
		this.stores = stores;
	}

	/*public List<Product> getStoreProducts() {
		return storeProducts;
	}

	public void setStoreProducts(List<Product> storeProducts) {
		this.storeProducts = storeProducts;
	}*/
	
	
	
}
