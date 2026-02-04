package com.betterlife.todo.dto;

import com.betterlife.todo.enums.RepeatType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RecurTaskCreateRequest {
    @Schema(example = "Test To-Do")
    private String title;
    @Schema(example = "true")
    private boolean allDay;
    @Schema(example = "DAILY")
    private RepeatType repeatType;
    @Schema(type = "integer", format = "int32", example = "1")
    private Byte repeatInterval;
    @Schema(type = "integer", format = "int32", example = "0")
    private Byte weeklyMask;
    @Schema(type = "integer", format = "int32", example = "0")
    private Byte monthlyDay;
    @Schema(type = "string", format = "time", example = "00:00:00", nullable = true)
    private LocalTime atTime;
    @Schema(type = "integer", format = "int32", example = "3")
    private Byte reminderMask;
    @Schema(example = "2026-01-15")
    private LocalDate startDate;
    @Schema(example = "2026-01-20")
    private LocalDate endDate;
    @Schema(example = "true")
    private boolean calendar;

    @Builder
    public RecurTaskCreateRequest(
            String title,
            boolean allDay,
            RepeatType repeatType,
            Byte repeatInterval,
            Byte weeklyMask,
            Byte monthlyDay,
            LocalTime atTime,
            Byte reminderMask,
            LocalDate startDate,
            LocalDate endDate,
            boolean calendar) {
        this.title = title;
        this.allDay = allDay;
        this.repeatType = repeatType;
        this.repeatInterval = repeatInterval;
        this.weeklyMask = weeklyMask;
        this.monthlyDay = monthlyDay;
        this.atTime = atTime;
        this.reminderMask = reminderMask;
        this.startDate = startDate;
        this.endDate = endDate;
        this.calendar = calendar;
    }
}
