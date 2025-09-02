package com.jetnotifier.notification.service;

import com.jetnotifier.notification.domain.entity.Template;
import com.jetnotifier.notification.domain.enums.NotificationType;
import com.jetnotifier.notification.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TemplateService {

    @Autowired
    private TemplateRepository templateRepository;

    public Page<Template> getAllTemplates(Pageable pageable) {
        return templateRepository.findAll(pageable);
    }

    public Template getTemplateById(String id) {
        return templateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template not found"));
    }

    public Template createTemplate(Template template) {
        template.setCreatedAt(LocalDateTime.now());
        template.setUpdatedAt(LocalDateTime.now());
        
        return templateRepository.save(template);
    }

    public Template updateTemplate(String id, Template template) {
        Template existingTemplate = getTemplateById(id);
        
        existingTemplate.setName(template.getName());
        existingTemplate.setSubject(template.getSubject());
        existingTemplate.setBody(template.getBody());
        existingTemplate.setType(template.getType());
        existingTemplate.setVariables(template.getVariables());
        existingTemplate.setIsActive(template.getIsActive());
        existingTemplate.setUpdatedAt(LocalDateTime.now());
        
        return templateRepository.save(existingTemplate);
    }

    public void deleteTemplate(String id) {
        templateRepository.deleteById(id);
    }

    public Page<Template> getTemplatesByType(String type, Pageable pageable) {
    	
        NotificationType notificationType = NotificationType.valueOf(type.toUpperCase());
        return templateRepository.findByType(notificationType, pageable);
        
    }

    public String processTemplate(String templateId, Map<String, Object> variables) {
    	
        Template template = getTemplateById(templateId);
        String content = template.getBody();
        
        if (variables != null) {
        	
            for (Map.Entry<String, Object> entry : variables.entrySet()) {
                String placeholder = "{{" + entry.getKey() + "}}";
                content = content.replace(placeholder, String.valueOf(entry.getValue()));
            }
        }
        
        return content;
    }
}