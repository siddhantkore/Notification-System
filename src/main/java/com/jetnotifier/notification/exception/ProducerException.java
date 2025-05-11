package com.jetnotifier.notification.exception;

import lombok.Getter;

@Getter
public class ProducerException extends Exception {
	
	private static final String error = "PRODUCER_EXCEPTION"; 
	
	public ProducerException(String message) {
		super(message);
	}
	
	public ProducerException () {
		super(error);
	}
	
}
