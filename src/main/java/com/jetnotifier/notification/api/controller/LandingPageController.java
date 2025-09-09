package com.jetnotifier.notification.api.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LandingPageController {

    @GetMapping("/")
    public ResponseEntity<String> serveLandingPage(Model model) throws IOException {

        ClassPathResource htmlFile = new ClassPathResource("static/index.html");
        String htmlContent =
                StreamUtils.copyToString(htmlFile.getInputStream(), StandardCharsets.UTF_8);

        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(htmlContent);

        // can edit and configure and replace;
        //		return "index"; require Theamlife dependency
    }
}
