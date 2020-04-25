package com.cmpe275project.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {


	@RequestMapping("/order")
	public String index()
	{
		
		return "CartShare";
	}
}
