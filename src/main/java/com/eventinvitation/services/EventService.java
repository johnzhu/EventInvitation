package com.eventinvitation.services;

import java.util.List;

import com.eventinvitation.domain.UserDetailsEntity;
import com.eventinvitation.domain.dto.AcceptListDTO;
import com.eventinvitation.domain.dto.EventDTO;

public interface EventService {
	
	public EventDTO createEvent(String name,String address,String time,String description,String[] mailling_list,UserDetailsEntity owner);
	
	public EventDTO getEvent(String eventId);
	
	public List<EventDTO> listEventsByUser(String userId);
	
	public void acceptEvent(String urlPattern) throws Exception ;
	
	public void rejectEvent(String urlPattern) throws Exception ;
	
	public List<AcceptListDTO> getEventAttendance(String eventId);

}
