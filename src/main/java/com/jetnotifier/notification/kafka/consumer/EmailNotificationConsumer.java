package com.jetnotifier.notification.kafka.consumer;

import com.jetnotifier.notification.channel.EmailChannel;
import com.jetnotifier.notification.domain.entity.Notification;
import com.jetnotifier.notification.domain.entity.User;
import com.jetnotifier.notification.exception.ConsumerException;
import com.jetnotifier.notification.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


//Temp

@Component
public class EmailNotificationConsumer {

    @Autowired
    private EmailChannel emailChannel;
    
    @Autowired
    UserRepository userRepository;

    @KafkaListener(
        topics = "${kafka.topic.email}",
        groupId = "notification-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(Notification notification) {
        try {
            User user = userRepository.findById(notification.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

            boolean sent = emailChannel.send(notification, user);
            if (!sent) {
                throw new ConsumerException("Failed to send email notification");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ConsumerException("Failed to consume email notification", e);
        }
    }
}
