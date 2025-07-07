package com.jetnotifier.notification.api.controller;

import com.jetnotifier.notification.api.dto.request.NotificationRequest;
import com.jetnotifier.notification.api.dto.response.NotificationResponse;
import com.jetnotifier.notification.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;




import java.util.Map;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @GetMapping("/notifications")
    public ResponseEntity<Page<NotificationResponse>> getAllNotifications(Pageable pageable) {
        return ResponseEntity.ok(adminService.getAllNotifications(pageable));
    }


    @GetMapping("/notifications/{id}")
    public ResponseEntity<NotificationResponse> getNotificationById(@PathVariable String id) {
        return ResponseEntity.ok(adminService.getNotificationById(id));
    }

    @PostMapping("/notifications/broadcast")
    public ResponseEntity<String> broadcastNotification(@Valid @RequestBody NotificationRequest request) {
        adminService.broadcastNotification(request);
        return ResponseEntity.ok("Broadcast notification initiated");
    }

    @GetMapping("/notifications/stats")
    public ResponseEntity<Map<String, Object>> getNotificationStats() {
        return ResponseEntity.ok(adminService.getNotificationStats());
    }

    @PostMapping("/notifications/{id}/retry")
    public ResponseEntity<String> retryNotification(@PathVariable String id) {
        adminService.retryNotification(id);
        return ResponseEntity.ok("Notification retry initiated");
    }

    @DeleteMapping("/notifications/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable String id) {
        adminService.deleteNotification(id);
        return ResponseEntity.ok("Notification deleted successfully");
    }
}
