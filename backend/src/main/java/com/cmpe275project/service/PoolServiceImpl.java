package com.cmpe275project.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275project.dao.PoolDao;
import com.cmpe275project.dao.UserDao;
import com.cmpe275project.model.Pool;
import com.cmpe275project.requestObjects.PoolRequest;

@Service
@Transactional
public class PoolServiceImpl implements PoolService{

	@Autowired PoolDao poolDao;
	
	@Autowired UserDao userDao;
	
	@Override
	@Transactional
	public void createPool(Pool pool) {
		
		/*
		 * Pool pool1 = new Pool(); pool1.setNeighbourhoodname("alameda");
		 * pool1.setPooldesc("garden"); pool1.setPoolleaderid(5);
		 * pool1.setPoolname("testpool"); pool1.setPoolrating("5star");
		 * pool1.setPoolzip(12);
		 * 
		 */
		
		poolDao.createPool(pool);
		
	}
	@Override
	@Transactional
	public void joinPool(Long userid, Long poolid) {
		poolDao.joinPool(poolid,userid);
	}
	@Override
	public boolean checkPoolNameExists(String poolname) {
		return poolDao.checkPoolNameExists(poolname);
	}
	@Override
	public boolean isPoolIdExist(Long poolid) {
		return poolDao.isPoolIdExists(poolid);
	}
	@Override
	public int countMembers(Long poolid) {
		
		return poolDao.countMembers(poolid);
	}
}
