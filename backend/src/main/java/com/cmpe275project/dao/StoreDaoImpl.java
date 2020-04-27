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

import com.cmpe275project.model.Store;
import com.cmpe275project.model.StoreProduct;

@Repository
public class StoreDaoImpl implements StoreDao{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Boolean chkStoreNameExists(String store_name) {
		
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Store> root = criteriaQuery.from( Store.class );
		criteriaQuery.select(builder.count(root));
		
		criteriaQuery.where(
				                           builder.equal(
				                                                 root.get( "store_name" ),store_name
				                                                )
				                       );
		
		
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery); 
		Long count = (Long) query.getSingleResult();
		
		if(count>0)
			  return true;
		
		return false;
		
	}
	
	@Override
	public Boolean isStoreNameAvailable(String store_name, Long id) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Store> root = criteriaQuery.from( Store.class );
		criteriaQuery.select(builder.count(root));
		
		criteriaQuery.where(
				                         builder.equal(
				                        		                root.get( "store_name" ), store_name
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
	public Boolean isStoreIdExists(Long id) {
		
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Store> root = criteriaQuery.from( Store.class );
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
	public Long add(Store store) {
		Long id = (Long) entityManager.unwrap(Session.class).save(store);
		
		return id;
	}

	@Override
	public void edit(Store store) {
		entityManager.unwrap(Session.class).update(store);
	}
	
	@Override
	public void delete(Store store) {
		entityManager.unwrap(Session.class).delete(store);
	}
	
	@Override
	public Store getStoreInfoById(Long id) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Store> criteriaQuery = builder.createQuery(Store.class);
		Root<Store> root = criteriaQuery.from( Store.class);
		criteriaQuery.select(root);
		criteriaQuery.where(builder.equal(root.get( "id" ),id));
		TypedQuery<Store> query = entityManager.createQuery(criteriaQuery);
		Store store = null;
		
		try {
			store = query.getSingleResult();
		}catch(Exception ex) {
			System.out.println("Error in Store Dao "+ex.getMessage());
		}
		
		return  store;
		
	}

	@Override
	public List<StoreProduct> getStoreProductList(Long store_id){
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<StoreProduct> criteriaQuery = builder.createQuery(StoreProduct.class);
		Root<StoreProduct> root = criteriaQuery.from( StoreProduct.class);
		criteriaQuery.select(root);
		criteriaQuery.where(builder.equal(root.get( "store_id" ),store_id));
		TypedQuery<StoreProduct> query = entityManager.createQuery(criteriaQuery);
		List<StoreProduct> storeProducts = null;
		
		try {
			storeProducts = query.getResultList();
		}catch(Exception ex) {
			System.out.println("Error in Store Dao "+ex.getMessage());
		}
		
		return  storeProducts;
		
	}
	
	@Override
	public Boolean chkStoreProductExists(Long store_id,Long product_id) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<StoreProduct> root = criteriaQuery.from( StoreProduct.class );
		criteriaQuery.select(builder.count(root));
		
		criteriaQuery.where(
				                           builder.equal(
				                                                 root.get( "store_id" ),store_id
				                                                ),
				                           builder.and(
		                        		                       builder.equal(root.get( "product_id" ), product_id)
		                        		             )
				                       );
		
		
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery); 
		Long count = (Long) query.getSingleResult();
		
		if(count>0)
			  return true;
		
		return false;
	
	}
	
	public void addStoreProduct(StoreProduct storeProduct) {
		entityManager.unwrap(Session.class).save(storeProduct);
	}
	
	public void deleteStoreProduct(StoreProduct storeProduct) {
		entityManager.unwrap(Session.class).delete(storeProduct);
	}
	
	@Override
	public List<Store> getAllStores() {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Store> criteriaQuery = builder.createQuery(Store.class);
		Root<Store> root = criteriaQuery.from( Store.class);
		criteriaQuery.select(root);
		TypedQuery<Store> query = entityManager.createQuery(criteriaQuery);
		List<Store> stores = null;
		
		try {
			stores = query.getResultList();
		}catch(Exception ex) {
			System.out.println("Error in Store Dao List of Stores "+ex.getMessage());
		}
		
		return  stores;
		
	}
	
}
