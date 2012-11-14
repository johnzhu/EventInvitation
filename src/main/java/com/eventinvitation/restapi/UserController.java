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
	
	@RequestMapping(value = "/restapi/sign_up", method = RequestMethod.POST,params={"user_name","email","address","full_name","password"}, produces = "application/json")
	public @ResponseBody UserDTO sign_up(
			@RequestParam(value = "user_name", required = true) String username,
			@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "full_name", required = true) String fullName) throws Exception{
		
		return getUserService().signup(username, email, address, fullName, password);
	}

	@RequestMapping(value = "/restapi/is_user", method = RequestMethod.GET,params={"url"})
	public @ResponseBody boolean isUser(@RequestParam(value = "url", required = true) String url) {
		return getUserService().checkUserExist(url);
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
