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
					orderDetail.setUnit_type(product.getUnit_type());
					
				}else {
					response.setMessage("Unable to Place Order, Product Id does not exits or Total Price of a product is not available");
					return new ResponseEntity<>(response, status);
				}
				
			}//for loop close
			
			orderService.addAllInfo(orderRequest);

			
			if(orderRequest.getType_of_pickup().equals("other"))
			 {	
				user.setContribution_credits(user.getContribution_credits() - 1);
				String user_email = user.getEmail();
				Map<String, Object> map = new HashMap<>();
				map.put("pooler_name", user.getScreen_name());
				emailService.sendEmailForOrderConfirmation(user_email, map);
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
	public ResponseEntity<?> ordersAssignment(@PathVariable Long store_id,@PathVariable Integer num_of_orders,HttpServletRequest request)
	{
		HttpStatus status = HttpStatus.BAD_REQUEST;
		OrderResponse response = new OrderResponse();
		List<Order> ordersToBePicked=null;
		
		Long user_id = (Long) request.getAttribute("user_id");
		User user = userService.getUserInfoById(user_id);
		
		if(storeService.isStoreIdExists(store_id)) {
			
			if(poolService.isPoolIdExist(user.getPool().getId())) {
					
					if(num_of_orders>=1)
					      ordersToBePicked = orderService.getAvailableOrders(user.getPool().getId(),store_id, num_of_orders);
					
					orderService.assignPicker(user_id,ordersToBePicked);
						
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
	
	@PutMapping("/pickup")
	public ResponseEntity<?> pickupOrders(@RequestParam Set<Long> order_ids, HttpServletRequest request) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException
	{
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		OrderResponse response = new OrderResponse();
		Order order = null;
		
		for(long order_id : order_ids) {
			if(orderService.isOrderIdExists(order_id)) {
				order = orderService.getOrderInfoById(order_id);
				order.setStatus("ORDER_PICKEDUP");
				orderService.edit(order);
				
				//TODO Send Email order picked up
			}
			else {
					httpStatus = HttpStatus.NOT_FOUND;
					response.setMessage("Order Id Not Found");
					return new ResponseEntity<>(response, httpStatus);
			}
		}
				
		httpStatus =HttpStatus.OK;
		response.setMessage("Orders Pickedup");
		
		return new ResponseEntity<>(response,httpStatus);
	}
	
	@PutMapping("/change_order_status/{order_id}/{status}")
	public ResponseEntity<?> changeOrderStatus(@PathVariable Long order_id ,@PathVariable String status, HttpServletRequest request) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException
	{
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		OrderResponse response = new OrderResponse();
		
		if(orderService.isOrderIdExists(order_id)) {
		
			Order order = orderService.getOrderInfoById(order_id);
			
			if(order!=null) {
				
				order.setStatus(status);
				orderService.edit(order);
				
				///-------------TODO --------------------Mail to be Send
				if(order.getStatus().toLowerCase().equals("delivered")) {
					String user_email = order.getUser().getEmail();
					String user_screen_name = order.getUser().getScreen_name();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("pooler_name", user_screen_name);
					map.put("order_id", order_id);
					
					emailService.sendEmailforOrderDelivered(user_email, map);
				}
				
				if(order.getStatus().toLowerCase().equals("delivered-not-received")) {
					Long deliverer_id = order.getPicker_user_id();
					String user_screen_name = order.getUser().getScreen_name();
					String deliverer_screen_name = userService.getUserInfoById(deliverer_id).getScreen_name();
					String deliverer_email = userService.getUserInfoById(deliverer_id).getScreen_name();
					
					Map<String, Object> map = new HashMap<>();
					map.put("deliverer", deliverer_screen_name);
					map.put("pooler_screen_name", user_screen_name);
					map.put("order_id", order_id);
					
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
