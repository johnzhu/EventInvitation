package com.eventinvitation.util;

import java.util.List;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.eventinvitation.domain.EventMailingList;
import com.eventinvitation.services.InviteFriends;

public class Mailer implements InviteFriends {

	private MailSender mailSender;
	
    private SimpleMailMessage templateMessage;
    
    private String view_link_text = null;
    
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }

	public void setView_link_text(String view_link_text) {
		this.view_link_text = view_link_text;
	}

	public void inviteFriends(List<EventMailingList> eventMailingList) {
		if(eventMailingList != null){
			for(EventMailingList email : eventMailingList){
				SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
				msg.setSubject("Invitation to attend " + email.getEvent().getName());
				msg.setTo(email.getEmail());
		        msg.setText("Dear " + email.getEmail() 
		        			+ "<br> Your friend " 
		        			+ email.getEvent().getOwner().getName() 
		        			+ " Invite you attend " 
		        			+ email.getEvent().getName() 
		        			+ "<br>"
		        			+ String.format(view_link_text, email.getId())
		        			);
		        try{
		            this.mailSender.send(msg);
		        }
		        catch(MailException ex) {
		            System.err.println(ex.getMessage());            
		        }
			}
		}
	}
    
}
