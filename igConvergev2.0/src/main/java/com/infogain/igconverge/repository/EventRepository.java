package com.infogain.igconverge.repository; 

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.infogain.igconverge.model.Event;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {

	// inbuilt method by naming conventions.
	public List<Event> findBystartTimeBetween(Date startTime, Date endTime);
	
	
	public Event findById(String eventId);

}
