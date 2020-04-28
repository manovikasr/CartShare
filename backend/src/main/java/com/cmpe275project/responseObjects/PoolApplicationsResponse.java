package com.cmpe275project.responseObjects;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cmpe275project.model.PoolRequest;

public class PoolApplicationsResponse {

	private String message;
	private Map<String, String> errors;
	private List<PoolRequest> listPoolApplications;
	
	
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
	public List<PoolRequest> getListPoolApplications() {
		return listPoolApplications;
	}
	public void setListPoolApplications(List<PoolRequest> listPoolApplications) {
		this.listPoolApplications = listPoolApplications;
	}
	
	
	
}
