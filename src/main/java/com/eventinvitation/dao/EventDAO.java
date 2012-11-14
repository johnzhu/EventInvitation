package com.eventinvitation.dao;

import java.util.List;

import com.eventinvitation.domain.Event;
import com.eventinvitation.domain.EventMailingList;

public interface EventDAO {

	public Event saveEvent(Event event);
	
	public Event getEvent(String eventId);
	
	public List<Event> listEventsByUser(String userId);
	
	public void acceptEvent(String urlPattern) throws Exception ;
	
	public void rejectEvent(String urlPattern) throws Exception ;
	
	public List<EventMailingList> getEventAttendance(String eventId);
}
