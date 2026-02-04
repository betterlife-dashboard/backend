package com.betterlife.todo.event;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TodoCreatedEvent {
    private Long id;
    private Long userId;
    private String title;
    private boolean allDay;
    private LocalDate occurrenceDate;
    private LocalTime atTime;
    private Byte reminderMask;

    @Builder
    public TodoCreatedEvent(Long id, Long userId, String title, boolean allDay, LocalDate occurrenceDate, LocalTime atTime, Byte reminderMask) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.allDay = allDay;
        this.occurrenceDate = occurrenceDate;
        this.atTime = atTime;
        this.reminderMask = reminderMask;
    }
}
