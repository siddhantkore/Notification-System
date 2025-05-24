package com.jetnotifier.notification.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jetnotifier.notification.domain.enums.NotificationStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/jetnotifier/status")
public class StatusController {
	
	
	
	
	@GetMapping
	public String getStatus(@RequestParam String userid, @RequestParam String clientid) { // 
		return "will send pending, sent, failed status of a resuest";
	}
	
	
	public String getNotificationStatus(@RequestParam String notificationid) {
		// looking for it still configuring 
		return "give the status of a message by notification id if has";
	}
	// many more like
		// all stats about a client
		// all stats about a user
	
	
}
