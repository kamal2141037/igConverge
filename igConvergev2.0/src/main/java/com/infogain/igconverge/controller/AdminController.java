package com.infogain.igconverge.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.infogain.igconverge.model.Event;
import com.infogain.igconverge.model.Mail;
import com.infogain.igconverge.model.Meals;
import com.infogain.igconverge.service.EmployeeHandler;
import com.infogain.igconverge.service.EventHandler;
import com.infogain.igconverge.service.Mailer;
import com.infogain.igconverge.service.MealHandler;
import com.infogain.igconverge.util.IGConvergeLogger;
import com.infogain.igconverge.util.IGConvergeUtil;

/**
 * This will serve as Controller for only admin workflow.
 * 
 * @author Infogain Dev Team
 * 
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	Mailer mailer;
	@Autowired
	EventHandler eventHandler;

	@Autowired
	EmployeeHandler employeeHandler;

	@Autowired
	MealHandler mealHandler;

	@IGConvergeLogger
	Logger logger;

	IGConvergeUtil igConvergeUtil;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome to Admin Controller! The client locale is {"
				+ locale + "}.");
		// ---------------------------------------------------------------------------------------------------------
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	// service for check admin or not via mongo USER collection.
	@RequestMapping(value = { "/checkAdminOrNot" }, method = RequestMethod.POST)
	public String userRoleChecking(String employeeId) {
		boolean roleAdminOrNot;
		try {

			roleAdminOrNot = employeeHandler.checkAdminOrNot(employeeId);
			if (roleAdminOrNot) {
				return "adminUser";
			} else {
				return "nonAdminUser";
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("exception is :" + e + "\n\n Error Message:\n"
					+ e.getMessage());
			return "nonAdminUser";
		}
	}

	/*
	 * -----Service to fetch Feedback-----
	 */
	@RequestMapping(value = { "/fetchfeedback" }, method = RequestMethod.GET)
	public @ResponseBody String fetchFeedbacks(Date date,String name) {
		try {
			Gson gson = new Gson();
			logger.debug("request for fetch feedback");
			String json = gson.toJson(mealHandler.fetchFeedback(name, date));
			//logger.debug(feedbacks);
					return json;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	/*
	 * -----Service to save Event-----
	 */
	@RequestMapping(value = { "/saveevent" }, method = RequestMethod.POST)
	public @ResponseBody String saveEvent(Event event, MultipartFile image) {
		try {
			logger.debug("request for save event");

			if (image != null) {
				event.setEventImage(image.getBytes());
			} else {
				logger.debug("Image Empty");
			}
			eventHandler.insert(event);
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "failure";
		}
	}

	/*
	 * -----Service to save meal-----
	 */
	@RequestMapping(value = { "/savemeal" }, method = RequestMethod.POST)
	public String saveMeal(Meals meal) {
		try {

			logger.debug("Request for save meal web service");
			
			return mealHandler.insert(meal);
			
		} catch (Exception e) {
			logger.error("Sorry, Something went wrong.\nException is: ", e);
			return "MealNotSaved";
		}

	}

	/*
	 * -------Service to delete an event by Id -----Author-DIVYAM------
	 */
	@RequestMapping(value = { "/deleteevent" }, method = RequestMethod.GET)
	public String deleteEvent(@RequestParam String eventId) {

		logger.debug("Delete Event service called..");
		eventHandler.deleteEvent(eventId);
		logger.debug("event deleted by service successfully");
		return "success";

	}

	/*
	 * ----service to delete meal from db-----
	 */
	@RequestMapping(value = { "/deletemeal" }, method = RequestMethod.POST)
	public String deleteMeal(@RequestParam String id) {
		try {
			logger.debug("Request to delete meal web service");
			mealHandler.deleteMeal(id);
		} catch (Exception e) {
			logger.error("Sorry, Something went wrong.\nException is: ", e);
			return "failure";
		}
		return "success";

	}

	/*
	 * -----Service to Update meal-----
	 */
	@RequestMapping(value = { "/updatemeal" }, method = RequestMethod.POST)
	public String updateMeal(String id, Meals meal) {
		try {
			logger.debug("Request for update meal web service in admin controller");
			mealHandler.updateMeal(id, meal);
		} catch (Exception e) {
			logger.error(
					"Sorry, Something went wrong while updating meal.\nException is: ",
					e);
			return "failure";
		}
		return "success";

	}

	// Service to update the event
	@RequestMapping(value = { "/updateevent" }, method = RequestMethod.GET)
	public String eventUpdation(String id, Event newEvent) {

		try {
			logger.debug("Request to update the feedback");
			eventHandler.updateEvent(id, newEvent);
		} catch (Exception e) {
			logger.error("Sorry, Something went wrong.\nException is: ", e);
			return "failure";

		}
		return "success";
	}
	@RequestMapping(value={"/sendmail"},method=RequestMethod.GET)
	public String sendMail(){
		System.out.println("this is send mail");
		
		Mail mail = new Mail();
		  mail.setMailFrom("piyush1.singh@infogain.com");
		  //mail.setMailTo(mailTo);;
		  mail.setMailSubject("Subject - Send Email using Spring Velocity Template");
		  mail.setTemplateName("emailtemplate.vm");
		  mail.setMailContent("Hello Everyone");
//		  try {
////			mailer.sendMail(mail);
//		} catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return "true";
	}

}
