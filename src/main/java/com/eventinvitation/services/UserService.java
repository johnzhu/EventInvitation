package com.eventinvitation.services;

import com.eventinvitation.domain.dto.UserDTO;

public interface UserService {
	
	public UserDTO signup(String userName,String email,String street,String fullName,String password,String state,String country,String city) throws Exception;
	
	public String checkUserExist(String urlPattern);

}
