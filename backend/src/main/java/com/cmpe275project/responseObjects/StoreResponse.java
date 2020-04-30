package com.cmpe275project.responseObjects;

import java.util.List;
import java.util.Map;

import com.cmpe275project.model.Store;

public class StoreResponse {

	private String message;
    
	private Map<String, String> errors; 
	
	private Store store;
	
	//private List<Product> storeProducts;
	
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

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	/*public List<Product> getStoreProducts() {
		return storeProducts;
	}

	public void setStoreProducts(List<Product> storeProducts) {
		this.storeProducts = storeProducts;
	}*/
	
	
	
}
