package com.infogain.igconverge.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.infogain.igconverge.util.CustomJsonDateDeserializer;
import com.infogain.igconverge.util.JsonDateSerializer;


@Document(collection = "Rides")
public class Ride implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@Id
		private String id;
		private Map<String, String> user;
		
		private String startingLocation;
		private int numberOfSeats;
		private String carNumber;
		private double fare;
		private List<String> days;
		private boolean status;
		private String departureTime;
		private Date postedOn;
		private Date updatedOn;
		private String description;
		private ArrayList<Map<String, String>> occupants;
		
		public Ride() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		
		public Ride(String id, Map<String, String> user,
				String startingLocation, int numberOfSeats, String carNumber,
				double fare, List<String>days, boolean status, String departureTime, Date postedOn,
				String description, ArrayList<Map<String, String>> occupants, Date updatedOn) {
			super();
			this.id = id;
			this.user = user;
			this.startingLocation = startingLocation;
			this.numberOfSeats = numberOfSeats;
			this.carNumber = carNumber;
			this.fare = fare;
			this.days =days;
			this.status = status;
			this.departureTime = departureTime;
			this.postedOn = postedOn;
			this.updatedOn = updatedOn;
			this.description = description;
			this.occupants = occupants;
		}

		

		public Ride(String id, Map<String, String> user,
				String startingLocation, int numberOfSeats, String carNumber,
				double fare, List<String> days, boolean status, String departureTime, Date postedOn, Date updatedOn,
				String description) {
			super();
			this.id = id;
			this.user = user;
			this.startingLocation = startingLocation;
			this.numberOfSeats = numberOfSeats;
			this.carNumber = carNumber;
			this.fare = fare;
			this.status = status;
			this.departureTime = departureTime;
			this.postedOn = postedOn;
			this.updatedOn = updatedOn;
			this.description = description;
		}


		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public Map<String, String> getUser() {
			return user;
		}
		public void setUser(Map<String, String> user) {
			this.user = user;
		}
		public String getStartingLocation() {
			return startingLocation;
		}
		public void setStartingLocation(String startingLocation) {
			this.startingLocation = startingLocation;
		}
		public int getNumberOfSeats() {
			return numberOfSeats;
		}
		public void setNumberOfSeats(int numberOfSeats) {
			this.numberOfSeats = numberOfSeats;
		}
		public String getCarNumber() {
			return carNumber;
		}
		public void setCarNumber(String carNumber) {
			this.carNumber = carNumber;
		}
		public List<String> getDays() {
			return days;
		}

		public void setDays(List<String> days) {
			this.days = days;
		}
		public double getFare() {
			return fare;
		}
		public void setFare(double fare) {
			this.fare = fare;
		}
		public boolean isStatus() {
			return status;
		}
		public void setStatus(boolean status) {
			this.status = status;
		}
		
	
		public String getDepartureTime() {
			return departureTime;
		}
		
		public void setDepartureTime(String departureTime) {
			this.departureTime = departureTime;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public ArrayList<Map<String, String>> getOccupants() {
			return occupants;
		}
		public void setOccupants(ArrayList<Map<String, String>> occupants) {
			this.occupants = occupants;
		}
		
		@JsonSerialize(using = JsonDateSerializer.class)
		public Date getPostedOn() {
			return postedOn;
		}
		
		@JsonDeserialize(using = CustomJsonDateDeserializer.class)
		public void setPostedOn(Date postedOn) {
			this.postedOn = postedOn;
		}

		@JsonSerialize(using = JsonDateSerializer.class)
		public Date getUpdatedOn() {
			return updatedOn;
		}
		
		@JsonDeserialize(using = CustomJsonDateDeserializer.class)
		public void setUpdatedOn(Date updatedOn) {
			this.updatedOn = updatedOn;
		}

		@Override
		public String toString() {
			return "Ride [id=" + id + ", user=" + user + ", startingLocation="
					+ startingLocation + ", numberOfSeats=" + numberOfSeats
					+ ", carNumber=" + carNumber + ", fare=" + fare + ", days="
					+ days + ", status=" + status + ", departureTime="
					+ departureTime + ", postedOn=" + postedOn + ", updatedOn="
					+ updatedOn + ", description=" + description
					+ ", occupants=" + occupants + "]";
		}

		
}