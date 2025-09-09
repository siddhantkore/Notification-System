package com.jetnotifier.notification.domain.entity;

import com.jetnotifier.notification.domain.enums.NotificationPriority;
import com.jetnotifier.notification.domain.enums.NotificationStatus;
import com.jetnotifier.notification.domain.enums.NotificationType;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Document(collection = "notifications")
public class Notification {

    @Id private String id;

    @Field("user_id")
    private String userId;

    private String title;
    private String message;
    private NotificationType type;
    private NotificationPriority priority;
    private NotificationStatus status;

    @Field("template_id")
    private String templateId;

    @Field("channel_config")
    private Map<String, Object> channelConfig;

    @Field("metadata")
    private Map<String, Object> metadata;

    @Field("scheduled_at")
    private LocalDateTime scheduledAt;

    @Field("sent_at")
    private LocalDateTime sentAt;

    @Field("created_at")
    private LocalDateTime createdAt;

    @Field("updated_at")
    private LocalDateTime updatedAt;

    @Field("retry_count")
    private Integer retryCount = 0;

    @Field("max_retries")
    private Integer maxRetries = 3;

    @Field("error_message")
    private String errorMessage;

    public Notification() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = NotificationStatus.PENDING;
    }

    public Notification(String userId, String title, String message, NotificationType type) {
        this();
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.type = type;
        this.priority = NotificationPriority.MEDIUM;
    }
}
/*
{
	"userId": "user123",
	"templateId": "order-shipped",
	"data": {
	"orderNumber": "ORD-12345",
	"trackingNumber": "TRK-9876",
	"estimatedDelivery": "2025-04-24",
	"link" : "https://app/link"
},
"channels": ["EMAIL", "SMS"]
}
*/
