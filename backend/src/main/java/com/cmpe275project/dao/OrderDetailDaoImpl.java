package com.cmpe275project.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.cmpe275project.model.OrderDetail;


@Repository
public class OrderDetailDaoImpl implements OrderDetailDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void add(OrderDetail orderDetail) {
		
       entityManager.unwrap(Session.class).save(orderDetail);
		
	}

}
