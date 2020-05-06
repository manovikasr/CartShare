package com.cmpe275project.responseObjects;

import java.util.List;
import java.util.Map;

import com.cmpe275project.model.Order;
import com.cmpe275project.model.Store;

public class OrderResponse {

	private String message;
    
	private Map<String, String> errors; 
	
	private Order order;
	
	private List<Order> orders;
		
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	
	
}
