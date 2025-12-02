package com.betterlife.notify.repository;

import com.betterlife.notify.domain.NotificationChannel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationChannelRepository extends JpaRepository<NotificationChannel, Long> {
}
