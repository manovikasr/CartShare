package com.cmpe275project.controller;


import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275project.config.JwtTokenUtil;
import com.cmpe275project.model.User;
import com.cmpe275project.requestObjects.JwtRequest;
import com.cmpe275project.responseObjects.JwtResponse;
import com.cmpe275project.responseObjects.UserAuthResult;
import com.cmpe275project.service.UserService;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;


@RestController
//@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userService;
	
	//@Autowired
	//private PasswordEncoder bcryptEncoder;
	

	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody JwtRequest authenticationRequest, Errors errors) throws Exception {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		UserAuthResult result = new UserAuthResult();
		
		if(errors.hasErrors()) {
			
			Map<String,String> errorMap = new HashMap<String,String>();
			
			 for (ObjectError error : errors.getAllErrors()) {
			       String fieldError = ((FieldError) error).getField();
			       errorMap.put(fieldError, error.getDefaultMessage());
			   }
			result.setErrors(errorMap);
			result.setMessage("Unable to Authenticate User--Issues with Input Data");
			
			return new ResponseEntity<>(result, status);
		}
		
		if(!userService.isEmailExists(authenticationRequest.getEmail())) {
			result.setMessage("User does not exists");
			result.setUserExists(false);
			return new ResponseEntity<>(result, status);
		}
		
		if(!userService.isEmailVerified(authenticationRequest.getEmail())) {
			result.setMessage("User Email Not Verified");
			result.setUserExists(true);
			return new ResponseEntity<>(result, status);
		}
		
		if(!userService.isUserActive(authenticationRequest.getEmail())) {
			result.setMessage("User Account is Not Active");
			result.setUserExists(true);
			return new ResponseEntity<>(result, status);
		}
		
		//final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		final User user = userService.getUserInfoByEmail(authenticationRequest.getEmail());

		final String token = jwtTokenUtil.generateToken(user);
		status = HttpStatus.OK;
		result.setMessage("User Token");
		result.setUserExists(true);
		result.setToken(token);
		
		return new ResponseEntity<>(result, status);
	}
	
	@PostMapping("/signUp")
	public ResponseEntity<?> saveUser(@Valid @RequestBody User user, Errors errors) throws Exception {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		UserAuthResult result = new UserAuthResult();
		
		if(errors.hasErrors()) {
			
			Map<String,String> errorMap = new HashMap<String,String>();
			
			 for (ObjectError error : errors.getAllErrors()) {
			       String fieldError = ((FieldError) error).getField();
			       errorMap.put(fieldError, error.getDefaultMessage());
			   }
			result.setErrors(errorMap);
			result.setMessage("Unable to Add User");
			
			return new ResponseEntity<>(result, status);
		}
		
		if(!userService.isEmailExists(user.getEmail())) {
			
			if(user.getScreen_name()!=null && userService.isScreenNameExists(user.getScreen_name())) {
				status = HttpStatus.CONFLICT;
				result.setMessage("Screen Name Already In Use");
			}else if(user.getNick_name()!=null && userService.isNickNameExists(user.getNick_name())) {
				status = HttpStatus.CONFLICT;
				result.setMessage("Nick Name Already In Use");
			}else {
				  
				    //user.setPassword(bcryptEncoder.encode(user.getPassword()));
				   String domain = user.getEmail().substring(user.getEmail() .indexOf("@") + 1);
					  
				   if(domain.equals("sjsu.edu"))
			          	 user.setRole("admin");
				   else
					    user.setRole("pooler");
				  
				    userService.add(user);
				    result.setMessage("User Successfully Added");
				    
				    User userDetails = userService.getUserInfoByEmail(user.getEmail());

					String token = jwtTokenUtil.generateToken(userDetails);
                    result.setToken(token);
                    result.setUserExists(true);
				    status = HttpStatus.OK;
			}
			
		}else {
			status = HttpStatus.CONFLICT;
			result.setMessage("Email Already In Use");
		}
		
		return new ResponseEntity<>(result,status);
	}

	/*private void authenticate(String email, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid Credentials", e);
		}
	}*/
}