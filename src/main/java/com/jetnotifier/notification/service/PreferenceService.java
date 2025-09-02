package com.jetnotifier.notification.service;

import com.jetnotifier.notification.domain.entity.NotificationPreference;
import com.jetnotifier.notification.repository.NotificationPreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.lang.RuntimeException;

@Service
public class PreferenceService {

    @Autowired
    private NotificationPreferenceRepository preferenceRepository;

    public List<NotificationPreference> getUserPreferences(String userId) {
        return preferenceRepository.findByUserId(userId);
    }

    public NotificationPreference createPreference(NotificationPreference preference) {
        
    	preference.setCreatedAt(LocalDateTime.now());
        preference.setUpdatedAt(LocalDateTime.now());
        
        return preferenceRepository.save(preference);
        
    }

    public NotificationPreference updatePreference(String id, NotificationPreference preference) {
        NotificationPreference existingPreference = preferenceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Preference not found"));

        existingPreference.setType(preference.getType());
        existingPreference.setEmailEnabled(preference.getEmailEnabled());
        existingPreference.setSmsEnabled(preference.getSmsEnabled());
        existingPreference.setPushEnabled(preference.getPushEnabled());
        existingPreference.setWebhookEnabled(preference.getWebhookEnabled());
        existingPreference.setQuietHoursStart(preference.getQuietHoursStart());
        existingPreference.setQuietHoursEnd(preference.getQuietHoursEnd());
        existingPreference.setTimezone(preference.getTimezone());
        existingPreference.setFrequencyLimit(preference.getFrequencyLimit());
        existingPreference.setUpdatedAt(LocalDateTime.now());

        return preferenceRepository.save(existingPreference);
    }


    public void deletePreference(String id) {
        preferenceRepository.deleteById(id);
    }

    public boolean isChannelEnabled
    (String userId, String channel, String notificationType) {
    	
        List<NotificationPreference> preferences = getUserPreferences(userId);
        
        for (NotificationPreference pref : preferences) {
            if (pref.getType().name().equals(notificationType)) {
                switch (channel.toLowerCase().trim()) {
                    case "email":
                        return pref.getEmailEnabled();
                    case "sms":
                        return pref.getSmsEnabled();
                    case "push":
                        return pref.getPushEnabled();
                    case "webhook":
                        return pref.getWebhookEnabled();
                    default:
                        return true;
                }
            }
        }
        return true; // Default to enabled if no preference found
    }
}