package com.cmpe275project.responseObjects;

import java.util.Map;

import com.cmpe275project.model.Pool;

public class PoolResponse {

	private String message;
    
	private Map<String, String> errors; 
	
	private Pool pool;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

	public Pool getPool() {
		return pool;
	}

	public void setPool(Pool pool) {
		this.pool = pool;
	}
	
}
