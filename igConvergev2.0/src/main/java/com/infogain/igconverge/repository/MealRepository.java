package com.infogain.igconverge.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.infogain.igconverge.model.Meals;

@Repository
public interface MealRepository extends MongoRepository<Meals, String>,
		MealCustomRepostiory {

	// @Query(value = "{ 'name' : ?0, 'date': ?1}", fields =
	// "{ 'name':1,'items':1 }")
	public Meals findByNameAndDate(String name, Date date);
	
	@Query(value = "{ 'date':?0 ,'name' :?1}", fields = "{ 'name':1,'date':1,'feedbacks':1}")
	List<Map> fetchFeedbacks( Date date,String name);
	
	@Query(value = "{ 'date':{ $gte : ?0 , $lte : ?1}}", fields = "{ 'name':1,'date':1,'items':1, 'bellCount':1,'bellRingByEmployees':1}")
	List<Map> fetchMealsByDates( Date startDate,
			Date endDate);

	// service to fetch items of meal by name and Date RAnge
	@Query(value = "{ 'name' : ?0 ,'date':{ $gte : ?1 , $lte : ?2}}", fields = "{ 'name':1,'date':1,'items':1, 'bellCount':1,'bellRingByEmployees':1}")
	List<Map> fetchMealsByNameAndDates(String mealName, Date startDate,
			Date endDate);
}
