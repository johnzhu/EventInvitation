package com.eventinvitation.domain.dto;

import java.io.Serializable;

import com.eventinvitation.domain.Address;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 8304817856447116717L;
	
	private String fullName;
	private String email;
	private Address address;
	private String userId;
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
