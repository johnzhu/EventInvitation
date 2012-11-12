package com.eventinvitation.services;

import com.eventinvitation.domain.dto.UserDTO;

public interface UserService {
	
	public UserDTO signup(String userName,String email,String address,String fullName,String password) throws Exception;
	
	public boolean checkUserExist(String urlPattern);

}
