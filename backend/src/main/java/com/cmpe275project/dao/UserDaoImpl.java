package com.cmpe275project.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.cmpe275project.model.User;


@Repository
public class UserDaoImpl implements UserDao{

	@PersistenceContext
    private EntityManager entityManager;

//	@Override
//	public Boolean registerUser(Long playerId, Long opponentId){
//		
//        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//
//		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
//		Root<Opponent> root = criteriaQuery.from( Opponent.class );
//		criteriaQuery.select(builder.count(root));
//		
//		criteriaQuery.where(
//				                           builder.equal(
//				                                                 root.get( "player_id" ),playerId
//				                                                ),
//				                           builder.equal(
//	                                                 root.get( "opponent_id" ),opponentId
//	                                                )
//				                       );
//		
//		
//		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery); 
//		Long count = (Long) query.getSingleResult();
//		
//		if(count>0)
//			  return true;
//		
//		return false;
//		
//	}

	@Override
	public void registerUser(User user) {
		entityManager.unwrap(Session.class).save(user);
	}
}
