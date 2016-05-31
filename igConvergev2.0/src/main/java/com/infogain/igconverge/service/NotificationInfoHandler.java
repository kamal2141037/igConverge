package com.infogain.igconverge.service;

import java.util.Date;
import java.util.List;

import com.infogain.igconverge.model.NotificationInfo;

public interface NotificationInfoHandler 
{
	public void saveNotification(NotificationInfo notificationInfo);
	
	public List<NotificationInfo> fetchNotification(String type, Date date);
}
