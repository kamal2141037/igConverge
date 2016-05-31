/**
 * 
 */
package com.infogain.igconverge.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.infogain.igconverge.model.BellRing;
import com.infogain.igconverge.model.DeviceInfo;
import com.infogain.igconverge.model.Feedback;
import com.infogain.igconverge.model.Mail;
import com.infogain.igconverge.model.NotificationInfo;
import com.infogain.igconverge.model.Ride;
import com.infogain.igconverge.repository.MealRepository;
import com.infogain.igconverge.service.DeviceInfoHandler;
import com.infogain.igconverge.service.EventHandler;
import com.infogain.igconverge.service.GCMNotificationService;
import com.infogain.igconverge.service.Mailer;
import com.infogain.igconverge.service.MealHandler;
import com.infogain.igconverge.service.NotificationInfoHandler;
import com.infogain.igconverge.service.RideHandler;
import com.infogain.igconverge.util.AspireTest;
import com.infogain.igconverge.util.IGConvergeLogger;
import com.infogain.igconverge.util.LDAPValidation;
import com.infogain.igconverge.util.IGConvergeUtil.NotificationType;

/**
 * This will serve as Controller for both Meal and Event workflow
 * 
 * @author Infogain Dev Team
 * 
 */
@Controller
@RequestMapping("/")
public class MainController {

	@Autowired
	AspireTest aspireTest;

	@Autowired
	EventHandler eventHandler;

	@Autowired
	MealHandler mealhandler;

	@Autowired
	RideHandler rideHandler;

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
	Mailer mailer;

	@Autowired
	NotificationInfoHandler notificationInfoHandler;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView mainDashboard() throws Exception {
		ModelAndView model = new ModelAndView("index");
		return model;
	}

	// /**
	// * Simply selects the home view to render by returning its name.
	// */
	// @RequestMapping(value = "/", method = RequestMethod.GET)
	// public String home(Locale locale, Model model) {
	// logger.info("Welcome  to Main Controller! The client locale is {"
	// + locale + "}.");
	// //
	// ---------------------------------------------------------------------------------------------------------
	// Date date = new Date();
	// DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
	// DateFormat.LONG, locale);
	//
	// String formattedDate = dateFormat.format(date);
	//
	// model.addAttribute("serverTime", formattedDate);
	//
	// return "NewFile";
	// }

	// ------------------------------------------------------------------------
	@RequestMapping(value = "/user/fetchfeedback", method = RequestMethod.GET)
	@ResponseBody
	public String fetchFeedback(@RequestParam String mealName, Date startDate,
			Date endDate) {

		try {
			logger.debug("Request for AverageFeedback By Date Range and Name");
			Gson gson = new Gson();
			String json = gson.toJson(mealhandler.fetchFeedback(mealName,
					startDate, endDate));
			return json;
		} catch (Exception e) {
			logger.error("Sorry, Something went wrong.\nException is: ", e);

		}
		return null;
	}

	@RequestMapping(value = "/user/fetchmeals", method = RequestMethod.GET)
	@ResponseBody
	public String fetchMeals(
			@RequestParam(value = "mealName", required = false) String mealName,
			Date startDate, Date endDate) {

		try {
			logger.debug("Request for Fetch Meals By Dates");
			Gson gson = new Gson();
			if (mealName == null)
				mealName = "";
			String json = gson.toJson(mealhandler.fetchMeals(mealName,
					startDate, endDate));
			return json;

		} catch (Exception e) {
			logger.error("Sorry, Something went wrong.\nException is: ", e);

		}
		return null;
	}

	/*
	 * -----Service to Fetch all Events From
	 * db------------------------------------
	 */
	@RequestMapping(value = "/user/fetchevents", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getAllEvents(HttpServletResponse response) {
		try {
			logger.debug("Request for Fetch Events web service.");

			Gson gson = new Gson();
			String json = gson.toJson(eventHandler.getEventList());
			response.setContentType("application/json");
			// logger.debug("json to be returned = " + json);

			return json;

		} catch (Exception e) {
			logger.error("Sorry, Something went wrong.\nException is: ", e);
			return "failure";
		}
	}

	/*
	 * ----- service to find events based on start time and end time.-----
	 */
	@RequestMapping(value = { "/user/findeventsbydate" }, method = RequestMethod.GET)
	@ResponseBody
	public String getEventsBetween(@RequestParam Date eventStartTime,
			Date eventEndTime) {

		try {
			logger.debug("Request for Fetch Events By Dates web service.");
			Gson gson = new Gson();
			String json = gson.toJson(eventHandler.findEventBetweenDates(
					eventStartTime, eventEndTime));
			return json;
		} catch (Exception e) {
			logger.error("Sorry, Something went wrong.\nException is: ", e);
			return "failure";
		}

	}

	// Service to Save feedback via portal.
	@RequestMapping(value = { "/user/savefeedbackviaportal" }, method = RequestMethod.POST)
	public String saveFeedbackViaPortal(@RequestParam String id,
			Feedback feedback) {
		String feedbackStatus = "feedbackNotSaved";
		try {
			logger.debug("Request to Save feedback via web portal");
			feedbackStatus = mealhandler.saveFeedbackPortal(id, feedback);
		} catch (Exception e) {
			logger.error("Sorry, Something went wrong.\nException is: ", e);
			return feedbackStatus;
		}
		return feedbackStatus;
	}

	// Service to Save bell rings.
	@RequestMapping(value = { "/user/ringbell" }, method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody HashMap<String, String> startRingingBell(
			@RequestParam String mealId, @RequestParam String employeeId) {
		HashMap<String, String> response = new HashMap<String, String>();
		String status = "";
		String responseMessage = "";
		String bellCount = "";
		BellRing bellRing = new BellRing(employeeId);
		try {
			logger.debug("Request to ring the bell");

			String totalResponse = mealhandler.saveBellRingByEmployee(mealId,
					bellRing);
			String[] tempResponse = totalResponse.split(",");

			responseMessage = tempResponse[0].trim();
			bellCount = tempResponse[1].trim();

			if (responseMessage.equalsIgnoreCase("bellRingSuccessful")) {
				responseMessage = "Thank you for the Feedback!";
			} else if (responseMessage.equalsIgnoreCase("alreadyRungTheBell")) {
				responseMessage = "Feedback already submitted!";
			} else if (responseMessage.equalsIgnoreCase("hasNotRungYet")) {
				responseMessage = "Could not Ring the Bell, Please Try Again.";
			}
			status = "success";
		} catch (Exception e) {
			logger.error("Sorry, Something went wrong.\nException is: ", e);
			status = "failure";
			responseMessage = "Connection Error, Sorry for the Inconvenience!";
			bellCount = "0";
		}
		response.put("status", status);
		response.put("message", responseMessage);
		response.put("bellCount", bellCount);
		return response;
	}

	// emergency web service
	@RequestMapping(value = "/user/emergency", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, String> emergencySOS(
			String id,
			String name,
			int type,
			@RequestParam(value = "longitude", required = false) Float longitude,
			@RequestParam(value = "latitude", required = false) Float latitude) {
		HashMap<String, String> response = new HashMap<String, String>();
		String message = "";
		String status = "";
		String[] to = { "AdminDept@infogain.com" };
		String from = "igConverge<igConverge@infogain.com>";
		String[] cc = { "igConverge@infogain.com" };
		Mail mail = new Mail();
		Mailer mailer = new Mailer();
		try {
			logger.debug("Request for Emergency Service");
			switch (type) {
			case 0:
				logger.debug("case 0");

				mail.setMailSubject("Reached Home Safely [" + name + "]");
				mail.setMailContent("The employee " + name + " with id " + id
						+ " reached home safely.");
				mail.setMailFrom(from);
				mail.setMailCc(cc);
				mail.setMailTo(to);
				mailer.sendMail(mail);
				message = "";
				logger.debug("The values are" + id + " " + type);
				break;
			case 1:
				logger.debug("case 1");
				mail.setMailSubject("BreakDown Assistance[" + name + "]");
				if (latitude == null || longitude == null) {
					mail.setMailContent("The employee  " + name + " with id "
							+ id + " is stuck at.\nLocation not known");
				} else {
					mail.setMailContent("The employee  " + name + " with id "
							+ id + " is stuck at.\n"
							+ "http://maps.google.com/maps?q=" + latitude + "+"
							+ longitude);
				}
				mail.setMailFrom(from);
				mail.setMailCc(cc);
				mail.setMailTo(to);
				mailer.sendMail(mail);
				break;
			case 2:
				mail.setMailSubject("Distress SOS[" + name + "]");
				mail.setMailContent("The employee " + name + " with id " + id
						+ " needs help at.\n"
						+ "http://maps.google.com/maps?q=" + latitude + "+"
						+ longitude);
				mail.setMailFrom(from);
				mail.setPriority(1);
				mail.setMailCc(cc);
				mail.setMailTo(to);
				mailer.sendMail(mail);
			}
			status = "success";
			message = "Successfully Sent";
			response.put("status", status);
			response.put("message", message);
		} catch (Exception e) {
			logger.error("Sorry, Something went wrong.\nException is: ", e);
			status = "failure";
			message = "Something Went Wrong";

			response.put("status", status);
			response.put("message", message);
		}
		return response;
	}

	/*----------CarPool Web Services-------------
	 * -----Service to save ride-----
	 */
	@RequestMapping(value = "/user/ride/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String saveRide(@RequestBody Ride ride) {
		try {
			logger.debug("Request for save ride web service" + ride);
			return rideHandler.insert(ride);
		} catch (Exception e) {
			logger.error("Sorry, Something went wrong.\nException is: ", e);
			return "Unable to save ride";
		}

	}

	/*
	 * -----Service to Fetch all Rides From DB-----
	 */
	@RequestMapping(value = "/user/ride/fetchAllRides", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getAllRides() {
		try {
			logger.debug("Request for Fetch Ride web service.");
			Gson gson = new Gson();
			List<Ride> rideList = rideHandler.getAllRides();
			String json = gson.toJson(rideList);
			logger.debug("Ride List Count" + rideList.size());
			return json;
		} catch (Exception e) {
			logger.error("Sorry, Something went wrong.\nException is: ", e);
			return "Unable to fetch rides";
		}
	}

	/*
	 * ----service to softly delete ride from db-----
	 */
	@RequestMapping(value = { "/user/ride/softDelete" }, method = RequestMethod.POST)
	@ResponseBody
	public String deleteRide(@RequestParam String rideId) {
		String result = null;
		try {
			
			Ride ride = rideHandler.findById(rideId);
			String title = "Ride Deleted";
			String message = "Ride for Car No. : " + ride.getCarNumber()
					+ " is deleted by owner : " + ride.getUser().get("name");
			ArrayList<DeviceInfo> androidDevices = new ArrayList<DeviceInfo>();
			
			logger.debug("Request to delete ride web service");
			result = rideHandler.softDelete(rideId);

			// ----------------Mailer-------------------------
			String ownerEmail = ride.getUser().get("email");
			String ownerName = ride.getUser().get("name");
			
			Mail mail = new Mail();
			mail.setMailFrom(ownerEmail);
			ArrayList<String> to = new ArrayList<String>();
			List<Map<String, String>> occupants = ride.getOccupants();
			if(occupants!=null)
			{
				for (Map<String, String> occup : ride.getOccupants()) {
					to.add(occup.get("email"));
				}
				
				String[] sendTo = new String[to.size()];
				to.toArray(sendTo);
				mail.setMailTo(sendTo);
				mail.setMailSubject("Carpool ride deleted");
				mail.setTemplateName("emailtemplate.vm");
				mail.setMailContent(ownerName
						+ " has deleted the car pool ride for Car No. "
						+ ride.getCarNumber() + ". Kindly contact " + ownerName
						+ " for any queries.");
				try {
					mailer.sendMail(mail);
				} catch (Exception e) {
					logger.debug("Sorry Something went wrong in mailer. Exception "
							, e);
				}
			}

			// --------------------------------------------------
			// Creating Notification Object
			NotificationInfo notificationInfo = new NotificationInfo();
			notificationInfo.setTitle(title);
			notificationInfo.setMessage(message);
			notificationInfo.setDate(new Date());
			notificationInfo.setType(NotificationType.Two.getValue());

			// Saving Notification Object
			notificationInfoHandler.saveNotification(notificationInfo);
			
			try {
				// Sending Notification to Occupant's mobile
				
				if (occupants != null) {
					for (Map<String, String> occupant : occupants) {
						androidDevices.add(deviceInfoHandler
								.findByEmployeeId(occupant.get("id")));
					}
					sendGenericNotification(NotificationType.Two.getValue(), title, message, androidDevices);
				}
			} catch (Exception e) {
				logger.debug("Sorry Something went wrong in sending notification. Exception "
						, e);
			}
		} catch (Exception e) {
			logger.error("Sorry, Something went wrong.\nException is: ", e);
			result = "Failed to delete ride";
		}
		return result;
	}

	/*
	 * ----service to accept ride request-----
	 */
	@RequestMapping(value = { "/user/ride/acceptRequest" }, method = RequestMethod.POST)
	@ResponseBody
	public String acceptRideRequest(@RequestParam String rideId, @RequestParam String empId) {
		String result = "Unable to accept ride request";
		try {
			logger.debug("Request to confireRequest ride web service");
			Ride ride = rideHandler.findById(rideId);
			
			if(ride!=null)
			{
				int acceptedOccupantsCount = 0;
				
				List<Map<String, String>> occupants =  ride.getOccupants();
				
					for(Map<String,String> occupant : occupants){
						if(occupant.get("status").equalsIgnoreCase("accepted")){
							acceptedOccupantsCount++;
						}
					}	
							
				
				if(acceptedOccupantsCount<ride.getNumberOfSeats())
				{
					result = rideHandler.acceptRideRequest(rideId, empId);
					ride = rideHandler.findById(rideId);
					occupants =  ride.getOccupants();
					if(++acceptedOccupantsCount==ride.getNumberOfSeats())
					{
						// remove occupants with pending status
						logger.debug("Inside confirmRequestService, No.of seats are full, moving occupants with status pending");
						Iterator<Map<String,String>> iterator = occupants.iterator();
						Map<String,String> occupant = new HashMap<String, String>();
						while(iterator.hasNext())
						{
							occupant = iterator.next();
							if(occupant.get("status").equalsIgnoreCase("pending")){
								//removing from database
								rideHandler.leaveCarpool(rideId, occupant);
								//send mail
								Mail mail = new Mail();
								mail.setMailFrom(ride.getUser().get("email"));
								String[] to = { occupant.get("email") };
								mail.setMailTo(to);
								mail.setMailSubject("Carpool request rejected");
								mail.setTemplateName("emailtemplate.vm");
								
								mail.setMailContent(ride.getUser().get("name")
										+ " has rejected your car pool request from "
										+ ride.getStartingLocation() + " to Infogain for Car No. "
										+ ride.getCarNumber() + " because seats are full. Kindly contact " + ride.getUser().get("name")
										+ " for any queries.");
								try {
									mailer.sendMail(mail);
								} catch (Exception e) {
									logger.debug("Sorry Something went wrong in mailer. Exception "
											, e);
								}
								
								//send notification
								String title = "Ride Request Rejected";
								String message = "Your Ride Request for Car No. : "
										+ ride.getCarNumber() + " has been rejected by owner : "
										+ ride.getUser().get("name")+" because seats are full.";
								ArrayList<DeviceInfo> androidDevices = new ArrayList<DeviceInfo>();
								
								// Creating Notification Object
								NotificationInfo notificationInfo = new NotificationInfo();
								notificationInfo.setTitle(title);
								notificationInfo.setMessage(message);
								notificationInfo.setDate(new Date());
								notificationInfo.setType(NotificationType.Two.getValue());
								
								// Saving Notification Object into database
								notificationInfoHandler.saveNotification(notificationInfo);
								
								try {
									// Sending Notification to Occupant's mobile notifying ride request is rejected
									DeviceInfo device = deviceInfoHandler.findByEmployeeId(occupant
											.get("id"));
									androidDevices.add(device);
									sendGenericNotification(NotificationType.Two.getValue(), title, message, androidDevices);
								} catch (Exception e) {
									logger.debug("Sorry Something went wrong in sending notification. Exception "
											, e);
								}
								//removing from java object
								iterator.remove();
							}
						}
					}

					
					// --------------EMail to occupant whose request has accepted---------------
					
					String ownerEmail = ride.getUser().get("email");
					String ownerName = ride.getUser().get("name");
					String occpantEmail = new String();

					for (Map<String, String> occup : occupants) {
						if ((occup.get("id")).equals(empId)) {
							occpantEmail = occup.get("email");
						}
					}

					Mail mail = new Mail();
					mail.setMailFrom(ownerEmail);
					String[] to = { occpantEmail };
					mail.setMailTo(to);
					mail.setMailSubject("Carpool request accepted");
					mail.setTemplateName("emailtemplate.vm");
					mail.setMailContent(ownerName
							+ " has accepted your car pool request from "
							+ ride.getStartingLocation() + " to Infogain for Car No. "
							+ ride.getCarNumber() + ". Kindly contact " + ownerName
							+ " for any queries.");

					try {
						mailer.sendMail(mail);
					} catch (Exception e) {
						logger.debug("Sorry Something went wrong in mailer. Exception "
								, e);
					}

					// -----------Notification to occupant whose request has accepted--------------

					// Creating Notification Object
					
					String title = "Ride Request Accepted";
					String message = "Your Ride Request for Car No. : "
							+ ride.getCarNumber() + " has been accepted by owner : "
							+ ride.getUser().get("name");
					ArrayList<DeviceInfo> androidDevices = new ArrayList<DeviceInfo>();
					
					NotificationInfo notificationInfo = new NotificationInfo();
					notificationInfo.setTitle(title);
					notificationInfo.setMessage(message);
					notificationInfo.setDate(new Date());
					notificationInfo.setType(NotificationType.Two.getValue());
					
					// Saving Notification Object into database
					notificationInfoHandler.saveNotification(notificationInfo);
					
					try {
						// Sending Notification to Occupant's mobile notifying ride request
						// is rejected
						DeviceInfo device = deviceInfoHandler.findByEmployeeId(empId);
						androidDevices.add(device);
						sendGenericNotification(NotificationType.Two.getValue(), title, message, androidDevices);
					} catch (Exception e) {
						logger.debug("Sorry Something went wrong in sending notification. Exception "
								, e);
					}
					
				}
				
			}
		} catch (Exception e) {
			logger.error("Sorry, Something went wrong.\nException is: ", e);
			result = "Unable to accept ride request";
		}
		return result;
	}

	/*
	 * ----service to reject ride request-----
	 */

	@RequestMapping(value = { "/user/ride/rejectRequest/{rideId}" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String rejectRideRequest(@PathVariable(value = "rideId") String rideId, @RequestBody Map<String, String> occupant) {
		String result = null;
		try {
			String title = "Ride Request Rejected";
			Ride ride = rideHandler.findById(rideId);
			String message = "Your Ride Request for Car No. : "
					+ ride.getCarNumber() + " has been rejected by owner : "
					+ ride.getUser().get("name");
			ArrayList<DeviceInfo> androidDevices = new ArrayList<DeviceInfo>();
			
			logger.debug("Request to rejectRideRequest web service");
			result = rideHandler.leaveCarpool(rideId, occupant);

			// ------------------------------------------------------
			String ownerEmail = ride.getUser().get("email");
			String ownerName = ride.getUser().get("name");
			String occpantEmail = occupant.get("email");

			Mail mail = new Mail();
			mail.setMailFrom(ownerEmail);
			String[] to = { occpantEmail };
			mail.setMailTo(to);
			mail.setMailSubject("Carpool request rejected");
			mail.setTemplateName("emailtemplate.vm");
			mail.setMailContent(ownerName
					+ " has rejected your car pool request from "
					+ ride.getStartingLocation() + " to Infogain for Car No. "
					+ ride.getCarNumber() + ". Kindly contact " + ownerName
					+ " for any queries.");
			try {
				mailer.sendMail(mail);
			} catch (Exception e) {
				logger.debug("Sorry Something went wrong in mailer. Exception "
						, e);
			}
			// ----------------------------------------------------

			// Creating Notification Object
			NotificationInfo notificationInfo = new NotificationInfo();
			notificationInfo.setTitle(title);
			notificationInfo.setMessage(message);
			notificationInfo.setDate(new Date());
			notificationInfo.setType(NotificationType.Two.getValue());
			
			// Saving Notification Object into database
			notificationInfoHandler.saveNotification(notificationInfo);
			
			try {
				// Sending Notification to Occupant's mobile notifying ride request
				// is rejected
				DeviceInfo device = deviceInfoHandler.findByEmployeeId(occupant
						.get("id"));
				androidDevices.add(device);
				sendGenericNotification(NotificationType.Two.getValue(), title, message, androidDevices);
			} catch (Exception e) {
				logger.debug("Sorry Something went wrong in sending notification. Exception "
						, e);
			}
		} catch (Exception e) {
			logger.error("Sorry, Something went wrong.\nException is: ", e);
			result = "Unable to reject ride request";
		}
		return result;
	}

	/*
	 * ----service to add occupant in existing ride-----
	 */
	@RequestMapping(value = { "/user/ride/addOccupant/{rideId}" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String addOccupant(@PathVariable(value = "rideId") String rideId, @RequestBody Map<String, String> occupant) {
		String result = null;
		logger.debug("Request to addOccupant web service");
		try {
			Ride ride = rideHandler.findById(rideId);
			if (ride != null) {

				int seats = ride.getNumberOfSeats();
				int acceptedOccupantsCount = 0;

				if (ride.getOccupants() != null) {
					for (Map<String, String> occup : ride.getOccupants()) {
						if (occup.get("status").equalsIgnoreCase("accepted")) {
							acceptedOccupantsCount++;
						}
					}
					if (acceptedOccupantsCount== seats) {
						return "Seats are full";
					}
				}
					

					result = rideHandler.addOccupant(rideId, occupant);

					// ------------------------------------------------------
					String ownerEmail = ride.getUser().get("email");
					String occpantemail = occupant.get("email");
					String occupantname = occupant.get("name");

					Mail mail = new Mail();
					mail.setMailFrom(occpantemail);
					String[] to = { ownerEmail };
					mail.setMailTo(to);
					mail.setMailSubject("Request for carpool");
					mail.setTemplateName("emailtemplate.vm");
					mail.setMailContent(occupantname
							+ " wants to join the carpool from "
							+ ride.getStartingLocation()
							+ " to Infogain for Car No. " + ride.getCarNumber()
							+ ". Kindly contact " + occupantname
							+ " for any queries.");

					try {
						mailer.sendMail(mail);
					} catch (Exception e) {
						logger.debug(
								"Sorry Something went wrong in mailer. Exception ", e);
					}
					// ---------------------------------------------------------

					// Creating Notification Object
					String title = "Request for Ride";
					String message = occupant.get("name")
							+ " sent a ride request for Car No. : "
							+ ride.getCarNumber();
					ArrayList<DeviceInfo> androidDevices = new ArrayList<DeviceInfo>();

					NotificationInfo notificationInfo = new NotificationInfo();
					notificationInfo.setTitle(title);
					notificationInfo.setMessage(message);
					notificationInfo.setDate(new Date());
					notificationInfo.setType(NotificationType.Two.getValue());

					// Saving Notification Object into database
					notificationInfoHandler.saveNotification(notificationInfo);

					try {
						// Sending Notification to Ride owner's mobile notifying
						// a request
						// is raised for ride
						DeviceInfo device = deviceInfoHandler
								.findByEmployeeId(rideHandler.findById(rideId)
										.getUser().get("id"));
						androidDevices.add(device);
						sendGenericNotification(
								NotificationType.Two.getValue(), title,
								message, androidDevices);
					} catch (Exception e) {
						logger.debug(
								"Sorry Something went wrong in sending notification. Exception ",
								e);
					}

				
			}
		} catch (Exception e) {
			logger.error("Sorry, Something went wrong.\nException is: ", e);
			result = "Unable to send ride request";
		}
		return result;
	}

	/*
	 * ----service to remove occupant(Leave Carpool) in existing ride-----
	 */
	@RequestMapping(value = { "/user/ride/removeOccupant/{rideId}" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String leaveCarpool(@PathVariable(value = "rideId") String rideId, @RequestBody Map<String, String> occupant) {
		String result = null;

		try {
			Ride ride = rideHandler.findById(rideId);
			String title = occupant.get("name") + " has Left carpool";
			String message = occupant.get("name") + " has left ride for Car No. : "
					+ ride.getCarNumber();
			ArrayList<DeviceInfo> androidDevices = new ArrayList<DeviceInfo>();
			
			logger.debug("Request to removeOccupant web service");
			result = rideHandler.leaveCarpool(rideId, occupant);

			// ----------------Mailer-------------------------
			String email = ride.getUser().get("email");
			String occpantemail = occupant.get("email");
			String occupantname = occupant.get("name");

			Mail mail = new Mail();
			mail.setMailFrom(occpantemail);
			String[] to = { email };
			mail.setMailTo(to);
			mail.setMailSubject("Leaving carpool");
			mail.setTemplateName("emailtemplate.vm");
			mail.setMailContent(occupantname + " has left your car pool from "
					+ ride.getStartingLocation() + " to Infogain for Car No. "
					+ ride.getCarNumber() + ". Kindly contact " + occupantname
					+ " for any queries.");
			try {
				mailer.sendMail(mail);
			} catch (Exception e) {
				logger.debug("Sorry Something went wrong in mailer. Exception "
						, e);
			}

			// --------------------------------------------------

			// Creating Notification Object
			NotificationInfo notificationInfo = new NotificationInfo();
			notificationInfo.setTitle(title);
			notificationInfo.setMessage(message);
			notificationInfo.setDate(new Date());
			notificationInfo.setType(NotificationType.Two.getValue());
			
			// Saving Notification Object into database
			notificationInfoHandler.saveNotification(notificationInfo);
			
			try {
				// Sending Notification to Ride Owner's mobile notifying the
				// occupant has left the ride
				DeviceInfo device = deviceInfoHandler.findByEmployeeId(rideHandler
						.findById(rideId).getUser().get("id"));
				androidDevices.add(device);
				sendGenericNotification(NotificationType.Two.getValue(), title, message, androidDevices);
				
			} catch (Exception e) {
				logger.debug("Sorry Something went wrong in sending notification. Exception "
						, e);
			}
		} catch (Exception e) {
			logger.error("Sorry, Something went wrong.\nException is: ", e);
			result = "Unable to leave carpool";
		}
		return result;
	}

	/*
	 * ----service to update information in existing ride-----
	 */
	@RequestMapping(value = { "user/ride/update/{rideId}" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String updateRide(@PathVariable(value = "rideId") String rideId, @RequestBody Ride ride) {
		String result = null;
		boolean status = false;
		
		try {
			Ride oldRide = rideHandler.findById(rideId);
			String title = "Ride updated by " + oldRide.getUser().get("name");
			String message = oldRide.getUser().get("name")
					+ " has updated ride information for Car No. : "
					+ oldRide.getCarNumber();
			ArrayList<DeviceInfo> androidDevices = new ArrayList<DeviceInfo>();
			
			logger.debug("Request to update Ride");
			result = rideHandler.updateRide(rideId, ride);
			status = true;
			
				// ----------------Mailer-------------------------
				String ownerEmail = oldRide.getUser().get("email");
				String ownerName = oldRide.getUser().get("name");
				
				Mail mail = new Mail();
				mail.setMailFrom(ownerEmail);
				ArrayList<String> to = new ArrayList<String>();
				
				 List<Map<String, String>> occupants = oldRide.getOccupants();
					if (occupants != null) {
						for (Map<String, String> occup : oldRide.getOccupants()) {
							to.add(occup.get("email"));
						}
						
						String[] sendTo = new String[to.size()];
						to.toArray(sendTo);
						mail.setMailTo(sendTo);
						mail.setMailSubject("Regarding your carpool request.");
						mail.setTemplateName("emailtemplate.vm");
						mail.setMailContent(ownerName
								+ " has changed the details of the car pool ride for Car No. "
								+ oldRide.getCarNumber() + ". Kindly contact " + ownerName
								+ " for any queries.");
						try {
							mailer.sendMail(mail);
						} catch (Exception e) {
							logger.debug("Sorry Something went wrong in mailer. Exception "
									, e);
						}
					}

				// --------------------------------------------------
				// Creating Notification Object
				NotificationInfo notificationInfo = new NotificationInfo();
				notificationInfo.setTitle(title);
				notificationInfo.setMessage(message);
				notificationInfo.setDate(new Date());
				notificationInfo.setType(NotificationType.Two.getValue());
				
				// Saving Notification Object
				notificationInfoHandler.saveNotification(notificationInfo);
				
				try {
					// Sending Notification to Occupant's mobile
					if (occupants != null) {
						for (Map<String, String> occupant : occupants) {
							androidDevices.add(deviceInfoHandler
									.findByEmployeeId(occupant.get("id")));
						}
						System.out.println("Devices : " + androidDevices.toString());
						sendGenericNotification(NotificationType.Two.getValue(), title, message, androidDevices);
					}
				} catch (Exception e) {
					logger.debug("Sorry Something went wrong in sending notification.\n  Exception is: ", e);
				}
		} catch (Exception e) {
			logger.error("Sorry, Something went wrong.\nException is: ", e);
			result = "Ride not updated";
		}
		return "{\"message\":\"" + result + "\",\"status\":" + status + "}";
	}

	/*
	 * -----Service to Fetch all active Rides corresponding to an employee From
	 * DB-----
	 */
	@RequestMapping(value = "/user/ride/fetchAllRidesByEmployeeId", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getAllRidesByEmployeeId(@RequestParam String empId) {
		try {
			logger.debug("Request for getAllRidesByEmployeeId web service.");
			Gson gson = new Gson();
			List<Ride> rideList = rideHandler.findByUserIdAndStatusTrue(empId);
			String json = gson.toJson(rideList);  
			logger.debug("GetAllRidesByEmployeeId Count: "+rideList.size());
			return json;
		} catch (Exception e) {
			logger.error("Sorry, Something went wrong.\nException is: ", e);
			return "Failed to fetch rides";
		}
	}

	/*
	 * ---Method to send notification to android devices
	 */
	public void sendGenericNotification(String type, String title,
			String message, ArrayList<DeviceInfo> androidDevices)
			throws CommunicationException, KeystoreException {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("category", type);
		data.put("title", title);
		data.put("message", message);
		gcmNotificationService.pushAndroidNotification(data, androidDevices);
	}
	// @RequestMapping(value = {"/user/fetchEmployeeDetails"}, method =
	// RequestMethod.POST)
	// public @ResponseBody String
	// fetchEmployeeDetails(@RequestParam("userName") String userName)
	// {
	// logger.debug("Fetch Data From Aspire");
	// List<AspireEmployee> aspireEmployees =
	// aspireTest.getAspireData(userName);
	// Gson gson = new Gson();
	// for(AspireEmployee aspireEmployee : aspireEmployees)
	// {
	// if(aspireEmployee.getIsActive().equals("1"))
	// return gson.toJson(aspireEmployee, AspireEmployee.class);
	// }
	// return "";
	// }

	// @RequestMapping(value = { "/user/updatefeedback" }, method =
	// RequestMethod.POST)
	// public @ResponseBody String updateMeal(String id, String feedbackJson,
	// @RequestParam("mealImage") MultipartFile image)
	// {
	// try
	// {
	// logger.debug("Request to update the feedback");
	//
	// Gson gson = new Gson();
	// Feedback feedback = gson.fromJson(feedbackJson, Feedback.class);
	// feedback.getRatings().setMealImage(image.getBytes());
	//
	// mealhandler.updateFeedback(id, feedback);
	//
	// } catch (Exception e)
	// {
	// logger.error("Sorry, Something went wrong.\nException is: ", e);
	// return "failure";
	// }
	// return "success";
	// }

	// // Service for User Login
	// @RequestMapping(value = { "/user/login" }, method = RequestMethod.POST,
	// produces = "application/json")
	// public @ResponseBody
	// String login1(@RequestParam String userName, String password) {
	// System.out.println("inside login1");
	// HashMap<String, String> loginStatusMap= new HashMap<String, String>();
	// String statusMessage = "success";
	// // LDAP Validation !-- Not use for testing purpose
	// if(ldapValidation.loginCredentialValidation(userName, password))
	// {
	// statusMessage = "success";
	// }
	// else
	// {
	// statusMessage = "failure";
	// loginStatusMap.put("failureCause", "Credentials Are Not Valid");
	// }
	// loginStatusMap.put("status", statusMessage);
	// return JSON.serialize(loginStatusMap);
	// }

}
