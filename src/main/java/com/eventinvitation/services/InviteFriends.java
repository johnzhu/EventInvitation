package com.eventinvitation.services;

import java.util.List;

import com.eventinvitation.domain.EventMailingList;

public interface InviteFriends {
	
	public void inviteFriends(List<EventMailingList> eventMailingList);
}
