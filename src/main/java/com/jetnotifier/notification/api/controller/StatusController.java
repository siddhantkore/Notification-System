package com.jetnotifier.notification.api.controller;

import com.jetnotifier.notification.service.StatusService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    @Autowired private StatusService statusService;

    /**
     * @param id of notification of which we want to get status
     * @return status such as SENT, FAILED
     */
    @GetMapping("/notification/{id}")
    public ResponseEntity<Map<String, Object>> getNotificationStatus(@PathVariable String id) {
        Map<String, Object> status = statusService.getNotificationStatus(id);
        return ResponseEntity.ok(status);
    }

    /**
     * @param userId for which to find stats
     * @return stats about the user notification
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getUserNotificationStats(
            @PathVariable String userId) {

        Map<String, Object> stats = statusService.getUserNotificationStats(userId);

        return ResponseEntity.ok(stats);
    }

    /**
     * @return get system info
     */
    @GetMapping("/system")
    public ResponseEntity<Map<String, Object>> getSystemStatus() {
        Map<String, Object> systemStatus = statusService.getSystemStatus();
        return ResponseEntity.ok(systemStatus);
    }
}
