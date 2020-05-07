package com.cmpe275project.service;

import java.util.List;

import com.cmpe275project.model.Order;

public interface OrderService {

	public Long add(Order order);
	
	public void edit(Order order);
	
	public void addAllInfo(Order order);
	
	public Order getOrderInfoById(Long order_id);
	
	public List<Order> getAvailableOrders(Long pool_id ,Long store_id, Integer num_of_orders);
	
	public List<Order> getAvailableOrdersForAssignment(Long pool_id,Long store_id);
	
	public void assignPicker(Long user_id,List<Order> orders);
	
	public Boolean isOrderIdExists(Long id);
	
    public List<Order> getMyAllOrders(Long user_id);
	
	public List<Order> getOrdersForPickup(Long user_id);
	
	public List<Order> getOrdersForDelivery(Long user_id);
	
}
