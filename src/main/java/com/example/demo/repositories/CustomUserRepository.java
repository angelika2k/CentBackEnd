package com.example.demo.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.usermodel.User;

@Repository

public class CustomUserRepository {
	@Autowired
    MongoTemplate mongoTemplate;
 
    public Integer getMaxUserId() {
		Query query = new Query();
		query.with(Sort.by(Sort.Direction.DESC, "id"));
		query.limit(1);
		User maxObject = mongoTemplate.findOne(query, User.class);
		if (maxObject == null) {
		    return 0;
		}
		return maxObject.getId();
    }
}
