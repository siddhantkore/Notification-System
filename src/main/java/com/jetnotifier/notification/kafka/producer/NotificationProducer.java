package com.jetnotifier.notification.kafka.producer;

import com.jetnotifier.notification.domain.entity.Notification;
import com.jetnotifier.notification.domain.enums.NotificationType;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationProducer {

	// Hard coded value avoid keep them configurable 
 ///   private static final String NOTIFICATION_TOPIC = "notification-requests";
 //   private static final String BATCH_NOTIFICATION_TOPIC = "batch-notification-requests";

    @Value("${kafka.topic.email}")
    private String emailTopic;

    @Value("${kafka.topic.push}")
    private String pushTopic;

    @Value("${kafka.topic.sms}")
    private String smsTopic;

    @Value("${kafka.topic.webhook}")
    private String webhookTopic;

    @Value("${kafka.topic.in_app}")
    private String inAppTopic;

    
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    public NotificationProducer (KafkaTemplate<String, Object> kafkaTemplate) {
    	this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(Notification notification) {
        String topic = resolveTopic(notification.getType());
        kafkaTemplate.send(topic, notification.getId(), notification);
        
        
    }

    private String resolveTopic(NotificationType type) {
        return switch (type) {
            case EMAIL -> emailTopic;
            case PUSH -> pushTopic;
            case SMS -> smsTopic;
            case WEBHOOK -> webhookTopic;
            case IN_APP -> inAppTopic;
        };
    }
    
    
}