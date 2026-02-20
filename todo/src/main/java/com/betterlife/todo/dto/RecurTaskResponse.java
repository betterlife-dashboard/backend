package com.betterlife.todo.dto;

import com.betterlife.todo.domain.RecurTaskEntity;
import com.betterlife.todo.enums.RepeatType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RecurTaskResponse {
    private Long id;
    private Long userId;
    private String title;
    private boolean allDay;
    private RepeatType repeatType;
    @Schema(type = "integer")
    private Byte repeatInterval;
    @Schema(type = "integer")
    private Byte weeklyMask;
    @Schema(type = "integer")
    private Byte monthlyDay;
    @Schema(type = "string", format = "time", example = "00:00:00", nullable = true)
    private LocalTime atTime;
    @Schema(type = "integer")
    private Byte reminderMask;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean calendar;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static RecurTaskResponse fromEntity(RecurTaskEntity entity) {
        RecurTaskResponse dto = new RecurTaskResponse();
        dto.id = entity.getId();
        dto.userId = entity.getUserId();
        dto.title = entity.getTitle();
        dto.allDay = entity.isAllDay();
        dto.repeatType = entity.getRepeatType();
        dto.repeatInterval = entity.getRepeatInterval();
        dto.weeklyMask = entity.getWeeklyMask();
        dto.monthlyDay = entity.getMonthlyDay();
        dto.atTime = entity.getAtTime();
        dto.reminderMask = entity.getReminderMask();
        dto.startDate = entity.getStartDate();
        dto.endDate = entity.getEndDate();
        dto.calendar = entity.isCalendar();
        return dto;
    }
}
