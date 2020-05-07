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

import com.cmpe275project.model.Order;
import com.cmpe275project.model.Store;

@Repository
public class OrderDaoImpl implements OrderDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Long add(Order order) {
		
		Long id = (Long) entityManager.unwrap(Session.class).save(order);
		return id;
		
	}
	
	@Override
	public void edit(Order order) {
		// TODO Auto-generated method stub
		entityManager.unwrap(Session.class).update(order);
	}
	
	@Override
	public Order getOrderInfoById(Long order_id) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from( Order.class);
		criteriaQuery.select(root);
		criteriaQuery.where(builder.equal(root.get( "id" ),order_id));
		TypedQuery<Order> query = entityManager.createQuery(criteriaQuery);
		Order order = null;
		
		try {
			order = query.getSingleResult();
		}catch(Exception ex) {
			System.out.println("Error in Order Dao "+ex.getMessage());
		}
		
		return  order;
	}
	
	public List<Order> getSelfOrders(Long pool_id,Long store_id ,Integer num_of_orders){
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from( Order.class );
		criteriaQuery.select(root);
		criteriaQuery.orderBy(builder.asc(root.get("created_on")));
		
		criteriaQuery.where(
				                           builder.and(
							                        		   builder.equal(
						                        		                root.get( "pool_id" ), pool_id
						                        		              ),
							                        		   builder.equal(
						                        		                root.get( "store_id" ), store_id
						                        		              ),
							                        		   builder.equal(
						                        		                root.get( "status" ), "placed"
						                        		              ),
							                        		   builder.equal(root.get( "type_of_pickup" ), "other")
				                        		              )
				                         );
		
		TypedQuery<Order> query = entityManager.createQuery(criteriaQuery); 
		query.setMaxResults(num_of_orders);
		List<Order> orders= null;
		
		try{
			orders = query.getResultList();
		}catch(Exception ex) {
			System.out.println("Error in Order Dao Impl "+ex.getMessage());
		}

		
		return orders;
		
	}

	@Override
	public Boolean isOrderIdExists(Long id) {
		
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Order> root = criteriaQuery.from( Order.class );
		criteriaQuery.select(builder.count(root));
		
		criteriaQuery.where(
				                           builder.equal(
				                                                 root.get( "id" ),id
				                                                )
				                       );
		
		
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery); 
		Long count = (Long) query.getSingleResult();
		
		if(count>0)
			  return true;
		
		return false;
		
	}

	@Override
	public List<Order> getMyAllOrders(Long user_id) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from( Order.class );
		criteriaQuery.select(root);
		criteriaQuery.orderBy(builder.asc(root.get("created_on")));
		
		criteriaQuery.where(
				                           builder.and(
							                        		   builder.equal(
						                        		                root.get( "user_id" ), user_id
						                        		              )
				                        		              )
				                         );
		
		TypedQuery<Order> query = entityManager.createQuery(criteriaQuery); 
		List<Order> orders= null;
		
		try{
			orders = query.getResultList();
		}catch(Exception ex) {
			System.out.println("Error in Order Dao Impl -Get My All Orders "+ex.getMessage());
		}

		
		return orders;
	}

	@Override
	public List<Order> getOrdersForPickup(Long user_id) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from( Order.class );
		criteriaQuery.select(root);
		criteriaQuery.orderBy(builder.asc(root.get("created_on")));
		
		criteriaQuery.where(
				                           builder.and(
							                        		   builder.equal(
						                        		                root.get( "picker_user_id" ), user_id
						                        		              ),
							                        		   builder.equal(
						                        		                root.get( "status" ), "picker_assigned"
						                        		              )
				                        		              )
				                         );
		
		TypedQuery<Order> query = entityManager.createQuery(criteriaQuery); 
		List<Order> orders= null;
		
		try{
			orders = query.getResultList();
		}catch(Exception ex) {
			System.out.println("Error in Order Dao Impl -Orders for Pickup"+ex.getMessage());
		}

		
		return orders;
	}
	
	@Override
	public List<Order> getOrdersForDelivery(Long user_id) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from( Order.class );
		criteriaQuery.select(root);
		criteriaQuery.orderBy(builder.asc(root.get("created_on")));
		
		criteriaQuery.where(
				                           builder.and(
							                        		   builder.equal(
						                        		                root.get( "picker_user_id" ), user_id
						                        		              ),
							                        		   builder.equal(
						                        		                root.get( "status" ), "picked"
						                        		              )
				                        		              )
				                         );
		
		TypedQuery<Order> query = entityManager.createQuery(criteriaQuery); 
		List<Order> orders= null;
		
		try{
			orders = query.getResultList();
		}catch(Exception ex) {
			System.out.println("Error in Order Dao Impl -Orders Picked"+ex.getMessage());
		}

		
		return orders;
	}

	@Override
	public List<Order> getAvailableOrdersForAssignment(Long pool_id,Long store_id) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from( Order.class );
		criteriaQuery.select(root);
		criteriaQuery.orderBy(builder.asc(root.get("created_on")));
		
		criteriaQuery.where(
				                           builder.and(
							                        		   builder.equal(
						                        		                root.get( "pool_id" ), pool_id
						                        		              ),
							                        		   builder.equal(
						                        		                root.get( "store_id" ), store_id
						                        		              ),
							                        		   builder.equal(
						                        		                root.get( "status" ), "placed"
						                        		              ),
							                        		   builder.equal(root.get( "type_of_pickup" ), "other")
				                        		              )
				                         );
		
		TypedQuery<Order> query = entityManager.createQuery(criteriaQuery); 
		
		List<Order> orders= null;
		
		try{
			orders = query.getResultList();
		}catch(Exception ex) {
			System.out.println("Error in Order Dao Impl Available Orders For Assignment"+ex.getMessage());
		}

		
		return orders;
	}
	

}
