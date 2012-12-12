package com.eventinvitation.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventinvitation.dao.EventDAO;
import com.eventinvitation.dao.UserDAO;
import com.eventinvitation.domain.Address;
import com.eventinvitation.domain.EntityAudit;
import com.eventinvitation.domain.Event;
import com.eventinvitation.domain.EventMailingList;
import com.eventinvitation.domain.UserDetailsEntity;
import com.eventinvitation.domain.UserEntity;
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
	private UserDAO userDAO;

	@Autowired
	private Mailer mailer;

	private List<EventMailingList> eventMailingLists;

	public EventDTO createEvent(String name, String street,String country,String state, String time,
			String description, String[] mailling_list, UserDetailsEntity owner) {
		String invalidEmails = "Invalid Emails: ";
		boolean errorExist = false;
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
			if(Validator.isValidEmail(mail)){
				EventMailingList eventMailingList = new EventMailingList();
				eventMailingList.setAudit(audit);
				eventMailingList.setEmail(mail);
				eventMailingList.setEvent(event);
				eventMailingList.setStatus("Pending");
				eventMailingLists.add(eventMailingList);
			}
			else{
				invalidEmails += mail + " ";
				errorExist = true;
			}
		}
		event.setMaillingList(eventMailingLists);
		new Thread(new Runnable() {
			public void run() {
				getMailer().inviteFriends(eventMailingLists);
			}
		}).start();
		event = eventDAO.saveEvent(event);
		EventDTO eventDTO = EventDTOMapper.mapEventToEventDTO(event);
		if(errorExist)
			eventDTO.setErrorMessage(invalidEmails);
		return eventDTO;
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

	public EventDTO getLastEvent(String currentUserId) {
		return EventDTOMapper.mapEventToEventDTO(eventDAO.getLastEvent(currentUserId));
	}

	public void acceptEvent(String urlPattern,String currentLogedInEmail) throws Exception  {
		eventDAO.acceptEvent(urlPattern,currentLogedInEmail);
	}

	public void rejectEvent(String urlPattern) throws Exception  {
		eventDAO.rejectEvent(urlPattern);
	}

	public List<AcceptListDTO> getEventAttendance(String eventId) {
		return EventDTOMapper.mapMaillingListToAcceptListDTO(eventDAO.getEventAttendance(eventId),null);
	}
	
	public boolean refreshUserStatus(String userId, String onlineFlag) {
		UserEntity userEntity = getUserDAO().getUser(userId);
		if(userEntity != null){
			UserDetailsEntity userDetailsEntity = userEntity.getUserDetails();
			Date currentDate = new Date();
			if(((currentDate.getTime() - userDetailsEntity.getAudit().getUpdatedOn().getTime())/1000) > Integer.parseInt(onlineFlag)){
				return false;
			}
			else{
				return true;
			}
		}
		return false;
	}

	public List<AcceptListDTO> refreshListStatus(String eventId,String onlineFlag, String listName) {
		return EventDTOMapper.mapMaillingListToAcceptListDTO(eventDAO.getRefreshedAttendance(eventId,listName),onlineFlag);
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
	
	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

}
