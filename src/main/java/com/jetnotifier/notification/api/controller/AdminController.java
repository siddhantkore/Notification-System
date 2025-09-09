package com.jetnotifier.notification.api.controller;

import com.jetnotifier.notification.api.dto.request.NotificationRequest;
import com.jetnotifier.notification.api.dto.response.NotificationResponse;
import com.jetnotifier.notification.service.AdminService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author siddhant kore
 */
@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired private AdminService adminService;

    /**
     * @param pageable for pagination
     * @return return saved notifications from database
     * {@code @route} /api/admin/notifications
     */
    @GetMapping("/notifications")
    public ResponseEntity<Page<NotificationResponse>> getAllNotifications(Pageable pageable) {
        return ResponseEntity.ok(adminService.getAllNotifications(pageable));
    }

    /**
     * @param id for which we are getting Notification
     * @return Notification of specified id
     * {@code @route} /api/admin/notifications/{id}
     */
    @GetMapping("/notifications/{id}")
    public ResponseEntity<NotificationResponse> getNotificationById(@PathVariable String id) {
        return ResponseEntity.ok(adminService.getNotificationById(id));
    }

    /**
     * @param request Notification entity
     * @return ok
     * {@code @route} /api/admin/notifications/broadcast
     */
    @PostMapping("/notifications/broadcast")
    public ResponseEntity<String> broadcastNotification(
            @Valid @RequestBody NotificationRequest request) {
        adminService.broadcastNotification(request);
        return ResponseEntity.ok("Broadcast notification initiated");
    }

    /**
     * @implNote Not Ready yet
     * @return Notification statistics
     * {@code @route} /api/admin/notifications/stats
     */
    @GetMapping("/notifications/stats")
    public ResponseEntity<Map<String, Object>> getNotificationStats() {
        return ResponseEntity.ok(adminService.getNotificationStats());
    }

    /**
     * @param id
     * @return
     * @route /api/admin/notifications/{id}/retry
     */
    @PostMapping("/notifications/{id}/retry")
    public ResponseEntity<String> retryNotification(@PathVariable String id) {
        adminService.retryNotification(id);
        return ResponseEntity.ok("Notification retry initiated");
    }

    /**
     * @param id
     * @return
     * @route /api/admin/notifications/{id}
     */
    @DeleteMapping("/notifications/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable String id) {
        adminService.deleteNotification(id);
        return ResponseEntity.ok("Notification deleted successfully");
    }
}
