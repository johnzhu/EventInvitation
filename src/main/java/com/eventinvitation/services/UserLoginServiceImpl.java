package com.eventinvitation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventinvitation.dao.UserDAO;
import com.eventinvitation.domain.UserEntity;
import com.eventinvitation.domain.dto.UserLoginDTO;

@Service("userLoginServiceImpl")
public class UserLoginServiceImpl implements UserLoginService {
	
	private static int USER_NOT_FOUND_DB = 301;
	
	@Autowired
	private UserDAO userDao;

	public UserLoginDTO handleUserLogin(String username, String password) {
		
		UserLoginDTO loginDTO = new UserLoginDTO();
		
		try {
			UserEntity userEntity = null; // load by username
			
			if(userEntity != null) {
				loginDTO.setEntity(userEntity);
				
			} else {
				userEntity = null; // load by email
				if(userEntity != null)
					loginDTO.setEntity(userEntity);
				else
					loginDTO.setErrorCode(USER_NOT_FOUND_DB);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return loginDTO;
	}
}
