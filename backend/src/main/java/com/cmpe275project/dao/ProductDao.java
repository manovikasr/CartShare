package com.cmpe275project.dao;

import com.cmpe275project.model.Product;

public interface ProductDao {

	public Boolean chkProductNameExists(String product_name);
	
	public Boolean chkSkuAndStoreIdExists(String sku, Long store_id);
	
	public Boolean chkSKUExists(String sku);
	
	public Boolean isProductNameAvailable(String product_name,Long id);
	
	public Boolean isSkuAndStoreIdAvailable(String sku,Long store_id, Long product_id);
	
	public Boolean isProductIdExists(Long id);
	
	public Long add(Product product);
	
	public void edit(Product product);
	
	public void delete(Product product);
	
	public Product getProductInfoById(Long id);
	
}
