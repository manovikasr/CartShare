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
import com.cmpe275project.model.User;

@Repository
public class PoolDaoImpl implements PoolDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public void createPool(Pool p) {
		User user = entityManager.find(User.class,p.getPoolleaderid());
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
	public boolean checkPoolNameExists(String poolname) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Pool> root = criteriaQuery.from( Pool.class );
		criteriaQuery.select(builder.count(root));
		
		criteriaQuery.where(
				                           builder.equal(
				                                                 root.get( "poolname" ),poolname
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

}
