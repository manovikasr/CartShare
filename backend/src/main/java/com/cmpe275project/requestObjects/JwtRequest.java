package com.cmpe275project.requestObjects;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class JwtRequest{

	//private static final long serialVersionUID = 5926468583005150707L;
	
	@NotNull(message = "Email is Mandatory")
	@NotEmpty(message = "Email is Mandatory")
	@NotBlank(message = "Email is Mandatory")
	@Email(message="Please enter a valid email address.")
	private String email;
	
	/*@NotNull(message = "Password is Mandatory")
	@NotEmpty(message = "Password is Mandatory")
	@NotBlank(message = "Password is Mandatory")
	private String password;*/
	
	//need default constructor for JSON Parsing
		public JwtRequest()
		{
			
		}
	
	public JwtRequest(String email) {
		super();
		this.email = email;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}