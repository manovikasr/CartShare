package com.cmpe275project.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275project.model.Pool;
import com.cmpe275project.model.PoolRequest;
import com.cmpe275project.model.User;
import com.cmpe275project.responseObjects.PoolApplicationsResponse;
import com.cmpe275project.responseObjects.PoolResponse;
import com.cmpe275project.responseObjects.UserResponse;
import com.cmpe275project.service.EmailService;
import com.cmpe275project.service.PoolService;
import com.cmpe275project.service.UserService;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@RestController
@RequestMapping("/pool")
public class PoolController {

	@Autowired
	private PoolService poolService;
	
	@Autowired UserService userService;
	
	@Autowired
	EmailService emailService;
	
	
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
	@GetMapping("/all")
	public ResponseEntity<?> getAllPools()
	{
		PoolResponse response = new PoolResponse();
		HttpStatus status = HttpStatus.NOT_FOUND;
		List<Pool> poolList = poolService.getAllPools();
		response.setPoolList(poolList);
		status = HttpStatus.OK;
		return new ResponseEntity<>(response,status);
	}

	@PostMapping("/apply")
	public ResponseEntity<?> applyPool(@RequestParam(name="user_id") Long userid,@RequestParam(name="pool_id") Long poolid,@RequestParam(name="ref_name") String refname,@RequestParam(name="knows_leader") Boolean knowsleader) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException
	{
		HttpStatus status = HttpStatus.NOT_FOUND;
		UserResponse response = new UserResponse();
		String user_screenname="";
		if(!userService.isUserIdExists(userid)) {
			response.setMessage("User does not exist");
			response.setUser(null);
			return new ResponseEntity<>(response,status);
		}
		else {
			user_screenname = userService.getUserInfoById(userid).getScreen_name();
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
		int membersCount = poolService.countMembers(poolid);
		if(membersCount>=4) {
			response.setMessage("Can't add more than 4 poolers");
			status = HttpStatus.CONFLICT;
			return new ResponseEntity<>(response,status); 
		}
		if(knowsleader==true) {
			poolService.addPoolRequest(userid,user_screenname,poolid,refname,true);
		}
		else {
			poolService.addPoolRequest(userid,user_screenname,poolid,refname,false);
		}
		User referenceUser = userService.getUserInfoByScreenName(refname);
		String ref_email = referenceUser.getEmail();
		String ref_screen_name = referenceUser.getScreen_name();
		user_screenname = userService.getUserInfoById(userid).getScreen_name();
		
		Map<String, Object> map = new HashMap<>();
		map.put("applicant_screen_name", user_screenname);
		map.put("ref_screen_name",ref_screen_name);
		
		emailService.sendRefEmail(ref_email, map);
	
		status = HttpStatus.OK;
		User user = userService.getUserInfoById(userid);
		response.setMessage("Email sent successfully");
		response.setUser(user);
		return new ResponseEntity<>(response,status);
		
	}
	
	@PostMapping("/applicationByRefName")
	public ResponseEntity<?> getApplicationsByRefName(@RequestParam(name="ref_name") String refname)
	{
		HttpStatus status = HttpStatus.NOT_FOUND;
		User referenceUser = userService.getUserInfoByScreenName(refname);
		PoolApplicationsResponse response = new PoolApplicationsResponse();
		if(referenceUser == null)
		{	
			response.setMessage("user does not exist");
			return new ResponseEntity<>(response,status);
		}
		
		List<PoolRequest> poolRequests = (List<PoolRequest>) poolService.getApplicationsByRefName(refname);
		response.setMessage("List fetched successfully");
		response.setListPoolApplications(poolRequests);
		//response.setSetApps(new HashSet<PoolRequest>(poolRequests));
		status = HttpStatus.OK;
		return new ResponseEntity<>(response,status);
		
		
	}
	
	@PostMapping("/leave")
	public ResponseEntity<?> leavePool(@RequestParam(name="user_id") Long userid, @RequestParam(name="pool_id") Long poolid)
	{
		HttpStatus status = HttpStatus.NOT_FOUND;
		UserResponse response = new UserResponse();
		
		if(!userService.isUserIdExists(userid)) {
			response.setMessage("User does not exist");
			response.setUser(null);
			return new ResponseEntity<>(response,status);
		}
		if(!userService.checkHasPool(userid)) {
			response.setMessage("Not a member of any pool ");
			status = HttpStatus.BAD_REQUEST;
			return new ResponseEntity<>(response,status);
		}
		if(!poolService.isPoolIdExist(poolid)) {
			response.setMessage("Pool Does Not exist....");
			return new ResponseEntity<>(response,status);
		}
		
		poolService.leavePool(userid,poolid);
		response.setMessage("Left pool");
		status = HttpStatus.OK;
		
		return new ResponseEntity<>(response,status);
		
	}
	@PostMapping("/supportPoolRequest")
	public ResponseEntity<?> supportPoolRequest(@RequestParam(name="application_id") Long applicationid)
	{	HttpStatus status = HttpStatus.NOT_FOUND;
		PoolResponse response = new PoolResponse();
		Long poolid = poolService.supportPoolRequest(applicationid);
		Long poolLeaderId = poolService.getPoolLeaderId(poolid);
		User poolLeader = null;
		if(poolLeaderId!=null) {
			poolLeader = userService.getUserInfoById(poolLeaderId);
		}
		String emailId = poolLeader.getEmail();
		/*
		 * send email to poolleader for approving
		 * */
		status = HttpStatus.OK;
		response.setMessage("Waiting for Leaders approval");
		return new ResponseEntity<>(response,status);
	}
	
	@PostMapping("/allSupportedApplicationsByPoolId")
	public ResponseEntity<?> allSupportedApplicationsByPool(@RequestParam(name="pool_id") Long poolid)
	{
		HttpStatus status = HttpStatus.NOT_FOUND;
		PoolApplicationsResponse response = new PoolApplicationsResponse();
		
		if(!poolService.isPoolIdExist(poolid)) {
			response.setMessage("Pool Does Not exist....");
			return new ResponseEntity<>(response,status);
		}
		List<PoolRequest> poolApplications = poolService.getAllSupportedApplicationsByPoolId(poolid);
		response.setMessage("Successfully fetched records....");
		response.setListPoolApplications(poolApplications);
		status = HttpStatus.OK;
		return new ResponseEntity<>(response,status);
	}
	
	@PostMapping("/approve")
	public ResponseEntity<?> approvePool(@RequestParam(name="user_id") Long userid, @RequestParam(name="pool_id") Long poolid)
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
			/*int membersCount = poolService.countMembers(poolid);
			if(membersCount>=4) {
				response.setMessage("Can't add more than 4 poolers");
				status = HttpStatus.CONFLICT;
				return new ResponseEntity<>(response,status); 
			}else*/
			{
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
