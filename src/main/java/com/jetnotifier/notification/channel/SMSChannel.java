package com.jetnotifier.notification.channel;

import com.jetnotifier.notification.domain.entity.Notification;
import com.jetnotifier.notification.domain.entity.User;
import com.jetnotifier.notification.domain.enums.NotificationType;
import org.springframework.stereotype.Component;

@Component
public class SMSChannel implements NotificationChannel {

    @Override
    public boolean send(Notification notification, User user) {
        try {
            if (user.getPhone() == null || user.getPhone().isEmpty()) {
                return false;
            }

            // This would typically integrate with Twilio, AWS SNS, or other SMS services
            // For demo purposes, we'll just log it
            System.out.println(
                    "Sending SMS to " + user.getPhone() + ": " + notification.getMessage());

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getChannelType() {
        return NotificationType.SMS.name();
    }
}
