package com.jetnotifier.notification.api.controller;

import com.jetnotifier.notification.domain.entity.Template;
import com.jetnotifier.notification.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/templates")
@CrossOrigin(origins = "*")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    /**
     * 
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<Template>> getAllTemplates(Pageable pageable) {
        Page<Template> templates = templateService.getAllTemplates(pageable);
        return ResponseEntity.ok(templates);
    }

    /**
     * 
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Template> getTemplate(@PathVariable String id) {
        Template template = templateService.getTemplateById(id);
        return ResponseEntity.ok(template);
    }

    /**
     * 
     * @param template
     * @return
     */
    @PostMapping
    public ResponseEntity<Template> createTemplate
    	(@Valid @RequestBody Template template) {
    	
        Template created = templateService.createTemplate(template);
        return ResponseEntity.ok(created);
    }

    /**
     * 
     * @param id
     * @param template
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<Template> updateTemplate
    	(@PathVariable String id, @Valid @RequestBody Template template) {
    	
        Template updated = templateService.updateTemplate(id, template);
        return ResponseEntity.ok(updated);
    }

    /**
     * 
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTemplate(@PathVariable String id) {
        templateService.deleteTemplate(id);
        return ResponseEntity.ok("Template deleted successfully");
    }

    /**
     * 
     * @param type
     * @param pageable
     * @return
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<Page<Template>> getTemplatesByType(@PathVariable String type, Pageable pageable) {
        Page<Template> templates = templateService.getTemplatesByType(type, pageable);
        return ResponseEntity.ok(templates);
    }
    
    
}