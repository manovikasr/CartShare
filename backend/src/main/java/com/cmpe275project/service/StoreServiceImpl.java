package com.cmpe275project.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275project.dao.StoreDao;
import com.cmpe275project.model.Store;


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
	

}
