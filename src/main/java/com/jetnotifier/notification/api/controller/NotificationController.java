package com.jetnotifier.notification.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jetnotifier.notification.api.dto.request.NotificationRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/jetnotifier/notification")
public class NotificationController {
	
	@PostMapping("/send")
	public String sendNotification(@RequestBody NotificationRequest notification) {
		return "The Notification object will be sent to the Kafka";
	}
	
}
