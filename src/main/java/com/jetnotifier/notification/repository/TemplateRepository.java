package com.jetnotifier.notification.repository;


import com.jetnotifier.notification.domain.entity.Template;
import com.jetnotifier.notification.domain.enums.NotificationType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends MongoRepository<Template, String> {

	Page<Template> findByType(NotificationType notificationType, Pageable pageable);

}
