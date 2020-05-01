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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275project.model.Pool;
import com.cmpe275project.model.PoolRequest;
import com.cmpe275project.model.Store;
import com.cmpe275project.model.User;
import com.cmpe275project.responseObjects.PoolApplicationsResponse;
import com.cmpe275project.responseObjects.PoolResponse;
import com.cmpe275project.responseObjects.StoreResponse;
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

	@Autowired
	UserService userService;

	@Autowired
	EmailService emailService;

	@PostMapping("")
	public ResponseEntity<?> addPool(@Valid @RequestBody Pool poolRequest, Errors errors) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		PoolResponse response = new PoolResponse();

		if (errors.hasErrors()) {

			Map<String, String> errorMap = new HashMap<String, String>();

			for (ObjectError error : errors.getAllErrors()) {
				String fieldError = ((FieldError) error).getField();
				errorMap.put(fieldError, error.getDefaultMessage());
			}
			response.setErrors(errorMap);
			response.setMessage("Unable to add Pool");

			return new ResponseEntity<>(response, status);
		}

        
		if(!userService.isUserIdExists(poolRequest.getPool_leader_id())) {
			status = HttpStatus.NOT_FOUND;
        	response.setMessage("Pool leader does not exist");
        	return new ResponseEntity<>(response,status);
		}
		
		if(poolService.checkPoolIDExists(poolRequest.getPool_id())){
			status = HttpStatus.CONFLICT;
        	response.setMessage("Pool ID Already In Use");
		}
			
		if(!poolService.checkPoolNameExists(poolRequest.getPool_name())){

		

			poolService.createPool(poolRequest);
			status = HttpStatus.OK;
			response.setMessage("Pool Successfully Added");
			response.setPool(poolRequest);

		}
		else {
			status = HttpStatus.CONFLICT;
			response.setMessage("Pool Name Already In Use");
		}
					
		return new ResponseEntity<>(response,status);
	}
	

	@GetMapping("/all")
	public ResponseEntity<?> getAllPools() {
		PoolResponse response = new PoolResponse();
		HttpStatus status = HttpStatus.NOT_FOUND;
		List<Pool> poolList = poolService.getAllPools();
		response.setPoolList(poolList);
		status = HttpStatus.OK;
		return new ResponseEntity<>(response, status);
	}
	
	@GetMapping("/user/{user_id}")
	public ResponseEntity<?> getPoolForUser(@PathVariable Long user_id)
	{
		PoolResponse response = new PoolResponse();
		HttpStatus status = HttpStatus.NOT_FOUND;
		User user = userService.getUserInfoById(user_id);
		if(user != null) {
			response.setPool(user.getPool());
			status = HttpStatus.OK;
		}
		
		return new ResponseEntity<>(response,status);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updatePool(@PathVariable Long id, @Valid @RequestBody Pool poolRequest, Errors errors)
	{
		HttpStatus status = HttpStatus.BAD_REQUEST;
		PoolResponse response = new PoolResponse();
		
		if (errors.hasErrors()) {

			Map<String, String> errorMap = new HashMap<String, String>();

			for (ObjectError error : errors.getAllErrors()) {
				String fieldError = ((FieldError) error).getField();
				errorMap.put(fieldError, error.getDefaultMessage());
			}
			response.setErrors(errorMap);
			response.setMessage("Unable to update Pool");

			return new ResponseEntity<>(response, status);
		}
		
		if(!poolService.isPoolIdExist(id)) {
			status = HttpStatus.NOT_FOUND;
			response.setMessage("Pool Id not found");
			return new ResponseEntity<>(response,status);
		}
		
		if(!poolService.checkPoolNameAvailable(poolRequest.getPool_name(), id)) {
		      
			Pool pool = poolService.getPoolInfoById(id);
			
			pool.setPool_name(poolRequest.getPool_name());
			pool.setNeighbourhood_name(poolRequest.getNeighbourhood_name());
			pool.setPool_desc(poolRequest.getPool_desc());
			
			poolService.editPool(pool);
			
			status         = HttpStatus.OK;
			response.setMessage("Pool Successfully Edited");
	        response.setPool(pool);		
			
		}else {
			status = HttpStatus.CONFLICT;
			response.setMessage("Pool Name is already in use");
		}
		
		return new ResponseEntity<>(response,status);
	}
	
	@PostMapping("/delete/{pool_id}")
	public ResponseEntity<?> deletePool(@PathVariable Long pool_id)
	{
		HttpStatus status = HttpStatus.NOT_FOUND;
		PoolResponse response = new PoolResponse();
		
		if(!poolService.isPoolIdExist(pool_id)) {
			response.setMessage("Pool Does Not exist");
			return new ResponseEntity<>(response,status);
		}
		
		int membersCount = poolService.countMembers(pool_id);
		if(membersCount>1) {
			status = HttpStatus.CONFLICT;
			response.setMessage("Pool has active members");
		}
		else {
			Long leader_id = poolService.getPoolLeaderId(pool_id);

			poolService.leavePool(leader_id, pool_id);
			poolService.deletePool(pool_id);
			status = HttpStatus.OK;
			response.setMessage("Pool deleted successfully");
		}

		return new ResponseEntity<>(response,status);
	}
	
	@PostMapping("/leave/{poolid}/{userid}")
	public ResponseEntity<?> leavePool(@PathVariable Long poolid, @PathVariable Long userid)
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
			response.setMessage("Pool Does Not exist");
			return new ResponseEntity<>(response,status);
		}
		
		poolService.leavePool(userid,poolid);
		response.setMessage("Left pool");
		status = HttpStatus.OK;
		
		return new ResponseEntity<>(response,status);
		
	}
	

	@PostMapping("/apply")
	public ResponseEntity<?> applyPool(@RequestParam(name = "user_id") Long user_id,
			@RequestParam(name = "pool_id") Long pool_id, @RequestParam(name = "ref_name") String refname,
			@RequestParam(name = "knows_leader") Boolean knows_leader) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, TemplateException {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		UserResponse response = new UserResponse();
		
		if (!userService.isUserIdExists(user_id)) {
			response.setMessage("User does not exist");
			return new ResponseEntity<>(response, status);
		} 
		
		if (userService.checkHasPool(user_id)) {
			response.setMessage("User already in a pool");
			status = HttpStatus.BAD_REQUEST;
			return new ResponseEntity<>(response, status);
		}
		
		if (!poolService.isPoolIdExist(pool_id)) {
			response.setMessage("Pool does not exist");
			return new ResponseEntity<>(response, status);
		}
		
		int membersCount = poolService.countMembers(pool_id);
		if (membersCount >= 4) {
			response.setMessage("Can't add more than 4 poolers");
			status = HttpStatus.CONFLICT;
			return new ResponseEntity<>(response, status);
		}
		
		User reference_user = userService.getUserInfoByScreenName(refname);
		
		if(reference_user == null) {
			response.setMessage("Reference user does not exist");
			return new ResponseEntity<>(response, status);
		}
		
		if(!userService.checkHasPool(reference_user.getId()) || reference_user.getPool().getId() != pool_id) {
			response.setMessage("Referenced user is not present in this pool");
			status = HttpStatus.CONFLICT;
			return new ResponseEntity<>(response, status);
		}

		
		String ref_email = reference_user.getEmail();
		String ref_screen_name = reference_user.getScreen_name();
		String pool_name = reference_user.getPool().getPool_name();
		String user_screen_name = userService.getUserInfoById(user_id).getScreen_name();
		
		poolService.addPoolRequest(user_id, user_screen_name, pool_id, ref_screen_name, knows_leader);

		Map<String, Object> map = new HashMap<>();
		map.put("applicant_screen_name", user_screen_name);
		map.put("ref_screen_name", ref_screen_name);
		map.put("pool_name", pool_name);
		
		emailService.sendRefEmail(ref_email, map);

		status = HttpStatus.OK;
		
		response.setMessage("Applied successfully");
		return new ResponseEntity<>(response, status);

	}
	
	@GetMapping("/application/user/{user_id}")
	public ResponseEntity<?> getUserApplications(@PathVariable Long user_id) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		User user = userService.getUserInfoById(user_id);
		PoolApplicationsResponse response = new PoolApplicationsResponse();
		if (user == null) {
			response.setMessage("User does not exist");
			return new ResponseEntity<>(response, status);
		}

		List<PoolRequest> pool_applications = (List<PoolRequest>) poolService.getUserApplications(user_id);
		response.setMessage("User's Pool Applications");
		response.setPool_Applications_List(pool_applications);
		// response.setSetApps(new HashSet<PoolRequest>(poolRequests));
		status = HttpStatus.OK;
		return new ResponseEntity<>(response, status);

	}

	@GetMapping("/application/reference/{pool_id}/{ref_name}")
	public ResponseEntity<?> getApplicationsByRefName(@PathVariable String ref_name, @PathVariable Long pool_id) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		User referenceUser = userService.getUserInfoByScreenName(ref_name);
		PoolApplicationsResponse response = new PoolApplicationsResponse();
		
		if (referenceUser == null) {
			response.setMessage("User does not exist");
			return new ResponseEntity<>(response, status);
		}

		List<PoolRequest> pool_applications = (List<PoolRequest>) poolService.getApplicationsByRefName(ref_name, pool_id);
		response.setMessage("List fetched successfully");
		response.setPool_Applications_List(pool_applications);
		status = HttpStatus.OK;
		return new ResponseEntity<>(response, status);

	}
	
	@GetMapping("/application/pending/{pool_id}")
	public ResponseEntity<?> allSupportedApplicationsByPool(@PathVariable Long pool_id) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		PoolApplicationsResponse response = new PoolApplicationsResponse();

		if (!poolService.isPoolIdExist(pool_id)) {
			response.setMessage("Pool Does Not exist....");
			return new ResponseEntity<>(response, status);
		}
		List<PoolRequest> poolApplications = poolService.getAllSupportedApplicationsByPoolId(pool_id);
		response.setMessage("Successfully fetched records....");
		response.setPool_Applications_List(poolApplications);
		status = HttpStatus.OK;
		return new ResponseEntity<>(response, status);
	}

	@PostMapping("/application/support")
	public ResponseEntity<?> supportPoolRequest(@RequestParam(name = "application_id") Long applicationid) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		HttpStatus status = HttpStatus.NOT_FOUND;
		PoolResponse response = new PoolResponse();
		Long poolid = poolService.supportPoolRequest(applicationid);
		Long poolLeaderId = poolService.getPoolLeaderId(poolid);
		User poolLeader = null;
		if (poolLeaderId != null) {
			poolLeader = userService.getUserInfoById(poolLeaderId);
		}
		PoolRequest appInfo = poolService.getApplicationInfo(applicationid);
		User userId = userService.getUserInfoById(appInfo.getRequserid());
		String emailId = poolLeader.getEmail();
		String pool_leader_name = poolLeader.getScreen_name();
		String applicant_name = userId.getScreen_name();
		String pool_name = userId.getPool().getPool_name();
		
		Map<String, Object> map = new HashMap<>();
		map.put("applicant_screen_name", applicant_name);
		map.put("pool_leader_name", pool_leader_name);
		map.put("pool_name", pool_name);
		
		emailService.sendEmailForLeaderApproval(emailId, map);
		
		status = HttpStatus.OK;
		response.setMessage("Waiting for Leaders approval");
		return new ResponseEntity<>(response, status);
	}

	@PostMapping("/application/approve")
	public ResponseEntity<?> approvePool(@RequestParam(name = "user_id") Long userid,
			@RequestParam(name = "pool_id") Long poolid, @RequestParam(name = "application_id") Long appid) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		UserResponse response = new UserResponse();

		if (!userService.isUserIdExists(userid)) {
			response.setMessage("User does not exist");
			response.setUser(null);
			return new ResponseEntity<>(response, status);
		}
		if (userService.checkHasPool(userid)) {
			response.setMessage("Already joined pool ");
			status = HttpStatus.BAD_REQUEST;
			return new ResponseEntity<>(response, status);
		}
		if (!poolService.isPoolIdExist(poolid)) {
			response.setMessage("Pool Does Not exist....Create a pool first");
			return new ResponseEntity<>(response, status);
		} else {
			/*
			 * int membersCount = poolService.countMembers(poolid); if(membersCount>=4) {
			 * response.setMessage("Can't add more than 4 poolers"); status =
			 * HttpStatus.CONFLICT; return new ResponseEntity<>(response,status); }else
			 */
			
				poolService.joinPool(userid, poolid);
				poolService.removePoolRequest(appid);
				User user = userService.getUserInfoById(userid);
				System.out.println("pool id added " + user.getPool().getId());
				status = HttpStatus.OK;
				response.setMessage("User joined pool successfully");
				response.setUser(user);
				return new ResponseEntity<>(response, status);

			
		}

	}

}
