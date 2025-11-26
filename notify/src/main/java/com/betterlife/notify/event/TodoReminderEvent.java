package com.betterlife.notify.event;

import lombok.Data;

@Data
public class TodoReminderEvent {
    private String eventType;
    private Long todoId;
    private Long userId;
    private String title;
    private String deadline;
}
