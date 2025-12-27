package com.betterlife.notify.message;

import com.betterlife.notify.event.ScheduleAlarmEvent;
import com.betterlife.notify.service.NotifyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotifyConsumer {

    private final ObjectMapper objectMapper;
    private final NotifyService notifyService;

    @RabbitListener(queues = "${rabbit.queue.todo.alarm.name}")
    public void consumeTodoAlarmEvent(String message) {
        try {
            ScheduleAlarmEvent event = objectMapper.readValue(message, ScheduleAlarmEvent.class);
            if (event.getEventType().equals("create")) {
                notifyService.createTodoNotification(event);
            } else if (event.getEventType().equals("delete-todo")) {
                notifyService.deleteTodoNotification(event);
            } else if (event.getEventType().equals("delete-user")) {
                notifyService.deleteUserNotification(event);
            }
        } catch (Exception e) {
            System.err.println("e = " + e);
        }
    }
}
