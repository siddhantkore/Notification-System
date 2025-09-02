package com.jetnotifier.notification.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jetnotifier.notification.domain.entity.User;


@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
	
	Optional<User> findByEmailAndPassword(String email, String password);

	Optional<User> findById(String userId);
}
