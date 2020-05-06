package com.cmpe275project.service;

import java.util.List;

import com.cmpe275project.model.Order;

public interface OrderService {

	public Long add(Order order);
	
	public void edit(Order order);
	
	public void addAllInfo(Order order);
	
	public Order getOrderInfoById(Long order_id);
	
	public List<Order> getSelfOrders(Long pool_id ,Integer num_of_orders);
	
	public void assignPicker(Order order,List<Order> orders);
	
	public Boolean isOrderIdExists(Long id);
	
    public List<Order> getMyAllOrders(Long user_id);
	
	public List<Order> getOrdersForPickup(Long user_id);
	
	public List<Order> getOrdersForDelivery(Long user_id);
	
}
