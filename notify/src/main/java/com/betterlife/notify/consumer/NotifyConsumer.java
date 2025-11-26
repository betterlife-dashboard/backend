package com.betterlife.notify.consumer;

import com.betterlife.notify.event.TodoDeadlineEvent;
import com.betterlife.notify.event.TodoReminderEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotifyConsumer {

    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "${rabbit.deadline.queue.name}")
    public void consumeTodoDeadline(String message) {
        try {
            TodoDeadlineEvent event = objectMapper.readValue(message, TodoDeadlineEvent.class);
            System.out.println("event = " + event.getTitle());
        } catch (Exception e) {
            System.out.println("e = " + e);
        }
    }

    @RabbitListener(queues = "${rabbit.reminder.queue.name}")
    public void consumeTodoReminder(String message) {
        try {
            TodoReminderEvent event = objectMapper.readValue(message, TodoReminderEvent.class);
            System.out.println("event = " + event.getTitle());
        } catch (Exception e) {
            System.out.println("e = " + e);
        }
    }
}
