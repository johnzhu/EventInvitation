package com.eventinvitation.domain.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.eventinvitation.domain.UserEntity;

public class UserLoginDTO extends UserLoginSeviceDTO {

	private UserEntity entity;
	
	private List<GrantedAuthority> grantedAuthority;

	public Collection<? extends GrantedAuthority> getGrantedAuthority() {
		return grantedAuthority;
	}

	public void setGrantedAuthority(List<GrantedAuthority> grantedAuthority) {
		this.grantedAuthority = grantedAuthority;
	}

	public UserEntity getEntity() {
		return entity;
	}

	public void setEntity(UserEntity entity) {
		this.entity = entity;
	}
	
}
