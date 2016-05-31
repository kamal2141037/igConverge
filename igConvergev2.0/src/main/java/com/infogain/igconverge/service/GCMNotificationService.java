package com.infogain.igconverge.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.infogain.igconverge.model.DeviceInfo;
import com.infogain.igconverge.util.IGConvergeLogger;
import com.infogain.igconverge.util.IGConvergeUtil;
import com.mongodb.util.JSON;

@Service("gcmNotificationService")
public class GCMNotificationService 
{
	@IGConvergeLogger
	Logger logger;
	
	@Autowired
	DeviceInfoHandler deviceInfoHandler;
	
	private static final int MULTICAST_SIZE = 1000;
	private Sender sender;
	private static final Executor threadPool = Executors.newFixedThreadPool(5);
	
	public void pushAndroidNotification(HashMap<String,String> data, List<DeviceInfo> androidDevice)
	{
		sender = new Sender(IGConvergeUtil.SERVER_ANDROID_API_KEY);
		String status;
		if (androidDevice.isEmpty()) 
		{
			status = "Message ignored as there is no device registered!";
		} else 
		{
			if (androidDevice.size() == 1) 
			{
				String registrationId = androidDevice.get(0).getTokenId();
				Message message = new Message.Builder().delayWhileIdle(true).addData(IGConvergeUtil.MESSAGE_KEY, JSON.serialize(data)).build();
				Result result = null;
				try 
				{
					result = sender.send(message, registrationId, 5);
				} catch (IOException e) 
				{
					logger.error("Error in GCMService",e);//.getCause());
					e.printStackTrace();
				}
				status = "Sent message to one device: " + result;
			} else 
			{
				int total = androidDevice.size();
				List<String> partialDevices = new ArrayList<String>(total);
				int counter = 0;
				int tasks = 0;
				for (DeviceInfo device : androidDevice) 
				{
					counter++;
					partialDevices.add(device.getTokenId());
					int partialSize = partialDevices.size();
					if (partialSize == MULTICAST_SIZE || counter == total)
					{
						asyncSend(partialDevices, data);
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
	private void asyncSend(List<String> partialDevices, final HashMap<String, String> data) 
	{
		final List<String> devices = new ArrayList<String>(partialDevices);
		threadPool.execute(new Runnable() 
		{
			public void run() 
			{
				Message message = new Message.Builder().delayWhileIdle(true).addData(IGConvergeUtil.MESSAGE_KEY, JSON.serialize(data)).build();
				MulticastResult multicastResult;
				try 
				{
					multicastResult = sender.send(message, devices, 5);
				} catch (IOException e) 
				{
					logger.debug("Exception Handler");
					logger.error(e.getCause());
					return;
				}
				List<Result> results = multicastResult.getResults();
				for (int i = 0; i < devices.size(); i++)
				{
					String regId = devices.get(i);
					Result result = results.get(i);
					String messageId = result.getMessageId();
					if (messageId != null) 
					{
						logger.debug("Succesfully sent message to device: " + regId + "; messageId = " + messageId); 
						String canonicalRegId = result.getCanonicalRegistrationId();
						if (canonicalRegId != null) 
						{
//							// same device has more than one registration id: update it
//							logger.info("canonicalRegId " + canonicalRegId);
//							Datastore.updateRegistration(regId, canonicalRegId);
						}
					} else 
					{
						String error = result.getErrorCodeName();
						if (error.equals(Constants.ERROR_NOT_REGISTERED)) 
						{
							logger.info("Unregistered device: " + regId);
							deviceInfoHandler.removeUnregisteredDevice(regId, "android");
						} 
						else 
						{
							logger.error("Error sending message to " + regId + ": " + error);
						}
					}
				}
			}
		});
	}
}
