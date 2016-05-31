package com.infogain.igconverge.service;

import java.util.List;
import java.util.Map;

import com.infogain.igconverge.model.Ride;

/**
 * Interface for Ride Service - includes all functions related to Ride workflow
 * 
 * @author Infogain Dev Team
 * 
 */
public interface RideHandler {
	public String insert(Ride ride);
	
	public List<Ride> getAllRides();
	
	
	public String softDelete(String id);

	public String acceptRideRequest(String rideId, String empId);
	
	public String addOccupant(String rideId, Map<String,String> occupant);
	
	public String leaveCarpool(String rideId, Map<String,String> occupant);
	
	public String updateRide(String rideId, Ride ride);
	
	//public List<Ride> getAllRidesByEmployeeId(String employeeId);
	
	public List<Ride> findByUserIdAndStatusTrue(String employeeId);
	
	public Ride findById(String rideId);
}