package com.cmpe275project.dao;

import java.util.List;

import com.cmpe275project.model.OrderDetail;

public interface OrderDetailDao {

	public void add(OrderDetail orderDetail);
	
	public List<OrderDetail> getOrderIdsByProductId(Long product_id);
}
