/**
 * 
 */
package com.infogain.igconverge.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.infogain.igconverge.model.BellRing;
import com.infogain.igconverge.model.Feedback;

/**
 * This class will include methods that can not be executed using naming
 * convention method of MongoRepository Custom Queries will be written in this
 * interface
 * 
 * @author Infogain Dev Team
 * 
 */
public interface MealCustomRepostiory {

	public void saveFeedback(String mealId, Feedback feedback);
	
	public void saveBellRings(String mealId, BellRing bellRingByEmps, int totalBellCount);

	//service to fetch average feedback according to meal Name and meal Date Range
	List<Map> fetchAvgFeedbackByNameAndDates(String mealName, Date startDate,
			Date endDate);
	

	
}
