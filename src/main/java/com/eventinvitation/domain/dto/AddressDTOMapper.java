package com.eventinvitation.domain.dto;

import com.eventinvitation.domain.Address;

public class AddressDTOMapper {
	
	public static AddressDTO mapFromAddressToAddressDTO(Address address){
		AddressDTO addressDTO = new AddressDTO();
		if(address != null){
			addressDTO.setCountry(address.getCountry());
			addressDTO.setState(address.getState());
			addressDTO.setStreet(address.getStreet());
			addressDTO.setCity(address.getCity());
		}
		return addressDTO;
	}

}
