package com.jetnotifier.notification.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DeadLetterConsumer {

    @KafkaListener(
        topics = "${kafka.topic.dlq}",
        groupId = "notification-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeFromDLQ(Object failedRecord) {
        System.out.println("Received message from DLQ: " + failedRecord);
        // Optionally log to file, alert, or store in a failed_notifications collection
    }
}
