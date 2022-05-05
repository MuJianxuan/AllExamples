package org.example.rocketmq.consumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * desc: 订阅关系一致性  group topic tag一致
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
     * 1、重复消费组会导致启动失败
     *
     * 一个应用可能多个组，那么多个组表示这个服务可能在多个集群中，这是合理的吗，会不会容易出现集群消费不一致的情况。
     * 消费组是全球唯一的
     */
    @Slf4j
    @Service
    //@RocketMQMessageListener(topic = "test-topic-1", consumerGroup = "my-consumer_test-topic-1")
    @RocketMQMessageListener(topic = "test-topic-1", consumerGroup = "my-consumer_test-topic1")
    public static class MyConsumer1 implements RocketMQListener<String> {
        @Override
        public void onMessage(String message) {
            log.info("received message: {}", message);
        }
    }

    /**
     * 消费组和生产组会有关系吗
     *
     *
     * 不可以基于注解创建同样名字的消费组？？？可是想要不同的 topic 怎么实现？
     */
    @Slf4j
    @Service
    //@RocketMQMessageListener(topic = "test-topic-2", consumerGroup = "my-consumer_test-topic-2")
    @RocketMQMessageListener(topic = "test-topic-2", consumerGroup = "my-consumer_test-topic2")
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
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderPaidEvent implements Serializable {

        private String orderId;

        private BigDecimal paidMoney;
    }

}
