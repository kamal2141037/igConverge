package com.infogain.igconverge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.infogain.igconverge.model.UpdateInfo;
import com.infogain.igconverge.repository.UpdateInfoRepository;

@Service("updateInfoService")
public class UpdateInfoService implements UpdateInfoHandler
{

	@Autowired
	UpdateInfoRepository updateInfoRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;

	public List<UpdateInfo> findByDevice(String deviceType, boolean serviceType) 
	{
		Query query = new Query(Criteria.where("deviceType").is(deviceType));
		query.with(new Sort(Sort.Direction.DESC, "version"));
		if(serviceType)
		{
			query.addCriteria(Criteria.where("isMandatory").is("true"));
		}
		return mongoTemplate.find(query, UpdateInfo.class);
	}

	public UpdateInfo findByVersionAndDevice(String version, String deviceType) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveUpdateInfo(UpdateInfo updateInfo) 
	{
		updateInfoRepository.save(updateInfo);
	}
	
	
	
}
