package com.cmpe275project.service;

import java.util.List;

import com.cmpe275project.model.OrderDetail;

public interface OrderDetailService {

	public void add(OrderDetail orderDetail);
	
	public void addAll(List<OrderDetail> orderDetails);
}
