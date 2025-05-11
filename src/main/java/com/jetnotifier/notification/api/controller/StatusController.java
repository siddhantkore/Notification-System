package com.jetnotifier.notification.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/jetnotifier/status")
public class StatusController {
	
	@GetMapping("?userid && ?clientid")
	public String getStatus() { // 
		return "will send pending, sent, failed status of a resuest";
	}
	
	
	public String getNotificationStatus() {
		// looking for it still configuring 
		return "give the status of a message by notification id if has";
	}
	// many more like
		// all stats about a client
		// all stats about a user
	
}
