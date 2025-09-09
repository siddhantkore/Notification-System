package com.jetnotifier.notification.exception;

import lombok.Getter;

@Getter
public class ChannelException extends Exception {

    private static final long serialVersionUID = 1L;

    private static final String error = "CHANNEL_EXCEPTION";

    public ChannelException(String message) {
        super(error);
    }

    public ChannelException() {
        super(error);
    }
}
