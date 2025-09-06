package com.jetnotifier.notification.api.dto.request;

import com.jetnotifier.notification.domain.enums.NotificationPriority;
import com.jetnotifier.notification.domain.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Setter
@Getter
public class NotificationRequest {
    @NotBlank(message = "User ID is required")
    private String userId;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Message is required")
    private String message;
    
    @NotNull(message = "Notification type is required")
    private NotificationType type;
    
    private NotificationPriority priority = NotificationPriority.MEDIUM;
    private String templateId;
    private Map<String, Object> channelConfig;
    private Map<String, Object> metadata;
    private LocalDateTime scheduledAt;

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