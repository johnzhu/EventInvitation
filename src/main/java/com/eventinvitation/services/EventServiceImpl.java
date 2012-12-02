package com.eventinvitation.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventinvitation.dao.EventDAO;
import com.eventinvitation.domain.Address;
import com.eventinvitation.domain.EntityAudit;
import com.eventinvitation.domain.Event;
import com.eventinvitation.domain.EventMailingList;
import com.eventinvitation.domain.UserDetailsEntity;
import com.eventinvitation.domain.dto.AcceptListDTO;
import com.eventinvitation.domain.dto.EventDTO;
import com.eventinvitation.domain.dto.EventDTOMapper;
import com.eventinvitation.util.Mailer;
import com.eventinvitation.util.Validator;

/**
 * @author saeedhas
 *
 */
@Service
@Transactional
public class EventServiceImpl implements EventService {

	@Autowired
	private EventDAO eventDAO;

	@Autowired
	private Mailer mailer;

	private List<EventMailingList> eventMailingLists;

	public EventDTO createEvent(String name, String street,String country,String state, String time,
			String description, String[] mailling_list, UserDetailsEntity owner) {
		Event event = new Event();
		Address address = new Address();
		address.setCountry(country);
		address.setState(state);
		address.setStreet(street);
		if(Validator.isValidAddress(address))
			event.setAddress(address);
		event.setDdescription(description);
		event.setName(name);
		event.setTime(time);
		event.setOwner(owner);
		EntityAudit audit = new EntityAudit();
		audit.setCreatedOn(new Date());
		audit.setUpdatedOn(new Date());
		event.setAudit(audit);
		eventMailingLists = new ArrayList<EventMailingList>();
		for (String mail : mailling_list) {
			EventMailingList eventMailingList = new EventMailingList();
			eventMailingList.setAudit(audit);
			eventMailingList.setEmail(mail);
			eventMailingList.setEvent(event);
			eventMailingList.setStatus("Pending");
			eventMailingLists.add(eventMailingList);
		}
		event.setMaillingList(eventMailingLists);
		new Thread(new Runnable() {
			public void run() {
				getMailer().inviteFriends(eventMailingLists);
			}
		}).start();
		event = eventDAO.saveEvent(event);
		return EventDTOMapper.mapEventToEventDTO(event);
	}

	public EventDTO getEvent(String eventId) {
		return EventDTOMapper.mapEventToEventDTO(eventDAO.getEvent(eventId));
	}

	public EventDTO getEventByPattern(String pattern) {
		return EventDTOMapper.mapEventToEventDTO(eventDAO.getEventByPattern(pattern));
	}

	public List<EventDTO> listEventsByUser(String userId) {
		List<EventDTO> eventDTOs = new ArrayList<EventDTO>();
		List<Event> events = eventDAO.listEventsByUser(userId);
		if (events != null) {
			for (Event event : events) {
				eventDTOs.add(EventDTOMapper.mapEventToEventDTO(event));
			}
		}
		return eventDTOs;
	}

	public void acceptEvent(String urlPattern) throws Exception  {
		eventDAO.acceptEvent(urlPattern);
	}

	public void rejectEvent(String urlPattern) throws Exception  {
		eventDAO.rejectEvent(urlPattern);
	}

	public List<AcceptListDTO> getEventAttendance(String eventId) {
		return EventDTOMapper.mapMaillingListToAcceptListDTO(eventDAO.getEventAttendance(eventId));
	}

	public EventDAO getEventDAO() {
		return eventDAO;
	}

	public void setEventDAO(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}

	public Mailer getMailer() {
		return mailer;
	}

	public void setMailer(Mailer mailer) {
		this.mailer = mailer;
	}

}
