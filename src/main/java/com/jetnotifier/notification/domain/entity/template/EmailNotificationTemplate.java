package com.jetnotifier.notification.domain.entity.template;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import java.time.LocalDateTime;
import java.util.Map;


@Getter
@Setter
@Document(collection = "email_notification_templates")
public class EmailNotificationTemplate {

    @Id
    private String id;
    
    private String name;

    private String subject;
    
    @Field("html_body")
    private String htmlBody;
    
    @Field("text_body")
    private String textBody;
    
    @Field("from_email")
    private String fromEmail;
    
    @Field("from_name")
    private String fromName;
    
    @Field("variables")
    private Map<String, String> variables;
    
    @Field("is_active")
    private boolean isActive = true;
    
    @Field("created_at")
    private LocalDateTime createdAt;
    
    @Field("updated_at")
    private LocalDateTime updatedAt;

    public EmailNotificationTemplate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}