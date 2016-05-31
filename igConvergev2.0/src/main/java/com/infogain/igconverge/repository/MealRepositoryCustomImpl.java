package com.infogain.igconverge.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.infogain.igconverge.model.BellRing;
import com.infogain.igconverge.model.Feedback;
import com.infogain.igconverge.model.Meals;
import com.infogain.igconverge.util.IGConvergeLogger;

/**
 * This class will have implementation of Custom Queries method declared in
 * MealCustomRepository Interface.
 * 
 * @author Infogain Dev Team
 * 
 */
public class MealRepositoryCustomImpl implements MealCustomRepostiory {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	MealRepository mealRepository;

	@IGConvergeLogger
	Logger logger;

	public void saveFeedback(String mealId, Feedback feedback) {
		try {
			Query query = new Query(Criteria.where("_id").is(mealId));
			Update update = new Update();
			update.push("feedbacks", feedback);
			mongoTemplate.updateFirst(query, update, Meals.class);
		} catch (Exception e) {
			logger.error("Sorry, Something went wrong.\nException is: ", e);
		}
	}
	
	

	public void saveBellRings(String mealId, BellRing bellRingByEmps, int totalBellCount) {
		try {
			logger.info("saveBellRings method from meal custom impl.");
			Query query = new Query(Criteria.where("_id").is(mealId));
			Update update = new Update();
			update.push("bellRingByEmployees", bellRingByEmps);
			//update.push("bellRingCount", ++totalBellCount);
			
			mongoTemplate.updateFirst(query, update, Meals.class);
			
			int count = totalBellCount + 1;
			
			Query query2 = new Query(Criteria.where("_id").is(mealId));
			Meals meals =mongoTemplate.findOne(query2,Meals.class);
			meals.setBellCount(count);
			mealRepository.save(meals);
			
		} catch (Exception e) {
			logger.error("Sorry, Something went wrong while saving bell ring.\nException is: ", e);
		}
	}
	
	

	// service to get list of meals through two given dates
	public List<Map> fetchAvgFeedbackByNameAndDates(String mealName,
			Date startDate, Date endDate) {
		try {

			logger.debug("Request for Find FEedbacks By Date RAnge and Name");
			AggregationOperation unwind = Aggregation.unwind("feedbacks");
			AggregationOperation group = Aggregation.group("date")
					.avg("feedbacks.ratings.score").as("averageFeedback");
			AggregationOperation match = Aggregation.match(Criteria
					.where("date").gte(startDate).lte(endDate).and("name")
					.is(mealName));
			AggregationOperation project = Aggregation.project("date",
					"averageFeedback");
			Aggregation agg = Aggregation.newAggregation(match, unwind, group,
					project);

			logger.debug(mongoTemplate.aggregate(agg, "Meals", Map.class)
					.getMappedResults());
			return mongoTemplate.aggregate(agg, "Meals", Map.class)
					.getMappedResults();

		} catch (Exception e) {
			logger.error("Divyam Something went wrong.\nException is: ", e);
			return null;
		}

	}




}
