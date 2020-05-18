package com.cmpe275project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275project.model.Order;
import com.cmpe275project.model.OrderDetail;
import com.cmpe275project.model.Product;
import com.cmpe275project.model.Store;
import com.cmpe275project.responseObjects.StoreListResponse;
import com.cmpe275project.responseObjects.StoreResponse;
import com.cmpe275project.service.OrderDetailService;
import com.cmpe275project.service.OrderService;
import com.cmpe275project.service.ProductService;
import com.cmpe275project.service.StoreService;

@RestController
@RequestMapping("/store")
public class StoreController {

	@Autowired
	private StoreService storeService;
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("")
	public ResponseEntity<?> addStore(@Valid @RequestBody Store storeRequest, Errors errors)
	{
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StoreResponse response = new StoreResponse();
		
        if(errors.hasErrors()) {
			
			Map<String,String> errorMap = new HashMap<String,String>();
			
			 for (ObjectError error : errors.getAllErrors()) {
			       String fieldError = ((FieldError) error).getField();
			       errorMap.put(fieldError, error.getDefaultMessage());
			   }
			 response.setErrors(errorMap);
			 response.setMessage("Unable to add Store");
			
			return new ResponseEntity<>(response, status);
		}
		
        if(!storeService.chkStoreNameExists(storeRequest.getStore_name())){
        	storeService.add(storeRequest);
        	response.setMessage("Store Successfully Added");
        	status = HttpStatus.OK;
        	response.setStore(storeRequest);
        }else {
        	status = HttpStatus.CONFLICT;
        	response.setMessage("Store Name Already In Use");
        }
        
		return new ResponseEntity<>(response,status);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateStore(@PathVariable Long id, @Valid @RequestBody Store storeRequest, Errors errors)
	{
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StoreResponse response = new StoreResponse();
		
		if (errors.hasErrors()) {

			Map<String, String> errorMap = new HashMap<String, String>();

			for (ObjectError error : errors.getAllErrors()) {
				String fieldError = ((FieldError) error).getField();
				errorMap.put(fieldError, error.getDefaultMessage());
			}
			response.setErrors(errorMap);
			response.setMessage("Unable to update Store");

			return new ResponseEntity<>(response, status);
		}
		
		if(!storeService.isStoreIdExists(id)) {
			status = HttpStatus.NOT_FOUND;
			response.setMessage("Store Id in Invalid");
			return new ResponseEntity<>(response,status);
		}
		
		if(!storeService.isStoreNameAvailable(storeRequest.getStore_name(), id)) {
		      
			Store store = storeService.getStoreInfoById(id);
			store.setStore_name(storeRequest.getStore_name());
			store.setAddress(storeRequest.getAddress());
			store.setState(storeRequest.getState());
			store.setCity(storeRequest.getCity());
			store.setZip(storeRequest.getZip());
			
			storeService.edit(store);
			status         = HttpStatus.OK;
			response.setMessage("Store Successfully Edited");
	        response.setStore(store);		
			
		}else {
			status = HttpStatus.CONFLICT;
			response.setMessage("Store Name is Already in Use");
			return new ResponseEntity<>(response,status);
		}
		
		return new ResponseEntity<>(response,status);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getStoreInfo(@PathVariable Long id)
	{
		HttpStatus status = HttpStatus.NOT_FOUND;
		Store store = null;
		StoreResponse response = new StoreResponse();
		if(storeService.isStoreIdExists(id)) {
			
			store = storeService.getStoreInfoById(id);
			response.setMessage("Store Details");
			response.setStore(store);
			//response.set
			status = HttpStatus.OK;
		}else {
			response.setMessage("Store Details Not Available");
		}
		
		return new ResponseEntity<>(response,status);
	}
	
	//@PreAuthorize("hasAuthority('pooler')")
	@GetMapping("")
	public ResponseEntity<?> getAllStores()
	{
		HttpStatus status = HttpStatus.NOT_FOUND;
		List<Store> stores = null;
		StoreListResponse response = new StoreListResponse();
		stores = storeService.getAllStores();
		if(stores!=null) {
			response.setMessage("All Stores Details");
			response.setStore(stores);
			status = HttpStatus.OK;
		}else {
			response.setMessage("Store Details Not Available");
		}
		
		return new ResponseEntity<>(response,status);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteStore(@PathVariable Long id)
	{
		HttpStatus status = HttpStatus.NOT_FOUND;
		Store store = null;
		StoreResponse response = new StoreResponse();
		if(storeService.isStoreIdExists(id)) {
			
			store = storeService.getStoreInfoById(id);
			
			List<Order> orders = store.getOrders();
			
			for(Order order:orders) {
				
				if(!order.getStatus().equals("ORDER_DELIVERED") && !order.getStatus().equals("ORDER_PICKEDUP_SELF")
				&& !order.getStatus().equals("ORDER_CANCELLED")) {
					status = HttpStatus.BAD_REQUEST;
					response.setMessage("Cannot Delete Store. There are some undelivered orders.");
					return new ResponseEntity<>(response,status);
				}
				
			}
			     
			   storeService.delete(store);
			
			    response.setMessage("Store Successfully Deleted...");
			    status = HttpStatus.OK;
		}else {
			response.setMessage("Store Details Not Available");
		}
		
		return new ResponseEntity<>(response,status);
	}
	
}
