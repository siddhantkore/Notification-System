package com.jetnotifier.notification.service;

import com.jetnotifier.notification.api.dto.request.NotificationRequest;
import com.jetnotifier.notification.api.dto.response.NotificationResponse;
import com.jetnotifier.notification.domain.entity.Notification;
import com.jetnotifier.notification.domain.entity.User;
import com.jetnotifier.notification.domain.enums.NotificationStatus;
import com.jetnotifier.notification.repository.NotificationRepository;
import com.jetnotifier.notification.repository.UserRepository;
import com.jetnotifier.notification.kafka.producer.NotificationProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    private NotificationProducer notificationProducer;

    @Autowired
    private NotificationService notificationService;
    
    public AdminService (NotificationProducer notificationProducer) {
    	this.notificationProducer = notificationProducer;
    }

    public Page<NotificationResponse> getAllNotifications(Pageable pageable) {
        Page<Notification> notifications = notificationRepository.findAll(pageable);
        return notifications.map(this::convertToResponse);
    }

    public NotificationResponse getNotificationById(String id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        return convertToResponse(notification);
    }

    public void broadcastNotification(NotificationRequest request) {
        List<User> users = userRepository.findAll();
        
        for (User user : users) {
            NotificationRequest userRequest = new NotificationRequest();
            userRequest.setUserId(user.getId());
            userRequest.setTitle(request.getTitle());
            userRequest.setMessage(request.getMessage());
            userRequest.setType(request.getType());
            userRequest.setPriority(request.getPriority());
            userRequest.setTemplateId(request.getTemplateId());
            userRequest.setChannelConfig(request.getChannelConfig());
            userRequest.setMetadata(request.getMetadata());
            userRequest.setScheduledAt(request.getScheduledAt());
            
            notificationService.createNotification(userRequest);
        }
    }

    public Map<String, Object> getNotificationStats() {
    	
        Map<String, Object> stats = new HashMap<>();
        
        long totalNotifications = notificationRepository.count();
        long sentNotifications = notificationRepository.countByStatus(NotificationStatus.SENT);
        long failedNotifications = notificationRepository.countByStatus(NotificationStatus.FAILED);
        long pendingNotifications = notificationRepository.countByStatus(NotificationStatus.PENDING);
        
        stats.put("total", totalNotifications);
        stats.put("sent", sentNotifications);
        stats.put("failed", failedNotifications);
        stats.put("pending", pendingNotifications);
        stats.put("successRate", totalNotifications > 0 ? (double) sentNotifications / totalNotifications * 100 : 0);
        
        return stats;
    }

    public void retryNotification(String id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        
        if (notification.getRetryCount() < notification.getMaxRetries()) {
            notification.setStatus(NotificationStatus.PENDING);
            notification.setRetryCount(notification.getRetryCount() + 1);
            notification.setErrorMessage(null);
            notificationRepository.save(notification);
            
            notificationProducer.sendNotification(notification);
        } else {
            throw new RuntimeException("Maximum retry limit reached");
        }
    }

    public void deleteNotification(String id) {
        notificationRepository.deleteById(id);
    }

    private NotificationResponse convertToResponse(Notification notification) {
        NotificationResponse response = new NotificationResponse();
        response.setId(notification.getId());
        response.setUserId(notification.getUserId());
        response.setTitle(notification.getTitle());
        response.setMessage(notification.getMessage());
        response.setType(notification.getType());
        response.setPriority(notification.getPriority());
        response.setStatus(notification.getStatus());
        response.setTemplateId(notification.getTemplateId());
        response.setChannelConfig(notification.getChannelConfig());
        response.setMetadata(notification.getMetadata());
        response.setScheduledAt(notification.getScheduledAt());
        response.setSentAt(notification.getSentAt());
        response.setCreatedAt(notification.getCreatedAt());
        response.setUpdatedAt(notification.getUpdatedAt());
        response.setRetryCount(notification.getRetryCount());
        response.setErrorMessage(notification.getErrorMessage());
        return response;
    }
}