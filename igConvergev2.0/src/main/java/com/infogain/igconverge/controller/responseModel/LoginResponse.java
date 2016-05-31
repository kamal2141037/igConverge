package com.infogain.igconverge.controller.responseModel;

import java.io.Serializable;
import java.util.HashMap;

import com.infogain.igconverge.model.AspireEmployee;

public class LoginResponse implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5526500846198234063L;
	String status;
	String message;
	AspireEmployee aspireEmployee;
	HashMap<String, String> updateResponse;
	/**
	 * 
	 */
	public LoginResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param status
	 * @param message
	 * @param aspireEmployee
	 * @param updateResponse
	 */
	public LoginResponse(String status, String message,
			AspireEmployee aspireEmployee,
			HashMap<String, String> updateResponse) {
		super();
		this.status = status;
		this.message = message;
		this.aspireEmployee = aspireEmployee;
		this.updateResponse = updateResponse;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @return the aspireEmployee
	 */
	public AspireEmployee getAspireEmployee() {
		return aspireEmployee;
	}
	/**
	 * @param aspireEmployee the aspireEmployee to set
	 */
	public void setAspireEmployee(AspireEmployee aspireEmployee) {
		this.aspireEmployee = aspireEmployee;
	}
	/**
	 * @return the updateResponse
	 */
	public HashMap<String, String> getUpdateResponse() {
		return updateResponse;
	}
	/**
	 * @param updateResponse the updateResponse to set
	 */
	public void setUpdateResponse(HashMap<String, String> updateResponse) {
		this.updateResponse = updateResponse;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginResponse [status=");
		builder.append(status);
		builder.append(", message=");
		builder.append(message);
		builder.append(", aspireEmployee=");
		builder.append(aspireEmployee);
		builder.append(", updateResponse=");
		builder.append(updateResponse);
		builder.append("]");
		return builder.toString();
	}
	
}
