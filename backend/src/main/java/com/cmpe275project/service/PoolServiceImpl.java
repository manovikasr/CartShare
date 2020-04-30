package com.cmpe275project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275project.dao.PoolDao;
import com.cmpe275project.dao.UserDao;
import com.cmpe275project.model.Pool;
import com.cmpe275project.model.PoolRequest;

@Service
@Transactional
public class PoolServiceImpl implements PoolService{

	@Autowired PoolDao poolDao;
	
	@Autowired UserDao userDao;
	
	@Override
	@Transactional
	public void createPool(Pool pool) {
		poolDao.createPool(pool);	
	}

	@Override
	@Transactional
	public void joinPool(Long userid, Long poolid) {
		poolDao.joinPool(poolid,userid);
	}

	@Override
	public boolean checkPoolIDExists(String pool_id) {
		return poolDao.checkPoolIDExists(pool_id);
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

	@Override
	public List<Pool> getAllPools() {
		
		return poolDao.getAllPools();
	}

	@Override
	public void addPoolRequest(Long userid,String user_screenname,Long poolid, String refname, boolean b) {
		poolDao.addPoolRequest(userid,user_screenname,poolid,refname,b);
		
	}

	@Override
	public List<PoolRequest> getApplicationsByRefName(String refname) {
		return poolDao.getApplicationsByRefName(refname);
	}

	@Override
	public void leavePool(Long userid, Long poolid) {
		
		poolDao.leavePool(userid,poolid);
	}

	@Override
	public Long supportPoolRequest(Long applicationid) {
		
		return poolDao.supportPoolRequest(applicationid);
	}

	@Override
	public Long getPoolLeaderId(Long poolid) {
		
		return poolDao.getPoolLeaderId(poolid);
	}
	@Override
	public List<PoolRequest> getAllSupportedApplicationsByPoolId(Long poolid) {
		
		return poolDao.getAllSupportedApplicationsByPoolId(poolid);
	}
	
	@Override
	public void deletePool(Long poolid) {
		
		poolDao.deletePool(poolid);
		
	}
}
