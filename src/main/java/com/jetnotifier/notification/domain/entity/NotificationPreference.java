package com.jetnotifier.notification.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import com.jetnotifier.notification.domain.enums.NotificationType;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Document(collection = "notification_preferences")
public class NotificationPreference {
    @Id
    private String id;
    
    @Field("user_id")
    private String userId;
    
    private NotificationType type;
    
    @Field("email_enabled")
    private Boolean emailEnabled = true;
    
    @Field("sms_enabled")
    private Boolean smsEnabled = true;
    
    @Field("push_enabled")
    private Boolean pushEnabled = true;
    
    @Field("webhook_enabled")
    private Boolean webhookEnabled = false;
    
    @Field("quiet_hours_start")
    private String quietHoursStart;
    
    @Field("quiet_hours_end")
    private String quietHoursEnd;
    
    @Field("timezone")
    private String timezone = "UTC";
    
    @Field("frequency_limit")
    private Map<String, Integer> frequencyLimit;
    
    @Field("created_at")
    private LocalDateTime createdAt;
    
    @Field("updated_at")
    private LocalDateTime updatedAt;

    
    public NotificationPreference() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}