package com.jetnotifier.notification.api.dto.response;

import com.jetnotifier.notification.domain.enums.NotificationPriority;
import com.jetnotifier.notification.domain.enums.NotificationStatus;
import com.jetnotifier.notification.domain.enums.NotificationType;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotificationResponse {
    private String id;
    private String userId;
    private String title;
    private String message;

    private NotificationType type;
    private NotificationPriority priority;
    private NotificationStatus status;

    private String templateId;

    private Map<String, Object> channelConfig;
    private Map<String, Object> metadata;

    private LocalDateTime scheduledAt;
    private LocalDateTime sentAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Integer retryCount;
    private String errorMessage;
}
