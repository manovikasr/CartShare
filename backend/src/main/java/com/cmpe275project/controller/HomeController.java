package com.cmpe275project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275project.service.UserService;
import com.cmpe275project.service.UserServiceImpl;


@RestController
public class HomeController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/")
	public String index()
	{
		userService.registerUser(null);
		return "CartShare";
	}
	
}
