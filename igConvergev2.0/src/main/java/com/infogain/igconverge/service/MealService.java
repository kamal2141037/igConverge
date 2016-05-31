package com.infogain.igconverge.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.infogain.igconverge.model.BellRing;
import com.infogain.igconverge.model.Feedback;
import com.infogain.igconverge.model.Meals;
import com.infogain.igconverge.repository.MealRepository;
import com.infogain.igconverge.util.IGConvergeLogger;

/**
 * Implements Meal Interface. Will include business logic related to the Meal
 * workflow.
 * 
 * @author Infogain Dev Team
 * 
 */
@Service("mealService")
public class MealService implements MealHandler {

	@Autowired
	MealRepository mealRepository;

	@Autowired
	MongoTemplate mongoTemplate;
	
	@IGConvergeLogger
	Logger logger;

	public Map<String, Object> getMeals() {
		// Sample Code - just for example
		// TODO change the code to fetch data from DB.
		Map<String, Object> mealMap = new HashMap<String, Object>();
		mealMap.put("breakfast", "omlet");
		return mealMap;
	}

	public String insert(Meals meal) {
		logger.debug("Insert meal method invoked");
		
		if(checkMealByNameAndDate(meal.getName(), meal.getDate() ))
		{
			// true if meal not found.
			mealRepository.save(meal);
			return "MealSaved";
		}
		else
		{
			// false  means meal already exist.
			return "MealAlreadyExist";
		}
	}

	public void deleteMeal(String id) {
		logger.debug("Delete meal method invoked");
		mealRepository.delete(id);
	}
	
	
	// save bell ring.
	public String saveBellRingByEmployee(String mealId, BellRing bellRing) {
		// TODO Auto-generated method stub
		String status = "hasNotRungYet" + ",0";
		
		logger.debug("Save Bell Ring method invoked");
		Meals meal = mealRepository.findOne(mealId);
		
		int bellCount = meal.getBellCount();
		logger.debug("bell count from meal: "+ bellCount);
		int flag=1;
		if(meal.getBellRingByEmployees()!= null)
		{	
			for(BellRing ite : meal.getBellRingByEmployees())
			{
				if(ite.getEmployeeId().equals(bellRing.getEmployeeId()))
				{
					//if emp had already rung the bell set flag = 0.
					flag = 0;
					break;
				}
			}// for 
			
			if (flag == 1) 
			{
				mealRepository.saveBellRings(mealId, bellRing, bellCount);
				status = "bellRingSuccessful" + ","+ ++bellCount;
			}
			if (flag == 0) 
			{
				logger.error("Employee had already rung the bell.");
				status =  "alreadyRungTheBell" + ","+ bellCount;
			}
		}//if null
		else {
			mealRepository.saveBellRings(mealId, bellRing, bellCount);
			status = "bellRingSuccessful" + ","+ ++bellCount;
		}
		
		return status;
	}
	
	public String updateFeedback(String id, Feedback feedback) 
	{
		String status = "feedbackNotSaved";
		if (feedback.getEmployee().getId() != null
				&& feedback.getEmployee().getName() != null
				&& feedback.getEmployee().getId().matches("[0-9]+")) 
		{
			logger.debug("Save Feedback method invoked");
			Meals meal = mealRepository.findOne(id);
			if (meal.getFeedbacks() != null) 
			{
//				logger.debug(meal);
				int flag = 1;
				for (Feedback ite : meal.getFeedbacks()) 
				{
					if (ite.getEmployee().getId()
							.equals(feedback.getEmployee().getId())) {
						flag = 0;
						break;
					}
				}
				if (flag == 1) 
				{
					mealRepository.saveFeedback(id, feedback);
					status = "feedbackSaved";
				}
				if (flag == 0) 
				{
					logger.error("Employee Comment Already Exists");
					status =  "empCommentAlreadyExist";
				}
			} 
			else 
			{
				logger.debug("Feedback Not Exists");
				mealRepository.saveFeedback(id, feedback);
				status =  "feedbackSaved";
			}
		} 
		else 
		{
			logger.error("Something went wrong");
			status =  "feedbackNotSaved";
		}
		return status;
	}

	public String saveFeedbackPortal(String id, Feedback feedback) {
		// TODO Auto-generated method stub
		logger.debug("Save Feedback Portal method invoked");
//		logger.debug("id: " + id + feedback);
		// Meals meal = mealRepository.findOne(id);
		// logger.debug("fetched meal: "+meal);
		logger.debug("feedback save handled via web portal.");
		
		return updateFeedback(id, feedback);
		
	}

	// service to fetch meals by mealname and between two supplied dates
	public List<Map> fetchFeedback(String mealName, Date startDate, Date endDate) {
		List<Map> feedback = mealRepository.fetchAvgFeedbackByNameAndDates(
				mealName, startDate, endDate);
//		logger.debug(feedback);
		return feedback;
	}

	public void updateMeal(String id, Meals meal) {
		logger.debug("Update meal method invoked");
		meal.setId(id);
		mealRepository.save(meal);
	}

	// service to fetch items by date range and name
			public List<Map> fetchMeals(String mealName, Date startDate, Date endDate) {
				if(mealName!="")
				{
				List<Map> meals = mealRepository.fetchMealsByNameAndDates(mealName,
						startDate, endDate);
				logger.debug("Fetch meal method service\n");
				
				return meals;
				}
				else
				{
					List<Map> meals = mealRepository.fetchMealsByDates(startDate, endDate);
					logger.debug("Fetch meal method Dates\n");
					return meals;
				}
					
			}

	public List<Meals> fetchMealsForApp(String mealName, Date startDate,
			Date endDate) {
		Query query = new Query(Criteria.where("name").is(mealName).andOperator(Criteria.where("date").gte(startDate)
			    .andOperator(Criteria.where("date").lte(endDate))));
		query.fields().exclude("feedbacks");
		return mongoTemplate.find(query, Meals.class);
	}
	
	public Boolean checkMealByNameAndDate(String mealName, Date mealDate) {
		// TODO Auto-generated method stub
		
		Meals foundMeal  = mealRepository.findByNameAndDate(mealName, mealDate);
		logger.debug("found meal before save"+ foundMeal);
		
		if(foundMeal == null)
		{
			//meal NOT found n return true.
			return true;	
		}
		return false;
	}
	//function for fetching feedbacks
	public List<Map> fetchFeedback(String name, Date date) {
		List<Map> feedbacks=mealRepository.fetchFeedbacks(date, name);
		logger.debug("Feedbacks="+feedbacks);
		return feedbacks;
	}

	

}
