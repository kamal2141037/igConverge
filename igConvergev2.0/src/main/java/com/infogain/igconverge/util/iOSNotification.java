package com.infogain.igconverge.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;

import javax.servlet.ServletContext;

import org.slf4j.Logger;

import com.infogain.igconverge.model.DeviceInfo;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.PayloadBuilder;
import com.notnoop.apns.ReconnectPolicy;


public class iOSNotification 
{
	
	@IGConvergeLogger
	Logger logger;
	
	private static final int MULTICAST_SIZE = 1000;
	
	ApnsService service;
	
	ServletContext servletContext;
	
	
	public iOSNotification(ServletContext servletContext) 
	{
		this.servletContext = servletContext;
		InputStream resource = servletContext.getResourceAsStream("WEB-INF/production.p12");
		service = APNS.newService().
				withCert(resource, "P1G@TQMv").
				withGatewayDestination("gateway.push.apple.com", 2195).
				withReconnectPolicy(ReconnectPolicy.Provided.NEVER).
				build();
	}
	
	public void test(String type,String title,String message, List<DeviceInfo> devices) throws CommunicationException, KeystoreException
	{
		String status = "";
		service.start();
		if (devices.isEmpty()) 
		{
			status = "Message ignored as there is no device registered!";
		} else 
		{
			PayloadBuilder payloadBuilder = APNS.newPayload();
			payloadBuilder.badge(Constants.BADGE_KEY).sound(Constants.DEFAULT_MESSAGE_KEY);
			payloadBuilder.alertBody(message);
			payloadBuilder.customField("title", title);
			payloadBuilder.customField("category", type);
			String payload = payloadBuilder.build();
			if (devices.size() == 1) 
			{
				String registrationId = devices.get(0).getTokenId();
				try 
				{
					service.push(registrationId, payload);
				} catch (Exception e) 
				{
					logger.debug("Exception Handler");
				}
			} else 
			{
				int total = devices.size();
				Set<String> partialDevices = new HashSet<String>();
				int counter = 0;
				int tasks = 0;
				for (DeviceInfo device : devices) 
				{
					counter++;
					partialDevices.add(device.getTokenId());
					int partialSize = partialDevices.size();
					if (partialSize == MULTICAST_SIZE || counter == total) 
					{
						asyncSend(partialDevices, payload);
						partialDevices.clear();
						tasks++;
					}
				}
				status = "Asynchronously sending " + tasks + " multicast messages to " + total + " devices";
			}
		}
		System.out.println(status);
	}
	
	/**
	 * Send message to devices asynchronously.
	 * @param partialDevices
	 */
	private void asyncSend(Set<String> partialDevices, final String payload) 
	{
		try 
		{
			service.push(partialDevices, payload);
		} catch (Exception e) 
		{
			logger.debug("Exception Handler");
		}
	}
	
	static class Constants 
	{
		public static String ALERT_MESSAGE_KEY = "alertMessage";
		public static String DEFAULT_MESSAGE_KEY = "default";
		public static int BADGE_KEY = 1;
	}

}

