package com.cmpe275project.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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

	@Override
	public List<OrderDetail> getOrderIdsByProductId(Long product_id) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<OrderDetail> criteriaQuery = builder.createQuery(OrderDetail.class);
		Root<OrderDetail> root = criteriaQuery.from( OrderDetail.class);
		criteriaQuery.select(root);
		criteriaQuery.where(builder.equal(root.get( "product_id" ),product_id));
		TypedQuery<OrderDetail> query = entityManager.createQuery(criteriaQuery);
		List<OrderDetail> orders = null;
		
		try {
			orders = query.getResultList();
		}catch(Exception ex) {
			System.out.println("Error in Order Details Dao Get Orders Id"+ex.getMessage());
		}
		
		return  orders;
	}

}
