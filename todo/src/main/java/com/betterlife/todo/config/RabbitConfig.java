package com.betterlife.todo.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Value("${rabbit.exchanges.todo}")
    private String todoExchangeName;

    @Bean
    public DirectExchange todoExchange() {
        return new DirectExchange(todoExchangeName);
    }
}
