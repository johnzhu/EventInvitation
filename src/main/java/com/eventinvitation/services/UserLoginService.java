package com.eventinvitation.services;

import com.eventinvitation.domain.dto.UserLoginDTO;

public interface UserLoginService {
	
	UserLoginDTO handleUserLogin(String username, String password);

}
