package com.jetnotifier.notification.channel;

import com.jetnotifier.notification.domain.entity.Notification;
import com.jetnotifier.notification.domain.entity.User;
import com.jetnotifier.notification.domain.enums.NotificationType;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class PushChannel implements NotificationChannel {

    @Override
    public boolean send(Notification notification, User user) {
        try {
            if (user.getPushToken() == null || user.getPushToken().isEmpty()) {
                return false;
            }

            Map<String, Object> pushPayload = new HashMap<>();
            pushPayload.put("to", user.getPushToken());
            pushPayload.put("title", notification.getTitle());
            pushPayload.put("body", notification.getMessage());
            pushPayload.put("data", notification.getMetadata());

            // This would typically integrate with FCM, APNS, or other push services
            // For demo purposes, we'll just log it
            System.out.println("Sending push notification: " + pushPayload);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getChannelType() {
        return NotificationType.PUSH.name();
    }
}