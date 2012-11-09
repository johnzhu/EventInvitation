package com.eventinvitation.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.eventinvitation.config.exception.UserNotExistsException;
import com.eventinvitation.domain.dto.UserLoginDTO;
import com.eventinvitation.services.UserLoginService;

public class CustomDataBaseAuthenticator implements AuthenticationProvider{

	@Autowired
	private Md5PasswordEncoder md5PasswordEncoder;
	
	@Autowired
	private UserLoginService userLoginService;
	
	private static int USER_NOT_FOUND_DB = 301;

	public boolean supports(Class<? extends Object> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

			UsernamePasswordAuthenticationToken user = null;

			UserLoginDTO loginDTO = null;
			
			try {
				loginDTO = userLoginService.handleUserLogin(authentication.getName(), authentication.getCredentials().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(loginDTO.getErrorCode().equals(USER_NOT_FOUND_DB)) {
				throw new UserNotExistsException("User not exists in our system");
			}
			
			else {
				user = handleUsernamePassword(loginDTO);
			}


		return user;
	}

	private UsernamePasswordAuthenticationToken handleUsernamePassword(UserLoginDTO loginDTO) {

		UsernamePasswordAuthenticationToken loggedUser = null;
		
		try {

			if (loginDTO == null) {
				throw new UsernameNotFoundException("This user is not exist in our system");
			} else {
				
				String encryptiedPass = md5PasswordEncoder.encodePassword(loginDTO.getPassword().toString(), null);
				
				loggedUser = new UsernamePasswordAuthenticationToken(loginDTO.getEntity(), encryptiedPass, loginDTO.getGrantedAuthority());
			}

			return loggedUser;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}



}
