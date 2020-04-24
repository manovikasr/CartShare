package com.cmpe275project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275project.service.InventoryService;
import com.cmpe275project.service.UserService;

@RestController
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;
	
	@RequestMapping("/inventory")
	public String index()
	{
		inventoryService.createStore(null);
		return "CartShare";
	}
}
