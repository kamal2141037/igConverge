package com.infogain.igconverge.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.infogain.igconverge.model.Ride;

@Repository
public interface RideRepository extends MongoRepository<Ride, String>, RideRepositoryCustom
{
	@Query(value = "{ '_id' : ?0 }")
	public Ride findById(String rideId);
	
	@Query(value = "{ 'user.id' : ?0, 'status':true }")
	public List<Ride> findByUserIdAndStatusTrue(String employeeId);
}
