package com.cmpe275project.controller;

import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
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
import com.cmpe275project.service.PoolService;
import com.cmpe275project.service.ProductService;
import com.cmpe275project.service.StoreService;
import com.cmpe275project.service.UserService;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PoolService poolService;
	
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
		
		String order_status = orderRequest.getType_of_pickup().equals("other") ? "ORDER_PLACED" : "PICKUP_ASSIGNED";
		Long picker_user_id = orderRequest.getType_of_pickup().equals("self") ? (Long)request.getAttribute("user_id") : null;
		
		if(storeService.isStoreIdExists(orderRequest.getStore_id())) {
			
			Store store = storeService.getStoreInfoById(orderRequest.getStore_id());
			User user = userService.getUserInfoById((Long)request.getAttribute("user_id"));
			orderRequest.setStore_name(store.getStore_name());
			orderRequest.setUser_id(user.getId());
			orderRequest.setPool_id(user.getPool().getId());
			orderRequest.setStatus(order_status);
			orderRequest.setPicker_user_id(picker_user_id);
			
			for(OrderDetail orderDetail:orderRequest.getOrder_details()) {
				
				if((orderDetail.getProduct_id()!=null) && (productService.isProductIdExists(orderDetail.getProduct_id())) && orderDetail.getTotal_price()!=null) {
					
					Product product = productService.getProductInfoById(orderDetail.getProduct_id());
					
					orderDetail.setProduct_brand(product.getProduct_brand());
					orderDetail.setProduct_desc(product.getProduct_desc());
					orderDetail.setProduct_name(product.getProduct_name());
					orderDetail.setProduct_img(product.getProduct_img());
					orderDetail.setProduct_price(product.getPrice());
					orderDetail.setSku(product.getSku());
					orderDetail.setUnit_type(product.getUnit_type());
					
				}else {
					response.setMessage("Unable to Place Order, Product Id does not exits or Total Price of a product is not available");
					return new ResponseEntity<>(response, status);
				}
				
			}//for loop close
			
			orderService.addAllInfo(orderRequest);

			
			if(orderRequest.getType_of_pickup().equals("other"))
			 {	
				String user_email = user.getEmail();
				Map<String, Object> map = new HashMap<>();
				map.put("pooler_name", user.getScreen_name());
				map.put("order", orderRequest);
				emailService.sendEmailForOrderConfirmation(user_email, map);
			 } 
			else {
				String user_email = user.getEmail();
				Map<String, Object> map = new HashMap<>();
				map.put("pooler_name", user.getScreen_name());
				map.put("order", orderRequest);
				emailService.sendEmailForSelfOrderConfirmation(user_email, map);
			}
			response.setMessage("Order Successfully Placed");
				
		}else {
			response.setMessage("Unable to Place Order, Store Id not exits");
			return new ResponseEntity<>(response,status);
		}
		
		 status = HttpStatus.OK;
		 return new ResponseEntity<>(response,status);
	}
	
	@PostMapping("/assign/{store_id}/{num_of_orders}")
	public ResponseEntity<?> ordersAssignment(@PathVariable Long store_id,@PathVariable Integer num_of_orders,HttpServletRequest request) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException
	{
		HttpStatus status = HttpStatus.BAD_REQUEST;
		OrderResponse response = new OrderResponse();
		List<Order> ordersToBePicked=null;
		
		Long user_id = (Long) request.getAttribute("user_id");
		User user = userService.getUserInfoById(user_id);
		Store store = storeService.getStoreInfoById(store_id);
		String store_name = store.getStore_name();
		
		if(storeService.isStoreIdExists(store_id)) {
			
			if(poolService.isPoolIdExist(user.getPool().getId())) {
					
					if(num_of_orders>=1)
					      ordersToBePicked = orderService.getAvailableOrders(user.getPool().getId(),store_id, num_of_orders);
					
					orderService.assignPicker(user_id, ordersToBePicked);
					
					Map<String, Object> map = new HashMap<>();
					map.put("deliverer", user.getScreen_name());
					map.put("no_of_orders", ordersToBePicked.size());
					map.put("orders", ordersToBePicked);
					map.put("store_name", store_name);
					
					emailService.sendEmailPoolerOrderDetails(user.getEmail(), map);
					
					for(Order order : ordersToBePicked) {
						Map<String, Object> order_map = new HashMap<>();
						User pooler = userService.getUserInfoById(order.getUser_id());
						order_map.put("deliverer", user.getScreen_name());
						order_map.put("order_id", order.getId());
						order_map.put("pooler_name", pooler.getScreen_name());
						order_map.put("order",order);
						emailService.sendEmailForPickerAssigned(pooler.getEmail(), order_map);
					}
						
			} else {
				response.setMessage("Pool Id does not exists");
				return new ResponseEntity<>(response,status);
			}

		}else {
			response.setMessage("Store Id does not exists");
			return new ResponseEntity<>(response,status);
		}

		
		 response.setMessage("Picker Successfully Assigned to Orders");	    	
		 status = HttpStatus.OK;
		 return new ResponseEntity<>(response,status);
	}
	
	@GetMapping("/available/{store_id}")
	public ResponseEntity<?> availableOrdersForAssignment(@PathVariable Long store_id,HttpServletRequest request)
	{
		OrderResponse response = new OrderResponse();
		List<Order> availableOrders=null;
		Order myOrder = null;
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Long user_id = (Long) request.getAttribute("user_id");
		User user = userService.getUserInfoById(user_id);
												
		availableOrders = orderService.getAvailableOrdersForAssignment(user.getPool().getId(),store_id);
								
		if(availableOrders!=null) {
			response.setMessage("Available Orders");
			response.setOrders(availableOrders);
			status = HttpStatus.OK;
		} else {
			response.setMessage("Orders Not Found");
			status = HttpStatus.NOT_FOUND;
		}
		
		return new ResponseEntity<>(response,status);
	}
	
	
	@GetMapping("/pickup")
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
	
	@GetMapping("/delivery")
	public ResponseEntity<?> ordersForDelivery(HttpServletRequest request)
	{
		HttpStatus status = HttpStatus.NOT_FOUND;
		OrderResponse response = new OrderResponse();
		
		List<Order> ordersToBeDelivered=orderService.getOrdersForDelivery((Long)request.getAttribute("user_id"));
		
		if(ordersToBeDelivered != null) {
			status =HttpStatus.OK;
			response.setMessage("Orders To be Delivered");
			response.setOrders(ordersToBeDelivered);
		}
		
	    return new ResponseEntity<>(response,status);
	}
	
	@GetMapping("/myorders")
	public ResponseEntity<?> myOrders(HttpServletRequest request)
	{
		HttpStatus status = HttpStatus.NOT_FOUND;
		OrderResponse response = new OrderResponse();
		Date today = new Date();
		
		List<Order> myOrders=orderService.getMyAllOrders((Long)request.getAttribute("user_id"));
		
		if(myOrders==null) {
			response.setMessage("Sorry, There are no orders");
		} else {
			
			for(Order order : myOrders) {
				if(((order.getCreated_on().getTime() - today.getTime())/(24*60*60*1000)) > 2 ) {
					order.setStatus("ORDER_CANCELLED");
					orderService.edit(order);
				}
			}
			
			status =HttpStatus.OK;
			response.setMessage("My Orders");
			response.setOrders(myOrders);
		}
		
		
	    return new ResponseEntity<>(response,status);
	}
	
	@PutMapping("/pickup")
	public ResponseEntity<?> pickupOrders(@RequestParam Set<Long> order_ids, HttpServletRequest request) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException
	{
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		OrderResponse response = new OrderResponse();
		Order order = null;
		List<Order> ordersTobeDelivered = new ArrayList<>();
		User picker_user = null;
		User to_deliver_user = null;
		
		for(long order_id : order_ids) {
			if(orderService.isOrderIdExists(order_id)) {
				order = orderService.getOrderInfoById(order_id);
				if(order.getPicker_user_id() == order.getUser_id()) {
					order.setStatus("ORDER_PICKEDUP_SELF");
				} else {
					order.setStatus("ORDER_PICKEDUP");
				}
				orderService.edit(order);
				
				picker_user = userService.getUserInfoById(order.getPicker_user_id());
				to_deliver_user = userService.getUserInfoById(order.getUser_id());
				
				if(picker_user.getId() != to_deliver_user.getId()) {
					ordersTobeDelivered.add(order);
					Map<String, Object> map = new HashMap<>();
					map.put("pooler_name", to_deliver_user.getScreen_name());
					map.put("deliverer", picker_user.getScreen_name());
					map.put("order_id", order.getId());
					map.put("order",order);
					
					emailService.sendEmailForPickedUpConfirmation(to_deliver_user.getEmail(), map);
				} else {
					Map<String, Object> map = new HashMap<>();
					map.put("deliverer", picker_user.getScreen_name());
					map.put("order_id", order.getId());
					map.put("store_name", order.getStore_name());
					map.put("order",order);
					
					emailService.sendEmailForSelfPickedUpConfirmation(to_deliver_user.getEmail(), map);
				}
				
			}
			else {
					httpStatus = HttpStatus.NOT_FOUND;
					response.setMessage("Order Id Not Found");
					return new ResponseEntity<>(response, httpStatus);
			}
		}
		
		if(order_ids.size() - 1 > 0) {
			Map<String, Object> map = new HashMap<>();
		
			map.put("deliverer", picker_user.getScreen_name());
			map.put("no_of_orders", order_ids.size() - 1);
			map.put("orders", ordersTobeDelivered);
	
			emailService.sendEmailOfUserOrderDetails(picker_user.getEmail(), map);
		}
				
		httpStatus =HttpStatus.OK;
		response.setMessage("Orders Pickedup");
		
		return new ResponseEntity<>(response,httpStatus);
	}
	
	@PutMapping("/status/{order_id}/{status}")
	public ResponseEntity<?> changeOrderStatus(@PathVariable Long order_id ,@PathVariable String status, HttpServletRequest request) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException
	{
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		OrderResponse response = new OrderResponse();
		
		if(orderService.isOrderIdExists(order_id)) {
		
			Order order = orderService.getOrderInfoById(order_id);
			
			if(order!=null) {
				
				order.setStatus(status);
				orderService.edit(order);
				
				User user = order.getUser();
				User delivery_man = userService.getUserInfoById(order.getPicker_user_id());
				
				///-------------TODO --------------------Mail to be Send
				if(order.getStatus().equals("ORDER_DELIVERED")) {
					if(order.getUser().getId() != order.getPicker_user_id()) {
						user.setContribution_credits((user.getContribution_credits() - 1));
						userService.edit(user);
						delivery_man.setContribution_credits((delivery_man.getContribution_credits() + 1));
						userService.edit(delivery_man);
					}
					
					String user_email = order.getUser().getEmail();
					String user_screen_name = order.getUser().getScreen_name();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("pooler_name", user_screen_name);
					map.put("deliverer", delivery_man.getScreen_name());
					map.put("order_id", order_id);
					map.put("order",order);					
					emailService.sendEmailforOrderDelivered(user_email, map);
				}
				
				if(order.getStatus().equals("ORDER_NOT_DELIVERED")) {
					user.setContribution_credits((user.getContribution_credits() + 1));
					userService.edit(user);
					delivery_man.setContribution_credits((delivery_man.getContribution_credits() - 1));
					userService.edit(delivery_man);
					
					Long deliverer_id = order.getPicker_user_id();
					String user_screen_name = order.getUser().getScreen_name();
					String deliverer_screen_name = userService.getUserInfoById(deliverer_id).getScreen_name();
					String deliverer_email = userService.getUserInfoById(deliverer_id).getEmail();
					
					Map<String, Object> map = new HashMap<>();
					map.put("deliverer", deliverer_screen_name);
					map.put("pooler_screen_name", user_screen_name);
					map.put("order_id", order_id);
					map.put("order",order);
					
					emailService.sendEmailforOrderNotDelivered(deliverer_email, map);
				}
				
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
