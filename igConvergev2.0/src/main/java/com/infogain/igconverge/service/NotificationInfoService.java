package com.infogain.igconverge.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.infogain.igconverge.model.NotificationInfo;
import com.infogain.igconverge.repository.NotificationInfoRepository;

@Service("notificationInfoService")
public class NotificationInfoService implements NotificationInfoHandler
{

	@Autowired
	NotificationInfoRepository notificationInfoRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	
	public void saveNotification(NotificationInfo notificationInfo) 
	{
		notificationInfoRepository.save(notificationInfo);
	}

	
	public List<NotificationInfo> fetchNotification(String type, Date date) 
	{
		Query query = new Query(Criteria.where("date").gte(date).and("type").is(type));
		query.with(new Sort(Sort.Direction.DESC, "date"));
		return mongoTemplate.find(query, NotificationInfo.class);
	}

}
