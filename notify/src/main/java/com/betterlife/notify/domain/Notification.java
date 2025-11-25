package com.betterlife.notify.domain;

import com.betterlife.notify.enums.EventType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.Map;

@Table(name = "notifications")
@Entity
@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private Map<String, Object> data;

    @Column(nullable = false)
    private Boolean isRead = false;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Timestamp updatedAt;

    @Builder
    public Notification(
            Long id,
            Long userId,
            EventType eventType,
            String title,
            String body,
            Map<String, Object> data,
            Boolean isRead
    ) {
        this.id = id;
        this.userId = userId;
        this.eventType = eventType;
        this.title = title;
        this.body = body;
        this.data = data;
        this.isRead = isRead;
    }
}
