package com.jetnotifier.notification.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    @GetMapping("")
    public ResponseEntity<Map<String, Object>> healthCheck() {

        Map<String, Object> health = new HashMap<>();
        
        try {
            // Check MongoDB connection
            mongoTemplate.getDb().runCommand(new org.bson.Document("ping", 1));
            health.put("database", "UP");
        } catch (Exception e) {
            health.put("database", "DOWN");
        }

        try {
            // Check Kafka connection
            kafkaTemplate.getProducerFactory().createProducer().close();
            health.put("kafka", "UP");
        } catch (Exception e) {
            health.put("kafka", "DOWN");
        }

        health.put("status", "UP");
        health.put("service", "notification-system");
        
        return ResponseEntity.ok(health);
    }
}