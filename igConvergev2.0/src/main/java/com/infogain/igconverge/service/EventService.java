package com.infogain.igconverge.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.infogain.igconverge.model.Event;
import com.infogain.igconverge.repository.EventRepository;
import com.infogain.igconverge.util.IGConvergeLogger;

/**
 * Implements Event Interface. Will include business logic related to the Event
 * workflow.
 * 
 * @author Infogain Dev Team
 * 
 */
@Service("eventService")
public class EventService implements EventHandler {

	@Autowired
	private EventRepository eventRepository;

	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@IGConvergeLogger
	Logger logger;

	public List<Event> getEventList() 
	{
		logger.debug("Request for get all events method of service.");
		return eventRepository.findAll();
	}
	
	public List<Event> getEventListForApp(Date startDate, Date endDate) 
	{
		logger.debug("Request for get all events method of service.");
		Query query;
		if(endDate != null)
			query = new Query(Criteria.where("startTime").gte(startDate).andOperator(Criteria.where("endTime").lte(endDate)));
		else
			query = new Query(Criteria.where("startTime").gte(startDate));
		query.with(new Sort(Sort.Direction.ASC, "startTime"));
		List<Event> events = mongoTemplate.find(query, Event.class);
		return events;
	}

	

	public void insert(Event event) {
		logger.debug("Insert Event method invoked");
		eventRepository.save(event);
	}

	// calling inbuilt method by custom method.
	public List<Event> findEventBetweenDates(Date startTime,Date endTime) {

		logger.debug("Request for Find Events By Dates method of service.");
		return eventRepository.findBystartTimeBetween(startTime, endTime);

	}

	// call inbuilt remove operation by manual method

	public void deleteEvent(String id) {
		logger.debug("Request for delete Events method of service.");
		eventRepository.delete(id);
	}
	
	public void updateEvent(String id, Event newEvent) {
		logger.debug("Request for update the Event");
		newEvent.setId(id);
		eventRepository.save(newEvent);

	}

	public List<Event> findEventBetweenDates2(Date startTime, Date endTime) {
		// TODO Auto-generated method stub
		return null;
	}

	public Event getEventById(String eventId)
	{
		Query query = new Query(Criteria.where("id").is(eventId));
		query.fields().include("id");
		query.fields().include("eventImage");
		return mongoTemplate.findOne(query, Event.class);
	}
	
	
	public List<Event> getEventByTime(int time) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Event findEventById(String id) {
		// TODO Auto-generated method stub
		
		return eventRepository.findById(id);
	}

}
