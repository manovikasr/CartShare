package com.cmpe275project.responseObjects;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cmpe275project.model.PoolRequest;

public class PoolApplicationsResponse {

	private String message;
	private Map<String, String> errors;
	private List<PoolRequest> pool_applications_list;
	
	
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
	public List<PoolRequest> getPool_Applications_List() {
		return pool_applications_list;
	}
	public void setPool_Applications_List(List<PoolRequest> pool_applications_list) {
		this.pool_applications_list = pool_applications_list;
	}
	
	
	
}
