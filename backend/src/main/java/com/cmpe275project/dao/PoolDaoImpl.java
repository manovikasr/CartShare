package com.cmpe275project.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.cmpe275project.model.Pool;
import com.cmpe275project.model.PoolRequest;
import com.cmpe275project.model.Store;
import com.cmpe275project.model.User;

@Repository
public class PoolDaoImpl implements PoolDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void createPool(Pool p) {
		User user = entityManager.find(User.class,p.getPool_leader_id());
		user.setPool(p);
		entityManager.unwrap(Session.class).update(user);
	}

	@Override
	public void joinPool(long poolid, long userid) {
		
		User user = entityManager.find(User.class,userid);
		Pool p = entityManager.find(Pool.class,poolid);
		
		user.setPool(p);
		entityManager.unwrap(Session.class).update(user);
		
	}
	
	@Override
	public boolean checkPoolIDExists(String pool_id) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Pool> root = criteriaQuery.from( Pool.class );
		criteriaQuery.select(builder.count(root));
		
		criteriaQuery.where(
				                           builder.equal(
				                                                 root.get( "pool_id" ), pool_id
				                                                )
				                       );
		
		
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery); 
		Long count = (Long) query.getSingleResult();
		
		if(count>0)
			  return true;
		
		return false;
	}

	@Override
	public boolean checkPoolNameExists(String pool_name) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Pool> root = criteriaQuery.from( Pool.class );
		criteriaQuery.select(builder.count(root));
		
		criteriaQuery.where(
				                           builder.equal(
				                                                 root.get( "pool_name" ),pool_name
				                                                )
				                       );
		
		
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery); 
		Long count = (Long) query.getSingleResult();
		
		if(count>0)
			  return true;
		
		return false;
	}
	
	@Override
	public boolean checkPoolNameAvailable(String pool_name, Long pool_id) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Pool> root = criteriaQuery.from( Pool.class );
		criteriaQuery.select(builder.count(root));
		
		criteriaQuery.where(
				                           builder.equal(
				                                                 root.get( "pool_name" ),pool_name
				                                                ),
				                           builder.and(
				                        		   builder.notEqual(root.get("id"), pool_id)
				                        		   )
				                           
				                       );
		
		
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery); 
		Long count = (Long) query.getSingleResult();
		
		if(count>0)
			  return true;
		
		return false;
		
	}

	@Override
	public boolean isPoolIdExists(Long poolid) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Pool> root = criteriaQuery.from( Pool.class );
		criteriaQuery.select(builder.count(root));
		
		criteriaQuery.where(
				                           builder.equal(
				                                                 root.get( "id" ),poolid
				                                                )
				                       );
		
		
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery); 
		Long count = (Long) query.getSingleResult();
		
		if(count>0)
			  return true;
		
		return false;
	}

	@Override
	public int countMembers(Long poolid) {
		Pool p = entityManager.find(Pool.class,poolid);
		List<User> poolers = p.getUser();
		return poolers.size();
	}

	@Override
	public List<Pool> getAllPools() {
		CriteriaQuery<Pool> criteria = entityManager.getCriteriaBuilder().createQuery(Pool.class);
	    criteria.select(criteria.from(Pool.class));
	    List<Pool> listPools = entityManager.createQuery(criteria).getResultList();
	    return listPools;
	}
	
	@Override
	public Pool getPoolInfoById(Long id) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pool> criteriaQuery = builder.createQuery(Pool.class);
		Root<Pool> root = criteriaQuery.from( Pool.class);
		criteriaQuery.select(root);
		criteriaQuery.where(builder.equal(root.get( "id" ),id));
		TypedQuery<Pool> query = entityManager.createQuery(criteriaQuery);
		Pool pool = null;
		
		try {
			pool = query.getSingleResult();
		}catch(Exception ex) {
			System.out.println("Error in Pool Dao "+ex.getMessage());
		}
		
		return pool;
	}

	@Override
	public void addPoolRequest(Long userid, String user_screenname, Long poolid, String refname, boolean b) {
		
		PoolRequest poolreq = new PoolRequest();
		poolreq.setRequserid(userid);
		poolreq.setRequserscreenname(user_screenname);
		poolreq.setReqpoolid(poolid);
		poolreq.setRefusername(refname);
		poolreq.setRefsupportstatus(b);
		
		entityManager.unwrap(Session.class).save(poolreq);
	}
	
	@Override
	public List<PoolRequest> getUserApplications(Long user_id) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PoolRequest> criteriaQuery = builder.createQuery(PoolRequest.class);
		
		Root<PoolRequest> root = criteriaQuery.from( PoolRequest.class );

	    criteriaQuery.where(
                builder.equal(
                                      root.get( "requserid" ),user_id
                                     )
            ).distinct(true);
	    List<PoolRequest> listPoolRequests = entityManager.createQuery(criteriaQuery).getResultList();
	    
	    return listPoolRequests;
	}

	@Override
	public List<PoolRequest> getApplicationsByRefName(String ref_name, Long pool_id) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PoolRequest> criteriaQuery = builder.createQuery(PoolRequest.class);
		
		Root<PoolRequest> root = criteriaQuery.from( PoolRequest.class );

	    criteriaQuery.where(
                builder.equal(
                                      root.get( "refusername" ),ref_name
                                     ),
                builder.and(
      		             builder.equal(root.get( "reqpoolid" ), pool_id)
      		             )
            ).distinct(true);
	    List<PoolRequest> listPoolRequests = entityManager.createQuery(criteriaQuery).getResultList();
	    
	    return listPoolRequests;
	}

	@Override
	public void leavePool(Long userid, Long poolid) {
		User user = entityManager.find(User.class,userid);
		//Pool p = entityManager.find(Pool.class,poolid);
		user.setPool(null);
		entityManager.unwrap(Session.class).update(user);
		
	}

	@Override
	public Long supportPoolRequest(Long applicationid) {
		
		PoolRequest request = entityManager.find(PoolRequest.class, applicationid);
		Long poolid = request.getReqpoolid();
		request.setRefsupportstatus(true);
		entityManager.unwrap(Session.class).update(request);
		
		return poolid; 
	}

	@Override
	public Long getPoolLeaderId(Long poolid) {
		Pool pool = entityManager.find(Pool.class, poolid);
		return pool.getPool_leader_id();
	}

	@Override
	public List<PoolRequest> getAllSupportedApplicationsByPoolId(Long poolid) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PoolRequest> criteriaQuery = builder.createQuery(PoolRequest.class);
	//	criteriaQuery.select(criteriaQuery.from(PoolRequest.class));
		Root<PoolRequest> root = criteriaQuery.from( PoolRequest.class );
	    criteriaQuery.where(
                builder.equal(
                                      root.get( "reqpoolid" ),poolid
                                     ),
                builder.and(
   		             builder.equal(root.get( "refsupportstatus" ), true)
   		             )
            ).distinct(true);
	    List<PoolRequest> listPoolRequests = entityManager.createQuery(criteriaQuery).getResultList();
	    return listPoolRequests;
	}
	
	@Override
	public void editPool(Pool pool) {
		entityManager.unwrap(Session.class).update(pool);
	}

	@Override
	public void deletePool(Long poolid) {
		Pool pool = entityManager.find(Pool.class, poolid);
		entityManager.unwrap(Session.class).delete(pool);
	}

	@Override
	public void removePoolRequest(Long appid) {
		PoolRequest poolReq = entityManager.find(PoolRequest.class, appid);
		entityManager.unwrap(Session.class).delete(poolReq);
	}

	@Override
	public PoolRequest getApplicationInfo(Long applicationid) {
		PoolRequest poolReq = entityManager.find(PoolRequest.class, applicationid);
		return poolReq;
	}

	@Override
	public boolean hasUserAlreadyAppliedToSamePool(Long user_id, Long pool_id) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PoolRequest> criteriaQuery = builder.createQuery(PoolRequest.class);
	//	criteriaQuery.select(criteriaQuery.from(PoolRequest.class));
		Root<PoolRequest> root = criteriaQuery.from( PoolRequest.class );
	    criteriaQuery.where(
               builder.and(
            		   builder.equal(
            				   root.get( "requserid" ),user_id
                      ), 
            		   builder.equal(
                               root.get( "reqpoolid" ),pool_id
                              )
            		   )
                
            );
	    List<PoolRequest> listPoolRequests = entityManager.createQuery(criteriaQuery).getResultList();
	    if(listPoolRequests.size()>0)
	    	return true;
	    else
	    	return false;
	}

}
