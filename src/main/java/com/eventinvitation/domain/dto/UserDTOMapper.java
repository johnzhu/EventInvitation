package com.eventinvitation.domain.dto;

import com.eventinvitation.domain.UserDetailsEntity;
import com.eventinvitation.domain.UserEntity;

public class UserDTOMapper {

	
	public static UserDTO mapUserEntityToUserDTO(UserEntity userEntity){
		UserDTO userDTO = new UserDTO();
		userDTO.setAddress(userEntity.getUserDetails().getAddress());
		userDTO.setEmail(userEntity.getUserDetails().getEmail());
		userDTO.setFullName(userEntity.getUserDetails().getName());
		userDTO.setUserId(userEntity.getId());
		return userDTO;
	}
	
	public static UserDTO mapUserDetailsToUserDTO(UserDetailsEntity userDetailsEntity){
		UserDTO userDTO = new UserDTO();
		userDTO.setFullName(userDetailsEntity.getAddress());
		userDTO.setEmail(userDetailsEntity.getEmail());
		userDTO.setFullName(userDetailsEntity.getName());
		userDTO.setUserId(userDetailsEntity.getId());
		return userDTO;
	}
}
