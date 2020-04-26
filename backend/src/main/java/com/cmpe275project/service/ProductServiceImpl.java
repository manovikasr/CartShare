package com.cmpe275project.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275project.dao.ProductDao;
import com.cmpe275project.model.Product;


@Service
@Transactional
public class ProductServiceImpl implements ProductService{

	@Autowired 
	private ProductDao productDao;

	@Override
	public Boolean chkProductNameExists(String product_name) {
		// TODO Auto-generated method stub
		return productDao.chkProductNameExists(product_name);
	}

	@Override
	public Boolean chkSKUExists(String sku) {
		// TODO Auto-generated method stub
		return productDao.chkSKUExists(sku);
	}

	@Override
	public Boolean isProductNameAvailable(String product_name, Long id) {
		// TODO Auto-generated method stub
		return productDao.isProductNameAvailable(product_name, id);
	}

	@Override
	public Boolean isSKUAvailable(String sku, Long id) {
		// TODO Auto-generated method stub
		return productDao.isSKUAvailable(sku, id);
	}

	@Override
	public Boolean isProductIdExists(Long id) {
		// TODO Auto-generated method stub
		return productDao.isProductIdExists(id);
	}

	@Override
	public Long add(Product product) {
		// TODO Auto-generated method stub
		return productDao.add(product);
	}

	@Override
	public void edit(Product product) {
		// TODO Auto-generated method stub
		productDao.edit(product);
	}

	@Override
	public void delete(Product product) {
		// TODO Auto-generated method stub
		productDao.delete(product);
	}

	@Override
	public Product getProductInfoById(Long id) {
		// TODO Auto-generated method stub
		return productDao.getProductInfoById(id);
	}

	
}
