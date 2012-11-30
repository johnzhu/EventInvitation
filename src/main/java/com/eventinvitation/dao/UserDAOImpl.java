package com.eventinvitation.dao;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.eventinvitation.domain.EntityAudit;
import com.eventinvitation.domain.EventMailingList;
import com.eventinvitation.domain.UserDetailsEntity;
import com.eventinvitation.domain.UserEntity;

@Repository
@Transactional( propagation = Propagation.MANDATORY )
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public UserEntity findUserByName(String name) {
		Session currentSession = getSessionFactory().openSession();
		Criteria criteria = currentSession.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("userName", name));
		UserEntity userEntity = (UserEntity)criteria.uniqueResult();
		currentSession.close();
		return userEntity;
	}

	public UserEntity findUserByEmail(String email) {
		Session currentSession = getSessionFactory().openSession();
		Criteria criteria = currentSession.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("userDetails.email", email));
		criteria.createAlias("userDetails", "userDetails");
		UserEntity userEntity = (UserEntity)criteria.uniqueResult();
		currentSession.close();
		return userEntity;
	}

	public UserEntity save(UserEntity userEntity) throws Exception {
		String message = checkUserNameOrEmailExist(userEntity.getUsername(), userEntity.getUserDetails().getEmail());
		if(message == null){
			Session currentSession = getSessionFactory().getCurrentSession();
			currentSession.save(userEntity);
			if(userEntity.getUserDetails().getAddress() != null){
				userEntity.getUserDetails().getAddress().setId(userEntity.getId());
				currentSession.save(userEntity.getUserDetails().getAddress());
			}
			return userEntity;
		}
		else{
			throw new Exception(message);
		}
	}
	
	public boolean isUserExistUsingUrlPattern(String urlPattern){
		boolean result = false;
		Session currentSession = getSessionFactory().getCurrentSession();
		EventMailingList eventMailingList = (EventMailingList)currentSession.get(EventMailingList.class, urlPattern);
		if(eventMailingList == null)
			result = false;
		else{
			String email = eventMailingList.getEmail();
			Criteria criteria = currentSession.createCriteria(UserEntity.class);
			criteria.add(Restrictions.eq("userDetails.email", email));
			criteria.createAlias("userDetails", "userDetails");
			UserEntity userEntity = (UserEntity)criteria.uniqueResult();
			if(userEntity != null)
				result = true;
			else
				result = false;
		}
		return result;
	}
	
	private String checkUserNameOrEmailExist(String username,String email){
		String message = null;
		Session currentSession = getSessionFactory().getCurrentSession();
		Criteria criteria = currentSession.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("userDetails.email", email));
		criteria.createAlias("userDetails", "userDetails");
		UserEntity userEntity = (UserEntity)criteria.uniqueResult();
		if(userEntity != null){
			message = "Error: Email already exist";
		}
		currentSession = getSessionFactory().getCurrentSession();
		criteria = currentSession.createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq("userName", username));
		userEntity = (UserEntity)criteria.uniqueResult();
		if(userEntity != null){
			if(message == null)
				message = "Error: Username already exist";
			else
				message = message + " and Username already exist";
		}
		return message;
	}

	public void updateUsetOnlineDate(String userId) {
		Session session = getSessionFactory().openSession();
		UserDetailsEntity userDetailsEntity = (UserDetailsEntity) session.get(UserDetailsEntity.class, userId);
		EntityAudit audit = userDetailsEntity.getAudit();
		audit.setUpdatedOn(new Date());
		userDetailsEntity.setAudit(audit);
		session.update(userDetailsEntity);
		session.flush();
		session.close();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
