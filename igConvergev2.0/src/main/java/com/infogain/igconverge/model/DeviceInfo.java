package com.infogain.igconverge.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "DeviceInfo")
public class DeviceInfo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 199299124945759348L;
	
	@Id
	String tokenId;
	String deviceType;
	String employeeId;
	/**
	 * 
	 */
	public DeviceInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the tokenId
	 */
	public String getTokenId() {
		return tokenId;
	}
	/**
	 * @param tokenId the tokenId to set
	 */
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	/**
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return deviceType;
	}
	/**
	 * @param deviceType the deviceType to set
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceInfo [tokenId=");
		builder.append(tokenId);
		builder.append(", deviceType=");
		builder.append(deviceType);
		builder.append(", employeeId=");
		builder.append(employeeId);
		builder.append("]");
		return builder.toString();
	}
	
	
}
