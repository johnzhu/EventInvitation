package com.eventinvitation.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.eventinvitation.dao.UserDAO;
import com.eventinvitation.domain.Address;
import com.eventinvitation.domain.EntityAudit;
import com.eventinvitation.domain.UserDetailsEntity;
import com.eventinvitation.domain.UserEntity;
import com.eventinvitation.domain.dto.UserDTO;
import com.eventinvitation.domain.dto.UserDTOMapper;
import com.eventinvitation.util.Validator;

@Service
@Transactional( propagation = Propagation.MANDATORY )
public class UserServiceImpl implements UserService {
	
	@Autowired 
	private UserDAO userDAO;
	
	@Autowired
	private Md5PasswordEncoder md5PasswordEncoder;
	
	
	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public UserDTO signup(String userName, String email, String street,String fullName, String password,String state,String country,String city) throws Exception{
		UserEntity userEntity = new UserEntity();
		userEntity.setEnabled(true);
		userEntity.setPassword(md5PasswordEncoder.encodePassword(password, null));
		userEntity.setUsername(userName);
		UserDetailsEntity userDetailsEntity = new UserDetailsEntity();
		Address address = new Address();
		address.setCountry(country);
		address.setState(state);
		address.setStreet(street);
		address.setCity(city);
		if(Validator.isValidAddress(address))
			userDetailsEntity.setAddress(address);
		userDetailsEntity.setEmail(email);
		userDetailsEntity.setName(fullName);
		EntityAudit audit = new EntityAudit();
		audit.setCreatedOn(new Date(System.currentTimeMillis()));
		audit.setUpdatedOn(new Date(System.currentTimeMillis()));
		userDetailsEntity.setAudit(audit);
		userDetailsEntity.setUser(userEntity);
		userEntity.setUserDetails(userDetailsEntity);
		return UserDTOMapper.mapUserEntityToUserDTO(userDAO.save(userEntity));
	}
	
	public void logout(String userId){
		userDAO.logout(userId);
	}

	public String checkUserExist(String urlPattern) {
		return userDAO.isUserExistUsingUrlPattern(urlPattern);
	}
	
}
