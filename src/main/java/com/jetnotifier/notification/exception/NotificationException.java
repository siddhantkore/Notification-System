package com.jetnotifier.notification.exception;

public class NotificationException extends Exception {

    private static final long serialVersionUID = 1L;

    private static final String error = "NOTIFICATION_EXCEPTION";

    public NotificationException(String message) {
        super(message);
    }

    public NotificationException() {
        super(error);
    }
}
