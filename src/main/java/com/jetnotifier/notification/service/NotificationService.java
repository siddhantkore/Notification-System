package com.jetnotifier.notification.service;

import com.jetnotifier.notification.api.dto.request.NotificationRequest;
import com.jetnotifier.notification.api.dto.response.NotificationResponse;
import com.jetnotifier.notification.domain.entity.Notification;
import com.jetnotifier.notification.domain.enums.NotificationStatus;
import com.jetnotifier.notification.repository.NotificationRepository;
import com.jetnotifier.notification.kafka.producer.NotificationProducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

   
    private NotificationRepository notificationRepository;

   
    private NotificationProducer notificationProducer;
   
//    private final Notification notification;
    
    @Autowired
    public NotificationService(NotificationRepository notificationRepository, NotificationProducer notificationProducer) {
//    	this.notification = notification;
    	this.notificationProducer = notificationProducer;
    	this.notificationRepository = notificationRepository;
    }

    public NotificationResponse createNotification(NotificationRequest request) {

        Notification notification = new Notification();

        notification.setUserId(request.getUserId());
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setType(request.getType());
        notification.setPriority(request.getPriority());
        notification.setTemplateId(request.getTemplateId());
        notification.setChannelConfig(request.getChannelConfig());
        notification.setMetadata(request.getMetadata());
        notification.setScheduledAt(request.getScheduledAt());

        if (request.getScheduledAt() != null && request.getScheduledAt().isAfter(LocalDateTime.now())) {
            notification.setStatus(NotificationStatus.SCHEDULED);
        } else {
            notification.setStatus(NotificationStatus.PENDING);
        }


        notification = notificationRepository.save(notification);

        // Send to Kafka for processing
        if (notification.getStatus() == NotificationStatus.PENDING) {
            notificationProducer.sendNotification(notification);
        }

        return convertToResponse(notification);
    }




    public NotificationResponse getNotificationById(String id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        return convertToResponse(notification);
    }

    public List<NotificationResponse> getNotificationsByUserId(String userId) {
        List<Notification> notifications = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return notifications.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    
    public Page<NotificationResponse> getAllNotifications(Pageable pageable) {
        Page<Notification> notifications = notificationRepository.findAll(pageable);
        return notifications.map(this::convertToResponse);
    }

    
    public void updateNotificationStatus(String id, String status) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        
        notification.setStatus(NotificationStatus.valueOf(status.toUpperCase()));
        if (status.equals("SENT")) {
            notification.setSentAt(LocalDateTime.now());
        }
        notificationRepository.save(notification);
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