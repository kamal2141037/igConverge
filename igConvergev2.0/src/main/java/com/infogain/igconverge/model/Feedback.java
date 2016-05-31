package com.infogain.igconverge.model;

import java.io.Serializable;

public class Feedback implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Employee employee;
	private Ratings ratings;

	public Feedback() {

		super();
		// TODO Auto-generated constructor stub
	}

	public Feedback(Employee employee) {
		super();
		this.employee = employee;
	}

	public Feedback(Employee employee, Ratings ratings) {
		super();
		this.employee = employee;
		this.ratings = ratings;
	}

	public Employee getEmployee() {
		return employee;
	}

	public Ratings getRatings() {
		return ratings;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public void setRatings(Ratings ratings) {
		this.ratings = ratings;
	}

	@Override
	public String toString() {
		return "Feedback [employee=" + employee + ", ratings=" + ratings + "]";
	}

}
