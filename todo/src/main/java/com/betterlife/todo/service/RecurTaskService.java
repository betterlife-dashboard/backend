package com.betterlife.todo.service;

import com.betterlife.todo.dto.RecurTaskCreateRequest;
import com.betterlife.todo.dto.RecurTaskResponse;
import com.betterlife.todo.repository.RecurTaskRepository;
import com.betterlife.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecurTaskService {

    private final RecurTaskRepository recurTaskRepository;
    private final TodoRepository todoRepository;

    public RecurTaskResponse createRecurTask(Long userId, RecurTaskCreateRequest request) {
//        RecurTaskEntity todo = RecurTaskEntity.builder()
//                .userId(userId)
//                .title(request.getTitle())
//                .todoType(TodoType.TODO)
//                .repeatType(request.getRepeatType())
//                .repeatInterval(request.getRepeatInterval())
//                .weeklyMask(request.getWeeklyMask())
//                .monthlyDay(request.getMonthlyDay())
//                .allDay(request.isAllDay())
//                .atTime(request.getAtTime())
//                .reminderMask(request.getReminderMask())
//                .startDate(request.getStartDate())
//                .endDate(request.getEndDate())
//                .calendar(request.isCalendar())
//                .build();
//        return TodoResponse.fromEntity(saved);
        return null;
    }

    public RecurTaskResponse getRecurTaskById(Long recurTaskId, Long userId) {
        return null;
    }

    //    public List<TodoResponse> getTodosByDate(Long userId, LocalDate date) {
//        LocalDateTime todayStart = date.atStartOfDay();
//        LocalDateTime todayEnd = date.plusDays(1).atStartOfDay().minusSeconds(1);
//        return todoRepository.findAllByUserIdAndActiveFromBeforeAndActiveUntilAfter(userId, todayEnd, todayStart)
//                .stream()
//                .map(TodoResponse::fromEntity)
//                .toList();
//    }
//
//    public List<TodoResponse> getTodosByScheduledAndMonth(Long userId, LocalDate month) {
//        LocalDateTime monthStart = month.atStartOfDay();
//        LocalDateTime monthEnd = month.plusMonths(1).atStartOfDay().minusSeconds(1);
//        return todoRepository.findAllByUserIdAndTypeAndActiveFromBeforeAndActiveUntilAfter(userId, TodoType.SCHEDULE, monthEnd, monthStart)
//                .stream()
//                .map(TodoResponse::fromEntity)
//                .toList();
//    }

//    @Transactional
//    public TodoResponse updateRepeatTodo(Long userId, Long todoId, RepeatTodoUpdateRequest todoRequest) {
//        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 Todo입니다."));
//        if (!todo.getUserId().equals(userId)) {
//            throw new AccessDeniedException("이 Todo에 접근할 권한이 없습니다.");
//        } else if (todoRequest.getRepeatDays() == 0) {
//            throw new InvalidRequestException("반복용 Todo는 반드시 반복되어야 합니다.");
//        }
//
//        todo.changeTitle(todoRequest.getTitle());
//        todo.changeType(todoRequest.getType());
//        todo.changeRepeatDays(todoRequest.getRepeatDays());
//
//        return TodoResponse.fromEntity(todo);
//    }

//    public List<TodoResponse> getRecurTodos(Long userId) {
//        return todoRepository.findAllByUserIdAndIsRecurring(userId, true)
//                .stream()
//                .map(TodoResponse::fromEntity)
//                .toList();
//    }
//
//    public Boolean checkRepeatDate(Integer weeks) {
//        LocalDate today = LocalDate.now();
//        DayOfWeek dayOfWeek = today.getDayOfWeek();
//        int now = 1 << (dayOfWeek.getValue() - 1);
//        return (weeks & now) != 0;
//    }
//
//    @Transactional
//    public void generateRecurringTodos() {
//        List<Todo> todos = todoRepository.findAllByIsRecurring(true);
//        for (Todo todo : todos) {
//            if (checkRepeatDate(todo.getRepeatDays())) {
//                Todo child = Todo.builder()
//                        .userId(todo.getUserId())
//                        .title(todo.getTitle())
//                        .type(todo.getType())
//                        .status(TodoStatus.PLANNED)
//                        .repeatDays(0)
//                        .activeFrom(LocalDate.now().atStartOfDay())
//                        .activeUntil(LocalDate.now().plusDays(1).atStartOfDay().minusSeconds(1))
//                        .build();
//                todo.addChildTodo(child);
//            }
//        }
//    }
//
//    @Transactional
//    public void closePastTodos() {
//        LocalDateTime currentTime = LocalDateTime.now();
//        List<Todo> todos = todoRepository.findAllByStatusAndActiveUntilBefore(TodoStatus.PLANNED, currentTime);
//        todos.forEach(todo -> {
//                todo.updateStatus(TodoStatus.EXPIRED);
//        });
//    }
}
