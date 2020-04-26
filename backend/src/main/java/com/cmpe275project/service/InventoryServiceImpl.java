package com.cmpe275project.service;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275project.dao.InventoryDao;
import com.cmpe275project.model.Product;
import com.cmpe275project.model.Store;
import com.cmpe275project.requestObjects.StoreRequest;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService{

	@Autowired
	InventoryDao inventoryDao;
	
	@Override
	public void createStore(StoreRequest storeReq) {
		Store store = new Store();
		store.setAddress("Alameda");
		store.setCity("San Jose");
		store.setState("CA");
		store.setStore_name("SAFEWAY");
		store.setZip(0);
		
		/*Set<Product> set = new HashSet<Product>();
		Product p1 = new Product();
		p1.setDesc("food");
		p1.setPrice(23);
		p1.setProductbrand("d");
		p1.setProductname("milk");
		p1.setSKU("345");
		p1.setUnittype("single");
		p1.setProductimg("jpg");
		//p1.setStores(stores);
		set.add(p1);
		store.setProducts(set);
		inventoryDao.createStore(store);*/
	}
}
