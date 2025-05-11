package com.jetnotifier.notification.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
public class ConsumerException extends Exception {
	
	private static final long serialVersionUID = 1L;

	private final static String error = "CONSUMER_EXCEPTION";
	
	public ConsumerException(String message) {
		super(message);
	}
	
	public ConsumerException() {
		super(error);
	}
	
	
}
