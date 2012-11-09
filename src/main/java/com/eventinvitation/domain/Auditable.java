package com.eventinvitation.domain;

import java.io.Serializable;

public interface Auditable extends Serializable {

	
	public EntityAudit getAudit();

	public void setAudit(EntityAudit audit);

}
