package com.betterlife.todo.message;

import com.betterlife.todo.event.ScheduleAlarmEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EventProducer {

    private final String todoAlarmExchange;
    private final String todoAlarmKey;
    private final RabbitTemplate rabbitTemplate;

    public EventProducer(
            @Value("${rabbit.exchanges.todo.alarm}") String todoAlarmExchange,
            @Value("${rabbit.queue.todo.alarm.key}") String todoAlarmKey,
            RabbitTemplate rabbitTemplate
    ) {
        this.todoAlarmExchange = todoAlarmExchange;
        this.todoAlarmKey = todoAlarmKey;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendTodoAlarm(ScheduleAlarmEvent scheduleAlarmEvent) {
        rabbitTemplate.convertAndSend(todoAlarmExchange, todoAlarmKey, scheduleAlarmEvent);
    }

}
