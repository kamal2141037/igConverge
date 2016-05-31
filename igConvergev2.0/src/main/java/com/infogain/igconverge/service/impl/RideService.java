package com.infogain.igconverge.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infogain.igconverge.model.Ride;
import com.infogain.igconverge.repository.RideRepository;
import com.infogain.igconverge.service.RideHandler;
import com.infogain.igconverge.util.IGConvergeLogger;

/**
 * Implements Ride Interface. Will include business logic related to the Ride
 * workflow.
 * 
 * @author Infogain Dev Team
 * 
 */
@Service("rideService")
public class RideService implements RideHandler {

	@Autowired
	RideRepository rideRepository;

	@IGConvergeLogger
	Logger logger;

	public String insert(Ride ride) {
		logger.debug("Insert ride  service method invoked");
		rideRepository.save(ride);
		return "Ride Saved";
	}
	
	public List<Ride> getAllRides() 
	{
		logger.debug("Request for get all rides method of service.");
		return rideRepository.findAll();
	}
	
	public String softDelete(String id) {
		logger.debug("Delete ride service method invoked");
		return rideRepository.softDelete(id);
	}
	public String acceptRideRequest(String rideId, String empId)
	{
		logger.debug("acceptRideRequest service method invoked");
		return rideRepository.acceptRideRequest(rideId,empId);
	}
	
	
	public String addOccupant(String rideId, Map<String,String> occupant)
	{
		logger.debug("addOccupant service method invoked");
		return rideRepository.addOccupant(rideId, occupant);
	}
	public String leaveCarpool(String rideId, Map<String,String> occupant)
	{
		logger.debug("leaveCarpool service method invoked");
		return rideRepository.leaveCarpool(rideId, occupant);
	}

	public String updateRide(String rideId, Ride ride)
	{
		logger.debug("updateRide service method invoked");
		return rideRepository.updateRide(rideId, ride);
	}
	
	public List<Ride> findByUserIdAndStatusTrue(String employeeId)
	{
		logger.debug("getAllRidesByEmployeeId service method invoked");
		return rideRepository.findByUserIdAndStatusTrue(employeeId);
	}

	public Ride findById(String rideId)
	{
		logger.debug("findById(Ride) service method invoked");
		return rideRepository.findById(rideId);
	}
}
