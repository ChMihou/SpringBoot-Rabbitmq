package com.space.rabbitmq.receiver;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息消费者2
 */
@Component
public class SecondConsumer {

    /**
     *类内传输消息类型为org.springframework.amqp.core.Message;
     */
    @RabbitListener(queues = {"second-queue"})
    public void handleMessage(Message message) throws Exception {
        // 处理消息
        System.out.println("SecondConsumer {} handleMessage :" + message);
    }
}
