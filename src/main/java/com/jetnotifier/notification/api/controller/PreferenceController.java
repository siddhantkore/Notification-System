package com.jetnotifier.notification.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jetnotifier.notification.domain.entity.NotificationPreference;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/jetnotifier/preference")
public class PreferenceController {

   @GetMapping("/user/{userid}")
   public NotificationPreference getUserPreference(@PathVariable String userid) {
       // fetch from DB
       return null;
   }

   @PutMapping("/user/{userid}")
   public void updateUserPreference(@PathVariable String userid, @RequestBody NotificationPreference pref) {
       // save to DB
   }

   @GetMapping("/client/{clientid}")
   public NotificationPreference getClientPreference(@PathVariable String clientid) {
       return null;
   }

   @PutMapping("/client/{clientid}")
   public void updateClientPreference(@PathVariable String clientid, @RequestBody NotificationPreference pref) {
       // save to DB
   }

}

