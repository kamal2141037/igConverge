package com.infogain.igconverge.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.infogain.igconverge.util.CustomJsonDateDeserializer;
import com.infogain.igconverge.util.JsonDateSerializer;

@Document(collection = "Events")
public class Event implements Serializable {

	private static final long serialVersionUID = 6795055793670561024L;
	@Id
	private String id;
	private String name;
	private Date startTime;
	private Date endTime;
	private String venue;
	private String description;
	private boolean notification;

	private String category;
	private byte[] eventImage;

	public Event() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Event(String id, String name, Date startTime, Date endTime,
			String venue, String description, boolean notification,
			String category) {
		super();
		this.id = id;
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.venue = venue;
		this.description = description;
		this.notification = notification;
		this.category = category;
	}

	// const with image file for eventImage.
	public Event(String id, String name, Date startTime, Date endTime,
			String venue, String description, boolean notification,
			String category, byte[] eventImage) {
		super();
		this.id = id;
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.venue = venue;
		this.description = description;
		this.notification = notification;
		this.category = category;
		this.eventImage = eventImage;
	}

	public String getDescription() {
		return description;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getEndTime() {
		return endTime;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getStartTime() {
		return startTime;
	}

	public String getVenue() {
		return venue;
	}

	public boolean isNotification() {
		return notification;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonDeserialize(using = CustomJsonDateDeserializer.class)
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNotification(boolean notification) {
		this.notification = notification;
	}

	@JsonDeserialize(using = CustomJsonDateDeserializer.class)
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public byte[] getEventImage() {
		return eventImage;
	}

	public void setEventImage(byte[] eventImage) {
		this.eventImage = eventImage;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", name=" + name + ", startTime="
				+ startTime + ", endTime=" + endTime + ", venue=" + venue
				+ ", description=" + description + ", notification="
				+ notification + ", category=" + category + ", eventImage="
				+ Arrays.toString(eventImage) + "]";
	}

	

}
