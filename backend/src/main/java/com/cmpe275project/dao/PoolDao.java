package com.cmpe275project.dao;

import java.util.List;

import com.cmpe275project.model.Pool;
import com.cmpe275project.model.PoolRequest;

public interface PoolDao {

	public void createPool(Pool p);

	public void joinPool(long poolid, long userid);

	public boolean checkPoolNameExists(String poolname);

	public boolean isPoolIdExists(Long poolid);

	public int countMembers(Long poolid);

	public List<Pool> getAllPools();

	public void addPoolRequest(Long userid, String user_screenname, Long poolid, String refname, boolean b);

	public List<PoolRequest> getApplicationsByRefName(String refname);

	public void leavePool(Long userid, Long poolid);

	public Long supportPoolRequest(Long applicationid);

	public Long getPoolLeaderId(Long poolid);

	public List<PoolRequest> getAllSupportedApplicationsByPoolId(Long poolid);

	public void deletePool(Long poolid);
}
