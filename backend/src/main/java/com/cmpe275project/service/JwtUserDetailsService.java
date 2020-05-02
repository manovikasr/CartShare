package com.cmpe275project.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cmpe275project.dao.UserDao;
import com.cmpe275project.model.User;


@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	//Loading User By Email-> as username
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userDao.getUserInfoByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with email: " + email);
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(),null,
				new ArrayList<>());
	}

	public UserDetails loadUserById(Long id) throws UsernameNotFoundException {

		User user = userDao.getUserInfoById(id);
		if (user == null) {
			throw new UsernameNotFoundException("User not found for id "+ id);
		}
		
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		setAuths.add(new SimpleGrantedAuthority(user.getRole()));
        

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(setAuths);

		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), "",authorities);
	}


}