package com.infogain.igconverge.controller.responseModel;

import com.infogain.igconverge.model.Meals;

public class MealServiceResponse 
{
	Meals meals;
	String averageFeedback;
	
	/**
	 * 
	 */
	public MealServiceResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the meals
	 */
	public Meals getMeals() {
		return meals;
	}

	/**
	 * @param meals the meals to set
	 */
	public void setMeals(Meals meals) {
		this.meals = meals;
	}

	/**
	 * @return the averageFeedback
	 */
	public String getAverageFeedback() {
		return averageFeedback;
	}

	/**
	 * @param averageFeedback the averageFeedback to set
	 */
	public void setAverageFeedback(String averageFeedback) {
		this.averageFeedback = averageFeedback;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MealServiceResponse [meals=");
		builder.append(meals);
		builder.append(", averageFeedback=");
		builder.append(averageFeedback);
		builder.append("]");
		return builder.toString();
	}
	
	
}
