package com.infogain.igconverge.controller.responseModel;

import java.io.Serializable;

import com.infogain.igconverge.model.Event;

public class EventServiceResponse implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4492735471581806038L;
	String imageId;
	Event event;
	/**
	 * 
	 */
	public EventServiceResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the imageId
	 */
	public String getImageId() {
		return imageId;
	}
	/**
	 * @param imageId the imageId to set
	 */
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	/**
	 * @return the event
	 */
	public Event getEvent() {
		return event;
	}
	/**
	 * @param event the event to set
	 */
	public void setEvent(Event event) {
		this.event = event;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EventServiceResponse [imageId=");
		builder.append(imageId);
		builder.append(", event=");
		builder.append(event);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
}
