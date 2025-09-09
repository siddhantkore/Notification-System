package com.jetnotifier.notification.service;

import com.jetnotifier.notification.domain.entity.Notification;
import com.jetnotifier.notification.domain.enums.NotificationStatus;
import com.jetnotifier.notification.repository.NotificationRepository;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    @Autowired private NotificationRepository notificationRepository;

    public Map<String, Object> getNotificationStatus(String id) {

        Notification notification =
                notificationRepository
                        .findById(id)
                        .orElseThrow(() -> new RuntimeException("Notification not found"));

        Map<String, Object> status = new HashMap<>();
        status.put("id", notification.getId());
        status.put("status", notification.getStatus());
        status.put("createdAt", notification.getCreatedAt());
        status.put("sentAt", notification.getSentAt());
        status.put("retryCount", notification.getRetryCount());
        status.put("errorMessage", notification.getErrorMessage());

        return status;
    }

    public Map<String, Object> getUserNotificationStats(String userId) {
        Map<String, Object> stats = new HashMap<>();

        long totalNotifications = notificationRepository.countByUserId(userId);
        long sentNotifications =
                notificationRepository.countByUserIdAndStatus(userId, NotificationStatus.SENT);
        long failedNotifications =
                notificationRepository.countByUserIdAndStatus(userId, NotificationStatus.FAILED);
        long pendingNotifications =
                notificationRepository.countByUserIdAndStatus(userId, NotificationStatus.PENDING);

        stats.put("userId", userId);
        stats.put("total", totalNotifications);
        stats.put("sent", sentNotifications);
        stats.put("failed", failedNotifications);
        stats.put("pending", pendingNotifications);

        return stats;
    }

    public Map<String, Object> getSystemStatus() {
        Map<String, Object> systemStatus = new HashMap<>();

        long totalNotifications = notificationRepository.count();
        long sentNotifications = notificationRepository.countByStatus(NotificationStatus.SENT);
        long failedNotifications = notificationRepository.countByStatus(NotificationStatus.FAILED);
        long pendingNotifications =
                notificationRepository.countByStatus(NotificationStatus.PENDING);
        long processingNotifications =
                notificationRepository.countByStatus(NotificationStatus.PROCESSING);

        systemStatus.put("total", totalNotifications);
        systemStatus.put("sent", sentNotifications);
        systemStatus.put("failed", failedNotifications);
        systemStatus.put("pending", pendingNotifications);
        systemStatus.put("processing", processingNotifications);

        systemStatus.put(
                "successRate",
                totalNotifications > 0 ? (double) sentNotifications / totalNotifications * 100 : 0);

        return systemStatus;
    }
}
