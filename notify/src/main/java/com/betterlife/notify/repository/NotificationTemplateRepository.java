package com.betterlife.notify.repository;

import com.betterlife.notify.domain.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long> {
}
