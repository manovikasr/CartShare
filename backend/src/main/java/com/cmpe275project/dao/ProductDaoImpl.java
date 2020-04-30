package com.cmpe275project.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.cmpe275project.model.Product;

@Repository
public class ProductDaoImpl implements ProductDao{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Boolean chkProductNameExists(String product_name) {
		
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Product> root = criteriaQuery.from( Product.class );
		criteriaQuery.select(builder.count(root));
		
		criteriaQuery.where(
				                           builder.equal(
				                                                 root.get( "product_name" ),product_name
				                                                )
				                       );
		
		
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery); 
		Long count = (Long) query.getSingleResult();
		
		if(count>0)
			  return true;
		
		return false;
		
	}
	
	@Override
	public Boolean chkSKUExists(String sku) {
		
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Product> root = criteriaQuery.from( Product.class );
		criteriaQuery.select(builder.count(root));
		
		criteriaQuery.where(
				                           builder.equal(
				                                                 root.get( "sku" ),sku
				                                                )
				                       );
		
		
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery); 
		Long count = (Long) query.getSingleResult();
		
		if(count>0)
			  return true;
		
		return false;
		
	}
	
	@Override
	public Boolean isSKUAvailable(String sku, Long id) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Product> root = criteriaQuery.from( Product.class );
		criteriaQuery.select(builder.count(root));
		
		criteriaQuery.where(
				                         builder.equal(
				                        		                root.get( "sku" ), sku
				                        		              ),
				                         builder.and(
				                        		             builder.notEqual(root.get( "id" ), id)
				                        		             )
				                         );
		
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery); 
		Long count = (Long) query.getSingleResult();
		
		if(count>0)
			  return true;
		
		return false;

	}
	
	@Override
	public Boolean isProductNameAvailable(String product_name, Long id) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Product> root = criteriaQuery.from( Product.class );
		criteriaQuery.select(builder.count(root));
		
		criteriaQuery.where(
				                         builder.equal(
				                        		                root.get( "product_name" ), product_name
				                        		              ),
				                         builder.and(
				                        		             builder.notEqual(root.get( "id" ), id)
				                        		             )
				                         );
		
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery); 
		Long count = (Long) query.getSingleResult();
		
		if(count>0)
			  return true;
		
		return false;

	}
	
	@Override
	public Boolean isProductIdExists(Long id) {
		
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Product> root = criteriaQuery.from( Product.class );
		criteriaQuery.select(builder.count(root));
		
		criteriaQuery.where(
				                           builder.equal(
				                                                 root.get( "id" ),id
				                                                )
				                       );
		
		
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery); 
		long count = 0;
		
		try {
			count = (Long) query.getSingleResult();	
		}catch(Exception ex){
			System.out.println("Error in Product Dao -isProductIdExists "+ex.getMessage());
		}
		
		if(count>0)
			  return true;
		
		return false;
		
	}
		
	@Override
	public Long add(Product product) {
		Long id = (Long) entityManager.unwrap(Session.class).save(product);
		
		return id;
	}

	@Override
	public void edit(Product product) {
		entityManager.unwrap(Session.class).update(product);
	}
	
	@Override
	public void delete(Product product) {
		entityManager.unwrap(Session.class).delete(product);
	}
	
	@Override
	public Product getProductInfoById(Long id) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = builder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from( Product.class);
		criteriaQuery.select(root);
		criteriaQuery.where(builder.equal(root.get( "id" ),id));
		TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);
		Product store = null;
		
		try {
			store = query.getSingleResult();
		}catch(Exception ex) {
			System.out.println("Error in User Dao "+ex.getMessage());
		}
		
		return  store;
		
	}

}
