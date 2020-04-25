package com.cmpe275project.service;

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
	public void registerUser(UserRequest userReq) {
		User user = new User();
		user.setAccess_code(100);
		user.setAuth_mode("b");
		user.setContribution_credits(1);
		user.setContribution_status("d");
		user.setEmail("a@gmail");
		user.setNick_name("aish");
		user.setScreen_name("Aishwarya");
		user.setRole("r");
		
		  Pool p =new Pool(); p.setNeighbourhoodname("dd"); p.setPooldesc("desc");
		  p.setPoolleaderid(0); p.setPoolname("pool aish"); p.setPoolrating("w");
		  p.setPoolzip(98);
		  
		  user.setPool(p);
		 
		userDao.registerUser(user);
	}
	
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

	

}
