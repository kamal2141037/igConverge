package com.infogain.igconverge.repository;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.infogain.igconverge.model.DeviceInfo;

@Repository
public interface DeviceInfoRepository extends MongoRepository<DeviceInfo, Serializable>
{
	@Query(value = "{ '_id' : ?0,'deviceType':?1}")
	public DeviceInfo findByTokenIdAndDeviceType(String tokenId, String deviceType);
	
	@Query(value = "{ 'employeeId' : ?0}")
	public DeviceInfo findByEmployeeId(String employeeId);
}
