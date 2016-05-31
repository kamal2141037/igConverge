package com.infogain.igconverge.repository;

import java.util.Map;

import com.infogain.igconverge.model.Ride;


/**
 * This class will include methods that can not be executed using naming
 * convention method of MongoRepository Custom Queries will be written in this
 * interface
 * 
 * @author Infogain Dev Team
 * 
 */
public interface RideRepositoryCustom {
	
	/* This method updates the status field to false
	 * of a ride.
	 */
	public String softDelete(String rideId);
	
	/*This method will update the status of an occupant from pending to accepted
	 */
	public String acceptRideRequest(String rideId, String empId);
	
	/*This method will add an occupant to a ride with status pending
	 */
	public String addOccupant(String rideId, Map<String,String> occupant);
	
	/*This method will delete an occupant from the ride
	 */
	public String leaveCarpool(String rideId, Map<String,String> occupant);
	
	/*This method will update the ride information except occupants
	 */
	public String updateRide(String rideId, Ride ride);
	
}
