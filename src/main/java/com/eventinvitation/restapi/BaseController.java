package com.eventinvitation.restapi;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventinvitation.config.exception.UserNotExistsException;
import com.eventinvitation.domain.UserEntity;
import com.eventinvitation.domain.dto.UserDTO;
import com.eventinvitation.domain.dto.UserDTOMapper;

public abstract class BaseController {

	protected static Logger LOGGER;

	public BaseController(Class<? extends BaseController> controllerClass) {
		LOGGER = LoggerFactory.getLogger(controllerClass);
	}	

	@InitBinder
	protected void initBinder(HttpServletRequest request, WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd-MM-yyyy"), true));
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public String handleException(Exception ex, HttpServletRequest request) {
		LOGGER.error("handle  exception- Catching: ex = {}", ex);
		return ex.getMessage();
	}

	protected final UserDTO getLoggedInUser() throws UserNotExistsException{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() != null && !"anonymousUser".equalsIgnoreCase(authentication.getPrincipal().toString())) {
			UserEntity userEntity = (UserEntity) authentication.getPrincipal();
			return UserDTOMapper.mapUserEntityToUserDTO(userEntity);
		}
		else{
			throw new UserNotExistsException("Un-authorized user");
		}
	}
}