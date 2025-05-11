package com.jetnotifier.notification.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Entity
@Getter
@Setter
public class User {

	private int id;
	
	private String name;
	
	private String username;
	
	private String password;
	
	private String email;
	
	private double phoneNo; 
	
}
