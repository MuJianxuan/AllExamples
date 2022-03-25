package org.example.rabbitmq.producer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * desc:
 *
 * @author create 2022/3/7 by rao
 */
@Configuration
public class HeadersConfig {

    @Bean
    public HeadersExchange headersExchange(){
        return new HeadersExchange("headersExchange");
    }

    @Bean
    public Queue xiaomiHeader(){
        return new Queue("xiaomiHeader");
    }

    @Bean
    public Queue huaweiHeader(){
        return new Queue("huaweiHeader");
    }

    @Bean
    public Binding xiaomiBinding(){
        // 存在xiaomi 这个key吧还是value呢？ key ok,value不行
        return BindingBuilder.bind(xiaomiHeader()).to(headersExchange()).whereAny("xiaomi").exist();
    }

    @Bean
    public Binding huaweiBinding(){
        // 简单理解  key为huawei 值要是 6s
        return BindingBuilder.bind(huaweiHeader()).to(headersExchange()).where("huawei").matches("6s");
    }

}
