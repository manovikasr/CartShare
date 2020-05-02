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
	public boolean checkPoolNameAvailable(String poolname, Long poolid) {
		return poolDao.checkPoolNameAvailable(poolname, poolid);
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
	public Pool getPoolInfoById(Long poolid) {
		return poolDao.getPoolInfoById(poolid);
	}

	@Override
	public void addPoolRequest(Long userid,String user_screenname,Long poolid, String refname, boolean b) {
		poolDao.addPoolRequest(userid,user_screenname,poolid,refname,b);
		
	}
	
	@Override
	public List<PoolRequest> getUserApplications(Long user_id) {
		return poolDao.getUserApplications(user_id);
	}

	@Override
	public List<PoolRequest> getApplicationsByRefName(String ref_name, Long pool_id) {
		return poolDao.getApplicationsByRefName(ref_name, pool_id);
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
	public void editPool(Pool pool) {
		poolDao.editPool(pool);
	}
	
	@Override
	public void deletePool(Long poolid) {
		
		poolDao.deletePool(poolid);
		
	}

	@Override
	public void removePoolRequest(Long appid) {
		
		poolDao.removePoolRequest(appid);
	}

	@Override
	public PoolRequest getApplicationInfo(Long applicationid) {
		// TODO Auto-generated method stub
		return poolDao.getApplicationInfo(applicationid);
	}
}
