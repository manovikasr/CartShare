package com.cmpe275project.dao;

import com.cmpe275project.model.Pool;

public interface PoolDao {

	public void createPool(Pool p);

	public void joinPool(long poolid, long userid);

	public boolean checkPoolNameExists(String poolname);

	public boolean isPoolIdExists(Long poolid);

	public int countMembers(Long poolid);
}
