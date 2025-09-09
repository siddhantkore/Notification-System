package com.jetnotifier.notification.channel;

import com.jetnotifier.notification.domain.entity.Notification;
import com.jetnotifier.notification.domain.entity.User;
import com.jetnotifier.notification.domain.enums.NotificationType;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WebHookChannel implements NotificationChannel {

    private final RestTemplate restTemplate;

    @Autowired
    public WebHookChannel(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean send(Notification notification, User user) {
        try {
            // Get webhook URL from channel config
            if (notification.getChannelConfig() == null
                    || !notification.getChannelConfig().containsKey("webhookUrl")) {
                return false;
            }

            String webhookUrl = (String) notification.getChannelConfig().get("webhookUrl");

            Map<String, Object> payload = new HashMap<>();
            payload.put("notificationId", notification.getId());
            payload.put("userId", user.getId());
            payload.put("title", notification.getTitle());
            payload.put("message", notification.getMessage());
            payload.put("type", notification.getType());
            payload.put("metadata", notification.getMetadata());
            payload.put("timestamp", notification.getCreatedAt());

            restTemplate.postForObject(webhookUrl, payload, String.class);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getChannelType() {
        return NotificationType.WEBHOOK.name();
    }
}
