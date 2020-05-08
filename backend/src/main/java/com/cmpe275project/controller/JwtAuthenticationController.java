package com.cmpe275project.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275project.config.JwtTokenUtil;
import com.cmpe275project.model.User;
import com.cmpe275project.requestObjects.JwtRequest;
import com.cmpe275project.responseObjects.JwtResponse;
import com.cmpe275project.responseObjects.UserAuthResult;
import com.cmpe275project.responseObjects.UserResponse;
import com.cmpe275project.service.EmailService;
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
	
	@Autowired
	private EmailService emailService;
	
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
			status = HttpStatus.NOT_FOUND;
			result.setMessage("User Not Found");
			result.setUserExists(false);
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
	
	@PostMapping("/register")
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
				  
				   	user.setAccess_code(userService.generateAccessCode());
				    userService.add(user);
				    result.setMessage("User Added Successfully");
				    
				    User userDetails = userService.getUserInfoByEmail(user.getEmail());

					String token = jwtTokenUtil.generateToken(userDetails);
					
                    result.setToken(token);
                    result.setUserExists(true);
                    
                    Map<String, Object> map = new HashMap<>();
                    map.put("screen_name", user.getScreen_name());
                    map.put("access_code", user.getAccess_code().toString());
                    
                    emailService.sendVerificationEmail(user.getEmail(), map);
                    
				    status = HttpStatus.OK;
			}
			
		}else {
			status = HttpStatus.CONFLICT;
			result.setMessage("Email Already In Use");
		}
		
		return new ResponseEntity<>(result,status);
	}
	
	@PostMapping("/verify/{email}/{access_code}")
	public ResponseEntity<?> verifyEmail(@PathVariable String email,@PathVariable Integer access_code ) throws Exception {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		UserAuthResult result = new UserAuthResult();
		
		if(userService.isEmailExists(email.trim())) {
			String trimmedEmail = email.trim();
			
			User userDetails = userService.getUserInfoByEmail(trimmedEmail);
			
			if(userService.isAccessCodeMatches(trimmedEmail, access_code)) {
				
				userDetails.setAccess_code(null);
	            userDetails.setEmail_verified(true);
	            userService.edit(userDetails);
	            
				result.setMessage("Email Verified Successfully");
			}
			else {
				result.setMessage("Email address could not be verified");
			}
            
			String token = jwtTokenUtil.generateToken(userDetails);
					
            result.setToken(token);
            result.setUserExists(true);
                    
            status = HttpStatus.OK;
			
		}else {
			result.setMessage("Invalid Email");
		}
		
		return new ResponseEntity<>(result,status);
	}
	
	@GetMapping("/profile")
	public ResponseEntity<?> getUserInfo(HttpServletRequest request)
	{
		HttpStatus status = HttpStatus.NOT_FOUND;
		UserResponse response = new UserResponse();
		User user = userService.getUserInfoById((Long) request.getAttribute("user_id"));
		
		if(user!=null) {
			status = HttpStatus.OK;
			response.setMessage("User Details");
			response.setUser(user);
		}else {
			response.setMessage("User Not Found");
		}
		
		return new ResponseEntity<>(response,status);
	}

	@PutMapping("/profile/{user_id}")
	public ResponseEntity<?> updateUserProfile(@Valid @RequestBody User userRequest, @PathVariable Long user_id, Errors errors)
	{
		HttpStatus status = HttpStatus.BAD_REQUEST;
	    UserResponse response = new UserResponse();
	    
		if (errors.hasErrors()) {

			Map<String, String> errorMap = new HashMap<String, String>();

			for (ObjectError error : errors.getAllErrors()) {
				String fieldError = ((FieldError) error).getField();
				errorMap.put(fieldError, error.getDefaultMessage());
			}
			response.setErrors(errorMap);
			response.setMessage("Unable to update User Profile");

			return new ResponseEntity<>(response, status);
		}

		User user = userService.getUserInfoById(user_id);
		
		if(!userService.isNickNameAvailable(userRequest.getNick_name(), user_id)) {
			
			user.setAddress(userRequest.getAddress());
			user.setCity(userRequest.getCity());
			user.setState(userRequest.getState());
			user.setZip(userRequest.getZip());
			user.setContribution_credits(userRequest.getContribution_credits());
			user.setContribution_status(userRequest.getContribution_status());
			user.setNick_name(userRequest.getNick_name());
			
			userService.edit(user);
			String token = jwtTokenUtil.generateToken(user);
			response.setMessage("User Profile Successfully Edited");
			response.setToken(token);
			status = HttpStatus.OK;
		}else {
			status = HttpStatus.CONFLICT;
			response.setMessage("Unable to update User Profile, Nick Name not available");
			return new ResponseEntity<>(response, status);
		}
		
		return new ResponseEntity<>(response,status);
	}
	
	/*private void authenticate(String email, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid Credentials", e);
		}
	}*/
}