package com.jetnotifier.notification.domain.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Setter;

@Data
@Document
@Setter
public class Notification {
	
	private String name;
	
	public void setName(String name) {
		this.name = name;
	}
	
}
