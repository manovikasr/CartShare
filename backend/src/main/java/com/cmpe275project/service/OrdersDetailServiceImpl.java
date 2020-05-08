package com.cmpe275project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275project.dao.OrderDetailDao;
import com.cmpe275project.model.OrderDetail;

@Service
@Transactional
public class OrdersDetailServiceImpl implements OrderDetailService {

	@Autowired
	private OrderDetailDao orderDetailDao;
	
	@Override
	public void add(OrderDetail orderDetail) {
		// TODO Auto-generated method stub
       orderDetailDao.add(orderDetail);
	}

	@Override
	@Transactional
	public void addAll(List<OrderDetail> orderDetails) {
		// TODO Auto-generated method stub
		for(OrderDetail orderDetail:orderDetails)
			             orderDetailDao.add(orderDetail);
	}

}
