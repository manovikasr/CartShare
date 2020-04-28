package com.cmpe275project.controller;

import java.io.IOException;
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

import com.cmpe275project.model.User;
import com.cmpe275project.requestObjects.MessageRequest;
import com.cmpe275project.responseObjects.UserResponse;
import com.cmpe275project.service.EmailService;
import com.cmpe275project.service.UserService;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@RequestMapping("/user")
	public String index()
	{
		return "Cart Share";
	}
	
	@PostMapping("/message")
	public ResponseEntity<?> sendMessage(@Valid @RequestBody MessageRequest messageRequest, Errors errors) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		HttpStatus status = HttpStatus.NOT_FOUND;
		UserResponse response = new UserResponse();
		
		if(errors.hasErrors()) {
			Map<String,String> errorMap = new HashMap<String,String>();
			
			for (ObjectError error : errors.getAllErrors()) {
				String fieldError = ((FieldError) error).getField();
				errorMap.put(fieldError, error.getDefaultMessage());
			}
			response.setErrors(errorMap);
			response.setMessage("Unable to send message");
			
			return new ResponseEntity<>(response, status);
		}
		
		User sender = userService.getUserInfoByScreenName(messageRequest.getSender_screen_name());
		User receiver = userService.getUserInfoByScreenName(messageRequest.getReceiver_screen_name());
		if(sender != null && receiver != null) {
			String receiver_email = receiver.getEmail();
			String sender_screen_name = sender.getScreen_name();
			String receiver_screen_name = receiver.getScreen_name();
			
			Map<String, Object> map = new HashMap<>();
			map.put("from_screen_name", sender_screen_name);
			map.put("to_screen_name", receiver_screen_name);
			map.put("text", messageRequest.getMessage());
			
			emailService.sendMessageEmail(sender_screen_name, receiver_email, map);
			status = HttpStatus.OK;
			response.setMessage("Message sent succesfully");
		}
		else {
			status = HttpStatus.NOT_FOUND;
			response.setMessage("User not found");
		}
		return new ResponseEntity<>(response, status);
	}
}
