package com.infogain.igconverge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.infogain.igconverge.model.DeviceInfo;
import com.infogain.igconverge.repository.DeviceInfoRepository;

@Service("deviceInfoService")
public class DeviceInfoService implements DeviceInfoHandler
{

	@Autowired
	DeviceInfoRepository deviceInfoRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	public DeviceInfo findByTokenIdAndDeviceType(String tokenId, String deviceType) 
	{
		return deviceInfoRepository.findByTokenIdAndDeviceType(tokenId, deviceType);
	}

	
	public void saveInfo(DeviceInfo deviceInfo) 
	{
		deviceInfoRepository.save(deviceInfo);
	}
	
	public List<DeviceInfo> findAllDevice()
	{
		return deviceInfoRepository.findAll();
	}
	
	public List<DeviceInfo> findAllDeviceByDeviceType(String deviceType)
	{
		Query query = new Query(Criteria.where("deviceType").is(deviceType));
		query.fields().include("tokenId");
		return mongoTemplate.find(query, DeviceInfo.class);
	}

	public void removeUnregisteredDevice(String tokenId, String deviceType) 
	{
		Query query = new Query(Criteria.where("deviceType").is(deviceType).andOperator(Criteria.where("_id").is(tokenId)));
		mongoTemplate.remove(query, DeviceInfo.class);
		
	}

	public DeviceInfo findByEmployeeId(String empId) {
		return deviceInfoRepository.findByEmployeeId(empId);
	}

}
