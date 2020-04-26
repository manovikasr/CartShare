package com.cmpe275project.dao;

import com.cmpe275project.model.Store;

public interface StoreDao {

	public Boolean chkStoreNameExists(String store_name);
	
	public Boolean isStoreNameAvailable(String store_name,Long id);
	
	public Boolean isStoreIdExists(Long id);
	
	public Long add(Store store);
	
	public void edit(Store store);
	
	public void delete(Store store);
	
	public Store getStoreInfoById(Long id);
	
}
