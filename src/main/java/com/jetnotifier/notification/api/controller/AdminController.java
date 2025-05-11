package com.jetnotifier.notification.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("jetnotifier/admin")
public class AdminController {
	
	@GetMapping("/login")
	public String logInAdmin() {
		return "Not Set Up Yet";
	}
	
	@PostMapping("/signup")
	public String signUpAdmin() {
		return "Not Yet Ready";
	}
	
}
