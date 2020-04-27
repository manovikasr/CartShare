package com.cmpe275project.service;

import javax.validation.Valid;

import com.cmpe275project.model.Pool;
import com.cmpe275project.requestObjects.PoolRequest;

public interface PoolService {

	public void createPool(@Valid Pool poolRequest);

	public boolean checkPoolNameExists(String poolname);

	public void joinPool(Long userid, Long poolid);

	public boolean isPoolIdExist(Long poolid);

	public int countMembers(Long poolid);
}
