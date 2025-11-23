package com.jetnotifier.notification.exception;

public class TemplateException extends Exception {

    private static final long serialVersionUID = 1L;

    private static final String ERROR = "TEMPLATE_ERROR";

    public TemplateException(String message) {
        super(message);
    }

    public TemplateException() {
        super(ERROR);
    }
}
