package com.eventinvitation.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventinvitation.domain.dto.AcceptListDTO;
import com.eventinvitation.domain.dto.EventDTO;
import com.eventinvitation.services.EventService;

@Controller
@Transactional
public class EventController extends BaseController {
	
	@Autowired
	private EventService eventService;

	public EventController(){
		super(EventController.class);
	}
	
	@RequestMapping(value = "/restapi/secured/create_event", method = RequestMethod.POST,params={"name", "street","country","state", "time","description","mailling_list"}, produces = "application/json")
	public @ResponseBody EventDTO createEvent
			(
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "street", required = true) String street,
			@RequestParam(value = "country", required = true) String country,
			@RequestParam(value = "state", required = true) String state,
			@RequestParam(value = "time", required = true) String time,
			@RequestParam(value = "description", required = true) String description,
			@RequestParam(value = "mailling_list", required = true) String mailling_list
			) {
		String mailling_list_arr[] = mailling_list.split(";|,");
		for(String user : mailling_list_arr)
			System.out.println(user);
		return getEventService().createEvent(name,street,country,state, time, description, mailling_list_arr,getLoggedInUser().getUserDetails());
	}
	
	@RequestMapping(value = "/restapi/secured/event", method = RequestMethod.GET,params={"id"})
	public @ResponseBody EventDTO event(@RequestParam(value = "id", required = true) String id) {
		return getEventService().getEvent(id);
	}
	
	@RequestMapping(value = "/restapi/eventByPattern", method = RequestMethod.GET,params={"pattern"})
	public @ResponseBody EventDTO eventByPattern(@RequestParam(value = "pattern", required = true) String pattern) {
		return getEventService().getEventByPattern(pattern);
	}
	
	@RequestMapping(value = "/restapi/secured/lastEvent", method = RequestMethod.GET)
	public @ResponseBody EventDTO lastEvent() {
		return getEventService().getLastEvent(getLoggedInUser().getId());
	}
	
	@RequestMapping(value = "/restapi/secured/events", method = RequestMethod.GET)
	public @ResponseBody List<EventDTO> events() {
		return getEventService().listEventsByUser(getLoggedInUser().getId());
	}
	
	@RequestMapping(value = "/restapi/secured/accept_event", method = RequestMethod.GET,params={"url"})
	public @ResponseBody void acceptEvent(@RequestParam(value = "url", required = true) String url) throws Exception{
		getEventService().acceptEvent(url,getLoggedInUser().getUserDetails().getEmail());
	}
	
	@RequestMapping(value = "/restapi/reject_event", method = RequestMethod.GET,params={"url"})
	public @ResponseBody void rejectEvent(@RequestParam(value = "url", required = true) String url) throws Exception {
		getEventService().rejectEvent(url);
	}
	
	@RequestMapping(value = "/restapi/secured/accept_list", method = RequestMethod.GET,params={"id"})
	public @ResponseBody List<AcceptListDTO> acceptList(@RequestParam(value = "id", required = true) String id) {
		return getEventService().getEventAttendance(id);
	}
	
	@RequestMapping(value = "/restapi/secured/refresh", method = RequestMethod.GET,params={"id","online_flag","list_name"})
	public @ResponseBody List<AcceptListDTO> refresh(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "online_flag", required = true) String onlineFlag,@RequestParam(value = "list_name", required = true) String listName) {
		return getEventService().refreshListStatus(id,onlineFlag,listName);
	}
	
	@RequestMapping(value = "/restapi/secured/refresh_user_status", method = RequestMethod.GET,params={"id","online_flag"})
	public @ResponseBody boolean refreshUserStatus(@RequestParam(value = "id", required = true) String id,@RequestParam(value = "online_flag", required = true) String onlineFlag) {
		return getEventService().refreshUserStatus(id,onlineFlag);
	}

	public EventService getEventService() {
		return eventService;
	}

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}
}
