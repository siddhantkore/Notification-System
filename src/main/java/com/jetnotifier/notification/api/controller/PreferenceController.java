package com.jetnotifier.notification.api.controller;

import com.jetnotifier.notification.domain.entity.NotificationPreference;
import com.jetnotifier.notification.service.PreferenceService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/preferences")
@CrossOrigin(origins = "*")
public class PreferenceController {

    @Autowired private PreferenceService preferenceService;

    /**
     * @param userId
     * @return get preference of user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationPreference>> getUserPreferences(
            @PathVariable String userId) {
        List<NotificationPreference> preferences = preferenceService.getUserPreferences(userId);
        return ResponseEntity.ok(preferences);
    }

    /**
     * @param preference
     * @return pass
     */
    @PostMapping
    public ResponseEntity<NotificationPreference> createPreference(
            @Valid @RequestBody NotificationPreference preference) {
        NotificationPreference created = preferenceService.createPreference(preference);
        return ResponseEntity.ok(created);
    }

    /**
     * @param id
     * @param preference
     * @return pass
     */
    @PutMapping("/{id}")
    public ResponseEntity<NotificationPreference> updatePreference(
            @PathVariable String id, @Valid @RequestBody NotificationPreference preference) {
        NotificationPreference updated = preferenceService.updatePreference(id, preference);
        return ResponseEntity.ok(updated);
    }

    /**
     * @param id
     * @return pass
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePreference(@PathVariable String id) {
        preferenceService.deletePreference(id);
        return ResponseEntity.ok("Preference deleted successfully");
    }
}
