package com.cmpe275project.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.cmpe275project.model.Store;

@Repository
public class InventoryDaoImpl implements InventoryDao{

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public void createStore(Store store) {
		
		entityManager.unwrap(Session.class).save(store);
	}

	
}
