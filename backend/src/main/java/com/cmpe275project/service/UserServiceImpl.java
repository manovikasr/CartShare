package com.cmpe275project.service;

import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275project.dao.UserDao;
import com.cmpe275project.model.Pool;
import com.cmpe275project.model.User;
import com.cmpe275project.requestObjects.UserRequest;


@Service
@Transactional
public class UserServiceImpl implements UserService{

	
	@Autowired 
	private UserDao userDao;
	
	@Override
	public Boolean isEmailExists(String email) {
		
		  return userDao.isEmailExists(email);
		  
	}
	
	@Override
	public Boolean isNickNameExists(String nick_name) {
		// TODO Auto-generated method stub
		return userDao.isNickNameExists(nick_name);
	}

	@Override
	public Boolean isScreenNameExists(String screen_name) {
		// TODO Auto-generated method stub
		return userDao.isScreenNameExists(screen_name);
	}

	
	@Override
	public Boolean isUserIdExists(Long id) {
		
		  return userDao.isUserIdExists(id);
		  
	}
	
	@Override
	public User getUserInfoByEmailPwd(String email, String password) {
		
		  return userDao.getUserInfoByEmailPwd(email, password);
		  
	}
	
	@Override
	public Boolean isEmailAvailable(String email,Long id) {
		
		  return userDao.isEmailAvailable(email, id);
		  
	}
	
	@Override
	public Boolean isNickNameAvailable(String nick_name, Long id) {
		// TODO Auto-generated method stub
		return userDao.isNickNameAvailable(nick_name, id);
	}
	
	@Override
	public Boolean isUserActive(String email) {
		return userDao.isUserActive(email);
	}
	
	@Override
	public Boolean isEmailVerified(String email) {
		return userDao.isEmailVerified(email);
	}
	
	@Override
	public Long add(User user) {
		
		return userDao.add(user);
	}

	@Override
	public void edit(User user) {
		
		userDao.edit(user);
	}
	
	@Override
	public void delete(User user)
	{
		userDao.delete(user);
	}

	@Override
	public User getUserInfoById(Long id) {
		
		return userDao.getUserInfoById(id);
	}
	
	@Override
	public User getUserInfoByEmail(String email) {
		
		return userDao.getUserInfoByEmail(email);
	}
	
	@Override
	public User getUserInfoByScreenName(String screen_name) {
		return userDao.getUserInfoByScreenName(screen_name);
	}

	@Override
	public Integer generateAccessCode() {
		// TODO Auto-generated method stub
		
		Random r = new Random( System.currentTimeMillis() );
	 
		return 10000 + r.nextInt(20000); 
	}

	@Override
	public boolean isAccessCodeMatches(String email, Integer access_code) {
		// TODO Auto-generated method stub
		return userDao.isAccessCodeMatches(email,access_code);
	}

	@Override
	public boolean checkHasPool(Long userid) {
		// TODO Auto-generated method stub
		return userDao.checkHasPool(userid);
	}


}
