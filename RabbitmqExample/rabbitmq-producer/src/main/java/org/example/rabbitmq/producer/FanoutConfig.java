package org.example.rabbitmq.producer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * desc: 广播模式使用
 *
 * @author: create 2022/3/7 by rao
 */
@Configuration
public class FanoutConfig {

    /**
     * aQueue
     */
    @Bean
    public Queue aQueue(){
        return new Queue("aQueue");
    }

    /**
     * bQueue
     * @return
     */
    @Bean
    public Queue bQueue(){
        return new Queue("bQueue");
    }

    /**
     * 定义广播交换机
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    public Binding bindingBQueue(){
        return BindingBuilder.bind(bQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingAQueue(){
        return BindingBuilder.bind( aQueue()).to(fanoutExchange());
    }

}
