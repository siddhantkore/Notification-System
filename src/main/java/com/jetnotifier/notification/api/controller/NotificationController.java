package com.jetnotifier.notification.api.controller;

import com.jetnotifier.notification.api.dto.request.NotificationRequest;
import com.jetnotifier.notification.api.dto.response.NotificationResponse;
import com.jetnotifier.notification.service.NotificationService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired private NotificationService notificationService;

    /**
     * @param request NotificationRequest
     * @return same notification after creation (receive)
     * @route /api/notifications/
     */
    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(
            @Valid @RequestBody NotificationRequest request) {

        NotificationResponse response = notificationService.createNotification(request);
        return ResponseEntity.ok(response);
    }

    /**
     * @param id
     * @return return specified notification
     * @route api/notifications/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getNotification(@PathVariable String id) {

        NotificationResponse response = notificationService.getNotificationById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * @param userId to find his notifications
     * @return Notifications of a user
     * @route /api/notifications/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponse>> getUserNotifications(
            @PathVariable String userId) {
        List<NotificationResponse> notifications =
                notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    /**
     * @param pageable for pagination
     * @return return notifications in paging
     * @route /api/notifications/
     */
    @GetMapping
    public ResponseEntity<Page<NotificationResponse>> getAllNotifications(Pageable pageable) {
        Page<NotificationResponse> notifications =
                notificationService.getAllNotifications(pageable);
        return ResponseEntity.ok(notifications);
    }

    /**
     * @param id for which we want to change
     * @param status new status of notification
     * @return update the status of notification by setting it manually
     * @route /api/notifications/{id}/status
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateNotificationStatus(
            @PathVariable String id, @RequestParam String status) {

        notificationService.updateNotificationStatus(id, status);
        return ResponseEntity.ok("Notification status updated");
    }

    /**
     * @param id to delete notification
     * @return Delete and return ok
     * @route /api/notifications/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable String id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok("Notification deleted successfully");
    }
}
