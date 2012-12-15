package com.eventinvitation.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventinvitation.domain.dto.UserDTO;
import com.eventinvitation.domain.dto.UserDTOMapper;
import com.eventinvitation.services.UserService;
import com.eventinvitation.util.Validator;

@Controller
@Transactional
public class UserController extends BaseController{
	
	@Autowired
	private UserService userService;

	public UserController() {
		super(UserController.class);
	}
	
	@RequestMapping(value = "/restapi/secured/sign_in", method = RequestMethod.POST)
	public @ResponseBody UserDTO sign_in() {
		return UserDTOMapper.mapUserEntityToUserDTO(getLoggedInUser());
	}
	
	@RequestMapping(value = "/restapi/sign_up", method = RequestMethod.POST,params={"user_name","email","street","full_name","password","state","country","city"}, produces = "application/json")
	public @ResponseBody UserDTO sign_up(
			@RequestParam(value = "user_name", required = true) String username,
			@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "street", required = false) String street,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "full_name", required = true) String fullName,
			@RequestParam(value = "country", required = true) String country,
			@RequestParam(value = "state", required = true) String state,
			@RequestParam(value = "city", required = true) String city) throws Exception{
		
		username = username.trim();
		email    = email.trim();
		street   = street.trim();
		password = password.trim();
		fullName = fullName.trim();
		country  = country.trim();
		state    = state.trim();
		city     = city.trim();		
		
		if(!Validator.isValidPassword(password))
			throw new Exception("Error: Invalid password, Minimum length is 8 digits/characters");
		
		if(!Validator.isValidEmail(email))
			throw new Exception("Error: Invalid email format");
		
		return getUserService().signup(username, email, street, fullName, password,state,country,city);
	}

	@RequestMapping(value = "/restapi/is_user", method = RequestMethod.GET,params={"url"})
	public @ResponseBody String isUser(@RequestParam(value = "url", required = true) String url) {
		return getUserService().checkUserExist(url.trim());
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
