package com.cmpe275project.dao;

import java.util.List;

import com.cmpe275project.model.Pool;
import com.cmpe275project.model.PoolRequest;

public interface PoolDao {

	public void createPool(Pool p);

	public void joinPool(long poolid, long userid);
	
	public boolean checkPoolIDExists(String pool_id);

	public boolean checkPoolNameExists(String poolname);
	
	public boolean checkPoolNameAvailable(String poolname, Long poolid);

	public boolean isPoolIdExists(Long poolid);

	public int countMembers(Long poolid);

	public List<Pool> getAllPools();
	
	public Pool getPoolInfoById(Long poolid);

	public void addPoolRequest(Long userid, String user_screenname, Long poolid, String refname, boolean b);
	
	public List<PoolRequest> getUserApplications(Long user_id);

	public List<PoolRequest> getApplicationsByRefName(String ref_name, Long pool_id);

	public void leavePool(Long userid, Long poolid);

	public Long supportPoolRequest(Long applicationid);

	public Long getPoolLeaderId(Long poolid);

	public List<PoolRequest> getAllSupportedApplicationsByPoolId(Long poolid);

	public void editPool(Pool pool);
	
	public void deletePool(Long poolid);

	public void removePoolRequest(Long appid);

	public PoolRequest getApplicationInfo(Long applicationid);
}
