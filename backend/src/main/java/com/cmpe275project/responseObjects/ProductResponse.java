package com.cmpe275project.responseObjects;

import java.util.Map;

import com.cmpe275project.model.Product;

public class ProductResponse {

	private String message;
    
	private Map<String, String> errors; 
	
	private Product product;
	
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

	public Product getProduct() {
		return product;
	}

	public void setStore(Product product) {
		this.product = product;
	}
	
	
	
}
