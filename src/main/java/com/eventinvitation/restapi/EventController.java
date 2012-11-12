package com.eventinvitation.restapi;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventinvitation.domain.Event;

@Controller
@Transactional
public class EventController extends BaseController {

	public EventController(){
		super(EventController.class);
	}
	
	@RequestMapping(value = "/restapi/secured/create_event", method = RequestMethod.POST,params={"name","address","time","description","mailling_list"})
	public @ResponseBody Event createEvent
			(
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "address", required = true) String address,
			@RequestParam(value = "time", required = true) String time,
			@RequestParam(value = "description", required = true) String description,
			@RequestParam(value = "mailling_list", required = true) String[] mailling_list
			) {
		return null;
	}
	
	@RequestMapping(value = "/restapi/secured/event", method = RequestMethod.GET,params={"id"})
	public @ResponseBody Event event(@RequestParam(value = "id", required = true) String id) {
		return null;
	}
	
	@RequestMapping(value = "/restapi/secured/events", method = RequestMethod.GET)
	public @ResponseBody List<Event> events() {
		return null;
	}
	
	@RequestMapping(value = "/restapi/secured/accept_event", method = RequestMethod.GET,params={"url"})
	public @ResponseBody void acceptEvent(@RequestParam(value = "url", required = true) String url) {
	}
	
	@RequestMapping(value = "/restapi/secured/reject_event", method = RequestMethod.GET,params={"url"})
	public @ResponseBody void rejectEvent(@RequestParam(value = "url", required = true) String url) {
	}
	
	@RequestMapping(value = "/restapi/secured/accept_list", method = RequestMethod.GET,params={"id"})
	public @ResponseBody void acceptList(@RequestParam(value = "id", required = true) String id) {
	}
}
