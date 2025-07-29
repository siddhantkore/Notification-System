package com.jetnotifier.notification.api.controller;

import com.jetnotifier.notification.domain.entity.NotificationPreference;
import com.jetnotifier.notification.service.PreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/preferences")
@CrossOrigin(origins = "*")
public class PreferenceController {

    @Autowired
    private PreferenceService preferenceService;

    /**
     * 
     * 
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationPreference>> getUserPreferences(@PathVariable String userId) {
        List<NotificationPreference> preferences = preferenceService.getUserPreferences(userId);
        return ResponseEntity.ok(preferences);
    }

    /**
     * 
     * @param preference
     * @return
     */
    @PostMapping
    public ResponseEntity<NotificationPreference> createPreference(@Valid @RequestBody NotificationPreference preference) {
        NotificationPreference created = preferenceService.createPreference(preference);
        return ResponseEntity.ok(created);
    }

    /**
     * 
     * @param id
     * @param preference
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<NotificationPreference> updatePreference(
            @PathVariable String id, 
            @Valid @RequestBody NotificationPreference preference) {
        NotificationPreference updated = preferenceService.updatePreference(id, preference);
        return ResponseEntity.ok(updated);
    }

    /**
     * 
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePreference(@PathVariable String id) {
        preferenceService.deletePreference(id);
        return ResponseEntity.ok("Preference deleted successfully");
    }
}