//package com.debug.steadyjack.springbootMQ.server.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.support.CorrelationData;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * rabbitmq 配置
// * Created by steadyjack on 2017/12/01.
// */
//@Configuration
//public class RabbitmqConfig {
//
//    private final static Logger log = LoggerFactory.getLogger("mqLog");
//
//    @Autowired
//    private Environment env;
//
//    @Autowired
//    private CachingConnectionFactory connectionFactory;
//
//    @Autowired
//    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;
//
//    /**延迟队列配置**/
//
//    @Bean(name = "registerDelayQueue")
//    public Queue registerDelayQueue(){
//        Map<String, Object> params = new HashMap<>();
//        params.put("x-dead-letter-exchange",env.getProperty("register.exchange.name"));
//        params.put("x-dead-letter-routing-key","all");
//        return new Queue(env.getProperty("register.delay.queue.name"), true,false,false,params);
//    }
//
//    @Bean
//    public DirectExchange registerDelayExchange(){
//        return new DirectExchange(env.getProperty("register.delay.exchange.name"));
//    }
//
//    @Bean
//    public Binding registerDelayBinding(){
//        return BindingBuilder.bind(registerDelayQueue()).to(registerDelayExchange()).with("");
//    }
//
//    /**延迟队列配置**/
//
//    /**指标消费队列配置**/
//
//    @Bean
//    public TopicExchange registerTopicExchange(){
//        return new TopicExchange(env.getProperty("register.exchange.name"));
//    }
//
//    @Bean
//    public Binding registerBinding(){
//        return BindingBuilder.bind(registerQueue()).to(registerTopicExchange()).with("all");
//    }
//
//    @Bean(name = "registerQueue")
//    public Queue registerQueue(){
//        return new Queue(env.getProperty("register.queue.name"),true);
//    }
//
//    /**指标消费队列配置**/
//
//    /**
//     * 单一消费者
//     * @return
//     */
//    @Bean(name = "singleListenerContainer")
//    public SimpleRabbitListenerContainerFactory listenerContainer(){
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setMessageConverter(new Jackson2JsonMessageConverter());
//        factory.setConcurrentConsumers(1);
//        factory.setMaxConcurrentConsumers(1);
//        factory.setPrefetchCount(1);
//        factory.setTxSize(1);
//        return factory;
//    }
//
//    /**
//     * 多个消费者
//     * @return
//     */
//    @Bean(name = "multiListenerContainer")
//    public SimpleRabbitListenerContainerFactory multiListenerContainer(){
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factoryConfigurer.configure(factory,connectionFactory);
//        factory.setMessageConverter(new Jackson2JsonMessageConverter());
//        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
//        return factory;
//    }
//
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(){
//        connectionFactory.setPublisherConfirms(true);
//        connectionFactory.setPublisherReturns(true);
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMandatory(true);
//        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
//            @Override
//            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//                log.info("消息发送成功:correlationData({}),ack({}),cause({})",correlationData,ack,cause);
//            }
//        });
//        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
//            @Override
//            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//                log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message);
//            }
//        });
//        return rabbitTemplate;
//    }
//
//}
