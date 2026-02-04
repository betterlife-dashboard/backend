package com.betterlife.todo.event;

import com.betterlife.todo.domain.TodoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EventProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.todo-updated.exchange}")
    private String todoUpdatedExchangeName;

    @Value("${rabbitmq.todo-updated.key}")
    private String todoUpdatedKey;

    @Value("${rabbitmq.todo-deleted.exchange}")
    private String todoDeletedExchangeName;

    @Value("${rabbitmq.todo-deleted.key}")
    private String todoDeletedKey;

    @Value("${rabbitmq.todo-created.exchange}")
    private String todoCreatedExchangeName;

    @Value("${rabbitmq.todo-created.key}")
    private String todoCreatedKey;

    public void sendTodoCreatedEvent(TodoEntity todo) {
        TodoCreatedEvent todoCreatedEvent = TodoCreatedEvent.builder()
                .id(todo.getId())
                .userId(todo.getUserId())
                .title(todo.getTitle())
                .allDay(todo.isAllDay())
                .occurrenceDate(todo.getOccurrenceDate())
                .atTime(todo.getAtTime())
                .reminderMask(todo.getReminderMask())
                .build();

        CorrelationData cd = new CorrelationData("todoCreated:" + todoCreatedEvent.getId() + ":" + UUID.randomUUID());

        rabbitTemplate.convertAndSend(
                todoCreatedExchangeName,
                todoCreatedKey,
                todoCreatedEvent,
                message -> {
                    message.getMessageProperties().setMessageId(cd.getId());
                    return message;
                },
                cd
        );
    }

    public void sendTodoUpdatedEvent(TodoEntity todo) {
        TodoUpdatedEvent todoUpdatedEvent = TodoUpdatedEvent.builder()
                .id(todo.getId())
                .userId(todo.getUserId())
                .title(todo.getTitle())
                .allDay(todo.isAllDay())
                .occurrenceDate(todo.getOccurrenceDate())
                .atTime(todo.getAtTime())
                .reminderMask(todo.getReminderMask())
                .build();

        CorrelationData cd = new CorrelationData("userUpdated:" + todoUpdatedEvent.getId() + ":" + UUID.randomUUID());

        rabbitTemplate.convertAndSend(
                todoUpdatedExchangeName,
                todoUpdatedKey,
                todoUpdatedEvent,
                message -> {
                    message.getMessageProperties().setMessageId(cd.getId());
                    return message;
                },
                cd
        );
    }

    public void sendTodoDeletedEvent(Long todoId) {
        TodoDeletedEvent todoDeletedEvent = new TodoDeletedEvent(todoId);

        CorrelationData cd = new CorrelationData("userDeleted:" + todoId + ":" + UUID.randomUUID());

        rabbitTemplate.convertAndSend(
                todoDeletedExchangeName,
                todoDeletedKey,
                todoDeletedEvent,
                message -> {
                    message.getMessageProperties().setMessageId(cd.getId());
                    return message;
                },
                cd
        );
    }
}
