package com.betterlife.notify.domain;

import com.betterlife.notify.enums.ChannelType;
import com.betterlife.notify.enums.EventType;
import com.betterlife.notify.enums.Lang;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Table(name = "notification_templates")
@Entity
@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class NotificationTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column(columnDefinition = "VARCHAR(20)")
    @Enumerated(EnumType.STRING)
    private ChannelType channelType;

    @Column(columnDefinition = "VARCHAR(10)")
    @Enumerated(EnumType.STRING)
    private Lang lang;

    @Column(nullable = false)
    private String titleTemplate;

    @Column(nullable = false)
    private String bodyTemplate;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Builder
    public NotificationTemplate(
            Long id,
            EventType eventType,
            ChannelType channelType,
            Lang lang,
            String titleTemplate,
            String bodyTemplate
        ) {
        this.id = id;
        this.eventType = eventType;
        this.channelType = channelType;
        this.lang = lang;
        this.titleTemplate = titleTemplate;
        this.bodyTemplate = bodyTemplate;
    }
}
