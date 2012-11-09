package com.eventinvitation.config.exception;

import org.springframework.security.core.AuthenticationException;

public class UserNotExistsException extends AuthenticationException {

	private static final long serialVersionUID = -29012397494818019L;

	public UserNotExistsException(String message) {
		super(message);
	}

}
