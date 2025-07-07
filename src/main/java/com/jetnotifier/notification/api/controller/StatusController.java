package com.jetnotifier.notification.api.controller;

import com.jetnotifier.notification.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @GetMapping("/notification/{id}")
    public ResponseEntity<Map<String, Object>> getNotificationStatus(@PathVariable String id) {
        Map<String, Object> status = statusService.getNotificationStatus(id);
        return ResponseEntity.ok(status);
    }



    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getUserNotificationStats(@PathVariable String userId) {

        Map<String, Object> stats = statusService.getUserNotificationStats(userId);
        
        return ResponseEntity.ok(stats);
    
    }


    @GetMapping("/system")
    public ResponseEntity<Map<String, Object>> getSystemStatus() {
        Map<String, Object> systemStatus = statusService.getSystemStatus();
        return ResponseEntity.ok(systemStatus);
    }
    
}