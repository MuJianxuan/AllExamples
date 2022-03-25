package org.example.rabbitmq.producer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * desc: TopicExchange 是比较复杂但是也比较灵活的一种路由策略，
 * 在 TopicExchange 中，Queue 通过 routingkey 绑定到 TopicExchange 上，
 * 当消息到达 TopicExchange 后，TopicExchange 根据消息的 routingkey 将消息路由到一个或者多个 Queue 上。
 *
 * @author create 2022/3/7 by rao
 */
@Configuration
public class TopicConfig {

    /**
     * 定义 topicExchange
     * @return
     */
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("topicExchange");
    }

    @Bean
    public Queue xiaomi(){
        return new Queue("xiaomi");
    }

    @Bean
    public Queue huawei(){
        return new Queue("huawei");
    }

    /**
     * * 需要前后都匹配
     * @return
     */
    @Bean
    public Binding xiaomiBanding(){
        return BindingBuilder.bind(xiaomi()).to(topicExchange()).with("*.xiaomi.*");
    }

    /**
     * #
     * @return
     */
    @Bean
    public Binding huaweiBanding(){
        return BindingBuilder.bind(huawei()).to(topicExchange()).with("#.huawei.#");
    }

}
