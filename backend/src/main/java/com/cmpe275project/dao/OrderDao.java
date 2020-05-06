package com.cmpe275project.dao;

import java.util.List;

import com.cmpe275project.model.Order;

public interface OrderDao {

	public Long add(Order order);
	
	public void edit(Order order);
	
	public Order getOrderInfoById(Long order_id);
	
	public List<Order> getSelfOrders(Long pool_id ,Integer num_of_orders);
	
	public Boolean isOrderIdExists(Long id);
	
	 public List<Order> getMyAllOrders(Long user_id);
		
	 public List<Order> getOrdersForPickup(Long user_id);
	 
	 public List<Order> getOrdersForDelivery(Long user_id);
}
