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

	@Override
	public Boolean isEmailExists(String email) {
		
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<User> root = criteriaQuery.from( User.class );
		criteriaQuery.select(builder.count(root));
		
		criteriaQuery.where(
				                           builder.equal(
				                                                 root.get( "email" ),email
				                                                )
				                       );
		
		
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery); 
		Long count = (Long) query.getSingleResult();
		
		if(count>0)
			  return true;
		
		return false;
		
	}
	
	@Override
	public Boolean isNickNameExists(String nick_name) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<User> root = criteriaQuery.from( User.class );
		criteriaQuery.select(builder.count(root));
		
		criteriaQuery.where(
				                           builder.equal(
				                                                 root.get( "nick_name" ),nick_name
				                                                )
				                       );
		
		
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery); 
		Long count = (Long) query.getSingleResult();
		
		if(count>0)
			  return true;
		
		return false;
	}

	@Override
	public Boolean isScreenNameExists(String screen_name) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<User> root = criteriaQuery.from( User.class );
		criteriaQuery.select(builder.count(root));
		
		criteriaQuery.where(
				                           builder.equal(
				                                                 root.get( "screen_name" ),screen_name
				                                                )
				                       );
		
		
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery); 
		Long count = (Long) query.getSingleResult();
		
		if(count>0)
			  return true;
		
		return false;
	}
	
	@Override
	public Boolean isUserIdExists(Long id) {
		
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<User> root = criteriaQuery.from( User.class );
		criteriaQuery.select(builder.count(root));
		
		criteriaQuery.where(
				                           builder.equal(
				                                                 root.get( "id" ),id
				                                                )
				                       );
		
		
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery); 
		Long count = (Long) query.getSingleResult();
		
		if(count>0)
			  return true;
		
		return false;
		
	}
	
	@Override
	public Boolean isEmailAvailable(String email, Long id) {
		
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<User> root = criteriaQuery.from( User.class );
		criteriaQuery.select(builder.count(root));
		
		criteriaQuery.where(
				                         builder.equal(
				                        		                root.get( "email" ), email
				                        		              ),
				                         builder.and(
				                        		             builder.notEqual(root.get( "id" ), id)
				                        		             )
				                         );
		
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery); 
		Long count = (Long) query.getSingleResult();
		
		if(count>0)
			  return true;
		
		return false;
		
	}
	
	@Override
	public Boolean isNickNameAvailable(String nick_name, Long id) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<User> root = criteriaQuery.from( User.class );
		criteriaQuery.select(builder.count(root));
		
		criteriaQuery.where(
				                         builder.equal(
				                        		                root.get( "nick_name" ), nick_name
				                        		              ),
				                         builder.and(
				                        		             builder.notEqual(root.get( "id" ), id)
				                        		             )
				                         );
		
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery); 
		Long count = (Long) query.getSingleResult();
		
		if(count>0)
			  return true;
		
		return false;

	}

	
	
	@Override
	public User getUserInfoByEmailPwd(String email, String password) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
		Root<User> root = criteriaQuery.from( User.class);
		criteriaQuery.select(root);
		
		criteriaQuery.where(
                builder.equal(
               		                root.get( "email" ), email
               		              ),
                builder.and(
               		             builder.notEqual(root.get( "password" ), password)
               		             )
                );
		
		TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
		User user = null;
		
		try {
			user = query.getSingleResult();
		}catch(Exception ex) {
			System.out.println("Error in User Dao "+ex.getMessage());
		}
		
		return  user;
		
	}
	
	@Override
	public Boolean isUserActive(String email) {
		
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<User> root = criteriaQuery.from( User.class );
		criteriaQuery.select(builder.count(root));
		
		criteriaQuery.where(
						                builder.equal(
						               		                root.get( "email" ), email
						               		              ),
						                builder.and(
						               		             builder.equal(root.get( "is_active" ), true)
						               		             )
                                       );
		
		
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery); 
		Long count = (Long) query.getSingleResult();
		
		if(count>0)
			  return true;
		
		return false;
		
	}
	
	@Override
	public Boolean isEmailVerified(String email) {
		
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<User> root = criteriaQuery.from( User.class );
		criteriaQuery.select(builder.count(root));
		
		criteriaQuery.where(
						                builder.equal(
						               		                root.get( "email" ), email
						               		              ),
						                builder.and(
						               		             builder.equal(root.get( "email_verified" ), true)
						               		             )
                                       );
		
		
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery); 
		Long count = (Long) query.getSingleResult();
		
		if(count>0)
			  return true;
		
		return false;
		
	}

	
	@Override
	public Long add(User user) {
		Long id = (Long) entityManager.unwrap(Session.class).save(user);
		
		return id;
	}

	@Override
	public void edit(User user) {
		entityManager.unwrap(Session.class).merge(user);
	}
	
	@Override
	public void delete(User user) {
		entityManager.unwrap(Session.class).delete(user);
	}
	
	@Override
	public User getUserInfoById(Long id) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
		Root<User> root = criteriaQuery.from( User.class);
		criteriaQuery.select(root);
		criteriaQuery.where(builder.equal(root.get( "id" ),id));
		TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
		User user = null;
		
		try {
			user = query.getSingleResult();
		}catch(Exception ex) {
			System.out.println("Error in User Dao "+ex.getMessage());
		}
		
		return  user;
		
	}

	@Override
	public User getUserInfoByEmail(String email) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
		Root<User> root = criteriaQuery.from( User.class);
		criteriaQuery.select(root);
		criteriaQuery.where(builder.equal(root.get( "email" ),email));
		TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
		User user = null;
		
		try {
			user = query.getSingleResult();
		}catch(Exception ex) {
			System.out.println("Error in User Dao "+ex.getMessage());
		}
		
		return  user;
		
	}

	@Override
	public boolean isAccessCodeExists(String email, Integer access_code) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<User> root = criteriaQuery.from( User.class );
		criteriaQuery.select(builder.count(root));
		
		criteriaQuery.where(
				                           builder.equal(
				                                                 root.get( "email" ),email
				                                                ),
				                           builder.and(
					               		                        builder.equal(root.get( "access_code" ), access_code)
					               		                     )
				                       );
		
		
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery); 
		Long count = (Long) query.getSingleResult();
		
		if(count>0)
			  return true;
		
		return false;

	}
	
}
