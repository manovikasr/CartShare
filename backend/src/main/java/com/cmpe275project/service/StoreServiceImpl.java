package com.cmpe275project.service;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275project.dao.StoreDao;
import com.cmpe275project.model.Product;
import com.cmpe275project.model.Store;
import com.cmpe275project.model.StoreProduct;


@Service
@Transactional
public class StoreServiceImpl implements StoreService{

	@Autowired 
	private StoreDao storeDao;
	
	@Override
	public Boolean chkStoreNameExists(String store_name) {
		// TODO Auto-generated method stub
		return storeDao.chkStoreNameExists(store_name);
	}

	@Override
	public Boolean isStoreNameAvailable(String store_name, Long id) {
		// TODO Auto-generated method stub
		return storeDao.isStoreNameAvailable(store_name, id);
	}

	public Boolean isStoreIdExists(Long id) {
		return storeDao.isStoreIdExists(id);
	}
	
	@Override
	public Long add(Store store) {
		// TODO Auto-generated method stub
		return storeDao.add(store);
	}

	@Override
	public void edit(Store store) {
		// TODO Auto-generated method stub
		storeDao.edit(store);
	}

	@Override
	public void delete(Store store) {
		// TODO Auto-generated method stub
		storeDao.delete(store);
	}

	@Override
	public Store getStoreInfoById(Long id) {
		// TODO Auto-generated method stub
		return storeDao.getStoreInfoById(id);
	}
	
	@Override
	public List<StoreProduct> getStoreProductList(Long store_id){
		return storeDao.getStoreProductList(store_id);
	}
	
	public Boolean chkStoreProductExists(Long store_id,Long product_id) {
		return storeDao.chkStoreProductExists(store_id, product_id);
	}
	
	public void addAllStoreProducts(List<StoreProduct> storeProductsList) {
		
		for(StoreProduct storeProduct:storeProductsList) {
			
			storeDao.addStoreProduct(storeProduct);
		
		}
		
	}

	public void deleteAllStoreProducts(List<StoreProduct> existingStoreProducts) {

		for(StoreProduct existingStoreProduct:existingStoreProducts) {
			storeDao.deleteStoreProduct(existingStoreProduct);
		}
		
	}

	@Override
	public List<Store> getAllStores() {
		// TODO Auto-generated method stub
		return storeDao.getAllStores();
	}
	
}
