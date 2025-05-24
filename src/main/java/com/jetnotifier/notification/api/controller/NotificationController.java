package com.jetnotifier.notification.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jetnotifier.notification.api.dto.request.NotificationRequest;
import com.jetnotifier.notification.domain.entity.User;
import com.jetnotifier.notification.repository.NotificationRepository;
import com.jetnotifier.notification.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/jetnotifier/notification")
public class NotificationController {
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/send")
	public String sendNotification(@RequestBody NotificationRequest notification) {
		
		User user = new User();
		user.setId(1);
		user.setEmail("a@gmail.com");
		user.setName("A");
		user.setPassword("909");
		user.setPhoneNo(00000);
		user.setUsername("siddhant");
		
		userRepository.save(user);
		
		return "Success";
	}
	
}
