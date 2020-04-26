package com.cmpe275project.dao;

import java.util.List;

import com.cmpe275project.model.Store;
import com.cmpe275project.model.StoreProduct;

public interface StoreDao {

	public Boolean chkStoreNameExists(String store_name);
	
	public Boolean isStoreNameAvailable(String store_name,Long id);
	
	public Boolean isStoreIdExists(Long id);
	
	public Long add(Store store);
	
	public void edit(Store store);
	
	public void delete(Store store);
	
	public Store getStoreInfoById(Long id);
	
	public List<StoreProduct> getStoreProductList(Long store_id);
	
	public Boolean chkStoreProductExists(Long store_id,Long product_id);
	
	public void addStoreProduct(StoreProduct storeProduct);
	
	public void deleteStoreProduct(StoreProduct storeProduct);
}
