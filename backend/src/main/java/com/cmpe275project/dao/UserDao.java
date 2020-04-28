package com.cmpe275project.dao;

import com.cmpe275project.model.User;

public interface UserDao {
	
   public Boolean isEmailExists(String email);
	
    public Boolean isNickNameExists(String nick_name);
    
    public Boolean isScreenNameExists(String screen_name);
	
	public Boolean isUserIdExists(Long id);
	
	public Boolean isEmailAvailable(String email,Long id);
	
	public Boolean isNickNameAvailable(String nick_name,Long id);
	
	public User getUserInfoByEmailPwd(String email,String password);
	
	public Boolean isUserActive(String email);
	
	public Boolean isEmailVerified(String email);
	
	public Long add(User user);
	
	public void edit(User user);
	
	public void delete(User user);
	
	public User getUserInfoById(Long id);
	
	public User getUserInfoByEmail(String email);
	
	public User getUserInfoByScreenName(String screen_name);
	
	public boolean isAccessCodeMatches(String email, Integer access_code);

	public boolean checkHasPool(Long userid);

	public User getUserInfoByScreenName(String screen_name);
}
