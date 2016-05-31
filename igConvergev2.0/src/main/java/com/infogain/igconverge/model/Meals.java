package com.infogain.igconverge.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.infogain.igconverge.util.CustomJsonDateDeserializer;
import com.infogain.igconverge.util.JsonDateSerializer;

@Document(collection = "Meals")
public class Meals implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2559370351342760091L;
	@Id
	private String id;
	private String name;
	private List<Item> items;
	private List<Feedback> feedbacks;
	private Date date;
	
	// bell ring total count for 1 meal
	private int bellCount=0;
	private List<BellRing> bellRingByEmployees;
	
	
	public Meals() {
		super();
		// TODO Auto-generated constructor stub
	}

// constructor without feedback
	public Meals(String id, String name, List<Item> items, Date date) {
		super();
		this.id = id;
		this.name = name;
		this.items = items;
		this.date = date;
	}


	public Meals( String name, List<Item> items,
			 Date date) {
		super();
	
		this.name = name;
		this.items = items;
		
		this.date = date;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getDate() {
		return date;
	}

	public List<Feedback> getFeedbacks() {
		return feedbacks;
	}

	public String getId() {
		return id;
	}

	public List<Item> getItems() {
		return items;
	}

	public String getName() {
		return name;
	}

	@JsonDeserialize(using = CustomJsonDateDeserializer.class)
	public void setDate(Date date) {
		this.date = date;
	}

	public void setFeedbacks(List<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the bellRingCount
	 */
	public int getBellCount() {
		return bellCount;
	}

	/**
	 * @param bellRingCount the bellRingCount to set
	 */
	public void setBellCount(int bellRingCount) {
		this.bellCount = bellRingCount;
	}

	/**
	 * @return the bellRingByEmployees
	 */
	public List<BellRing> getBellRingByEmployees() {
		return bellRingByEmployees;
	}

	/**
	 * @param bellRingByEmployees the bellRingByEmployees to set
	 */
	public void setBellRingByEmployees(List<BellRing> bellRingByEmployees) {
		this.bellRingByEmployees = bellRingByEmployees;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Meals [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", items=");
		builder.append(items);
		builder.append(", date=");
		builder.append(date);
		builder.append(", bellRingCount=");
		builder.append(bellCount);
		builder.append(", bellRingByEmployees=");
		builder.append(bellRingByEmployees);
		builder.append("]");
		return builder.toString();
	}
	
	
	
		

}
