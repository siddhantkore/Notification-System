package com.jetnotifier.notification.exception;

public class ConsumerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ConsumerException(String message) {
        super(message);
    }

    public ConsumerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConsumerException(Throwable cause) {
        super("CONSUMER_EXCEPTION", cause);
    }

    public ConsumerException() {

        super("CONSUMER_EXCEPTION");
    }
}
