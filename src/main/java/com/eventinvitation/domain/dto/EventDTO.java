package com.eventinvitation.domain.dto;

import java.io.Serializable;
import java.util.List;

import com.eventinvitation.domain.Address;

public class EventDTO implements Serializable {

	private static final long serialVersionUID = -2655825145416045630L;
	
	private String id;
	private String name;
	private String description;
	private String time;
	private Address address;
	private UserDTO owner;
	
	private List<AcceptListDTO> acceptListDTOs;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public UserDTO getOwner() {
		return owner;
	}
	public void setOwner(UserDTO owner) {
		this.owner = owner;
	}
	public List<AcceptListDTO> getAcceptListDTOs() {
		return acceptListDTOs;
	}
	public void setAcceptListDTOs(List<AcceptListDTO> acceptListDTOs) {
		this.acceptListDTOs = acceptListDTOs;
	}
}
