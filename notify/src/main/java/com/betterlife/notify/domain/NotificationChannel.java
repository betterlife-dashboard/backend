package com.betterlife.notify.domain;

import com.betterlife.notify.enums.ChannelType;
import com.betterlife.notify.enums.SendStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Table(name = "notification_channels")
@Entity
@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class NotificationChannel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "notification_id")
    private Notification notification;

    @Column(columnDefinition = "VARCHAR(20)")
    @Enumerated(value = EnumType.STRING)
    private ChannelType channelType;

    @Column(columnDefinition = "VARCHAR(20)")
    @Enumerated(value = EnumType.STRING)
    private SendStatus sendStatus = SendStatus.PENDING;

    @Column(nullable = false, name = "send_at")
    private Timestamp sendAt;

    private String errorMessage;

    @Builder
    public NotificationChannel(
            Long id,
            Notification notification,
            ChannelType channelType,
            SendStatus sendStatus,
            Timestamp sendAt,
            String errorMessage
    ) {
        this.id = id;
        this.notification = notification;
        this.channelType = channelType;
        this.sendStatus = sendStatus;
        this.sendAt = sendAt;
        this.errorMessage = errorMessage;
    }
}
