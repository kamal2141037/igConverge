package com.infogain.igconverge.model;

import java.util.Arrays;

public class Ratings
{
	private String comment;
	private int score;
	private byte[] mealImage;
	public Ratings() {
		super();
		// TODO Auto-generated constructor stub
	}
	
		
	public Ratings(String comment, int score) {
		super();
		this.comment = comment;
		this.score = score;
	}


	public Ratings(String comment, int score, byte[] mealImage) {
		super();
		this.comment = comment;
		this.score = score;
		this.mealImage = mealImage;
	}
	public String getComment() {
		return comment;
	}
	public byte[] getMealImage() {
		return mealImage;
	}
	public int getScore() {
		return score;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setMealImage(byte[] mealImage) {
		this.mealImage = mealImage;
	}
	public void setScore(int score) {
		this.score = score;
	}
	@Override
	public String toString() {
		return "Ratings [comment=" + comment + ", score=" + score
				+ ", mealImage=" + Arrays.toString(mealImage) + "]";
	}
	
	
}
