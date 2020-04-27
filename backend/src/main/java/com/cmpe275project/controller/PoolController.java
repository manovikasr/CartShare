package com.cmpe275project.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275project.model.Pool;
import com.cmpe275project.model.User;
import com.cmpe275project.responseObjects.PoolResponse;
import com.cmpe275project.responseObjects.UserResponse;
import com.cmpe275project.service.PoolService;
import com.cmpe275project.service.UserService;

@RestController
@RequestMapping("/pool")
public class PoolController {

	@Autowired
	private PoolService poolService;
	
	@Autowired UserService userService;
	
	
	@PostMapping("")
	public ResponseEntity<?> addPool(@Valid @RequestBody Pool poolRequest, Errors errors)
	{
		HttpStatus status = HttpStatus.BAD_REQUEST;
		PoolResponse response = new PoolResponse();
		
        if(errors.hasErrors()) {
			
			Map<String,String> errorMap = new HashMap<String,String>();
			
			for (ObjectError error : errors.getAllErrors()) {
			       String fieldError = ((FieldError) error).getField();
			       errorMap.put(fieldError, error.getDefaultMessage());
			  }
			response.setErrors(errorMap);
			response.setMessage("Unable to add Pool");
			
			return new ResponseEntity<>(response, status);
		}
		if(!userService.isUserIdExists(poolRequest.getPoolleaderid())) {
			status = HttpStatus.NOT_FOUND;
			System.out.println(poolRequest.getPoolleaderid()+"----------------------------");
        	response.setMessage("Pool leader does not exist");
        	return new ResponseEntity<>(response,status);
		}
        if(!poolService.checkPoolNameExists(poolRequest.getPoolname())){
        	poolService.createPool(poolRequest);
        	status = HttpStatus.OK;
        	response.setMessage("Pool Successfully Added");
        	response.setPool(poolRequest);
        }else {
        	status = HttpStatus.CONFLICT;
        	response.setMessage("Pool Name Already In Use");
        }
        
		return new ResponseEntity<>(response,status);
	}
	
	@PostMapping("/join")
	public ResponseEntity<?> joinPool(@RequestParam(name="user_id") Long userid, @RequestParam(name="pool_id") Long poolid)
	{
		HttpStatus status = HttpStatus.NOT_FOUND;
		UserResponse response = new UserResponse();
		
		if(!userService.isUserIdExists(userid)) {
			response.setMessage("User does not exist");
			response.setUser(null);
			return new ResponseEntity<>(response,status);
		}
		if(userService.checkHasPool(userid)) {
			response.setMessage("Already joined pool ");
			status = HttpStatus.BAD_REQUEST;
			return new ResponseEntity<>(response,status);
		}
		if(!poolService.isPoolIdExist(poolid)) {
			response.setMessage("Pool Does Not exist....Create a pool first");
			return new ResponseEntity<>(response,status);
		}
		else {
			int membersCount = poolService.countMembers(poolid);
			if(membersCount>=4) {
				response.setMessage("Can't add more than 4 poolers");
				status = HttpStatus.CONFLICT;
				return new ResponseEntity<>(response,status); 
			}else {
				poolService.joinPool(userid,poolid);
				User user = userService.getUserInfoById(userid);
				System.out.println("pool id added "+user.getPool().getId());
				status = HttpStatus.OK;
				response.setMessage("User joined pool successfully");
				response.setUser(user);
				return new ResponseEntity<>(response,status);
				
			}
		}
		
	}
	
}
