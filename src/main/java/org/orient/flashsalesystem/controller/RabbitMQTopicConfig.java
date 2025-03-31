package org.orient.flashsalesystem.controller;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 主题路由
 */
@Configuration
public class RabbitMQTopicConfig {
    private static final String EXCHANGE = "SecKillExchange";
    private static final String QUEUE = "SecKillQueue";

    @Bean
    public Queue Queue() {
        return new Queue(QUEUE);
    }

    @Bean
    public TopicExchange Exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding Binding() {
        return BindingBuilder.bind(Queue()).to(Exchange()).with("seckill.#");
    }
}
