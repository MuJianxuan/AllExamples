package org.example.rabbitmq.producer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * desc: rabbitmq 中有默认的交换机，各模式都有默认的交换机
 * create 2022/3/6 by rao
 */
@Configuration
public class DirectConfig  {

    /**
     * 使用默认的直连交换机队列
     *
     * @return
     */
    @Bean
    public Queue defaultExchangeQueue() {
        // 默认是持久化的
        return new Queue("defaultExchangeQueue");
    }

    /**
     * 过期消息队列
     * @return
     */
    @Bean
    public Queue expireMsgQueue(){
        return new Queue("expireMsgQueue");
    }

    // 使用自定义的

    /**
     * 创建直连队列
     * @return
     */
    @Bean
    public Queue directQueue(){
        return new Queue("directQueue");
    }

    /**
     * 创建直连交换机
     * @return
     */
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("directExchange");
    }

    /**
     * 绑定交换机和队列
     * @return
     */
    @Bean
    public Binding directBinding(){
        return BindingBuilder.bind(directQueue()).to(directExchange()).withQueueName();
    }


}
