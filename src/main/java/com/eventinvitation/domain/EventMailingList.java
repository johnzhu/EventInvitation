package com.eventinvitation.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "EVENT_MAILING_LIST")
public class EventMailingList extends AbstractEntity implements Auditable{

	private static final long serialVersionUID = -8668444431126870460L;

	@Id
	@GeneratedValue(generator = "db-guid")
	@GenericGenerator(name = "db-guid", strategy = "guid")
	@Column(name = "EMAIL_TOKEN")
	private String id;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="EVENT_ID")
	private Event event;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "STATUS")
	private String status;
	
	@Embedded
	private EntityAudit audit = new EntityAudit();
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public EntityAudit getAudit() {
		return this.audit;
	}

	public void setAudit(EntityAudit audit) {
		this.audit = audit;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
