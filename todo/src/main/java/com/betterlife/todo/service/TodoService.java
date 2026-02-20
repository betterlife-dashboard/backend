package com.betterlife.todo.service;

import com.betterlife.todo.domain.RecurTaskEntity;
import com.betterlife.todo.domain.TodoEntity;
import com.betterlife.todo.dto.*;
import com.betterlife.todo.enums.TodoStatus;
import com.betterlife.todo.enums.TodoType;
import com.betterlife.todo.event.TodoCreatedEvent;
import com.betterlife.todo.event.TodoDeletedEvent;
import com.betterlife.todo.event.TodoUpdatedEvent;
import com.betterlife.todo.exception.AccessDeniedException;
import com.betterlife.todo.exception.TodoNotFoundException;
import com.betterlife.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public TodoResponse getTodoById(Long userId, Long todoId) {
        TodoEntity todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new TodoNotFoundException("존재하지 않는 Todo입니다."));
        if (!todo.getUserId().equals(userId)) {
            throw new AccessDeniedException("이 Todo에 접근할 권한이 없습니다.");
        }
        return TodoResponse.fromEntity(todo);
    }

    @Transactional
    public TodoResponse createTodo(Long userId, TodoCreateRequest todoCreateRequest) {
        TodoEntity todo = TodoEntity.builder()
                .userId(userId)
                .recurTask(null)
                .todoType(TodoType.TODO)
                .todoStatus(TodoStatus.PENDING)
                .title(todoCreateRequest.getTitle())
                .memo(todoCreateRequest.getMemo())
                .allDay(todoCreateRequest.isAllDay())
                .occurrenceDate(todoCreateRequest.getOccurrenceDate())
                .atTime(todoCreateRequest.getAtTime())
                .completedAt(null)
                .durationSec(null)
                .reminderMask(todoCreateRequest.getReminderMask())
                .calendar(todoCreateRequest.isCalendar())
                .build();
        TodoEntity saved = todoRepository.save(todo);
        applicationEventPublisher.publishEvent(TodoCreatedEvent.builder()
                        .id(todo.getId())
                        .userId(todo.getUserId())
                        .title(todo.getTitle())
                        .allDay(todo.isAllDay())
                        .occurrenceDate(todo.getOccurrenceDate())
                        .atTime(todo.getAtTime())
                        .reminderMask(todo.getReminderMask())
                        .build());
        return TodoResponse.fromEntity(saved);
    }

    @Transactional
    public void deleteTodo(Long userId, Long todoId) {
        TodoEntity todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new TodoNotFoundException("존재하지 않는 Todo입니다."));
        if (!todo.getUserId().equals(userId)) {
            throw new AccessDeniedException("이 Todo에 접근할 권한이 없습니다.");
        }
        applicationEventPublisher.publishEvent(new TodoDeletedEvent(todoId));
        todoRepository.deleteById(todoId);
    }

    @Transactional
    public void deleteTodoByRecurTask(Long recurTaskId) {
        List<TodoEntity> todos = todoRepository.findAllByRecurTaskIdAndOccurrenceDateGreaterThanEqual(recurTaskId, LocalDate.now());
        for (TodoEntity todo : todos) {
            applicationEventPublisher.publishEvent(new TodoDeletedEvent(todo.getId()));
            todoRepository.deleteById(todo.getId());
        }
    }

    @Transactional
    public TodoResponse updateTodo(Long userId, Long todoId, TodoUpdateRequest todoRequest) {
        TodoEntity todo = todoRepository.findById(todoId).orElseThrow(() -> new TodoNotFoundException("존재하지 않는 Todo입니다."));
        if (!todo.getUserId().equals(userId)) {
            throw new AccessDeniedException("이 Todo에 접근할 권한이 없습니다.");
        }
        todo.update(todoRequest);
        applicationEventPublisher.publishEvent(TodoUpdatedEvent.builder()
                        .id(todo.getId())
                        .userId(todo.getUserId())
                        .title(todo.getTitle())
                        .allDay(todo.isAllDay())
                        .occurrenceDate(todo.getOccurrenceDate())
                        .atTime(todo.getAtTime())
                        .reminderMask(todo.getReminderMask())
                        .build());
        return TodoResponse.fromEntity(todo);
    }

    @Transactional
    public void planTodoByRecurTask(RecurTaskEntity recurTask, ArrayList<LocalDate> planDates) {
        for (LocalDate planDate : planDates) {
            TodoEntity todo = TodoEntity.builder()
                    .userId(recurTask.getUserId())
                    .recurTask(recurTask)
                    .todoType(TodoType.TODO)
                    .todoStatus(TodoStatus.PENDING)
                    .title(recurTask.getTitle())
                    .memo(null)
                    .allDay(recurTask.isAllDay())
                    .occurrenceDate(planDate) // 변경해야 함
                    .atTime(recurTask.getAtTime())
                    .completedAt(null)
                    .durationSec(null)
                    .reminderMask(recurTask.getReminderMask())
                    .calendar(recurTask.isCalendar())
                    .build();
            TodoEntity saved = todoRepository.save(todo);
            applicationEventPublisher.publishEvent(TodoCreatedEvent.builder()
                    .id(saved.getId())
                    .userId(saved.getUserId())
                    .title(saved.getTitle())
                    .allDay(saved.isAllDay())
                    .occurrenceDate(saved.getOccurrenceDate())
                    .atTime(saved.getAtTime())
                    .reminderMask(saved.getReminderMask())
                    .build());
        }
    }

    @Transactional
    public void deleteUser(Long userId) {
        todoRepository.deleteAllByUserId(userId);
    }

}