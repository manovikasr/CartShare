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
		user.setAccesscode("a");
		user.setAuthmode("b");
		user.setContributioncredits("c");
		user.setContributionstatus("d");
		user.setEmail("a@gmail");
		user.setNickname("aish");
		user.setScreenname("Aishwarya");
		user.setRole("r");
		
		  Pool p =new Pool(); p.setNeighbourhoodname("dd"); p.setPooldesc("desc");
		  p.setPoolleaderid(0); p.setPoolname("pool aish"); p.setPoolrating("w");
		  p.setPoolzip(98);
		  
		  user.setPool(p);
		 
		userDao.registerUser(user);
	}

}
