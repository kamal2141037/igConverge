package com.infogain.igconverge.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.infogain.igconverge.controller.responseModel.EventServiceResponse;
import com.infogain.igconverge.controller.responseModel.LoginResponse;
import com.infogain.igconverge.controller.responseModel.NotificationResponse;
import com.infogain.igconverge.model.AspireEmployee;
import com.infogain.igconverge.model.DeviceInfo;
import com.infogain.igconverge.model.Employee;
import com.infogain.igconverge.model.Event;
import com.infogain.igconverge.model.Feedback;
import com.infogain.igconverge.model.Mail;
import com.infogain.igconverge.model.Meals;
import com.infogain.igconverge.model.NotificationInfo;
import com.infogain.igconverge.model.Ratings;
import com.infogain.igconverge.model.UpdateInfo;
import com.infogain.igconverge.repository.MealRepository;
import com.infogain.igconverge.service.DeviceInfoHandler;
import com.infogain.igconverge.service.EventHandler;
import com.infogain.igconverge.service.GCMNotificationService;
import com.infogain.igconverge.service.Mailer;
import com.infogain.igconverge.service.MealHandler;
import com.infogain.igconverge.service.NotificationInfoHandler;
import com.infogain.igconverge.service.UpdateInfoHandler;
import com.infogain.igconverge.util.AspireTest;
import com.infogain.igconverge.util.IGConvergeLogger;
import com.infogain.igconverge.util.IGConvergeUtil;
import com.infogain.igconverge.util.IGConvergeUtil.NotificationType;
import com.infogain.igconverge.util.LDAPValidation;
import com.infogain.igconverge.util.iOSNotification;
import com.mongodb.util.JSON;

@Controller
public class MobileServicesController {
	@Autowired
	ServletContext servletContext;

	@Autowired
	AspireTest aspireTest;

	@Autowired
	EventHandler eventHandler;

	@Autowired
	MealHandler mealhandler;

	@Autowired
	UpdateInfoHandler updateInfoHandler;

	@IGConvergeLogger
	Logger logger;

	@Autowired
	MealRepository mealRepository;

	@Autowired
	LDAPValidation ldapValidation;

	@Autowired
	DeviceInfoHandler deviceInfoHandler;

	@Autowired
	GCMNotificationService gcmNotificationService;

	@Autowired
	NotificationInfoHandler notificationInfoHandler;
	@Autowired
	Mailer mailer;

	private static int index = 0;

	/*
	 * 1. Service To Authenticate Employee Using userName and password From LDAP
	 * 
	 * @param userName the userName to set
	 * 
	 * @param password the password to set
	 * 
	 * @return loginResponse
	 */
	@RequestMapping(value = { "/mobile/login" }, method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	LoginResponse login(@RequestParam("userName") String userName,
			@RequestParam("password") String password, String version,
			String deviceType) {
		LoginResponse loginResponse = new LoginResponse();
		// LDAP Validation !-- Not use for testing purpose

		// TODO: Check For @infogain.com
		// if (userName.indexOf('@') != -1)
		// {
		// userName = userName.substring(0,userName.indexOf('@'));
		// }
		if (userName.contains("@infogain.com")) {
			int index = userName.lastIndexOf("@infogain.com");
			userName = userName.substring(0, index);
		}

		HashMap<String, String> ldapResponse = ldapValidation
				.loginCredentialValidation(userName, password);
		if (ldapResponse.get("status").toString().trim()
				.equalsIgnoreCase("success")) {
			AspireEmployee aspireEmployee = fetchEmployeeDetails(userName);
			logger.info("Login by: Id:"+aspireEmployee.getId()+ "Emp Username: "+userName+ "on Timestamp: "+ new Date());
			loginResponse.setAspireEmployee(aspireEmployee);
			if (version != null && deviceType != null)
				loginResponse.setUpdateResponse(updateInfoMethod(
						Double.parseDouble(version), deviceType));
		}
		loginResponse.setStatus(ldapResponse.get("status").toString().trim());
		loginResponse.setMessage(ldapResponse.get("message").toString().trim());
		return loginResponse;
	}

	/*
	 * 2. Service To Fetch Employee Details From Aspire DB
	 * 
	 * @param userName the userName to set
	 * 
	 * @return aspireEmployee object
	 */
	public AspireEmployee fetchEmployeeDetails(
			@RequestParam("userName") String userName) {
		List<AspireEmployee> aspireEmployees = aspireTest
				.getAspireData(userName);
		for (AspireEmployee aspireEmployee : aspireEmployees) {
			if (aspireEmployee.getIsActive().equals("1"))
				return aspireEmployee;
		}
		return null;
	}

	/*
	 * 3. Service To Fetch Meal Details From igConverge DB
	 * 
	 * @param mealName the mealName to set
	 * 
	 * @param startDate the startDate to set
	 * 
	 * @param endDate the endDate to set
	 * 
	 * @return the {"employeeId":"", "employeeName":"", "employeeMailId":"",
	 * "employeeImageName"}
	 */
	@RequestMapping(value = "/mobile/fetchmeals", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<Meals> fetchMeals(@RequestParam("name") String mealName,
			@RequestParam("startDate") Date startDate,
			@RequestParam("endDate") Date endDate) {
		List<Meals> meals = mealhandler.fetchMealsForApp(mealName, startDate,
				endDate);
		return meals;
	}

	/*
	 * 4. Service To fetch average feedback for specific meal from igConverge DB
	 * 
	 * @param name the name to set
	 * 
	 * @param startDate the startDate to set
	 * 
	 * @param endDate the endDate to set
	 * 
	 * @return responseMessage
	 */
	@RequestMapping(value = "/mobile/fetchfeedback", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String fetchFeedback(@RequestParam("name") String mealName,
			@RequestParam("startDate") Date startDate,
			@RequestParam("endDate") Date endDate) {

		try {
			logger.debug("Request for AverageFeedback By Date Range and Name");
			Gson gson = new Gson();
			String responseMessage = gson.toJson(mealhandler.fetchFeedback(
					mealName, startDate, endDate));
			return responseMessage;
		} catch (Exception e) {
			logger.error("Sorry, Something went wrong.\nException is: ", e);
		}
		return null;
	}

	// Service to Save bell rings.
	// @RequestMapping(value = { "/mobile/ringbell" }, method =
	// RequestMethod.POST)
	// public String startRingingBell(
	// @RequestParam String mealId,
	// String employeeId) {
	// String bellStatus = "hasNotRungYet";
	// BellRing bellRing = new BellRing(employeeId);
	// try {
	// logger.debug("Request to ring the bell");
	// bellStatus = mealhandler.saveBellRingByEmployee(mealId, bellRing);
	// } catch (Exception e) {
	// logger.error("Sorry, Something went wrong.\nException is: ", e);
	// return bellStatus;
	// }
	// return bellStatus;
	// }
	// --------------------------------------------------------------------------------
	/*
	 * 5. Service To Save User Feedback To igConverge DB
	 * 
	 * @param mealId the mealId to set
	 * 
	 * @param employeeId the employeeId to set
	 * 
	 * @param employeeName the employeeName to set
	 * 
	 * @param feedbackComment the feedbackComment to set
	 * 
	 * @param feedbackRating the feedbackRating to set
	 * 
	 * @param mealImage the mealImage to set
	 * 
	 * @return responseMessage
	 */
	@RequestMapping(value = { "/mobile/savefeedback" }, method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	String updateMeal(@RequestParam("mealId") String id,
			@RequestParam("employeeId") String employeeId,
			@RequestParam("employeeName") String employeeName,
			@RequestParam("feedbackRating") String feedbackRating,
			String feedbackComment, MultipartFile mealImage) {
		String responseMessage = "";
		String status = "";
		try {
			logger.debug("Request to Save the feedback");

			if (!feedbackRating.equals("0")) {
				Employee employee = new Employee();
				employee.setId(employeeId);
				employee.setName(employeeName);

				Ratings ratings = new Ratings();
				ratings.setComment(feedbackComment);
				ratings.setScore(Integer.parseInt(feedbackRating));

				if (mealImage != null) {
					ratings.setMealImage(mealImage.getBytes());
				} else {
					logger.debug("Image Empty");
				}

				Feedback feedback = new Feedback();
				feedback.setEmployee(employee);
				feedback.setRatings(ratings);

				logger.debug(feedback);

				responseMessage = mealhandler.updateFeedback(id, feedback);
				if (responseMessage.equalsIgnoreCase("feedbackSaved")) {
					responseMessage = "Feedback successfully submitted";
				} else if (responseMessage
						.equalsIgnoreCase("empCommentAlreadyExist")) {
					responseMessage = "Feedback already submitted, Not allowed to submit again";
				} else if (responseMessage.equalsIgnoreCase("feedbackNotSaved")) {
					responseMessage = "Feedback not submitted";
				}
				status = "success";
			} else {
				responseMessage = "Provide Rating For Specified Meal";
			}
			status = "success";
		} catch (Exception e) {
			logger.error("Sorry, Something went wrong.\nException is: ", e);
			responseMessage = "Connection Error";
			status = "failure";
		}
		HashMap<String, String> feedbackStatus = new HashMap<String, String>();
		feedbackStatus.put("status", status);
		feedbackStatus.put("message", responseMessage);
		return JSON.serialize(feedbackStatus);
	}

	/*
	 * 6. Service To Fetch Events Details From igConverge DB
	 * 
	 * @return the {"id":"", "name":"", "startDate":"", "endDate:"", "venue":"",
	 * "description":"", "category":""}
	 */
	@RequestMapping(value = "/mobile/fetchevents", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<EventServiceResponse> getAllEvents(String checkValue) {
		List<EventServiceResponse> eventServiceResponses = new ArrayList<EventServiceResponse>();
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		date = calendar.getTime();
		List<Event> events;
		// Date startDate =
		// IGConvergeUtil.returnStartDate(IGConvergeUtil.START_DATE_MODIFIER_ZERO);
		// Date endDate = IGConvergeUtil.returnEndDate(startDate,
		// IGConvergeUtil.END_DATE_AFTER_ONE);
		if (checkValue == null)
			events = eventHandler.getEventListForApp(date, null);
		else
			events = eventHandler.getEventListForApp(date, null);
		logger.debug("Event list : "+events.toString());
		for (Event event : events) {
			EventServiceResponse eventServiceResponse = new EventServiceResponse();
			if (event.getEventImage() != null) {
				eventServiceResponse.setImageId(event.getId());
			}
			event.setEventImage(null);
			eventServiceResponse.setEvent(event);
			eventServiceResponses.add(eventServiceResponse);
		}
		return eventServiceResponses;
	}

	/*
	 * 7. Service To Fetch Events Image From igConverge DB
	 * 
	 * @param imageId the imageId to set
	 * 
	 * @return responseMessage
	 */
	@SuppressWarnings("restriction")
	@RequestMapping(value = "/mobile/fetcheventsimage", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	String fetchEventImage(@RequestParam("imageId") String imageId) {
		String responseMessage = "";
		HashMap<String, String> eventImage = new HashMap<String, String>();
		try {
			logger.debug("Request for Fetch Events web service.");
			Event event = eventHandler.getEventById(imageId);
			if (event.getEventImage().length > 0) {
				sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
				eventImage.put("image", encoder.encode(event.getEventImage()));
				eventImage.put("status", "success");
			} else {
				eventImage.put("status", "failure");
			}
			eventImage.put("id", event.getId());

			responseMessage = JSON.serialize(eventImage);
		} catch (Exception e) {
			eventImage.put("status", "failure");
		}
		return responseMessage;
	}

	/*
	 * 8. Service To check update version of application
	 * 
	 * @param version the version to set
	 * 
	 * @param deviceType the deviceType to set
	 * 
	 * @return checkStatus
	 */
	@RequestMapping(value = "/mobile/checkupdate", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public HashMap<String, String> checkUpdate(
			@RequestParam("version") double version,
			@RequestParam("deviceType") String deviceType) {
		return updateInfoMethod(version, deviceType);
	}

	/*
	 * 9. Service To register user devices for notifications
	 * 
	 * @param tokenId the version to tokenId
	 * 
	 * @param deviceType the deviceType to set
	 * 
	 * @param id the id to set(employeeId)
	 * 
	 * @return response
	 */
	@RequestMapping(value = "/mobile/registerdevice", method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, String> registerDevice(
			@RequestParam("deviceToken") String deviceToken,
			@RequestParam("deviceType") String deviceType, String id) {
		//System.out.println("Register Device");
		logger.info("Employee Id: "+id+" -- with Device Id: "+deviceToken+" -- on Timestamp: "+ new Date());
		HashMap<String, String> response = new HashMap<String, String>();
		DeviceInfo deviceInfo = deviceInfoHandler.findByTokenIdAndDeviceType(
				deviceToken, deviceType);
		if (deviceInfo == null) {
			deviceInfo = new DeviceInfo();
			deviceInfo.setDeviceType(deviceType);
			deviceInfo.setTokenId(deviceToken);
			deviceInfo.setEmployeeId(id);
			try {
				deviceInfoHandler.saveInfo(deviceInfo);
				deviceInfo = deviceInfoHandler.findByTokenIdAndDeviceType(
						deviceToken, deviceType);
				if (deviceInfo != null)
					response.put("status", "true");
				else
					response.put("status", "false");
			} catch (Exception e) {
				response.put("status", "false");
			}

		} else {
			deviceInfo.setEmployeeId(id);
			deviceInfoHandler.saveInfo(deviceInfo);
			deviceInfo = deviceInfoHandler.findByTokenIdAndDeviceType(
					deviceToken, deviceType);
			if (deviceInfo.getEmployeeId().equalsIgnoreCase(id))
				response.put("status", "true");
			else
				response.put("status", "false");
		}
		return response;
	}

	@RequestMapping(value = "/mobile/sendeventnotification", method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, String> sendEventNotification(
			@RequestParam("id") String event_id) throws Exception {
		HashMap<String, String> response = new HashMap<String, String>();
		Event event = eventHandler.findEventById(event_id);
		System.out.println(event);
		String statusMessage = "";
		if (event == null) {

		} else {
			System.out.println("enter into ");
			Mail mail = new Mail();
			mail.setMailSubject(event.getName());
			mail.setMailContent(event.getDescription());
			if (event.getEventImage() != null) {
				// sun.misc.BASE64Encoder encoder = new
				// sun.misc.BASE64Encoder();
				mail.setMailImage(event.getEventImage());
				System.out.println(mail.getMailImage());
			}
			mail.setMailFrom("Admin Team<Adminteam@infogain.com>");
			String[] to = { "AdminDept@infogain.com" };
			mail.setMailTo(to);
			String[] cc = { "igConverge@infogain.com" };
			mail.setMailCc(cc);
			try {
				mailer.sendMail(mail);
				System.out.println("mail sent");
				pushEventNotification(event.getName(), event.getDescription());
				statusMessage = "success";
			} catch (MessagingException e) {
				logger.debug("Mailing Exception");
				statusMessage = "failure";
			} catch (Exception exception) {
				logger.debug("Phone Exception");
				statusMessage = "success";
			}
		}
		response.put("status", statusMessage);
		return response;
	}

	/*
	 * 10. Service To check push notification to users
	 * 
	 * @param title the title to set
	 * 
	 * @param message the message to set
	 * 
	 * @return void
	 */
	@RequestMapping(value = "/mobile/sendnotification", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	HashMap<String, String> sendNotification(
			@RequestParam("title") String title,
			@RequestParam("message") String message) throws Exception {

		HashMap<String, String> response = new HashMap<String, String>();
		String statusMessage = "";
		try {
			NotificationInfo notificationInfo = new NotificationInfo();
			notificationInfo.setTitle(title);
			notificationInfo.setMessage(message);
			notificationInfo.setDate(new Date());
			notificationInfo.setType(NotificationType.Zero.getValue());
			
			notificationInfoHandler.saveNotification(notificationInfo);
			// Code for sending instant mail
			String[] to = { "AdminDept@infogain.com" };
			String from = "Admin Team<Adminteam@infogain.com>";
			String[] cc = { "igConverge@infogain.com" };
			Mail mail = new Mail();
			mail.setMailSubject(title);
			mail.setMailContent(message);
			mail.setMailFrom(from);
			mail.setMailCc(cc);
			mail.setMailTo(to);
			mailer.sendMail(mail);

			System.out.println("after sending mail");

			List<DeviceInfo> androidDevices = deviceInfoHandler
					.findAllDeviceByDeviceType("android");
			List<DeviceInfo> iOSDevices = deviceInfoHandler
					.findAllDeviceByDeviceType("iOS");
			sendGenericNotification(NotificationType.Zero.getValue(), title, message, androidDevices,
					iOSDevices);
			statusMessage = "success";
		}
		// catch (MessagingException e)
		// {
		// logger.debug("Mailing Exception");
		// statusMessage = "failure";
		// }
		catch (Exception exception) {
			logger.debug(exception.getLocalizedMessage());
			statusMessage = exception.getLocalizedMessage();
		}
		response.put("status", statusMessage);
		return response;
	}

	/*
	 * 11. Service To check event push notification to users
	 * 
	 * @param version the version to set
	 * 
	 * @param deviceType the deviceType to set
	 * 
	 * @return void
	 */
	public void pushEventNotification(String title, String message)
			throws CommunicationException, KeystoreException {
		List<DeviceInfo> androidDevices = deviceInfoHandler
				.findAllDeviceByDeviceType("android");
		List<DeviceInfo> iOSDevices = deviceInfoHandler
				.findAllDeviceByDeviceType("iOS");
		sendGenericNotification(NotificationType.One.getValue(), title, message, androidDevices, iOSDevices);
	}

	/*
	 * 12. Service To log out users
	 * 
	 * @param deviceToken the deviceToken to set
	 * 
	 * @param deviceType the deviceType to set
	 * 
	 * @return response
	 */
	@RequestMapping(value = "/mobile/logout", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	HashMap<String, String> logout(
			@RequestParam("deviceToken") String deviceToken,
			@RequestParam("deviceType") String deviceType) {
		HashMap<String, String> response = new HashMap<String, String>();
		try {
			deviceInfoHandler.removeUnregisteredDevice(deviceToken, deviceType);
			response.put("status", "success");
			response.put("message", "Successfully Logout");
		} catch (Exception exception) {
			response.put("status", "failure");
			response.put("message", "Network Error");
		}
		return response;
	}

	/*
	 * 13. Service To fetch notifications
	 * 
	 * @return response
	 */
	@RequestMapping(value = "/mobile/fetchnotification/{type}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	NotificationResponse fetchNotification(@PathVariable String type) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -2);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		date = calendar.getTime();
		NotificationResponse notificationResponse = new NotificationResponse();
		try {
			notificationResponse.setNotification(notificationInfoHandler
					.fetchNotification(type, date));
			notificationResponse.setStatus("success");
		} catch (Exception exception) {
			notificationResponse.setStatus("failure");
		}
		return notificationResponse;
	}

	public HashMap<String, String> updateInfoMethod(double version,
			String deviceType) {
		HashMap<String, String> checkStatus = new HashMap<String, String>();
		try {
			logger.debug("Request for updation of application");
			List<UpdateInfo> updateInfoList = updateInfoHandler.findByDevice(
					deviceType, false);
			if (updateInfoList.size() > 0) {
				UpdateInfo updateInfo = updateInfoList.get(index);
				if (updateInfo.getVersion() == version) {
					checkStatus.put("isUpdate", "false");
					checkStatus.put("title", "No Update Available");
					checkStatus
							.put("message", "Your application is up to date");
					checkStatus.put("url", "");
					checkStatus.put("isMandatory", "");
				} else {
					checkStatus.put("isUpdate", "true");
					checkStatus.put("title", "New Update Available");
					if (updateInfo.getIsMandatory().equalsIgnoreCase("false")) {
						updateInfoList.clear();
						updateInfoList = updateInfoHandler.findByDevice(
								deviceType, true);
						if (updateInfoList.size() > 0) {
							if (updateInfoList.get(index).getVersion() >= version) {
								checkStatus
										.put("message",
												"A mandatory update (Version "
														+ updateInfo
																.getVersion()
														+ ") is available for download");
								checkStatus.put("isMandatory", "true");
							} else {
								checkStatus
										.put("message",
												"A optional update (Version "
														+ updateInfo
																.getVersion()
														+ ") is available for download");
								checkStatus.put("isMandatory", "false");
							}
						} else {
							checkStatus.put(
									"message",
									"A optional update (Version "
											+ updateInfo.getVersion()
											+ ") is available for download");
							checkStatus.put("isMandatory", "false");
						}
					} else {
						checkStatus.put(
								"message",
								"A mandatory update (Version "
										+ updateInfo.getVersion()
										+ ") is available for download");
						checkStatus.put("isMandatory", "true");
					}
					if (updateInfo.getDeviceType().equalsIgnoreCase("android"))
						checkStatus.put("url",
								IGConvergeUtil.igConvergeAndroidUrl);
					else
						checkStatus.put("url",
								IGConvergeUtil.igConvergeDownloadPage);

				}
			} else {
				checkStatus.put("isUpdate", "false");
				checkStatus.put("title", "No Update Available");
				checkStatus.put("message", "Your application is up to date");
				checkStatus.put("url", "");
				checkStatus.put("isMandatory", "");
			}
		} catch (Exception e) {
			logger.error("Sorry, Something went wrong.\nException is: ", e);
			checkStatus.put("isUpdate", "false");
			checkStatus.put("title", "Network Error");
			checkStatus.put("message", "Unable to check update");
			checkStatus.put("url", "");
			checkStatus.put("isMandatory", "false");
		}
		return checkStatus;
	}

	public void sendGenericNotification(String type, String title,
			String message, List<DeviceInfo> androidDevices,
			List<DeviceInfo> iOSDevices) throws CommunicationException,
			KeystoreException {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("category", type);
		data.put("title", title);
		data.put("message", message);
		gcmNotificationService.pushAndroidNotification(data, androidDevices);
		iOSNotification iosNotification = new iOSNotification(servletContext);
		iosNotification.test(type, title, message, iOSDevices);
	}
}
