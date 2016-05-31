package com.infogain.igconverge.repository;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.infogain.igconverge.model.NotificationInfo;

@Repository
public interface NotificationInfoRepository extends MongoRepository<NotificationInfo, Serializable>
{

}
