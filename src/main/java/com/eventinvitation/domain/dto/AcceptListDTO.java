package com.eventinvitation.domain.dto;

import java.io.Serializable;

public class AcceptListDTO implements Serializable{
	
	private static final long serialVersionUID = -725660493988824883L;
	
	private String name;
	private String email;
	private String status;
	private String lastOnlineDateTime;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLastOnlineDateTime() {
		return lastOnlineDateTime;
	}
	public void setLastOnlineDateTime(String lastOnlineDateTime) {
		this.lastOnlineDateTime = lastOnlineDateTime;
	}
	
	

}
