package com.betterlife.todo.service;

import com.betterlife.todo.domain.RecurTaskEntity;
import com.betterlife.todo.dto.RecurTaskCreateRequest;
import com.betterlife.todo.dto.RecurTaskResponse;
import com.betterlife.todo.dto.RecurTaskUpdateRequest;
import com.betterlife.todo.enums.RepeatType;
import com.betterlife.todo.exception.AccessDeniedException;
import com.betterlife.todo.exception.TodoNotFoundException;
import com.betterlife.todo.repository.RecurTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class RecurTaskService {

    private final RecurTaskRepository recurTaskRepository;
    private final TodoService todoService;

    public RecurTaskResponse createRecurTask(Long userId, RecurTaskCreateRequest request) {
        RecurTaskEntity recurTask = RecurTaskEntity.builder()
                .userId(userId)
                .title(request.getTitle())
                .allDay(request.isAllDay())
                .repeatType(request.getRepeatType())
                .repeatInterval(request.getRepeatInterval())
                .weeklyMask(request.getWeeklyMask())
                .monthlyDay(request.getMonthlyDay())
                .atTime(request.getAtTime())
                .reminderMask(request.getReminderMask())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .calendar(request.isCalendar())
                .build();
        RecurTaskEntity saved = recurTaskRepository.save(recurTask);
        todoService.planTodoByRecurTask(saved, checkPlanDate(saved));
        return RecurTaskResponse.fromEntity(saved);
    }

    public RecurTaskResponse getRecurTaskById(Long recurTaskId, Long userId) {
        RecurTaskEntity recurTask = recurTaskRepository.findById(recurTaskId)
                .orElseThrow(() -> new TodoNotFoundException("존재하지 않는 반복 일정입니다."));
        if (!recurTask.getUserId().equals(userId)) {
            throw new AccessDeniedException("이 일정에 접근할 권한이 없습니다.");
        }
        return RecurTaskResponse.fromEntity(recurTask);
    }

    @Transactional
    public void deleteRecurTask(Long recurTaskId, Long userId) {
        RecurTaskEntity recurTask = recurTaskRepository.findById(recurTaskId)
                .orElseThrow(() -> new TodoNotFoundException("존재하지 않는 반복 일정입니다."));
        if (!recurTask.getUserId().equals(userId)) {
            throw new AccessDeniedException("이 일정에 접근할 권한이 없습니다.");
        }
        todoService.deleteTodoByRecurTask(recurTaskId);
        recurTaskRepository.deleteById(recurTaskId);
    }

    @Transactional
    public RecurTaskResponse updateRecurTask(Long recurTaskId, Long userId, RecurTaskUpdateRequest request) {
        RecurTaskEntity recurTask = recurTaskRepository.findById(recurTaskId)
                .orElseThrow(() -> new TodoNotFoundException("존재하지 않는 반복 일정입니다."));
        if (!recurTask.getUserId().equals(userId)) {
            throw new AccessDeniedException("이 일정에 접근할 권한이 없습니다.");
        }
        recurTask.update(request);
        todoService.deleteTodoByRecurTask(recurTaskId);
        todoService.planTodoByRecurTask(recurTask, checkPlanDate(recurTask));
        return RecurTaskResponse.fromEntity(recurTask);
    }

    private ArrayList<LocalDate> checkPlanDate(RecurTaskEntity recurTask) {
        ArrayList<LocalDate> planDates = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate startDate = recurTask.getStartDate();
        LocalDate endDate = recurTask.getEndDate();
        if (endDate == null) { endDate = LocalDate.MAX; }
        if (RepeatType.DAILY.equals(recurTask.getRepeatType())) {
            for (int i = 0; i <= 10; i++) {
                LocalDate plan = today.plusDays(i);
                long between = ChronoUnit.DAYS.between(startDate, plan);
                if (!plan.isBefore(startDate) && !plan.isAfter(endDate)
                    && between % recurTask.getRepeatInterval() == 0) {
                    planDates.add(plan);
                }
            }
        } else if (RepeatType.WEEKLY.equals(recurTask.getRepeatType())) {
            for (int i = 0; i <= 10; i++) {
                LocalDate plan = today.plusDays(i);
                int dayValue = plan.getDayOfWeek().getValue();
                int bit = 1 << (dayValue - 1);
                long between = ChronoUnit.WEEKS.between(startDate, plan);
                if ((bit & recurTask.getWeeklyMask()) != 0
                        && !plan.isBefore(startDate) && !plan.isAfter(endDate)
                        && between % recurTask.getRepeatInterval() == 0) {
                    planDates.add(plan);
                }
            }
        } else {
            for (int i = 0; i <= 10; i++) {
                LocalDate plan = today.plusDays(i);
                int dayValue = plan.getDayOfMonth();
                long between = ChronoUnit.MONTHS.between(startDate, plan);
                if (dayValue == recurTask.getMonthlyDay()
                        && !plan.isBefore(startDate) && !plan.isAfter(endDate)
                        && between % recurTask.getRepeatInterval() == 0) {
                    planDates.add(plan);
                }
            }
        }
        return planDates;
    }

}
