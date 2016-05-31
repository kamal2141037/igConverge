package com.infogain.igconverge.model;

import java.io.Serializable;

public class BellRing implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String employeeId;
	
	public BellRing() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BellRing(String employeeId) {
		super();
		this.employeeId = employeeId;
	}

	/**
	 * @return the employeeId
	 */
	public String getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	@Override
	public String toString() {
		return "BellRing [employeeId=" + employeeId + "]";
	}
	
	
	
	
}
