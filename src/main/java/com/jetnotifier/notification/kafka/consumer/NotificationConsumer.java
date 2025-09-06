package com.jetnotifier.notification.kafka.consumer;

import com.jetnotifier.notification.channel.NotificationChannel;
import com.jetnotifier.notification.domain.entity.Notification;
import com.jetnotifier.notification.domain.entity.User;
import com.jetnotifier.notification.domain.enums.NotificationStatus;
import com.jetnotifier.notification.exception.ConsumerException;
import com.jetnotifier.notification.repository.NotificationRepository;
import com.jetnotifier.notification.repository.UserRepository;
import com.jetnotifier.notification.utils.NotificationChannelRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class NotificationConsumer {

    private final NotificationChannelRegistry registry;
    
    private final UserRepository userRepository;
    
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationConsumer(
            NotificationChannelRegistry registry,
            UserRepository userRepository,
            NotificationRepository notificationRepository
    ) {
        this.registry = registry;
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    @KafkaListener(
        topics = {
            "${kafka.topic.email}",
            "${kafka.topic.push}",
            "${kafka.topic.sms}",
            "${kafka.topic.webhook}",
            "${kafka.topic.in_app}"
        },
        groupId = "notification-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(Notification notification) {
        try {
            // Load user
        	notification.setStatus(NotificationStatus.PROCESSING);
        	notification.setUpdatedAt(LocalDateTime.now());
        	
        	notificationRepository.save(notification);
        	
        	User user = userRepository.findById(notification.getUserId())
                    .orElseThrow(() -> new ConsumerException("User not found for ID: " + notification.getUserId()));

            // Resolve channel
            String typeKey = notification.getType().name(); // e.g., EMAIL
            NotificationChannel channel = registry.getChannel(typeKey);

            if (channel == null) {
                throw new ConsumerException("No channel found for type: " + typeKey);
            }

            boolean sent = channel.send(notification, user);
            
            if(sent) {
            	notification.setStatus(NotificationStatus.SENT);
            	notification.setSentAt(LocalDateTime.now());
            } else {
				notification.setStatus(NotificationStatus.FAILED);
				notification.setRetryCount(notification.getRetryCount()+1);
				notification.setErrorMessage("Channel send returned false");
			}

        } catch (Exception ex) {
            ex.printStackTrace();
            notification.setStatus(NotificationStatus.FAILED);
			notification.setRetryCount(notification.getRetryCount()+1);
			notification.setErrorMessage(ex.getMessage());
			
        } finally {
			notification.setUpdatedAt(LocalDateTime.now());
			notificationRepository.save(notification);
		}
    }
}
