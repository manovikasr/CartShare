package com.cmpe275project.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
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

import com.cmpe275project.model.Order;
import com.cmpe275project.model.OrderDetail;
import com.cmpe275project.model.Product;
import com.cmpe275project.model.Store;
import com.cmpe275project.model.User;
import com.cmpe275project.responseObjects.OrderResponse;
import com.cmpe275project.service.EmailService;
import com.cmpe275project.service.OrderService;
import com.cmpe275project.service.ProductService;
import com.cmpe275project.service.StoreService;
import com.cmpe275project.service.UserService;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;
	
	@PostMapping("")
	public ResponseEntity<?> placeOrder(@Valid @RequestBody Order orderRequest,Errors errors,HttpServletRequest request) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException
	{
		HttpStatus status = HttpStatus.BAD_REQUEST;
		OrderResponse response = new OrderResponse();
		
		if (errors.hasErrors()) {

			Map<String, String> errorMap = new HashMap<String, String>();

			for (ObjectError error : errors.getAllErrors()) {
				String fieldError = ((FieldError) error).getField();
				errorMap.put(fieldError, error.getDefaultMessage());
			}
			
			response.setErrors(errorMap);
			response.setMessage("Unable to Place Order");

			return new ResponseEntity<>(response, status);
		}
		
		if(storeService.isStoreIdExists(orderRequest.getStore_id())) {
			
			Store store = storeService.getStoreInfoById(orderRequest.getStore_id());
			User user = userService.getUserInfoById((Long)request.getAttribute("user_id"));
			orderRequest.setStore_name(store.getStore_name());
			orderRequest.setUser_id(user.getId());
			orderRequest.setPool_id(user.getPool().getId());
			orderRequest.setStatus("placed");
			
			for(OrderDetail orderDetail:orderRequest.getOrder_details()) {
				
				if((orderDetail.getProduct_id()!=null) && (productService.isProductIdExists(orderDetail.getProduct_id())) && orderDetail.getTotal_price()!=null) {
					
					Product product = productService.getProductInfoById(orderDetail.getProduct_id());
					
					orderDetail.setProduct_brand(product.getProduct_brand());
					orderDetail.setProduct_desc(product.getProduct_desc());
					orderDetail.setProduct_name(product.getProduct_name());
					orderDetail.setProduct_img(product.getProduct_img());
					orderDetail.setProduct_price(product.getPrice());
					orderDetail.setSku(product.getSku());
					orderDetail.setUnit_type(product.getSku());
					
				}else {
					response.setMessage("Unable to Place Order, Product Id not exits or Total Price Not Available");
					return new ResponseEntity<>(response, status);
				}
				
			}//for loop close
			
			orderService.addAllInfo(orderRequest);
			
			//TODO --------------Email Shoot-------------------------------------
			if(orderRequest.getType_of_pickup()=="other")
			 {	
				String user_email = user.getEmail();
				Map<String, Object> map = new HashMap<>();
				map.put("pooler_name", user.getScreen_name());
				emailService.sendEmailForPickUpConfirmation(user_email, map);
			 }
				response.setMessage("Order Successfully Placed");
				
		}else {
			response.setMessage("Unable to Place Order, Store Id not exits");
			return new ResponseEntity<>(response,status);
		}
		
		 status = HttpStatus.OK;
		 return new ResponseEntity<>(response,status);
	}
	
	@GetMapping("/self/{order_id}/{num_of_orders}")
	public ResponseEntity<?> ordersAssignment(@PathVariable Long order_id,@PathVariable Integer num_of_orders,HttpServletRequest request)
	{
		HttpStatus status = HttpStatus.BAD_REQUEST;
		OrderResponse response = new OrderResponse();
		List<Order> ordersToBePicked=null;
		Order myOrder = null;
		
		if(orderService.isOrderIdExists(order_id)) {
		        
				Long user_id = (Long) request.getAttribute("user_id");
				User user = userService.getUserInfoById(user_id);
				
				myOrder = orderService.getOrderInfoById(order_id);
			
			if(myOrder.getStatus().equals("placed")) {
				
				if(myOrder.getType_of_pickup().equals("self")) {
					
					if(num_of_orders>=2)
					      ordersToBePicked = orderService.getSelfOrders(user.getPool().getId(), num_of_orders-1);
					
					orderService.assignPicker(myOrder,ordersToBePicked);
						
				}else {
					response.setMessage("Orders is not for Self Pickup, thus picker could not be assigned");
					return new ResponseEntity<>(response,status);
				}
				
			}else {
				response.setMessage("Sorry, Picker already Assigned to the order");
				return new ResponseEntity<>(response,status);
			}

		}else {
			response.setMessage("Orders Id Not exists");
			return new ResponseEntity<>(response,status);
		}
		
		 response.setMessage("Picker Successfully Assigned to Orders");	    	
		 status = HttpStatus.OK;
		 return new ResponseEntity<>(response,status);
	}
	
	@GetMapping("/orders_for_pickup")
	public ResponseEntity<?> ordersForPickup(HttpServletRequest request)
	{
		HttpStatus status = HttpStatus.NOT_FOUND;
		OrderResponse response = new OrderResponse();
		
		List<Order> ordersToBePicked=orderService.getOrdersForPickup((Long)request.getAttribute("user_id"));
		
		if(ordersToBePicked==null) {
			response.setMessage("Sorry, There are no orders for pickup");
		}else {
			status =HttpStatus.OK;
			response.setMessage("Orders To be Picked");
			response.setOrders(ordersToBePicked);
		}
		
	    return new ResponseEntity<>(response,status);
	}
	
	@GetMapping("/orders_for_delivery")
	public ResponseEntity<?> ordersForDelivery(HttpServletRequest request)
	{
		HttpStatus status = HttpStatus.NOT_FOUND;
		OrderResponse response = new OrderResponse();
		
		List<Order> ordersToBeDelivered=orderService.getOrdersForDelivery((Long)request.getAttribute("user_id"));
		
		if(ordersToBeDelivered==null) {
			response.setMessage("Sorry, There are no orders for delivery");
		}else {
			status =HttpStatus.OK;
			response.setMessage("Orders To be Delivered");
			response.setOrders(ordersToBeDelivered);
		}
		
	    return new ResponseEntity<>(response,status);
	}
	
	@GetMapping("/my_orders")
	public ResponseEntity<?> myOrders(HttpServletRequest request)
	{
		HttpStatus status = HttpStatus.NOT_FOUND;
		OrderResponse response = new OrderResponse();
		
		List<Order> myOrders=orderService.getMyAllOrders((Long)request.getAttribute("user_id"));
		
		if(myOrders==null) {
			response.setMessage("Sorry, There are no orders");
		}else {
			status =HttpStatus.OK;
			response.setMessage("My Orders");
			response.setOrders(myOrders);
		}
		
		
	    return new ResponseEntity<>(response,status);
	}
	
	@PutMapping("/change_order_status/{order_id}/{status}")
	public ResponseEntity<?> changeOrderStatus(@PathVariable Long order_id ,@PathVariable String status, HttpServletRequest request)
	{
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		OrderResponse response = new OrderResponse();
		
		if(orderService.isOrderIdExists(order_id)) {
		
			Order order = orderService.getOrderInfoById(order_id);
			
			if(order!=null) {
				
				order.setStatus(status);
				orderService.edit(order);
				
				///-------------TODO --------------------Mail to be Send
				
				httpStatus =HttpStatus.OK;
				response.setMessage("Order Status Updated Successfully..");
				
			}else {
				response.setMessage("Unable to Update Order Status");
			}
			
		}else {
			httpStatus = HttpStatus.NOT_FOUND;
			response.setMessage("Order Id Not Found");
		}
		
		return new ResponseEntity<>(response,httpStatus);
	}
	
}
