package com.eventinvitation.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eventinvitation.domain.EntityAudit;
import com.eventinvitation.domain.Event;
import com.eventinvitation.domain.EventMailingList;
import com.eventinvitation.domain.UserDetailsEntity;

@Repository
@Transactional
public class EventDAOImpl implements EventDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public Event saveEvent(Event event) {
		Session session = getSessionFactory().getCurrentSession();
		session.save(event);
		if(event.getAddress() != null){
			event.getAddress().setId(event.getId());
			session.save(event.getAddress());
		}
		return event;
	}

	public Event getEvent(String eventId) {
		Session session = getSessionFactory().getCurrentSession();
		Event event = (Event)session.get(Event.class, eventId);
		event.setMaillingList(getEventAttendance(event.getId()));
		return event;
	}

	public Event getEventByPattern(String pattern) {
		Session session = getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(EventMailingList.class);
		criteria.add(Restrictions.eq("id", pattern));
		EventMailingList eventMailingList = (EventMailingList) criteria.uniqueResult();
		Event event = eventMailingList.getEvent();
		event.setMaillingList(getEventAttendance(event.getId()));
		return event;
	}

	@SuppressWarnings("unchecked")
	public List<Event> listEventsByUser(String userId) {
		Session session = getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(Event.class);
		criteria.add(Restrictions.eq("owner.id", userId));
		return criteria.list();
	}

	public void acceptEvent(String urlPattern,String currentEmail) throws Exception {
		Session session = getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(EventMailingList.class);
		criteria.add(Restrictions.eq("id", urlPattern));
		EventMailingList eventMailingList = (EventMailingList) criteria.uniqueResult();
		if(eventMailingList != null){
			if(!eventMailingList.getEmail().equals(currentEmail)){
				throw new Exception("You are not authorized to access this link.");
			}
			Event event = eventMailingList.getEvent();
			eventMailingList.setStatus("Accepted");
			EntityAudit audit = eventMailingList.getAudit();
			audit.setUpdatedOn(new Date());
			eventMailingList.setAudit(audit);
			session.update(eventMailingList);
			UserDetailsEntity userDetailsEntity = eventMailingList.getUserDetailsEntity();
			List<UserDetailsEntity> attends = event.getAttendes();
			if(attends != null)
				event.getAttendes().add(userDetailsEntity);
			else{
				attends = new ArrayList<UserDetailsEntity>();
				attends.add(userDetailsEntity);
				event.setAttendes(attends);
			}
			session.update(event);
		}
		else{
			throw new Exception("Can't found this event to accept.");
		}
		
	}

	public void rejectEvent(String urlPattern) throws Exception{
		Session session = getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(EventMailingList.class);
		criteria.add(Restrictions.eq("id", urlPattern));
		EventMailingList eventMailingList = (EventMailingList) criteria.uniqueResult();
		if(eventMailingList != null){
			eventMailingList.setStatus("Rejected");
			EntityAudit audit = eventMailingList.getAudit();
			audit.setUpdatedOn(new Date());
			eventMailingList.setAudit(audit);
			session.update(eventMailingList);
		}
		else{
			throw new Exception("Can't found this event to accept.");
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<EventMailingList> getEventAttendance(String eventId) {
		Session session = getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(EventMailingList.class);
		criteria.add(Restrictions.eq("event.id", eventId));
		List<EventMailingList> eventMailingLists = criteria.list();
		if(eventMailingLists != null){
			for(EventMailingList eventMailingList : eventMailingLists){
				criteria = session.createCriteria(UserDetailsEntity.class);
				criteria.add(Restrictions.eq("email", eventMailingList.getEmail()));
				UserDetailsEntity userDetailsEntity = (UserDetailsEntity)criteria.uniqueResult();
				if(userDetailsEntity != null){
					eventMailingList.setUserDetailsEntity(userDetailsEntity);
				}
			}
		}
		return eventMailingLists;
	}
	
	@SuppressWarnings("unchecked")
	public List<EventMailingList> getRefreshedAttendance(String id,String listName) {
		Session session = getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(EventMailingList.class);
		criteria.add(Restrictions.eq("event.id", id));
		criteria.add(Restrictions.eq("status", listName));
		List<EventMailingList> eventMailingLists = criteria.list();
		if(eventMailingLists != null){
			for(EventMailingList eventMailingList : eventMailingLists){
				criteria = session.createCriteria(UserDetailsEntity.class);
				criteria.add(Restrictions.eq("email", eventMailingList.getEmail()));
				UserDetailsEntity userDetailsEntity = (UserDetailsEntity)criteria.uniqueResult();
				if(userDetailsEntity != null){
					eventMailingList.setUserDetailsEntity(userDetailsEntity);
				}
			}
		}
		return eventMailingLists;
	}

	@SuppressWarnings("rawtypes")
	public Event getLastEvent(String currentUserId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Event.class);
		criteria.createAlias("attendes", "attendes");
		criteria.add(Restrictions.eq("attendes.id", currentUserId));
		criteria.addOrder(Order.desc("audit.createdOn"));
		criteria.setMaxResults(1);
		List result = criteria.list();
		if(result != null && result.size() > 0){
			Event event = (Event)result.get(0);
			event.setMaillingList(getEventAttendance(event.getId()));
			return event;
		}
		else{
			criteria = session.createCriteria(Event.class);
			criteria.add(Restrictions.eq("owner.id", currentUserId));
			criteria.addOrder(Order.desc("audit.createdOn"));
			criteria.setMaxResults(1);
			result = criteria.list();
			if(result != null && result.size() > 0){
				Event event = (Event)result.get(0);
				event.setMaillingList(getEventAttendance(event.getId()));
				return event;
			}
			else
				return null;
		}
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
