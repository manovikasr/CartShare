package com.cmpe275project.responseObjects;

import java.util.Map;

import com.cmpe275project.model.User;

public class UserResponse {

	private String message;
    
	private Map<String, String> errors; 
	
	private User user;
	
	private String token;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	
}
