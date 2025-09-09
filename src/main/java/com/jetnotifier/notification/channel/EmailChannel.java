package com.jetnotifier.notification.channel;

import com.jetnotifier.notification.domain.entity.Notification;
import com.jetnotifier.notification.domain.entity.User;
import com.jetnotifier.notification.domain.enums.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailChannel implements NotificationChannel {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailChannel(JavaMailSender javaMailSender) {
        this.mailSender = javaMailSender;
    }

    @Override
    public boolean send(Notification notification, User user) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject(notification.getTitle());
            message.setText(notification.getMessage());
            message.setFrom(
                    "megastorage2112@gmail.com"); // Consider Configurable and fetch from external
            // sources instead of hard coding

            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getChannelType() {
        return NotificationType.EMAIL.name();
    }
}
