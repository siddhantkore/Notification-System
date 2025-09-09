package com.jetnotifier.notification.api.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @Autowired private MongoTemplate mongoTemplate;

    @Autowired private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * @return return UP If everything is ok Till Kafka not configured
     */
    @GetMapping("/")
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
            // Check Kafka connection // not correct
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
