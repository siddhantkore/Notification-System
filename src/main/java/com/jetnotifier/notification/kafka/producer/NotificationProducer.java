package com.jetnotifier.notification.kafka.producer;

import org.springframework.stereotype.Component;

/*
 * will put or send messages to Kafka 
 * consider key based storage like for email,  SMS, Push etc have have their own partition so that messages will be consumed fast
 * as each consumer will consume only one type of message ( initially See more about tread and tasks to perform operation
 */

@Component
public interface NotificationProducer {
	public void pushMessage(String message);
}
