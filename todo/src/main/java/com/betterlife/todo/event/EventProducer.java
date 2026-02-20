package com.betterlife.todo.event;

import com.betterlife.todo.domain.TodoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

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

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendTodoCreatedEvent(TodoCreatedEvent todoCreatedEvent) {
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

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendTodoUpdatedEvent(TodoUpdatedEvent todoUpdatedEvent) {
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

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendTodoDeletedEvent(TodoDeletedEvent todoDeletedEvent) {
        CorrelationData cd = new CorrelationData("userDeleted:" + todoDeletedEvent.getId() + ":" + UUID.randomUUID());

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
