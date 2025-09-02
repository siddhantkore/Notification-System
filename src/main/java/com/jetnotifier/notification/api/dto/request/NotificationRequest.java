package com.jetnotifier.notification.api.dto.request;

import com.jetnotifier.notification.domain.enums.NotificationPriority;
import com.jetnotifier.notification.domain.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Map;

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
    
    
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public NotificationType getType() {
		return type;
	}
	public void setType(NotificationType type) {
		this.type = type;
	}
	public NotificationPriority getPriority() {
		return priority;
	}
	public void setPriority(NotificationPriority priority) {
		this.priority = priority;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public Map<String, Object> getChannelConfig() {
		return channelConfig;
	}
	public void setChannelConfig(Map<String, Object> channelConfig) {
		this.channelConfig = channelConfig;
	}
	public Map<String, Object> getMetadata() {
		return metadata;
	}
	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}
	public LocalDateTime getScheduledAt() {
		return scheduledAt;
	}
	public void setScheduledAt(LocalDateTime scheduledAt) {
		this.scheduledAt = scheduledAt;
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