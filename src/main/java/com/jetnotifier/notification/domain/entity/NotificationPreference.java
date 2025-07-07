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
    
    

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public NotificationType getType() {
		return type;
	}

	public void setType(NotificationType type) {
		this.type = type;
	}

	public Boolean getEmailEnabled() {
		return emailEnabled;
	}

	public void setEmailEnabled(Boolean emailEnabled) {
		this.emailEnabled = emailEnabled;
	}

	public Boolean getSmsEnabled() {
		return smsEnabled;
	}

	public void setSmsEnabled(Boolean smsEnabled) {
		this.smsEnabled = smsEnabled;
	}

	public Boolean getPushEnabled() {
		return pushEnabled;
	}

	public void setPushEnabled(Boolean pushEnabled) {
		this.pushEnabled = pushEnabled;
	}

	public Boolean getWebhookEnabled() {
		return webhookEnabled;
	}

	public void setWebhookEnabled(Boolean webhookEnabled) {
		this.webhookEnabled = webhookEnabled;
	}

	public String getQuietHoursStart() {
		return quietHoursStart;
	}

	public void setQuietHoursStart(String quietHoursStart) {
		this.quietHoursStart = quietHoursStart;
	}

	public String getQuietHoursEnd() {
		return quietHoursEnd;
	}

	public void setQuietHoursEnd(String quietHoursEnd) {
		this.quietHoursEnd = quietHoursEnd;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public Map<String, Integer> getFrequencyLimit() {
		return frequencyLimit;
	}

	public void setFrequencyLimit(Map<String, Integer> frequencyLimit) {
		this.frequencyLimit = frequencyLimit;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

    
    
    
}