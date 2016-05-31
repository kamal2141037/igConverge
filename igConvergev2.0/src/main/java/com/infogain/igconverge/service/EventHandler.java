package com.infogain.igconverge.service;

import java.util.Date;
import java.util.List;

import com.infogain.igconverge.model.Event;

/**
 * Interface for Event Service - includes all functions related to Event
 * workflow
 * 
 * @author Infogain Dev Team
 * 
 */

public interface EventHandler {

	public List<Event> getEventList();
	
	public List<Event> getEventListForApp(Date startDate, Date endDate);
	
	public Event getEventById(String eventId);

	public void insert(Event event);

	public void deleteEvent(String id);

	public void updateEvent(String id, Event event);

	// custom method for calling.
	public List<Event> findEventBetweenDates(Date startTime, Date endTime);
	
	// custom method for calling.
		public List<Event> findEventBetweenDates2(Date startTime, Date endTime);
		
		public List<Event> getEventByTime(int time);
	
	public Event findEventById(String id);

}
