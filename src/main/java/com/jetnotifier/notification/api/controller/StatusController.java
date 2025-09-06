package com.jetnotifier.notification.api.controller;

import com.jetnotifier.notification.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    /**
     * 
     * @param id
     * @return
     */
    @GetMapping("/notification/{id}")
    public ResponseEntity<Map<String, Object>> getNotificationStatus(@PathVariable String id) {
        Map<String, Object> status = statusService.getNotificationStatus(id);
        return ResponseEntity.ok(status);
    }



    /**
     * 
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getUserNotificationStats(@PathVariable String userId) {

        Map<String, Object> stats = statusService.getUserNotificationStats(userId);
        
        return ResponseEntity.ok(stats);
    
    }


    /**
     * 
     * @return
     */
    @GetMapping("/system")
    public ResponseEntity<Map<String, Object>> getSystemStatus() {
        Map<String, Object> systemStatus = statusService.getSystemStatus();
        return ResponseEntity.ok(systemStatus);
    }
    
}