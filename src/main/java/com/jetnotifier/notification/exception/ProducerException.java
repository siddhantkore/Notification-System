package com.jetnotifier.notification.exception;

import lombok.Getter;

@Getter
public class ProducerException extends Exception {

    private static final long serialVersionUID = 1L;
    private static final String ERROR = "PRODUCER_EXCEPTION";

    public ProducerException(String message) {
        super(message);
    }

    public ProducerException() {
        super(ERROR);
    }
}
