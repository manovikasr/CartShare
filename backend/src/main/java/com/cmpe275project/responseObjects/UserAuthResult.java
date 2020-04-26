package com.cmpe275project.responseObjects;

import java.util.Map;

public class UserAuthResult {

	private String message;
    
	private Map<String, String> errors; 
	
	private boolean userExists=false;
	
	private String token;
	
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
	
	public boolean isUserExists() {
		return userExists;
	}
	
	public void setUserExists(boolean userExists) {
		this.userExists = userExists;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
}
