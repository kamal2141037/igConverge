package com.infogain.igconverge.repository;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.infogain.igconverge.model.UpdateInfo;

@Repository
public interface UpdateInfoRepository extends MongoRepository<UpdateInfo, Serializable>
{
	
}
