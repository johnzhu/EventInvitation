package com.eventinvitation.domain.dto;

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
}
