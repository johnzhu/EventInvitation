package com.eventinvitation.restapi;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController extends BaseController{

	public UserController() {
		super(UserController.class);
	}
	
	@RequestMapping(value = "/restapi/secured/servertime", method = RequestMethod.GET)
	public @ResponseBody String servertime(ModelMap model) {
		LOGGER.info("Inside UserControlelr/servertime method");
		return "Server Time" + System.currentTimeMillis();
	}


}
