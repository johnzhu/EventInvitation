package com.eventinvitation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eventinvitation.dao.UserDAO;
import com.eventinvitation.domain.UserEntity;
import com.eventinvitation.domain.dto.UserLoginDTO;

@Service("userLoginService")
public class UserLoginServiceImpl implements UserLoginService {
	
	private static int USER_NOT_FOUND_DB = 401;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private Md5PasswordEncoder md5PasswordEncoder;

	public UserLoginDTO handleUserLogin(String username, String password) {
		
		UserLoginDTO loginDTO = new UserLoginDTO();
		if(username == null || username.equals("") || password == null || password.equals("")){
			loginDTO.setErrorCode(USER_NOT_FOUND_DB);
			return loginDTO;
		}
		try {
			UserEntity userEntity = userDAO.findUserByName(username);
			
			if(userEntity != null) {
				if(md5PasswordEncoder.isPasswordValid(userEntity.getPassword(), password, null))
					loginDTO.setEntity(userEntity);
				else
					loginDTO.setErrorCode(USER_NOT_FOUND_DB);
			} 
			else 
			{
				userEntity = userDAO.findUserByEmail(username);
				if(userEntity != null){
					if(md5PasswordEncoder.isPasswordValid(userEntity.getPassword(), password, null))
						loginDTO.setEntity(userEntity);
					else
						loginDTO.setErrorCode(USER_NOT_FOUND_DB);
				}
				else
					loginDTO.setErrorCode(USER_NOT_FOUND_DB);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return loginDTO;
	}

	public Md5PasswordEncoder getMd5PasswordEncoder() {
		return md5PasswordEncoder;
	}

	public void setMd5PasswordEncoder(Md5PasswordEncoder md5PasswordEncoder) {
		this.md5PasswordEncoder = md5PasswordEncoder;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
}
