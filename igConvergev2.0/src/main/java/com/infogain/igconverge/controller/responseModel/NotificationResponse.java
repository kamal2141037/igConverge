package com.infogain.igconverge.controller.responseModel;

import java.util.List;

import com.infogain.igconverge.model.NotificationInfo;

public class NotificationResponse 
{
	String status;
	List<NotificationInfo> notification;
	/**
	 * 
	 */
	public NotificationResponse() {
		super();
		// TODO Auto-generated constructor stub
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
	 * @return the notification
	 */
	public List<NotificationInfo> getNotification() {
		return notification;
	}
	/**
	 * @param notification the notification to set
	 */
	public void setNotification(List<NotificationInfo> notification) {
		this.notification = notification;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NotificationResponse [status=");
		builder.append(status);
		builder.append(", notification=");
		builder.append(notification);
		builder.append("]");
		return builder.toString();
	}
	
	
}
