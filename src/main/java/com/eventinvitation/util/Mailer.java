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
    
    private String accept_link_text = null;
    
    private String reject_link_text = null;
    
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }

	public void setAccept_link_text(String accept_link_text) {
		this.accept_link_text = accept_link_text;
	}

	public void setReject_link_text(String reject_link_text) {
		this.reject_link_text = reject_link_text;
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
		        			+ String.format(accept_link_text, email.getId()) 
		        			+ " | " 
		        			+ String.format(reject_link_text, email.getId())
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
