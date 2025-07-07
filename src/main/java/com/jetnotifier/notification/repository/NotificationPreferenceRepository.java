package com.jetnotifier.notification.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jetnotifier.notification.domain.entity.NotificationPreference;

public interface NotificationPreferenceRepository extends MongoRepository<NotificationPreference, String> {

	List<NotificationPreference> findByUserId(String userId);

	Optional<NotificationPreference> findById(String id);

}
