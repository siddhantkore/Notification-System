package com.jetnotifier.notification.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jetnotifier.notification.domain.entity.User;


@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
	
}
