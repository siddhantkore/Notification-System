package com.jetnotifier.notification.domain.entity;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
@Document(collection = "users")
public class User {
    @Id private String id;

    private String email;
    private String phone;
    private String name;
    private String password;

    @Field("push_token")
    private String pushToken;

    @Field("preferences")
    private Map<String, Object> preferences;

    @Field("created_at")
    private LocalDateTime createdAt;

    @Field("updated_at")
    private LocalDateTime updatedAt;

    public User() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
