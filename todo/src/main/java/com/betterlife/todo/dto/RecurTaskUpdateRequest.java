package com.betterlife.todo.dto;

import com.betterlife.todo.enums.RepeatType;
import com.betterlife.todo.enums.TodoStatus;
import com.betterlife.todo.enums.TodoType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class RecurTaskUpdateRequest {

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
}