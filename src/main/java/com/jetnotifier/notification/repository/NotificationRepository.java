package com.jetnotifier.notification.repository;

import com.jetnotifier.notification.domain.entity.Notification;
import com.jetnotifier.notification.domain.enums.NotificationStatus;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

    public long countByStatus(NotificationStatus status);

    public List<Notification> findByUserIdOrderByCreatedAtDesc(String userId);

    public long countByUserId(String userId);

    public long countByUserIdAndStatus(String userId, NotificationStatus sent);
}
