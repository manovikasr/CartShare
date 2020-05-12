package com.cmpe275project.service;

import java.util.List;

import com.cmpe275project.model.Product;
import com.cmpe275project.model.Store;
import com.cmpe275project.model.StoreProduct;

public interface StoreService {

    public Boolean chkStoreNameExists(String store_name);
	
	public Boolean isStoreNameAvailable(String store_name,Long id);
	
	public Boolean isStoreIdExists(Long id);
	
	public Long add(Store store);
	
	public void edit(Store store);
	
	public void delete(Store store);
	
	public Store getStoreInfoById(Long id);

	public List<StoreProduct> getStoreProductList(Long store_id);
	
	public Boolean chkStoreProductExists(Long store_id,Long product_id);
	
	public void addAllStoreProducts(List<StoreProduct> storeProductsList);
	
	public void deleteAllStoreProducts(List<StoreProduct> existingStoreProducts);
	
	public List<Store> getAllStores();
	
	public void deleteStoreAndProduct(Store store,Product product);
}
