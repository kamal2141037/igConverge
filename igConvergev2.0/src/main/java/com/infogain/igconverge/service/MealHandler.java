package com.infogain.igconverge.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.infogain.igconverge.model.BellRing;
import com.infogain.igconverge.model.Feedback;
import com.infogain.igconverge.model.Meals;

/**
 * Interface for Meal Service - includes all functions related to Meal workflow
 * 
 * @author Infogain Dev Team
 * 
 */
// @Service("mealService")
public interface MealHandler {

	public Map<String, Object> getMeals();

	public String insert(Meals meal);
	
	public List<Map> fetchFeedback(String mealName, Date date);

	public List<Map> fetchFeedback(String mealName, Date startDate, Date endDate);

	public List<Map> fetchMeals(String mealName, Date startDate, Date endDate);
	
	public List<Meals> fetchMealsForApp(String mealName, Date startDate, Date endDate);


	public void deleteMeal(String id);

	public void updateMeal(String id, Meals meal);

	public String updateFeedback(String id, Feedback feedback);

	public String saveFeedbackPortal(String id, Feedback feedback);
	
	public Boolean checkMealByNameAndDate(String mealName, Date mealDate);
	
	public String saveBellRingByEmployee(String mealId, BellRing bellRing);
}
