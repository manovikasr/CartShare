package com.cmpe275project.responseObjects;

import java.util.List;
import java.util.Map;

import com.cmpe275project.model.Pool;

public class PoolResponse {

	private String message;
    
	private Map<String, String> errors; 
	
	private Pool pool;
	
	private List<Pool> pool_list;
	
	

	public List<Pool> getPool_list() {
		return pool_list;
	}

	public void setPoolList(List<Pool> pool_list) {
		this.pool_list = pool_list;
	}

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
