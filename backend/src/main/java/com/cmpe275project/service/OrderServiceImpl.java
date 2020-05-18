package com.cmpe275project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275project.dao.OrderDao;
import com.cmpe275project.dao.OrderDetailDao;
import com.cmpe275project.model.Order;
import com.cmpe275project.model.OrderDetail;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private OrderDetailDao orderDetailDao;

	@Override
	public Long add(Order order) {
		// TODO Auto-generated method stub
		return orderDao.add(order);
	}

	@Override
	public void edit(Order order) {
		// TODO Auto-generated method stub
	   orderDao.edit(order);	
	}
	
	@Override
	@Transactional
	public void addAllInfo(Order order) {
		// TODO Auto-generated method stub
		Long order_id = orderDao.add(order);
		
		for(OrderDetail orderDetail : order.getOrder_details())
		      {
			       orderDetail.setOrder_id(order_id);
			       orderDetailDao.add(orderDetail);
		      }
		
	}
	
	public Order getOrderInfoById(Long order_id) {
		return orderDao.getOrderInfoById(order_id);
	}

	@Override
	public List<Order> getAvailableOrders(Long pool_id,Long store_id ,Integer num_of_orders){
		// TODO Auto-generated method stub
		return orderDao.getAvailableOrders(pool_id,store_id,num_of_orders);
	}

	@Override
	public Boolean isOrderIdExists(Long id) {
		// TODO Auto-generated method stub
		return orderDao.isOrderIdExists(id);
	}

	@Override
	@Transactional
	public void assignPicker(Long user_id, List<Order> orders) {
		// TODO Auto-generated method stub
		
		if(orders!=null) {

			for(Order placedOrder : orders) {
				placedOrder.setPicker_user_id(user_id);
				placedOrder.setStatus("PICKUP_ASSIGNED");
			    orderDao.edit(placedOrder);
		      }
			
		}
		
	}

	@Override
	public List<Order> getMyAllOrders(Long user_id) {
		// TODO Auto-generated method stub
		return orderDao.getMyAllOrders(user_id);
	}

	@Override
	public List<Order> getOrdersForPickup(Long user_id) {
		// TODO Auto-generated method stub
		return orderDao.getOrdersForPickup(user_id);
	}

	@Override
	public List<Order> getOrdersForDelivery(Long user_id){
		return orderDao.getOrdersForDelivery(user_id);
	}

	@Override
	public List<Order> getAvailableOrdersForAssignment(Long pool_id,Long store_id) {
		// TODO Auto-generated method stub
		return orderDao.getAvailableOrdersForAssignment(pool_id,store_id);
	}

	@Override
	public boolean hasActiveOrders(Long pool_id) {
		return orderDao.hashActiveOrders(pool_id);
	}

	@Override
	public boolean canLeave(Long user_id) {
		
		return orderDao.canLeave(user_id);
	}
	
	
	
}
