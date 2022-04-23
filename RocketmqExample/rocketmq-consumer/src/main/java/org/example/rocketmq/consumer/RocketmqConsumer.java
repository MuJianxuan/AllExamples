package org.example.rocketmq.consumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * desc:
 *
 * @author Rao
 * @Date 2022/04/20
 **/
@SpringBootApplication
public class RocketmqConsumer {
    public static void main(String[] args) {
        SpringApplication.run( RocketmqConsumer.class,args);
    }

    /**
     * 消费组概念
     * RocketMQMessageListener 注解的实现是基于 process实现。
     */
    @Slf4j
    @Service
    @RocketMQMessageListener(topic = "test-topic-1", consumerGroup = "my-consumer_test-topic-1")
    public static class MyConsumer1 implements RocketMQListener<String> {
        @Override
        public void onMessage(String message) {
            log.info("received message: {}", message);
        }
    }

    @Slf4j
    @Service
    @RocketMQMessageListener(topic = "test-topic-2", consumerGroup = "my-consumer_test-topic-2")
    public static class MyConsumer2 implements RocketMQListener<OrderPaidEvent>{
        @Override
        public void onMessage(OrderPaidEvent orderPaidEvent) {
            log.info("received orderPaidEvent: {}", orderPaidEvent);
        }
    }


    /**
     * 这里需要留意的是  订单对象的包路径是不同的，看序列化消息接受会不会失败
     */
    @Data
    @AllArgsConstructor
    public static class OrderPaidEvent implements Serializable {

        private String orderId;

        private BigDecimal paidMoney;
    }

}
