package com.infogain.igconverge.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.infogain.igconverge.util.CustomJsonDateDeserializer;
import com.infogain.igconverge.util.JsonDateSerializer;

@Document(collection = "NotificationInfo")
public class NotificationInfo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8029858796115522424L;
	@Id
	String id;
	String type;
	String title;
	String message;
	Date date;
	
	/**
	 * @param title
	 * @param message
	 * @param date
	 */
	public NotificationInfo(String id, String type, String title, String message, Date date) 
	{
		super();
		this.id = id;
		this.title = title;
		this.message = message;
		this.date = date;
		this.type = type;
	}

	/**
	 * 
	 */
	public NotificationInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the date
	 */
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	@JsonDeserialize(using = CustomJsonDateDeserializer.class)
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NotificationInfo [id=");
		builder.append(id);
		builder.append(", title=");
		builder.append(title);
		builder.append(", message=");
		builder.append(message);
		builder.append(", date=");
		builder.append(date);
		builder.append(", type="+type);
		builder.append("]");
		return builder.toString();
	}
	
}
