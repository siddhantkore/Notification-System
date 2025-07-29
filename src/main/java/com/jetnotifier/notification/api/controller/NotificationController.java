package com.jetnotifier.notification.api.controller;

import com.jetnotifier.notification.api.dto.request.NotificationRequest;
import com.jetnotifier.notification.api.dto.response.NotificationResponse;
import com.jetnotifier.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     * 
     * @param request  NotificationRequest
     * @return
     * @route /api/notifications/
     */
    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(@Valid @RequestBody NotificationRequest request) {

        NotificationResponse response = notificationService.createNotification(request);
        return ResponseEntity.ok(response);
    
    }

    /**
     * 
     * @param id
     * @return
     * @route api/notifications/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getNotification(@PathVariable String id) {

        NotificationResponse response = notificationService.getNotificationById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * 
     * @param userId
     * @return
     * @route /api/notifications/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponse>> getUserNotifications(@PathVariable String userId) {
        List<NotificationResponse> notifications = notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    /**
     * 
     * @param pageable
     * @return
     * @route /api/notifications/
     */
    @GetMapping
    public ResponseEntity<Page<NotificationResponse>> getAllNotifications(Pageable pageable) {
        Page<NotificationResponse> notifications = notificationService.getAllNotifications(pageable);
        return ResponseEntity.ok(notifications);
    }

    /**
     * 
     * @param id
     * @param status
     * @return
     * @route /api/notifications/{id}/status
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateNotificationStatus (
            @PathVariable String id, 
            @RequestParam String status) {

        notificationService.updateNotificationStatus(id, status);
        return ResponseEntity.ok("Notification status updated");
    }

    /**
     * 
     * @param id
     * @return
     * @route /api/notifications/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable String id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok("Notification deleted successfully");
    }
}