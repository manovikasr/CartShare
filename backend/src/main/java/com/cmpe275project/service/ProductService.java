package com.cmpe275project.service;

import com.cmpe275project.model.Product;

public interface ProductService {

public Boolean chkProductNameExists(String product_name);
	
	public Boolean chkSKUExists(String sku);
	
	public Boolean isProductNameAvailable(String product_name,Long id);
	
	public Boolean isSKUAvailable(String sku,Long id);
	
	public Boolean isProductIdExists(Long id);
	
	public Long add(Product product);
	
	public void edit(Product product);
	
	public void delete(Product product);
	
	public Product getProductInfoById(Long id);
	
}
