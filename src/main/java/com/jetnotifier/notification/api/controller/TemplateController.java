package com.jetnotifier.notification.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jetnotifier.notification.domain.entity.Template;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/jetnotifier/template")
public class TemplateController {
	
	@GetMapping("/all")
	public Template getAllTemplates() {
		return null;
//	ref->	package com.jetnotifier.notification.domain.entity;
//
//		public class Template {
//			// will store template info but GUI creation is separate 
//		}
	}
	
	public Template getTemplate(String templateId) {
		return null;
	}
	
	public void addTemplate(Template template) {
		// add the template in the database after validating & only admin can do this 
	}
	
	public void removeTemplate (Template template ) {
		// it is also can only done by admin only
	}
	
}
