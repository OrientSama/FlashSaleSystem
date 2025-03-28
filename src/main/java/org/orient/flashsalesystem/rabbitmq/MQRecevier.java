package org.orient.flashsalesystem.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQRecevier {
    @RabbitListener(queues = "queue")
    public void receive(Object msg) {
        log.info("接受消息:" + msg);
    }
}
