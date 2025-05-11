package com.jetnotifier.notification.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jetnotifier.notification.domain.entity.Notification;

import jakarta.persistence.Id;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, Id> {
	
}
