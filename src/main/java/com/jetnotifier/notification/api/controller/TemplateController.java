package com.jetnotifier.notification.api.controller;

import com.jetnotifier.notification.domain.entity.Template;
import com.jetnotifier.notification.service.TemplateService;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/templates")
@CrossOrigin(origins = "*")
public class TemplateController {

    @Autowired private TemplateService templateService;

    /**
     * @param pageable for pagination
     * @return return templates from DB
     */
    @GetMapping
    public ResponseEntity<Page<Template>> getAllTemplates(Pageable pageable) {
        Page<Template> templates = templateService.getAllTemplates(pageable);
        return ResponseEntity.ok(templates);
    }

    /**
     * @param id of template to receive it form database
     * @return return the template of specified id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Template> getTemplate(@PathVariable String id) {
        Template template = templateService.getTemplateById(id);
        return ResponseEntity.ok(template);
    }

    /**
     * @param template to insert in database
     * @return send created template response
     */
    @PostMapping
    public ResponseEntity<Template> createTemplate(@Valid @RequestBody Template template) {

        Template created = templateService.createTemplate(template);
        return ResponseEntity.ok(created);
    }

    /**
     * @param id of old template and new template itself
     * @return will update existing templates by searching it by its id
     */
    @PutMapping("/{id}")
    public ResponseEntity<Template> updateTemplate(
            @PathVariable String id, @Valid @RequestBody Template template) {

        Template updated = templateService.updateTemplate(id, template);
        return ResponseEntity.ok(updated);
    }

    /**
     * @param id of Template
     * @return will delete the template of specified id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTemplate(@PathVariable String id) {
        templateService.deleteTemplate(id);
        return ResponseEntity.ok("Template deleted successfully");
    }

    /**
     * @param type of template i.e email, sms
     * @return templates
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<Page<Template>> getTemplatesByType(
            @PathVariable String type, Pageable pageable) {
        Page<Template> templates = templateService.getTemplatesByType(type, pageable);
        return ResponseEntity.ok(templates);
    }
}
