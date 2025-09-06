package com.jetnotifier.notification.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import com.jetnotifier.notification.domain.enums.NotificationType;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Setter
@Getter
@Document(collection = "templates")
public class Template {
    @Id
    private String id;
    
    private String name;
    private String subject;
    private String body;
    private NotificationType type;
    
    @Field("variables")
    private Map<String, String> variables;
    
    @Field("is_active")
    private Boolean isActive = true;
    
    @Field("created_at")
    private LocalDateTime createdAt;
    
    @Field("updated_at")
    private LocalDateTime updatedAt;

    public Template() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

}