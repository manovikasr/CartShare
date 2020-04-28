package com.cmpe275project.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275project.model.User;
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
	public ResponseEntity<?> sendMessage(@RequestParam String from_email, @RequestParam String to_screen_name, @RequestParam String text) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		HttpStatus status;
		UserResponse response = new UserResponse();
		if(userService.isEmailExists(from_email)) {
			User user1 = userService.getUserInfoByEmail(from_email);
			User user2 = userService.getUserInfoByScreenName(to_screen_name);
			String user1_screen_name = user1.getScreen_name();
			String to_email = user2.getEmail();
			Map<String, Object> map = new HashMap<>();
			map.put("from_screen_name", user1_screen_name);
			map.put("to_screen_name", to_screen_name);
			map.put("text", text);
			
			emailService.sendMessageEmail(user1_screen_name, to_email, map);
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
