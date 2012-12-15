package com.eventinvitation.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "USER_DETAILS")
public class UserDetailsEntity extends AbstractEntity implements Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "foreign")
	@GenericGenerator(name = "foreign", strategy = "foreign", parameters = @Parameter(name = "property", value = "user"))
	@Column(name = "USER_ID")
	private String id;

	@Column(name = "EMAIL")
	@NaturalId
	private String email;

	@Embedded
	private EntityAudit audit = new EntityAudit();

	@Column(name = "NAME")
	private String name;

	@OneToOne
	@PrimaryKeyJoinColumn
	private Address address;

	@OneToOne
	@PrimaryKeyJoinColumn
	private UserEntity user;
	
	@OneToMany(mappedBy="owner")
	private List<Event> myEvents;

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public EntityAudit getAudit() {
		return audit;
	}

	public void setAudit(EntityAudit audit) {
		this.audit = audit;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public List<Event> getMyEvents() {
		return myEvents;
	}

	public void setMyEvents(List<Event> myEvents) {
		this.myEvents = myEvents;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}