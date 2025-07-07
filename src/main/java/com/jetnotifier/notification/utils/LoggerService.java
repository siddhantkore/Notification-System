package com.jetnotifier.notification.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggerService {

    public Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    
  
    public void info(Logger logger, String message, Object... args) {
       
    	
    	if (logger.isInfoEnabled()) {
            logger.info(format(message, args));
        }
    }

    public void warn(Logger logger, String message, Object... args) {
        if (logger.isWarnEnabled()) {
            logger.warn(format(message, args));
        }
    }
    
  
    public void error(Logger logger, String message, Object... args) {
        if (logger.isErrorEnabled()) {
            logger.error(format(message, args));
        }
    }

    
    private String format(String message, Object... args) {
        return String.format(message, args);
    }
}
