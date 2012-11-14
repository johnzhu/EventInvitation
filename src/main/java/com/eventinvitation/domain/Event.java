package com.eventinvitation.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "EVENT")
public class Event extends AbstractEntity implements Auditable {

	private static final long serialVersionUID = -3096715627702373545L;

	@Id
	@GeneratedValue(generator = "db-guid")
	@GenericGenerator(name = "db-guid", strategy = "guid")
	@Column(name = "EVENT_ID")
	private String id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "TIME")
	private String time;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private UserDetailsEntity owner;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="USER_EVENTS",joinColumns={@JoinColumn(name="EVENT_ID")},inverseJoinColumns={@JoinColumn(name="USER_ID")})
	private List<UserDetailsEntity> attendes;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="EVENT_ID")
	private List<EventMailingList> maillingList;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDdescription(String description) {
		this.description = description;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<UserDetailsEntity> getAttendes() {
		return attendes;
	}

	public void setAttendes(List<UserDetailsEntity> attendes) {
		this.attendes = attendes;
	}

	public UserDetailsEntity getOwner() {
		return owner;
	}

	public void setOwner(UserDetailsEntity owner) {
		this.owner = owner;
	}

	public List<EventMailingList> getMaillingList() {
		return maillingList;
	}

	public void setMaillingList(List<EventMailingList> maillingList) {
		this.maillingList = maillingList;
	}

}
