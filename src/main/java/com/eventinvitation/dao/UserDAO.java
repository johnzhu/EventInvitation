package com.eventinvitation.dao;

import com.eventinvitation.domain.UserEntity;

public interface UserDAO {

	public UserEntity findUserByName(String name);
	
	public UserEntity findUserByEmail(String email);

	public UserEntity save(UserEntity userEntity) throws Exception ;
	
	public String isUserExistUsingUrlPattern(String urlPattern);
	
	public void updateUsetOnlineDate(String userId);
	
	public UserEntity getUser(String userId);
}
